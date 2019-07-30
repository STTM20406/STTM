<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script>

	var currnt_prj_st = "";
	
	$(document).ready(function(){
		//프로젝트 상태 클릭 이벤트
		$(".my_project_list").on("click", ".prj_item_st input", function(){
			
			var prj_st = $(this).val().trim();					//프로젝트 상태값
			var prj_id_text = $(this).attr("id").split("_");	//프로젝트 아이디 뽑아오기
			var prj_id = prj_id_text[2].trim();					//프로젝트 아이디 prj_id에 저장
	
			//ajax 호출
			prjStAjax(prj_id, prj_st);
			
			//현재 선택한 버튼의 부모의 이전 클래스를 찾음.
			currnt_prj_st = $(this).parent().prev(".currnt_prj_st");
		});
		
		//프로젝트 상태값 변경 ajax
		function prjStAjax(prj_id, prj_st){
			$.ajax({
				url:"/project/prjStAjax",
				method:"post",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
				data: "prj_id=" + prj_id + "&prj_st=" + prj_st,
				success:function(data){
					var text = data.data.prj_st;			
					currnt_prj_st.text(text);
				}
				
			});
		}
		
		
		//프로젝트 상태별 프로젝트 리스트 조회 ajax
		$("#prj_all_st").on("change", function(){
			var prj_status = $(this).val();
			
			//ajax 호출
			prjStListAjax(prj_status);
			
		});
		
		//프로젝트 상태별 프로젝트 리스트 조회 ajax
		function prjStListAjax(prj_status){
			$.ajax({
				url:"/project/prjStListAjax",
				method:"post",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				data: "prj_st=" + prj_status,
				success:function(data){
					
					var html = "";
					
					//상태값이 전체프로젝트가 아닐때
					if(prj_status != "전체프로젝트"){
						//데이터에서 값을 꺼내 리스트 생성
						data.data.projectList.forEach(function(project){
							//html 생성
			 				html += "<div class='project_item'><ul class='project_item_hd'>";
			 				html += "<li>" + project.prj_nm + "</li>";
			 				html += "<li><a href=''>설정</a></li></ul>"
			 				html += "<ul class='project_item_con'><li>"
			 				html += "<p class='currnt_prj_st'>" + project.prj_st + "</p>"
			 				html += "<div class='prj_item_st'>"
			 				html += "<input type='button' value='계획' id='prj_st_"+ project.prj_id + "'>"
			 				html += "<input type='button' value='진행중' id='prj_st_"+ project.prj_id + "'>"
			 				html += "<input type='button' value='완료' id='prj_st_"+ project.prj_id + "'>"
			 				html += "<input type='button' value='보류' id='prj_st_"+ project.prj_id + "'>"
			 				html += "<input type='button' value='취소' id='prj_st_"+ project.prj_id + "'>"
			 				html += "<input type='button' value='상태없음' id='prj_st_"+ project.prj_id + "'>"
							html += "</div></li></ul></div>"	
						});	
					}else{
						//상태값이 전체프로젝트일때
						data.data.forEach(function(item, index){
							//html 생성
			 				html += "<div class='project_item'><ul class='project_item_hd'>";
			 				html += "<li>" + item.prj_nm + "</li>";
			 				html += "<li><a href=''>설정</a></li></ul>"
			 				html += "<ul class='project_item_con'><li>"
			 				html += "<p class='currnt_prj_st'>" + item.prj_st + "</p>"
			 				html += "<div class='prj_item_st'>"
			 				html += "<input type='button' value='계획' id='prj_st_"+ item.prj_id + "'>"
			 				html += "<input type='button' value='진행중' id='prj_st_"+ item.prj_id + "'>"
			 				html += "<input type='button' value='완료' id='prj_st_"+ item.prj_id + "'>"
			 				html += "<input type='button' value='보류' id='prj_st_"+ item.prj_id + "'>"
			 				html += "<input type='button' value='취소' id='prj_st_"+ item.prj_id + "'>"
			 				html += "<input type='button' value='상태없음' id='prj_st_"+ item.prj_id + "'>"
							html += "</div></li></ul></div>"	
						});	
					}
					$(".my_project_list").html(html);
				}
			});
		}

		
		//프로젝트 생성 다음 버튼 클릭시
		$("#prj_btn_next").on('click', function(){
			$(".new_proejct").animate({left:'-100%'}, 500);
			$(".select_template").animate({left:'0%'}, 500);
		});
		
		//프로젝트 생성 이전 버튼 클릭시
		$("#prj_btn_prev").on('click', function(){
			$(".new_proejct").animate({left:'0%'}, 500);
			$(".select_template").animate({left:'100%'}, 500);
			
		});
		
		
		//프로젝트명으로 프로젝트 검색
		$("#prj_sch").on("keyup", function(){
			
			//검색어 변수에 저장
			var prj_nm = $(this).val();
			
			//검색을 위한 ajax 호출
			prjSearchAjax(prj_nm);
		});
		
		function prjSearchAjax(prj_nm){
			$.ajax({
				url:"/project/prjSearchAjax",
				method:"post",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				data: "prj_nm=" + prj_nm,
				success:function(data){
					
					console.log(data);
					var html = "";
					
					data.data.projectList.forEach(function(project){
						//html 생성
		 				html += "<div class='project_item'><ul class='project_item_hd'>";
		 				html += "<li>" + project.prj_nm + "</li>";
		 				html += "<li><a href=''>설정</a></li></ul>"
		 				html += "<ul class='project_item_con'><li>"
		 				html += "<p class='currnt_prj_st'>" + project.prj_st + "</p>"
		 				html += "<div class='prj_item_st'>"
		 				html += "<input type='button' value='계획' id='prj_st_"+ project.prj_id + "'>"
		 				html += "<input type='button' value='진행중' id='prj_st_"+ project.prj_id + "'>"
		 				html += "<input type='button' value='완료' id='prj_st_"+ project.prj_id + "'>"
		 				html += "<input type='button' value='보류' id='prj_st_"+ project.prj_id + "'>"
		 				html += "<input type='button' value='취소' id='prj_st_"+ project.prj_id + "'>"
		 				html += "<input type='button' value='상태없음' id='prj_st_"+ project.prj_id + "'>"
						html += "</div></li></ul></div>"	
					});	
					
					$(".my_project_list").html(html);
				}
				
			});
		}
		
	});
	
	//layer popup
	function layer_open(el){

		var temp = $('#' + el);
		var bg = temp.prev().hasClass('bg');	//dimmed 레이어를 감지하기 위한 boolean 변수

		if(bg){
			$('.layer').fadeIn();	//'bg' 클래스가 존재하면 레이어가 나타나고 배경은 dimmed 된다. 
		}else{
			temp.fadeIn();
		}

		// 화면의 중앙에 레이어를 띄운다.
		if (temp.outerHeight() < $(document).height() ) temp.css('margin-top', '-'+temp.outerHeight()/2+'px');
		else temp.css('top', '0px');
		if (temp.outerWidth() < $(document).width() ) temp.css('margin-left', '-'+temp.outerWidth()/2+'px');
		else temp.css('left', '0px');

		temp.find('a.cbtn').click(function(e){
			if(bg){
				$('.layer').fadeOut(); //'bg' 클래스가 존재하면 레이어를 사라지게 한다. 
			}else{
				temp.fadeOut();
			}
			e.preventDefault();
		});

		$('.layer .bg').click(function(e){	//배경을 클릭하면 레이어를 사라지게 하는 이벤트 핸들러
			$('.layer').fadeOut();
			e.preventDefault();
		});

	}
</script>

<div class="sub_menu">
	<div class="prj_hd">
		<div class="prj_st_select">
			<select name="prj_all_st" id="prj_all_st">
				<option value="전체프로젝트">전체 프로젝트</option>
				<option value="상태없음">상태없음</option>
				<option value="계획">계획</option>
				<option value="진행중">진행중</option>
				<option value="완료">완료</option>
				<option value="보류">보류</option>
				<option value="취소">취소</option>
			</select>
		</div>
		<div class="prj_sch_box">
			<div class="prj_sch_wr">
				<fieldset id="hd_sch">
	                <legend>사이트 내 프로젝트 검색</legend>
	                <input type="text" name="prj_sch" id="prj_sch" maxlength="20" placeholder="검색어를 입력해주세요">
	                <button type="button" id="sch_submit" value="검색">검색</button>
	           	</fieldset>
           	</div>
		</div>
	</div>
	<div class="sub_btn">
		<ul>
			<li><input type="button" class="inp_style_01" onclick="layer_open('layer2');return false;" value="프로젝트 생성"></li>
		</ul>
	</div>
</div>

<section class="contents">
	<div class="project">
		<div class="project_wrap">
			<h2>즐겨찾는 프로젝트</h2>
			<div class="project_list">
				<div class="project_item">
					<ul class="project_item_hd">
						<li>프로젝트 이름</li>
						<li><a href="">설정</a></li>
					</ul>
					<ul class="project_item_con">
						<li>
							<a href="">완료됨</a>
							<div class="prj_item_st">
								<a href="">계획</a>
								<a href="">진행중</a>
								<a href="">완료</a>
								<a href="">보류</a>
								<a href="">취소</a>
								<a href="">상태없음</a>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		
		<div class="project_wrap">
			<h2>프로젝트 리스트</h2>
			<div class="project_list my_project_list">
				<c:forEach items="${projectList}" var="projectList">
				<div class="project_item">
					<ul class="project_item_hd">
						<li>${projectList.prj_nm}</li>
						<li><a href="">설정</a></li>
					</ul>
					<ul class="project_item_con">
						<li>
							<p class="currnt_prj_st">${projectList.prj_st}</p>
							<div class="prj_item_st">
								<input type="button" value="계획" id="prj_st_${projectList.prj_id}">
								<input type="button" value="진행중" id="prj_st_${projectList.prj_id}">
								<input type="button" value="완료" id="prj_st_${projectList.prj_id}">
								<input type="button" value="보류" id="prj_st_${projectList.prj_id}">
								<input type="button" value="취소" id="prj_st_${projectList.prj_id}">
								<input type="button" value="상태없음" id="prj_st_${projectList.prj_id}">
							</div>
						</li>
					</ul>
				</div>
				</c:forEach>
			</div>
		</div>
		
	</div>
</section>


<!-- popup -->
<div class="layer">
	<div class="bg"></div>
	<div id="layer2" class="pop-layer">
		<div class="pop-container">
			<div class="pop-project">
				<!--content //-->
				<form action="/project/form" method="post" id="prj_insert">
					<div class="new_proejct">
						<h2>새로운 프로젝트 생성하기</h2>
						<ul>
							<li>
								<label for="prj_nm">Project Name</label>
								<input type="text" id="prj_nm" name="prj_nm" placeholder="예) 웹사이트 개발">
							</li>
							<li>
								<label for="prj_exp">Project Description</label>
								<input type="text" id="prj_exp" name="prj_exp" placeholder="(선택) 프로젝트 설명을 입렵해 주세요.">
							</li>
							<li>
								<label for="prj_exp">Project Authority</label>
								<select name="prj_auth">
									<option value="ASC01">전체 액세스</option>
									<option value="ASC02">제한 액세스</option>
									<option value="ASC03">통제 액세스</option>
								</select>
							</li>
							<li>
								<label for="prj_mem">Project Member</label>
								<div class="prj_mem_list">
									<div class="prj_mem_sch">
										<fieldset id="hd_sch">
							                <legend>사이트 내 프로젝트 검색</legend>
								                <input type="text" name="prj_mem" id="prj_mem" maxlength="20" placeholder="검색어를 입력해주세요">
								                <button type="submit" id="prj_btn_submit" value="검색">검색</button>
							           	</fieldset>
									</div>
									<ul class="prj_mem_item">
										<li>또굥이</li>
										<li>개굴이</li>
										<li>사과</li>
										<li>갈비</li>
									</ul>
								</div>
							</li>
						</ul>
						<div class="prj_btn">
							<a href="javascript:;" id="prj_btn_next">다음 : 템플릿 선택</a>
						</div>
					</div>
					<div class="select_template">
						<h2>프로젝트 템플릿 선택하기</h2>
						<ul>
							<li>없음</li>
							<li>요일별</li>
							<li>개인별</li>
						</ul>
						<div class="prj_btn">
							<a href="javascript:;" id="prj_btn_prev">뒤로</a>
							<input type="submit" id="prj_btn_submit" value="프로젝트 만들기">
						</div>
					</div>
				</form>
				<div class="btn-r">
					<a href="#" class="cbtn"></a>
				</div>
				<!--// content-->
			</div>
		</div>
	</div>
</div>





