
/*기본적인 지도 생성 */
var map = new naver.maps.Map('map', {
    center: new naver.maps.LatLng(36.29760008226935, 127.33674235121521), // 위도 경도 넣기 / 대전 서구 관저중로64번길 34 301
    zoom: 17,			 //확대 얼마나 할건지 기본은 15
    mapTypeControl: true //일반, 위성화면 볼건지
});

//지도위 안내 문구 나타내기
var infoWindow = new naver.maps.InfoWindow({
    anchorSkew: true
});


/* 마커 설정하기 */
var marker = new naver.maps.Marker({
    position: new naver.maps.LatLng(36.29760008226935, 127.33674235121521), //마커 표시할 위도, 경도 / 대전 서구 관저중로64번길 34 301
    map: map
});

/* 안내 문구 제작 */
var contentString = [
        '<div class="iw_inner">',
        '   <h3>스폿라이트 사진관 관저점</h3>',
        '   <p>대전 서구 관저중로64번길 34 301호</p>',
        '</div>'
    ].join('');

    var infowindow = new naver.maps.InfoWindow({
    content: contentString
});

/* 마커 누르면 안내문구 사라졌다 생겼다 */
naver.maps.Event.addListener(marker, "click", function(e) {
    if (infowindow.getMap()) {
        infowindow.close();
    } else {
        infowindow.open(map, marker);
    }
});

/* 처음부터 안내문구 보이기 */
infowindow.open(map, marker);