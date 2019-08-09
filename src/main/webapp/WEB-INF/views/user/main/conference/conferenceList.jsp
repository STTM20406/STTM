<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
.minutes:hover{
	cursor: pointer;
}

.searchBox{
	
}
</style>

<script>
	$(document).ready(function(){
		//사용자 tr 태그 이벤트 등록
		$(".minutes").on("click", function(){
			var mnu_id = $(this).find(".mnu_id").text();
			$('#mnu_id').val(mnu_id);
			$('#frm').submit();
			
		});
		
		$('#searchBtn').on('click',function(){
			if($('#searchText').val().length == 0){
				alert("검색어를 입력해주세요");
			}else{
				var search = $(this).find(".search").text();
				console.log(search);
				$('#frmSearch').submit();
			}
		});
	});
</script>

<section class="contents">
	<div id="container">
	<form id="frm" action="/conferenceDetail" method="get">
		<input type="hidden" id="mnu_id" name="mnu_id">
	</form>
	
	<div class="searchBox">
		<div class="tb_sch_wr">
			<form id="frmSearch" action="/searchMinutes" method="get">
	                <select class="search" name="subject">
	                	<option value="writer">작성자</option>
	                	<option value="attender">참석자</option>
	                </select>
                <input type="text" name="searchText" id="searchText" maxlength="20" placeholder="검색어를 입력해주세요">
                <button type="button" id ="searchBtn" value="검색">검색</button>
			</form>
    	</div>
    </div>
	<br><br>
	
	<table>
		<tr>
			<th>게시글 ID</th>
			<th>작성일시  ♬♩♪  작성자</th>
		</tr>
		<c:forEach items="${minutesList}" var="MList">
			<c:choose>
				<c:when test="${MList.del_fl eq 'N'}">
					<tr class="minutes">
						<td class="mnu_id">${MList.mnu_id}</td>
						<fmt:formatDate value="${MList.write_date}" var="date" pattern="yyyy-MM-dd HH:mm" />
						<td>${date}에 ${MList.user_nm} 님에 의해 작성된 회의록</td>
					</tr>
				</c:when>
			</c:choose>
		</c:forEach>
	</table>

		<div class="pagination">
			<c:choose>
				<c:when test="${pageVo.page eq 1}">
					<li class="disabled"><span>&lt;</span></li>
				</c:when>
				<c:otherwise>
					<li><a href="/conferenceList?prj_id=1&page=${pageVo.page-1}&pageSize=${pageVo.pageSize}">&lt;
					</a></li>
				</c:otherwise>
			</c:choose>
		
			<c:forEach var="i" begin="1" end="${paginationSize}">
				<c:choose>
					<c:when test="${pageVo.page eq i}">
						<li class="active"><span>${i}</span></li>
					</c:when>
					<c:otherwise>
						<li><a href="/conferenceList?prj_id=1&page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		
			<c:choose>
				<c:when test="${pageVo.page eq paginationSize}">
					<li class="disabled"><span>&gt;</span></li>
				</c:when>
				<c:otherwise>
					<li><a href="/conferenceList?prj_id=1&page=${pageVo.page+1}&pageSize=${pageVo.pageSize}">&gt;</a>
					</li>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</section>