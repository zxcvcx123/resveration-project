window.onload = function () {

    load_Candle(Year, Month);

}

/* 달력에 필요한 객체 선언 및 변수 만들기 */

let date = new Date(); // Date 객체 선언

let Year = date.getFullYear(); //달력 년도

let Month = date.getMonth() + 1; //달력 월

let Day = date.getDate(); //일

/* 달력출력하기 */
function load_Candle(Year, Month) {
	
    //년도
    let year = document.getElementById('load_year');

    //월
    let month = document.getElementById('load_month');

    //마지막 일 구하기
    let dayDate = new Date(Year, Month, 0);

    //이전 달 마지막 일 구하기
    let lastMonthDayDate = dayDate.getDate();

    //달력 월의 시작 요일 구하기
    let monthStartDay = new Date(Year, Month - 1, 1);
    let StartDay = monthStartDay.getDay();
	
    //몇 주 인지 계산
    let weekCount = Math.ceil((StartDay + lastMonthDayDate) / 7);

    //년도, 월, 일 뿌려주기
    year.innerHTML = Year + ".";

    if ((Month / 10) % 10 < 1) {
		
        month.innerHTML = "0" + Month;
    } else {
        month.innerHTML = Month;
    }

    //날짜 테이블 생성할 ID값 
    let candle_Table_Start_Day = document.getElementById('candle_Table_Start_Day');

    candle_Table_Start_Day.innerHTML = ""; // 기존 테이블 초기화

    let html = "";

    //날짜 생성될 위치
    let candleXYZ = 0;
	
    //날짜
    let candleDay = 0;
    
    
    //월 , 날짜가 두자리가 아닌경우 0을 추가해주기
    let Cmonth = "";
    
    if ((Month / 10) % 10 < 1) {
        Cmonth = "0" + Month;
    } else {
        Cmonth = Month;
    }
	
    for (let i = 0; i < weekCount; i++) {
        html += "<tr>";

        for (let y = 0; y < 7; y++) {
            html += "<td>";
            if (StartDay <= candleXYZ && candleDay < lastMonthDayDate) {
                candleDay++;
                if((candleDay / 10) % 10 < 1){
                	html += "<span style='display: block; width: 100%; height: 100%' id="+Year+"-"+Cmonth+"-"+"0"+candleDay+"><a style='font-weight:600;'" + Year +"-"+ Cmonth +"-"+ "0" + candleDay + ">" + candleDay + "</a></span>";
            	} else {
					html += "<span style='display: block; width: 100%; height: 100%'id="+Year+"-"+Cmonth+"-"+candleDay+"><a style='font-weight:600;'" + Year +"-"+Cmonth +"-"+ candleDay + ">" + candleDay + "</a></span>";
				}
            }
            html += "</td>";
            candleXYZ++;
        }

        html += "</tr>";
    }

    candle_Table_Start_Day.innerHTML = html;
	
	/* ===== 예약 건 수 출력하는 부분 시작 ===== */
	let calDay = document.querySelectorAll('span'); //타임리프로 가져온 데이터랑 비교할 날짜의 id값을 가진 요소 선택
	let dateDay = getDay; //타임리프에서 가져온 백엔드 데이터(html 맨 하단에 보면 있음)
	
	/* id 값과 타임리프 가져온 데이터를 확인 하는 작업 */
	for (let i = 0; i < calDay.length; i++) {
	  let count = 0;
	  for (let j = 0; j < getDay.length; j++) {
	    if (calDay[i].id === dateDay[j]) {
	      count++;
	    }
	  }
	  /*조건이 맞으면 이걸 입력해라*/
	  if (count > 0) {
		let newContent = document.createElement('a');
		newContent.classList.add('dd');
	    newContent.textContent = "예약"+count+"건";
	    newContent.href = "/reservation/" + calDay[i].id; // 링크 URL 설정
	    calDay[i].parentElement.appendChild(newContent);
	  }
	}
	/* ===== 끝 ===== */
}

/*버튼 눌렀을때 월 및 년도 바뀌게 하기*/ 
function right_Btn() {

    Month++; // Month 변수를 증가시킵니다.

    // 월이 12월을 넘어가면 (12보다 크면), 월을 1월로 초기화하고 연도도 증가시킵니다.
    if (Month > 12) {
        Month = 1;
        Year++; // 월이 초기화되면 연도도 증가시킵니다.
    }

    // "load_Candle()" 함수를 호출하여 새로운 Month 값으로 달력을 업데이트합니다.
    load_Candle(Year, Month);
 
}

function left_Btn() {

    Month--; // Month 변수를 증가시킵니다.

    // 월이 01월보다 아래로 내려가면 (1보다 작으면), 월을 12월로 초기화하고 연도도 차감시킨다.
    if (Month < 1) {
        Month = 12;
        Year--; // 월이 초기화되면 연도도 증가시킵니다.
    }

    // "load_Candle()" 함수를 호출하여 새로운 Month 값으로 달력을 업데이트합니다.
    load_Candle(Year, Month);
    getReservationCount(); // 예약 인원 업데이트
    
}

function myBtn(){
	url = "/reservation/myreservation";
	location.href = url;
}
