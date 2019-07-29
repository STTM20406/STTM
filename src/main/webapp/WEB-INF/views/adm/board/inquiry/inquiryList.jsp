<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
.inquiryTr:hover{
		cursor: pointer;
}
ul.tabs {
	margin: 0px;
	padding: 0px;
	list-style: none;
}

ul.tabs li {
	background: none;
	color: #222;
	display: inline-block;
	padding: 10px 15px;
	cursor: pointer;
}

ul.tabs li.current {
	color: #222;
}

.tab-content {
	display: none;
	padding: 15px;
}

.tab-content.current {
	display: inherit;
}
</style>
<script>
	$(document).ready(function() {
		
		$("#search").on("change",function(){
			console.log($("#search").val());
			var searchValue = $("#search").val();
			$("#scText").val(searchValue);
		})
		
		$("#sch_submit").on("click",function(){
			$("#frmSearch").submit();
		})
		
		$('ul.tabs li').click(function() {
			var tab_id = $(this).attr('data-tab');

			$('ul.tabs li').removeClass('current');
			$('.tab-content').removeClass('current');

			$(this).addClass('current');
			$("#" + tab_id).addClass('current');
		});
		
		$(".inquiryTr").on("click",function(){
			console.log("inquiryTr click");
			var inq_id = $(this).find(".inquirynum").text();
			$("#inq_id").val(inq_id);
			
			$("#frm").submit();
		})
	});
</script>

<section class="contents">

	<form id="frm" action="/admInquiryView" method="get"> 
		<input type="hidden" id="inq_id" name="inq_id" value=""/>
		
	</form>
	
	<div id="container">


		<div class="sub_menu">
			<ul class="tabs">
				<li data-tab="tab-1">일반 문의</li>
				<li data-tab="tab-2">광고 문의</li>
			</ul>
		</div>

		<div class="tab_con">

			<div id="tab-1" class="tab-content current">
				<div>
					<div class="searchBox">
						<div class="tb_sch_wr">
							<fieldset id="hd_sch">
							 	<form id="frmSearch" action="/admInquirySearch" method="get">
			<input type="hidden" id="inq_cate" name="inq_cate" value="INQ01"/>
									<input type="hidden" id="scText" name="scText" value="title"/>
					                <legend>사이트 내 전체검색</legend>
						                <select id="search">
						                	<option value="title">제목</option>
						                	<option value="content">내용</option>
						                </select>
					                <input type="text" name="searchText" id="searchText" maxlength="20" placeholder="검색어를 입력해주세요">
					                <button type="submit" id="sch_submit" value="검색">검색</button>
				                </form>
			            	</fieldset>
		            	</div>
	            	</div>
					<table class="tb_style_01">
						<colgroup>
							<col width="10%">
							<col width="40%">
							<col width="30%">
							<col width="10%">
							<col width="10%">
						</colgroup>
						<tbody>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자 아이디</th>
								<th>작성일</th>
								<th>답변 여부</th>


								<c:forEach items="${inquiryList }" var="iq">
									<c:choose>
										<c:when test="${iq.inq_cate == 'INQ01'}">
											<tr class="inquiryTr">
												<td class="inquirynum">${iq.inq_id }</td>
												<td>${iq.subject }</td>
												<td>${iq.user_email }</td>
												<td><fmt:formatDate value="${iq.inq_dt }" pattern="yyyy-MM-dd"/></td>
												<c:choose>
													<c:when test="${iq.ans_st == 'Y'}">
														<td>답변 완료</td>
													</c:when>
													<c:otherwise>
														<td>답변 미완료</td>
													</c:otherwise>
												</c:choose>
											</tr>
										</c:when>
									</c:choose>
								</c:forEach>
							</tr>
						</tbody>
					</table>
				</div>

				<div class="pagination">
						<c:choose>
							<c:when test="${pageVo.page == 1 }">
								<a href class="btn_first"></a>
							</c:when>
							<c:otherwise>
								<a href="${cp}/admInquiry?inq_cate=${pageVo.inq_cate }&page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
							
							</c:otherwise>
						</c:choose>

						<c:forEach begin="1" end="${paginationSize}" var="i">
							<c:choose>
								<c:when test="${pageVo.page == i}">
									<span>${i}</span>
								</c:when>
								<c:otherwise>
								<a href="${cp}/admInquiry?inq_cate=${pageVo.inq_cate }&page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
								</c:otherwise>
							</c:choose>

						</c:forEach>

						<c:choose>
							<c:when test="${pageVo.page == paginationSize}">
								<a href class="btn_last"></a>
							</c:when>
							<c:otherwise>
							<a href="${cp}/admInquiry?inq_cate=${pageVo.inq_cate }&page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
								

							</c:otherwise>
						</c:choose>
				
				</div>
			</div>

			<div id="tab-2" class="tab-content">
				<div>
					<div>
						<div class="searchBox">
							<div class="tb_sch_wr">
								<fieldset id="hd_sch">
					                <form id="frmSearch" action="/admInquirySearch" method="get">
			<input type="hidden" id="inq_cate" name="inq_cate" value="INQ02"/>
									<input type="hidden" id="scText" name="scText" value="title"/>
					                <legend>사이트 내 전체검색</legend>
						                <select id="search">
						                	<option value="title">제목</option>
						                	<option value="content">내용</option>
						                </select>
					                <input type="text" name="searchText" id="searchText" maxlength="20" placeholder="검색어를 입력해주세요">
					                <button type="submit" id="sch_submit" value="검색">검색</button>
				                </form>
				            	</fieldset>
			            	</div>
		            	</div>
						<table class="tb_style_01">
							<colgroup>
								<col width="10%">
								<col width="40%">
								<col width="30%">
								<col width="10%">
								<col width="10%">
							</colgroup>
							<tbody>
								<tr>
	
									<th>번호</th>
									<th>제목</th>
									<th>작성자 아이디</th>
									<th>작성일</th>
									<th>답변 여부</th>
	
	
									<c:forEach items="${inquiryList }" var="iq">
										<c:choose>
											<c:when test="${iq.inq_cate == 'INQ02' }">
												<tr class="inquiryTr">
													<td class="inquirynum">${iq.inq_id }</td>
													<td>${iq.subject }</td>
													<td>${iq.user_email }</td>
													<td><fmt:formatDate value="${iq.inq_dt }" pattern="yyyy-MM-dd" /></td>
													<c:choose>
														<c:when test="${iq.ans_st == 'Y'}">
															<td>답변 완료</td>
														</c:when>
														<c:otherwise>
															<td>답변 미완료</td>
														</c:otherwise>
													</c:choose>
												</tr>
											</c:when>
										</c:choose>
									</c:forEach>
								</tr>
							</tbody>
						</table>
					</div>
	
					<div class="pagination">
							<c:choose>
								<c:when test="${pageVo.page == 1 }">
									<a href class="btn_first"></a>
								</c:when>
								<c:otherwise>
									<a href="${cp}/admInquiry?inq_cate=${pageVo.inq_cate }&page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
								
								</c:otherwise>
							</c:choose>
	
							<c:forEach begin="1" end="${paginationSize}" var="i">
								<c:choose>
									<c:when test="${pageVo.page == i}">
										<span>${i}</span>
									</c:when>
									<c:otherwise>
									<a href="${cp}/admInquiry?inq_cate=${pageVo.inq_cate }&page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
									</c:otherwise>
								</c:choose>
	
							</c:forEach>
	
							<c:choose>
								<c:when test="${pageVo.page == paginationSize}">
									<a href class="btn_last"></a>
								</c:when>
								<c:otherwise>
								<a href="${cp}/admInquiry?inq_cate=${pageVo.inq_cate }&page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
									
	
								</c:otherwise>
							</c:choose>
					</div>
			</div>


		</div>
	</div>

	</div>
</section>