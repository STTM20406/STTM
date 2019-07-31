<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href='/css/fullcalendar.min.css'>
<link rel="stylesheet" href='/css/main.css'>
<link rel="stylesheet" href='/css/material-icon.css'>
<link rel="stylesheet" href='/css/roboto.css'>
<link rel="stylesheet" href='/css/select2.min.css'>


<div class="container">
   <input type="hidden" id="userName" value="${param.name}">
   <!-- 일자 클릭시 메뉴오픈 -->
   <div id="contextMenu" class="dropdown clearfix">
      <ul class="dropdown-menu dropNewEvent" role="menu" aria-labelledby="dropdownMenu"
         style="display: block; position: static; margin-bottom: 5px;">
         <li><a tabindex="-1" href="#">일정 등록</a></li>
      </ul>
   </div>

   <div id="wrapper">
      <div id="loading"></div>
      <div id="calendar"></div>
   </div>

   <!-- 일정 추가 MODAL -->
   <div class="modal fade" tabindex="-1" role="dialog" id="eventModal">
         <div class="modal-dialog" role="document">
      <form id="frm" action="/calendarPost" method="post">
            <div class="modal-content">
               <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal"
                     aria-label="Close">
                     <span aria-hidden="true">&times;</span>
                  </button>
                  <h4 class="modal-title"></h4>
               </div>
               <div class="modal-body">

                  <div class="row">
                     <div class="col-xs-12">
                        <label class="col-xs-4" for="edit-title">일정 명</label> <input
                           class="inputModal" type="text" name="wrk_nm"
                           id="edit-title" required="required" />
                     </div>
                  </div>
                  <div class="row">
                     <div class="col-xs-12">
                        <label class="col-xs-4" for="edit-start">업무 시작일</label> <input
                           class="inputModal" type="text" name="start_dt"
                           id="edit-start" />
                     </div>
                  </div>
                  <div class="row">
                     <div class="col-xs-12">
                        <label class="col-xs-4" for="edit-end">업무 종료일</label> <input
                           class="inputModal" type="text" name="end_dt" id="edit-end" />
                     </div>
                  </div>

                  <div class="row">
                     <div class="col-xs-12">
                        <label class="col-xs-4" for="edit-type">업무 리스트</label> <select
                           class="inputModal" name="wrk_lst_id" id="edit-type">
	                           <c:forEach items="${workList}" var="WL">
	                              <option value="${WL.wrk_lst_id}">${WL.wrk_lst_nm}</option>
	                           </c:forEach>
                        </select> 
                     </div>
                  </div>
                  <div class="row">
                     <div class="col-xs-12">
                        <label class="col-xs-4" for="edit-color">색상</label> <select
                           class="inputModal" name="wrk_color_cd" id="edit-color">
                           <option value="#D25565" style="color: #D25565;">빨간색</option>
                           <option value="#9775fa" style="color: #9775fa;">보라색</option>
                           <option value="#ffa94d" style="color: #ffa94d;">주황색</option>
                           <option value="#74c0fc" style="color: #74c0fc;">파란색</option>
                           <option value="#f06595" style="color: #f06595;">핑크색</option>
                           <option value="#63e6be" style="color: #63e6be;">연두색</option>
                           <option value="#a9e34b" style="color: #a9e34b;">초록색</option>
                           <option value="#4d638c" style="color: #4d638c;">남색</option>
                           <option value="#495057" style="color: #495057;">검정색</option>
                        </select>
                     </div>
                  </div>
               </div>
               <div class="modal-footer modalBtnContainer-addEvent">
                  <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
                  <button type="submit" class="btn btn-primary" id="save-event">저장</button>
               </div>
               <div class="modal-footer modalBtnContainer-modifyEvent">
                  <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
                  <button type="button" class="btn btn-danger" id="deleteEvent">삭제</button>
                  <button type="button" class="btn btn-primary" id="updateEvent">저장</button>
               </div>
            </div>
      </form>
         </div>
   </div>
   <!-- /.modal -->
</div>

<!-- /.container -->
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/moment.min.js"></script>
<script src="/js/fullcalendar.min.js"></script>
<script src="/js/ko.js"></script>
<script src="/js/select2.min.js"></script>
<script src="/js/bootstrap-datetimepicker.min.js"></script>
<script src="/js/main.js"></script>
<script src="/js/addEvent.js"></script>
<script src="/js/editEvent.js"></script>
<script src="/js/etcSetting.js"></script>

