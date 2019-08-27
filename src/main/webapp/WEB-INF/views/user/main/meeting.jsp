<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	.meetingItem { border : 1px solid black;background-color : #e1e1e1; margin : 3px auto;}
	.div span { font-size: x-large; }
	.map_wrap, .map_wrap * {margin:0;padding:0;font-family:'Malgun Gothic',dotum,'돋움',sans-serif;font-size:12px;} .map_wrap a, .map_wrap a:hover, .map_wrap a:active{color:#000;text-decoration: none;} .map_wrap {position:relative;width:100%;height:500px;} #menu_wrap {position:absolute;top:0;left:0;bottom:0;width:250px;margin:10px 0 30px 10px;padding:5px;overflow-y:auto;background:rgba(255, 255, 255, 0.7);z-index: 1;font-size:12px;border-radius: 10px;} .bg_white {background:#fff;} #menu_wrap hr {display: block; height: 1px;border: 0; border-top: 2px solid #5F5F5F;margin:3px 0;} #menu_wrap .option{text-align: center;} #menu_wrap .option p {margin:10px 0;}   #menu_wrap .option button {margin-left:5px;} #placesList li {list-style: none;} #placesList .item {position:relative;border-bottom:1px solid #888;overflow: hidden;cursor: pointer;min-height: 65px;} #placesList .item span {display: block;margin-top:4px;} #placesList .item h5, #placesList .item .info {text-overflow: ellipsis;overflow: hidden;white-space: nowrap;} #placesList .item .info{padding:10px 0 10px 55px;} #placesList .info .gray {color:#8a8a8a;} #placesList .info .jibun {padding-left:26px;background:url(http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png) no-repeat;} #placesList .info .tel {color:#009900;} #placesList .item .markerbg {float:left;position:absolute;width:36px; height:37px;margin:10px 0 0 10px;background:url(http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png) no-repeat;} #placesList .item .marker_1 {background-position: 0 -10px;} #placesList .item .marker_2 {background-position: 0 -56px;} #placesList .item .marker_3 {background-position: 0 -102px} #placesList .item .marker_4 {background-position: 0 -148px;} #placesList .item .marker_5 {background-position: 0 -194px;} #placesList .item .marker_6 {background-position: 0 -240px;} #placesList .item .marker_7 {background-position: 0 -286px;} #placesList .item .marker_8 {background-position: 0 -332px;} #placesList .item .marker_9 {background-position: 0 -378px;} #placesList .item .marker_10 {background-position: 0 -423px;} #placesList .item .marker_11 {background-position: 0 -470px;} #placesList .item .marker_12 {background-position: 0 -516px;} #placesList .item .marker_13 {background-position: 0 -562px;} #placesList .item .marker_14 {background-position: 0 -608px;} #placesList .item .marker_15 {background-position: 0 -654px;} #pagination {margin:10px auto;text-align: center;} #pagination a {display:inline-block;margin-right:10px;} #pagination .on {font-weight: bold; cursor: default;color:#777;}
</style>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=253080e34ec6fc99f7cfccec73533ca8&libraries=services"></script>
<div style="width:100%; height:50px;" class="div"><span>일정 목록</span>
	<button class="btn btn-default pull-right" data-toggle="modal" data-target="#meetingmodal">일정 추가</button>
</div>
<div id="map2" style="width:49%;height:500px;position:relative;overflow:hidden;float:left; margin-right:8px;"></div>
<div id="meetings" style="width:49%; height:500px; float:left;"></div>
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
</script>
<script>
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

<div id="meetingmodal" class="modal fade" role="dialog">
	<div class="modal-dialog modal-lg">
		
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">장소일정 추가</h4>
			</div>
			<div class="modal-body">
				<div class="map_wrap">
    				<div id="map" style="width:434px;height:500px;position:relative;overflow:hidden; float:left ;margin-right:5px;">
    				</div>
			    <div id="menu_wrap" class="bg_white">
			        <div class="option">
			            <div>
			                <form onsubmit="searchPlaces(); return false;">
			                    키워드 : <input type="text" value="재단법인 대덕인재개발원" id="keyword" size="15"> 
			                    <button type="submit">검색하기</button> 
			                </form>
			            </div>
			        </div>
			        <hr>
			        <ul id="placesList"></ul>
			        <div id="pagination"></div>
			    </div>
				</div>
    				<div style="height: 100%; width:50%; float:left;">
						<form id="meetingFrm" onsubmit="return false;">
							<label>미팅 설명</label><input type="text" name="mt_exp"/><br>
							<label>미팅 일자</label><input type="date" name="date"/><br>
							<label>미팅 시간</label><input type="time" name="time"/><br>
							<label>장소명</label><input type="text" id="mt_lc" name="mt_lc"/><br>
							<p>실제로는 DatePicker 이용해서 일자 + 시간 형태로 받음</p><br>
							<input type="hidden" id="mt_lat" name="mt_lat"/><br>
							<input type="hidden" id="mt_lng" name="mt_lng"/><br>
							<input type="hidden" name="user_email" value="deviyu091@gmail.com"/><br>
							<input type="hidden" name="prj_id" value="2"/><br>
						</form>
					</div>
			</div>
			<div class="modal-footer">
				<button type="button" id="insertMeeting" class="btn btn-success" data-dismiss="modal">
				일정 추가
				</button>
				<button type="button" class="btn btn-default" 
				data-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>
<script>
	function insertMeeting(){
		var datas = $("#meetingFrm").serialize();
		$.ajax({
			url: '/meeting/insert',
			type: 'get',
			data: datas,
			success: function(data){
				console.log("ok");
				console.log(data);
				meetingList();
			}
		})
	}
	$("#insertMeeting").attr("onclick", "insertMeeting()");
</script>
<script src="/js/kakaomap.js"></script>