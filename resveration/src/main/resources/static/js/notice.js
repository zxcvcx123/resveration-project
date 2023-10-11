function searchBtn(){
	
	let field = document.getElementById('searchField').value;
	
	let keyword = document.getElementById('searchKeyword').value;
	
	if(keyword != '') {
		url = "/notice?field=" + field + "&keyword=" + keyword;
	} else if(keyword == '') {
		url = "/notice?field=" + field
	}
	

	
	location.href = url;
	
}