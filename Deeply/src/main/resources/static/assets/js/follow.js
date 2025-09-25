$(function(){
	
	$('.output_follow').on('click', function(){
		    let output_follow = $(this);
		    const followNum = output_follow.attr('data-num');
			
		    $.ajax({
		        url: '/follow/follow',
		        type: 'post',
		        data: { follow_num: followNum},
		        dataType: 'json',
		        beforeSend: function(xhr) {
					xhr.setRequestHeader($('.output_follow').attr('data-header'),
								         $('.output_follow').attr('data-token'));
		        },
		        success: function(param) {
		            if(param.result === 'logout') {
		                alert('로그인 후 팔로우가 가능합니다');
		            } else if(param.result === 'success') {
						displayFollow(param,output_follow);
		            } else {
		                alert('팔로우 오류 발생');
		            }
		        },
		        error: function() {
		            alert('네트워크 오류 발생');
		        }
		    });
		});
	
	//팔로우 표시 공통 함수
	function displayFollow(param,ui){
		let output;
		if(param.status == 'following'){
			output = '../assets/images/hr2/follow.svg';
		}else if(param.status == 'unfollow'){
			output = '../assets/images/hr2/unfollow.svg';			
		}else{
			alert('팔로우 표시 오류 발생');
		}
		//문서 객체에 추가
		ui.attr('src',output);
		ui.parent().find('.follow-cnt').text(param.follow_cnt);
	}
});