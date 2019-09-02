<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- date picker resource-->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=253080e34ec6fc99f7cfccec73533ca8&libraries=services"></script>

<div class="sub_menu">
	<ul class="sub_menu_item">
		<li><a href="/work/list">Work</a></li>
		<li><a href="/gantt/project">Gantt Chart</a></li>
		<li><a href="/analysis">Work Analysis</a></li>
		<li><a href="/publicFilePagination">File&amp;Link</a></li>
		<li><a href="/meeting/view">Meeting</a></li>
		<li><a href="/vote">Vote</a></li>
		<li><a href="/conferenceList">Minutes</a></li>
	</ul>
</div>

<section class="contents">
<h2 class="contentTitle">미팅장소</h2>
<div class="mapWrap">
	<div class="mapBox">
		<div id="map2"></div>
		<a href="#layer20" class="btn-example addPlace">장소 추가</a>
		<div id="meetings"></div>
	</div>
</div>
</section>

<script src="/js/kakaomap2.js"></script>
<script>

	function meetingList() {
		$.ajax({
			url: '/meeting/ajax',
			type: "GET",
			success: function(data){
				$("#meetings").html(data.data);
			},
			dataType: "json"
		})
	};
	
	meetingList();
	
	$("#meetings").on("click", ".meetingItem", function() {
		var lat = $(this).find("#lat").val();
		var lng = $(this).find("#lng").val();
		
		if(map2markers.length!=0)
			map2markers[0].setMap(null);
		
		var markerPosition = new kakao.maps.LatLng(lat, lng);
		//마커를 생성합니다
		var marker = new kakao.maps.Marker({
			position: markerPosition
		});
		//마커가 지도 위에 표시되도록 설정합니다
		marker.setMap(map2);
		map2.setCenter(markerPosition);
		map2markers[0] = marker;
	})
	
		
</script>

<div class="dim-layer">
	<div class="dimBg"></div>
	<div id="layer20" class="pop-layer">
        	<div class="pop-container">
            		<div class="pop-conts">
		                <!--content //-->
				<div class="new_proejct">
					<h2>미팅장소 추가하기 </h2>
    					<div id="map" style="width:100%;height:500px;"></div>
			    		<div id="menu_wrap" class="bg_white">
			        		<div class="option">
			            			<div>
						                <form onsubmit="searchPlaces(); return false;">
						                    키워드 : <input type="text"  value="재단법인 대덕인재개발원" id="keyword" size="15"> <button type="submit">검색하기</button> 
						                </form>
			            			</div>
			        		</div>
			        
					        <ul id="placesList"></ul>
					        <div id="pagination"></div>
			    		</div>
				</div>

			        <div style="height: 100%; width:50%; float:left;">
					<form id="meetingFrm" onsubmit="return false;">
						<label>미팅 설명</label><input type="text" name="mt_exp"/><br>
						<label>미팅 일</label><input class="flatpickr flatpickr-input" type="text" placeholder="Select Date.." data-id="rangeDisable" id="wps_start_date" name="date" readonly="readonly"><br>
						<label>장소명</label><input type="text" id="mt_lc" name="mt_lc"/><br>
						<input type="hidden" id="mt_lat" name="mt_lat"/><br>
						<input type="hidden" id="mt_lng" name="mt_lng"/><br>
					</form>
				</div>
				
		                <div class="btn-r">
		                    <a href="#" class="btn-layerClose">Close</a>
		                    <button type="button" id="insertMeeting" class="btn btn-success" data-dismiss="modal">일정 추가</button>
		                </div>
                	<!--// content-->
            		</div>
        	</div>
	</div>
</div>

<script src="/js/kakaomap.js"></script>
<script>

	//일시 선택
	$(".flatpickr").flatpickr({
	    	minDate: "today",
	    	enableTime: true
	});
	
	function insertMeeting(){
		var datas = $("#meetingFrm").serialize();
		$.ajax({
			url: '/meeting/insert',
			type: 'get',
			data: datas,
			success: function(data){
				meetingList();
				$('.dim-layer').fadeOut();
	            		return false;
			}
		})
	}
	$("#insertMeeting").attr("onclick", "insertMeeting()");
	
	$('.btn-example').click(function(){
		var $href = $(this).attr('href');
	        layer_popup($href);
	});
	
	function layer_popup(el){
	
	    var $el = $(el);							//레이어의 id를 $el 변수에 저장
	    var isDim = $el.prev().hasClass('dimBg');	//dimmed 레이어를 감지하기 위한 boolean 변수
	
	    isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();
	
	    var $elWidth = ~~($el.outerWidth()),
	        $elHeight = ~~($el.outerHeight()),
	        docWidth = $(document).width(),
	        docHeight = $(document).height();
	
	    // 화면의 중앙에 레이어를 띄운다.
	    if ($elHeight < docHeight || $elWidth < docWidth) {
	        $el.css({
	            marginTop: -$elHeight /2,
	            marginLeft: -$elWidth/2
	        })
	    } else {
	        $el.css({top: 0, left: 0});
	    }
	
	    $el.find('a.btn-layerClose').click(function(){
	        isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
	        return false;
	    });
	
	    $('.layer .dimBg').click(function(){
	        $('.dim-layer').fadeOut();
	        return false;
	    });
	
	}
</script>
