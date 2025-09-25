$(function(){
	/*---------------------------------
	 *            회원로그인
	 *---------------------------------*/

	$('#member_login').submit(function(){
		$('#error_id, #error_passwd, .error-invalid').text('');
		if($('#id').val().trim() == '' && $('#passwd_hash').val().trim() == ''){
			$('#error_id').text('아이디를 입력하세요').slideDown(500);
			$('#error_passwd').text('비밀번호를 입력하세요').slideDown(500);
			return false;
		}
		if($('#id').val().trim() == '' && $('#passwd_hash').val().trim() != ''){
			$('#error_id').text('아이디를 입력하세요').slideDown(500);
			return false;
		}
		if($('#id').val().trim() != '' && $('#passwd_hash').val().trim() == ''){
			$('#error_passwd').text('비밀번호를 입력하세요').slideDown(500);
			return false;
		}
	});
	$('#id').keydown(function(){
		$('#error_id, .error-invalid').slideUp(1000);
	});
	$('#passwd_hash').keydown(function(){
		$('#error_passwd, .error-invalid').slideUp(1000);
	});
});