function modiBtn() {
	let idx = document.getElementsByName('idx')[0].value;
	let href = "/noticeWrite?saveMode=UPDATE&idx=" + idx;

	location.href = href;
}

function deleteBtn() {

	if (!confirm("정말 삭제 하시겠습니까?")) {

	} else {

		let idx = document.getElementsByName('idx')[0].value;
		let href = "/notice/deleteNotice/" + idx;

		location.href = href;
	}

}

function commentSubmit() {

	let comment = {"comment": document.getElementById('comment').value};
	let pathURL = window.location.href + "/comment";
	

	if (!confirm("댓글 작성을 하시겠습니까?")) {
		alert("작성이 취소 되었습니다");
		return
	} else {
		setTimeout(()=>
		$.ajax({
			type: "POST",
			url: pathURL,
			data: JSON.stringify(comment),
			contentType: 'application/json; charset=utf-8',
			success: function() {
				alert("댓글 작성이 완료 되었습니다.");
			},
			error: function() {
				alert("댓글작성 실패!!");
			}
		})
		
		, 1500);
		
	}
}