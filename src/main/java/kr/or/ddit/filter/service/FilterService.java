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
		sdf.applyPattern("WW");
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
		String pieData = (String) chartDataMap.get("pieChartData");
		String priorData = (String) chartDataMap.get("priorData");
		String percentData = (String) chartDataMap.get("percentData"); 
		String isBlank = (String) chartDataMap.get("isBlank");
		
		resultMap.put("isBlank", isBlank);
		resultMap.put("pieChartData", pieData);
		resultMap.put("priorChartData", priorData);
		resultMap.put("percentChartData", percentData);
		
		resultMap.put("filterFrm", filterFrm);                          
		 
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
		SimpleDateFormat sdf = new SimpleDateFormat("M월 dd일");
		StringBuffer sb_result = new StringBuffer();
		Date nowDate = new Date();
		for(WorkVo work : workList) {
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
		
		Date nowDate = new Date();
		
		for(WorkVo work : workList) {
			if("Y".equals(work.getWrk_cmp_fl())) { // 완료된 업무
				cmpList.add(work);
			} else if(work.getWrk_end_dt()==null){ // 마감일 없는 업무
				no_deadlineList.add(work);
			} else if(nowDate.before(work.getWrk_start_dt())) { // 계획된 업무 : 업무 시작일이 현재보다 뒤일 때
				plannedList.add(work);
			} else if(nowDate.after(work.getWrk_end_dt())){ // 마감일 지난 업무 : 업무 마감일이 현재보다 앞일 때
				overdueList.add(work);
			}
		}
		
		String container = "{\"categories\": [\"완료 업무\", \"미완료 업무\"],"
				+ "\"series\": ["
				+ "{\"name\": \"중요도 A\", \"data\":["+ doneList_A.size()+ ", "+ undoneList_A.size() +"]},"
				+ "{\"name\": \"중요도 B\", \"data\":["+ doneList_B.size()+ ", "+ undoneList_B.size() +"]},"
				+ "{\"name\": \"중요도 C\", \"data\":["+ doneList_C.size()+ ", "+ undoneList_C.size() +"]},"
				+ "{\"name\": \"중요도 D\", \"data\":["+ doneList_D.size()+ ", "+ undoneList_D.size() +"]},"
				+ "{\"name\": \"중요도 E\", \"data\":["+ doneList_E.size()+ ", "+ undoneList_E.size() +"]}"
				+ "]}";
		String container2 = "{\"categories\": [\"  진척도  \"],"
				+ "\"series\": ["
				+ "{\"name\": \"완료\", \"data\": ["+ percentage +"]},"
				+ "{\"name\": \"미완료\", \"data\": ["+ (workList.size() == 0 ? 0 : (100-percentage)) +"]}"
				+ "]}";
		String container3 = "{\"series\": ["
				+ "{\"name\": \"완료된 업무\",\"data\": "+ cmpList.size() +"},"
				+ "{\"name\": \"마감일 지난 업무\",\"data\": "+ overdueList.size() +"},"
				+ "{\"name\": \"계획된 업무\",\"data\": "+ plannedList.size() +" },"
				+ "{\"name\": \"마감일 없는 업무\",\"data\": "+ no_deadlineList.size() +" }"
				+ "]}";
		String isBlank = workList.size() == 0 ? "true" : "false";
		
		chartDataMap.put("priorData", container);
		chartDataMap.put("percentData", container2);
		chartDataMap.put("pieChartData", container3);
		chartDataMap.put("isBlank", isBlank);
		return chartDataMap;
	}

	@Override
	public WorkVo getWork(int wrk_id) {
		return filterDao.workDetail(wrk_id);
	}
}
