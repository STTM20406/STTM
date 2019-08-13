<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(document).ready(function() {
		
		$(".workList_set ").hide();
		
		//업무리스트 삭제 버튼에서 마우스가 떠났을때 삭제버튼 사라지게 하기
		$(".workListBox").on("mouseleave", ".workList_set", function(){
			$(this).fadeOut(300);
		});
		
		//업무리스트 추가 버튼 클릭시
		$(".workListAdd").on("click", "#btnWorkList", function() {
			var html = "";
			html += "<input type='text' value='' id='workListName'>";

			//workListAdd input의 개수를 파악해 텍스트 창 한개만 만들기
			var len = $(".workListAdd input").length;
			if (len < 2) {
				$(".workListAdd").append(html);
			}

			//현재 버튼을 취소 버튼으로 변경하고 id도 변경
			$(this).attr("id", "btnCancelWorklist");
			$(this).attr("value", "취소");
		});

		//업무리스트 취소 버튼 클릭시
		$(".workListAdd").on("click", "#btnCancelWorklist", function() {
			$(this).attr("id", "btnWorkList");
			$(this).attr("value", "업무리스트 추가");
			//업무리스트 이름 입력하는 창 없애기
			$("#workListName").remove();
		});

		//업무리스트 이름 입력 후 엔터 또는 다른곳 클릭시 업무리스트 추가하는 ajax실행 
		$(".workListAdd").on("keydown blur", "#workListName", function(key) {
			if (key.keyCode == 13) {
				var workListNm = $(this).val();
				workListAddAjax(workListNm);
				$(this).val("").focus();
			}
		});

		
		//업무리스트 추가
		function workListAddAjax(workListNm) {
			$.ajax({
				url : "/work/workListAddAjax",
				method : "post",
				data : "wrk_lst_nm=" + workListNm,
				success : function(data) {
					var html = "";
					data.workList.forEach(function(workList, index) {
						html += "<div class='column' id='"+ workList.wrk_lst_id + "'>";
						html += "<div class='portle'><div class='portlet-header'>";
						html += "<input type='text' value='"+ workList.wrk_lst_nm +"' id='wrkListName'></div>";
						html += "<div class='portlet-side'>";
						html += "<input type='button' class='workList_add_i' value='새업무 추가'>";
						html += "<a href='javascript:;' class='workList_set_i'>업무리스트 설정</a>";
						html += "<div class='workList_set'>";
						html += "<input type='button' id='btnWorkListDel_"+workList.wrk_lst_id+"' value='업무리스트 삭제'></div></div>";
						html += "<div class='portlet-status'><ul>";
						html += "<li><p>진행중 업무<span>4</span></p><a href='javascript:;'>완료된업무보기 <span>2</span></a></li>";
						html += "<li class='graph'></li></ul></div>";
						html += "<div class='portlet-content'><ul id='sortable1' class='connectedSortable'>";
						
						data.works.forEach(function(work, index2) {
	 						if(work.wrk_lst_id == workList.wrk_lst_id){
	 							html += "<li class='ui-state-default' id='"+workList.wrk_lst_id+"'>"+work.wrk_nm+"</li>";
	 						}
						});
							
						html += "</ul></div></div></div>";
					});
					
					$(".workListBox").html(html);
				}
			});
		}

		//업무리스트 삭제 버튼 클릭시
		$(".workListBox").on("click", ".workList_set input[type=button]", function() {
			var idText = $(this).attr("id").split("_");
			var workListID = idText[1];
			
			workListDelAjax(workListID);
			
			
		});
		
		//업무리스트 삭제
		function workListDelAjax(workListID) {
			$.ajax({
				url : "/work/workListDelAjax",
				method : "post",
				data : "wrk_lst_id=" + workListID,
				error :function(e){
					console.log(e);	
				},
				success : function(data) {
					if(data.null == ""){
		 				$(".ctxt").text("해당 업무리스트에 업무가 존재 합니다.");
		 				layer_popup("#layer2");
						return false;
					}else{
						var html = "";
						data.workList.forEach(function(item, index) {
							html += "<div class='column' id='"+ item.wrk_lst_id + "'>";
							html += "<div class='portle'><div class='portlet-header'>";
							html += "<input type='text' value='"+ item.wrk_lst_nm +"' id='wrkListName'></div>";
							html += "<div class='portlet-side'>";
							html += "<input type='button' class='workList_add_i' value='새업무 추가'>";
							html += "<a href='javascript:;' class='workList_set_i'>업무리스트 설정</a>";
							html += "<div class='workList_set'>";
							html += "<input type='button' id='btnWorkListDel_"+item.wrk_lst_id+"' value='업무리스트 삭제'></div></div>";
							html += "<div class='portlet-status'><ul>";
							html += "<li><p>진행중 업무<span>4</span></p><a href='javascript:;'>완료된업무보기 <span>2</span></a></li>";
							html += "<li class='graph'></li></ul></div>";
							html += "<div class='portlet-content'><ul id='sortable1' class='connectedSortable'>";
							
							data.works.forEach(function(work, index2) {
		 						if(work.wrk_lst_id == item.wrk_lst_id){
		 							html += "<li class='ui-state-default' id='"+item.wrk_lst_id+"'>"+work.wrk_nm+"</li>";
		 						}
							});
								
							html += "</ul></div></div></div>";
							$(".workListBox").html(html);
						});
					}
					
				}
			});
		}
		
		$(".workListBox").on("click", ".workList_add_i", function(){
			alert("test");
		});
		
		//업무리스트 설정 아이콘클릭시 삭제 버튼 보여주기
		$(".workListBox").on("click", ".workList_set_i", function() {
			$(this).next(".workList_set").fadeIn(300);
		});
		
		
		
		$(function() {
			$("#sortable1, #sortable2").sortable({
				connectWith : ".connectedSortable"
			}).disableSelection();
		});

		$(function() {
			$(".column").sortable({
				connectWith : ".column",
				handle : ".portlet-header",
				cancel : ".portlet-toggle",
				placeholder : "portlet-placeholder ui-corner-all"
			});

			$(".portlet")
					.addClass("ui-widget ui-widget-content ui-helper-clearfix ui-corner-all")
					.find(".portlet-header")
					.addClass("ui-widget-header ui-corner-all")
					.prepend("<span class='ui-icon ui-icon-minusthick portlet-toggle'></span>");

			$(".portlet-toggle").on("click", function() {
				var icon = $(this);
				icon.toggleClass("ui-icon-minusthick ui-icon-plusthick");
				icon.closest(".portlet").find(".portlet-content").toggle();
			});
		});
		
	});
</script>


<div class="sub_menu">
	<ul class="sub_menu_item">
		<li><a href="/work/list">Work</a></li>
		<li><a href="/gantt/project">Gantt Chart</a></li>
		<li><a href="/analysis">Work Analysis</a></li>
		<li><a href="/publicFileLinkPagination">File&amp;Link</a></li>
		<li><a href="">Meeting</a></li>
		<li><a href="/vote">Vote</a></li>
	</ul>
	<div class="sub_btn">
		<ul>
			<li><input type="button" value="4"></li>
			<li><input type="button" value="프로젝트 대화"></li>
			<li><input type="button" value="프로젝트 설정"></li>
		</ul>
	</div>
</div>

<style>
.column {
	width: 250px; float : left;
	padding-bottom: 100px;
	float: left;
	
}

.portlet-header {
	position: relative;
	height:50px
}

.portlet-toggle {
	position: absolute;
	top: 50%;
	right: 0;
	margin-top: -8px;
}

.portlet {
	border: 1px solid #e1e1e1
}
</style>

	<section class="contents">
		<h2>${PROJECT_INFO.prj_nm}</h2>

		<div class="workListWrap">
			<div class="workListBox">
			<c:forEach items="${workList}" var="workList">
				<div class="column" id="${workList.wrk_lst_id}">
					<div class="portlet">
						<input type="text" value="${workList.wrk_lst_nm}" id="wrkListName">
						<div class="portlet-header">
						</div>
						<div class="portlet-side">
							<input type="button" class="workList_add_i" value="새업무 추가">
							<a href="javascript:;" class="workList_set_i">업무리스트 설정</a>
							<div class="workList_set">
								<input type="button" id="btnWorkListDel_${workList.wrk_lst_id}" value="업무리스트 삭제">
							</div>
						</div>
						<div class="portlet-status">
							<ul>
								<li>
									<p>진행중 업무 <span>4</span></p>
									<a href="javascript:;">완료된업무보기 <span>2</span></a>
								</li>
								<li class="graph"></li>
							</ul>
						</div>
						<div class="portlet-content">
							<ul id="sortable1" class="connectedSortable">
								<c:forEach items="${works}" var="work">
									<c:choose>
										<c:when test="${workList.wrk_lst_id == work.wrk_lst_id}">
											<li class="ui-state-default" id="${work.wrk_lst_id}">${work.wrk_nm}</li>
										</c:when>
									</c:choose>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</c:forEach>
			</div>
			<div class="workListAdd">
				<input type="button" id="btnWorkList" value="업무리스트 추가">
			</div>
		</div>
	</section>

	<!-- <section class="contents"> -->
	<!-- 	<div class="work_list_wrap"> -->
	<!-- 		<div class="workList"> -->
	<!-- 			<div class="workList_hd"> -->
	<!-- 				<dl> -->
	<!-- 					<dt><input type="text" value="" id="wrkListName"></dt> -->
	<!-- 					<dd> -->
	<!-- 						<input type="button" class="workList_add_i" value="새업무 추가"> -->
	<!-- 						<a href="" class="workList_set_i">업무리스트 설정</a> -->
	<!-- 						<div class="workList_set"> -->
	<!-- 							<input type="button" value="업무리스트 삭제"> -->
	<!-- 						</div> -->
	<!-- 					</dd> -->
	<!-- 				</dl> -->
	<!-- 				<ul> -->
	<!-- 					<li> -->
	<!-- 						<p>진행중 업무 <span>4</span></p> -->
	<!-- 						<a href="javascript:;">완료된업무보기 <span>2</span></a> -->
	<!-- 					</li> -->
	<!-- 					<li class="graph"></li> -->
	<!-- 				</ul> -->
	<!-- 			</div> -->
	<!-- 			<div class="workList_content"> -->
	<!-- 				<div class="wkList_item"> -->

	<!-- 				</div> -->
	<!-- 			</div> -->
	<!-- 		</div> -->
	<!-- 	</div> -->
	<!-- </section> -->
	
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