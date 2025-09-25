$(function(){
	$('.output_follow').on('click', function(){
	    const followNum = $(this).attr('data-num');
	    const followerNum = $(this).attr('data-rnum');

	    $.ajax({
	        url: '/follow/unfollow',
	        type: 'post',
	        data: { follow_num: followNum, follower_num:followerNum },
	        dataType: 'json',
	        beforeSend: function(xhr) {
	            xhr.setRequestHeader($('table').attr('data-header'), $('table').attr('data-token'));
	        },
	        success: function(param) {
	            if(param.result === 'logout') {
	                alert('로그인 후 언팔로우가 가능합니다');
	            } else if(param.result === 'success') {
	                alert('언팔로우 완료');
				    location.href='../follow/followList';
	            } else {
	                alert('언팔로우 오류 발생');
	            }
	        },
	        error: function() {
	            alert('네트워크 오류 발생');
	        }
	    });
	});
});