<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


	<div>
		<table>
			<tr>
				<th>번호</th>
				<th>파일명</th>
				<th>해당 업무명</th>
				<th>다운로드 받은 멤버 ID</th>
				<th>다운로드 받은 날짜</th>
			</tr>
			<c:forEach items="${historyList}" var="history">
				<tr>
					<td>${history.num}</td>
					<td>${history.original_file_nm }</td>
					<td>${history.wrk_nm}</td>
					<td>${history.user_email}</td>
					<fmt:formatDate value="${history.down_date}" var="date" pattern="yyyy-MM-dd HH:mm"/>
					<td>${date}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<div class="pagination">
	<c:choose>
		<c:when test="${pageVo.page eq 1}">
			<li class="disabled"><span>&lt;</span></li>
		</c:when>
		<c:otherwise>
			<li><a
				href="/historyPagination?prj_id=${prj_id}&page=${pageVo.page-1}&pageSize=${pageVo.pageSize}">&lt; </a>
			</li>
		</c:otherwise>
	</c:choose>

	<c:forEach var="i" begin="1" end="${paginationSize}">
		<c:choose>
			<c:when test="${pageVo.page eq i}">
				<li class="active"><span>${i}</span></li>
			</c:when>
			<c:otherwise>
				<li><a
					href="/historyPagination?prj_id=${prj_id}&page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
				</li>
			</c:otherwise>
		</c:choose>
	</c:forEach>

	<c:choose>
		<c:when test="${pageVo.page eq paginationSize}">
			<li class="disabled"><span>&gt;</span></li>
		</c:when>
		<c:otherwise>
			<li><a
				href="/historyPagination?prj_id=${prj_id}&page=${pageVo.page+1}&pageSize=${pageVo.pageSize}">&gt;</a>
			</li>
		</c:otherwise>
	</c:choose>
	</div>
	