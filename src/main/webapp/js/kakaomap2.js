/**
 * 미팅 내용 클릭시 좌측 지도에 장소를 표시해주는 js 파일입니다.
 */
/* Map2(오른쪽 맵) 부분 시작*/
var map2markers = [];
var mapContainer2 = document.getElementById('map2'), // 지도를 표시할 div의 id를 넣어주세요. 
mapOption2 = { 
    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
    level: 3 // 지도의 확대 레벨
}
var map2 = new kakao.maps.Map(mapContainer2, mapOption2); // 지도를 생성합니다
/* Map2(오른쪽 맵) 부분 끝*/

