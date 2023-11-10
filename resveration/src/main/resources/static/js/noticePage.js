function modiBtn() {
	let idx = document.getElementsByName('idx')[0].value;
	let href = "/noticeWrite?saveMode=UPDATE&idx=" + idx;

	location.href = href;
}

/* 게시물 삭제 */
function deleteBtn() {

	if (!confirm("정말 삭제 하시겠습니까?")) {

	} else {

		let idx = document.getElementsByName('idx')[0].value;
		let href = "/notice/deleteNotice/" + idx;

		location.href = href;
	}

}

/* 댓글 작성하기 버튼 */
function commentSubmit() {
	let idx = document.getElementsByName('idx')[0].value;
	let comment = {"comment": document.getElementById('comment').value};
	let pathURL = "/notice/"+ idx + "/comment";
	let cv = document.getElementsByClassName('comment_View');
	
	
	// cv[0].replaceChildren(); // 모든 자식 지우기


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
			success: function(result) {
				alert("댓글 작성이 완료 되었습니다.");

				userName = result[0].cwriter;
				content = result[0].comment;
				let text = "<div class='comment_outLine'><div class='comment_id'><span>" + userName + 
					"</span></div> <div class='comment_content'><span>"+ content + 
					"</span></div></div>";
				cv[0].insertAdjacentHTML("afterbegin", text);
				document.getElementById('comment').value = "";

				
			},
			error: function() {
				alert("댓글작성 실패!!");
			}
		})
		
		, 1000);
		
	}
}

/* 댓글 페이징 ajax */
/*function commentPagingView(num){
	
	let commentPagingNum = {cnum: $(num).text()};
	let url = window.location.href;
	
	$.ajax({
			type: "POST",
			url: url,
			data: JSON.stringify(commentPagingNum),
			contentType: 'application/json; charset=utf-8',
			success: function(result) {	
				console.log(result);
				
			},
			error: function(e) {
				console.log(e)
			}
		})
}*/

/* 좋아용~ */
function checkLike(cidx){
	console.log(cidx);
	

	
}

function toLogin(){
	window.location.href ="/login"
}