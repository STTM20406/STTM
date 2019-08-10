<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script>
 	$(document).ready(function(){ 
 		$(".workList_set").hide(); 
 		$(".workList_set_i").on("click", function(){ 
 			$(this).next(".workList_set").fadeIn(300); 
 		}); 
 		$(".workList_set").mouseleave(function(){ 
 			$(this).fadeOut(300); 
 		}); 
 	});
</script>


<div class="sub_menu">
	<ul class="sub_menu_item">
		<li><a href="">Work</a></li>
		<li><a href="">Gantt Chart</a></li>
		<li><a href="">Work Analysis</a></li>
		<li><a href="">File&amp;Link</a></li>
		<li><a href="">Metting</a></li>
	</ul>
	<div class="sub_btn">
		<ul>
			<li><input type="button" value="4"></li>
			<li><input type="button" value="프로젝트 대화"></li>
			<li><input type="button" value="프로젝트 설정"></li>
		</ul>
	</div>
</div>

  <style>
  
  .column {
  	width:250px;
    float: left;
    padding-bottom: 100px;
  }
  .portlet-header {
    position: relative;
  }
  .portlet-toggle {
    position: absolute;
    top: 50%;
    right: 0;
    margin-top: -8px;
  }
  
  .portlet {border:1px solid #e1e1e1}
  </style>
  
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
  
	$( function() {
    		$( "#sortable1, #sortable2" ).sortable({
      			connectWith: ".connectedSortable"
    		}).disableSelection();
  	} );
  
  	$( function() {
	    	$( ".column" ).sortable({
	      		connectWith: ".column",
	      		handle: ".portlet-header",
	      		cancel: ".portlet-toggle",
	      		placeholder: "portlet-placeholder ui-corner-all"
	    	});
	 
	    	$( ".portlet" )
	      		.addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
	      		.find( ".portlet-header" )
	       		.addClass( "ui-widget-header ui-corner-all" )
	        	.prepend( "<span class='ui-icon ui-icon-minusthick portlet-toggle'></span>");
	    		
	    		$( ".portlet-toggle" ).on( "click", function() {
	      			var icon = $( this );
	     	 		icon.toggleClass( "ui-icon-minusthick ui-icon-plusthick" );
	      			icon.closest( ".portlet" ).find( ".portlet-content" ).toggle();
	    		});
	  } );
  </script>
</head>
<body>

<section class="contents">
	<h2>${PROJECT_INFO.prj_nm}</h2>
	
	<c:forEach items="${workList}" var="workList">
	<div class="column">
		<div class="portlet">
			<div class="portlet-header"><input type="text" value="${workList.wrk_lst_nm}" id="wrkListName"></div>
			<div class="portlet-side">
				<input type="button" class="workList_add_i" value="새업무 추가">
				<a href="javascript:;" class="workList_set_i">업무리스트 설정</a>
				<div class="workList_set">
					<input type="button" value="업무리스트 삭제">
				</div>
			</div>
			<div class="portlet-status">
				<ul>
					<li>
						<p>진행중 업무 <span>4</span></p>
						<a href="javascript:;">완료된업무보기 <span>2</span></a>
					</li>
					<li class="graph"></li>
				</ul>
			</div>
			<div class="portlet-content">
				<ul id="sortable1" class="connectedSortable">
					<c:forEach items="${work}" var="work">
						<li class="ui-state-default">${work.wrk_nm}</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	</c:forEach>
</section>
 



<section class="contents">
	<div class="work_list_wrap">
		<div class="workList">
			<div class="workList_hd">
				<dl>
					<dt><input type="text" value="" id="wrkListName"></dt>
					<dd>
						<input type="button" class="workList_add_i" value="새업무 추가">
						<a href="" class="workList_set_i">업무리스트 설정</a>
						<div class="workList_set">
							<input type="button" value="업무리스트 삭제">
						</div>
					</dd>
				</dl>
				<ul>
					<li>
						<p>진행중 업무 <span>4</span></p>
						<a href="javascript:;">완료된업무보기 <span>2</span></a>
					</li>
					<li class="graph"></li>
				</ul>
			</div>
			<div class="workList_content">
				<div class="wkList_item">
					
				</div>
			</div>
		</div>
	</div>
</section>