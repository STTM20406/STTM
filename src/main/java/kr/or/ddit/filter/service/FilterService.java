package kr.or.ddit.filter.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.calendar.model.CalendarVo;
import kr.or.ddit.filter.dao.IFilterDao;
import kr.or.ddit.filter.model.FilterVo;
import kr.or.ddit.ganttChart.model.GanttChartVo;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.project.service.IProjectService;
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
	
	@Resource(name="projectService")
	private IProjectService projectService;
	
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
	public Map<String, Object> calendarTemplateJSON(FilterVo filterVo) {
		Map<String, Object> resultMap = new HashMap<>();
		String filterFrm = listFilterTemplateCalendar(filterVo);
		String makerList = makerListTemplate(filterVo);
		String prjList = prjListTemplate(filterVo);
		
		List<WorkVo> workList = filterList(filterVo);
		List<CalendarVo> calList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		for(WorkVo work : workList) {
			CalendarVo calVo = new CalendarVo();
			calVo.set_id(work.getWrk_id());
			calVo.setTitle(work.getWrk_nm());
			calVo.setDescription(work.getPrj_nm() + " > " + work.getWrk_lst_nm());
			calVo.setStart(sdf.format(work.getWrk_start_dt()));
			calVo.setEnd(sdf.format(work.getWrk_end_dt()));
			calVo.setUsername(work.getUser_nm());
			calVo.setType(work.getWrk_lst_nm());
			calVo.setTextColor("#ffffff");
			calVo.setBackgroundColor(work.getWrk_color_cd());
			calVo.setAllDay(false);
			
			calList.add(calVo);
		}
		resultMap.put("filterFrm", filterFrm);
		resultMap.put("makerList", makerList);
		resultMap.put("prjList", prjList);
		resultMap.put("data", calList);
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> workListJSON(FilterVo filterVo) {
		resultMap = new HashMap<String, Object>();
		List<WorkVo> workList = filterList(filterVo);
		String result = resultListTemplate(workList);
		String prj_str = prjListTemplate(filterVo); // 프로젝트 리스트 부분
		String followerList_str = followerListTemplate(filterVo); // 팔로워 리스트 부분
		String makerList_str = makerListTemplate(filterVo);	// 작성자 리스트 부분
		String filterFrm = listFilterTemplate(filterVo);
		Map<String, Object> chartDataMap = workListCalc(workList);
		String isBlank = (String) chartDataMap.get("isBlank");
		
		resultMap.put("isBlank", isBlank);
		
		resultMap.put("filterFrm", filterFrm);                          
		 
		resultMap.put("chartData", chartDataMap);
		resultMap.put("prjList", prj_str);
		resultMap.put("makerList", makerList_str);
		resultMap.put("followerList", followerList_str);
		resultMap.put("result", result);
		return resultMap;
	}
	/**
	 * Method : ganttListJSON
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
		String filterFrm = listFilterTemplate(filterVo);
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
			String tag = "";
			
			if(work.getWrk_cmp_fl().equals("Y"))
				tag += " (완료)";
			
			String text = work.getWrk_nm() + tag;
			String parent = "list#" + work.getWrk_lst_id();
			Boolean unscheduled = work.getWrk_start_dt() == null || work.getWrk_end_dt() == null ? true : null;
			String start_date = work.getWrk_start_dt() == null ? null : sdf.format(work.getWrk_start_dt());			
			String end_date = work.getWrk_end_dt() == null ? null : sdf.format(work.getWrk_end_dt().getTime() + (1000*60*60*24));
			String color_cd = work.getWrk_color_cd();
			if("AUTH04".equals(work.getAuth())) {
				continue;
			}
			
			GanttChartVo ganttVo = new GanttChartVo();
			
				
//			if(work.getWrk_start_dt()!=null && work.getWrk_end_dt()!=null) {
//				if(work.getWrk_end_dt().before(new Date()) && "N".equals(work.getWrk_cmp_fl())) {
//					ganttVo.setColor("#ef1010");
//				}
//				
//				if(work.getWrk_cmp_dt()!=null) {
//					if(work.getWrk_cmp_dt().after(work.getWrk_end_dt())) {
//						ganttVo.setColor("#c7c20e");
//					} else if ("Y".equals(work.getWrk_cmp_fl())){
//						ganttVo.setColor("#00cc00");
//					}
//				}
//			}
			
			if("AUTH02".equals(work.getAuth()) || "AUTH03".equals(work.getAuth()))
				ganttVo.setReadonly(true);
			
			ganttVo.setColor(color_cd);
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
		SimpleDateFormat sdf = new SimpleDateFormat("MM. dd");
		SimpleDateFormat sdf_y = new SimpleDateFormat("yyyy. MM. dd");
		StringBuffer sb_result = new StringBuffer();
		Date nowDate = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		
		
		for(WorkVo work : workList) {
			if("AUTH04".equals(work.getAuth()))
				continue;
			
			String date_str = null;
			int startInt = 0;
			int endInt = 0;
			
			if(work.getWrk_start_dt() != null) {
				cal.setTime(work.getWrk_start_dt());
				startInt = cal.get(Calendar.YEAR);
			}
			
			if(work.getWrk_end_dt() != null) {
				cal.setTime(work.getWrk_end_dt());
				endInt = cal.get(Calendar.YEAR);
			}
			
			if(startInt == endInt && startInt != 0 && endInt != 0) {
				date_str = sdf_y.format(work.getWrk_start_dt()) + " ~ " + sdf.format(work.getWrk_end_dt()); 
			} else if (startInt != endInt && startInt != 0 && endInt != 0){
				date_str = sdf_y.format(work.getWrk_start_dt()) + " ~ " + sdf_y.format(work.getWrk_end_dt()); 
			} else if (endInt == 0 && startInt != 0) {
				date_str = sdf_y.format(work.getWrk_start_dt()) + " ~ ";
			} else if (endInt == 0 && startInt == 0) {
				date_str = "";
			}
			
			sb_result.append("<div class='result' style='border:1px solid black; width:330px; padding:5px 10px; margin: 3px;' data-wrk_id='"+ work.getWrk_id() +"'>");
			sb_result.append("<span>"+ work.getPrj_nm() + " > " + work.getWrk_lst_nm() +"</span>");
			sb_result.append("<br>");
			sb_result.append("<span class='wrk_nm'><b>"+ work.getWrk_nm() +"</b></span>");
			
			
			
			if(work.getWrk_end_dt()==null) 
				{
					if("Y".equals(work.getWrk_cmp_fl()))
						sb_result.append("<span>&nbsp;&nbsp; "+ date_str +" </span><span class='cmp' style='color:#32a89b;'>&nbsp;&nbsp;"+ sdf_y.format(work.getWrk_cmp_dt()) +" <b>완료</b></span>" );
					else if("N".equals(work.getWrk_cmp_fl()))
						sb_result.append("<span class='no_deadline' style='color:#616161;'>&nbsp;&nbsp; " + date_str + " <b>마감일 없음</b></span>");
				} 
					else if(nowDate.after(work.getWrk_end_dt()) && "N".equals(work.getWrk_cmp_fl()))
				{
					sb_result.append("<span>&nbsp;&nbsp;"+ date_str +"</span><span class='overdue' style='color:#a83232;'>&nbsp;&nbsp;<b> "+ "마감일 지남" +"</b></span>");
				} 
					else if(nowDate.before(work.getWrk_start_dt()) && "N".equals(work.getWrk_cmp_fl()))
				{
					sb_result.append("<span>&nbsp;&nbsp;"+ date_str +"</span><span class='planned' style='color:#7b8500;'>&nbsp;&nbsp; <b>계획됨</b></span>");
				} 
					else if("Y".equals(work.getWrk_cmp_fl())) 
				{
					if(work.getWrk_cmp_dt().after(work.getWrk_end_dt()))
						sb_result.append("<span>&nbsp;&nbsp; "+ date_str +" </span><span class='latecmp' style='color:#b71bbf;'>&nbsp;&nbsp;"+ sdf.format(work.getWrk_cmp_dt()) +" <b>완료</b></span>");
				}
			
				if(work.getWrk_start_dt() != null && work.getWrk_end_dt()!=null && nowDate.before(work.getWrk_end_dt()) && nowDate.after(work.getWrk_start_dt())) {
					sb_result.append("<span class='cmp' style='color:#32a89b;'>&nbsp;&nbsp;"+ date_str +"</span>");
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
		sb_prjList.append("<ul>");
		sb_prjList.append("<li>");
		sb_prjList.append("<label>프로젝트</label>");
		for(ProjectVo prj : prjIdList) {
			sb_prjList.append("<p class='pp'><input type='checkbox' class='filter' name='prj_id_list' value='" + prj.getPrj_id() + "'>" );
			sb_prjList.append(prj.getPrj_nm());
			sb_prjList.append("</p>");
		}
		sb_prjList.append("</li>");
		sb_prjList.append("</ul>");
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
		sb_followerList.append("<ul>");
		sb_followerList.append("<li>");
		sb_followerList.append("<label>팔로워</label>");
		for(UserVo user : followerIdList) {
			sb_followerList.append("<p class='pp'><input type='checkbox' class='filter' name='wrk_follower' value='" + user.getUser_email() +"'>" + user.getUser_nm());
			sb_followerList.append("</p>");
		}
		sb_followerList.append("</li>");
		sb_followerList.append("</ul>");
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
		sb_makerList.append("<ul>");
		sb_makerList.append("<li>");
		sb_makerList.append("<label>작성자</label>");
		for(UserVo user : makerIdList) {
			sb_makerList.append("<p class='pp'><input type='checkbox' class='filter' name='wrk_maker' value='"+ user.getUser_email() +"'>" + user.getUser_nm());
			sb_makerList.append("</p>");
		}
		sb_makerList.append("</li>");
		sb_makerList.append("</ul>");
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
		
		return sb_detail.toString(); 
	}
	
	/**
	 * Method : listFilterTemplate
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-26 최초 생성
	 * @return
	 * Method 설명 : 전체 개요 페이지의 필터를 Html 형식으로 작성해주는 메서드
	 */
	private String listFilterTemplate(FilterVo filterVo) {
		StringBuffer sb_form = new StringBuffer();
		sb_form.append("<form id='filterFrm'>");
		sb_form.append("<label>업무 구분</label>");
		sb_form.append("<br>");
		
		sb_form.append("<select name='wrk_is_mine' class='filter'>");
		sb_form.append("<option value='all' selected>전체 업무</option>");
		sb_form.append("<option value='mine'>내가 맡은 업무만</option>");
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
		
		sb_form.append("<div id='prjList'></div>");
		
		sb_form.append("<br><br>");
		
		sb_form.append("<label>마감일 기준</label>");
		sb_form.append("<br>");
		
		sb_form.append("<input type='checkbox' class='filter' name='overdue' value='y'> 마감일 지남 <br>");
		sb_form.append("<input type='checkbox' class='filter' name='till_this_week' value='y'> 이번 주까지 <br>");
		sb_form.append("<input type='checkbox' class='filter' name='till_this_month' value='y'> 이번 달까지 <br>");
		sb_form.append("<input type='checkbox' class='filter' name='no_deadline' value='y'> 마감일 없음 <br>");
		
		sb_form.append("<br><br>");
		
		sb_form.append("<label>업무 상태</label>");
		sb_form.append("<br>");
		
		sb_form.append("<input type='checkbox' class='filter' name='is_cmp' value='y'> 완료된 업무 <br>");
		sb_form.append("<br><br>");
		
		sb_form.append("<div id='makerList'></div>");
		
		sb_form.append("<br><br>");
		
		sb_form.append("<div id='followerList'></div>");
		
		
		sb_form.append("<br>");
		sb_form.append("<button type='button' class='btn_style_02' onclick='reset()'> 필터 초기화 </button>");
		sb_form.append("<br>");
		sb_form.append("<input type='hidden' name='user_email' value='"+ filterVo.getUser_email() +"'>");
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
	public String listFilterTemplateCalendar(FilterVo filterVo) {
		StringBuffer sb_form = new StringBuffer();
		sb_form.append("<form id='filterFrm'>");
		sb_form.append("<label>업무 구분</label>");
		sb_form.append("<br>");
		
		sb_form.append("<select name='wrk_is_mine' class='filter'>");
		sb_form.append("<option value='all' selected>전체 업무</option>");
		sb_form.append("<option value='mine'>내가 맡은 업무만</option>");
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
		sb_form.append("<br><br>");
		
		sb_form.append("<br>");
		sb_form.append("<div id='makerList'></div>");
		sb_form.append("<br>");
		sb_form.append("<button type='button' onclick='reset()'> 필터 초기화 </button>");
		sb_form.append("<br>");
		sb_form.append("<input type='hidden' name='user_email' value='"+ filterVo.getUser_email() + "'>");
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
		
		Map<String, Object> barChart1Data = new HashMap<>();
		Map<String, Object> barChart1Done = new HashMap<>();
		Map<String, Object> barChart1Undone = new HashMap<>();
		
		Map<String, Object> barChart2Data = new HashMap<>();
		
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
				
			int priorPt = getPoint(work);
			String status = getStatus(work);
			switch(priorPt) {
				case 5:
					entirePt += priorPt; 
					workList_A.add(work);
					status = getStatus(work);
					if("cmp".equals(status)) {	
						donePt += priorPt;
						doneList.add(work);
						doneList_A.add(work);
					} else {	
						undoneList.add(work);
						undoneList_A.add(work);
					}
					break;
				case 4:
					entirePt += priorPt; 
					workList_B.add(work);
					status = getStatus(work);
					if("cmp".equals(status)) {
						donePt += priorPt;
						doneList.add(work);
						doneList_B.add(work);
					} else {
						undoneList.add(work);
						undoneList_B.add(work);
					}
					break;
				case 3:
					workList_C.add(work);
					priorPt = 3;
					entirePt += priorPt;
					status = getStatus(work);
					if("cmp".equals(status)) {
						doneList.add(work);
						doneList_C.add(work);
						donePt += priorPt;
					} else {
						undoneList.add(work);
						undoneList_C.add(work);
					}
					break;
				case 2:
					priorPt = 2;
					entirePt += priorPt; 
					workList_D.add(work);
					status = getStatus(work);
					if("cmp".equals(status)) {
						doneList.add(work);
						doneList_D.add(work);
						donePt += priorPt;
					} else {
						undoneList.add(work);
						undoneList_D.add(work);
					}
					break;
				case 1:
					priorPt = 1;
					entirePt += priorPt; 
					workList_E.add(work);
					status = getStatus(work);
					if("cmp".equals(status)) {
						donePt += priorPt;
						doneList.add(work);
						doneList_E.add(work);
					} else {
						undoneList.add(work);
						undoneList_E.add(work);
					}
					break;
			}
		}
		barChart1Data.put("완료 업무", barChart1Done);
		barChart1Data.put("미완료 업무", barChart1Undone);
		
		barChart1Done.put("중요도 A", doneList_A);
		barChart1Done.put("중요도 B", doneList_B);
		barChart1Done.put("중요도 C", doneList_C);
		barChart1Done.put("중요도 D", doneList_D);
		barChart1Done.put("중요도 E", doneList_E);
		
		barChart1Undone.put("중요도 A", undoneList_A);
		barChart1Undone.put("중요도 B", undoneList_B);
		barChart1Undone.put("중요도 C", undoneList_C);
		barChart1Undone.put("중요도 D", undoneList_D);
		barChart1Undone.put("중요도 E", undoneList_E);
		
		barChart2Data.put("완료", doneList);
		barChart2Data.put("미완료", undoneList);
		
		double percentage = (int)((double)donePt / entirePt * 10000) / 100.0;
		
		//------------------------------------업무상태 확인----------------------------------
		List<WorkVo> cmpList = new ArrayList<WorkVo>();
		List<WorkVo> overdueList = new ArrayList<WorkVo>();
		List<WorkVo> plannedList = new ArrayList<WorkVo>();
		List<WorkVo> no_deadlineList = new ArrayList<WorkVo>();
		
		double entPt = 0;
		int cmpPt = 0;
		int planPt = 0;
		int overduePt = 0;
		int nodeadlinePt = 0;
		
		for(WorkVo work : workList) {
			if("AUTH04".equals(work.getAuth()))
				continue;
			
			int point = getPoint(work);
			String status = getStatus(work);
			if("cmp".equals(status)) { // 완료된 업무
				entPt += point;
				cmpPt += point;
				cmpList.add(work);
			} else if("nodeadline".equals(status)){ // 마감일 없는 업무
				entPt += point;
				nodeadlinePt += point;
				no_deadlineList.add(work);
			} else if("plan".equals(status)) { // 계획된 업무 : 업무 시작일과 종료일이 존재하고, 마감일이 아직 지나지 않았을 때
				entPt += point;
				planPt += point;
				plannedList.add(work);
			} else if("overdue".equals(status)){ // 마감일 지난 업무 : 업무 마감일이 현재보다 앞일 때
				entPt += point;
				overduePt += point;
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
		if(!(doneList_A.isEmpty() && undoneList_A.isEmpty())) 
		seriesList_1.add(seriesMap_1);
		
		Map<String, Object> seriesMap_2 = new HashMap<>();
		List<Integer> seriesData_2 = new ArrayList<>();
		seriesMap_2.put("name", "중요도 B");
		seriesData_2.add(doneList_B.size());
		seriesData_2.add(undoneList_B.size());
		seriesMap_2.put("data", seriesData_2);
		if(!(doneList_B.isEmpty() && undoneList_B.isEmpty())) 
		seriesList_1.add(seriesMap_2);
		
		Map<String, Object> seriesMap_3 = new HashMap<>();
		List<Integer> seriesData_3 = new ArrayList<>();
		seriesMap_3.put("name", "중요도 C");
		seriesData_3.add(doneList_C.size());
		seriesData_3.add(undoneList_C.size());
		seriesMap_3.put("data", seriesData_3);
		if(!(doneList_C.isEmpty() && undoneList_C.isEmpty())) 
		seriesList_1.add(seriesMap_3);
		
		Map<String, Object> seriesMap_4 = new HashMap<>();
		List<Integer> seriesData_4 = new ArrayList<>();
		seriesMap_4.put("name", "중요도 D");
		seriesData_4.add(doneList_D.size());
		seriesData_4.add(undoneList_D.size());
		seriesMap_4.put("data", seriesData_4);
		if(!(doneList_D.isEmpty() && undoneList_D.isEmpty())) 
		seriesList_1.add(seriesMap_4);
		
		Map<String, Object> seriesMap_5 = new HashMap<>();
		List<Integer> seriesData_5 = new ArrayList<>();
		seriesMap_5.put("name", "중요도 E");
		seriesData_5.add(doneList_E.size());
		seriesData_5.add(undoneList_E.size());
		seriesMap_5.put("data", seriesData_5);
		if(!(doneList_E.isEmpty() && undoneList_E.isEmpty())) 
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
		Map<String, Object> pieChartData = new HashMap<>();
		pieChartData.put("cmp", cmpList);
		pieChartData.put("plan", plannedList);
		pieChartData.put("overdue", overdueList);
		pieChartData.put("nodeadline", no_deadlineList);
		pieChartMap.put("pieData", pieChartData);
		
		double cmpData = 0; 
		double planData = 0;
		double overdueData = 0; 
		double nodeadlineData = 0;
		
		if(cmpPt!=0) 
			cmpData = Math.round((cmpPt/entPt)*100);
		
		if(planPt!=0) 
			planData = Math.round((planPt/entPt)*100);
		
		if(overduePt!=0) 
			overdueData = Math.round((overduePt/entPt)*100);
		
		if(nodeadlinePt!=0) 
			nodeadlineData = Math.round((nodeadlinePt/entPt)*100);
		
		List<Object> series3List = new ArrayList<>();
		pieChartMap.put("series", series3List);
		
		Map<String, Object> cmpMap = new HashMap<>();
		cmpMap.put("name", "완료된 업무");
		cmpMap.put("data", cmpData);
		series3List.add(cmpMap);
		
		Map<String, Object> plannedMap = new HashMap<>();
		plannedMap.put("name", "계획된 업무");
		plannedMap.put("data", planData);
		series3List.add(plannedMap);
		
		Map<String, Object> overdueMap = new HashMap<>();
		overdueMap.put("name", "마감일 지난 업무");
		overdueMap.put("data", overdueData);
		series3List.add(overdueMap);
		
		Map<String, Object> nodeadlineMap = new HashMap<>();
		nodeadlineMap.put("name", "마감일 없는 업무");
		nodeadlineMap.put("data", nodeadlineData);
		series3List.add(nodeadlineMap);
		
		if((cmpList.size() == 0) && (plannedList.size() == 0) && (overdueList.size() == 0) && (no_deadlineList.size() == 0)) {
			pieChartMap.put("isBlank", "true");
		} else {
			pieChartMap.put("isBlank", "false");
		}
		
		barChartMap_1.put("priorData", barChart1Data);
		barChartMap_2.put("percentData", barChart2Data);
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
		int doneCnt = 0;
		int undoneCnt = 0;
		int entPnt = 0;
		int donePnt = 0;
		int undonePnt = 0;
		for(WorkVo work : workList) {
			int point = getPoint(work);
			String status = getStatus(work);
			entPnt += point;
			switch(status) {
			case "cmp":
				doneCnt++;
				donePnt += point;
				break;
			default :
				undoneCnt++;
				undonePnt += point;
				break;
			}
		}
		double donePercent = 0;
		
			if(donePnt!=0)
				donePercent = Math.round(((double)donePnt/entPnt) * 100);
		double undonePercent = 0;
		
			if(undonePnt!=0)
				undonePercent = Math.round(((double)undonePnt/entPnt) * 100);
			
		Map<String, Object> cntMap = new HashMap<String, Object>();
		cntMap.put("doneCnt", doneCnt);
		cntMap.put("undoneCnt", undoneCnt);
		cntMap.put("donePercent", donePercent);
		cntMap.put("undonePercent", undonePercent);
		
		ProjectVo prjVo = projectService.getProject(filterVo.getPrj_id());
		resultMap.put("prjVo", prjVo);
		Map<String, Object> progressMap = chartProgress(workList);
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
		
		int authCnt = filterDao.checkAuth(filterVo);
		
		
		resultMap.put("assign", assignedPieData);
		resultMap.put("made", madePieData);
		resultMap.put("following", followingPieData);
		resultMap.put("list", wrkLstMap);
		resultMap.put("progress", progressMap);
		resultMap.put("cnt", cntMap);
		resultMap.put("auth", authCnt == 0 ? "NO" : "OK");
		return resultMap;
	}
	
	@Override
	public List<String> prjList(String user_email) {
		List<ProjectVo> prjList = filterDao.prjList(user_email);
		List<String> htmlList = new ArrayList<>();
		for(ProjectVo prjVo : prjList) {
			htmlList.add("<option class='prjList' value='"+ prjVo.getPrj_id() +"'>" + prjVo.getPrj_nm() +"</option>");
		}
		return htmlList;
	} 
	
	/**
	 * Method : chartProgress
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-06 최초 생성
	 * @param workList
	 * @return
	 * Method 설명 : 특정 프로젝트 개요  - 프로젝트 개요 차트 정보 작성 메서드 
	 */
	private Map<String, Object> chartProgress(List<WorkVo> workList) {
		Map<String, Object> progressMap = new HashMap<>();
		List<WorkVo> cmpPrgList = new ArrayList<>();
		List<WorkVo> overduePrgList = new ArrayList<>();
		List<WorkVo> plannedPrgList = new ArrayList<>();
		List<WorkVo> nodeadlinePrgList = new ArrayList<>();
		double entPt = 0;
		int cmpPt = 0;
		int planPt = 0;
		int overduePt = 0;
		int nodeadlinePt = 0;
		
		for(WorkVo work : workList) {
			if("AUTH04".equals(work.getAuth()))
				continue;
			
			int point = getPoint(work);
				entPt += point;
			String status = getStatus(work);
			if("cmp".equals(status)) { // 완료된 업무
				cmpPt += point;
				cmpPrgList.add(work);
			} else if("nodeadline".equals(status)){ // 마감일 없는 업무
				nodeadlinePt += point;
				nodeadlinePrgList.add(work);
			} else if("plan".equals(status)) { // 계획된 업무 : 업무 시작일과 종료일이 존재하고, 마감일이 아직 지나지 않았을 때
				planPt += point;
				plannedPrgList.add(work);
			} else if("overdue".equals(status)){ // 마감일 지난 업무 : 업무 마감일이 현재보다 앞일 때
				overduePt += point;
				overduePrgList.add(work);
			}
		}
		List<String> categories = new ArrayList<>();
		categories.add("");
		List<Object> seriesData = new ArrayList<>();
		Map<String, Object> cmpMap = new HashMap<>();
		Map<String, Object> plannedMap = new HashMap<>();
		Map<String, Object> overdueMap = new HashMap<>();
		Map<String, Object> nodeadlineMap = new HashMap<>();
		
		double cmpData = Math.round((cmpPt/entPt)*100);
		cmpMap.put("data", cmpData);
		cmpMap.put("name", "완료된 업무");
		
		double planData = Math.round((planPt/entPt)*100);
		plannedMap.put("data", planData);
		plannedMap.put("name", "계획된 업무");
		logger.debug("planPt: {}, entPt: {}",planPt, entPt);
		double overdueData = Math.round((overduePt/entPt)*100);
		overdueMap.put("data", overdueData);
		overdueMap.put("name", "마감일 지난 업무");
		
		double nodeadlineData = Math.round(nodeadlinePt/entPt)*100;
		nodeadlineMap.put("data", nodeadlineData);
		nodeadlineMap.put("name", "마감일 없는 업무");
		
		seriesData.add(cmpMap);
		seriesData.add(plannedMap);
		seriesData.add(overdueMap);
		seriesData.add(nodeadlineMap);
		
		progressMap.put("cmpList", cmpPrgList);
		progressMap.put("planList", plannedPrgList);
		progressMap.put("overdueList", overduePrgList);
		progressMap.put("nodeadlineList", nodeadlinePrgList);
		progressMap.put("series", seriesData);
		progressMap.put("categories", categories);
		
		return progressMap;
	}

	private Map<String, Object> workListbChart(List<WorkVo> workList) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<Integer, Object> wrkListMap = new HashMap<>();
		List<String> categories = new ArrayList<>();
		Map<String, Object> workVoMap = new HashMap<>();
		
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
		
		List<Double> blankList = new ArrayList<>();
		for(int i = 0; i<wrkListMap.size(); i++) {
			blankList.add(0.0);
		}
		List<Double> cmpList = new ArrayList<>(blankList);
		List<Double> planList = new ArrayList<>(blankList);
		List<Double> overdueList = new ArrayList<>(blankList);
		List<Double> nodeadlineList = new ArrayList<>(blankList);
		
		logger.debug("wrkListMap.size() : {}",wrkListMap.size()); 
		for(Integer wrk_lst_id : wrkListMap.keySet()) {
			double entPt = 0;
			int cmpPt = 0;
			int planPt = 0;
			int overduePt = 0;
			int nodeadlinePt = 0;
			categories.add((String)wrkListMap.get(wrk_lst_id));
			int index = categories.indexOf(wrkListMap.get(wrk_lst_id));
			
			Map<String, Object> workMap = new HashMap<>();
			List<WorkVo> workCmpList = new ArrayList<>();
			List<WorkVo> workPlanList = new ArrayList<>();
			List<WorkVo> workOverdueList = new ArrayList<>();
			List<WorkVo> workNodeadlineList = new ArrayList<>();
			
			workVoMap.put((String)wrkListMap.get(wrk_lst_id), workMap);
			
			for(WorkVo work : workList) {
				if("AUTH04".equals(work.getAuth()))
					continue;
				
				if(work.getWrk_lst_id() == wrk_lst_id) {
					int point = getPoint(work);
						entPt += point;
					String status = getStatus(work);
					if("cmp".equals(status)) { // 완료된 업무
						cmpPt += point;
						workCmpList.add(work);
					} else if("nodeadline".equals(status)){ // 마감일 없는 업무
						nodeadlinePt += point;
						workNodeadlineList.add(work);
					} else if("plan".equals(status)) { // 계획된 업무 : 업무 시작일과 종료일이 존재하고, 마감일이 아직 지나지 않았을 때
						planPt += point;
						workPlanList.add(work);
					} else if("overdue".equals(status)){ // 마감일 지난 업무 : 업무 마감일이 현재보다 앞일 때
						overduePt += point;
						workOverdueList.add(work);
					}
					
					double cmpData = 0; 
					double planData = 0;
					double overdueData = 0; 
					double nodeadlineData = 0;
					
					if(cmpPt!=0) 
						cmpData = Math.round((cmpPt/entPt)*100);
					
					if(planPt!=0) 
						planData = Math.round((planPt/entPt)*100);
					
					if(overduePt!=0) 
						overdueData = Math.round((overduePt/entPt)*100);
					
					if(nodeadlinePt!=0) 
						nodeadlineData = Math.round((nodeadlinePt/entPt)*100);
					
					cmpList.set(index, cmpData);
					nodeadlineList.set(index, nodeadlineData);
					planList.set(index, planData);
					overdueList.set(index, overdueData);
				}
			}
			workMap.put("cmpList", workCmpList);
			workMap.put("planList", workPlanList);
			workMap.put("overdueList", workOverdueList);
			workMap.put("nodeadlineList", workNodeadlineList);
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
		resultMap.put("work", workVoMap);
		return resultMap;
	}
	@Override
	public ProjectVo updatePrj(ProjectVo prjVo) {
		ProjectVo projectVo = projectService.getProject(prjVo.getPrj_id());
		logger.debug("projectVo 변경 전 : {}", projectVo);
		
		projectVo.setPrj_start_dt(prjVo.getPrj_start_dt());
		projectVo.setPrj_end_dt(prjVo.getPrj_end_dt());
		projectVo.setPrj_cmp_dt(prjVo.getPrj_cmp_dt());
		
		logger.debug("projectVo 변경 후 : {}", projectVo);
		
		int cnt = projectService.updateAllProject(projectVo);
		
		ProjectVo newPrjVo = projectService.getProject(prjVo.getPrj_id());
		if(cnt==1)
			return newPrjVo;
		else 
			return null;
	}
	/**
	 * Method : getWrkgradePnt
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-06 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 : 업무 등급을 확인하여 점수로 환산해주는 메서드
	 */
	private int getPoint(WorkVo workVo) {
		int point = 0;
		switch(workVo.getWrk_grade()) {
			case "A":
				point = 5;
				break;
			case "B":
				point = 4;
				break;
			case "C":
				point = 3;
				break;
			case "D":
				point = 2;
				break;
			case "E":
				point = 1;
				break;
		}
		return point;
	}
	
	/**
	 * Method : getWrkStatus
	 * 작성자 : 유승진
	 * 변경이력 : 2019-08-06 최초 생성
	 * @param workVo
	 * @return
	 * Method 설명 : 업무 시작일, 마감일, 완료일을 확인하여 업무 상태를 반환하는 메서드
	 */
	private String getStatus(WorkVo workVo) {
		Date nowDate = new Date();
		String wrkStatus = "";
		if("Y".equals(workVo.getWrk_cmp_fl())) { // 완료된 업무
			wrkStatus = "cmp";
		} else if(workVo.getWrk_end_dt()==null){ // 마감일 없는 업무
			wrkStatus = "nodeadline";
		} else if(workVo.getWrk_start_dt()!=null && workVo.getWrk_end_dt()!=null && nowDate.before(workVo.getWrk_end_dt())) { // 계획된 업무 : 업무 시작일과 종료일이 존재하고, 마감일이 아직 지나지 않았을 때
			wrkStatus = "plan";
		} else if(nowDate.after(workVo.getWrk_end_dt())){ // 마감일 지난 업무 : 업무 마감일이 현재보다 앞일 때
			wrkStatus = "overdue";
		}
		return wrkStatus;
	}
}
