// DOM이 로드되었을 때 이벤트 핸들러를 등록합니다.
document.addEventListener("DOMContentLoaded", function() {
	/* ===== 이메일 선택화면 변경 ===== */
	document.getElementById('emailSelect').addEventListener("change", emailSelect);
});



/* ===== 이메일 선택화면 변경 ===== */
function emailSelect() {

	let val = document.getElementById('emailSelect').value;

	if (val === "직접입력") {
		document.getElementsByName('email2')[0].style.display = "inline-block";
		document.getElementsByName('email2')[0].value = "";
	} else if (val === "naver.com") {
		document.getElementsByName('email2')[0].value = val;
	} else if (val === "daum.net") {
		document.getElementsByName('email2')[0].value = val;
	} else if (val === "gmail.com") {
		document.getElementsByName('email2')[0].value = val;
	}
}


/* ===== 유효성 검사 ===== */
function checkForm() {

	/* 각 input의 값들 */
	let id = document.getElementsByName('id')[0].value;
	let pw = document.getElementsByName('pw')[0].value;
	let pwCheck = document.getElementsByName('pwCheck')[0].value;
	let name = document.getElementsByName('name')[0].value;
	let phone = document.getElementsByName('phone')[0].value;
	let email1 = document.getElementsByName('email1')[0].value;


	/* 각 input에 대한 정규식 패턴 */
	let idPattern = /^[a-zA-Z0-9]*$/;
	let pwPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
	let namePattern = /^[가-힣,a-z,A-Z]+$/;
	let phonePattern = /^(0[0-9]{2})-([0-9]{4})-([0-9]{4})$/;
	let emailPattern = /^[a-zA-Z0-9]*$/;

	/* 검사 실행 */
	if (id != "") {
		if (idPattern.test(id)) {
			document.getElementsByClassName('checkFont')[0].style.display = 'none';
		} else {
			document.getElementsByClassName('checkFont')[0].style.display = 'block';
		}
	}

	if (pw != "") {
		if (pwPattern.test(pw)) {
			document.getElementsByClassName('checkFont')[1].style.display = 'none';
		} else {
			document.getElementsByClassName('checkFont')[1].style.display = 'block';
		}
	}

	if (pwCheck != "") {
		if (pw == pwCheck) {
			document.getElementsByClassName('checkFont')[2].style.display = 'none';
		} else {
			document.getElementsByClassName('checkFont')[2].style.display = 'block';
		}
	}

	if (name != "") {
		if (namePattern.test(name)) {
			document.getElementsByClassName('checkFont')[3].style.display = 'none';
		} else {
			document.getElementsByClassName('checkFont')[3].style.display = 'block';
		}
	}

	if (phone != "") {
		if (phonePattern.test(phone)) {
			document.getElementsByClassName('checkFont')[4].style.display = 'none';
		} else {
			document.getElementsByClassName('checkFont')[4].style.display = 'block';
		}
	}

	if (email1 != "") {
		if (emailPattern.test(email1)) {
			document.getElementsByClassName('checkFont')[5].style.display = 'none';
		} else {
			document.getElementsByClassName('checkFont')[5].style.display = 'block';
		}
	}


}

let sig = 0;

/* ===== 가입하기 ===== */
function memberJoin() {

	/* 각 input의 값들 */
	let id = document.getElementsByName('id')[0].value;
	let pw = document.getElementsByName('pw')[0].value;
	let pwCheck = document.getElementsByName('pwCheck')[0].value;
	let name = document.getElementsByName('name')[0].value;
	let phone = document.getElementsByName('phone')[0].value;

	/* 각 input에 대한 정규식 패턴 */
	let idPattern = /^[a-zA-Z0-9]*$/;
	let pwPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
	let namePattern = /^[가-힣,a-z,A-Z]+$/;
	let phonePattern = /^(0[0-9]{2})-([0-9]{4})-([0-9]{4})$/;
	let emailPattern = /^[a-zA-Z0-9]*$/;

	/* 메일 통합 */
	let email1 = document.getElementsByName('email1')[0].value;
	let email2 = document.getElementsByName('email2')[0].value;
	let email = email1 + "@" + email2;


	let member = {
		"id": document.getElementsByName('id')[0].value,
		"pw": document.getElementsByName('pw')[0].value,
		"name": document.getElementsByName('name')[0].value,
		"phone": document.getElementsByName('phone')[0].value,
		"email": email
	};

	if (idPattern.test(id) && 
		pwPattern.test(pw) && 
		namePattern.test(name) && 
		phonePattern.test(phone) && 
		emailPattern.test(email1) && 
		sig == 1 && 
		email2 != "") {
		$.ajax({
			type: "POST",
			url: "/memberDo",
			data: member,
			dataType: 'json',
			success: function(result) {
				if (result) {
					alert("가입이 완료 되었습니다");
					window.location.href = '/login';
				}
			},
			error: function(xhr, status, error) {
				alert("회원 가입 실패!! \n\n" + xhr.responseText);
			}
		})
	} else if (sig == 0) {
		alert("ID가 중복 되었는지 확인해주세요.");
	} else if (email2 == "") {
		alert("이메일 주소를 선택해주세요.");
	} else {
		alert("모든 항목을 올바르게 입력했는지 확인해주세요.")
	}

}

/* ===== ID 중복확인 ===== */
function checkID() {
	let userid = document.getElementsByName('id')[0].value;

	userid = userid.trim();
	if (userid != "") {
		$.ajax({
			type: "POST",
			url: "/checkID",
			data: JSON.stringify({ userid: userid }),
			contentType: "application/json",
			success: function(response) {
				if (response.result === "success") {
					alert("사용 가능한 아이디 입니다.");
					sig = 1;
				} else if (response.result === "error") {
					alert("중복된 아이디 입니다.");
					sig = 0;
				}
			},
			error: function() {
				alert("서버 요청 실패");
			}
		})
	} else {
		alert("아이디를 입력해주세요.")
		document.getElementsByName('id')[0].focus();
	}


}

