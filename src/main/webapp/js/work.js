function commentPagination(wps_wrk_id,page, pageSize){
	$.ajax({
		url:"/comment",
		method:"post",
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data:"wps_wrk_id="+wps_wrk_id+"&page="+page+"&pageSize="+pageSize,
		success:function(data){
			var html = "";
			
			data.commentList.forEach(function(comm, index){
				console.log(data);
				html += "<tr class='commTr'>";
				html += "<input type='hidden' name='commContent' value='"+comm.comm_content +"'/>"
				html += "<input type='hidden' name='commComm_id' value='"+comm.comm_id +"'/>"
				html += "<input type='hidden' name='commPrj_id' value='"+comm.prj_id +"'/>"
				html += "<td>"+comm.comm_content+"</td>";
				html += "<td>"+comm.user_email+"</td>";
				html += "<td>"+comm.commDateStr+"</td>";
				html += "<td>";
				html += "<input type='hidden' id='prj_id02' value='"+ comm.prj_id +"'/>"
				html += "<input type='hidden' id='comm_id02' value='"+comm.comm_id +"'/>"
				if(comm.user_email == data.user_email){
				html += "<button type='button' id='commUpdateBtn' class='commUpdateBtn'>수정</button>"
				}
				html += "</td>";
				html += "<td class='commDeleteTd'>";
				html += "<input type='hidden' value='"+comm.prj_id +"'/>"
				if(comm.user_email == data.user_email){
					html += "<button type='button' id='commDeleteBtn' class='commDeleteBtn' name='"+comm.comm_id+"'>삭제</button>"
				}
				html += "</td>";
				html += "</tr>";
				
			});
			
			var pHtml = "";
			var pageVo = data.pageVo;
			
			if (pageVo.page == 1)
				pHtml += "<li class='disabled'><span>«<span></li>";
			else
				pHtml += "<li><a href='javascript:commentPagination(" + wps_wrk_id + "," + (pageVo.page - 1) + ", " + pageVo.pageSize + ");'>«</a></li>";
			for (var i = 1; i <= data.commPageSize; i++) {
				if (pageVo.page == i)
					pHtml += "<li class='active'><span>" + i + "</span></li>";
				else
					pHtml += "<li><a href='javascript:commentPagination(" + wps_wrk_id + ","+ i + ", " + pageVo.pageSize + ");'>" + i + "</a></li>";
			}
			if (pageVo.page == data.commPageSize)
				pHtml += "<li class='disabled'><span>»<span></li>";
			else
				pHtml += "<li><a href='javascript:commentPagination(" + wps_wrk_id + "," + (pageVo.page + 1) + ", " + pageVo.pageSize + ");'>»</a></li>";
				
			$(".pagination").html(pHtml);
			$("#commBody").html(html);
		}
	})
}
function commentInsert(wps_wrk_id,wps_wrk_nm,content,page, pageSize){
	$.ajax({
		url:"/commentInsert",
		method:"post",
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data:"wps_wrk_id="+wps_wrk_id+"&wps_wrk_nm="+wps_wrk_nm+"&comm_content="+content+"&page="+page+"&pageSize="+pageSize,
		success:function(data){
			if(socket){
				var socketMsg = "";
				for(var i=0;i<data.data.length;i++){
					socketMsg = "wrk_comment," + data.data[i].user_email +"," + data.data3.wrk_nm;
					socket.send(socketMsg);
				}
				
				for(var i=0;i<data.data2.length;i++){
					socketMsg = "wrk_comment," + data.data2[i].user_email +"," + data.data3.wrk_nm;
					socket.send(socketMsg);
				}
				// websocket에 보내기!!
			}
			commentPagination(wps_wrk_id,1, 10);
		}
	})
}
function updateTest(inq_trim02,prj_id,comm_id){
	$.ajax({
		url:"/commUpdate",
		method:"get",
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data : "inq_trim="+inq_trim02+"&prj_id="+prj_id+"&comm_id="+comm_id,
		success:function(data){
			
		}
	})
}
function commDeleteAjax(wps_wrk_id,comm_id){
	$.ajax({
		url:"/commDelete",
		method:"post",
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data:"wps_wrk_id=" + wps_wrk_id +"&comm_id="+comm_id,
		success:function(data){
			commentPagination(wps_wrk_id,1, 10);
		
		}
	})	
}
function propertyWorkSetAjax(wrk_id){
	$.ajax({
		url:"/work/propertyWorkSetAjax",
		method:"post",
		data:"wrk_id=" + wrk_id,
		success:function(data){
			var auth = data.workVo.auth;
			$("#wps_id").val(data.workVo.wrk_id);
			$("#wps_nm").val(data.workVo.wrk_nm);
			$("#wps_write_nm").text(data.workVo.user_nm);
			$("#wps_write_date").text(data.workVo.wrkDtStr);
			$("#wps_start_date").val(data.workVo.wrkStartDtStr + " ~ " + data.workVo.wrkEndDtStr);
			$("#wrk_gd").val(data.workVo.wrk_grade);
			$(".wrk_color").removeClass("colorSelect");
			
			$(data.workVo.wrk_color_cd).prev().addClass("colorSelect");
			$(data.workVo.wrk_color_cd).prop("checked", true);
			
			var html2 = "";
			if(data.getWrokPush != null){
				html2 += "<p class='resDate' id='"+data.workVo.wrk_rv_id+"'>"+data.getWrokPush.wrkDtStr+" | <span class='pushDel'>삭제</span></p>";
				$(".notifyCon").html(html2);
				$("#resNotifyBtn").css({display:"none"});
			}else{
				html2 = "<input type='button' id='resNotifyBtn' value=예약알림추가'>";
				$(".notifyCon").html(html2);
				$(".resDate").css({display:"none"});
			}
			
			//배정된 업무 멤버 불러오기
			var html = "";
			data.wrkMemList.forEach(function(item, index){
				html += "<li id='"+ item.user_email +"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='wrkMemDel' value='삭제'></li>";
			});	
			$(".wrk_add_box").html(html);
			
			//업무 팔로워 멤버 불러오기
			var html = "";
			data.wrkFlwList.forEach(function(item, index){
				html += "<li id='"+ item.user_email +"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='wrkFlwDel' value='삭제'></li>";
			});	
			$(".wrk_mem_flw_box").html(html);
			
			//멤버레벨이 1인데 권한이 ASC02 또는 ASC03 일때
			if(auth=="AUTH02"){
				$(".propertySet input").prop('readonly', true); 										//설정창의 모든 input readonly
				$(".propertySet select").prop('disabled',true);										//설정창의 모든 select disabled
				$(".propertySet button[name='wps_mem_set']").prop('disabled', true);					//설정창의 모든 button disabled
				$(".propertySet button[name='wrk_flw_set']").prop('disabled', true);					//설정창의 모든 button disabled
				$(".propertySet input[type=button]").prop('disabled', true);
				$(".flatpickr-calendar").css({display:"none"});
			}else if(auth=="AUTH03"){
				$(".propertySet input").prop('readonly', true); 										//설정창의 모든 input readonly
				$(".propertySet select").prop('disabled',true);										//설정창의 모든 select disabled
				$(".propertySet button").prop('disabled', true);										//설정창의 모든 button disabled
				$(".propertySet input[type=button]").prop('disabled', true);
				$(".propertySet input[type=radio]").prop('disabled', true);
				$(".flatpickr-calendar").css({display:"none"});
			}
//				else{
//					$(".propertySet input").prop('readonly', false);
//					$(".propertySet select").prop('disabled',false);
//					$(".propertySet button").prop('disabled', false);
//					$(".prj_add_box input").css({visibility:"visible"});
//					$(".prj_mem_add_box input").css({visibility:"visible"});
//					$(".propertySet input[type=button]").prop('disabled', false);
//					$(".datepicker").css({display:"block"});
//				}
		}
	});
}
//업무 설정 업데이트 ajax
function propertyWorkSetItemAjax(projectWorkSet){
	$.ajax({
		url:"/work/propertyWorkSetItemAjax",
		method:"post",
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		data:"wrk_id=" + projectWorkSet.id +
			"&wrk_nm=" + projectWorkSet.name +
			"&wrk_grade=" + projectWorkSet.wrk_gd +
			"&wrk_start_dt=" + projectWorkSet.work_start_dt +
			"&wrk_end_dt=" + projectWorkSet.work_end_dt,
		success:function(data){
			$(".workListItem").each(function() {
				var wrkID = $(this).attr("data-wrkid");
				if(wrkID == projectWorkSet.id){
					$(this).find(".wrk_title").html("<span>"+data.data.wrk_grade+"</span>" + data.data.wrk_nm);
					$(this).find(".wrk_date").text(data.data.wrkStartDtStr +" ~ "+ data.data.wrkEndDtStr );
				}
			});
		}
	});
}
//업무 라벨 색상 변경
function propertyWorkLableColorAjax(id, wrk_color){
		console.log(wrk_color);
		var changeColor = encodeURIComponent(wrk_color);
	$.ajax({
		url:"/work/propertyWorkLableColorAjax",
		method:"post",
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		data:"wrk_id=" + id + "&wrk_color_cd=" + changeColor,
		success:function(data){
			$(".workListItem").each(function() {
				var wrkID = $(this).attr("data-wrkid");
				if(wrkID == id){
					$(this).css({borderTop:"3px solid" + wrk_color});
				}
			});
		}
	});
}

//업무 멤버 가져오는 ajax
function workMemListAjax(wrkID){
	$.ajax({
		url:"/work/workMemListAjax",
		method:"post",
		data: "wrk_id="+ wrkID,
		success:function(data){
			
			var html = "";
			data.projectMemList.forEach(function(item, index){
				//html 생성
				html += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
			});	
			$(".wrk_mem_item").html(html);
		}
	});
}

//배정된 멤버로 선택한 멤버 추가
function workMemAddAjax(wrkID, mem_add_email){
	$.ajax({
		url:"/work/workMemAddAjax",
		method:"post",
		data: "wrk_id="+ wrkID + "&user_email=" + mem_add_email,
		success:function(data){
			console.log(data);
			
			var html = "";
			var html2 = "";
			var html3 = "";
			var html4 = "";
			
			data.wrkFlwList.forEach(function(item, index){
				html += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
			});	
			
			data.projectFlwList.forEach(function(item, index){
				html2 += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
			});	
			
			data.wrkMemList.forEach(function(item, index){
				html3 += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
			});
			
			data.projectMemList.forEach(function(item, index){
				html4 += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
			});	
			
			
			$(".wrk_mem_flw_box").html(html);
			$(".wrk_flw_item_list").html(html2);
			$(".wrk_add_box").html(html3);
			$(".wrk_mem_item").html(html4);
		}
	});
}

function workMemDelAjax(id, email){
	$.ajax({
		url:"/work/workMemDelAjax",
		method:"post",
		data:"wrk_id="+ id + "&user_email=" + email,
		success:function(data){
			
			var html = "";
			var html2 = "";
			
			data.wrkMemList.forEach(function(item, index){
				html += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
			});	
			
			data.projectMemList.forEach(function(item, index){
				html2 += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
			});	
			
			$(".wrk_add_box").html(html);
			$(".wrk_mem_item").html(html2);
		}
	});
}

//팔로워 멤버 가져오는 ajax
function workFlwListAjax(wrkID){
	$.ajax({
		url:"/work/workFlwListAjax",
		method:"post",
		data: "wrk_id="+ wrkID,
		success:function(data){
			var html = "";
			data.projectMemList.forEach(function(item, index){
				//html 생성
				html += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
			});	
			$(".wrk_flw_item_list").html(html);
		}
	});
}


//팔로우 멤버로 멤버 추가
function workFlwAddAjax(wrkID, mem_add_email){
	$.ajax({
		url:"/work/workFlwAddAjax",
		method:"post",
		data: "wrk_id="+ wrkID + "&user_email=" + mem_add_email,
		success:function(data){
			
			var html = "";
			var html2 = "";
			var html3 = "";
			var html4 = "";
			
			data.wrkFlwList.forEach(function(item, index){
				html += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
			});	
			
			data.projectFlwList.forEach(function(item, index){
				html2 += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
			});	
			
			data.wrkMemList.forEach(function(item, index){
				html3 += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
			});
			
			data.projectMemList.forEach(function(item, index){
				html4 += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
			});	
			
			
			$(".wrk_mem_flw_box").html(html);
			$(".wrk_flw_item_list").html(html2);
			$(".wrk_add_box").html(html3);
			$(".wrk_mem_item").html(html4);
		}
	});
}

//예약알림
function notificationAddAjax(notifyMem, notifyDate, wrkID){
	$.ajax({
		url:"/work/notificationAddAjax",
		method:"post",
		data:"wrk_id="+ wrkID + "&memType=" + notifyMem + "&wrk_dt=" + notifyDate,
		success:function(data){
			
			$(".notifyArea").fadeOut(100);
			$("#resNotifyBtn").css({display:"none"});
			
			var html = "<p class='resDate' id='"+data.getWrokPush.wrk_rv_id+"'>"+data.getWrokPush.wrkDtStr+" | <span class='pushDel'>삭제</span></p>";
			
			$(".notifyCon").html(html)
		}
	});
};

//예약알림삭제
function notificationDelAjax(wrk_rv_id){
	$.ajax({
		url:"/work/notificationDelAjax",
		method:"post",
		data:"wrk_rv_id="+ wrk_rv_id,
		success:function(data){
			
			var html = "<input type='button' id='resNotifyBtn' value=예약알림추가'>";
			$(".resDate").css({display:"none"});
			$(".notifyCon").html(html)
		}
	});
};

//업무 배정된 멤버 삭제 클릭 했을 때
function workFlwDelAjax(id, email){
	$.ajax({
		url:"/work/workFlwDelAjax",
		method:"post",
		data:"wrk_id="+ id + "&user_email=" + email,
		success:function(data){
			
			var html = "";
			var html2 = "";
			
			data.wrkFlwList.forEach(function(item, index){
				html += "<li id='"+item.user_email+"_"+item.wrk_id+"'>"+ item.user_nm +"<input type='button' class='memDel' value='삭제'></li>";
			});	
			
			data.projectMemList.forEach(function(item, index){
				html2 += "<li id='"+ item.user_email +"'><span>"+ item.user_nm +"</span>"+ item.user_email + "</li>";
			});	
			
			$(".wrk_mem_flw_box").html(html);
			$(".wrk_flw_item_list").html(html2);
		}
	});
}

function workFilePagination(page, pageSize, wrk_id) {
	$.ajax({
		url : "/workFilePagination",
		method : "post",
		data : "page=" + page + "&pageSize=" + pageSize + "&wrk_id="+ wrk_id,
		success : function(data) {
			//사용자 리스트
			var hhtml = "";
			var html = "";

			//hhtml생성
			hhtml += "<tr>";
			hhtml += "<th>파일명</th>";
			hhtml += "<th>공유한 멤버 이름</th>";
			hhtml += "<th>등록한 날짜</th>";
			hhtml += "<th>삭제</th>";
			hhtml += "</tr>";

			var user_email = data.user_email;
			data.workFileList.forEach(function(file, index) {
				//html생성
				html += "<tr id='filetr'>";
				html += "<td><a href='/fileDownLoad?file_id="+file.file_id+"'>" + file.original_file_nm+ "</a></td>";
				html += "<td>" + file.user_nm + "</td>";
				html += "<td>" + file.prjStartDtStr + "</td>";
				if(file.user_email == user_email){
					html += "<td><a href='javascript:workDelFile("+ file.file_id + "," + file.wrk_id+ ")'>삭제</a></td>";
				}else{
					html += "<td>삭제</td>";
				}
				html += "</tr>";	
			});
			var pHtml = "";
			var pageVo = data.pageVo;

			if (pageVo.page == 1)
				pHtml += "<li class='disabled'><span>«<span></li>";
			else
				pHtml += "<li><a href='javascript:workFilePagination("
						+ (pageVo.page - 1) + ", " + pageVo.pageSize + ","
						+ wrk_id + ");'>«</a></li>";
			for (var i = 1; i <= data.paginationSize; i++) {
				if (pageVo.page == i)
					pHtml += "<li class='active'><span>" + i
							+ "</span></li>";
				else
					pHtml += "<li><a href='javascript:workFilePagination("
							+ i + ", " + pageVo.pageSize + "," + wrk_id
							+ ");'>" + i + "</a></li>";
			}
			if (pageVo.page == data.paginationSize)
				pHtml += "<li class='disabled'><span>»<span></li>";
			else
				pHtml += "<li><a href='javascript:workFilePagination("
						+ (pageVo.page + 1) + ", " + pageVo.pageSize + ","
						+ wrk_id + ");'>»</a></li>";
			$(".pagination").html(pHtml);
			$("#publicHeader").html(hhtml);
			$("#publicList").html(html);
		}
	});
}

function workLinkPagination(page, pageSize, wrk_id) {
	$.ajax({
		url : "/workLinkPagination",
		method : "post",
		data : "page=" + page + "&pageSize=" + pageSize + "&wrk_id="
				+ wrk_id,
		success : function(data) {
			//사용자 리스트
			var hhtml = "";
			var html = "";

			//hhtml생성
			hhtml += "<tr>";
			hhtml += "<th>링크명</th>";
			hhtml += "<th>공유한 멤버 이름</th>";
			hhtml += "<th>등록한 날짜</th>";
			hhtml += "<th>삭제</th>";
			hhtml += "</tr>";

			var user_email = data.user_email;
			data.workLinkList.forEach(function(link, index) {
				//html생성
				html += "<tr>";
				html += "<td><a href='https://"+link.attch_url+"' target='_blank'>"+ link.attch_url + "</a></td>";
				html += "<td>" + link.user_nm + "</td>";
				html += "<td>" + link.prjStartDtStr + "</td>";
				if(link.user_email==data.user_email){
					html += "<td><a href='javascript:workDelLink("+ link.link_id + "," + link.wrk_id+ ")'>삭제</a></td>";
				}else{
					html += "<td>삭제</td>";
				}
				html += "</tr>";
			});
			var pHtml = "";
			var pageVo = data.pageVo;
			console.log(data);
			console.log(pageVo);

			if (pageVo.page == 1)
				pHtml += "<li class='disabled'><span>«<span></li>";
			else
				pHtml += "<li><a href='javascript:workLinkPagination("
						+ (pageVo.page - 1) + ", " + pageVo.pageSize + ","
						+ wrk_id + ");'>«</a></li>";

			for (var i = 1; i <= data.paginationSize; i++) {
				if (pageVo.page == i)
					pHtml += "<li class='active'><span>" + i
							+ "</span></li>";
				else
					pHtml += "<li><a href='javascript:workLinkPagination("
							+ i + ", " + pageVo.pageSize + "," + wrk_id
							+ ");'>" + i + "</a></li>";
			}
			if (pageVo.page == data.paginationSize)
				pHtml += "<li class='disabled'><span>»<span></li>";
			else
				pHtml += "<li><a href='javascript:workLinkPagination("
						+ (pageVo.page + 1) + ", " + pageVo.pageSize + ","
						+ wrk_id + ");'>»</a></li>";
			$(".pagination").html(pHtml);
			$("#publicHeader").html(hhtml);
			$("#publicList").html(html);
		}
	});
}

function workDelFile(fileID, wrk_id) {
	$.ajax({
		url : "/workDelFile",
		method : "post",
		data : "file_id=" + fileID + "&wrk_id=" + wrk_id,
		success : function(data) {
			//사용자 리스트
			var hhtml = "";
			var html = "";

			//hhtml생성
			hhtml += "<tr>";
			hhtml += "<th>파일명</th>";
			hhtml += "<th>공유한 멤버 이름</th>";
			hhtml += "<th>등록한 날짜</th>";
			hhtml += "<th>삭제</th>";
			hhtml += "</tr>";

			var user_email = data.user_email;
			data.workFileList.forEach(function(file, index) {
				//html생성
				html += "<tr id='filetr'>";
				html += "<td><a href='/fileDownLoad?file_id="+file.file_id+"'>" + file.original_file_nm+ "</a></td>";
				html += "<td>" + file.user_nm + "</td>";
				html += "<td>" + file.prjStartDtStr + "</td>";
				if(file.user_email == user_email){
					html += "<td><a href='javascript:workDelFile("+ file.file_id + "," + file.wrk_id+ ")'>삭제</a></td>";
				}else{
					html += "<td>삭제</td>";
				}
				html += "</tr>";
			});
			var pHtml = "";
			var pageVo = data.pageVo;
			console.log(data);
			console.log(pageVo);

			if (pageVo.page == 1)
				pHtml += "<li class='disabled'><span>«<span></li>";
			else
				pHtml += "<li><a href='javascript:workFilePagination("
						+ (pageVo.page - 1) + ", " + pageVo.pageSize + ","
						+ wrk_id + ");'>«</a></li>";
			for (var i = 1; i <= data.paginationSize; i++) {
				if (pageVo.page == i)
					pHtml += "<li class='active'><span>" + i
							+ "</span></li>";
				else
					pHtml += "<li><a href='javascript:workFilePagination("
							+ i + ", " + pageVo.pageSize + "," + wrk_id
							+ ");'>" + i + "</a></li>";
			}
			if (pageVo.page == data.paginationSize)
				pHtml += "<li class='disabled'><span>»<span></li>";
			else
				pHtml += "<li><a href='javascript:workFilePagination("
						+ (pageVo.page + 1) + ", " + pageVo.pageSize + ","
						+ wrk_id + ");'>»</a></li>";
			$(".pagination").html(pHtml);
			$("#publicHeader").html(hhtml);
			$("#publicList").html(html);
		}
	});
}

function workDelLink(linkID, wrk_id) {
	$.ajax({
		url : "/workDelLink",
		method : "post",
		data : "link_id=" + linkID + "&wrk_id=" + wrk_id,
		success : function(data) {
			//사용자 리스트
			var hhtml = "";
			var html = "";

			//hhtml생성
			hhtml += "<tr>";
			hhtml += "<th>링크명</th>";
			hhtml += "<th>공유한 멤버 이름</th>";
			hhtml += "<th>등록한 날짜</th>";
			hhtml += "<th>삭제</th>";
			hhtml += "</tr>";

			var user_email = data.user_email;
			data.workLinkList.forEach(function(link, index) {
				//html생성
				html += "<tr>";
				html += "<td><a href='https://"+link.attch_url+"' target='_blank'>"
						+ link.attch_url + "</a></td>";
				html += "<td>" + link.user_nm + "</td>";
				html += "<td>" + link.prjStartDtStr + "</td>";
				if(link.user_email==data.user_email){
					html += "<td><a href='javascript:workDelLink("+ link.link_id + "," + link.wrk_id+ ")'>삭제</a></td>";
				}else{
					html += "<td>삭제</td>";
				}
				html += "</tr>";
			});
			var pHtml = "";
			var pageVo = data.pageVo;
			console.log(data);
			console.log(pageVo);

			if (pageVo.page == 1)
				pHtml += "<li class='disabled'><span>«<span></li>";
			else
				pHtml += "<li><a href='javascript:workLinkPagination("
						+ (pageVo.page - 1) + ", " + pageVo.pageSize + ","
						+ wrk_id + ");'>«</a></li>";

			for (var i = 1; i <= data.paginationSize; i++) {
				if (pageVo.page == i)
					pHtml += "<li class='active'><span>" + i
							+ "</span></li>";
				else
					pHtml += "<li><a href='javascript:workLinkPagination("
							+ i + ", " + pageVo.pageSize + "," + wrk_id
							+ ");'>" + i + "</a></li>";
			}
			if (pageVo.page == data.paginationSize)
				pHtml += "<li class='disabled'><span>»<span></li>";
			else
				pHtml += "<li><a href='javascript:workLinkPagination("
						+ (pageVo.page + 1) + ", " + pageVo.pageSize + ","
						+ wrk_id + ");'>»</a></li>";
			$(".pagination").html(pHtml);
			$("#publicHeader").html(hhtml);
			$("#publicList").html(html);
		}
	});
}
//로드시--------------------------------
$(function() {
	// 업무 코멘트 시작점
		// 업무코멘트 수정
		$(".workTab").on("click", "#commentId", function(){
			var wps_wrk_id = $('#wps_wrk_id').val();
			commentPagination(wps_wrk_id,1, 10);
		})
		
		
		
		$("#commBody").on("click",".commUpdateBtn", function() {
			var commprid = $(this).siblings("input").val();
			
 			var inq_id = $(this).parent().prev().prev().prev().text();
 			var inq_trim = $.trim(inq_id);
 			$(this).parent().prev().prev().prev().replaceWith("<td><input type='text' name='updateComm' id='changeInput' value='"+inq_trim+"'/></td>");
 			$(this).replaceWith("<button type='button' id='commUpdateChgBtn' class='commUpdateChgBtn'>수정완료</button>");
 			
			$("#commBody").on("click","#commUpdateChgBtn", function(){
	 			var changVal = $("#changeInput").val();
								
				var inq_trim02 = $.trim(changVal);
				var prj_id = $(this).siblings("#prj_id02").val();
				var comm_id = $(this).siblings("#comm_id02").val();
				
				updateTest(inq_trim02,prj_id,comm_id);
				$(this).parent().prev().prev().prev().replaceWith("<td><p>"+inq_trim02+"</p></td>");
				$(this).replaceWith("<button type='button' id='commUpdateBtn' class='commUpdateBtn'>수정</button>");
			})
		})
		
		
		
		// 코멘트 삭제하기
		$("#commBody").on("click", "#commDeleteBtn", function(){
			var wps_wrk_id = $('#wps_wrk_id').val();
			var commDelete = $(this).attr("name");
			var comm_id = commDelete;
			
			commDeleteAjax(wps_wrk_id,comm_id);
		})
		
		
		
		// 댓글등록하기 버튼
		$("#replyBtn").on("click",function(){
			var wps_wrk_id = $('#wps_wrk_id').val();
			var wps_wrk_nm = $('#wps_nm').val();
			var content = $('#comm_content').val();
			commentInsert(wps_wrk_id,wps_wrk_nm,content,1,10);
		})
		
		
		//프로젝트 닫기 버튼을 클릭했을 때
		$(".btnSetClose").on("click", function(){
			$("#propertyWorkSet").animate({right:'-700px'}, 500);
		});
		
		
		
		//업무 클릭 했을 때 처음 보여주는 탭은 설정 탭
		$(".propertySetWrap div:nth-child(3)").show();

		$(".workTab ul.tabs li:nth-child(1)").css({background:"#dee2e7"});
		//탭 버튼 클릭시 div 변경
		$(".workTab").on("click", "ul.tabs li", function () {
		    	$(".workTab ul.tabs li").removeClass("active").css({background:"#fff", color:"#2e3f57"});
		    	$(this).addClass("active").css({background:"#dee2e7"});
		    	$(".tab_content").hide()
		    	var activeTab = $(this).attr("data-tab");
		    	$("#" + activeTab).fadeIn()
		});
		
		
		var wrk_color = "";
		
		
		/* 여기서부터 업무 셋팅 업데이트를 위한 이벤트 핸들러 입니다. */
		$("#propertyWorkSet input, #propertyWorkSet select").on("propertychange keyup change click", function(){
			
			//프로젝트 셋팅 값 가져오기
			var id = $("#wps_id").val();
			var name = $("#wps_nm").val();
			var work_date = $("#wps_start_date").val();
			var res_date = $("#wps_res_date").val();
			var wrk_gd = $("#wrk_gd").val();
			
			var workSplit = work_date.split(" to ");
			var resSplit = res_date.split(" to ");
			
			var work_start_dt = workSplit[0];
			var work_end_dt = workSplit[1];
			
			if(!name){
				$(".ctxt").text("업무 이름을 작성해 주세요.");
 				layer_popup("#layer2");
				return false;
			}
			
			if(typeof work_start_dt == "undefined" || typeof work_end_dt == "undefined"){
				work_start_dt = "";
				work_end_dt = "";
			}
			
			var projectWorkSet = {
							  id : id
						  	, name : name
						  	, work_start_dt : work_start_dt
						 	, work_end_dt : work_end_dt
						  	, wrk_gd : wrk_gd
			}
			
			propertyWorkSetItemAjax(projectWorkSet);
		});
		
		
		//레이블 색상 선택 (라디오버튼 선택 / 선택해제)
		
		var beforeChecked = -1;
		
      		$(".lableColor").on("click", "input[type=radio]", function(e) {
      			var id = $("#wps_id").val();
         		var index = $(this).prev().index("label");
         		$(this).prev().removeClass("colorSelect");
         		
         		if(beforeChecked == index) {
	         		beforeChecked = -1;
	         		$(this).prop("checked", false);
	         		wrk_color = $("input:radio[name='wrk_color_cd']:checked").val();
	         		console.log(wrk_color);
         		}else{
	         		beforeChecked = index;
	         		wrk_color = $("input:radio[name='wrk_color_cd']:checked").val();
	         		$(".wrk_color").removeClass("colorSelect");
	         		$(this).prev().addClass("colorSelect");
         		}
         		
         		if(typeof wrk_color == "undefined"){
    				wrk_color = "";
    			}else{
    				wrk_color = $("input:radio[name='wrk_color_cd']:checked").val();
    			}
         		
         		propertyWorkLableColorAjax(id, wrk_color);
	      	});
      		
      		
		
      		//예약알림 버튼 클릭시
      		$(".notifyCon").on("click", "#resNotifyBtn", function(){
      			$(".notifyArea").fadeIn(100);
      		});
      		
      		//예약알림 취소 버튼 클릭시
      		$("#notificationCancelBtn").on("click", function(){
      			$(".notifyArea").fadeOut(100);
      		});
      		
		//날짜 선택 - 예약알림
		$(".flatpickr1").flatpickr({
			inline: true,
		    	minDate: "today",
		    	enableTime: true
		});
		
		//날짜 선택 - 시작일, 종료일
		$(".flatpickr").flatpickr({
		    	mode: "range",
		    	minDate: "today",
		    	enableTime: true
		});
		
		
		$("#memClose").on("click", function(){
			$(".wrk_add_mem").fadeOut(300);
		});
		
		$("#flwClose").on("click", function(){
			$(".wrk_add_flw").fadeOut(300);
		});
		
		
		// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중
		
		$('#workLink').hide(0);
		
		$(".workTab").on("click", "#FL", function(){
			var wrk_id = $('#wps_wrk_id').val();
			workFilePagination(1, 5, wrk_id);
		})
	
		$(".tab_sub_menu").on("click", "#fileList",function(){
			var wrk_id = $('#wps_wrk_id').val();
			$('#locker').fadeIn(0);
			$('#workLink').fadeOut(0);
			$('#workFile').fadeIn(0);
			workFilePagination(1, 5, wrk_id);
		})
		
		$(".tab_sub_menu").on("click", "#linkList",function(){
			$('#locker').hide(0);
			var wrk_id = $('#wps_wrk_id').val();
			$('#workFile').fadeOut(0);
			$('#workLink').fadeIn(0);
			workLinkPagination(1, 5,wrk_id);
		})
		
		
		$("#workLink").on('click','#uploadLink', function(){
			var attch_url = $('.link').val();
			var locker = $("#locker input[type=radio]:checked").val();
			var wrk_id = $('#wps_wrk_id').val();
			
			$('#box').val(locker);
			$('#work').val(wrk_id);
			
			$.ajax({
	 		    url: "/workLinkUpload",
	 		    type: "POST",
	 		    data: "attch_url=" + attch_url + "&locker=" + locker + "&wrk_id="+ wrk_id,
	 		    contentType:"application/x-www-form-urlencoded; charset=UTF-8",
	 		    success: function(data){
	 		    	workLinkPagination(1, 5, wrk_id);
	 		    },
	 		    error: function(e){
	 		        alert(e.reponseText);
	 		    }
	 		});
		})
		
		$("#workFile").on('click','#uploadFile', function(){

			var form = document.getElementById("frm");
			console.log(form);
			form.method = "POST";
			form.enctype = "multipart/form-data";
			
			var locker = $("#locker input[type=radio]:checked").val();
			var wrk_id = $('#wps_wrk_id').val();
			$('#box').val(locker);
			$('#work').val(wrk_id);
			var formData  = new FormData(form);
			$(".ctxt").text("파일 업로드 완료!!!");
				layer_popup("#layer2");
			$.ajax({
	 		    url: "/workFileUpload",
	 		    type: "POST",
	 		    data: formData,
				cache : false,		
	 		    contentType: false,
	 		    processData: false,
	 		    success: function(data){
	 		    	workFilePagination(1, 5, wrk_id);
	 		    	
	 		    	if(socket){
	 					var socketMsg = "";
	 					for(i=0;i<data.wrkFlwList.length;i++){
	 						socketMsg = "file&link," + data.wrkMemList[i].user_email +"," + data.workFileList.wrk_nm;
	 						socket.send(socketMsg);
	 					}
	 					
	 					for(var i=0;i<data.wrkMemList.length;i++){
	 						socketMsg = "file&link," + data.wrkFlwList[i].user_email +"," + data.workFileList.wrk_nm;
	 						socket.send(socketMsg);
	 					}
	 					// websocket에 보내기!!
	 				}
	 		    },
	 		    error: function(e){
	 		        alert(e.reponseText);
	 		    }
	 		});
		});
		
		// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중// file, link 등록 부분 구현중
		
		//업무 멤버 추가하기 버튼 클릭시 해당 프로젝트 멤버 가져오기
		$(".wrk_add_mem").fadeOut(0); //멤버리스트 layer 숨기기
		$("#wps_mem_set").on("click", function(){
			var wrkID = $("#wps_id").val();
			$(".wrk_add_flw").fadeOut(0);
			$(".wrk_add_mem").fadeIn(300);
			workMemListAjax(wrkID);
		});
		
		
		
		//업무 멤버 클릭 했을 때
		$(".wrk_mem_item").on("click", "li", function(){
			var mem_add_email = $(this).attr("id");
			var wrkID = $("#wps_id").val();
			workMemAddAjax(wrkID, mem_add_email);	
		});
		
		
		
		//업무 배정된 멤버 삭제 클릭 했을 때
		$(".wrk_add_box").on("click", "li input", function(){
			var textSplit = $(this).parent().attr("id").split("_");
			var id = textSplit[1];
			var email = textSplit[0];
			workMemDelAjax(id, email);
		});
		
		
		
		//팔로워 멤버 추가하기 버튼 클릭시 해당 프로젝트 멤버 가져오기
		$(".wrk_add_flw").fadeOut(0); //멤버리스트 layer 숨기기
		$("#wrk_flw_set").on("click", function(){
			var wrkID = $("#wps_id").val();
			$(".wrk_add_flw").fadeIn(300);
			$(".wrk_add_mem").fadeOut(0);
			workFlwListAjax(wrkID);
		});
		
		
		
		
		//팔로워 멤버 클릭 했을 때
		$(".wrk_flw_item_list").on("click", "li", function(){
			var mem_add_email = $(this).attr("id");
			var wrkID = $("#wps_id").val();
			
			workFlwAddAjax(wrkID, mem_add_email);	
		});
		
		//업무 배정된 멤버 삭제 클릭 했을 때
		$(".wrk_mem_flw_box").on("click", "li input", function(){
			var textSplit = $(this).parent().attr("id").split("_");
			var id = textSplit[1];
			var email = textSplit[0];
			workFlwDelAjax(id, email);
		});
		
		
		//예약알림
		$("#notificationAddBtn").on("click", function(){
			var notifyMem = $("#notificationMem option:selected").val();
			var notifyDate = $("#wps_res_date").val();
			var wrkID = $("#wps_id").val();
			
			notificationAddAjax(notifyMem, notifyDate, wrkID);
		});
		
		//예약알림 삭제 클릭시
		$(".notifyCon").on("click", ".pushDel", function(){
			var wrk_rv_id = $(this).parent().attr("id");
			notificationDelAjax(wrk_rv_id);
		});
		
});
