<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
	$(document).ready(function(){
		$("#notifyTable").on("click","#notifyDeleteBtn",function(){
			var not_id = $(this).siblings("#notifyNot_id").val();
			console.log(not_id);
			
			notifyDeleteAjax(not_id,1,10);
		});
		
	});
	
	function notifyDeleteAjax(not_id,page,pageSize){
		$.ajax({
			url:"/deleteNotify",
			method:"post",
			data:"not_id="+not_id+"&page="+page+"&pageSize="+pageSize,
			success:function(data){
				console.log(data.notifiList)
				console.log(data);
				var html="";
				data.notifiList.forEach(function(notify,index){
					html+="<tr>";
					html+="<td>"+notify.not_con+"</td>";
					html+="<td>"+notify.notifyDate+"</td>";
					html+="<td>";
					html+="<button class='btn_style_01' id='notifyDeleteBtn'>삭제 </button>";
					html+="	<input type='hidden' id='notifyNot_id' value='"+notify.not_id+"'/>";
					html+="</td>";
					html+="<td style='display:none;'>"+data.user_email+"</td>";
					html+="</tr>";
				});
				var pHtml = "";
				var pageVo = data.pageVo;
				
				if (pageVo.page == 1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a href='javascript:notifyDeleteAjax(" + not_id + "," + (pageVo.page - 1) + ", " + pageVo.pageSize + ");'>«</a></li>";
				for (var i = 1; i <= data.notifiPageSize; i++) {
					if (pageVo.page == i)
						pHtml += "<li class='active'><span>" + i + "</span></li>";
					else
						pHtml += "<li><a href='javascript:notifyDeleteAjax(" + not_id + ","+ i + ", " + pageVo.pageSize + ");'>" + i + "</a></li>";
				}
				if (pageVo.page == data.notifiPageSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:notifyDeleteAjax(" + not_id + "," + (pageVo.page + 1) + ", " + pageVo.pageSize + ");'>»</a></li>";
				
				$("#notifyTbody").html(html);
				$(".pagination").html(pHtml);
			}
			
		});
	}

</script>


<section class="contents">
	<div id="container">
		<table class="tb_style_01" id="notifyTable">
			<thead>
				<tr>
					<th>메세지 내용</th>
					<th>날짜</th>
					<th></th>
				</tr>			
			</thead>
			<tbody id="notifyTbody">
				<c:forEach items="${notifiList }" var="noti">
					<tr>
						<td>${noti.not_con }</td>
						<td><fmt:formatDate value="${noti.not_dt }" pattern="yyyy-MM-dd HH:mm" /></td>
						<td>
							<button class="btn_style_01" id="notifyDeleteBtn">삭제 </button>
							<input type="hidden" id="notifyNot_id" value="${noti.not_id }"/>
						</td>
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