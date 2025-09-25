$(function () {
	/* =========================
	 *  사진 등록시 이미지 바로 띄우기
	 * ========================= */
	let quantity = 1;
	let total_quantity = 0;
	$(".quan_amount").each(function () {
	    total_quantity += parseInt($(this).data("value")); 
	});
	console.log("total_quantity: " + total_quantity);
	
	let totalAmount = $('#totalAmount').data('value');
	$('#cart_val').html(formatNumber(totalAmount) + '원');
	$('#check_total').html(formatNumber(totalAmount) + '원');
	if(totalAmount < 50000){
		$('#delivery_fee').html(formatNumber(3000) + '원')
		$('#cart_total_val').html(formatNumber(totalAmount) + '원');
	}else{
		$('#delivery_fee').html('0원');
		$('#cart_total_val').html(formatNumber(totalAmount) + '원');
	}
	
	$('#upload_btn').click(function(e){
		e.preventDefault();
		$('#upload').click();
	});
	
	$('#upload').change(function(){
		let my_photo = this.files[0];
		if(!my_photo){
		//선택을 취소하면 원래 처음 화면으로 되돌림
		$('.items-img').attr('src',photo_path);
		alert('파일 선택 안됨');
		return;
		}
		//화면에서 이미지 미리보기
		const reader = new FileReader();
		reader.readAsDataURL(my_photo);
									
		reader.onload=function(){
		$('.items-img').attr('src',reader.result);
						
		};		
	});
	

	
	/* ======================
     *  장바구니
     * ====================== */
	$('.cminus_btn').click(function(){
		    let cquantity = $(this).parent().find('.quantity').data('value');
		    if (cquantity <= 1) {
		        cquantity = 1;
		    } else {
		        cquantity--;
				total_quantity--;
				totalAmount -= $(this).parents('.v-center').find('.price_total').data('price');
				$('#cart_val').html(formatNumber(totalAmount) + '원');
				if(totalAmount >= 50000){
					$('#cart_total_val').html(formatNumber(totalAmount) + '원');
					$('#delivery_fee').html('0원');
					$('#check_total').data('price', totalAmount);
					$('#check_total').html(formatNumber(totalAmount) + '원');
				}else{
					$('#cart_total_val').html(formatNumber(totalAmount + 3000) + '원');
					$('#delivery_fee').html(formatNumber(3000) + '원');
					$('#check_total').data('price', totalAmount + 3000);
					$('#check_total').html(formatNumber(totalAmount + 3000) + '원');
				}
				
		    }
		    $(this).parent().find('.quantity').data('value', cquantity);
		    $(this).parent().find('.quantity').html(cquantity);

		    const totalPrice = $(this).parents('.v-center').find('.price_total').data('price') * cquantity;
		    $(this).parents('.v-center').find('.price_total').html(formatNumber(totalPrice) + '원');
			
		});

		$('.cplus_btn').click(function(){
			let cquantity = $(this).parent().find('.quantity').data('value');
		    if (cquantity >= 3) {
		        cquantity = 3;
		    } else {
		        cquantity++;
				total_quantity++;
				totalAmount += $(this).parents('.v-center').find('.price_total').data('price');
				$('#cart_val').html(formatNumber(totalAmount) + '원');
				if(totalAmount >= 50000){
					$('#cart_total_val').html(formatNumber(totalAmount) + '원');
					$('#delivery_fee').html('0원');
					$('#check_total').data('price', totalAmount);
					$('#check_total').html(formatNumber(totalAmount) + '원');
				}else{
					$('#cart_total_val').html(formatNumber(totalAmount + 3000) + '원');
					$('#delivery_fee').html(formatNumber(3000) + '원');
					$('#check_total').data('price', totalAmount + 3000);
					$('#check_total').html(formatNumber(totalAmount + 3000) + '원');
				}
		    }
		    $(this).parent().find('.quantity').data('value', cquantity);
		    $(this).parent().find('.quantity').html(cquantity);
		    const totalPrice = $(this).parents('.v-center').find('.price_total').data('price') * cquantity;
		    $(this).parents('.v-center').find('.price_total').html(formatNumber(totalPrice) + '원');

		});


	/* ======================
	 *  숫자 세자리마다 , 넣어짐
	 * ====================== */
	function formatNumber(num) {
	    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	}
	

	$('#alert_x_btn').click(function(){
		$('#addCart_alert').hide();
	});
	
	/* ======================
	 *  장바구니 수량 변경
	 * ====================== */

	$('.quantity-btn').click(function(){
		let item_num = $('#item_num').data('num');
		let order_quantity = $(this).closest('.q-box').find('.quantity').data('value');
		let cart_num = $(this).closest('.q-box').find('input[id^="this_cart_num_"]').data('value');
		
		let csrfToken = $("meta[name='_csrf']").attr("content");
		let csrfHeader = $("meta[name='_csrf_header']").attr("content");

		$.ajax({
		        url: '/item/modifyStock',
		        type: 'POST', // POST 요청
		        contentType: 'application/json', // JSON 데이터 전달
		        data: JSON.stringify({ item_num: item_num, order_quantity: order_quantity, cart_num: cart_num }),
		        beforeSend: function (xhr) {
		            xhr.setRequestHeader(csrfHeader, csrfToken); // CSRF 토큰 포함
		        },
		        dataType: 'json',
		        success: function (param) {
		            if (param.result == 'logout') {
		                alert('사용자 계정으로 로그인 후 이용해 주세요');
		            } else if (param.result == 'success') {
						alert('성공');
		            }else {
		                alert('장바구니 등록 오류 발생');
		            }
		        },
		        error: function () {
		            alert('네트워크 오류');
		        }
		    });
	});
	
	$('.item-premium-btn').click(function(event){
		event.preventDefault();
		$('.item-premium-btn').css('background', 'none');
		let item_category = $(this).data('num');
		$(this).css({'background': '#0369A1',
		        	 'color': '#FFFFFF' 
		    		});
		$('#category_val').val(item_category);
	});
	
	$('.cart-btn').click(function() {
		// 현재 버튼의 id에서 item_num 추출
	    let item_num = $(this).data('value');
	       
	    // 해당 수량 데이터 가져오기
	    let order_quantity = $(this).closest('.vertical-center').find('.quantity').data('value');
		   
		console.log(item_num);	
		console.log(order_quantity);   
	    // 페이지 이동
	    location.href = '../item/order?item_num='+item_num+'&quantity='+order_quantity;
	});
	   
	
	$('#cart_submit_btn').click(function(){
		const target = $('.hidden-check-box');
		target.show();
		$('.hidden-check-box')[0].scrollIntoView({ behavior: 'smooth' });
		
	});
	
	$('.hidden-close-btn').click(function(){
		$('.hidden-check-box').hide();
	});
	
	$('.hidden-submit-btn').click(function(){
		let total_price= $('#check_total').data('price');

		location.href='../charge/payment?pay_price='+total_price+'&item_quantity='+total_quantity;
	});
	
	
});