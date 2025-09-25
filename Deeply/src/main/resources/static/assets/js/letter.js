$(function () {
	
	
	const targetElement = $('#letter_form');
	if (targetElement.length) {
	    const elementPosition = targetElement.offset().top;
	    window.scrollTo(0, elementPosition); // 즉시 스크롤 이동
	}
	
	$('#letterFiles_btn').click(function(e){
		e.preventDefault();
		$('#letterFiles_upload').click();
	});
	
	$('#letterPhoto_btn').click(function(e){
		e.preventDefault();
		$('#letterPhoto_upload').click();
	});
		
		
	$('#letterPhoto_upload').change(function(){
					
		let my_photo = this.files[0];
		if(!my_photo){
			//선택을 취소하면 원래 처음 화면으로 되돌림
			$('.preview-img').attr('src',photo_path);
			alert('파일 선택 안됨');
			return;
		}
		//화면에서 이미지 미리보기
		const reader = new FileReader();
		reader.readAsDataURL(my_photo);
								
		reader.onload=function(){
			$('.preview-img').attr('src',reader.result);		
		};		
	});
	
	$('#reject_letter').click(function(e){
		e.preventDefault();
		alert("오늘의 편지 가능 횟수가 모두 소비되었습니다");
	});
});