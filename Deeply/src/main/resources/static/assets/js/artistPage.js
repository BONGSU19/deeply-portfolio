$(function () {
	
	let photo_path = $('#previewImage').attr('src');	
	
	$('.member-selection-close').click(function(){
		$('.member-selection-container').hide();
		$('.member-selection-container2').hide();
	});
	
	$('.ad-letter').click(function(){
		$('.member-selection-container').show();
		if ($('.member-selection-container').length > 0) {
			$('.member-selection-container')[0].scrollIntoView({ behavior: 'smooth', block: "center"});
		}
	});
	
	$('.ad-chat').click(function(){
			$('.member-selection-container2').show();
			if ($('.member-selection-container2').length > 0) {
				$('.member-selection-container2')[0].scrollIntoView({ behavior: 'smooth', block: "center"});
			}
	});
	
	$('.amainpic-select').click(function(e){
		e.preventDefault();
		$('#group_photo').click();
	});
	
	
	$('#group_photo').change(function(){
				
		let my_photo = this.files[0];
		if(!my_photo){
			//선택을 취소하면 원래 처음 화면으로 되돌림
			$('#previewImage').attr('src',photo_path);
			alert('파일 선택 안됨');
			return;
		}
		//화면에서 이미지 미리보기
		const reader = new FileReader();
		reader.readAsDataURL(my_photo);
							
		reader.onload=function(){
			$('#previewImage').attr('src',reader.result);
				
		};		
	});
	
	$('.member-item').click(function(){
		const member_val = $(this).data('value');
		
		$('.member-info').hide();
		
		$('.member-info').each(function () {
			const member_data = $(this).data('member'); // 각 member_info의 data-member 값 가져오기
		    if (member_data === member_val) {
		       $(this).show(); // 값이 일치하면 해당 요소를 표시
		    }
		});
	});
});