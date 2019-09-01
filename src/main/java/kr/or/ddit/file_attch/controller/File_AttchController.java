package kr.or.ddit.file_attch.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.file_attch.model.File_AttchVo;
import kr.or.ddit.file_attch.service.IFile_AttchService;
import kr.or.ddit.file_dw_his.model.File_Dw_HisVo;
import kr.or.ddit.file_dw_his.service.IFile_Dw_HisService;
import kr.or.ddit.link_attch.model.Link_attchVo;
import kr.or.ddit.link_attch.service.ILink_attchService;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.project.service.IProjectService;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.util.PartUtil;
import kr.or.ddit.work_mem_flw.model.Work_Mem_FlwVo;
import kr.or.ddit.work_mem_flw.service.IWork_Mem_FlwService;

//link Controller랑 합침!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
@Controller
public class File_AttchController {
	
	private static final Logger logger = LoggerFactory.getLogger(File_AttchController.class);
	
	@Resource(name="file_AttchService")
	private IFile_AttchService file_AttchService;
	
	@Resource(name="link_attchService")
	private ILink_attchService link_attchService;
	
	@Resource(name="projectService")
	private IProjectService projectService;
	
	@Resource(name="work_Mem_FlwService")
	private IWork_Mem_FlwService workMemFlwService;
	
	@Resource(name="file_Dw_HisService")
	private IFile_Dw_HisService file_Dw_HisService;
	
	/**
	 * Method 		: fileDownLoad
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-26 최초 생성
	 * @param file_id
	 * @param request
	 * @param response
	 * Method 설명 	: 파일 다운로드 처리!
	 */
	@RequestMapping(path = "/fileDownLoad", method = RequestMethod.GET)
	public void fileDownLoad(int file_id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		file_id = Integer.parseInt(request.getParameter("file_id"));
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();
		logger.debug("♬♩♪  prj_id:{}",prj_id);
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		File_Dw_HisVo file_Dw_HisVo = new File_Dw_HisVo(prj_id, user_email, file_id);
		int cnt = file_Dw_HisService.insertHistory(file_Dw_HisVo);
		if(cnt==1) {
			logger.debug("다운로드 기록 등록");
		}
		File_AttchVo file_AttchVo = file_AttchService.getFile(file_id);
		logger.debug("♬♩♪  fileDownLoad file_id: {}", file_id);
		// 파일 업로드된 경로
		String savePath = "C:\\uploadFile\\2019\\08\\";
		
		logger.debug("♬♩♪  savePath1: {}", savePath);
		
		// 실제 내보낼 파일명
		String original_file_nm = file_AttchVo.getOriginal_file_nm();
		logger.debug("♬♩♪  original_file_nm: {}", original_file_nm);
		savePath += File.separator + file_AttchVo.getDb_file_nm() + file_AttchVo.getFile_exts();
		logger.debug("♬♩♪  savePath2: {}", savePath);
		InputStream in = null;
		OutputStream os = null;
		File file = null;
		boolean skip = false;
		String client = "";

		try {
			// 파일을 읽어 스트림에 담기
			try {
				file = new File(savePath);
				in = new FileInputStream(file);
				logger.debug("♬♩♪  file: {}", file);
				logger.debug("♬♩♪  in: {}", in);
			} catch (FileNotFoundException fe) {
				skip = true;
			}
			
			logger.debug("♬♩♪  skip: {}", skip);
			client = request.getHeader("User-Agent");

			// 파일 다운로드 헤더 지정
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Description", "JSP Generated Data");

			if (!skip) {
				// IE
				if (client.indexOf("MSIE") != -1) {
					response.setHeader("Content-Disposition",
							"attachment; filename=" + new String(original_file_nm.getBytes("KSC5601"), "ISO8859_1"));

				} else {
					// 한글 파일명 처리
					original_file_nm = new String(original_file_nm.getBytes("utf-8"), "iso-8859-1");

					response.setHeader("Content-Disposition", "attachment; filename=\"" + original_file_nm + "\"");
					response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
				}

				response.setHeader("Content-Length", "" + file.length());

				os = response.getOutputStream();
				byte b[] = new byte[(int) file.length()];
				int leng = 0;

				while ((leng = in.read(b)) > 0) {
					os.write(b, 0, leng);
				}
			}
			logger.debug("♬♩♪  in:{}",in);
			in.close();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//공용보관함입니다.//공용보관함입니다.//공용보관함입니다.//공용보관함입니다.//공용보관함입니다.//공용보관함입니다.//공용보관함입니다.//공용보관함입니다.
	@RequestMapping(path = "/publicFilePagination", method = RequestMethod.GET)
	String publicFilePagination(HttpSession session, Model model) {
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		model.addAttribute("user_email", user_email);
		
		return "/main/fileLink/fileLinkCommon.user.tiles";
	}
	
	@RequestMapping(path = "/update", method = RequestMethod.GET)
	String update(int file_id,HttpSession session, Model model) {
		logger.debug("♬♩♪  file_id: {}", file_id);
		file_AttchService.updateFile(file_id);
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		model.addAttribute("user_email", user_email);
		return "redirect:/publicFilePagination";
	}
	
	@RequestMapping("/publicLinkPagination")
	public String publicLinkPagination(HttpSession session, Model model, PageVo pageVo) {
		logger.debug("♬♩♪  publicFilePagination");
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();
		
		//link pagination
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("page", pageVo.getPage());
		map1.put("pageSize", pageVo.getPageSize());
		map1.put("prj_id", prj_id);
		logger.debug("♬♩♪  map: {}", map1);
		
		Map<String, Object> resultMap =file_AttchService.publicLinkPagination(map1);
		List<File_AttchVo> publicLinkList = (List<File_AttchVo>) resultMap.get("publicLinkList");
		
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		model.addAttribute("user_email", user_email);
		
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);
		
		model.addAttribute("publicLinkList", publicLinkList);
		return "jsonView";
	}
	
	@RequestMapping("/publicFilePagination2")
	public  @ResponseBody HashMap<String, Object> publicFilePagination2(HttpSession session, Model model, PageVo pageVo) {
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("prj_id", prj_id);
		logger.debug("♬♩♪  map: {}", map);
		
		//file pagination
		Map<String, Object> resultMap =file_AttchService.publicFilePagination(map);
		List<File_AttchVo> publicFileList = (List<File_AttchVo>) resultMap.get("publicFileList");
		
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		//테스트
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		Project_MemVo project_MemVo = new Project_MemVo();
		
		project_MemVo.setUser_email(user_email);
		project_MemVo.setPrj_id(prj_id);
		logger.debug("♬♩♪  로그인한 사람의 LV조회!긔긔 : {}", file_AttchService.selectLV(project_MemVo));
		
		model.addAttribute("LV", file_AttchService.selectLV(project_MemVo));	
		//테스트
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("paginationSize", paginationSize);
		hashmap.put("pageVo", pageVo);
		hashmap.put("publicFileList", publicFileList);
		hashmap.put("prj_id", prj_id);
		hashmap.put("LV", file_AttchService.selectLV(project_MemVo));
		
		model.addAttribute("user_email", user_email);
		hashmap.put("user_email", user_email);
		
		return hashmap;
	}
	
	// 파일링크 리스트 보여주는 페이지, 등록하는 페이지에서의 삭제 처리
	@RequestMapping("/updateFile")
	public  @ResponseBody HashMap<String, Object> updateFile(int file_id, HttpSession session, PageVo pageVo) {
		logger.debug("♬♩♪  file_id: {}", file_id);

		int cnt = file_AttchService.updateFile(file_id);

		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("prj_id", prj_id);
		logger.debug("♬♩♪  map: {}", map);
		
		//file pagination
		Map<String, Object> resultMap =file_AttchService.publicFilePagination(map);
		List<File_AttchVo> publicFileList = (List<File_AttchVo>) resultMap.get("publicFileList");
		
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("paginationSize", paginationSize);
		hashmap.put("pageVo", pageVo);
		hashmap.put("publicFileList", publicFileList);
		hashmap.put("prj_id", prj_id);
		hashmap.put("user_email", user_email);
		
		logger.debug("♬♩♪  hashmap: {}", hashmap);
		
		return hashmap;
	}
		
	@RequestMapping("/updateLink")
	public  @ResponseBody HashMap<String, Object> updateLink(int link_id, HttpSession session, PageVo pageVo ) {
		logger.debug("♬♩♪  updateLink link_id: {}", link_id);
		
		int cnt = file_AttchService.updateLink(link_id);
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();
		
		//link pagination
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("page", pageVo.getPage());
		map1.put("pageSize", pageVo.getPageSize());
		map1.put("prj_id", prj_id);
		logger.debug("♬♩♪  map: {}", map1);
		
		Map<String, Object> resultMap =file_AttchService.publicLinkPagination(map1);
		List<File_AttchVo> publicLinkList = (List<File_AttchVo>) resultMap.get("publicLinkList");
		
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("paginationSize", paginationSize);
		hashmap.put("pageVo", pageVo);
		hashmap.put("publicLinkList", publicLinkList);
		hashmap.put("user_email", user_email);
		
		return hashmap;
	}
	//공용보관함입니다.//공용보관함입니다.//공용보관함입니다.//공용보관함입니다.//공용보관함입니다.//공용보관함입니다.//공용보관함입니다.//공용보관함입니다.
	
	//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.
	@RequestMapping("/individualBox")
	String individualBox() {
		logger.debug("♬♩♪  여기는 개인 보관함입니다");
		return "/main/fileLink/FileIndividualBox.user.tiles";
	}
	
	@RequestMapping("/individualPagination")
	String individualPagination(Model model, PageVo pageVo, HttpSession session) {
		logger.debug("♬♩♪  여기는 개인보관함 apgination 처리하는 controller입니다.");
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("user_email", user_email);
		
		Map<String, Object> resultMap =file_AttchService.individualPagination(map);
		List<File_AttchVo> individualList = (List<File_AttchVo>) resultMap.get("individualList");
		
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("individualList", individualList);
		model.addAttribute("user_email", user_email);
		
		return "jsonView";
	}
	
	@RequestMapping("/updateInFile")
	String updateInFile(Model model, PageVo pageVo, int file_id, HttpSession session) {
		int cnt = file_AttchService.updateFile(file_id);
		if(cnt==1) {
			logger.debug("♬♩♪  삭제완료");
		}
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("user_email", user_email);
		
		Map<String, Object> resultMap =file_AttchService.individualPagination(map);
		List<File_AttchVo> individualList = (List<File_AttchVo>) resultMap.get("individualList");
		
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("individualList", individualList);
		model.addAttribute("user_email", user_email);
		return "redirect:/individualPagination";
	}
	
	@RequestMapping("/searchFile")
	public  @ResponseBody HashMap<String, Object> searchFile(Model model, PageVo pageVo, String original_file_nm) {
		//파일이름으로 검색 fileName  모든 파일 검색 fileAll
		logger.debug("♬♩♪  original_file_nm: {}", original_file_nm);
		
			logger.debug("♬♩♪  여기는 파일 이름으로 검색!");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", pageVo.getPage());
			map.put("pageSize", pageVo.getPageSize());
			map.put("original_file_nm", original_file_nm);
			
			Map<String, Object> resultMap = file_AttchService.individualSearchPagination(map);
			List<File_AttchVo> individualList = (List<File_AttchVo>) resultMap.get("searchIndividualList");
			logger.debug("♬♩♪  individualList: {}", individualList);
			int paginationSize = (Integer) resultMap.get("paginationSize");
			model.addAttribute("paginationSize", paginationSize);
			model.addAttribute("pageVo", pageVo);
			model.addAttribute("individualList", individualList);
			
			HashMap<String, Object> hashmap = new HashMap<String, Object>();
			hashmap.put("paginationSize", paginationSize);
			hashmap.put("pageVo", pageVo);
			hashmap.put("individualList", individualList);
			return hashmap;
	}
	//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//개인보관함입니다.//
	

	
	//특정업무에서 파일, 링크 등록할때//특정업무에서 파일, 링크 등록할때//특정업무에서 파일, 링크 등록할때//특정업무에서 파일, 링크 등록할때//특정업무에서 파일, 링크 등록할때
	//workFilePagination
	@RequestMapping("/workFilePagination")
	public  @ResponseBody HashMap<String, Object> workFilePagination(HttpSession session, Model model, PageVo pageVo, int wrk_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("wrk_id", wrk_id);
		
		Map<String, Object> resultMap = file_AttchService.workFilePagination(map);
		List<File_AttchVo> workFileList = (List<File_AttchVo>) resultMap.get("workFileList");
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("workFileList", workFileList);
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("paginationSize", paginationSize);
		hashmap.put("pageVo", pageVo);
		hashmap.put("workFileList", workFileList);
		hashmap.put("user_email", user_email);
		
		return hashmap;
	}
	
	//workLinkPagination
	@RequestMapping("/workLinkPagination")
	public  @ResponseBody HashMap<String, Object> workLinkPagination(HttpSession session, Model model, PageVo pageVo, int wrk_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("wrk_id", wrk_id);
		
		Map<String, Object> resultMap = file_AttchService.workLinkPagination(map);
		List<Link_attchVo> workLinkList = (List<Link_attchVo>) resultMap.get("workLinkList");
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("workLinkList", workLinkList);
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("paginationSize", paginationSize);
		hashmap.put("pageVo", pageVo);
		hashmap.put("workLinkList", workLinkList);
		hashmap.put("user_email", user_email);
		
		return hashmap;
	}
	
	//work.jsp 에서 file 상태 삭제로 변경
	@RequestMapping("/workDelFile")
	public  @ResponseBody HashMap<String, Object> workDelFile(int file_id, HttpSession session, Model model, PageVo pageVo, int wrk_id) {
		logger.debug("♬♩♪  workDelFile입니다~~ : {}", file_id);
		logger.debug("♬♩♪  workDelFile입니다~~ : {}", wrk_id);
		
		int cnt = file_AttchService.updateFile(file_id);
	
		if(cnt==1) {
			logger.debug("♬♩♪  삭제완료");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("wrk_id", wrk_id);
		
		Map<String, Object> resultMap = file_AttchService.workFilePagination(map);
		List<File_AttchVo> workFileList = (List<File_AttchVo>) resultMap.get("workFileList");
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("workFileList", workFileList);
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("paginationSize", paginationSize);
		hashmap.put("pageVo", pageVo);
		hashmap.put("workFileList", workFileList);
		hashmap.put("user_email", user_email);
		
		return hashmap;
	}
	
	//work.jsp 에서 link 상태 삭제로 변경
	@RequestMapping("/workDelLink")
	public  @ResponseBody HashMap<String, Object> workDelLink(int link_id, HttpSession session, Model model, PageVo pageVo, int wrk_id) {
		logger.debug("♬♩♪  workDelFile입니다~~ : {}", link_id);
		logger.debug("♬♩♪  workDelFile입니다~~ : {}", wrk_id);
		
		int cnt = file_AttchService.updateLink(link_id);
		
		if(cnt==1) {
			logger.debug("♬♩♪  삭제완료");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("wrk_id", wrk_id);
		
		Map<String, Object> resultMap = file_AttchService.workLinkPagination(map);
		List<Link_attchVo> workLinkList = (List<Link_attchVo>) resultMap.get("workLinkList");
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("workLinkList", workLinkList);
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("paginationSize", paginationSize);
		hashmap.put("pageVo", pageVo);
		hashmap.put("workLinkList", workLinkList);
		hashmap.put("user_email", user_email);
		
		return hashmap;
	}
	
	//workFileUpload
	@RequestMapping("/workFileUpload")
	public @ResponseBody HashMap<String, Object> workFileUpload(HttpSession session, Model model, PageVo pageVo, String locker,
			@RequestPart MultipartFile profile, int wrk_id) {
		
		
		
		ProjectVo projectVo = projectService.getPrjByWrk(wrk_id);
		int prj_id = projectVo.getPrj_id();
		
		//배정된 업무 멤버 가져오기
		Work_Mem_FlwVo work_memVo = new Work_Mem_FlwVo(wrk_id, "M");
		List<Work_Mem_FlwVo> wrkMemList = workMemFlwService.workMemFlwList(work_memVo); 
		
		//업무 팔로워 멤버 가져오기
		Work_Mem_FlwVo work_flwVo = new Work_Mem_FlwVo(wrk_id, "F");
		List<Work_Mem_FlwVo> wrkFlwList = workMemFlwService.workMemFlwList(work_flwVo); 
		
		logger.debug("♬♩♪  wrk_id: {}", wrk_id);
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		logger.debug("♬♩♪  locker: {}", locker);
		int cnt = 0;
		// 올릴 파일수....
		int count = 0;
		
		String db_file_nm = UUID.randomUUID().toString();
			if (profile.getSize() > 0) {
				count++;
				String original_file_nm = profile.getOriginalFilename();
				String file_exts = PartUtil.getExt(original_file_nm);
				String uploadPath = PartUtil.getUploadPath();
				String path = uploadPath + File.separator + db_file_nm + file_exts;

				long file_size = profile.getSize();
				File uploadFile = new File(path);
				// 해당 위치에 업로드
				try {
					// 해당 파일 지정된 경로에 업로드...
					profile.transferTo(uploadFile);
					logger.debug("♬♩♪  생성완료");
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
				
				File_AttchVo file_attchVo = new File_AttchVo(
						prj_id, 
						user_email,
						wrk_id,
						original_file_nm,
						db_file_nm,
						file_size, 
						file_exts
						);
				
				if(locker.equals("public")) {
					cnt += file_AttchService.insertFilePublic(file_attchVo);
					
				}else if(locker.equals("individual")) {
					cnt += file_AttchService.insertFileindividual(file_attchVo);
					
				}else if(locker.equals("both")) {
					cnt += file_AttchService.insertFileboth(file_attchVo);
				}
			}
		if (cnt == count) {
			logger.debug("♬♩♪  업로드 완료!!!!!");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("wrk_id", wrk_id);
		
		Map<String, Object> resultMap = file_AttchService.workFilePagination(map);
		List<File_AttchVo> workFileList = (List<File_AttchVo>) resultMap.get("workFileList");
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("workFileList", workFileList);
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("paginationSize", paginationSize);
		hashmap.put("pageVo", pageVo);
		hashmap.put("workFileList", workFileList);
		hashmap.put("wrkMemList", wrkMemList);
		hashmap.put("wrkFlwList", wrkFlwList);
		hashmap.put("user_email", user_email);
		
		return hashmap;
		
	}
	
	//workLinkUpload
	@RequestMapping("/workLinkUpload")
	public @ResponseBody HashMap<String, Object> workLinkUpload(HttpSession session, Model model, 
			PageVo pageVo, String locker, int wrk_id, String attch_url) {
		
		logger.debug("♬♩♪  여기는 workLinkUpload insert하는곳");
		
		ProjectVo projectVo = projectService.getPrjByWrk(wrk_id);
		int prj_id = projectVo.getPrj_id();
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
			
		Link_attchVo link_attchVo = new Link_attchVo(prj_id, user_email, wrk_id, attch_url);	
			
		int cnt = file_AttchService.insertLink(link_attchVo);
		if(cnt==1) {
			logger.debug("♬♩♪  link 등록완료!");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("wrk_id", wrk_id);
		
		Map<String, Object> resultMap = file_AttchService.workLinkPagination(map);
		List<Link_attchVo> workLinkList = (List<Link_attchVo>) resultMap.get("workLinkList");
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("workLinkList", workLinkList);
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("paginationSize", paginationSize);
		hashmap.put("pageVo", pageVo);
		hashmap.put("workLinkList", workLinkList);
		hashmap.put("user_email", user_email);
		
		return hashmap;
	}
	//특정업무에서 파일, 링크 등록할때//특정업무에서 파일, 링크 등록할때//특정업무에서 파일, 링크 등록할때//특정업무에서 파일, 링크 등록할때//특정업무에서 파일, 링크 등록할때
	
	
	//chatBotApi//chatBotApi//chatBotApi//chatBotApi//chatBotApi//chatBotApi//chatBotApi//chatBotApi//chatBotApi//chatBotApi//chatBotApi
	@RequestMapping("/chatBotApi")
	String chatBotApi(Model model, String question) {

		if(question.contains("안녕")) {
			model.addAttribute("data", "안녕하세요. 척척박사 ChatBot이에요.");
		}else if(question.contains("시연")) {
			model.addAttribute("data", "시연 순서는 ");
		}else if(question.contains("닫기")) {
			model.addAttribute("data", "감사합니다");
		}
		return "jsonView";
	}
	//chatBotApi//chatBotApi//chatBotApi//chatBotApi//chatBotApi//chatBotApi//chatBotApi//chatBotApi//chatBotApi//chatBotApi//chatBotApi
	
	
	
	
	
	
	
	
	
	
	
}