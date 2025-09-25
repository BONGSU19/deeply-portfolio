/*======================================================================
 * customjs.js는 기본적으로 jquery 라이브러리를 요구함
 * =====================================================================*/
/*======================================================================
 * 숫자 세자리 쉼표 처리하기
 * 
 * 숫자를 입력하면 동적으로 세자리 쉼표 처리하고 숫자 데이터를 저장하는 input 태그를
 * 생성하도록 처리했음. 
 * 등록폼에서는 keyup 처리만 하면 되지만 수정폼에서는 숫자 데이터 수정 없이 submit할 수 있으므로 
 * submit 이벤트 뱔생시 아래 함수를 호출해야 함
 * 
 * 첫번째 파라미터는 입력되는 숫자, 두번째 파라미터는 0 허용 여부(true는 0 허용, false는 0 불허) 
 * =====================================================================*/
function customNumberLocale(target,zero){
	//전송되는 숫자 데이터가 저장될 태그의 id
	const param = target.attr('id')+'_num';
	if($(param).length==0){
		if($('#'+param).length>0) $('#'+param).remove();
		//전송되는 숫자 데이터가 저장될 태그를 생성
		target.after('<input type="hidden" name="'
		                     +target.attr("id")
		                     +'" id="'+param
		                     +'" value="">');
		//UI에 표시된 태그의 데이터가 전송되지 못 하도록 name 속성 제거
		target.removeAttr('name');
	} 
	
	//UI에 표시된 태그의 값이 비어있으면 데이터를 전송해 줄 태그의 값은 0 처리
	if(target.val()==''){
		$('#'+param).val(0);
		return;
	}
	
	if(!zero && target.val()==0){
		$('#'+param).val(0);
		//0을 허용하지 않을 때 0 값을 입력하면 0을 비어있게 처리
		target.val('');
		return;
	}
	
	if(isNaN(Number(target.val().replace(/,/g,'')))){
		alert('숫자만 입력 가능');
		$('#'+param).val(0);
		target.val(target.attr('value'));//태그에 최초 셋팅 데이터를 다시 읽어 옴
		return;
	}
	
	//전송할 값이 입력되는 태그에서는 ,를 제거한 숫자만 처리
	$('#'+param).val(Number(target.val().replace(/,/g,'')));	
	//화면에 표시되는 태그의 값은 세자리 쉼표 표시
	target.val(Number(target.val().replace(/,/g,'')).toLocaleString());
}
/*===================================================================
 * 업로드 이미지 미리보기
 * 파일 업로드 태그의 선택자,업로드되는 이미지가 보여지는 태그 선택자,업로드 허용 확장자,업로드 허용 파일 사이즈
 * =================================================================*/
function customViewImage(fileInput,imgSrc,fileTypes,fileSize){
	$(fileInput).attr('data-path',$(imgSrc).attr('src'));//처음 화면에 보여지는 이미지 읽기
	$(fileInput).change(function(){
		let my_photo = this.files[0];
		if(!my_photo){
			customCancelImage(this,imgSrc);
			return;
		}
		
		if(fileTypes.indexOf(my_photo.name.substring(my_photo.name.lastIndexOf(".")+1, my_photo.name.length).toLowerCase()) < 0){
			alert('파일의 확장자는 '+fileTypes+"만 허용합니다.");
			customCancelImage(this,imgSrc);
		    return;
		}
		
		if(my_photo.size > fileSize){
			alert(my_photo.size + 'bytes('+fileSize+'bytes까지만 업로드 가능)');
			customCancelImage(this,imgSrc);
			return;
		}
		
		const reader = new FileReader();
		reader.readAsDataURL(my_photo);
		
		reader.onload=function(){
			$(imgSrc).attr('src',reader.result);
		};
	});//end of change
}
//이미보기 취소시 사용
function customCancelImage(fileInput,imgSrc){
	$(imgSrc).attr('src',$(fileInput).attr('data-path'));
	$(fileInput).val('');
}
//파일 업로드 후 정보 초기화(파일을 ajax로 업로드할 때 화면 갱신을 위해 사용)
function customInitImage(fileInput,imgSrc){
	$(fileInput).attr('data-path',$(imgSrc).attr('src'));
	$(fileInput).val('');
}
