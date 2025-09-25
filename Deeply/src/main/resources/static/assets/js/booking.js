$(function () {
	let total = 0;
	console.log($('#book_member').length);
	const scrollTargetId = localStorage.getItem('scrollTarget');
	   if (scrollTargetId) {
	       const target = $(`.booking-category`);
	       if (target.length) {
				const elementPosition = target.offset().top;
				window.scrollTo(0, elementPosition);
	       }
	       // 스크롤 후 로컬 스토리지에서 제거
	       localStorage.removeItem('scrollTarget');
	   }
	   
	$('#book_member').on('input change', function () {
	     console.log('입력값 변경:', $(this).val());
		 if($(this).val() == "2"){
			$('#book2').show();
		 }else{
			$('#book2').hide();
		 }
	});
	
	
	$('.perf-img-btn').click(function(event){
		event.preventDefault();
		$('#event_upload').click();
	 });
	
	 $('#event_upload').change(function(){
		let my_photo = this.files[0];
		if(!my_photo){
		//선택을 취소하면 원래 처음 화면으로 되돌림
			$('#preview_perfImg').attr('src',photo_path);
			alert('파일 선택 안됨');
			return;
		}
		//화면에서 이미지 미리보기
		const reader = new FileReader();
		reader.readAsDataURL(my_photo);
										
		reader.onload=function(){
			$('#preview_perfImg').attr('src',reader.result);		
		};	
	 });
	 
	
    $('.booking-item').click(function(){
		$('.event-detail').css('display', 'block');
		$.ajax({
			url:'detail',
			type:'get',
			data:{perf_num:$(this).attr('data-num'),
				  isMembership:$(this).attr('data-val')
			},
			dataType:'json',
			success:function(response){
				if ($('.event-detail').length > 0) {
				    $('.event-detail')[0].scrollIntoView({ behavior: 'smooth' });
				}
				displayEventDetails(response);
				
				$('#booking_submit_btn').off('click').click(function(e){
					
					if(response.isBooked > 0){
						e.preventDefault();
						alert('이미 예약한 공연입니다');
					}
					else{
						if(response.event.perf_status=='over'){
							e.preventDefault();
							alert('예매 기간이 종료되었습니다');
							return;
						}else if(response.event.perf_status=='membership' && response.event.isMembership == 1){
							const url = '/booking/book?perf_num='+response.event.perf_num; 
							window.location.href=url;
						}else if(response.event.perf_status=='membership' && response.event.isMembership != 1){
							e.preventDefault();
							alert('선예매 자격요건에 해당되지 않습니다.\n자격요건: 아티스트 구독 + 20일');
							return;
						}else if(response.event.perf_status=='before'){
							e.preventDefault();
							alert('예매 기간이 아닙니다');
							return;
						}else{
							const url = '/booking/book?perf_num='+response.event.perf_num; 
							window.location.href=url;
						}	
					}
					
					
				});
				
			},
				error:function(){
				alert('네트워크 오류');
			}
		});
					
	});
	
	function displayEventDetails(response){
		const event = response.event;
		$('.e-img').attr('src', '../assets/upload/'+event.perf_photo);
		$('.e-title').text(event.perf_title);
		$('.e-date').text(event.perf_date);
		$('.e-time').text(event.perf_time +" ~ "+event.end_time);
		if(event.end_date == null){
			$('.e-period').text(event.perf_date);
		}else{
			$('.e-period').text(event.perf_date + ' ~ ' + event.end_date);	
		}
		$('.e-membership').text(event.mem_date);
		$('.e-place').text(event.hall_name);
		$('.e-desc').text(event.perf_desc);
		$('.e-address').text(event.location);
		$('.e-price').text(event.ticket_price + '원');
		$('.e-vip-price').text(Math.round(event.ticket_price * 1.4) + '원');
		if(event.perf_status=='over'){
			$('#booking_submit_btn').html("예매기간 종료");
		}else if(event.perf_status=='membership' && response.event.isMembership == 1){
			$('#booking_submit_btn').html("선예매");
		}else if(event.perf_status=='ongoing'){
			$('#booking_submit_btn').html("예매");
		}else{
			$('#booking_submit_btn').html("예매기간 이전");
		}
	}
	
	$('.seat-item').click(function () {
	    const seat_num = $(this).text(); // 클릭된 좌석 번호 가져오기
	    const seat_price = Number($(this).data('price')); // 좌석 금액 가져오기 (숫자 변환)
	    const booked_amount = Number($('#book_member').val()); // 선택한 인원 수

	    // 이미 선택된 좌석인지 확인
	    if ($(this).hasClass('selected')) {
	        // 선택 해제 처리
	        $(this).removeClass('selected').css('background-color', ''); // 좌석 선택 스타일 제거

	        // 테이블에서 해당 좌석의 행 제거
	        $(`.receipt tbody tr[data-seat-num="${seat_num}"]`).remove();

	        // 총 금액 차감
	        total -= seat_price;

	        // 입력 필드에서 제거
	        if ($('#seat_n1').val() === seat_num) {
	            $('#seat_n1').val('');
	        } else if ($('#seat_n2').val() === seat_num) {
	            $('#seat_n2').val('');
	        }

	        // 총 금액 행 업데이트
	        updateTotalRow(total);
	        $('#seat_price').val(total); // 총 금액 숨겨진 필드에 저장
	        return; // 클릭 이벤트 종료
	    }

	    // 현재 선택된 좌석 수 확인
	    const selectedSeats = $('.seat-item.selected').length;

	    if (selectedSeats >= booked_amount) {
	        alert('인원 수에 맞게 좌석을 선택해 주세요');
	        return;
	    }

	    // 좌석 선택 처리
	    if ($('#seat_n1').val() === "") {
	        $('#seat_n1').val(seat_num); // 첫 번째 필드에 좌석 번호 저장
	    } else if ($('#seat_n2').val() === "" && booked_amount > 1) {
	        $('#seat_n2').val(seat_num); // 두 번째 필드에 좌석 번호 저장
	    } else {
	        alert('인원 수에 맞게 좌석을 선택해 주세요');
	        return;
	    }

	    $(this).addClass('selected').css('background-color', '#0EA5E9'); // 좌석 선택 스타일 추가

	    // 총 금액 누적
	    total += seat_price;

	    // 테이블에 선택된 좌석 추가
	    $('.receipt tbody').append(`
	        <tr data-seat-num="${seat_num}">
	            <td>${seat_num}</td>
	            <td>${seat_price}원</td>
	            <td></td>
	        </tr>
	    `);

	    // 총 금액 행 업데이트
	    updateTotalRow(total);

	    $('#seat_price').val(total); // 총 금액 숨겨진 필드에 저장
	});

	// 총 금액 행 업데이트 함수
	function updateTotalRow(total) {
	    // 기존 총 금액 행 제거
	    $('.receipt tbody .total-row').remove();

	    // 새로운 총 금액 행 추가
	    $('.receipt tbody').append(`
	        <tr class="total-row">
	            <td></td>
	            <td></td>
	            <td>${total}원</td>
	        </tr>
	    `);
	}
	
	$('.dropdown-item').click(function (e) {
	    e.preventDefault();
	    
	    $('#hall_num').val($(this).data('value'));
	    
	    $('#drop_title').text($(this).text());
	});
	
	$('#event_dur1').click(function(e){
		e.preventDefault();
		$('.event-end-date').hide();
		$(this).css('background','#0369A1');
		$('#event_dur2').css('background','none');
	});
	$('#event_dur2').click(function(e){
		e.preventDefault();
		$('.event-end-date').show();
		$(this).css('background','#0369A1');
		$('#event_dur1').css('background','none');
	})
	
	
	
	$('#date-range').flatpickr({
	      mode: "range", // 범위 모드 활성화
	      dateFormat: "Y-m-d", // 날짜 형식 설정
	      onChange: function (selectedDates, dateStr, instance) {
			
			if (selectedDates.length === 2) {
				if (selectedDates[0].getTime() === selectedDates[1].getTime()) {
				               alert("서로 다른 날짜를 입력해주세요");
				               instance.clear(); // 선택된 날짜 초기화
				               $('#date-range').val(''); // 입력 필드 초기화
				               return;
				           }
				console.log("선택된 날짜:", dateStr); // 선택된 날짜 출력
				const formattedDate = dateStr.replace(' to ', ' ~ ');
				$('#date-range').val(formattedDate);
				//$('#booking_filter').submit();
				
				// status를 전체 보기로 설정 (all)
				const groupNum = $('#group_num').val(); // 그룹 번호 확인
				const url = `/booking/list?group_num=${groupNum}&dateRange=${formattedDate}`;
				window.location.href = url;
				const targetId = $(`.booking-category`);
				addScrollClass(targetId)
			}
			
			
	      }
	  });
	  
	  $('#calendar_img').click(function () {
	         
	    const today = new Date();
	    const hundredDaysLater = new Date();
	    hundredDaysLater.setDate(today.getDate() + 100);

	         
	    const formatDate = (date) => {
	        const year = date.getFullYear();
	        const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
	        const day = String(date.getDate()).padStart(2, '0');
	        return `${year}-${month}-${day}`;
	    };

	    const startDate = formatDate(today);
	    const endDate = formatDate(hundredDaysLater);

	    $('#date-range').val(`${startDate} ~ ${endDate}`);
		//$('#booking_filter').submit();
		
		// status를 전체 보기로 설정 (all)
		const groupNum = $('#group_num').val(); // 그룹 번호 확인
		const url = `/booking/list?group_num=${groupNum}&dateRange=${startDate} ~ ${endDate}`;
		window.location.href = url;
		const targetId = $(`.booking-category`);
		addScrollClass(targetId);
	 });
	 
	 $('.booking-btn').click(function(){
		$('#event_status').val($(this).data('value'));
		const targetId = $(`.booking-category`);
		addScrollClass(targetId);
	 }); 
	 
	 $('.register-event-btn').click(function(e){
		e.preventDefault();
	 });
	 
	 function addScrollClass(targetId) {
	     // 로컬 스토리지에 스크롤 대상 ID 저장
	     localStorage.setItem('scrollTarget', targetId);
	 }
	 
	
	 
	 
});