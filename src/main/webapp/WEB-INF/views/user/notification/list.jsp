<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<section class="contents">
	<div id="container">
		<table class="tb_style_01">
			<thead>
				<tr>
					<th>메세지 내용</th>
					<th>날짜</th>
				</tr>			
			</thead>
			<tbody>
				<c:forEach items="${notifiList }" var="noti">
					<tr>
						<td>${noti.not_con }</td>
						<td><fmt:formatDate value="${noti.not_dt }" pattern="yyyy-MM-dd" /></td>
						<td><button class="btn_style_01">삭제 </button></td>
						<td style="display:none;">${USER_INFO.user_email}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="pagination">
           <c:choose>
              <c:when test="${pageVo.page == 1 }">
                 <a href class="btn_first"></a>
              </c:when>
              <c:otherwise>
                 <a href="/notification?page=${pageVo.page - 1}&pageSize=10">«</a>
              
              </c:otherwise>
           </c:choose>

           <c:forEach begin="1" end="${notifiPageSize}" var="i">
              <c:choose>
                 <c:when test="${pageVo.page == i}">
                    <span>${i}</span>
                 </c:when>
                 <c:otherwise>
                 <a href="/notification?page=${i}&pageSize=10">${i}</a>
                 </c:otherwise>
              </c:choose>

           </c:forEach>

           <c:choose>
              <c:when test="${pageVo.page == notifiPageSize}">
                 <a href class="btn_last"></a>
              </c:when>
              <c:otherwise>
              <a href="/notification?page=${pageVo.page + 1}&pageSize=10">»</a>
                 

              </c:otherwise>
           </c:choose>
     
     </div>
</section>