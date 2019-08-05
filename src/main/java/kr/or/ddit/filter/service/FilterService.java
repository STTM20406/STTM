package kr.or.ddit.filter.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.filter.dao.IFilterDao;
import kr.or.ddit.filter.model.FilterVo;
import kr.or.ddit.ganttChart.model.GanttChartVo;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.work.model.WorkVo;

/**
 * FilterService.java
 *
 * @author 유승진
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 유승진 2019-07-31 최초 생성
 *
 * </pre>
 */
@Service
public class FilterService implements IFilterService{
	private static final Logger logger = LoggerFactory.getLogger(FilterService.class);
	@Resource(name="filterDao")
	private IFilterDao filterDao;

	private Map<String, Object> resultMap; 
	
	@Override
	public List<WorkVo> filterList(FilterVo filterVo) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		Date today = new Date();
		filterVo.setTd_month(sdf.format(today));
		sdf.applyPattern("ww");
		filterVo.setTd_week(sdf.format(today));
		return filterDao.filterList(filterVo);
	}

	@Override
	public Map<String, Object> workListJSON(FilterVo filterVo) {
		resultMap = new HashMap<String, Object>();
		List<WorkVo> workList = filterList(filterVo);
		String result = resultListTemplate(workList);
		String prj_str = prjListTemplate(filterVo);
		String followerList_str = followerListTemplate(filterVo);
		String makerList_str = makerListTemplate(filterVo);
		String filterFrm = listFilterTemplate();
		Map<String, Object> chartDataMap = workListCalc(workList);
//		String isBlank = (String) chartDataMap.get("isBlank");
		
//		resultMap.put("isBlank", isBlank);
		
		resultMap.put("filterFrm", filterFrm);                          
		 
		resultMap.put("chartData", chartDataMap);
		resultMap.put("prjList", prj_str);
		resultMap.put("makerList", makerList_str);
		resultMap.put("followerList", followerList_str);
		resultMap.put("result", result);
		return resultMap;
	}
	/**
	 * Method : ganttListTemplate
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-30 최초 생성
	 * @param workList
	 * @return
	 * Method 설명 : 필터링된 업무리스트를 간트차트 용 데이터로 변환해주는 메서드
	 */
	@Override
	public Map<String, Object> ganttListJSON(FilterVo filterVo) {
		resultMap = new HashMap<String, Object>();
		
		List<WorkVo> workList = filterList(filterVo);
		String prj_str = prjListTemplate(filterVo);
		String followerList_str = followerListTemplate(filterVo);
		String makerList_str = makerListTemplate(filterVo);
		String filterFrm = listFilterTemplate();
		Map<String, Object> result_Gantt= ganttMapTemplate(workList);
		
		if(workList.size()==0) 
			resultMap.put("isBlank", "true");
		else
			resultMap.put("isBlank", "false");
			
		resultMap.put("filterFrm", filterFrm);                          
		resultMap.put("prjList", prj_str);
		resultMap.put("makerList", makerList_str);
		resultMap.put("followerList", followerList_str);
		resultMap.put("result", result_Gantt);
		
		return resultMap;
	}
	
	/**
	 * Method : ganttMapTemplate
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-30 최초 생성
	 * @param workList
	 * @return
	 * Method 설명 : 필터링된 업무리스트를 간트차트 용 데이터로 변환해주는 메서드
	 */
	private Map<String, Object> ganttMapTemplate(List<WorkVo> workList) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Map<String, Object> ganttMap = new HashMap<>();
		
		Map<String, String> prjMap = new HashMap<String, String>();
		Map<String, String> wrkLstMap = new HashMap<String, String>();
		
		for(WorkVo work : workList) {
			prjMap.put("prj#"+work.getPrj_id(), work.getPrj_nm());
			wrkLstMap.put("list#"+work.getWrk_lst_id(), work.getWrk_lst_nm() + "/prj#" + work.getPrj_id());
		}
		
		logger.debug("prjMap : {}", prjMap);
		logger.debug("wrkLstMap : {}", wrkLstMap);
		List<GanttChartVo> ganttChartList = new ArrayList<>();
		for(String key : prjMap.keySet()) {
			GanttChartVo ganttVo = new GanttChartVo();
			ganttVo.setId(key);
			ganttVo.setText(prjMap.get(key));
			ganttVo.setUnscheduled(true);
			ganttVo.setOpen(true);
			ganttChartList.add(ganttVo);
		}
		
		for(String key : wrkLstMap.keySet()) {
			GanttChartVo ganttVo = new GanttChartVo();
			ganttVo.setId(key);
			String[] keyArr = wrkLstMap.get(key).split("/");
			ganttVo.setText(keyArr[0]);
			ganttVo.setParent(keyArr[1]);
			ganttVo.setUnscheduled(true);
			ganttVo.setOpen(true);
			ganttChartList.add(ganttVo);
		}
		
		for(WorkVo work : workList) {
			String id = "wrk#" + work.getWrk_id();
			String text = work.getWrk_nm();
			String parent = "list#" + work.getWrk_lst_id();
			Boolean unscheduled = work.getWrk_start_dt() == null || work.getWrk_end_dt() == null ? true : null;
			String start_date = work.getWrk_start_dt() == null ? null : sdf.format(work.getWrk_start_dt());			
			String end_date = work.getWrk_end_dt() == null ? null : sdf.format(work.getWrk_end_dt().getTime() + (1000*60*60*24));
			
			if("AUTH04".equals(work.getAuth())) {
				continue;
			}
			
			GanttChartVo ganttVo = new GanttChartVo();
			
				
			if(work.getWrk_start_dt()!=null && work.getWrk_end_dt()!=null) {
				if(work.getWrk_end_dt().before(new Date()) && "N".equals(work.getWrk_cmp_fl())) {
					ganttVo.setColor("#ef1010");
				}
				
				if(work.getWrk_cmp_dt()!=null) {
					if(work.getWrk_cmp_dt().after(work.getWrk_end_dt())) {
						ganttVo.setColor("#c7c20e");
					} else if ("Y".equals(work.getWrk_cmp_fl())){
						ganttVo.setColor("#00cc00");
					}
				}
			}
			
			if("AUTH02".equals(work.getAuth()) || "AUTH03".equals(work.getAuth()))
				ganttVo.setReadonly(true);
			
			
			ganttVo.setId(id);
			ganttVo.setText(text);
			ganttVo.setParent(parent);
			ganttVo.setUnscheduled(unscheduled);
			ganttVo.setStart_date(start_date);
			ganttVo.setEnd_date(end_date);
			ganttChartList.add(ganttVo);
		}
		
		ganttMap.put("data", ganttChartList);
		return ganttMap;
	}
	
	/**
	 * Method : resultListTemplate
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-26 최초 생성
	 * @param filterVo
	 * @return
	 * Method 설명 : 개인 업무리스트 화면에서 필터링을 거쳐 반환된 업무들을 Html 태그 형식으로 변환하는 메서드
	 */
	private String resultListTemplate(List<WorkVo>workList) {
		SimpleDateFormat sdf = new SimpleDateFormat("M월 d일");
		StringBuffer sb_result = new StringBuffer();
		Date nowDate = new Date();
		for(WorkVo work : workList) {
			if("AUTH04".equals(work.getAuth()))
				continue;
			
			sb_result.append("<div class='result' style='border:1px solid black; width:300px; padding:5px; margin: 3px;' data-wrk_id='"+ work.getWrk_id() +"'>");
			sb_result.append("<span>"+ work.getPrj_nm() + " > " + work.getWrk_lst_nm() +"</span>");
			sb_result.append("<br>");
			sb_result.append("<span class='wrk_nm'>"+ work.getWrk_nm() +"</span>");
			
			if(work.getWrk_end_dt()==null) 
				{
					if("Y".equals(work.getWrk_cmp_fl()))
						sb_result.append("<span class='cmp' style='color:#32a89b;'>&nbsp;&nbsp;"+ sdf.format(work.getWrk_cmp_dt()) +" 완료</span>");
					else if("N".equals(work.getWrk_cmp_fl()))
						sb_result.append("<span class='no_deadline' style='color:#616161;'>&nbsp;&nbsp; 마감일 없음</span>");
				} 
					else if(nowDate.after(work.getWrk_end_dt()) && "N".equals(work.getWrk_cmp_fl()))
				{
					sb_result.append("<span class='overdue' style='color:#a83232;'>&nbsp;&nbsp;"+ sdf.format(work.getWrk_end_dt()) +" 까지 마감</span>");
				} 
					else if(nowDate.before(work.getWrk_start_dt()) && "N".equals(work.getWrk_cmp_fl()))
				{
					sb_result.append("<span class='planned' style='color:#d8db1f;'>&nbsp;&nbsp;"+ sdf.format(work.getWrk_start_dt()) +" 부터 시작</span>");
				} 
					else if("Y".equals(work.getWrk_cmp_fl())) 
				{
					if(work.getWrk_cmp_dt().before(work.getWrk_end_dt()))
						sb_result.append("<span class='cmp' style='color:#32a89b;'>&nbsp;&nbsp;"+ sdf.format(work.getWrk_cmp_dt()) +" 완료</span>");
					else if(work.getWrk_cmp_dt().after(work.getWrk_end_dt()))
						sb_result.append("<span class='latecmp' style='color:#b71bbf;'>&nbsp;&nbsp;"+ sdf.format(work.getWrk_cmp_dt()) +" 완료</span>");
				}
			
				if(work.getWrk_start_dt() != null && work.getWrk_end_dt()!=null && nowDate.before(work.getWrk_end_dt()) && nowDate.after(work.getWrk_start_dt())) {
					sb_result.append("<span class='cmp' style='color:#32a89b;'>&nbsp;&nbsp;"+ sdf.format(work.getWrk_start_dt()) +"부터 " + sdf.format(work.getWrk_end_dt()) +" 까지</span>");
				}
			
			sb_result.append("</div>");
		}
		String result = sb_result.toString();
		return result;
	}

	/**
	 * Method : prjListTemplate
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-26 최초 생성
	 * @param filterVo
	 * @return
	 * Method 설명 : 사용자가 참여하고 있는 프로젝트 목록을 받아 Html 태그 형식으로 변환하는 메서드
	 */
	private String prjListTemplate(FilterVo filterVo) {
		StringBuffer sb_prjList = new StringBuffer();
		List<ProjectVo> prjIdList = filterDao.prjIdList(filterVo);
		for(ProjectVo prj : prjIdList) {
			sb_prjList.append("<input type='checkbox' class='filter' name='prj_id_list' value='" + prj.getPrj_id() + "'>" );
			sb_prjList.append(prj.getPrj_nm());
			sb_prjList.append("<br>");
		}
		String prj_str = sb_prjList.toString();
		return prj_str;
	}

	/**
	 * Method : followerListTemplate
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-26 최초 생성
	 * @param filterVo
	 * @return
	 * Method 설명 : 사용자가 참여하고 있는 프로젝트 내 업무의 팔로워 목록을 받아 Html 태그 형식으로 변환하는 메서드
	 */
	private String followerListTemplate(FilterVo filterVo) {
		StringBuffer sb_followerList = new StringBuffer();
		List<UserVo> followerIdList = filterDao.followerIdList(filterVo);
		for(UserVo user : followerIdList) {
			sb_followerList.append("<input type='checkbox' class='filter' name='wrk_follower' value='" + user.getUser_email() +"'>" + user.getUser_nm());
			sb_followerList.append("<br>");
		}
		String followerList_str = sb_followerList.toString();
		return followerList_str;
	}

	/**
	 * Method : makerListTemplate
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-26 최초 생성
	 * @param filterVo
	 * @return
	 * Method 설명 : 사용자가 참여하고 있는 프로젝트 내 업무 작성자 목록을 받아 Html 태그 형식으로 변환하는 메서드
	 */
	private String makerListTemplate(FilterVo filterVo) {
		List<UserVo> makerIdList = filterDao.makerIdList(filterVo);
		StringBuffer sb_makerList = new StringBuffer();
		for(UserVo user : makerIdList) {
			sb_makerList.append("<input type='checkbox' class='filter' name='wrk_maker' value='"+ user.getUser_email() +"'>" + user.getUser_nm());
			sb_makerList.append("<br>");
		}
		String makerList_str = sb_makerList.toString();
		return makerList_str;
	}
	
	/**
	 * Method : workDetail
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-26 최초 생성
	 * @param wrk_id
	 * @return
	 * Method 설명 : 사용자가 참여하고 있는 프로젝트 내 업무 목록을 받아 Html 태그 형식으로 변환하는 메서드
	 */
	@Override
	public String workDetail(int wrk_id) {
		WorkVo workVo = filterDao.workDetail(wrk_id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sb_detail = new StringBuffer();
		// 업무명, 작성자
		// 속성 탭 : 업무 위치, 날짜 설정, 미리 알림, 배정된 멤버, 팔로워, 포인트, 색상라벨
		sb_detail.append("<span>업무명</span>");
		sb_detail.append("&nbsp;" + "<input type='text' name='wrk_nm' value='"+ workVo.getWrk_nm() + "'>");
		sb_detail.append("<br>");
		sb_detail.append("<br>");
		sdf.applyPattern("yyyy년 M월 dd일 HH시 mm분");
		sb_detail.append("&nbsp;" + "<label>작성자</label> <label><b>"+ workVo.getUser_nm() +"</b></label>");
		sb_detail.append("<br>");
		sb_detail.append("&nbsp;" + "<label>"+ sdf.format(workVo.getWrk_dt()) +" 작성</label>");
		sdf.applyPattern("yyyy-MM-dd");
		sb_detail.append("<br>");
		sb_detail.append("<br>");
		sb_detail.append("<span>업무 작성일</span>");
		sb_detail.append("&nbsp;" + "<input type='date' name='wrk_dt' value='"+ sdf.format(workVo.getWrk_dt()) + "'>");
		sb_detail.append("<br>");
		sb_detail.append("<br>");
		sb_detail.append("<span>업무 작성일</span>");
		sb_detail.append("&nbsp;" + "<input type='date' name='wrk_dt' value='"+ sdf.format(workVo.getWrk_dt()) + "'>");
		sb_detail.append("<br>");
		sb_detail.append("<br>");
		sb_detail.append("<span>업무 작성일</span>");
		sb_detail.append("&nbsp;" + "<input type='date' name='wrk_dt' value='"+ sdf.format(workVo.getWrk_dt()) + "'>");
		sb_detail.append("<br>");
		sb_detail.append("<br>");
		sb_detail.append("<span>업무 작성일</span>");
		sb_detail.append("&nbsp;" + "<input type='date' name='wrk_dt' value='"+ sdf.format(workVo.getWrk_dt()) + "'>");
		sb_detail.append("<br>");
		sb_detail.append("<br>");
		sb_detail.append("<span>업무 작성일</span>");
		sb_detail.append("&nbsp;" + "<input type='date' name='wrk_dt' value='"+ sdf.format(workVo.getWrk_dt()) + "'>");
		sb_detail.append("<br>");
		sb_detail.append("<br>");
		
		return sb_detail.toString(); 
	}
	
	/**
	 * Method : listFilterTemplate
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-26 최초 생성
	 * @return
	 * Method 설명 : 전체 개요 페이지의 필터를 Html 형식으로 작성해주는 메서드
	 */
	private String listFilterTemplate() {
		StringBuffer sb_form = new StringBuffer();
		sb_form.append("<form id='filterFrm'>");
		sb_form.append("<label>업무 구분</label>");
		sb_form.append("<br>");
		
		sb_form.append("<select name='wrk_is_mine' class='filter'>");
		sb_form.append("<option value='all' selected>전체 업무</option>");
		sb_form.append("<option value='mine'>내 업무만</option>");
		sb_form.append("</select>");
		
		sb_form.append("<br><br>");
		
		sb_form.append("<label>작성일 기준</label>");
		sb_form.append("<br>");
		
		sb_form.append("<select name='wrk_dt' class='filter'>");
		sb_form.append("<option value='0' selected>전체</option>");
		sb_form.append("<option value='30'>30일 이내</option>");
		sb_form.append("<option value='60'>60일 이내</option>");
		sb_form.append("<option value='90'>90일 이내</option>");
		sb_form.append("</select>");
		
		sb_form.append("<br><br>");
		
		sb_form.append("<label>업무 주체</label>");
		sb_form.append("<br>");
		
		sb_form.append("<input type='checkbox' class='filter' name='wrk_i_assigned' value='y'> 내게 할당된 업무 <br>");
		sb_form.append("<input type='checkbox' class='filter' name='wrk_i_made' value='y'> 내가 작성한 업무 <br>");
		sb_form.append("<input type='checkbox' class='filter' name='wrk_i_following' value='y'> 내가 팔로우한 업무 <br>");
		sb_form.append("<br><br>");
		
		sb_form.append("<label>프로젝트 구분</label>");
		sb_form.append("<br>");
		sb_form.append("<div id='prjList'></div>");
		
		sb_form.append("<br><br>");
		
		sb_form.append("<label>마감일 기준</label>");
		sb_form.append("<br>");
		
		sb_form.append("<input type='checkbox' class='filter' name='overdue' value='y'> 마감일 지남 <br>");
		sb_form.append("<input type='checkbox' class='filter' name='till_this_week' value='y'> 이번 주까지 <br>");
		sb_form.append("<input type='checkbox' class='filter' name='till_this_month' value='y'> 이번 달까지 <br>");
		sb_form.append("<input type='checkbox' class='filter' name='no_deadline' value='y'> 마감일 없음 <br>");
		
		sb_form.append("<br><br>");
		
		sb_form.append("<label>업무 상태 구분</label>");
		sb_form.append("<br>");
		
		sb_form.append("<input type='checkbox' class='filter' name='is_cmp' value='y'> 완료된 업무 <br>");
		sb_form.append("<input type='checkbox' class='filter' name='is_del' value='y'> 삭제된 업무 <br>");
		
		sb_form.append("<br><br>");
		
		sb_form.append("<label>업무 작성자 구분</label>");
		sb_form.append("<br>");
		sb_form.append("<div id='makerList'></div>");
		
		sb_form.append("<br><br>");
		
		sb_form.append("<label>업무 팔로우 멤버</label>");
		sb_form.append("<br>");
		sb_form.append("<div id='followerList'></div>");
		
		
		sb_form.append("<br>");
		sb_form.append("<button type='button' onclick='reset()'> 필터 초기화 </button>");
		sb_form.append("<br>");
		sb_form.append("<input type='hidden' name='user_email' value='${USER_INFO.user_email}'>");
		sb_form.append("</form>");
		return sb_form.toString();
	}
	/**
	 * Method : listFilterTemplate
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-26 최초 생성
	 * @return
	 * Method 설명 : 전체 개요 페이지의 필터를 Html 형식으로 작성해주는 메서드
	 */
	private String listFilterTemplateCalendar() {
		StringBuffer sb_form = new StringBuffer();
		sb_form.append("<form id='filterFrm'>");
		sb_form.append("<label>업무 구분</label>");
		sb_form.append("<br>");
		
		sb_form.append("<select name='wrk_is_mine' class='filter'>");
		sb_form.append("<option value='all' selected>전체 업무</option>");
		sb_form.append("<option value='mine'>내 업무만</option>");
		sb_form.append("</select>");
		
		sb_form.append("<br><br>");
		
		sb_form.append("<label>작성일 기준</label>");
		sb_form.append("<br>");
		
		sb_form.append("<select name='wrk_dt' class='filter'>");
		sb_form.append("<option value='0' selected>전체</option>");
		sb_form.append("<option value='30'>30일 이내</option>");
		sb_form.append("<option value='60'>60일 이내</option>");
		sb_form.append("<option value='90'>90일 이내</option>");
		sb_form.append("</select>");
		
		sb_form.append("<br><br>");
		
		sb_form.append("<label>업무 주체</label>");
		sb_form.append("<br>");
		
		sb_form.append("<input type='checkbox' class='filter' name='wrk_i_assigned' value='y'> 내게 할당된 업무 <br>");
		sb_form.append("<input type='checkbox' class='filter' name='wrk_i_made' value='y'> 내가 작성한 업무 <br>");
		sb_form.append("<input type='checkbox' class='filter' name='wrk_i_following' value='y'> 내가 팔로우한 업무 <br>");
		sb_form.append("<br><br>");
		
		sb_form.append("<label>프로젝트 구분</label>");
		sb_form.append("<br>");
		sb_form.append("<div id='prjList'></div>");
		
		sb_form.append("<br><br>");
		
		sb_form.append("<label>마감일 기준</label>");
		sb_form.append("<br>");
		
		sb_form.append("<input type='checkbox' class='filter' name='overdue' value='y'> 마감일 지남 <br>");
		sb_form.append("<input type='checkbox' class='filter' name='till_this_week' value='y'> 이번 주까지 <br>");
		sb_form.append("<input type='checkbox' class='filter' name='till_this_month' value='y'> 이번 달까지 <br>");
		sb_form.append("<input type='checkbox' class='filter' name='no_deadline' value='y'> 마감일 없음 <br>");
		
		sb_form.append("<br><br>");
		
		sb_form.append("<label>업무 상태 구분</label>");
		sb_form.append("<br>");
		
		sb_form.append("<input type='checkbox' class='filter' name='is_cmp' value='y'> 완료된 업무 <br>");
		sb_form.append("<input type='checkbox' class='filter' name='is_del' value='y'> 삭제된 업무 <br>");
		
		sb_form.append("<br><br>");
		
		sb_form.append("<label>업무 작성자 구분</label>");
		sb_form.append("<br>");
		sb_form.append("<div id='makerList'></div>");
		
		sb_form.append("<br><br>");
		
		sb_form.append("<label>업무 팔로우 멤버</label>");
		sb_form.append("<br>");
		sb_form.append("<div id='followerList'></div>");
		
		
		sb_form.append("<br>");
		sb_form.append("<button type='button' onclick='reset()'> 필터 초기화 </button>");
		sb_form.append("<br>");
		sb_form.append("<input type='hidden' name='user_email' value='son@naver.com'>");
		sb_form.append("<input type='hidden' name='is_cal' value='true'>");
		sb_form.append("</form>");
		return sb_form.toString();
	}
	
	/**
	 * Method : workListCalc
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-26 최초 생성
	 * @param workList
	 * @return
	 * Method 설명 : 제공된 업무 리스트의 통계를 계산하는 메서드
	 */
	@Override
	public Map<String, Object> workListCalc(List<WorkVo> workList) {
		Map<String, Object> chartDataMap = new HashMap<String, Object>();
		
		List<WorkVo> doneList = new ArrayList<WorkVo>();
		List<WorkVo> undoneList = new ArrayList<WorkVo>();
		
		List<WorkVo> workList_A = new ArrayList<WorkVo>();
		List<WorkVo> workList_B = new ArrayList<WorkVo>();
		List<WorkVo> workList_C = new ArrayList<WorkVo>();
		List<WorkVo> workList_D = new ArrayList<WorkVo>();
		List<WorkVo> workList_E = new ArrayList<WorkVo>();
		
		List<WorkVo> doneList_A = new ArrayList<WorkVo>();
		List<WorkVo> doneList_B = new ArrayList<WorkVo>();
		List<WorkVo> doneList_C = new ArrayList<WorkVo>();
		List<WorkVo> doneList_D = new ArrayList<WorkVo>();
		List<WorkVo> doneList_E = new ArrayList<WorkVo>();
		
		List<WorkVo> undoneList_A = new ArrayList<WorkVo>();
		List<WorkVo> undoneList_B = new ArrayList<WorkVo>();
		List<WorkVo> undoneList_C = new ArrayList<WorkVo>();
		List<WorkVo> undoneList_D = new ArrayList<WorkVo>();
		List<WorkVo> undoneList_E = new ArrayList<WorkVo>();
		
		int entirePt = 0;
		int donePt = 0;

		for(WorkVo work : workList) {
			if("AUTH04".equals(work.getAuth()))
				continue;
				
			int priorPt = 0;
			switch(work.getWrk_grade()) {
				case "A":
					priorPt = 5;
					entirePt += priorPt; 
					workList_A.add(work);
					if("Y".equals(work.getWrk_cmp_fl())) 
					{	
						donePt += priorPt;
						doneList.add(work);
						doneList_A.add(work);
					} 
					else 
					{	
						undoneList.add(work);
						undoneList_A.add(work);
					}
					break;
				case "B":
					priorPt = 4;
					entirePt += priorPt; 
					workList_B.add(work);
					if("Y".equals(work.getWrk_cmp_fl())) 
					{
						donePt += priorPt;
						doneList.add(work);
						doneList_B.add(work);
					} 
					else 
					{
						undoneList.add(work);
						undoneList_B.add(work);
					}
					break;
				case "C":
					workList_C.add(work);
					priorPt = 3;
					entirePt += priorPt; 
					if("Y".equals(work.getWrk_cmp_fl())) 
					{
						doneList.add(work);
						doneList_C.add(work);
						donePt += priorPt;
					} 
					else 
					{
						undoneList.add(work);
						undoneList_C.add(work);
					}
					break;
				case "D":
					priorPt = 2;
					entirePt += priorPt; 
					workList_D.add(work);
					if("Y".equals(work.getWrk_cmp_fl())) 
					{
						doneList.add(work);
						doneList_D.add(work);
						donePt += priorPt;
					} 
					else 
					{
						undoneList.add(work);
						undoneList_D.add(work);
					}
					break;
				case "E":
					priorPt = 1;
					entirePt += priorPt; 
					workList_E.add(work);
					if("Y".equals(work.getWrk_cmp_fl())) 
					{
						donePt += priorPt;
						doneList.add(work);
						doneList_E.add(work);
					} 
					else 
					{
						undoneList.add(work);
						undoneList_E.add(work);
					}
					break;
			}
		}
		double percentage = (int)((double)donePt / entirePt * 10000) / 100.0;
		
		//------------------------------------업무상태 확인----------------------------------
		List<WorkVo> cmpList = new ArrayList<WorkVo>();
		List<WorkVo> overdueList = new ArrayList<WorkVo>();
		List<WorkVo> plannedList = new ArrayList<WorkVo>();
		List<WorkVo> no_deadlineList = new ArrayList<WorkVo>();
		int entPt = 0;
		int cmpPt = 0;
		int planPt = 0;
		int overduePt = 0;
		int nodeadlinePt = 0;
		Date nowDate = new Date();
		
		for(WorkVo work : workList) {
			if("AUTH04".equals(work.getAuth()))
				continue;
				
			if("Y".equals(work.getWrk_cmp_fl())) { // 완료된 업무
				switch(work.getWrk_grade()) {
					case "A":
						entPt += 5;
						cmpPt += 5;
					case "B":
						entPt += 4;
						cmpPt += 4;
					case "C":
						entPt += 3;
						cmpPt += 3;
					case "D":
						entPt += 2;
						cmpPt += 2;
					case "E":
						entPt += 1;
						cmpPt += 1;
				}
				cmpList.add(work);
			} else if(work.getWrk_end_dt()==null){ // 마감일 없는 업무
				switch(work.getWrk_grade()) {
				case "A":
					entPt += 5;
					nodeadlinePt += 5;
				case "B":
					entPt += 4;
					nodeadlinePt += 4;
				case "C":
					entPt += 3;
					nodeadlinePt += 3;
				case "D":
					entPt += 2;
					nodeadlinePt += 2;
				case "E":
					entPt += 1;
					nodeadlinePt += 1;
				}
				no_deadlineList.add(work);
			} else if(work.getWrk_start_dt()!=null && work.getWrk_end_dt()!=null && nowDate.before(work.getWrk_end_dt())) { // 계획된 업무 : 업무 시작일과 종료일이 존재하고, 마감일이 아직 지나지 않았을 때
				switch(work.getWrk_grade()) {
				case "A":
					entPt += 5;
					planPt += 5;
				case "B":
					entPt += 4;
					planPt += 4;
				case "C":
					entPt += 3;
					planPt += 3;
				case "D":
					entPt += 2;
					planPt += 2;
				case "E":
					entPt += 1;
					planPt += 1;
				}
				plannedList.add(work);
			} else if(nowDate.after(work.getWrk_end_dt())){ // 마감일 지난 업무 : 업무 마감일이 현재보다 앞일 때
				switch(work.getWrk_grade()) {
				case "A":
					entPt += 5;
					overduePt += 5;
				case "B":
					entPt += 4;
					overduePt += 4;
				case "C":
					entPt += 3;
					overduePt += 3;
				case "D":
					entPt += 2;
					overduePt += 2;
				case "E":
					entPt += 1;
					overduePt += 1;
				}
				overdueList.add(work);
			}
		}
		
		Map<String, Object> barChartMap = new HashMap<>();
		Map<String, Object> barChartMap_1 = new HashMap<>();
		List<String> categories = new ArrayList<>();
		barChartMap.put("priorChart", barChartMap_1);
		barChartMap_1.put("categories", categories);
		categories.add("완료 업무");
		categories.add("미완료 업무");
		
		List<Object> seriesList_1 = new ArrayList<>();
		barChartMap_1.put("series", seriesList_1);
		Map<String, Object> seriesMap_1 = new HashMap<>();
		List<Integer> seriesData_1 = new ArrayList<>();
		seriesMap_1.put("name", "중요도 A");
		seriesData_1.add(doneList_A.size());
		seriesData_1.add(undoneList_A.size());
		seriesMap_1.put("data", seriesData_1);
//		if(!(doneList_A.isEmpty() && undoneList_A.isEmpty())) 
		seriesList_1.add(seriesMap_1);
		
		Map<String, Object> seriesMap_2 = new HashMap<>();
		List<Integer> seriesData_2 = new ArrayList<>();
		seriesMap_2.put("name", "중요도 B");
		seriesData_2.add(doneList_B.size());
		seriesData_2.add(undoneList_B.size());
		seriesMap_2.put("data", seriesData_2);
//		if(!(doneList_B.isEmpty() && undoneList_B.isEmpty())) 
		seriesList_1.add(seriesMap_2);
		
		Map<String, Object> seriesMap_3 = new HashMap<>();
		List<Integer> seriesData_3 = new ArrayList<>();
		seriesMap_3.put("name", "중요도 C");
		seriesData_3.add(doneList_C.size());
		seriesData_3.add(undoneList_C.size());
		seriesMap_3.put("data", seriesData_3);
//		if(!(doneList_C.isEmpty() && undoneList_C.isEmpty())) 
		seriesList_1.add(seriesMap_3);
		
		Map<String, Object> seriesMap_4 = new HashMap<>();
		List<Integer> seriesData_4 = new ArrayList<>();
		seriesMap_4.put("name", "중요도 D");
		seriesData_4.add(doneList_D.size());
		seriesData_4.add(undoneList_D.size());
		seriesMap_4.put("data", seriesData_4);
		
//		if(!(doneList_D.isEmpty() && undoneList_D.isEmpty())) 
		seriesList_1.add(seriesMap_4);
		
		Map<String, Object> seriesMap_5 = new HashMap<>();
		List<Integer> seriesData_5 = new ArrayList<>();
		seriesMap_5.put("name", "중요도 E");
		seriesData_5.add(doneList_E.size());
		seriesData_5.add(undoneList_E.size());
		seriesMap_5.put("data", seriesData_5);
//		if(!(doneList_E.isEmpty() && undoneList_E.isEmpty())) 
		seriesList_1.add(seriesMap_5);
		
		Map<String, Object> barChartMap_2 = new HashMap<>();
		List<String> categories_2 = new ArrayList<>();
		categories_2.add("진척도");
		barChartMap_2.put("categories", categories_2);
		List<Object> seriesList_2 = new ArrayList<>();
		
		Map<String, Object> series2Data_1 = new HashMap<>();
		List<Double> series2List1 = new ArrayList<Double>();
		series2List1.add(percentage);
		series2Data_1.put("name", "완료");
		series2Data_1.put("data", series2List1);
		seriesList_2.add(series2Data_1);
		Map<String, Object> series2Data_2 = new HashMap<>();
		List<Double> series2List2 = new ArrayList<Double>();
		series2List2.add(workList.size() == 0 ? 0: (100-percentage));
		series2Data_2.put("name", "미완료");
		series2Data_2.put("data", series2List2);
		seriesList_2.add(series2Data_2);
		
		barChartMap_2.put("series", seriesList_2);
		barChartMap.put("percentChart", barChartMap_2);
		
		Map<String, Object> pieChartMap = new HashMap<>();
		List<Object> series3List = new ArrayList<>();
		pieChartMap.put("series", series3List);
		Map<String, Object> cmpMap = new HashMap<>();
		cmpMap.put("name", "완료된 업무");
		cmpMap.put("data", cmpList.size());
		series3List.add(cmpMap);
		Map<String, Object> plannedMap = new HashMap<>();
		plannedMap.put("name", "계획된 업무");
		plannedMap.put("data", plannedList.size());
		series3List.add(plannedMap);
		Map<String, Object> overdueMap = new HashMap<>();
		overdueMap.put("name", "마감일 지난 업무");
		overdueMap.put("data", overdueList.size());
		series3List.add(overdueMap);
		Map<String, Object> nodeadlineMap = new HashMap<>();
		nodeadlineMap.put("name", "마감일 없는 업무");
		nodeadlineMap.put("data", no_deadlineList.size());
		series3List.add(nodeadlineMap);
		
		if((cmpList.size() == 0) && (plannedList.size() == 0) && (overdueList.size() == 0) && (no_deadlineList.size() == 0)) {
			pieChartMap.put("isBlank", "true");
		} else {
			pieChartMap.put("isBlank", "false");
		}
			
		String isBlank = workList.size() == 0 ? "true" : "false";
		
		chartDataMap.put("isBlank", isBlank);
		chartDataMap.put("barChart", barChartMap);
		chartDataMap.put("pieChart", pieChartMap);
		return chartDataMap;
	}

	@Override
	public WorkVo getWork(int wrk_id) {
		return filterDao.workDetail(wrk_id);
	}

	@Override
	public Map<String, Object> projectOverviewJSON(FilterVo filterVo) {
		Map<String, Object> resultMap = new HashMap<>();
		List<WorkVo> workList = filterList(filterVo);
		logger.debug("workList : {}", workList);
		Map<String, Object> wrkLstMap = workListbChart(workList);
		filterVo.setWrk_i_assigned("y");
		List<WorkVo> assignedList = filterList(filterVo);
		Map<String, Object> assignedPieData = (Map<String, Object>) workListCalc(assignedList).get("pieChart");
		filterVo.setWrk_i_assigned(null);
		filterVo.setWrk_i_made("y");
		List<WorkVo> madeList = filterList(filterVo);
		Map<String, Object> madePieData = (Map<String, Object>) workListCalc(madeList).get("pieChart");
		filterVo.setWrk_i_made(null);
		filterVo.setWrk_i_following("y");
		List<WorkVo> followingList = filterList(filterVo);
		Map<String, Object> followingPieData = (Map<String, Object>) workListCalc(followingList).get("pieChart");
		resultMap.put("assign", assignedPieData);
		resultMap.put("made", madePieData);
		resultMap.put("following", followingPieData);
		resultMap.put("list", wrkLstMap);
		return resultMap;
	}

	private Map<String, Object> workListbChart(List<WorkVo> workList) {
		Date nowDate = new Date();
		Map<String, Object> resultMap = new HashMap<>();
		Map<Integer, Object> wrkListMap = new HashMap<>();
		List<String> categories = new ArrayList<>();
		
		Map<String, Object> cmpMap = new HashMap<>();
		Map<String, Object> planMap = new HashMap<>();
		Map<String, Object> overdueMap = new HashMap<>();
		Map<String, Object> nodeadlineMap = new HashMap<>();
		cmpMap.put("name", "완료된 업무");
		planMap.put("name", "계획된 업무");
		overdueMap.put("name", "마감일 지난 업무");
		nodeadlineMap.put("name", "마감일 없는 업무");
		
		
		for(WorkVo work : workList) {
			if("AUTH04".equals(work.getAuth()))
				continue;
			
			wrkListMap.put(work.getWrk_lst_id(),work.getWrk_lst_nm());
		}
		List<Integer> blankList = new ArrayList<>();
		for(int i = 0; i<wrkListMap.size(); i++) {
			blankList.add(0);
		}
		List<Integer> cmpList = new ArrayList<Integer>(blankList);
		List<Integer> planList = new ArrayList<>(blankList);
		List<Integer> overdueList = new ArrayList<>(blankList);
		List<Integer> nodeadlineList = new ArrayList<>(blankList);
		
		logger.debug("wrkListMap.size() : {}",wrkListMap.size()); 
		for(Integer wrk_lst_id : wrkListMap.keySet()) {
			int entPt = 0;
			int cmpPt = 0;
			int planPt = 0;
			int overduePt = 0;
			int nodeadlinePt = 0;
			categories.add((String)wrkListMap.get(wrk_lst_id));
			int index = categories.indexOf(wrkListMap.get(wrk_lst_id));
			for(WorkVo work : workList) {
				if("AUTH04".equals(work.getAuth()))
					continue;
				
				if(work.getWrk_lst_id() == wrk_lst_id) {
					int pt = 0;
					switch(work.getWrk_grade()) {
						case "A":
							pt = 5;
							entPt += 5;
							break;
						case "B":
							pt = 4;
							entPt += 4;
							break;
						case "C":
							pt = 3;
							entPt += 3;
							break;
						case "D":
							pt = 2;
							entPt += 2;
							break;
						case "E":
							pt = 1;
							entPt += 1;
							break;
					}
							
					if("Y".equals(work.getWrk_cmp_fl())) { // 완료된 업무
						cmpPt += pt;
					} else if(work.getWrk_end_dt()==null){ // 마감일 없는 업무
						nodeadlinePt += pt;
					} else if(work.getWrk_start_dt()!=null && work.getWrk_end_dt()!=null && nowDate.before(work.getWrk_end_dt())) { // 계획된 업무 : 업무 시작일과 종료일이 존재하고, 마감일이 아직 지나지 않았을 때
						planPt += pt;
					} else if(nowDate.after(work.getWrk_end_dt())){ // 마감일 지난 업무 : 업무 마감일이 현재보다 앞일 때
						overduePt += pt;
					}
					cmpList.set(index, cmpPt);
					nodeadlineList.set(index, nodeadlinePt);
					planList.set(index, planPt);
					overdueList.set(index, overduePt);
				}
			}
		}
		cmpMap.put("data", cmpList);
		planMap.put("data", planList);
		overdueMap.put("data", overdueList);
		nodeadlineMap.put("data", nodeadlineList);
		List<Object> seriesList = new ArrayList<>();
		seriesList.add(cmpMap);
		seriesList.add(planMap);
		seriesList.add(overdueMap);
		seriesList.add(nodeadlineMap);
		resultMap.put("series", seriesList);
		logger.debug("wrkListMap : {}", wrkListMap);
		resultMap.put("categories", categories);
		
		return resultMap;
	}
}
