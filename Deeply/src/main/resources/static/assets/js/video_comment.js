$(function(){
	/*===========================
	* 식별자 정의
	*===========================*/
	let rowCount = 10;
	let currentPage;
	let count;
	
	/*===========================
	* 댓글 목록
	*===========================*/	
	function selectList(pageNum){
	    currentPage = pageNum;
	    $.ajax({
	        url:'listReply',
	        type:'get',
	        data:{
	            videoId: $('#video_id').val(),
	            pageNum: pageNum,
	            rowCount: rowCount
	        },
	        dataType:'json',
	        beforeSend:function(){
	            $('#loading').show();
	        },
	        complete:function(){
	            $('#loading').hide();
	        },
	        success:function(param){
	            count = param.count;
	            if(pageNum == 1){
	                $('#output').empty();
	            }
	            displayReplyCount(param.count);
	            $(param.list).each(function(index,item){
	                if(index>0) $('#output').append('<hr size="1" width="100%">');
	                let output = '<div class="item">';
	                // 댓글 내용 구성
	                output += '</div>';
	                $('#output').append(output);
	            });
	            // 페이징 버튼 처리
	            if(currentPage >= Math.ceil(count / rowCount)){
	                $('.paging-button').hide();
	            } else {
	                $('.paging-button').show();
	            }
	        },
	        error:function(){
	            alert('네트워크 오류 발생');
	        }	  
	    });
	}
});