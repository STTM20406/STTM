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
			var mnu_id = $(this).find(".mnu_id").attr("id");
			$('#mnu_id').val(mnu_id);
			$('#frm').submit();
			
		});
		
		$('#searchBtn').on('click',function(){
			if($('#searchText').val().length == 0){
				alert("검색어를 입력해주세요");
			}else{
// 				var search = $(this).find(".search").val();
// 				console.log(search);
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
	                </select>
                <input type="text" name="user_nm" id="searchText" maxlength="20" placeholder="검색어를 입력해주세요">
                <button type="button" id ="searchBtn" value="검색">검색</button>
			</form>
    	</div>
    </div>
	<br><br>
	
	<table>
		<tr>
			<th>번호</th>
			<th>작성자</th>
			<th>작성일시 </th>
		</tr>
		<c:forEach items="${searchList}" var="SList">
			<c:choose>
				<c:when test="${SList.del_fl eq 'N'}">
					<tr class="minutes">
						<td class="mnu_id" id="${SList.mnu_id}">${SList.num}</td>
						<fmt:formatDate value="${SList.write_date}" var="date" pattern="yyyy-MM-dd" />
						<td>${SList.user_nm}  님이  </td>
						<td>${date}에 작성 하신 회의록 입니다.</td>
					</tr>
				</c:when>
			</c:choose>
		</c:forEach>
	</table>
	
	<a href="/insertConference">회의록 등록</a>
	
		<div class="pagination">
			<c:choose>
				<c:when test="${pageVo.page eq 1}">
					<li class="disabled"><span>&lt;</span></li>
				</c:when>
				<c:otherwise>
					<li><a href="/searchMinutes?prj_id=${prj_id}&page=${pageVo.page-1}&pageSize=${pageVo.pageSize}">&lt;
					</a></li>
				</c:otherwise>
			</c:choose>
		
			<c:forEach var="i" begin="1" end="${paginationSize}">
				<c:choose>
					<c:when test="${pageVo.page eq i}">
						<li class="active"><span>${i}</span></li>
					</c:when>
					<c:otherwise>
						<li><a href="/searchMinutes?prj_id=${prj_id}&page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		
			<c:choose>
				<c:when test="${pageVo.page eq paginationSize}">
					<li class="disabled"><span>&gt;</span></li>
				</c:when>
				<c:otherwise>
					<li><a href="/searchMinutes?prj_id=${prj_id}&page=${pageVo.page+1}&pageSize=${pageVo.pageSize}">&gt;</a>
					</li>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</section>