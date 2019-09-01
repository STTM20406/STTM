<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
   $(document).ready(function() {
      
      $("#search").on("change",function(){
         console.log($("#search").val());
         var searchValue = $("#search").val();
         $("#scText").val(searchValue);
      })
      
      $("#sch_submit01").on("click",function(){
         $("#frm01").attr("action","/userInquirySearch");
         $("#frm01").attr("method","get");
         $("#frm01").submit();
      })
      
      
      $('#generalBtn').click(function(){
         $("#frm01").attr("action","/userInquiryPost");
         $("#frm01").attr("method","get");
         $("#frm01").submit();   
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

   <form id="frm" action="/userInquiryView" method="get"> 
      <input type="hidden" id="inq_id" name="inq_id" value=""/>
   </form>   
   
   
   <div id="container">

      <div class="tab_con">
         <form id="frm01">
      
         <div id="tab-1" class="tab-content current">
         <input type="hidden" name="inqCate" value="INQ01"/>
         
            <div>
               <div class="searchBox">
                  <div class="tb_sch_wr">
                     <fieldset id="hd_sch">
         				   <input type="hidden" id="inq_cate" name="inq_cate" value="INQ01"/>
                           <input type="hidden" id="scText" name="scText" value="title"/>
                                  <select id="search">
                                     <option value="title">제목</option>
                                     <option value="content">내용</option>
                                  </select>
                               <input type="text" name="searchText" id="searchText" maxlength="20" placeholder="검색어를 입력해주세요">
                               <button type="submit" id="sch_submit01" value="검색">검색</button>
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
                     </tr>

                        <c:forEach items="${inquiryListOrigin }" var="iq">
                                <tr class="inquiryTr">
                                   <td class="inquirynum" style="display:none;">${iq.inq_id }</td>
                                   <td>${iq.rn }</td>
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
                        </c:forEach>
                     
                  </tbody>
               </table>
               
               <button id="generalBtn" type="button" class="btn_style_01">일반문의 작성</button>
               
            </div>
            
            <div class="pagination">
                  <c:choose>
                     <c:when test="${pageVo.page == 1 }">
                        <a class="btn_first"></a>
                     </c:when>
                     <c:otherwise>
                        <a href="${cp}/userInquiry?inq_cate=${pageVo.inq_cate }&page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
                     
                     </c:otherwise>
                  </c:choose>

                  <c:forEach begin="1" end="${paginationSizeOrigin}" var="i">
                     <c:choose>
                        <c:when test="${pageVo.page == i}">
                           <span>${i}</span>
                        </c:when>
                        <c:otherwise>
                        <a href="${cp}/userInquiry?inq_cate=${pageVo.inq_cate }&page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
                        </c:otherwise>
                     </c:choose>

                  </c:forEach>

                  <c:choose>
                     <c:when test="${pageVo.page == paginationSizeOrigin}">
                        <a class="btn_last"></a>
                     </c:when>
                     <c:otherwise>
                     <a href="${cp}/userInquiry?inq_cate=${pageVo.inq_cate }&page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
                        

                     </c:otherwise>
                  </c:choose>
            
            </div>
         </div>
      </form>
     
   </div>

   </div>
   
</section>