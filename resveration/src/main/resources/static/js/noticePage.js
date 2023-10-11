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