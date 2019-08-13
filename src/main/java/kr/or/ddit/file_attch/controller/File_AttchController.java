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
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.file_attch.model.File_AttchVo;
import kr.or.ddit.file_attch.service.IFile_AttchService;
import kr.or.ddit.link_attch.model.Link_attchVo;
import kr.or.ddit.link_attch.service.ILink_attchService;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.util.PartUtil;

//link Controller랑 합침!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
@Controller
@RequestMapping("/main")
public class File_AttchController {
	
	private static final Logger logger = LoggerFactory.getLogger(File_AttchController.class);
	
	@Resource(name="file_AttchService")
	private IFile_AttchService file_AttchService;
	
	@Resource(name="link_attchService")
	private ILink_attchService link_attchService;
	
	/**
	* Method : mainFile
	* 작성자 : PC13
	* 변경이력 :
	* @throws IOException
	* Method 설명 : fileLink_List 전체 업무에 대한 파일 조회, 삭제 하는곳 pagination
	*/
	@RequestMapping(path="/fileList" , method=RequestMethod.GET)
	public String fileList(Model model, PageVo pageVo) {
		// prj_id 받아와야함
		int prj_id = 1;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("prj_id", prj_id);

		Map<String, Object> resultMap = file_AttchService.fPagination(map);
		List<File_AttchVo> fileList = (List<File_AttchVo>) resultMap.get("fileList");
		int paginationSize = (Integer) resultMap.get("paginationSize");
		logger.debug("♬♩♪  paginationSize: {}", paginationSize);

		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);

		model.addAttribute("fileList", fileList);
		model.addAttribute("prj_id", prj_id);
		
		//삭제 구분
		String del = "FList";
		model.addAttribute("del", del);
		
		return "/main/fileLink.user.tiles";
	}
	
	@RequestMapping(path="/linkList" , method=RequestMethod.GET)
	public String LinkList(Model model, PageVo pageVo) {
		// prj_id 받아와야함
		
		int prj_id = 1;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("prj_id", prj_id);

		Map<String, Object> resultMap = link_attchService.lPagination(map);
		List<File_AttchVo> linkList = (List<File_AttchVo>) resultMap.get("linkList");
		int paginationSize = (Integer) resultMap.get("paginationSize");
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);

		model.addAttribute("linkList", linkList);
		model.addAttribute("prj_id", prj_id);
		
		//삭제 구분
		String del = "LList";
		model.addAttribute("del", del);
		
		return "/main/fileLink.user.tiles";
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	* Method : insertFLPost
	* 작성자 : PC13
	* 변경이력 ://
	* @param fileVo
	* @param profile
	 * @throws IOException
	* Method 설명 : insertFLPost 파일 추가
	*/
	@RequestMapping(path="/insertFilePost", method = RequestMethod.POST)
	public String insertFLPost(@RequestPart MultipartFile[] profile, Model model, HttpSession session) {
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();

		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		int wrk_id = 110; //나중에 누나꺼랑 합치면 바꿔야합니다~
		
		int cnt = 0;
		// 올릴 파일수....
		int count = 0;
		String db_file_nm = UUID.randomUUID().toString();
		logger.debug("♬♩♪  insertFilePost + db_file_nm: {}", db_file_nm);
		for (MultipartFile f : profile) {
			if (f.getSize() > 0) {
				count++;
				String original_file_nm = f.getOriginalFilename();
				String file_exts = PartUtil.getExt(original_file_nm);
				String uploadPath = PartUtil.getUploadPath();
				String path = uploadPath + File.separator + db_file_nm + file_exts;

				int file_size = profile.length;
				File uploadFile = new File(path);
				// 해당 위치에 업로드
				try {
					// 해당 파일 지정된 경로에 업로드...
					f.transferTo(uploadFile);
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
				
				cnt += file_AttchService.insertFile(file_attchVo);
			}
		}
		if (cnt == count) {
			logger.debug("♬♩♪  업로드 완료!!!!!");
		}
		model.addAttribute("fileList", file_AttchService.fileList(1));

		return "redirect:/main/filePagingList";
	}
	
	/**
	* Method : insertLinkPost
	* 작성자 : PC13
	* 변경이력 :
	* @param fileVo
	* @param profile
	 * @throws IOException
	* Method 설명 : insertLinkPost 링크 추가
	*/
	@RequestMapping(path="/insertLinkPost", method = RequestMethod.POST)
	public String insertLinkPost(String attch_url, Model model) {
		logger.debug("♬♩♪  attch_url: {}", attch_url);
		int prj_id =1;
		int wrk_id =1;
		String user_email = "chew@naver.com";
		
		Link_attchVo link_attchVo = new Link_attchVo(prj_id, user_email, wrk_id, attch_url);
		
		link_attchService.insertLink(link_attchVo);
		
		model.addAttribute("linkList", link_attchService.linkList(1));
		return "redirect:/main/linkPagingList";
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////
	//등록할때의 Pagination
	
	/**
	* Method : filePagingList
	* 작성자 : PC13
	* 변경이력 :
	* @param PageVo pageVo, int board_id, Model model
	* @param PageVo pageVo, int board_id, Model model
	 * @throws IOException
	* Method 설명 : filePagingList pagination
	*/
	@RequestMapping(path = "/filePagingList", method = RequestMethod.GET)
	public String filePagingList(PageVo pageVo,  Model model) {
		int wrk_id = 1;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("wrk_id", wrk_id);
		
		Map<String, Object> resultMap = file_AttchService.insertFPagination(map);
		List<File_AttchVo> fileList = (List<File_AttchVo>) resultMap.get("fileList");
		int paginationSize = (Integer) resultMap.get("paginationSize");
		logger.debug("♬♩♪  paginationSize: {}", paginationSize);

		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);

		model.addAttribute("fileList", fileList);
		model.addAttribute("wrk_id", wrk_id);
		
		//삭제 구분
		String del = "FIList";
		model.addAttribute("del", del);
		return "/propertySet/setWorkFile.user.tiles";
	}
	
	/**
	* Method : LinkPagingList
	* 작성자 : PC13
	* 변경이력 :
	* @param PageVo pageVo, int board_id, Model model
	* @param PageVo pageVo, int board_id, Model model
	 * @throws IOException
	* Method 설명 : LinkPagingList pagination
	*/
	@RequestMapping(path = "/linkPagingList", method = RequestMethod.GET)
	public String linkPagingList(PageVo pageVo,  Model model) {
		int wrk_id = 1;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("wrk_id", wrk_id);

		Map<String, Object> resultMap = link_attchService.insertLPagination(map);
		List<Link_attchVo> linkList = (List<Link_attchVo>) resultMap.get("linkList");
		int paginationSize = (Integer) resultMap.get("paginationSize");
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);

		model.addAttribute("linkList", linkList);
		model.addAttribute("wrk_id", wrk_id);
		
		String del = "LIList";
		model.addAttribute("del", del);
		return "/propertySet/setWorkFile.user.tiles";
	}

	//파일링크 리스트 보여주는 페이지, 등록하는 페이지에서의 삭제 처리
	@RequestMapping(path="/fileUpdate", method = RequestMethod.GET)
	public String fileUpdate(int file_id, String del) {
		logger.debug("♬♩♪  file_id: {}", file_id);
		logger.debug("♬♩♪  del: {}", del);
		file_AttchService.updateFile(file_id);
		if(del.equals("FList")) {
			logger.debug("♬♩♪  삭제완료 fileList");
			return "redirect:/main/fileList";
		}else {
			logger.debug("♬♩♪  삭제완료 filePagingList");
			return "redirect:/main/filePagingList";
		}
	}
	
	@RequestMapping(path="/linkUpdate", method = RequestMethod.GET)
	public String linkUpdate(int link_id, String del) {
		logger.debug("♬♩♪  link_id: {}", link_id);
		logger.debug("♬♩♪  del: {}", del);
		link_attchService.updateLink(link_id);
		if(del.equals("LList")) {
			logger.debug("♬♩♪  삭제완료 linkList");
			return "redirect:/main/linkList";
		}else {
			logger.debug("♬♩♪  삭제완료 linkPagingList");
			return "redirect:/main/linkPagingList";
		}
	}

	/**
	* Method : fileDownload
	* 작성자 : PC13
	* 변경이력 :
	* @param fileVo
	* @param profile
	* @throws IOException
	* Method 설명 : fileDownLoad
	*/
	@RequestMapping(path = "/fileDownload", method = RequestMethod.GET)
	public void fileDownload(int file_id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		logger.debug("♬♩♪  file_id: {}", file_id);

		file_id = Integer.parseInt(request.getParameter("file_id"));

		File_AttchVo fileVo = file_AttchService.getFile(file_id);
		logger.debug("♬♩♪  fileVo: {}", fileVo);
		
		// 파일 업로드된 경로
		String savePath = "C:\\Users\\손영하\\Desktop\\중요한거\\W_윈도우초반설정\\A_TeachingMaterial\\7.LastProject\\workspace\\STTM\\src\\main\\webapp\\uploadFile\\2019\\07";
		logger.debug("♬♩♪  savePath: {}", savePath);
		
		// 실제 내보낼 파일명
		String orgfilename = fileVo.getOriginal_file_nm();
		logger.debug("♬♩♪  orgfilename: {}", orgfilename);

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
			} catch (FileNotFoundException fe) {
				skip = true;
			}

			client = request.getHeader("User-Agent");

			// 파일 다운로드 헤더 지정
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Description", "JSP Generated Data");

			if (!skip) {
				// IE
				if (client.indexOf("MSIE") != -1) {
					response.setHeader("Content-Disposition",
							"attachment; filename=" + new String(orgfilename.getBytes("KSC5601"), "ISO8859_1"));

				} else {
					// 한글 파일명 처리
					orgfilename = new String(orgfilename.getBytes("utf-8"), "iso-8859-1");

					response.setHeader("Content-Disposition", "attachment; filename=\"" + orgfilename + "\"");
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
			in.close();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}