<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<section class="contents">
	<div>
		<table class="tb_style_01">
			<thead>
				<tr>
					<th>내가 한 일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ memoList}" var="memo">
					<tr>
						<td><fmt:formatDate value="${memo.memo_update }" pattern="yyyy년MM월dd일  hh:mm"/></td>
					</tr>			
				</c:forEach>
			</tbody>
		</table>
	</div>
</section>