/* 이벤트 리스너 사용할 때 DOM이 모두 load된 후 실행되게 해야 한다 */
document.addEventListener('DOMContentLoaded', function() {
	
	/* 예약 시간, 촬영 종류 선택을 위한 변수 선언 */
    let timeBtns = document.querySelectorAll('.timeBtn');
    let photoTypes = document.querySelectorAll('.typeBtn');
    
    /* 내 예약에서 수정 하는 경우 예약시간 값이 이미 있기에 그걸 감지해서 selectTime()함수 실행 */
	let reservationDay = document.getElementsByName('day')[0].value;
	if(reservationDay.length > 0){
		seletTime();
	}

    /* 시간 선택 / forEach 각 객체를 돈다 */
    timeBtns.forEach(timeBtn => {
        timeBtn.addEventListener('click', function() {

            let value = timeBtn.textContent;
            let type = document.getElementById('time');

            /* setAttribute로 input 속성을 변경 */
            type.setAttribute('type', 'text');
            type.setAttribute('value', value);
            type.setAttribute('readonly', true);
            
            document.getElementById('timeSelect').style.display = "none";

        });
    });

    /* 촬영종류 선택 / forEach 각 객체를 돈다 */
    photoTypes.forEach(photoType => {
        photoType.addEventListener('click', function() {

            let value = photoType.value;
            let type = document.getElementById('type');

            /* setAttribute로 input 속성을 변경 */
            type.setAttribute('type', 'text');
            type.setAttribute('value', value);
            type.setAttribute('readonly', true);
            
            document.getElementById('typeSelect').style.display = "none";

        });
    });
    
    

});

/* ===== 예약시간 선택 ===== */
function timeSelected() {
	
	let day = document.getElementsByName('day')[0].value;
	
	if(day != ""){
    	document.getElementById('timeSelect').style.display = "block";
	} else {
		alert("예약일자 먼저 선택해주세요.");
		document.getElementsByName('day')[0].focus();
	}
}

/* ===== 촬영종류 선택 ===== */
function typeSelected() {
	let day = document.getElementsByName('day')[0].value;
	
	if(day != ""){
    	 document.getElementById('typeSelect').style.display = "block";
	} else {
		alert("예약일자 먼저 선택해주세요.");
		document.getElementsByName('day')[0].focus();
	}
}

/* ===== 예약일자 선택시 그 일자에 따른 예약시간이 어떤 시간대 남아 있는지 ===== */
function seletTime(){
	
	//예약일자 시간 값
	let reservationDay = document.getElementsByName('day')[0].value;
	let timeBtn = document.getElementsByClassName('timeBtn');
	
	/* for문으로 돌려서 값을 담을 변수 */
	let time = new Array();
	let Btn = new Array();
	
	/* onchage 할때 마다 초기화 / 시간선택 버튼의 색상 및 disabled 초기화 */
	for (let i = 0; i < timeBtn.length; i++) {
        timeBtn[i].removeAttribute("disabled");
        timeBtn[i].style.background = "white"; // 또는 초기 색상으로 변경
    }
    
	
	/* DB에서 가져와 선택된 예약일자랑 맞는 시간을 time에 넣기 */
	for(let i = 0; i < getData.length; i++){
		if(getData[i].day === reservationDay){
			time.push(getData[i].time);
		}
	} 
	/* 시간 선택 버튼의 값들을 넣어주기 */
	for(let i = 0; i < timeBtn.length; i++){
		Btn.push(timeBtn[i].textContent);
	}
	/* 위 두개 배열의 공통된 값을 추출 filter 사용 */
	let commonDay = time.filter(it => Btn.includes(it));
	
	/* 시간 선택 버튼의 인덱스 위치 찾기 */
	for(let i=0; i < commonDay.length; i++){
		let index = Btn.indexOf(commonDay[i]);
		timeBtn[index].setAttribute("disabled", "true");
		timeBtn[index].style.background="gray";
	}
	
}

$(function cal() {
       //input을 datepicker로 선언
       $(".datepicker").datepicker({
           dateFormat: 'yy-mm-dd' //달력 날짜 형태nfonts_2304-2@1.0/TTWanjudaedunsanch
           ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
           ,showMonthAfterYear:true // 월- 년 순서가아닌 년도 - 월 순서
           ,changeYear: true //option값 년 선택 가능
           ,changeMonth: true //option값  월 선택 가능                     
           ,yearSuffix: "년" //달력의 년도 부분 뒤 텍스트
           ,monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 텍스트
           ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip
           ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 텍스트
           ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 Tooltip
           ,minDate: "-5Y" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
           ,maxDate: "+5y" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)  
       });                    
       
       //초기값을 오늘 날짜로 설정해줘야 합니다.
       //$('.datepicker').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)            
   });
 