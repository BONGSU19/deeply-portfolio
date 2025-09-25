$(function() {

    // 이벤트 핸들러 등록
    $('.blue-Fan-btn').on('click', function() {
        showModal('joinFan');
    });

    $('.success-join-btn').on('click', function() {
        successJoin();
    });

    $('.success-remove-btn').on('click', function() {
        successRemove();
    });

    $('.close-modal-btn').on('click', function() {
        closeModal($(this).data('modal-id'));
    });
});

// 모달 열기 함수
    function showModal(modalId) {
        $('#' + modalId).css('display', 'block');
    }

    // 모달 닫기 함수
    function closeModal(modalId) {
        $('#' + modalId).css('display', 'none');
    }
	
		// 가입 버튼 클릭 시
	    $("#joinForm").on("click", function() {
			var user_num = $(this).attr('data-num'); // userNum 값 가져오기
	      	var fan_artist = $(this).attr('data-arti'); // JSP에서 변수 가져오기
			var user_bal = $(this).attr('data-bal');

	      // 잔액이 부족한 경우
	      if (user_bal < 6500) {
	        alert('잔액이 부족합니다.');
	        return false;
	      }

	      // Ajax 요청 보내기
	      $.ajax({
	        url: 'joinFan', // 서버의 URL (컨트롤러)
	        type: 'POST',
	        data: {
				user_num: user_num,
	            fan_artist: fan_artist,
	        },
			dataType:'json',
			beforeSend:function(xhr){
				xhr.setRequestHeader($('#token_info').attr('data-header'),
			                         $('#token_info').attr('data-token'));
			},
	        success: function(param) {
	          if (param.result=='success') {
				closeModal('joinForm');
				showModal('successJoin')
	          }else if(param.result=='noMoney'){
	            alert('잔액이 부족하여 충전 페이지로 이동합니다.');
				href = "${pageContext.request.contextPath}/";
	          }else if(param.result=='used'){
				alert('이미 팬 가입된 회원입니다.');
				href = "${pageContext.request.contextPath}/";
			  }else{
				alert('팬 가입에 실패하였습니다.');
			  }
	        },
	        error: function() {
	          alert('서버 오류가 발생했습니다. 다시 시도해주세요.');
	        }
	      });
	    });
	  	
		// 해지 버튼 클릭 시
	    $("#removeForm").on("click", function() {
			var user_num = $(this).attr('data-num'); // userNum 값 가져오기
			var fan_artist = $(this).attr('data-arti'); // JSP에서 변수 가져오기
			var fan_status = $(this).attr('data-status'); // JSP에서 변수 가져오기

	      // 이미 해지한 경우
	      if (fan_status > 1) {
	        alert('이미 해지 신청이 된 상태입니다. 잔여일을 기다려주세요.');
	        return false;
	      }else if(fan_status >2){
			alert('이미 해지한 회원입니다.');
			return false;
		  }

	      // Ajax 요청 보내기
	      $.ajax({
	        url: 'removeFan', // 서버의 URL (컨트롤러)
	        type: 'POST',
	        data: {
				user_num: user_num,
	            fan_artist: fan_artist,
				fan_status: fan_status,
	        },
			dataType:'json',
			beforeSend:function(xhr){
				xhr.setRequestHeader($('#token_info').attr('data-header'),
			                         $('#token_info').attr('data-token'));
			},
	        success: function(param) {
	          if (param.result=='success') {
				closeModal('removeForm');
				showModal('successRemove')
	          } else {
	            alert('해지 실패');
	          }
	        },
			error: function() {
				alert('서버 오류가 발생했습니다. 다시 시도해주세요.');
	        }
	      });
	    });
		
		// 즉시해지 버튼 클릭 시
	    $("#deleteForm").on("click", function() {
			var user_num = $(this).attr('data-num'); // userNum 값 가져오기
			var fan_artist = $(this).attr('data-arti'); // JSP에서 변수 가져오기

	      // Ajax 요청 보내기
	      $.ajax({
	        url: 'deleteNowFan', // 서버의 URL (컨트롤러)
	        type: 'POST',
	        data: {
				user_num: user_num,
	            fan_artist: fan_artist
	        },
			dataType:'json',
			beforeSend:function(xhr){
				xhr.setRequestHeader($('#token_info').attr('data-header'),
			                         $('#token_info').attr('data-token'));
			},
	        success: function(param) {
	          if (param.result=='success') {
				closeModal('deleteForm');
				showModal('successRemove')
	          }else if(param.result=='noFan'){
	            alert('팬이 아닙니다.');
	          }else {
	            alert('즉시해지 실패');
	          }
	        },
			error: function() {
				alert('서버 오류가 발생했습니다. 다시 시도해주세요.');
	        }
	      });
	    });
    

    // 멤버십 해지 성공 처리
    function successRemove() {
        $.ajax({
            type: 'post',
            url: 'removeFan', // 실제 URL로 수정
            data: {}, // 필요에 따라 데이터 추가
            dataType: 'json',
			beforeSend:function(xhr){
				xhr.setRequestHeader($('#token_info').attr('data-header'),
			                         $('#token_info').attr('data-token'));
			},
            success: function(response) {
                if(response.isMember) { // 멤버십 회원 여부 확인
                    closeModal('removeFan');
                    showModal('successFan');
                } else {
                    alert('현재 멤버십 회원이 아니므로 해지할 수 없습니다.');
                }
            },
            error: function() {
                alert('네트워크 오류가 발생했습니다.');
            }
        });
    }