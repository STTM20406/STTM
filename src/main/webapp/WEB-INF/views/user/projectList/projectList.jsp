<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- date picker resource-->
<link href="/css/datepicker.min.css" rel="stylesheet" type="text/css">
<script src="/js/datepicker.min.js"></script>
<script src="/js/datepicker.en.js"></script>
<!-- Include English language -->

<script>
	
	var currnt_prj_st = "";
	var updateTime = "";
	
	$(document).ready(function(){
		var prjStatus =  "";
		var stColor ="";
		
		$('.prj_mem_sch').on('keyup',"#prj_mem",function(){
			var user_nm = $('#prj_mem').val();
			
			searchName(user_nm);
		});
		
		$('.searchPL').on('keyup',"#searchPL",function(){
			var user_nm = $('#searchPL').val();
			var prj_id = $("#ppt_id").val();
			searchPL(user_nm,prj_id);
		});
		
		$('.searchMem').on('keyup',"#searchMem",function(){
			var user_nm = $('#searchMem').val();
			
			searchMem(user_nm);
		});
		
		//프로젝트 리스트 클릭시 업무 페이지로 이동
		$(".my_project_list").on("click", ".prj_title", function(){
			var prj_id = $(this).attr("id");
			$("#projectId").val(prj_id);
			$("#projectFrm").submit();
		});
		
		
		//프로젝트 상태 클릭 이벤트
		$(".my_project_list").on("click", ".prj_item_st input", function(){
			
			var prj_st = $(this).val().trim();					//프로젝트 상태값
			var prj_id_text = $(this).attr("id").split("_");			//프로젝트 아이디 뽑아오기
			var prj_id = prj_id_text[2].trim();					//프로젝트 아이디 prj_id에 저장
	
			//ajax 호출
			prjStAjax(prj_id, prj_st);
			
			//현재 선택한 버튼의 부모의 이전 클래스를 찾음.
			currnt_prj_st = $(this).parent().prev(".currnt_prj_st");
			$(this).parent().fadeOut(300);
		});

		$(".my_project_list").on("click", ".currnt_prj_st", function(){
			$(this).next().fadeIn(300);
		});
		
		$(".my_project_list").on("mouseleave", ".prj_item_st", function(){
			$(this).fadeOut(300);
		});
		
		
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
			 				html += "<li><a href=''>설정</a></li></ul>";
			 				html += "<ul class='project_item_con'><li>";
			 				html += "<p class='currnt_prj_st'>" + project.prj_st + "</p>";
			 				html += "<div class='prj_item_st'>";
			 				html += "<input type='button' value='계획' id='prj_st_"+ project.prj_id + "'>";
			 				html += "<input type='button' value='진행중' id='prj_st_"+ project.prj_id + "'>";
			 				html += "<input type='button' value='완료' id='prj_st_"+ project.prj_id + "'>";
			 				html += "<input type='button' value='보류' id='prj_st_"+ project.prj_id + "'>";
			 				html += "<input type='button' value='취소' id='prj_st_"+ project.prj_id + "'>";
			 				html += "<input type='button' value='상태없음' id='prj_st_"+ project.prj_id + "'>";
							html += "</div></li></ul></div>"	;
						});	
					}else{
						//상태값이 전체프로젝트일때
						data.data.forEach(function(item, index){
							//html 생성
			 				html += "<div class='project_item'><ul class='project_item_hd'>";
			 				html += "<li>" + item.prj_nm + "</li>";
			 				html += "<li><a href=''>설정</a></li></ul>";
			 				html += "<ul class='project_item_con'><li>";
			 				html += "<p class='currnt_prj_st'>" + item.prj_st + "</p>";
			 				html += "<div class='prj_item_st'>";
			 				html += "<input type='button' value='계획' id='prj_st_"+ item.prj_id + "'>";
			 				html += "<input type='button' value='진행중' id='prj_st_"+ item.prj_id + "'>";
			 				html += "<input type='button' value='완료' id='prj_st_"+ item.prj_id + "'>";
			 				html += "<input type='button' value='보류' id='prj_st_"+ item.prj_id + "'>";
			 				html += "<input type='button' value='취소' id='prj_st_"+ item.prj_id + "'>";
			 				html += "<input type='button' value='상태없음' id='prj_st_"+ item.prj_id + "'>";
							html += "</div></li></ul></div>"	
						});	
					}
					$(".my_project_list").html(html);
				}
			});
		}

		//프로젝트 생성 다음 버튼 클릭시
		$(".prj_btn").on("click", "#prj_btn_next", function(){
			
			if(Check_nonTag(document.getElementById('prj_nm').value) == false){
			  	Check_nonTagReturn('prj_nm');
			  	return false;
			 }
			
			if($("#prj_nm").val().length ==0){
		           	$("#prj_nm").focus();
		           	return false;
			}
			
			$(".new_proejct").animate({left:'-100%'}, 500);
			$(".select_template").animate({left:'0%'}, 500);
		});
		
		//프로젝트 생성 이전 버튼 클릭시
		$(".prj_btn").on("click", "#prj_btn_prev", function(){
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
					var html = "";
					data.data.projectList.forEach(function(project){
						//html 생성
		 				html += "<div class='project_item'><ul class='project_item_hd'>";
		 				html += "<li>" + project.prj_nm + "</li>";
		 				html += "<li><a href=''>설정</a></li></ul>";
		 				html += "<ul class='project_item_con'><li>";
		 				html += "<p class='currnt_prj_st'>" + project.prj_st + "</p>";
		 				html += "<div class='prj_item_st'>";
		 				html += "<input type='button' value='계획' id='prj_st_"+ project.prj_id + "'>";
		 				html += "<input type='button' value='진행중' id='prj_st_"+ project.prj_id + "'>";
		 				html += "<input type='button' value='완료' id='prj_st_"+ project.prj_id + "'>";
		 				html += "<input type='button' value='보류' id='prj_st_"+ project.prj_id + "'>";
		 				html += "<input type='button' value='취소' id='prj_st_"+ project.prj_id + "'>";
		 				html += "<input type='button' value='상태없음' id='prj_st_"+ project.prj_id + "'>";
						html += "</div></li></ul></div>";
					});	
					
					$(".my_project_list").html(html);
				}
				
			});
		}
		
		//프로젝트 설정 버튼을 클릭했을 때
		$(".my_project_list").on("click", ".btnSetting", function(){
			$(".propertySet").animate({right:'0'}, 500);
			var prj_id = $(this).attr("id");
		 	
			propertySetAjax(prj_id);
		});
		
		//프로젝트 닫기 버튼을 클릭했을 때
		$(".btnSetClose").on("click", function(){
			$(".propertySet").animate({right:'-700px'}, 500);
		});
		
		
		
		
		
		
		/* 여기서부터 프로젝트 셋팅 업데이트를 위한 이벤트 핸들러 입니다. */
		$(".propertySet input, select").on("propertychange change keyup paste input blur", function(){
			
			//프로젝트 셋팅 값 가져오기
			var id = $("#ppt_id").val();
			var name = $("#ppt_nm").val();
			var exp = $("#ppt_exp").val();
			var auth = $("#ppt_asc").val();
			var status = $("#ppt_st").val();
			var start_date = $("#ppt_start_date").val();
			var end_date = $("#ppt_end_date").val();
			var cmp_date = $("#ppt_cmp_date").val();
			
			//프로젝트 이름이 없으면 return false
			if(!name){
				return false;
			}
			
			//프로젝트 종료일을 시작일 보다 작을 수 없음.
		        var startDateArr = start_date.split('-');
		        var endDateArr = end_date.split('-');
		        
		        var startDateCompare = new Date(startDateArr[0], parseInt(startDateArr[1])-1, startDateArr[2]);
		        var endDateCompare = new Date(endDateArr[0], parseInt(endDateArr[1])-1, endDateArr[2]);
		         
		        if(startDateCompare.getTime() > endDateCompare.getTime()) {
		        	$(".ctxt").text("프로젝트 마감일은 시작일 이전이여야 합니다. 다시 선택해 주세요.");
		        	layer_popup("#layer2");
		            return false;
		        }
			
			var projectSet = {
							  id : id
						  	, name : name
						  	, exp : exp
						 	, auth : auth
						  	, status : status
						  	, start_date : start_date
						  	, end_date : end_date
						  	, cmp_date : cmp_date
			}
			
			propertySetItemAjax(projectSet);
		});
		
		
		function propertySetItemAjax(projectSet){
			$.ajax({
				url:"/project/propertySetItemAjax",
				method:"post",
				contentType:"application/x-www-form-urlencoded; charset=UTF-8",
				data:"prj_id=" + projectSet.id +
					"&prj_nm=" + projectSet.name +
					"&prj_exp=" + projectSet.exp +
					"&prj_auth=" + projectSet.auth +
					"&prj_st=" + projectSet.status +
					"&prj_start_dt=" + projectSet.start_date +
					"&prj_end_dt=" + projectSet.end_date +
					"&prj_cmp_dt=" + projectSet.cmp_date,
				success:function(data){
					$(".project_item").each(function() {
						var prjItemsId = $(this).attr("id");
						if(prjItemsId == projectSet.id){
							$(this).find(".prj_title").text(data.data.prj_nm);
							$(this).find(".currnt_prj_st").text(data.data.prj_st);
							
							var stColor ="";
							if(data.data.prj_st == "계획"){
								stColor = "prj_st_color01";
							}else if(data.data.prj_st == "진행중"){
								stColor = "prj_st_color02";
							}else if(data.data.prj_st == "완료"){
								stColor = "prj_st_color03";
							}else if(data.data.prj_st == "보류"){
								stColor = "prj_st_color04";
							}else if(data.data.prj_st == "취소"){
								stColor = "prj_st_color05";
							}else if(data.data.prj_st == "상태없음"){
								stColor = "prj_st_color06";
							}
							
							$(this).find(".currnt_prj_st").addClass(stColor);
						}
					});
					
					if(socket){
						var socketMsg = "";
						for(var i=0;i<data.project_mem_list.length;i++){
							socketMsg = "project_setItem," + data.project_mem_list[i].user_email +"," + data.data.prj_nm;
							socket.send(socketMsg);
						}
					}
					
				}
			});
		}

		//프로젝트 생성 버튼 클릭시
		$('.btn_prj_create').on("click", function(){
		        var $href = $(this).attr('href');
		        layer_popup($href);
		        projectCreateMemAddAjax();
		});
		
		
		//프로젝트 관리자 추가하기 버튼 클릭시 해당 프로젝트 멤버 가져오기
		$(".prj_add_adm").fadeOut(0); //멤버리스트 layer 숨기기
		$("#ppt_adm_set").on("click", function(){
			$(".prj_add_adm").fadeIn(300);
			$(".prj_add_mem").fadeOut(300);
			var id = $("#ppt_id").val();
			alert(id);
			projectAdmListAjax(id);
		});
		
		//프로젝트 관리자 가져오는 ajax
		function projectAdmListAjax(id){
			$.ajax({
				url:"/project/projectAdmListAjax",
				method:"post",
				contentType:"application/x-www-form-urlencoded; charset=UTF-8",
				data:	"prj_id=" + id,
				success:function(data){
					var html = "";
					console.log(data.data);
					data.data.forEach(function(item, index){
						//html 생성
						html += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
					});	
					$(".searchPL_item").html(html);
				}
			});
		}
		
		//프로젝트 관리자 리스트를 클릭 했을 때
		$(".searchPL_item").on("click", "li", function(){
			var adm_add_email = $(this).attr("id");
			var id = $("#ppt_id").val();
			projectAdmAddAjax(id, adm_add_email);
		});
		
		//프로젝트 관리자로 선택한 멤버 추가
		function projectAdmAddAjax(id, adm_add_email){
			$.ajax({
				url:"/project/projectAdmAddAjax",
				method:"post",
				data:"prj_id="+ id + "&user_email=" + adm_add_email,
				success:function(data){
					var html = "";
					data.data.forEach(function(item, index){
						html += "<li id='"+ item.user_email +"_"+item.prj_id+"_"+item.prj_mem_lv+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
					});	
					propertySetAjax(id);
					$(".prj_add_box").html(html);
				}
			});
		}
		
		//프로젝트 관리자 삭제 클릭 했을 때
		$(".prj_add_box").on("click", "li input", function(){
			
			var admNum = $(".prj_add_box li").length;
			
			if(admNum == 1){
				$(".ctxt").text("프로젝트 관리자를 삭제할 수 없습니다. 프로젝트에는 최소 1인 이상의 프로젝트 관리자가 필요합니다.");
		        	layer_popup("#layer2");
		            	return false;
			}
			
			var textSplit = $(this).parent().attr("id").split("_");
			var id = textSplit[1];
			var email = textSplit[0];
			
			projectAdmDelAjax(id, email);
		});
		function projectAdmDelAjax(id, email){
			$.ajax({
				url:"/project/projectAdmDelAjax",
				method:"post",
				data:"prj_id="+ id + "&user_email=" + email,
				success:function(data){
					var html = "";
					data.data.forEach(function(item, index){
						html += "<li id='"+ item.user_email +"_"+item.prj_id+"_"+item.prj_mem_lv+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
					});	
					
					propertySetAjax(id);
					$(".prj_add_box").html(html);
				}
			});
		}
		
		//프로젝트 멤버 추가하기 버튼 클릭시 내가 속한 모든 프로젝트의 멤버들을 가져옴
		$(".prj_add_mem").fadeOut(0); //멤버리스트 layer 숨기기
		$("#ppt_mem_set").on("click", function(){
			$(".prj_add_mem").fadeIn(300);
			$(".prj_add_adm").fadeOut(300);
			var id = $("#ppt_id").val();
			alert(id);
			projectMemListAjax(id);
		});
		
		
		
		//프로젝트 멤버리스트를 클릭 했을 때
		$(".prj_mem_item_list").on("click", "li", function(){
			var mem_add_email = $(this).attr("id");
			var id = $("#ppt_id").val();
			console.log(id);
			console.log(mem_add_email);
			projectMemAddAjax(id, mem_add_email);
		});
		
		
		
		//프로젝트 멤버 삭제 클릭 했을 때
		$(".prj_mem_add_box").on("click", "li input", function(){
			
			var textSplit = $(this).parent().attr("id").split("_");
			var id = textSplit[1];
			var email = textSplit[0];
			var lv = textSplit[2];
			
			if(lv == "LV0"){
				$(".ctxt").text("프로젝트 관리자를 삭제할 수 없습니다. 프로젝트에는 최소 1인 이상의 프로젝트 관리자가 필요합니다.");
	        		layer_popup("#layer2");
	            		return false;
			}
			
			projectMemDelAjax(id, email);
		});
		
		
		
		//프로젝트 나가기
		$(".setItem").on("click", "#prjLeaveBtn", function(){
			
			var html = "";
			html += "<h2 class='prj_leave_title'>프로젝트 나가기</h2>";
			html += "<p class='prj_leave_exp'>정말 이 프로젝트를 나가시겠습니까?</p>";
			html += "<a href='' class='btn-layerClose'>아니요. 안나갈래요.</a>";
			html += "<button type='button' id='prjLeave'>네. 나갈래요.</button>";
			
			$(".btn-r").hide(0);
			
			$(".ctxt").html(html);
        		layer_popup("#layer2");
            		return false;
		});
		
		//레이어 팝업창 나갈래요 버튼 클릭시
		$(".pop-conts .ctxt").on("click", "button#prjLeave", function(){
			var prj_id = $("#leave_prj_id").val();
			var prj_mem_lv = $("#leave_prj_mem_lv").val();
			
			$("#projectDelFrm").attr("id", "projectLeaveFrm");
			$("#projectLeaveFrm").attr("action", "/project/leave");
			$(".btn-r").show(0);
			
			if(prj_mem_lv == "LV0"){
				$(".ctxt").text("프로젝트를 나가기를 할 수 없습니다. 프로젝트에는 최소 1인 이상의 프로젝트 관리자가 필요합니다.");
	        		layer_popup("#layer2");
	        		return false;
			}
			$("#projectLeaveFrm").submit();
		});
		
		//프로젝트 삭제
		$("#prjDelBtn").on("click", function(){
			
			var html = "";
			html += "<h2 class='prj_leave_title'>프로젝트 삭제</h2>";
			html += "<p class='prj_leave_exp'>정말 삭제하시겠습니까? 해당 프로젝트는 영구 삭제됩니다.</p>";
			html += "<a href='' class='btn-layerClose'>아니요.</a>";
			html += "<button type='button' id='prjDel'>네. 삭제 할래요.</button>";
			
			$(".btn-r").hide(0);
			
			$(".ctxt").html(html);
       			layer_popup("#layer2");
        		return false;
		});
		
		//레이어 팝업창 삭제할래요 버튼 클릭시
		$(".pop-conts .ctxt").on("click", "button#prjDel", function(){
			$("#projectLeaveFrm").attr("id", "projectDelFrm");
			$("#projectDelFrm").attr("action", "/project/delete");
			$(".btn-r").show(0);
			
			$("#projectDelFrm").submit();
			
		});
		
		$(".select_template").on("click", "li", function(){
			var templateType = $(this).text();
			$(".select_template li").removeClass("templateSelect");
			$(this).addClass("templateSelect");
			if(templateType == "없음"){
				$(".select_template_image span").html("업무리스트를 이용해서<br>자신만의 업무 프로세스를 만들어보세요.");
				$(".select_template_image p").html("<img src='/img/prj_create_img01.png' alt='이미지'>");
			}else if(templateType == "요일별"){
				$(".select_template_image span").html("에자일(Agile) 소프트웨어 개발<br>주별 계획에 적합합니다.");
				$(".select_template_image p").html("<img src='/img/prj_create_img02.png' alt='이미지'>");
			}else{
				$(".select_template_image span").html("각 팀원 별로 업무리스트를 생성합니다.");
				$(".select_template_image p").html("<img src='/img/prj_create_img03.png' alt='이미지'>");
			}
			
			$("#templateType").val(templateType);
		});

		$(".memClose").on("click", function(){
			$(".prj_add_adm").fadeOut(300);
		});
		
		$(".mamListClose").on("click", function(){
			$(".prj_add_mem").fadeOut(300);
		});
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
				
				var stColor ="";
				if(data.data.prj_st == "계획"){
					stColor = "currnt_prj_st prj_st_color01";
				}else if(data.data.prj_st == "진행중"){
					stColor = "currnt_prj_st prj_st_color02";
				}else if(data.data.prj_st == "완료"){
					stColor = "currnt_prj_st prj_st_color03";
				}else if(data.data.prj_st == "보류"){
					stColor = "currnt_prj_st prj_st_color04";
				}else if(data.data.prj_st == "취소"){
					stColor = "currnt_prj_st prj_st_color05";
				}else if(data.data.prj_st == "상태없음"){
					stColor = "currnt_prj_st prj_st_color06";
				}
				
				currnt_prj_st.attr("class", stColor);
			}
			
		});
	}


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
						
						var stColor ="";
						if(project.prj_st == "계획"){
							stColor = "currnt_prj_st prj_st_color01";
						}else if(project.prj_st == "진행중"){
							stColor = "currnt_prj_st prj_st_color02";
						}else if(project.prj_st == "완료"){
							stColor = "currnt_prj_st prj_st_color03";
						}else if(project.prj_st == "보류"){
							stColor = "currnt_prj_st prj_st_color04";
						}else if(project.prj_st == "취소"){
							stColor = "currnt_prj_st prj_st_color05";
						}else if(project.prj_st == "상태없음"){
							stColor = "currnt_prj_st prj_st_color06";
						}
						
						//html 생성
		 				html += "<div class='project_item'><ul class='project_item_hd'>";
		 				html += "<li class='prj_title' id='"+project.prj_id+"'>" + project.prj_nm + "</li>";
		 				html += "<li><a href='javascript:;' class='btnSetting' id='"+project.prj_id+"'>설정</a></li></ul>";
		 				html += "<ul class='project_item_con'><li>";
		 				html += "<p class='"+stColor+"'>" + project.prj_st + "</p>";
		 				html += "<div class='prj_item_st'>";
		 				html += "<input type='button' value='계획' id='prj_st_"+ project.prj_id + "'>";
		 				html += "<input type='button' value='진행중' id='prj_st_"+ project.prj_id + "'>";
		 				html += "<input type='button' value='완료' id='prj_st_"+ project.prj_id + "'>";
		 				html += "<input type='button' value='보류' id='prj_st_"+ project.prj_id + "'>";
		 				html += "<input type='button' value='취소' id='prj_st_"+ project.prj_id + "'>";
		 				html += "<input type='button' value='상태없음' id='prj_st_"+ project.prj_id + "'>";
						html += "</div></li></ul></div>"	;
					});	
				}else{
					//상태값이 전체프로젝트일때
					data.data.forEach(function(item, index){
						
						var stColor ="";
						if(item.prj_st == "계획"){
							stColor = "currnt_prj_st prj_st_color01";
						}else if(item.prj_st == "진행중"){
							stColor = "currnt_prj_st prj_st_color02";
						}else if(item.prj_st == "완료"){
							stColor = "currnt_prj_st prj_st_color03";
						}else if(item.prj_st == "보류"){
							stColor = "currnt_prj_st prj_st_color04";
						}else if(item.prj_st == "취소"){
							stColor = "currnt_prj_st prj_st_color05";
						}else if(item.prj_st == "상태없음"){
							stColor = "currnt_prj_st prj_st_color06";
						}
						
						//html 생성
		 				html += "<div class='project_item'><ul class='project_item_hd'>";
		 				html += "<li class='prj_title' id='"+item.prj_id+"'>" + item.prj_nm + "</li>";
		 				html += "<li><a href='javascript:;' class='btnSetting' id='"+item.prj_id+"'>설정</a></li></ul>";
		 				html += "<ul class='project_item_con'><li>"
		 				html += "<p class='"+stColor+"'>" + item.prj_st + "</p>"
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

function prjSearchAjax(prj_nm){
		$.ajax({
			url:"/project/prjSearchAjax",
			method:"post",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			data: "prj_nm=" + prj_nm,
			success:function(data){
				var html = "";
				data.data.projectList.forEach(function(project){
					
					var stColor ="";
					if(project.prj_st == "계획"){
						stColor = "currnt_prj_st prj_st_color01";
					}else if(project.prj_st == "진행중"){
						stColor = "currnt_prj_st prj_st_color02";
					}else if(project.prj_st == "완료"){
						stColor = "currnt_prj_st prj_st_color03";
					}else if(project.prj_st == "보류"){
						stColor = "currnt_prj_st prj_st_color04";
					}else if(project.prj_st == "취소"){
						stColor = "currnt_prj_st prj_st_color05";
					}else if(project.prj_st == "상태없음"){
						stColor = "currnt_prj_st prj_st_color06";
					}
					
					//html 생성
	 				html += "<div class='project_item'><ul class='project_item_hd'>";
	 				html += "<li class='prj_title' id='"+project.prj_id+"'>" + project.prj_nm + "</li>";
	 				html += "<li><a href='javascript:;' class='btnSetting' id='"+project.prj_id+"'>설정</a></li></ul>";
	 				html += "<ul class='project_item_con'><li>";
	 				html += "<p class='"+stColor+"'>" + project.prj_st + "</p>";
	 				html += "<div class='prj_item_st'>";
	 				html += "<input type='button' value='계획' id='prj_st_"+ project.prj_id + "'>";
	 				html += "<input type='button' value='진행중' id='prj_st_"+ project.prj_id + "'>";
	 				html += "<input type='button' value='완료' id='prj_st_"+ project.prj_id + "'>";
	 				html += "<input type='button' value='보류' id='prj_st_"+ project.prj_id + "'>";
	 				html += "<input type='button' value='취소' id='prj_st_"+ project.prj_id + "'>";
	 				html += "<input type='button' value='상태없음' id='prj_st_"+ project.prj_id + "'>";
					html += "</div></li></ul></div>";
				});	
				
				$(".my_project_list").html(html);
			}
			
		});
	}

function propertySetAjax(prj_id){
		$.ajax({
			url:"/project/propertySetAjax",
			method:"post",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data:"prj_id=" + prj_id,
			success:function(data){
				
				
				//멤버레벨이 1이면 삭제 버튼 없애기 
				if(data.userInfo.prj_mem_lv == "LV1"){
					$(".setItem:last-child").css({display:"none"});
				}else{
					$(".setItem:last-child").css({display:"block"});
				}
				
				
				$("#ppt_id").val(data.projectInfo.prj_id);
				$("#leave_prj_id").val(data.projectInfo.prj_id); //프로젝트 나가기를 위해 value값에 프로젝트 아이디 저장
				$("#leave_prj_mem_lv").val(data.userInfo.prj_mem_lv); //프로젝트 나가기를 위해 value값에 프로젝트 아이디 저장
				$("#ppt_nm").val(data.projectInfo.prj_nm);
				$("#ppt_exp").val(data.projectInfo.prj_exp);
				$("#ppt_asc").val(data.projectInfo.prj_auth);
				$("#ppt_st").val(data.projectInfo.prj_st);
				$("#ppt_start_date").val(data.projectInfo.prjStartDtStr);
				$("#ppt_end_date").val(data.projectInfo.prjEndDtStr);
				$("#ppt_cmp_date").val(data.projectInfo.prjCmpDtStr);
				
				var html = "";
				var html2 = "";
				data.projectAdmList.forEach(function(item, index){
					//html 생성
					html += "<li id='"+ item.user_email +"_"+item.prj_id+"_"+item.prj_mem_lv+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
					
				});	
				
				data.projectMemList.forEach(function(item, index){
					html2 += "<li id='"+ item.user_email +"_"+item.prj_id+"_"+item.prj_mem_lv+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
				});	
				
				$(".prj_add_box").html(html);
				$(".prj_mem_add_box").html(html2);
				
				//멤버레벨이 1인데 권한이 ASC02 또는 ASC03 일때
				if(data.userInfo.prj_mem_lv == "LV1" && data.projectInfo.prj_auth == "ASC02" || data.userInfo.prj_mem_lv == "LV1" && data.projectInfo.prj_auth == "ASC03"){
					$(".propertySet input").prop('readonly', true); 										//설정창의 모든 input readonly
					$(".propertySet select").prop('disabled',true);										//설정창의 모든 select disabled
					$(".propertySet button").prop('disabled', true);										//설정창의 모든 button disabled
					$(".propertySet input[type=button]").prop('disabled', true);
					$(".prjLeaveBtn").prop('disabled', false);
					$(".prj_add_box input").css({visibility:"hidden"});
					$(".prj_mem_add_box input").css({visibility:"hidden"});
					$(".datepicker").css({display:"none"});
					$("#prjLeaveBtn").prop('disabled', false);							
				}else{
					$(".propertySet input").prop('readonly', false);
					$(".propertySet select").prop('disabled',false);
					$(".propertySet button").prop('disabled', false);
					$(".prj_add_box input").css({visibility:"visible"});
					$(".prj_mem_add_box input").css({visibility:"visible"});
					$(".propertySet input[type=button]").prop('disabled', false);
					$(".datepicker").css({display:"block"});
				}
				
				updateTime = data.projectInfo.prj_update;
				updateDiff(updateTime);
			}
		});
	}


//프로젝트 생성시 멤버 리스트 불러오기
	function projectCreateMemAddAjax(){
		$.ajax({
			url:"/project/createMemAddAjax",
			method:"post",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			success:function(data){
				var html="";
				data.data.forEach(function(item, index){
					html += "<li class='checkList etrans'><input type='checkbox' id='infoCheck"+item.user_email+"' name='memItem' value='"+item.user_email+"'><label for='infoCheck"+item.user_email+"'>"+ item.user_nm +"</label><span>"+ item.user_email + "</span></li>";
				});	
				$(".prj_crt_mem_item").html(html);
			}
		});
	}

//프로젝트 관리자 가져오는 ajax
	function projectAdmListAjax(id){
		$.ajax({
			url:"/project/projectAdmListAjax",
			method:"post",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data:	"prj_id=" + id,
			success:function(data){
				var html = "";
				data.data.forEach(function(item, index){
					//html 생성
					html += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
				});	
				$(".prj_mem_item").html(html);
			}
		});
	}


//프로젝트 관리자로 선택한 멤버 추가
	function projectAdmAddAjax(id, adm_add_email){
		$.ajax({
			url:"/project/projectAdmAddAjax",
			method:"post",
			data:"prj_id="+ id + "&user_email=" + adm_add_email,
			success:function(data){
				var html = "";
				data.data.forEach(function(item, index){
					html += "<li id='"+ item.user_email +"_"+item.prj_id+"_"+item.prj_mem_lv+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
				});	
				
				$(".prj_add_box").html(html);
			}
		});
	}


function projectAdmDelAjax(id, email){
		$.ajax({
			url:"/project/projectAdmDelAjax",
			method:"post",
			data:"prj_id="+ id + "&user_email=" + email,
			success:function(data){
				var html = "";
				data.data.forEach(function(item, index){
					html += "<li id='"+ item.user_email +"_"+item.prj_id+"_"+item.prj_mem_lv+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
				});	
				
				$(".prj_add_box").html(html);
			}
		});
	}
	
	

//프로젝트 멤버 가져오는 ajax
	function projectMemListAjax(id){
		$.ajax({
			url:"/project/projectMemListAjax",
			method:"post",
			data:"prj_id=" + id,
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			success:function(data){
				console.log(data);
				
				var html = "";
				data.data.forEach(function(item, index){
					//html 생성
					html += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
				});	
				$(".prj_mem_item_list").html(html);
			}
		});
	}


//프로젝트 멤버 추가
	function projectMemAddAjax(id, mem_add_email){
		$.ajax({
			url:"/project/projectMemAddAjax",
			method:"post",
			data:"prj_id="+ id + "&user_email=" + mem_add_email,
			success:function(data){
				
				var html = "";
				var html2 = "";
				
				data.projectAdmList.forEach(function(item, index){
					html += "<li id='"+ item.user_email +"_"+item.prj_id+"_"+item.prj_mem_lv+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
				});	
				
				data.projectMemList.forEach(function(item, index){
					html2 += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
				});	
				
				$(".prj_mem_add_box").html(html);
				$(".prj_mem_item_list").html(html2);
			}
		});
	}


function projectMemDelAjax(id, email){
		$.ajax({
			url:"/project/projectMemDelAjax",
			method:"post",
			data:"prj_id="+ id + "&user_email=" + email,
			success:function(data){
				
				var html = "";
				var html2 = "";
				
				console.log(data);
				console.log(data.projectAdmList);
				
				data.projectMemList.forEach(function(item, index){
					html += "<li id='"+ item.user_email +"_"+item.prj_id+"_"+item.prj_mem_lv+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
					level = item.user
				});	
				
				data.projectAllMemList.forEach(function(item, index){
					html2 += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
				});	
				
				$(".prj_mem_add_box").html(html);
				$(".prj_mem_item_list").html(html2);
			}
		});
	}
=======
	//프로젝트 생성시 멤버 검색프로젝트 생성시 멤버 검색프로젝트 생성시 멤버 검색
	function searchName(user_nm){
		$.ajax({
			url:"/project/searchName",
			method:"post",
			data: "user_nm=" + user_nm,
			success:function(data){
				console.log(data);
				var html = "";
				data.data.forEach(function(item, index){
					//html 생성
					html += "<li><input type='checkbox' name='memItem' value='"+item.user_email+"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
				});	
				$(".prj_crt_mem_item").html(html);
			}
		});
	}
	//프로젝트 생성시 멤버 검색프로젝트 생성시 멤버 검색
	//프로젝트 생성 후 설정에서 프로젝트 관리자 검색
	function searchPL(user_nm, prj_id){
		$.ajax({
			url:"/project/searchPL",
			method:"post",
			data: "user_nm=" + user_nm + "&prj_id=" + prj_id,
			success:function(data){
				console.log(data);
				var html = "";
				data.data.forEach(function(item, index){
					//html 생성
					html += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
				});	
				$(".searchPL_item").html(html);
			}
		});
	}
	//프로젝트 생성 후 설정에서 프로젝트 관리자 검색
	//프로젝트 생성 후 설정에서 프로젝트 멤버 검색
	function searchMem(user_nm){
		$.ajax({
			url:"/project/searchMem",
			method:"post",
			data: "user_nm=" + user_nm,
			success:function(data){
				console.log(data);
				var html = "";
				data.data.forEach(function(item, index){
					//html 생성
					html += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
				});	
				$(".prj_mem_item_list").html(html);
			}
		});
	}
	//프로젝트 생성 후 설정에서 프로젝트 멤버 검색
	
>>>>>>> branch 'master' of https://github.com/STTM20406/STTM
	
	//프로젝트 생성 - 선택한멤버리스트 함께 넘기기
	function prjBtnSubmit(){
		var memArray = [];
		$("input[name=memItem]:checked").each(function(){
			memArray.push($(this).val());
		});
		$("#memList").val(memArray);
		$("#prj_insert").submit();
		return false;
	}
	
	//업데이트 시간 구하기
	function updateDiff(updateTime){
        var html = "";
        
        var secGap = new Date().getTime() - updateTime;
        var sec = Math.floor(secGap/1000); //초
         
        //시분초일로 표현
        if(sec < 60) {
			sec = sec + '초';
       	}else if(sec < 3600){
			sec = Math.floor(sec%3600/60) + '분 ' + sec%60 + '초';
       	}else if(sec < 86400){
       	 	sec = Math.floor(sec/3600) + '시간 ' + Math.floor(sec%3600/60) + '분 ' + sec%60 + '초';
       	}else{  
       		sec = Math.floor(sec/3600/24) + '일 ' + Math.floor(sec/3600%24) + '시간 ' + Math.floor(sec%3600/60) + '분 ' + sec%60 + '초';
       	}
         
		html += sec + "전에 업데이트 되었습니다.";
		
        // setTimeout함수를 통해 원하는 함수를 1초간격으로 출력해줌
        setTimeout("updateDiff(updateTime)", 1000);
        
        $(".prj_update").text(html);
    }

	
	//layer popup - 프로젝트 생성
	function layer_popup(el){
		console.log(el);

        var $el = $(el);		//레이어의 id를 $el 변수에 저장
        var isDim = $el.prev().hasClass('dimBg');	//dimmed 레이어를 감지하기 위한 boolean 변수

        isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

        var $elWidth = ~~($el.outerWidth()),
            $elHeight = ~~($el.outerHeight()),
            docWidth = $(document).width(),
            docHeight = $(document).height();

        // 화면의 중앙에 레이어를 띄운다.
        if ($elHeight < docHeight || $elWidth < docWidth) {
            $el.css({
                marginTop: -$elHeight /2,
                marginLeft: -$elWidth/2
            })
        } else {
            $el.css({top: 0, left: 0});
        }

        $el.find('a.btn-layerClose').click(function(){
            isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
            return false;
        });

        $('.layer .dimBg').click(function(){
            $('.dim-layer').fadeOut();
            return false;
        });

    }
	
	function Check_nonTag(text){
		var opentag = '><';
	 	var i ; 
	 
	 	for ( i=0; i < text.length; i++ ){
	  		if( opentag.indexOf(text.substring(i,i+1)) > 0){
	   		break ; 
		}
	}
	 
	if ( i != text.length ){
		return false;
	}else{
		return true ;
	} 
		return false;
	} 


	function Check_nonTagReturn(inputId){
		alert("HTML 태그는 입력하실 수 없습니다");
	  	document.getElementById(inputId).focus();
	  	return false;
	}
</script>

<form id="projectFrm" action="/work/list" method="post">
	<input type="hidden" name="prj_id" id="projectId" value="">
</form>

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
					<input type="text" name="prj_sch" id="prj_sch" maxlength="20"
						placeholder="검색어를 입력해주세요">
					<button type="button" id="sch_submit" value="검색">검색</button>
				</fieldset>
			</div>
		</div>
	</div>
	<div class="sub_btn">
		<ul>
			<li><a href="#layer1" class="btn_prj_create a_style_01">프로젝트
					생성</a></li>
		</ul>
	</div>
</div>

<section class="contents">
	<div class="project">
		<div class="project_wrap">
			<h2>프로젝트 리스트</h2>
			<div class="project_list my_project_list">
				<c:forEach items="${projectList}" var="projectList">
					<div class="project_item" id="${projectList.prj_id}">
						<ul class="project_item_hd">
							<li class="prj_title" id="${projectList.prj_id}">${projectList.prj_nm}</li>
							<li><a href="javascript:;" class="btnSetting" id="${projectList.prj_id}">설정</a></li>
						</ul>
						<ul class="project_item_con">
							<li>
								<c:choose>
									<c:when test="${projectList.prj_st == '계획'}">
										<p class="currnt_prj_st prj_st_color01">${projectList.prj_st}</p>
									</c:when>
									<c:when test="${projectList.prj_st == '진행중'}">
										<p class="currnt_prj_st prj_st_color02">${projectList.prj_st}</p>
									</c:when>
									<c:when test="${projectList.prj_st == '완료'}">
										<p class="currnt_prj_st prj_st_color03">${projectList.prj_st}</p>
									</c:when>
									<c:when test="${projectList.prj_st == '보류'}">
										<p class="currnt_prj_st prj_st_color04">${projectList.prj_st}</p>
									</c:when>
									<c:when test="${projectList.prj_st == '취소'}">
										<p class="currnt_prj_st prj_st_color05">${projectList.prj_st}</p>
									</c:when>
									<c:otherwise>
										<p class="currnt_prj_st prj_st_color06">${projectList.prj_st}</p>
									</c:otherwise>
								</c:choose>
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


<!--  프로젝트 생성 레이어 팝업창 -->
<div class="dim-layer">
	<div class="dimBg"></div>
	<div id="layer1" class="pop-layer">
		<div class="pop-container">
			<div class="pop-project">
				<!--content //-->
				<form action="/project/form" method="post" id="prj_insert">
					<input type="hidden" name="memList" id="memList" value="">
					<input type="hidden" name="templateType" id="templateType" value="">
					<div class="new_proejct">
						<h2>Create a new project</h2>
						<ul>
							<li>	
								<label for="prj_nm">프로젝트 이름</label> 
								<input type="text" id="prj_nm" name="prj_nm" placeholder="예) 웹사이트 개발">
							</li>
							<li>
								<label for="prj_exp">프로젝트 설명</label>
								<input type="text" id="prj_exp" name="prj_exp" placeholder="(선택) 프로젝트 설명을 입렵해 주세요.">
							</li>
							<li>
								<label for="prj_exp">프로젝트 권한</label>
								<select name="prj_auth">
									<option value="ASC01">전체 액세스</option>
									<option value="ASC02">제한 액세스</option>
									<option value="ASC03">통제 액세스</option>
								</select>
							</li>
							<li><label for="prj_mem">프로젝트 멤버 추가</label>
								<div class="prj_mem_list">
									<div class="prj_mem_sch">
										<fieldset id="hd_sch">
											<legend>사이트 내 프로젝트 검색</legend>
											<input type="text" name="prj_mem" id="prj_mem" maxlength="20" placeholder="검색어를 입력해주세요">
										</fieldset>
									</div>
								</div>
							</li>
						</ul>
						<ul class="prj_crt_mem_item"></ul>
						<div class="prj_btn">
							<a href="javascript:;" id="prj_btn_next">다음 : 템플릿 선택</a>
						</div>
					</div>
					<div class="select_template">
						<h2>Project Templates<span></span></h2>
						<ul>
							<li>없음</li>
							<li>요일별</li>
							<li>개인별</li>
						</ul>
						<div class="select_template_image">
							<span>업무리스트를 이용해서<br>자신만의 업무 프로세스를 만들어보세요.</span>
							<p><img src="/img/prj_create_img01.png" alt="이미지"></p>
						</div>
						<div class="prj_btn">
							<a href="javascript:;" id="prj_btn_prev">뒤로</a> <input type="button" onclick="prjBtnSubmit();" value="프로젝트 만들기">
						</div>
					</div>
				</form>
				<div class="btn-r">
					<a href="#" class="btn-layerClose">Close</a>
				</div>
				<!--// content-->
			</div>
		</div>
	</div>
</div>


<!-- property setting layer -->
<div id="propertySet" class="propertySet">
	<div class="propertySetWrap">
		<div class="setHd">
			<div class="setHdTitle">
				<input type="hidden" id="ppt_id" name="ppt_id" value=""> <span class="prj_asc">전체</span>
				<h2>
					<input type="text" id="ppt_nm" name="ppt_nm" value="">
				</h2>
			</div>
			<p class="prj_update">3분전에 업데이트 되었습니다.</p>
			<p>
				<input type="text" id="ppt_exp" name="ppt_exp" placeholder="프로젝트 설명을 입력해 주세요.">
			</p>
		</div>

		<div class="setCon">
			<dl class="setItem">
				<dt>프로젝트 권한설정</dt>
				<dd>
					<div class="setAsc">
						<select id="ppt_asc" name="ppt_asc">
							<option value="ASC01">전체 액세스</option>
							<option value="ASC02">권한 액세스</option>
							<option value="ASC03">통제 액세스</option>
						</select>
					</div>
				</dd>
			</dl>
			<dl class="setItem">
				<dt>프로젝트 상태</dt>
				<dd>
					<div class="setStatus">
						<select id="ppt_st" name="ppt_st">
							<option value="계획">계획</option>
							<option value="진행중">진행중</option>
							<option value="완료">완료</option>
							<option value="보류">보류</option>
							<option value="취소">취소</option>
							<option value="상태없음">상태없음</option>
						</select>
					</div>
				</dd>
			</dl>
			<dl class="setItem">
				<dt>프로젝트 시작일</dt>
				<dd>
					<input type="text" data-language="en" class="datepicker-here datePick" id="ppt_start_date">
				</dd>
			</dl>
			<dl class="setItem">
				<dt>프로젝트 마감일</dt>
				<dd>
					<input type="text" data-language="en" class="datepicker-here datePick" id="ppt_end_date">
				</dd>
			</dl>
			<dl class="setItem">
				<dt>프로젝트 완료일</dt>
				<dd>
					<input type="text" data-language="en" class="datepicker-here datePick" id="ppt_cmp_date">
				</dd>
			</dl>
			
			
			
			<dl class="setItem">
				<dt>프로젝트 관리자</dt>
				<dd>
					<button type="button" id="ppt_adm_set" name="ppt_adm_set">관리자 추가 버튼</button>

					<!-- 프로젝트 관리자 리스트 box -->
					
					<ul class="prj_add_box"></ul>

					<div class="prj_add_adm">
						<label for="prj_mem">프로젝트 관리자 추가</label>
						<input type="button" value="닫기" id="memClose" class="memClose">
<<<<<<< HEAD
						<div class="prj_mem_list">
							<div class="prj_mem_sch">
=======
						<div class="prj_mem_list"> 
							<div class="searchPL">
>>>>>>> branch 'master' of https://github.com/STTM20406/STTM
								<fieldset id="hd_sch">
									<input type="text" name="user_nm" id="searchPL" maxlength="20" placeholder="검색어를 입력해주세요">
								</fieldset>
							</div>
					<!-- 추가된 프로젝트 관리자 리스트 box -->
							<ul class="searchPL_item"></ul>
						</div>
					</div>
				</dd>
			</dl>
			
			<dl class="setItem">
				<dt>프로젝트 멤버</dt>
				<dd>
					<button type="button" id="ppt_mem_set" name="ppt_mem_set">프로젝트 멤버 추가 버튼</button>
					<!-- 프로젝트 멤버 리스트 box -->
					<ul class="prj_mem_add_box"></ul>

					<div class="prj_add_mem">
						<label for="prj_mem">프로젝트 멤버 추가</label>
						<input type="button" value="닫기" id="mamListClose" class="mamListClose">
<<<<<<< HEAD
						<div class="prj_mem_list">
							<div class="prj_mem_sch">
=======
						<div class="prj_mem_list"> 
							<div class="searchMem">
>>>>>>> branch 'master' of https://github.com/STTM20406/STTM
								<fieldset id="hd_sch">
									<legend>사이트 내 프로젝트 검색</legend>
									<input type="text" name="user_nm" id="searchMem" maxlength="20" placeholder="검색어를 입력해주세요" value="">
								</fieldset>
							</div>
					<!-- 추가된 프로젝트 멤버 리스트 box -->
							<ul class="prj_mem_item_list"></ul>
						</div>
					</div>
				</dd>
			</dl>
			<dl class="setItem">
				<dt>프로젝트 나가기</dt>
				<dd>
					<button type="button" id="prjLeaveBtn">프로젝트 나가기</button>
				</dd>
			</dl>
			<dl class="setItem">
				<dt>프로젝트 삭제</dt>
				<dd>
					<button type="button" id="prjDelBtn">프로젝트 삭제</button>
				</dd>
			</dl>
		</div>
	</div>
	<div class="btnSetClose">닫기</div>
</div>


<form id="projectLeaveFrm" action="/project/leave" method="post">
	<input type="hidden" id="leave_prj_id" name="prj_id" value="">
	<input type="hidden" id="leave_prj_mem_lv" value="">
</form>


<!-- 오류 알림창 -->
<!-- <div class="dim-layer"> -->
<!-- 	<div class="dimBg"></div> -->
<div id="layer2" class="pop-layer">
	<div class="pop-container">
		<div class="pop-conts">
			<!--content //-->
			<p class="ctxt mb20"></p>
			<div class="btn-r">
				<a href="#" class="btn-layerClose">Close</a>
			</div>
			<!--// content-->
		</div>
	</div>
</div>
<!-- </div> -->

