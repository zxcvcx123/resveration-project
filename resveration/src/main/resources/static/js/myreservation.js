let btn = document.getElementById('btn');


/* ===== 닫기버튼 ===== */
btn.addEventListener('click', () => {
	let url = "/reservation";
	location.href = url;
})

/* ===== 삭제 기능 ===== */
function delBtn(linkElement) {
	
	const idx = linkElement.getAttribute("data-idx");
	console.log(idx);
	
	if(!confirm("예약 취소를 하시겠습니까?")){
		alert("예약이 유지됐습니다.");
	} else {
		$.ajax({
			url: "/reservation/myreservation/delete",
			type:"POST",
			data: JSON.stringify({ idx: idx }),
			contentType: "application/json",
			success:function(){
				alert("예약이 취소 됐습니다.");
				location.reload();
			},
			error:function(){
				alert("ERROR: 관리자에게 문의해주세요");
			}
		})
	}
}