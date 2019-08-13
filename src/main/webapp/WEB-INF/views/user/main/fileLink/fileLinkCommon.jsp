<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>기ㅡ긔</h1>

<!-- 	<div> -->
<!-- 		<table> -->
<!-- 			<tr> -->
<!-- 				<th>File_Num</th> -->
<!-- 				<th>업무명</th> -->
<!-- 				<th>파일명</th> -->
<!-- 				<th>등록일</th> -->
<!-- 				<th>공유한 사람</th> -->
<!-- 				<th>삭제</th> -->
<!-- 			</tr> -->
<%-- 			<c:forEach items="${fileList}" var="file"> --%>
<%-- 				<c:choose> --%>
<%-- 				<c:when test="${file.del_fl eq 'N'}"> --%>
<!-- 				<tr> -->
<%-- 					<td>${file.file_id}</td> --%>
<%-- 					<td>${file.wrk_nm }</td> --%>
<%-- 					<td><a href="/main/fileDownload?file_id=${file.file_id}">${file.original_file_nm}</a></td> --%>
<%-- 					<fmt:formatDate value="${file.file_dt}" var="date" pattern="yyyy-MM-dd"/> --%>
<%-- 					<td>${date}</td> --%>
<%-- 					<td>${file.user_email}</td> --%>
<%-- 					<td><a href="/main/fileUpdate?file_id=${file.file_id}&del=${del}">삭제</a></td> --%>
<!-- 				</tr> -->
<%-- 				</c:when> --%>
<%-- 				</c:choose> --%>
<%-- 			</c:forEach> --%>
<!-- 		</table> -->
<!-- 	</div> -->
<!-- 	<div class="pagination"> -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${pageVo.page eq 1}"> --%>
<!-- 			<li class="disabled"><span>&lt;</span></li> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 			<li><a -->
<%-- 				href="/main/fileList?prj_id=1&page=${pageVo.page-1}&pageSize=${pageVo.pageSize}">&lt; </a> --%>
<!-- 			</li> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>

<%-- 	<c:forEach var="i" begin="1" end="${paginationSize}"> --%>
<%-- 		<c:choose> --%>
<%-- 			<c:when test="${pageVo.page eq i}"> --%>
<%-- 				<li class="active"><span>${i}</span></li> --%>
<%-- 			</c:when> --%>
<%-- 			<c:otherwise> --%>
<!-- 				<li><a -->
<%-- 					href="/main/fileList?prj_id=1&page=${i}&pageSize=${pageVo.pageSize}">${i}</a> --%>
<!-- 				</li> -->
<%-- 			</c:otherwise> --%>
<%-- 		</c:choose> --%>
<%-- 	</c:forEach> --%>

<%-- 	<c:choose> --%>
<%-- 		<c:when test="${pageVo.page eq paginationSize}"> --%>
<!-- 			<li class="disabled"><span>&gt;</span></li> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 			<li><a -->
<%-- 				href="/main/fileList?prj_id=1&page=${pageVo.page+1}&pageSize=${pageVo.pageSize}">&gt;</a> --%>
<!-- 			</li> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
<!-- 	</div> -->
<!-- 	<hr> -->
<!-- 	<div> -->
<!-- 		<table> -->
<!-- 			<tr> -->
<!-- 				<th>Link_Num</th> -->
<!-- 				<th>업무명</th> -->
<!-- 				<th>링크명</th> -->
<!-- 				<th>등록일</th> -->
<!-- 				<th>공유한 사람</th> -->
<!-- 				<th>삭제</th> -->
<!-- 			</tr> -->
<%-- 			<c:forEach items="${linkList}" var="link"> --%>
<%-- 				<c:choose> --%>
<%-- 				<c:when test="${link.del_fl eq 'N'}"> --%>
<!-- 				<tr> -->
<%-- 					<td>${link.link_id}</td> --%>
<%-- 					<td>${link.wrk_nm }</td> --%>
<%-- 					<td><a href="https://${link.attch_url}">${link.attch_url}</a></td> --%>
<%-- 					<fmt:formatDate value="${link.file_link_dt}" var="date" pattern="yyyy-MM-dd"/> --%>
<%-- 					<td>${date}</td> --%>
<%-- 					<td>${link.user_email}</td> --%>
<%-- 					<td><a href="/main/linkUpdate?link_id=${link.link_id}&del=${del}">삭제</a></td> --%>
<!-- 				</tr> -->
<%-- 				</c:when> --%>
<%-- 				</c:choose> --%>
<%-- 			</c:forEach> --%>
<!-- 		</table> -->
<!-- 	</div> -->
<!-- 	<div class="pagination"> -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${pageVo.page eq 1}"> --%>
<!-- 			<li class="disabled"><span>&lt;</span></li> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 			<li><a -->
<%-- 				href="/main/linkList?prj_id=1&page=${pageVo.page-1}&pageSize=${pageVo.pageSize}">&lt; </a> --%>
<!-- 			</li> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>

<%-- 	<c:forEach var="i" begin="1" end="${paginationSize}"> --%>
<%-- 		<c:choose> --%>
<%-- 			<c:when test="${pageVo.page eq i}"> --%>
<%-- 				<li class="active"><span>${i}</span></li> --%>
<%-- 			</c:when> --%>
<%-- 			<c:otherwise> --%>
<!-- 				<li><a -->
<%-- 					href="/main/linkList?prj_id=1&page=${i}&pageSize=${pageVo.pageSize}">${i}</a> --%>
<!-- 				</li> -->
<%-- 			</c:otherwise> --%>
<%-- 		</c:choose> --%>
<%-- 	</c:forEach> --%>

<%-- 	<c:choose> --%>
<%-- 		<c:when test="${pageVo.page eq paginationSize}"> --%>
<!-- 			<li class="disabled"><span>&gt;</span></li> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 			<li><a -->
<%-- 				href="/main/linkList?prj_id=1&page=${pageVo.page+1}&pageSize=${pageVo.pageSize}">&gt;</a> --%>
<!-- 			</li> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
<!-- 	</div> -->