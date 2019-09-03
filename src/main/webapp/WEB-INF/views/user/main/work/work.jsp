
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- date picker resource-->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

<!-- Sortable resource-->
<script src="/js/Sortable.js"></script>

<script>
	function commentPagination(wps_wrk_id, page, pageSize) {
		$
				.ajax({
					url : "/comment",
					method : "post",
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					data : "wps_wrk_id=" + wps_wrk_id + "&page=" + page
							+ "&pageSize=" + pageSize,
					success : function(data) {
						var html = "";

						data.commentList
								.forEach(function(comm, index) {
									console.log(data);
									html += "<tr class='commTr'>";
									html += "<input type='hidden' name='commContent' value='"+comm.comm_content +"'/>"
									html += "<input type='hidden' name='commComm_id' value='"+comm.comm_id +"'/>"
									html += "<input type='hidden' name='commPrj_id' value='"+comm.prj_id +"'/>"
									html += "<td>" + comm.comm_content
											+ "</td>";
									html += "<td>" + comm.user_email + "</td>";
									html += "<td>" + comm.commDateStr + "</td>";
									html += "<td>";
									html += "<input type='hidden' id='prj_id02' value='"+ comm.prj_id +"'/>"
									html += "<input type='hidden' id='comm_id02' value='"+comm.comm_id +"'/>"
									if (comm.user_email == data.user_email) {
										html += "<button type='button' id='commUpdateBtn' class='commUpdateBtn'>수정</button>"
									}
									html += "</td>";
									html += "<td class='commDeleteTd'>";
									html += "<input type='hidden' value='"+comm.prj_id +"'/>"
									if (comm.user_email == data.user_email) {
										html += "<button type='button' id='commDeleteBtn' class='commDeleteBtn' name='"+comm.comm_id+"'>삭제</button>"
									}
									html += "</td>";
									html += "</tr>";

								});

						var pHtml = "";
						var pageVo = data.pageVo;

						if (pageVo.page == 1)
							pHtml += "<li class='disabled'><span>«<span></li>";
						else
							pHtml += "<li><a href='javascript:commentPagination("
									+ wps_wrk_id
									+ ","
									+ (pageVo.page - 1)
									+ ", " + pageVo.pageSize + ");'>«</a></li>";
						for (var i = 1; i <= data.commPageSize; i++) {
							if (pageVo.page == i)
								pHtml += "<li class='active'><span>" + i
										+ "</span></li>";
							else
								pHtml += "<li><a href='javascript:commentPagination("
										+ wps_wrk_id
										+ ","
										+ i
										+ ", "
										+ pageVo.pageSize
										+ ");'>"
										+ i
										+ "</a></li>";
						}
						if (pageVo.page == data.commPageSize)
							pHtml += "<li class='disabled'><span>»<span></li>";
						else
							pHtml += "<li><a href='javascript:commentPagination("
									+ wps_wrk_id
									+ ","
									+ (pageVo.page + 1)
									+ ", " + pageVo.pageSize + ");'>»</a></li>";

						$(".pagination").html(pHtml);
						$("#commBody").html(html);
					}
				})
	}
	function commentInsert(wps_wrk_id, wps_wrk_nm, content, page, pageSize) {
		$.ajax({
			url : "/commentInsert",
			method : "post",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			data : "wps_wrk_id=" + wps_wrk_id + "&wps_wrk_nm=" + wps_wrk_nm
					+ "&comm_content=" + content + "&page=" + page
					+ "&pageSize=" + pageSize,
			success : function(data) {
				if (socket) {
					var socketMsg = "";
					for (var i = 0; i < data.data.length; i++) {
						socketMsg = "wrk_comment," + data.data[i].user_email
								+ "," + data.data3.wrk_nm;
						socket.send(socketMsg);
					}

					for (var i = 0; i < data.data2.length; i++) {
						socketMsg = "wrk_comment," + data.data2[i].user_email
								+ "," + data.data3.wrk_nm;
						socket.send(socketMsg);
					}
					// websocket에 보내기!!
				}
				commentPagination(wps_wrk_id, 1, 10);
			}
		})
	}
	$(document)
			.ready(
					function() {

						$("#selectProject option[id='${PROJECT_INFO.prj_id}']")
								.attr("selected", "selected");

						var authText = "";

						if ("${PROJECT_INFO.prj_auth}" == "ASC01") {
							authText = "전체";
						} else if ("${PROJECT_INFO.prj_auth}" == "ASC02") {
							authText = "제한";
						} else if ("${PROJECT_INFO.prj_auth}" == "ASC03") {
							authText = "통제";
						}

						$(".auth_txt span").text(authText);

						// 업무 코멘트 시작점
						// 업무코멘트 수정
						$(".workTab").on("click", "#commentId", function() {
							var wps_wrk_id = $('#wps_wrk_id').val();
							commentPagination(wps_wrk_id, 1, 10);
						})

						$("#commBody")
								.on(
										"click",
										".commUpdateBtn",
										function() {
											var commprid = $(this).siblings(
													"input").val();

											var inq_id = $(this).parent()
													.prev().prev().prev()
													.text();
											var inq_trim = $.trim(inq_id);
											$(this)
													.parent()
													.prev()
													.prev()
													.prev()
													.replaceWith(
															"<td><input type='text' name='updateComm' id='changeInput' value='"+inq_trim+"'/></td>");
											$(this)
													.replaceWith(
															"<button type='button' id='commUpdateChgBtn' class='commUpdateChgBtn'>수정완료</button>");

											$("#commBody")
													.on(
															"click",
															"#commUpdateChgBtn",
															function() {
																var changVal = $(
																		"#changeInput")
																		.val();

																var inq_trim02 = $
																		.trim(changVal);
																var prj_id = $(
																		this)
																		.siblings(
																				"#prj_id02")
																		.val();
																var comm_id = $(
																		this)
																		.siblings(
																				"#comm_id02")
																		.val();

																updateTest(
																		inq_trim02,
																		prj_id,
																		comm_id);
																$(this)
																		.parent()
																		.prev()
																		.prev()
																		.prev()
																		.replaceWith(
																				"<td><p>"
																						+ inq_trim02
																						+ "</p></td>");
																$(this)
																		.replaceWith(
																				"<button type='button' id='commUpdateBtn' class='commUpdateBtn'>수정</button>");
															})
										})

						function updateTest(inq_trim02, prj_id, comm_id) {
							$
									.ajax({
										url : "/commUpdate",
										method : "get",
										contentType : "application/x-www-form-urlencoded; charset=UTF-8",
										data : "inq_trim=" + inq_trim02
												+ "&prj_id=" + prj_id
												+ "&comm_id=" + comm_id,
										success : function(data) {

										}
									})
						}

						// 코멘트 삭제하기
						$("#commBody").on("click", "#commDeleteBtn",
								function() {
									var wps_wrk_id = $('#wps_wrk_id').val();
									var commDelete = $(this).attr("name");
									var comm_id = commDelete;

									commDeleteAjax(wps_wrk_id, comm_id);
								})

						function commDeleteAjax(wps_wrk_id, comm_id) {
							$
									.ajax({
										url : "/commDelete",
										method : "post",
										contentType : "application/x-www-form-urlencoded; charset=UTF-8",
										data : "wps_wrk_id=" + wps_wrk_id
												+ "&comm_id=" + comm_id,
										success : function(data) {
											commentPagination(wps_wrk_id, 1, 10);

										}
									})
						}

						// 댓글등록하기 버튼
						$("#replyBtn").on(
								"click",
								function() {
									var wps_wrk_id = $('#wps_wrk_id').val();
									var wps_wrk_nm = $('#wps_nm').val();
									var content = $('#comm_content').val();
									commentInsert(wps_wrk_id, wps_wrk_nm,
											content, 1, 10);
								})

						// -------------------------업무코멘트 종료지점 -----------------------------------------

						var projectAuth = "${PROJECT_INFO.prj_auth}";
						var projectMemLevel = "${userInfo.prj_mem_lv}";

						//업무리스트, 업무 이동 function
						sortable();

						$(".workList_set ").hide();

						//업무리스트 설정 아이콘클릭시 삭제 버튼 보여주기
						$("#workListBox").on("click", ".workList_set_i",
								function() {
									$(this).next(".workList_set").fadeIn(300);
								});

						//업무리스트 삭제 버튼에서 마우스가 떠났을때 삭제버튼 사라지게 하기
						$("#workListBox").on("mouseleave", ".workList_set",
								function() {
									$(this).fadeOut(300);
								});

						//업무리스트 추가 버튼 클릭시
						$(".workListAdd")
								.on(
										"click",
										"#btnWorkList",
										function() {
											var html = "";
											html += "<input type='text' value='' id='workListName'>";

											//workListAdd input의 개수를 파악해 텍스트 창 한개만 만들기
											var len = $(".workListAdd input").length;
											if (len < 2) {
												$(".workListAdd").append(html);
											}

											//현재 버튼을 취소 버튼으로 변경하고 id도 변경
											$(this).attr("id",
													"btnCancelWorklist");
											$(this).attr("value", "취소");

										});

						//업무리스트 취소 버튼 클릭시
						$(".workListAdd").on("click", "#btnCancelWorklist",
								function() {
									$(this).attr("id", "btnWorkList");
									$(this).attr("value", "업무리스트 추가");
									//업무리스트 이름 입력하는 창 없애기
									$("#workListName").remove();
								});

						//업무리스트 이름 입력 후 엔터 또는 다른곳 클릭시 업무리스트 추가하는 ajax실행 
						$(".workListAdd").on("keydown change", "#workListName",
								function(key) {
									if (key.keyCode == 13) {
										var workListNm = $(this).val();
										workListAddAjax(workListNm);
										$(this).val("").focus();
									}
								});

						//업무리스트 추가
						function workListAddAjax(workListNm) {
							$
									.ajax({
										url : "/work/workListAddAjax",
										method : "post",
										data : "wrk_lst_nm=" + workListNm,
										success : function(data) {
											var html = "";
											data.workList
													.forEach(function(workList,
															index) {

														html += "<div class='workList' id='"+ workList.wrk_lst_id + "'><span class='handle'>+++</span><div class='workList_hd'><dl>"
														html += "<dt><input type='text' value='"+ workList.wrk_lst_nm+"' id='"+workList.wrk_lst_id+"' class='wrkListName'></dt><dd>"
														html += "<input type='button' class='workList_add_i' value='새업무 추가'>"
														html += "<a href='javascript:;' class='workList_set_i'>업무리스트 설정</a>"
														html += "<div class='workList_set'><input type='button' id='btnWorkListDel_"+workList.wrk_lst_id+"' value='업무리스트 삭제'></div>"
														html += "</dd></dl><ul><li>"
														html += "<p>진행중 업무 <span></span></p>";
														html += "<p><a href='javascript:' class='btnComplete' id='"+workList.wrk_lst_id+"'>완료된업무보기</a><span></span></p></li>";
														html += "<li class='graph'></li></ul><div class='workCreateBox'>";
														html += "<textarea name='wrk_nm' id='wrk_nm' placeholder='업무 이름을 입력해 주세요. 업무 이름은 70자 이내로 입력해 주세요'></textarea>";
														html += "<div class='workCreatebtnBox'>";
														html += "<input type='button' value='취소' id='wrkCreateCancelBtn'>";
														html += "<input type='button' value='만들기' id='wrkCreateBtn' class='wrkCreateBtn' name='"+workList.wrk_lst_id+"' disabled='disabled'>";
														html += "</div></div>";
														html += "</div>";
														html += "<div class='workWrap'><div class='working list n1' id='"+workList.wrk_lst_id+"'>"
														data.works
																.forEach(function(
																		work,
																		index2) {
																	if (work.wrk_lst_id == workList.wrk_lst_id
																			&& work.wrk_cmp_fl == 'N') {
																		html += "<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work.wrk_id+"' class='workListItem'>";
																		html += "<div class='checkList etrans workCheck'>"
																		html += "<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl_"+work.wrk_id+"' class='wrk_cmp_fl'>"
																		html += "<label for='wrk_cmp_fl_"+work.wrk_id+"'></label></div>"
																		html += "<button type='button' id='workDelBtn' class='workDelBtn'>업무삭제</button>";
																		html += "<h2 class='wrk_title'><span>"
																				+ work.wrk_grade
																				+ "</span>"
																				+ work.wrk_nm
																				+ "</h2>";
																		html += "<ul>";
																		html += "<li class='wrk_data'>"
																				+ work.wrkStartDtStr
																				+ " ~ "
																				+ work.wrkEndDtStr
																				+ "</li>";
																		html += "<li><span></span><span></span></li>";
																		html += "</ul>";
																		html += "<div class='wrk_mem_flw'><dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd></dl>";
																		html += "<dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd>";
																		html += "</dl>";
																		html += "</div></div>";
																	}
																});
														html += "</div>";
														html += "<div class='complete_"+workList.wrk_lst_id+" list n1' id='"+workList.wrk_lst_id+"'>"
														data.works
																.forEach(function(
																		work1,
																		index3) {
																	if (work1.wrk_lst_id == workList.wrk_lst_id
																			&& work1.wrk_cmp_fl == 'Y') {
																		html += "<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work1.wrk_id+"' class='workListItem'>";
																		html += "<div class='checkList etrans workCheck'>"
																		html += "<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl_"+work.wrk_id+"' class='wrk_cmp_fl' checked>"
																		html += "<label for='wrk_cmp_fl_"+work.wrk_id+"'></label></div>"
																		html += "<button type='button' id='workDelBtn' class='workDelBtn'>업무삭제</button>";
																		;
																		html += "<h2 class='wrk_title'><span>"
																				+ work1.wrk_grade
																				+ "</span>"
																				+ work1.wrk_nm
																				+ "</h2>";
																		html += "<ul>";
																		html += "<li>"
																				+ work1.wrkStartDtStr
																				+ " ~ "
																				+ work1.wrkEndDtStr
																				+ "</li>";
																		html += "<li><span></span><span></span></li>";
																		html += "</ul>";
																		html += "<div class='wrk_mem_flw'><dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd></dl>";
																		html += "<dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd>";
																		html += "</dl>";
																		html += "</div></div>";
																	}
																});
														html += "</div></div>";
														html += "</div>";

														$("#workListBox").html(
																html);
														sortable();
													});
										}
									});
						}

						//업무리스트 삭제 버튼 클릭시
						$("#workListBox").on("click",
								".workList_set input[type=button]", function() {
									var idText = $(this).attr("id").split("_");
									var workListID = idText[1];

									workListDelAjax(workListID);
								});

						//업무리스트 삭제
						function workListDelAjax(workListID) {
							$
									.ajax({
										url : "/work/workListDelAjax",
										method : "post",
										data : "wrk_lst_id=" + workListID,
										success : function(data) {
											if (data.noResult == "") {
												$(".ctxt")
														.text(
																"해당 업무리스트에 업무가 존재 합니다.");
												layer_popup("#layer2");
												return false;
											} else {
												var html = "";
												data.workList
														.forEach(function(
																workList, index) {
															html += "<div class='workList' id='"+ workList.wrk_lst_id + "'><span class='handle'>+++</span><div class='workList_hd'><dl>"
															html += "<dt><input type='text' value='"+ workList.wrk_lst_nm+"' id='"+workList.wrk_lst_id+"' class='wrkListName'></dt><dd>"
															html += "<input type='button' class='workList_add_i' value='새업무 추가'>"
															html += "<a href='javascript:;' class='workList_set_i'>업무리스트 설정</a>"
															html += "<div class='workList_set'><input type='button' id='btnWorkListDel_"+workList.wrk_lst_id+"' value='업무리스트 삭제'></div>"
															html += "</dd></dl><ul><li>"
															html += "<p>진행중 업무 <span>4</span></p>";
															html += "<p><a href='javascript:' class='btnComplete' id='"+workList.wrk_lst_id+"'>완료된업무보기</a><span>2</span></p></li>";
															html += "<li class='graph'></li></ul><div class='workCreateBox'>";
															html += "<textarea name='wrk_nm' id='wrk_nm' placeholder='업무 이름을 입력해 주세요. 업무 이름은 70자 이내로 입력해 주세요'></textarea>";
															html += "<div class='workCreatebtnBox'>";
															html += "<input type='button' value='취소' id='wrkCreateCancelBtn'>";
															html += "<input type='button' value='만들기' id='wrkCreateBtn' class='wrkCreateBtn' name='"+workList.wrk_lst_id+"' disabled='disabled'>";
															html += "</div></div>";
															html += "</div>";
															html += "<div class='workWrap'><div class='working list n1' id='"+workList.wrk_lst_id+"'>"
															data.works
																	.forEach(function(
																			work,
																			index2) {
																		if (work.wrk_lst_id == workList.wrk_lst_id
																				&& work.wrk_cmp_fl == 'N') {
																			html += "<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work.wrk_id+"' class='workListItem'>";
																			html += "<div class='checkList etrans workCheck'>"
																			html += "<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl_"+work.wrk_id+"' class='wrk_cmp_fl'>"
																			html += "<label for='wrk_cmp_fl_"+work.wrk_id+"'></label></div>"
																			html += "<button type='button' id='workDelBtn' class='workDelBtn'>업무삭제</button>";
																			html += "<h2 class='wrk_title'><span>"
																					+ work.wrk_grade
																					+ "</span>"
																					+ work.wrk_nm
																					+ "</h2>";
																			html += "<ul>";
																			html += "<li class='wrk_data'>"
																					+ work.wrkStartDtStr
																					+ " ~ "
																					+ work.wrkEndDtStr
																					+ "</li>";
																			html += "<li><span></span><span></span></li>";
																			html += "</ul>";
																			html += "<div class='wrk_mem_flw'><dl class='wrk_mem'>";
																			html += "<dt></dt>";
																			html += "<dd>";
																			html += "<p></p>";
																			html += "</dd></dl>";
																			html += "<dl class='wrk_mem'>";
																			html += "<dt></dt>";
																			html += "<dd>";
																			html += "<p></p>";
																			html += "</dd>";
																			html += "</dl>";
																			html += "</div></div>";
																		}
																	});
															html += "</div>";
															html += "<div class='complete_"+workList.wrk_lst_id+" list n1' id='"+workList.wrk_lst_id+"'>"
															data.works
																	.forEach(function(
																			work1,
																			index3) {
																		if (work1.wrk_lst_id == workList.wrk_lst_id
																				&& work1.wrk_cmp_fl == 'Y') {
																			html += "<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work1.wrk_id+"' class='workListItem'>";
																			html += "<div class='checkList etrans workCheck'>"
																			html += "<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl_"+work.wrk_id+"' class='wrk_cmp_fl' checked>"
																			html += "<label for='wrk_cmp_fl_"+work.wrk_id+"'></label></div>"
																			html += "<button type='button' id='workDelBtn' class='workDelBtn'>업무삭제</button>";
																			html += "<h2 class='wrk_title'><span>"
																					+ work1.wrk_grade
																					+ "</span>"
																					+ work1.wrk_nm
																					+ "</h2>";
																			html += "<ul>";
																			html += "<li class='wrk_data'>"
																					+ work1.wrkStartDtStr
																					+ " ~ "
																					+ work1.wrkEndDtStr
																					+ "</li>";
																			html += "<li><span></span><span></span></li>";
																			html += "</ul>";
																			html += "<div class='wrk_mem_flw'><dl class='wrk_mem'>";
																			html += "<dt></dt>";
																			html += "<dd>";
																			html += "<p></p>";
																			html += "</dd></dl>";
																			html += "<dl class='wrk_mem'>";
																			html += "<dt></dt>";
																			html += "<dd>";
																			html += "<p></p>";
																			html += "</dd>";
																			html += "</dl>";
																			html += "</div></div>";
																		}
																	});
															html += "</div></div>";
															html += "</div>";

															$("#workListBox")
																	.html(html);
															sortable();
														});
											}

										}
									});
						}

						//업무리스트 이름 수정
						$("#workListBox").on(
								"change",
								".wrkListName",
								function() {
									var wrkListName = $(this).val();
									var wrkListId = $(this).attr("id");

									if (!wrkListName) {
										$(".ctxt").text("이름을 입력해 주세요.");
										layer_popup("#layer2");
										return false;
									}

									workListNameUpdateAjax(wrkListName,
											wrkListId);
								});

						//업무리스트 이름 수정하는 ajax
						function workListNameUpdateAjax(wrkListName, wrkListId) {
							$.ajax({
								url : "/work/workListNameUpdateAjax",
								method : "post",
								data : "wrk_lst_nm=" + wrkListName
										+ "&wrk_lst_id=" + wrkListId,
								success : function(data) {
									$(".ctxt").text("이름이 변경 되었습니다.");
									layer_popup("#layer2");
									return false;
								}
							});
						}

						//업무 추가 버튼 클릭시
						$("#workListBox").on(
								"click",
								".workList_add_i",
								function() {
									var workCreateArea = $(this).parent()
											.parent().next().next();
									$(".workCreateBox").fadeOut(0);
									workCreateArea.fadeIn(300);
								});

						//업무 생성 textarea에 텍스트를 썼을 때
						$("#workListBox").on(
								"keydown",
								"#wrk_nm",
								function() {
									var workName = $(this).val();
									if (workName.length > 1) {
										$(this).next()
												.children("#wrkCreateBtn")
												.prop("disabled", false);
									} else {
										$(this).next()
												.children("#wrkCreateBtn")
												.prop("disabled", true);
									}
								});

						//업무 만들기 버튼 클릭 했을 때
						$("#workListBox")
								.on(
										"click",
										"#wrkCreateBtn",
										function() {
											var workName = $(this).parent()
													.prev().val();
											var workLstID = $(this)
													.attr("name");

											wrkCreateAjax(workLstID, workName);

											if (socket) {
												var socketMsg = "videoNotify,"
														+ "프로젝트 'STTM'에서 업무가 추가가 되었습니다."
														+ "," + "son@naver.com";
												socket.send(socketMsg);
												// websocket에 보내기!!
											}
										});

						//진행중 n, y끝난거?
						var nCnt = "";
						var yCnt = "";

						function wrkCreateAjax(workLstID, workName) {
							$
									.ajax({
										url : "/work/wrkCreateAjax",
										method : "post",
										data : "wrk_lst_id=" + workLstID
												+ "&wrk_nm=" + workName,
										success : function(data) {
											var html = "";
											data.workList
													.forEach(function(workList,
															index) {
														html += "<div class='workList' id='"+ workList.wrk_lst_id + "'><span class='handle'>+++</span><div class='workList_hd'><dl>"
														html += "<dt><input type='text' value='"+ workList.wrk_lst_nm+"' id='"+workList.wrk_lst_id+"' class='wrkListName'></dt><dd>"
														html += "<input type='button' class='workList_add_i' value='새업무 추가'>"
														html += "<a href='javascript:;' class='workList_set_i'>업무리스트 설정</a>"
														html += "<div class='workList_set'><input type='button' id='btnWorkListDel_"+workList.wrk_lst_id+"' value='업무리스트 삭제'></div>"
														html += "</dd></dl><ul><li>"
														html += "<p>진행중 업무 <span>4</span></p>";
														html += "<p><a href='javascript:' class='btnComplete' id='"+workList.wrk_lst_id+"'>완료된업무보기</a><span>2</span></p></li>";
														html += "<li class='graph'></li></ul><div class='workCreateBox'>";
														html += "<textarea name='wrk_nm' id='wrk_nm' placeholder='업무 이름을 입력해 주세요. 업무 이름은 70자 이내로 입력해 주세요'></textarea>";
														html += "<div class='workCreatebtnBox'>";
														html += "<input type='button' value='취소' id='wrkCreateCancelBtn'>";
														html += "<input type='button' value='만들기' id='wrkCreateBtn' class='wrkCreateBtn' name='"+workList.wrk_lst_id+"' disabled='disabled'>";
														html += "</div></div>";
														html += "</div>";
														html += "<div class='workWrap'><div class='working list n1' id='"+workList.wrk_lst_id+"'>"
														data.works
																.forEach(function(
																		work,
																		index2) {
																	if (work.wrk_lst_id == workList.wrk_lst_id
																			&& work.wrk_cmp_fl == 'N') {
																		// 		 							nCnt = work.length;
																		// 		 							console.log(work.);
																		html += "<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work.wrk_id+"' class='workListItem'>";
																		html += "<div class='checkList etrans workCheck'>"
																		html += "<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl_"+work.wrk_id+"' class='wrk_cmp_fl'>"
																		html += "<label for='wrk_cmp_fl_"+work.wrk_id+"'></label></div>"
																		html += "<button type='button' id='workDelBtn' class='workDelBtn'>업무삭제</button>";
																		html += "<h2 class='wrk_title'><span>"
																				+ work.wrk_grade
																				+ "</span>"
																				+ work.wrk_nm
																				+ "</h2>";
																		html += "<ul>";
																		html += "<li class='wrk_data'>"
																				+ work.wrkStartDtStr
																				+ " ~ "
																				+ work.wrkEndDtStr
																				+ "</li>";
																		html += "<li><span></span><span></span></li>";
																		html += "</ul>";
																		html += "<div class='wrk_mem_flw'><dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd></dl>";
																		html += "<dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd>";
																		html += "</dl>";
																		html += "</div></div>";
																	}
																});
														html += "</div>";
														html += "<div class='complete_"+workList.wrk_lst_id+" list n1' id='"+workList.wrk_lst_id+"'>"
														data.works
																.forEach(function(
																		work1,
																		index3) {
																	if (work1.wrk_lst_id == workList.wrk_lst_id
																			&& work1.wrk_cmp_fl == 'Y') {
																		// 		 							yCnt = work1.length;
																		console
																				.log(data.works);
																		html += "<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work1.wrk_id+"' class='workListItem'>";
																		html += "<div class='checkList etrans workCheck'>"
																		html += "<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl_"+work.wrk_id+"' class='wrk_cmp_fl' checked>"
																		html += "<label for='wrk_cmp_fl_"+work.wrk_id+"'></label></div>"
																		html += "<button type='button' id='workDelBtn' class='workDelBtn'>업무삭제</button>";
																		html += "<h2 class='wrk_title'><span>"
																				+ work1.wrk_grade
																				+ "</span>"
																				+ work1.wrk_nm
																				+ "</h2>";
																		html += "<ul>";
																		html += "<li class='wrk_data'>"
																				+ work1.wrkStartDtStr
																				+ " ~ "
																				+ work1.wrkEndDtStr
																				+ "</li>";
																		html += "<li><span></span><span></span></li>";
																		html += "</ul>";
																		html += "<div class='wrk_mem_flw'><dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd></dl>";
																		html += "<dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd>";
																		html += "</dl>";
																		html += "</div></div>";
																	}
																});
														html += "</div></div>";
														html += "</div>";

														$("#workListBox").html(
																html);
														sortable();
													});
										}
									});
						}

						//업무 만들기 취소 버튼 클릭했을 때
						$("#workListBox").on("click", "#wrkCreateCancelBtn",
								function() {
									$(this).parent().parent().fadeOut(300);
								});

						//업무를 드래그 이동 했을 때
						$("#workListBox").on("draggable", ".list div",
								function(event, drag) {
								})

						//업무 리스트, 업무 이동 script
						function sortable() {
							new Sortable(
									document.getElementById('workListBox'), {
										handle : '.handle',
										animation : 150
									});
							var elements = document
									.getElementsByClassName('list');
							for (var i = 0; i < elements.length; i++) {

								//프로젝트 멤버 레벨이 1이고 프로젝트 권한이 제한 이거나 프로젝트 멤버 레벨이 1이고 프로젝트 권한이 통제 일 때 업무 이동 안됨.
								if (projectMemLevel == 'LV1'
										&& projectAuth == 'ASC02'
										|| projectMemLevel == 'LV1'
										&& projectAuth == 'ASC03') {
									new Sortable(elements[i], {
										disabled : true
									});
								} else {
									new Sortable(
											elements[i],
											{
												group : 'shared',
												invertSwap : true,
												animation : 150,

												//드래그가 끝났을 때
												onEnd : function(evt) {
													var itemEl = evt.item; // dragged HTMLElement

													//이동하려는 업무의 아이디
													var wrkID = itemEl
															.getAttribute('data-wrkid');
													//옮기려는 업무리스트 아이디
													var wrkListToID = evt.to
															.getAttribute('id');

													wrkTransAjax(wrkID,
															wrkListToID);

													// 업무이동시 변경되었다고 알림메세지 보내기
													console.log("업무이도오안료");
													if (socket) {
														var socketMsg = "videoNotify,"
																+ "프로젝트 'STTM'에서 업무가 추가가 되었습니다."
																+ ","
																+ "son@naver.com";
														socket.send(socketMsg);
														// websocket에 보내기!!
													}

												}
											});
								}

							}
						}

						//업무 리스트
						function wrkTransAjax(wrkID, wrkListToID) {
							$.ajax({
								url : "/work/wrkTransAjax",
								method : "post",
								data : "wrk_id=" + wrkID + "&wrk_lst_id="
										+ wrkListToID,
								success : function(data) {
									//이동시 바로 이동되기 때문에 별다른 태그 추가 없음
								}
							});
						}
						;

						//업무 클릭시 업무 설정 레이어 열기
						$("#workListBox").on(
								"click",
								".workListItem h2",
								function() {
									$("#propertyWorkSet").animate({
										right : '0'
									}, 500);

									$(".workTab ul.tabs li").removeClass(
											"active").css({
										background : "#fff",
										color : "#2e3f57"
									});
									$(".workTab ul.tabs li:nth-child(1)").css({
										background : "#dee2e7"
									});
									$(".tab_content").fadeOut(0);
									$("#tab1").fadeIn(0);

									//업무 아이디를 변수에 담음
									var wrk_id = $(this).parent().attr(
											"data-wrkid");
									$("#wps_wrk_id").val(wrk_id);

									propertyWorkSetAjax(wrk_id);
								});

						//프로젝트 닫기 버튼을 클릭했을 때
						$(".btnSetClose").on(
								"click",
								function() {
									$("#propertyWorkSet").animate({
										right : '-700px'
									}, 500);
									if (socket) {
										var socketMsg = "videoNotify,"
												+ "프로젝트 'STTM'에서 업무가 수정 되었습니다."
												+ "," + "son@naver.com";
										socket.send(socketMsg);
										// websocket에 보내기!!
									}
								});

						function propertyWorkSetAjax(wrk_id) {
							$
									.ajax({
										url : "/work/propertyWorkSetAjax",
										method : "post",
										data : "wrk_id=" + wrk_id,
										success : function(data) {
											var auth = data.workVo.auth;
											$("#wps_id")
													.val(data.workVo.wrk_id);
											$("#wps_nm")
													.val(data.workVo.wrk_nm);
											$("#wps_write_nm").text(
													data.workVo.user_nm);
											$("#wps_write_date").text(
													data.workVo.wrkDtStr);
											$("#wps_start_date")
													.val(
															data.workVo.wrkStartDtStr
																	+ " ~ "
																	+ data.workVo.wrkEndDtStr);
											$("#wrk_gd").val(
													data.workVo.wrk_grade);
											$(".wrk_color").removeClass(
													"colorSelect");

											$(data.workVo.wrk_color_cd).prev()
													.addClass("colorSelect");
											$(data.workVo.wrk_color_cd).prop(
													"checked", true);

											var html2 = "";
											if (data.getWrokPush != null) {
												html2 += "<p class='resDate' id='"+data.workVo.wrk_rv_id+"'>"
														+ data.getWrokPush.wrkDtStr
														+ " | <span class='pushDel'>삭제</span></p>";
												$(".notifyCon").html(html2);
												$("#resNotifyBtn").css({
													display : "none"
												});
											} else {
												html2 = "<input type='button' id='resNotifyBtn' value=예약알림추가'>";
												$(".notifyCon").html(html2);
												$(".resDate").css({
													display : "none"
												});
											}

											//배정된 업무 멤버 불러오기
											var html = "";
											data.wrkMemList
													.forEach(function(item,
															index) {
														html += "<li id='"+ item.user_email +"_"+item.wrk_id+"'>"
																+ item.user_nm
																+ "<input type='button' class='wrkMemDel' value='삭제'></li>";
													});
											$(".wrk_add_box").html(html);

											//업무 팔로워 멤버 불러오기
											var html = "";
											data.wrkFlwList
													.forEach(function(item,
															index) {
														html += "<li id='"+ item.user_email +"_"+item.wrk_id+"'>"
																+ item.user_nm
																+ "<input type='button' class='wrkFlwDel' value='삭제'></li>";
													});
											$(".wrk_mem_flw_box").html(html);

											//멤버레벨이 1인데 권한이 ASC02 또는 ASC03 일때
											if (auth == "AUTH02") {
												$(".propertySet input").prop(
														'readonly', true); //설정창의 모든 input readonly
												$(".propertySet select").prop(
														'disabled', true); //설정창의 모든 select disabled
												$(
														".propertySet button[name='wps_mem_set']")
														.prop('disabled', true); //설정창의 모든 button disabled
												$(
														".propertySet button[name='wrk_flw_set']")
														.prop('disabled', true); //설정창의 모든 button disabled
												$(
														".propertySet input[type=button]")
														.prop('disabled', true);
												$(".flatpickr-calendar").css({
													display : "none"
												});
											} else if (auth == "AUTH03") {
												$(".propertySet input").prop(
														'readonly', true); //설정창의 모든 input readonly
												$(".propertySet select").prop(
														'disabled', true); //설정창의 모든 select disabled
												$(".propertySet button").prop(
														'disabled', true); //설정창의 모든 button disabled
												$(
														".propertySet input[type=button]")
														.prop('disabled', true);
												$(
														".propertySet input[type=radio]")
														.prop('disabled', true);
												$(".flatpickr-calendar").css({
													display : "none"
												});
											}
											// 					else{
											// 						$(".propertySet input").prop('readonly', false);
											// 						$(".propertySet select").prop('disabled',false);
											// 						$(".propertySet button").prop('disabled', false);
											// 						$(".prj_add_box input").css({visibility:"visible"});
											// 						$(".prj_mem_add_box input").css({visibility:"visible"});
											// 						$(".propertySet input[type=button]").prop('disabled', false);
											// 						$(".datepicker").css({display:"block"});
											// 					}
										}
									});
						}

						//업무 삭제 클릭시
						$("#workListBox").on(
								"click",
								"#workDelBtn",
								function() {
									var wrkID = $(this).parent().attr(
											"data-wrkid");
									workDelAjax(wrkID);
								});

						function workDelAjax(wrkID) {
							$
									.ajax({
										url : "/work/workDelAjax",
										method : "post",
										data : "wrk_id=" + wrkID,
										success : function(data) {
											var html = "";
											data.workList
													.forEach(function(workList,
															index) {
														html += "<div class='workList' id='"+ workList.wrk_lst_id + "'><span class='handle'>+++</span><div class='workList_hd'><dl>"
														html += "<dt><input type='text' value='"+ workList.wrk_lst_nm+"' id='"+workList.wrk_lst_id+"' class='wrkListName'></dt><dd>"
														html += "<input type='button' class='workList_add_i' value='새업무 추가'>"
														html += "<a href='javascript:;' class='workList_set_i'>업무리스트 설정</a>"
														html += "<div class='workList_set'><input type='button' id='btnWorkListDel_"+workList.wrk_lst_id+"' value='업무리스트 삭제'></div>"
														html += "</dd></dl><ul><li>"
														html += "<p>진행중 업무 <span>4</span></p>";
														html += "<p><a href='javascript:' class='btnComplete' id='"+workList.wrk_lst_id+"'>완료된업무보기</a><span>2</span></p></li>";
														html += "<li class='graph'></li></ul><div class='workCreateBox'>";
														html += "<textarea name='wrk_nm' id='wrk_nm' placeholder='업무 이름을 입력해 주세요. 업무 이름은 70자 이내로 입력해 주세요'></textarea>";
														html += "<div class='workCreatebtnBox'>";
														html += "<input type='button' value='취소' id='wrkCreateCancelBtn'>";
														html += "<input type='button' value='만들기' id='wrkCreateBtn' class='wrkCreateBtn' name='"+workList.wrk_lst_id+"' disabled='disabled'>";
														html += "</div></div>";
														html += "</div>";
														html += "<div class='workWrap'><div class='working list n1' id='"+workList.wrk_lst_id+"'>"
														data.works
																.forEach(function(
																		work,
																		index2) {
																	if (work.wrk_lst_id == workList.wrk_lst_id
																			&& work.wrk_cmp_fl == 'N') {
																		html += "<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work.wrk_id+"' class='workListItem'>";
																		html += "<div class='checkList etrans workCheck'>"
																		html += "<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl_"+work.wrk_id+"' class='wrk_cmp_fl'>"
																		html += "<label for='wrk_cmp_fl_"+work.wrk_id+"'></label></div>"
																		html += "<button type='button' id='workDelBtn' class='workDelBtn'>업무삭제</button>";
																		html += "<h2 class='wrk_title'><span>"
																				+ work.wrk_grade
																				+ "</span>"
																				+ work.wrk_nm
																				+ "</h2>";
																		html += "<ul>";
																		html += "<li class='wrk_data'>"
																				+ work.wrkStartDtStr
																				+ " ~ "
																				+ work.wrkEndDtStr
																				+ "</li>";
																		html += "<li><span></span><span></span></li>";
																		html += "</ul>";
																		html += "<div class='wrk_mem_flw'><dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd></dl>";
																		html += "<dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd>";
																		html += "</dl>";
																		html += "</div></div>";
																	}
																});
														html += "</div>";
														html += "<div class='complete_"+workList.wrk_lst_id+" list n1' id='"+workList.wrk_lst_id+"'>"
														data.works
																.forEach(function(
																		work1,
																		index3) {
																	if (work1.wrk_lst_id == workList.wrk_lst_id
																			&& work1.wrk_cmp_fl == 'Y') {
																		html += "<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work1.wrk_id+"' class='workListItem'>";
																		html += "<div class='checkList etrans workCheck'>"
																		html += "<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl_"+work.wrk_id+"' class='wrk_cmp_fl' checked>"
																		html += "<label for='wrk_cmp_fl_"+work.wrk_id+"'></label></div>"
																		html += "<button type='button' id='workDelBtn' class='workDelBtn'>업무삭제</button>";
																		html += "<h2 class='wrk_title'><span>"
																				+ work1.wrk_grade
																				+ "</span>"
																				+ work1.wrk_nm
																				+ "</h2>";
																		html += "<ul>";
																		html += "<li class='wrk_data'>"
																				+ work1.wrkStartDtStr
																				+ " ~ "
																				+ work1.wrkEndDtStr
																				+ "</li>";
																		html += "<li><span></span><span></span></li>";
																		html += "</ul>";
																		html += "<div class='wrk_mem_flw'><dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd></dl>";
																		html += "<dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd>";
																		html += "</dl>";
																		html += "</div></div>";
																	}
																});
														html += "</div></div>";
														html += "</div>";

														$("#workListBox").html(
																html);
														sortable();

														$(".ctxt")
																.text(
																		"업무가 삭제 되었습니다.");
														layer_popup("#layer2");
														return false;
													});
										}
									});
						}

						//업무 클릭 했을 때 처음 보여주는 탭은 설정 탭
						$(".propertySetWrap div:nth-child(3)").show();

						$(".workTab ul.tabs li:nth-child(1)").css({
							background : "#dee2e7"
						});
						//탭 버튼 클릭시 div 변경
						$(".workTab").on(
								"click",
								"ul.tabs li",
								function() {
									$(".workTab ul.tabs li").removeClass(
											"active").css({
										background : "#fff",
										color : "#2e3f57"
									});
									$(this).addClass("active").css({
										background : "#dee2e7"
									});
									$(".tab_content").hide()
									var activeTab = $(this).attr("data-tab");
									$("#" + activeTab).fadeIn()
								});

						//완료된업무보기 클릭 했을 때
						$("#workListBox").on(
								"click",
								".btnComplete",
								function() {
									var id = $(this).attr("id");
									$(".complete_" + id).fadeIn(300);

									var text = $(this).text();
									$(this).text(
											text.replace('완료된업무보기',
													'완료된 업무 숨기기'));
									$(this).attr("class", "btnCompleteHide");
								});

						//완료된업무숨기기 클릭시
						$("#workListBox").on(
								"click",
								".btnCompleteHide",
								function() {
									var text = $(this).text();
									$(this).text(
											text.replace('완료된 업무 숨기기',
													'완료된업무보기'));
									var id = $(this).attr("id");
									$(".complete_" + id).fadeOut(300);
									$(this).attr("class", "btnComplete");
								});

						//업무 완료 체크 했을 떄
						$("#workListBox").on(
								"click",
								".wrk_cmp_fl",
								function() {
									var wrkID = $(this).parent().parent().attr(
											"data-wrkid");
									var wrkCMP = "";
									if ($(this).prop('checked')) {
										wrkCMP = "Y";
										completeCheckAjax(wrkID, wrkCMP);
									} else {
										wrkCMP = "N";
										completeCheckAjax(wrkID, wrkCMP);
									}
								});

						function completeCheckAjax(wrkID, wrkCMP) {
							$
									.ajax({
										url : "/work/completeCheckAjax",
										method : "post",
										data : "wrk_id=" + wrkID
												+ "&wrk_cmp_fl=" + wrkCMP,
										success : function(data) {
											// 					console.log(data);
											var html = "";
											data.workList
													.forEach(function(workList,
															index) {
														html += "<div class='workList' id='"+ workList.wrk_lst_id + "'><span class='handle'>+++</span><div class='workList_hd'><dl>"
														html += "<dt><input type='text' value='"+ workList.wrk_lst_nm+"' id='"+workList.wrk_lst_id+"' class='wrkListName'></dt><dd>"
														html += "<input type='button' class='workList_add_i' value='새업무 추가'>"
														html += "<a href='javascript:;' class='workList_set_i'>업무리스트 설정</a>"
														html += "<div class='workList_set'><input type='button' id='btnWorkListDel_"+workList.wrk_lst_id+"' value='업무리스트 삭제'></div>"
														html += "</dd></dl><ul><li>"
														html += "<p>진행중 업무 <span>4</span></p>";
														html += "<p><a href='javascript:' class='btnComplete' id='"+workList.wrk_lst_id+"'>완료된업무보기</a><span>2</span></p></li>";
														html += "<li class='graph'></li></ul><div class='workCreateBox'>";
														html += "<textarea name='wrk_nm' id='wrk_nm' placeholder='업무 이름을 입력해 주세요. 업무 이름은 70자 이내로 입력해 주세요'></textarea>";
														html += "<div class='workCreatebtnBox'>";
														html += "<input type='button' value='취소' id='wrkCreateCancelBtn'>";
														html += "<input type='button' value='만들기' id='wrkCreateBtn' class='wrkCreateBtn' name='"+workList.wrk_lst_id+"' disabled='disabled'>";
														html += "</div></div>";
														html += "</div>";
														html += "<div class='workWrap'><div class='working list n1' id='"+workList.wrk_lst_id+"'>"
														data.works
																.forEach(function(
																		work,
																		index2) {
																	if (work.wrk_lst_id == workList.wrk_lst_id
																			&& work.wrk_cmp_fl == 'N') {
																		html += "<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work.wrk_id+"' class='workListItem'>";
																		html += "<div class='checkList etrans workCheck'>"
																		html += "<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl_"+work.wrk_id+"' class='wrk_cmp_fl'>"
																		html += "<label for='wrk_cmp_fl_"+work.wrk_id+"'></label></div>"
																		html += "<button type='button' id='workDelBtn' class='workDelBtn'>업무삭제</button>";
																		html += "<h2 class='wrk_title'><span>"
																				+ work.wrk_grade
																				+ "</span>"
																				+ work.wrk_nm
																				+ "</h2>";
																		html += "<ul>";
																		html += "<li class='wrk_data'>"
																				+ work.wrkStartDtStr
																				+ " ~ "
																				+ work.wrkEndDtStr
																				+ "</li>";
																		html += "<li><span></span><span></span></li>";
																		html += "</ul>";
																		html += "<div class='wrk_mem_flw'><dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd></dl>";
																		html += "<dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd>";
																		html += "</dl>";
																		html += "</div></div>";
																	}
																});
														html += "</div>";
														html += "<div class='complete_"+workList.wrk_lst_id+" list n1' id='"+workList.wrk_lst_id+"'>"
														data.works
																.forEach(function(
																		work1,
																		index3) {
																	if (work1.wrk_lst_id == workList.wrk_lst_id
																			&& work1.wrk_cmp_fl == 'Y') {
																		html += "<div id='"+workList.wrk_lst_id+"' data-wrkid='"+work1.wrk_id+"' class='workListItem'>";
																		html += "<div class='checkList etrans workCheck'>"
																		html += "<input type='checkbox' name='wrk_cmp_fl' id='wrk_cmp_fl_"+work.wrk_id+"' class='wrk_cmp_fl' checked>"
																		html += "<label for='wrk_cmp_fl_"+work.wrk_id+"'></label></div>"
																		html += "<button type='button' id='workDelBtn' class='workDelBtn'>업무삭제</button>";
																		html += "<h2 class='wrk_title'><span>"
																				+ work1.wrk_grade
																				+ "</span>"
																				+ work1.wrk_nm
																				+ "</h2>";
																		html += "<ul>";
																		html += "<li class='wrk_data'>"
																				+ work1.wrkStartDtStr
																				+ " ~ "
																				+ work1.wrkEndDtStr
																				+ "</li>";
																		html += "<li><span></span><span></span></li>";
																		html += "</ul>";
																		html += "<div class='wrk_mem_flw'><dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd></dl>";
																		html += "<dl class='wrk_mem'>";
																		html += "<dt></dt>";
																		html += "<dd>";
																		html += "<p></p>";
																		html += "</dd>";
																		html += "</dl>";
																		html += "</div></div>";
																	}
																});
														html += "</div></div>";
														html += "</div>";

														$("#workListBox").html(
																html);
														sortable();
													});
										}
									});
						}

						var wrk_color = "";

						/* 여기서부터 업무 셋팅 업데이트를 위한 이벤트 핸들러 입니다. */
						$("#propertyWorkSet input, #propertyWorkSet select")
								.on(
										"propertychange keyup change click",
										function() {

											//프로젝트 셋팅 값 가져오기
											var id = $("#wps_id").val();
											var name = $("#wps_nm").val();
											var work_date = $("#wps_start_date")
													.val();
											var res_date = $("#wps_res_date")
													.val();
											var wrk_gd = $("#wrk_gd").val();

											var workSplit = work_date
													.split(" to ");
											var resSplit = res_date
													.split(" to ");

											var work_start_dt = workSplit[0];
											var work_end_dt = workSplit[1];

											if (!name) {
												$(".ctxt").text(
														"업무 이름을 작성해 주세요.");
												layer_popup("#layer2");
												return false;
											}

											if (typeof work_start_dt == "undefined"
													|| typeof work_end_dt == "undefined") {
												work_start_dt = "";
												work_end_dt = "";
											}

											var projectWorkSet = {
												id : id,
												name : name,
												work_start_dt : work_start_dt,
												work_end_dt : work_end_dt,
												wrk_gd : wrk_gd
											}

											propertyWorkSetItemAjax(projectWorkSet);
										});

						//업무 설정 업데이트 ajax
						function propertyWorkSetItemAjax(projectWorkSet) {
							$
									.ajax({
										url : "/work/propertyWorkSetItemAjax",
										method : "post",
										contentType : "application/x-www-form-urlencoded; charset=UTF-8",
										data : "wrk_id=" + projectWorkSet.id
												+ "&wrk_nm="
												+ projectWorkSet.name
												+ "&wrk_grade="
												+ projectWorkSet.wrk_gd
												+ "&wrk_start_dt="
												+ projectWorkSet.work_start_dt
												+ "&wrk_end_dt="
												+ projectWorkSet.work_end_dt,
										success : function(data) {
											$(".workListItem")
													.each(
															function() {
																var wrkID = $(
																		this)
																		.attr(
																				"data-wrkid");
																if (wrkID == projectWorkSet.id) {
																	$(this)
																			.find(
																					".wrk_title")
																			.html(
																					"<span>"
																							+ data.data.wrk_grade
																							+ "</span>"
																							+ data.data.wrk_nm);
																	$(this)
																			.find(
																					".wrk_date")
																			.text(
																					data.data.wrkStartDtStr
																							+ " ~ "
																							+ data.data.wrkEndDtStr);
																}
															});
										}
									});
						}
						//레이블 색상 선택 (라디오버튼 선택 / 선택해제)

						var beforeChecked = -1;

						$(".lableColor")
								.on(
										"click",
										"input[type=radio]",
										function(e) {
											var id = $("#wps_id").val();
											var index = $(this).prev().index(
													"label");
											$(this).prev().removeClass(
													"colorSelect");

											if (beforeChecked == index) {
												beforeChecked = -1;
												$(this).prop("checked", false);
												wrk_color = $(
														"input:radio[name='wrk_color_cd']:checked")
														.val();
												console.log(wrk_color);
											} else {
												beforeChecked = index;
												wrk_color = $(
														"input:radio[name='wrk_color_cd']:checked")
														.val();
												$(".wrk_color").removeClass(
														"colorSelect");
												$(this).prev().addClass(
														"colorSelect");
											}

											if (typeof wrk_color == "undefined") {
												wrk_color = "";
											} else {
												wrk_color = $(
														"input:radio[name='wrk_color_cd']:checked")
														.val();
											}

											propertyWorkLableColorAjax(id,
													wrk_color);
										});

						//업무 라벨 색상 변경
						function propertyWorkLableColorAjax(id, wrk_color) {
							console.log(wrk_color);
							var changeColor = encodeURIComponent(wrk_color);
							$
									.ajax({
										url : "/work/propertyWorkLableColorAjax",
										method : "post",
										contentType : "application/x-www-form-urlencoded; charset=UTF-8",
										data : "wrk_id=" + id
												+ "&wrk_color_cd="
												+ changeColor,
										success : function(data) {
											$(".workListItem")
													.each(
															function() {
																var wrkID = $(
																		this)
																		.attr(
																				"data-wrkid");
																if (wrkID == id) {
																	$(this)
																			.css(
																					{
																						borderTop : "3px solid"
																								+ wrk_color
																					});
																}
															});
										}
									});
						}

						//예약알림 버튼 클릭시
						$(".notifyCon").on("click", "#resNotifyBtn",
								function() {
									$(".notifyArea").fadeIn(100);
								});

						//예약알림 취소 버튼 클릭시
						$("#notificationCancelBtn").on("click", function() {
							$(".notifyArea").fadeOut(100);
						});

						//날짜 선택 - 예약알림
						$(".flatpickr1").flatpickr({
							inline : true,
							minDate : "today",
							enableTime : true
						});

						//날짜 선택 - 시작일, 종료일
						$(".flatpickr").flatpickr({
							mode : "range",
							minDate : "today",
							enableTime : true
						});

						$("#memClose").on("click", function() {
							$(".wrk_add_mem").fadeOut(300);
						});

						$("#flwClose").on("click", function() {
							$(".wrk_add_flw").fadeOut(300);
						});

						// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중

						$('#workLink').hide(0);

						$(".workTab").on("click", "#FL", function() {
							var wrk_id = $('#wps_wrk_id').val();
							workFilePagination(1, 5, wrk_id);
						})

						$(".tab_sub_menu").on("click", "#fileList", function() {
							var wrk_id = $('#wps_wrk_id').val();
							$('#locker').fadeIn(0);
							$('#workLink').fadeOut(0);
							$('#workFile').fadeIn(0);
							workFilePagination(1, 5, wrk_id);
						})

						$(".tab_sub_menu").on("click", "#linkList", function() {
							$('#locker').hide(0);
							var wrk_id = $('#wps_wrk_id').val();
							$('#workFile').fadeOut(0);
							$('#workLink').fadeIn(0);
							workLinkPagination(1, 5, wrk_id);
						})

						$("#workLink")
								.on(
										'click',
										'#uploadLink',
										function() {
											var attch_url = $('.link').val();
											var locker = $(
													"#locker input[type=radio]:checked")
													.val();
											var wrk_id = $('#wps_wrk_id').val();

											$('#box').val(locker);
											$('#work').val(wrk_id);

											$
													.ajax({
														url : "/workLinkUpload",
														type : "POST",
														data : "attch_url="
																+ attch_url
																+ "&locker="
																+ locker
																+ "&wrk_id="
																+ wrk_id,
														contentType : "application/x-www-form-urlencoded; charset=UTF-8",
														success : function(data) {
															workLinkPagination(
																	1, 5,
																	wrk_id);
														},
														error : function(e) {
															alert(e.reponseText);
														}
													});
										})

						$("#workFile")
								.on(
										'click',
										'#uploadFile',
										function() {

											var form = document
													.getElementById("frm");
											console.log(form);
											form.method = "POST";
											form.enctype = "multipart/form-data";

											var locker = $(
													"#locker input[type=radio]:checked")
													.val();
											var wrk_id = $('#wps_wrk_id').val();
											$('#box').val(locker);
											$('#work').val(wrk_id);
											var formData = new FormData(form);
											$(".ctxt").text("파일 업로드 완료!!!");
											layer_popup("#layer2");
											$
													.ajax({
														url : "/workFileUpload",
														type : "POST",
														data : formData,
														cache : false,
														contentType : false,
														processData : false,
														success : function(data) {
															workFilePagination(
																	1, 5,
																	wrk_id);

															if (socket) {
																var socketMsg = "";
																for (i = 0; i < data.wrkFlwList.length; i++) {
																	socketMsg = "file&link,"
																			+ data.wrkMemList[i].user_email
																			+ ","
																			+ data.workFileList.wrk_nm;
																	socket
																			.send(socketMsg);
																}

																for (var i = 0; i < data.wrkMemList.length; i++) {
																	socketMsg = "file&link,"
																			+ data.wrkFlwList[i].user_email
																			+ ","
																			+ data.workFileList.wrk_nm;
																	socket
																			.send(socketMsg);
																}
																// websocket에 보내기!!
															}
														},
														error : function(e) {
															alert(e.reponseText);
														}
													});
										});

						// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중

						//업무 멤버 추가하기 버튼 클릭시 해당 프로젝트 멤버 가져오기
						$(".wrk_add_mem").fadeOut(0); //멤버리스트 layer 숨기기
						$("#wps_mem_set").on("click", function() {
							var wrkID = $("#wps_id").val();
							$(".wrk_add_flw").fadeOut(0);
							$(".wrk_add_mem").fadeIn(300);
							workMemListAjax(wrkID);
						});

						//업무 멤버 가져오는 ajax
						function workMemListAjax(wrkID) {
							$
									.ajax({
										url : "/work/workMemListAjax",
										method : "post",
										data : "wrk_id=" + wrkID,
										success : function(data) {

											var html = "";
											data.projectMemList
													.forEach(function(item,
															index) {
														//html 생성
														html += "<li id='"+ item.user_email +"'><span>"
																+ item.user_nm
																+ "</span>"
																+ item.user_email
																+ "</li>";
													});
											$(".wrk_mem_item").html(html);
										}
									});
						}

						//업무 멤버 클릭 했을 때
						$(".wrk_mem_item").on("click", "li", function() {
							var mem_add_email = $(this).attr("id");
							var wrkID = $("#wps_id").val();
							workMemAddAjax(wrkID, mem_add_email);
						});

						//배정된 멤버로 선택한 멤버 추가
						function workMemAddAjax(wrkID, mem_add_email) {
							$
									.ajax({
										url : "/work/workMemAddAjax",
										method : "post",
										data : "wrk_id=" + wrkID
												+ "&user_email="
												+ mem_add_email,
										success : function(data) {
											console.log(data);

											var html = "";
											var html2 = "";
											var html3 = "";
											var html4 = "";

											data.wrkFlwList
													.forEach(function(item,
															index) {
														html += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"
																+ item.user_nm
																+ "<input type='button' class='memDel' value='삭제'></li>";
													});

											data.projectFlwList
													.forEach(function(item,
															index) {
														html2 += "<li id='"+ item.user_email +"'><span>"
																+ item.user_nm
																+ "</span>"
																+ item.user_email
																+ "</li>";
													});

											data.wrkMemList
													.forEach(function(item,
															index) {
														html3 += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"
																+ item.user_nm
																+ "<input type='button' class='memDel' value='삭제'></li>";
													});

											data.projectMemList
													.forEach(function(item,
															index) {
														html4 += "<li id='"+ item.user_email +"'><span>"
																+ item.user_nm
																+ "</span>"
																+ item.user_email
																+ "</li>";
													});

											$(".wrk_mem_flw_box").html(html);
											$(".wrk_flw_item_list").html(html2);
											$(".wrk_add_box").html(html3);
											$(".wrk_mem_item").html(html4);
										}
									});
						}

						//업무 배정된 멤버 삭제 클릭 했을 때
						$(".wrk_add_box").on(
								"click",
								"li input",
								function() {
									var textSplit = $(this).parent().attr("id")
											.split("_");
									var id = textSplit[1];
									var email = textSplit[0];
									workMemDelAjax(id, email);
								});

						function workMemDelAjax(id, email) {
							$
									.ajax({
										url : "/work/workMemDelAjax",
										method : "post",
										data : "wrk_id=" + id + "&user_email="
												+ email,
										success : function(data) {

											var html = "";
											var html2 = "";

											data.wrkMemList
													.forEach(function(item,
															index) {
														html += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"
																+ item.user_nm
																+ "<input type='button' class='memDel' value='삭제'></li>";
													});

											data.projectMemList
													.forEach(function(item,
															index) {
														html2 += "<li id='"+ item.user_email +"'><span>"
																+ item.user_nm
																+ "</span>"
																+ item.user_email
																+ "</li>";
													});

											$(".wrk_add_box").html(html);
											$(".wrk_mem_item").html(html2);
										}
									});
						}

						//팔로워 멤버 추가하기 버튼 클릭시 해당 프로젝트 멤버 가져오기
						$(".wrk_add_flw").fadeOut(0); //멤버리스트 layer 숨기기
						$("#wrk_flw_set").on("click", function() {
							var wrkID = $("#wps_id").val();
							$(".wrk_add_flw").fadeIn(300);
							$(".wrk_add_mem").fadeOut(0);
							workFlwListAjax(wrkID);
						});

						//팔로워 멤버 가져오는 ajax
						function workFlwListAjax(wrkID) {
							$
									.ajax({
										url : "/work/workFlwListAjax",
										method : "post",
										data : "wrk_id=" + wrkID,
										success : function(data) {
											var html = "";
											data.projectMemList
													.forEach(function(item,
															index) {
														//html 생성
														html += "<li id='"+ item.user_email +"'><span>"
																+ item.user_nm
																+ "</span>"
																+ item.user_email
																+ "</li>";
													});
											$(".wrk_flw_item_list").html(html);
										}
									});
						}

						//팔로워 멤버 클릭 했을 때
						$(".wrk_flw_item_list").on("click", "li", function() {
							var mem_add_email = $(this).attr("id");
							var wrkID = $("#wps_id").val();

							workFlwAddAjax(wrkID, mem_add_email);
						});

						//팔로우 멤버로 멤버 추가
						function workFlwAddAjax(wrkID, mem_add_email) {
							$
									.ajax({
										url : "/work/workFlwAddAjax",
										method : "post",
										data : "wrk_id=" + wrkID
												+ "&user_email="
												+ mem_add_email,
										success : function(data) {

											var html = "";
											var html2 = "";
											var html3 = "";
											var html4 = "";

											data.wrkFlwList
													.forEach(function(item,
															index) {
														html += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"
																+ item.user_nm
																+ "<input type='button' class='memDel' value='삭제'></li>";
													});

											data.projectFlwList
													.forEach(function(item,
															index) {
														html2 += "<li id='"+ item.user_email +"'><span>"
																+ item.user_nm
																+ "</span>"
																+ item.user_email
																+ "</li>";
													});

											data.wrkMemList
													.forEach(function(item,
															index) {
														html3 += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"
																+ item.user_nm
																+ "<input type='button' class='memDel' value='삭제'></li>";
													});

											data.projectMemList
													.forEach(function(item,
															index) {
														html4 += "<li id='"+ item.user_email +"'><span>"
																+ item.user_nm
																+ "</span>"
																+ item.user_email
																+ "</li>";
													});

											$(".wrk_mem_flw_box").html(html);
											$(".wrk_flw_item_list").html(html2);
											$(".wrk_add_box").html(html3);
											$(".wrk_mem_item").html(html4);
										}
									});
						}

						//업무 배정된 멤버 삭제 클릭 했을 때
						$(".wrk_mem_flw_box").on(
								"click",
								"li input",
								function() {
									var textSplit = $(this).parent().attr("id")
											.split("_");
									var id = textSplit[1];
									var email = textSplit[0];
									workFlwDelAjax(id, email);
								});

						//예약알림
						$("#notificationAddBtn").on(
								"click",
								function() {
									var notifyMem = $(
											"#notificationMem option:selected")
											.val();
									var notifyDate = $("#wps_res_date").val();
									var wrkID = $("#wps_id").val();

									notificationAddAjax(notifyMem, notifyDate,
											wrkID);
								});

						//예약알림 삭제 클릭시
						$(".notifyCon").on("click", ".pushDel", function() {
							var wrk_rv_id = $(this).parent().attr("id");
							notificationDelAjax(wrk_rv_id);
						});

					});

	//예약알림
	function notificationAddAjax(notifyMem, notifyDate, wrkID) {
		$
				.ajax({
					url : "/work/notificationAddAjax",
					method : "post",
					data : "wrk_id=" + wrkID + "&memType=" + notifyMem
							+ "&wrk_dt=" + notifyDate,
					success : function(data) {

						$(".notifyArea").fadeOut(100);
						$("#resNotifyBtn").css({
							display : "none"
						});

						var html = "<p class='resDate' id='"+data.getWrokPush.wrk_rv_id+"'>"
								+ data.getWrokPush.wrkDtStr
								+ " | <span class='pushDel'>삭제</span></p>";

						$(".notifyCon").html(html)
					}
				});
	};

	//예약알림삭제
	function notificationDelAjax(wrk_rv_id) {
		$
				.ajax({
					url : "/work/notificationDelAjax",
					method : "post",
					data : "wrk_rv_id=" + wrk_rv_id,
					success : function(data) {

						var html = "<input type='button' id='resNotifyBtn' value=예약알림추가'>";
						$(".resDate").css({
							display : "none"
						});
						$(".notifyCon").html(html)
					}
				});
	};

	//업무 배정된 멤버 삭제 클릭 했을 때
	function workFlwDelAjax(id, email) {
		$
				.ajax({
					url : "/work/workFlwDelAjax",
					method : "post",
					data : "wrk_id=" + id + "&user_email=" + email,
					success : function(data) {

						var html = "";
						var html2 = "";

						data.wrkFlwList
								.forEach(function(item, index) {
									html += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"
											+ item.user_nm
											+ "<input type='button' class='memDel' value='삭제'></li>";
								});

						data.projectMemList.forEach(function(item, index) {
							html2 += "<li id='"+ item.user_email +"'><span>"
									+ item.user_nm + "</span>"
									+ item.user_email + "</li>";
						});

						$(".wrk_mem_flw_box").html(html);
						$(".wrk_flw_item_list").html(html2);
					}
				});
	}

	function workFilePagination(page, pageSize, wrk_id) {
		$.ajax({
			url : "/workFilePagination",
			method : "post",
			data : "page=" + page + "&pageSize=" + pageSize + "&wrk_id="
					+ wrk_id,
			success : function(data) {
				//사용자 리스트
				var hhtml = "";
				var html = "";

				//hhtml생성
				hhtml += "<tr>";
				hhtml += "<th>파일명</th>";
				hhtml += "<th>공유한 멤버 이름</th>";
				hhtml += "<th>등록한 날짜</th>";
				hhtml += "<th>삭제</th>";
				hhtml += "</tr>";

				var user_email = data.user_email;
				data.workFileList.forEach(function(file, index) {
					//html생성
					html += "<tr id='filetr'>";
					html += "<td><a href='/fileDownLoad?file_id="
							+ file.file_id + "'>" + file.original_file_nm
							+ "</a></td>";
					html += "<td>" + file.user_nm + "</td>";
					html += "<td>" + file.prjStartDtStr + "</td>";
					if (file.user_email == user_email) {
						html += "<td><a href='javascript:workDelFile("
								+ file.file_id + "," + file.wrk_id
								+ ")'>삭제</a></td>";
					} else {
						html += "<td>삭제</td>";
					}
					html += "</tr>";
				});
				var pHtml = "";
				var pageVo = data.pageVo;

				if (pageVo.page == 1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a href='javascript:workFilePagination("
							+ (pageVo.page - 1) + ", " + pageVo.pageSize + ","
							+ wrk_id + ");'>«</a></li>";
				for (var i = 1; i <= data.paginationSize; i++) {
					if (pageVo.page == i)
						pHtml += "<li class='active'><span>" + i
								+ "</span></li>";
					else
						pHtml += "<li><a href='javascript:workFilePagination("
								+ i + ", " + pageVo.pageSize + "," + wrk_id
								+ ");'>" + i + "</a></li>";
				}
				if (pageVo.page == data.paginationSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:workFilePagination("
							+ (pageVo.page + 1) + ", " + pageVo.pageSize + ","
							+ wrk_id + ");'>»</a></li>";
				$(".pagination").html(pHtml);
				$("#publicHeader").html(hhtml);
				$("#publicList").html(html);
			}
		});
	}

	function workLinkPagination(page, pageSize, wrk_id) {
		$
				.ajax({
					url : "/workLinkPagination",
					method : "post",
					data : "page=" + page + "&pageSize=" + pageSize
							+ "&wrk_id=" + wrk_id,
					success : function(data) {
						//사용자 리스트
						var hhtml = "";
						var html = "";

						//hhtml생성
						hhtml += "<tr>";
						hhtml += "<th>링크명</th>";
						hhtml += "<th>공유한 멤버 이름</th>";
						hhtml += "<th>등록한 날짜</th>";
						hhtml += "<th>삭제</th>";
						hhtml += "</tr>";

						var user_email = data.user_email;
						data.workLinkList
								.forEach(function(link, index) {
									//html생성
									html += "<tr>";
									html += "<td><a href='https://"+link.attch_url+"' target='_blank'>"
											+ link.attch_url + "</a></td>";
									html += "<td>" + link.user_nm + "</td>";
									html += "<td>" + link.prjStartDtStr
											+ "</td>";
									if (link.user_email == data.user_email) {
										html += "<td><a href='javascript:workDelLink("
												+ link.link_id
												+ ","
												+ link.wrk_id
												+ ")'>삭제</a></td>";
									} else {
										html += "<td>삭제</td>";
									}
									html += "</tr>";
								});
						var pHtml = "";
						var pageVo = data.pageVo;
						console.log(data);
						console.log(pageVo);

						if (pageVo.page == 1)
							pHtml += "<li class='disabled'><span>«<span></li>";
						else
							pHtml += "<li><a href='javascript:workLinkPagination("
									+ (pageVo.page - 1)
									+ ", "
									+ pageVo.pageSize
									+ ","
									+ wrk_id
									+ ");'>«</a></li>";

						for (var i = 1; i <= data.paginationSize; i++) {
							if (pageVo.page == i)
								pHtml += "<li class='active'><span>" + i
										+ "</span></li>";
							else
								pHtml += "<li><a href='javascript:workLinkPagination("
										+ i
										+ ", "
										+ pageVo.pageSize
										+ ","
										+ wrk_id + ");'>" + i + "</a></li>";
						}
						if (pageVo.page == data.paginationSize)
							pHtml += "<li class='disabled'><span>»<span></li>";
						else
							pHtml += "<li><a href='javascript:workLinkPagination("
									+ (pageVo.page + 1)
									+ ", "
									+ pageVo.pageSize
									+ ","
									+ wrk_id
									+ ");'>»</a></li>";
						$(".pagination").html(pHtml);
						$("#publicHeader").html(hhtml);
						$("#publicList").html(html);
					}
				});
	}

	function workDelFile(fileID, wrk_id) {
		$.ajax({
			url : "/workDelFile",
			method : "post",
			data : "file_id=" + fileID + "&wrk_id=" + wrk_id,
			success : function(data) {
				//사용자 리스트
				var hhtml = "";
				var html = "";

				//hhtml생성
				hhtml += "<tr>";
				hhtml += "<th>파일명</th>";
				hhtml += "<th>공유한 멤버 이름</th>";
				hhtml += "<th>등록한 날짜</th>";
				hhtml += "<th>삭제</th>";
				hhtml += "</tr>";

				var user_email = data.user_email;
				data.workFileList.forEach(function(file, index) {
					//html생성
					html += "<tr id='filetr'>";
					html += "<td><a href='/fileDownLoad?file_id="
							+ file.file_id + "'>" + file.original_file_nm
							+ "</a></td>";
					html += "<td>" + file.user_nm + "</td>";
					html += "<td>" + file.prjStartDtStr + "</td>";
					if (file.user_email == user_email) {
						html += "<td><a href='javascript:workDelFile("
								+ file.file_id + "," + file.wrk_id
								+ ")'>삭제</a></td>";
					} else {
						html += "<td>삭제</td>";
					}
					html += "</tr>";
				});
				var pHtml = "";
				var pageVo = data.pageVo;
				console.log(data);
				console.log(pageVo);

				if (pageVo.page == 1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a href='javascript:workFilePagination("
							+ (pageVo.page - 1) + ", " + pageVo.pageSize + ","
							+ wrk_id + ");'>«</a></li>";
				for (var i = 1; i <= data.paginationSize; i++) {
					if (pageVo.page == i)
						pHtml += "<li class='active'><span>" + i
								+ "</span></li>";
					else
						pHtml += "<li><a href='javascript:workFilePagination("
								+ i + ", " + pageVo.pageSize + "," + wrk_id
								+ ");'>" + i + "</a></li>";
				}
				if (pageVo.page == data.paginationSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:workFilePagination("
							+ (pageVo.page + 1) + ", " + pageVo.pageSize + ","
							+ wrk_id + ");'>»</a></li>";
				$(".pagination").html(pHtml);
				$("#publicHeader").html(hhtml);
				$("#publicList").html(html);
			}
		});
	}

	function workDelLink(linkID, wrk_id) {
		$
				.ajax({
					url : "/workDelLink",
					method : "post",
					data : "link_id=" + linkID + "&wrk_id=" + wrk_id,
					success : function(data) {
						//사용자 리스트
						var hhtml = "";
						var html = "";

						//hhtml생성
						hhtml += "<tr>";
						hhtml += "<th>링크명</th>";
						hhtml += "<th>공유한 멤버 이름</th>";
						hhtml += "<th>등록한 날짜</th>";
						hhtml += "<th>삭제</th>";
						hhtml += "</tr>";

						var user_email = data.user_email;
						data.workLinkList
								.forEach(function(link, index) {
									//html생성
									html += "<tr>";
									html += "<td><a href='https://"+link.attch_url+"' target='_blank'>"
											+ link.attch_url + "</a></td>";
									html += "<td>" + link.user_nm + "</td>";
									html += "<td>" + link.prjStartDtStr
											+ "</td>";
									if (link.user_email == data.user_email) {
										html += "<td><a href='javascript:workDelLink("
												+ link.link_id
												+ ","
												+ link.wrk_id
												+ ")'>삭제</a></td>";
									} else {
										html += "<td>삭제</td>";
									}
									html += "</tr>";
								});
						var pHtml = "";
						var pageVo = data.pageVo;
						console.log(data);
						console.log(pageVo);

						if (pageVo.page == 1)
							pHtml += "<li class='disabled'><span>«<span></li>";
						else
							pHtml += "<li><a href='javascript:workLinkPagination("
									+ (pageVo.page - 1)
									+ ", "
									+ pageVo.pageSize
									+ ","
									+ wrk_id
									+ ");'>«</a></li>";

						for (var i = 1; i <= data.paginationSize; i++) {
							if (pageVo.page == i)
								pHtml += "<li class='active'><span>" + i
										+ "</span></li>";
							else
								pHtml += "<li><a href='javascript:workLinkPagination("
										+ i
										+ ", "
										+ pageVo.pageSize
										+ ","
										+ wrk_id + ");'>" + i + "</a></li>";
						}
						if (pageVo.page == data.paginationSize)
							pHtml += "<li class='disabled'><span>»<span></li>";
						else
							pHtml += "<li><a href='javascript:workLinkPagination("
									+ (pageVo.page + 1)
									+ ", "
									+ pageVo.pageSize
									+ ","
									+ wrk_id
									+ ");'>»</a></li>";
						$(".pagination").html(pHtml);
						$("#publicHeader").html(hhtml);
						$("#publicList").html(html);
					}
				});
	}
</script>


<div class="sub_menu">
	<ul class="sub_menu_item">
		<li><a href="/work/list">Work</a></li>
		<li><a href="/gantt/project">Gantt Chart</a></li>
		<li><a href="/analysis">Work Analysis</a></li>
		<li><a href="/publicFilePagination">File&amp;Link</a></li>
		<li><a href="/meeting/view">Meeting</a></li>
		<li><a href="/vote">Vote</a></li>
		<li><a href="/conferenceList">Minutes</a></li>
	</ul>
</div>

<style>
.half {
	display: inline-block;
	width: 49%;
	padding: 0;
	margin: 0;
	vertical-align: top;
}
</style>

<section class="contents">
	<div class="projectTitle">
		<p class="auth_txt">
			<span>제한</span>
		</p>
		<h2>${PROJECT_INFO.prj_nm}</h2>
		<div class="workListAdd">
			<input type="button" id="btnWorkList" value="업무리스트 추가">
		</div>
	</div>
	<div class="workListWrap">
		<div id="workListBox">
			<c:forEach items="${workList}" var="workList">
				<div class="workList">
					<span class="handle">+++</span>
					<div class="workList_hd">
						<dl>
							<dt>
								<input type="text" value="${workList.wrk_lst_nm}"
									id="${workList.wrk_lst_id}" class="wrkListName">
							</dt>
							<dd>
								<c:choose>
									<c:when
										test="${userInfo.prj_mem_lv == 'LV1' && PROJECT_INFO.prj_auth == 'ASC02' || userInfo.prj_mem_lv == 'LV1' && PROJECT_INFO.prj_auth == 'ASC03'}">
									</c:when>
									<c:otherwise>
										<input type="button" class="workList_add_i" value="새업무 추가">
										<a href="javascript:;" class="workList_set_i">업무리스트 설정</a>
										<div class="workList_set">
											<input type="button"
												id="btnWorkListDel_${workList.wrk_lst_id}" value="업무리스트 삭제">
										</div>
									</c:otherwise>
								</c:choose>
							</dd>
						</dl>
						<ul>
							<li><c:set var="yCnt" value="0" /> <c:set var="nCnt"
									value="0" /> <c:forEach items="${works}" var="work">
									<c:choose>
										<c:when
											test="${workList.wrk_lst_id == work.wrk_lst_id && work.wrk_cmp_fl == 'N'}">
											<c:set var="nCnt" value="${nCnt + 1 }" />
										</c:when>
										<c:when
											test="${workList.wrk_lst_id == work.wrk_lst_id && work.wrk_cmp_fl == 'Y'}">
											<c:set var="yCnt" value="${yCnt + 1 }" />
										</c:when>
									</c:choose>
								</c:forEach>

								<p>
									진행중 업무 <span>${nCnt }</span>
								</p>
								<p>
									<a href="javascript:;" class="btnComplete"
										id="${workList.wrk_lst_id}">완료된업무보기</a> <span>${yCnt }</span>
								</p></li>
							<li class="graph"></li>
						</ul>
						<div class="workCreateBox">
							<textarea name="wrk_nm" id="wrk_nm"
								placeholder="업무 이름을 입력해 주세요. 업무 이름은 70자 이내로 입력해 주세요"></textarea>
							<div class="workCreatebtnBox">
								<input type="button" value="취소" id="wrkCreateCancelBtn">
								<input type="button" value="만들기" id="wrkCreateBtn"
									class="wrkCreateBtn" name="${workList.wrk_lst_id}"
									disabled="disabled">
							</div>
						</div>
					</div>
					<div class="workWrap">
						<div class="list n1 working" id="${workList.wrk_lst_id}">
							<c:forEach items="${works}" var="work">
								<c:choose>
									<c:when
										test="${workList.wrk_lst_id == work.wrk_lst_id && work.wrk_cmp_fl == 'N'}">
										<div id="${work.wrk_lst_id}" data-wrkid="${work.wrk_id}"
											class="workListItem"
											style=" border-top: 3px solid ${work.wrk_color_cd}">
											<div class="checkList etrans workCheck">
												<input type="checkbox" name="wrk_cmp_fl"
													id="wrk_cmp_fl_${work.wrk_id}" class="wrk_cmp_fl">
												<label for="wrk_cmp_fl_${work.wrk_id}"></label>
											</div>
											<button type="button" id="workDelBtn" class="workDelBtn">업무삭제</button>
											<h2 class="wrk_title">
												<span>${work.wrk_grade}</span>${work.wrk_nm}</h2>
											<ul>
												<li class="wrk_date">${work.wrkStartDtStr}~
													${work.wrkEndDtStr}</li>
											</ul>
										</div>
									</c:when>
								</c:choose>
							</c:forEach>
						</div>
						<div class="complete_${workList.wrk_lst_id} list n1">
							<c:forEach items="${works}" var="work">
								<c:choose>
									<c:when
										test="${workList.wrk_lst_id == work.wrk_lst_id && work.wrk_cmp_fl == 'Y'}">
										<div id="${work.wrk_lst_id}" data-wrkid="${work.wrk_id}"
											class="workListItem"
											style=" border-top: 3px solid ${work.wrk_color_cd}">
											<div class="checkList etrans workCheck">
												<input type="checkbox" name="wrk_cmp_fl"
													id="wrk_cmp_fl_${work.wrk_id}" class="wrk_cmp_fl" checked>
												<label for="wrk_cmp_fl_${work.wrk_id}"></label>
											</div>
											<button type="button" id="workDelBtn" class="workDelBtn">업무삭제</button>
											<h2 class="wrk_title">
												<span>${work.wrk_grade}</span>${work.wrk_nm}</h2>
											<ul>
												<li class="wrk_date">${work.wrkStartDtStr}~
													${work.wrkEndDtStr}</li>
											</ul>
										</div>
									</c:when>
								</c:choose>
							</c:forEach>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</section>


<!-- work setting layer -->
<div id="propertyWorkSet" class="propertySet">
	<div class="propertySetWrap">
		<div class="setHd">
			<div class="setHdTitle">
				<input type="hidden" id="wps_id" name="wps_id" value=""> <input
					type="hidden" id="wps_wrk_id" name="wps_wrk_id" value="">
				<h2>
					<input type="text" id="wps_nm" name="wps_nm" value="">
				</h2>
			</div>
			<p class="wrk_update">
				<span>작성자</span><em id="wps_write_nm"></em><span>작성일</span><em
					id="wps_write_date"></em>
			</p>
		</div>

		<!-- work tab -->
		<div class="workTab">
			<ul class="tabs">
				<li class="active" data-tab="tab1">설정</li>
				<li id="commentId" data-tab="tab2">업무 코멘트</li>
				<li id="FL" data-tab="tab3">파일&amp;링크</li>
			</ul>
		</div>

		<!-- 여기서부터 property setting layer contents -->
		<!-- 업무설정 : tab1 / 업무코멘트 : tab2 / 업무파일 : tab3 -->

		<div class="setCon tab_content" id="tab1">
			<dl class="setItem">
				<dt>날짜 설정</dt>
				<dd>
					<input class="flatpickr flatpickr-input" type="text"
						placeholder="Select Date.." data-id="rangeDisable"
						id="wps_start_date" readonly="readonly">
				</dd>
			</dl>
			<dl class="setItem notifyBox">
				<dt>예약 알림</dt>
				<dd class="notifyCon">
					<input type="button" id="resNotifyBtn" value="예약알림추가">
				</dd>
				<dd class="notifyArea">
					<input class="flatpickr1 flatpickr-input" type="hidden"
						placeholder="Select Date.." data-id="rangeDisable"
						id="wps_res_date" readonly="readonly">
					<div class="notification" tabindex="-1">
						<h2>알림 받을 멤버</h2>
						<select id="notificationMem" name="notificationMem">
							<option value="나에게">나에게</option>
							<option value="배정된 멤버">배정된 멤버</option>
							<option value="팔로워 멤버">팔로워 멤버</option>
							<option value="모두">배정된 멤버 &amp; 팔로워 멤버</option>
						</select>
						<div class="notificationBtnArea">
							<input type="button" id="notificationCancelBtn" value="취소하기">
							<input type="button" id="notificationAddBtn" value="추가하기">
						</div>
					</div>
				</dd>
			</dl>
			<dl class="setItem">
				<dt>배정된 멤버</dt>
				<dd>
					<button type="button" id="wps_mem_set" name="wps_mem_set">멤버
						추가</button>

					<!-- 프로젝트 관리자 리스트 box -->
					<ul class="wrk_add_box"></ul>

					<div class="wrk_add_mem">
						<label for="wrk_mem">배정된 멤버 추가</label> <input type="button"
							value="닫기" id="memClose" class="close">
						<div class="wrk_mem_list">
							<div class="wrk_mem_sch">
								<fieldset id="hd_sch">
									<legend>멤버 검색 검색</legend>
									<input type="text" name="wrk_mem" id="wrk_mem" maxlength="20"
										placeholder="검색어를 입력해주세요">
								</fieldset>
							</div>

							<!-- 추가된 프로젝트 관리자 리스트 box -->
							<ul class="wrk_mem_item"></ul>
						</div>
					</div>
				</dd>
			</dl>
			<dl class="setItem">
				<dt>팔로워</dt>
				<dd>
					<button type="button" id="wrk_flw_set" name="wrk_flw_set">팔로워
						추가</button>

					<!-- 프로젝트 멤버 리스트 box -->
					<ul class="wrk_mem_flw_box"></ul>

					<div class="wrk_add_flw">
						<label for="wrk_flw">팔로워 추가</label> <input type="button"
							value="닫기" id="flwClose" class="close">
						<div class="wrk_flw_list">
							<div class="wrk_flw_sch">
								<fieldset id="hd_sch">
									<legend>멤버 검색</legend>
									<input type="text" name="wrk_flw" id="wrk_flw" maxlength="20"
										placeholder="검색어를 입력해주세요">
								</fieldset>
							</div>

							<!-- 추가된 프로젝트 멤버 리스트 box -->
							<ul class="wrk_flw_item_list"></ul>
						</div>
					</div>
				</dd>
			</dl>
			<dl class="setItem">
				<dt>중요도</dt>
				<dd>
					<div class="setGrade">
						<select id="wrk_gd" name="wrk_gd">
							<option value="A">A</option>
							<option value="B">B</option>
							<option value="C">C</option>
							<option value="D">D</option>
							<option value="E">E</option>
						</select>
					</div>
				</dd>
			</dl>

			<dl class="setItem">
				<dt>라벨 색상</dt>
				<dd>
					<div class="lableColor">
						<ul>
							<li><label for="7d3bff" class="wrk_color wrk_color01"
								style="background: #7d3bff"></label> <input type="radio"
								value="#7d3bff" name="wrk_color_cd" id="7d3bff"></li>
							<li><label for="cf5de1" class="wrk_color wrk_color02"
								style="background: #cf5de1"></label> <input type="radio"
								value="#cf5de1" name="wrk_color_cd" id="cf5de1"></li>
							<li><label for="75dfff" class="wrk_color wrk_color03"
								style="background: #75dfff"></label> <input type="radio"
								value="#75dfff" name="wrk_color_cd" id="75dfff"></li>
							<li><label for="287cff" class="wrk_color wrk_color04"
								style="background: #287cff"></label> <input type="radio"
								value="#287cff" name="wrk_color_cd" id="287cff"></li>
							<li><label for="ffe604" class="wrk_color wrk_color05"
								style="background: #ffe604"></label> <input type="radio"
								value="#ffe604" name="wrk_color_cd" id="ffe604"></li>
							<li><label for="ff8b03" class="wrk_color wrk_color06"
								style="background: #ff8b03"></label> <input type="radio"
								value="#ff8b03" name="wrk_color_cd" id="ff8b03"></li>
							<li><label for="de4439" class="wrk_color wrk_color07"
								style="background: #de4439"></label> <input type="radio"
								value="#de4439" name="wrk_color_cd" id="de4439"></li>
							<li><label for="0b16c6" class="wrk_color wrk_color08"
								style="background: #0b16c6"></label> <input type="radio"
								value="#0b16c6" name="wrk_color_cd" id="0b16c6"></li>
							<li><label for="ff2f77" class="wrk_color wrk_color09"
								style="background: #ff2f77"></label> <input type="radio"
								value="#ff2f77" name="wrk_color_cd" id="ff2f77"></li>
							<li><label for="3d434f" class="wrk_color wrk_color10"
								style="background: #3d434f"></label> <input type="radio"
								value="#3d434f" name="wrk_color_cd" id="3d434f"></li>
						</ul>
					</div>
				</dd>
			</dl>
		</div>

		<!--  여기서부터 work comment -->
		<form id="frm02">
			<div id="tab2" class="tab_content">
				<div class="tableWrap">
					<table class="tb_style_02">
						<colgroup>
							<col width="50%">
							<col width="10%">
							<col width="10%">
							<col width="10%">
							<col width="10%">
						</colgroup>
						<thead>
							<tr>
								<th>내용</th>
								<th>아이디</th>
								<th>작성일</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody id="commBody">

						</tbody>
					</table>
				</div>
				<div class="commWrite">
					<textarea rows="1" cols="60" name="comm_content" id="comm_content"
						placeholder="댓글을 작성해 주세요."></textarea>
					<button type="button" name="replyBtn" id="replyBtn">댓글등록</button>
				</div>
				<div class="pagination"></div>
			</div>
		</form>

		<!--  여기서부터 work file&link-->
		<!-- 영하가 수정함 여기부터 ㅎ-->
		<div id="tab3" class="tab_content">
			<div class="tab_sub_menu">
				<ul class="tabs">
					<li id="fileList">파일</li>
					<li id="linkList">링크</li>
				</ul>
			</div>

			<div id="locker">
				<input value="public" name="box" type="radio" checked>공유함 <input
					value="individual" name="box" type="radio">개인함 <input
					value="both" name="box" type="radio"> 공유&amp;개인함
			</div>

			<form id="frm" action="/workFileUpload" method="post"
				enctype="multipart/form-data">
				<div id="workFile">
					<input type="hidden" id="work" value="" name="wrk_id"> <input
						type="hidden" id="box" value="" name="locker"> <input
						type="file" class="file" name="profile" />
					<button type="button" id="uploadFile">등록</button>
				</div>
			</form>

			<div id="workLink">
				<label class="col-sm-2 control-label">공유할 링크주소:</label> <input
					type="text" class="link" name="attch_url" />
				<button type="button" id="uploadLink">등록</button>
			</div>

			<div class="tab_con">

				<div class="tab-content current">
					<div class="fileWrap">
						<table class="tb_style_03">
							<colgroup>
								<col width="30%">
								<col width="20%">
								<col width="30%">
								<col width="20%">
							</colgroup>

							<thead id="publicHeader">
							<thead>
							<tbody id="publicList">
							</tbody>

						</table>
					</div>

					<div class="pagination"></div>

				</div>
			</div>

		</div>
		<!-- 영하가 수정함 여기까지ㅎ -->

	</div>
	<div class="btnSetClose">닫기</div>
</div>



<!-- 오류 알림창 -->
<div id="layer2" class="pop-layer">
	<div class="pop-container">
		<div class="pop-alram">
			<div class="new_proejct">
				<h2>알 림</h2>
				<!--content //-->
				<p class="ctxt mb20 alram"></p>
				<div class="btn-r">
					<a href="#" class="btn-layerClose">Close</a>
				</div>
				<!--// content-->
			</div>
		</div>
	</div>
</div>
