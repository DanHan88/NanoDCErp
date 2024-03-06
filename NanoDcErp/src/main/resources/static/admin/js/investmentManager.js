$(document).ready(function() {
	
        $('#payout_btn').on('click', function() {
			$('#payout_modal').modal('show');
		});

         $('#payout_confirm_button').on('click', function() {
			 	var hw_product_id =  $('#hw_product_id').val();
			 	var total_fil = $('#total_fil').val();
							  $.ajax({
                    type: 'POST',
                    url: '/admin/payout',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        hw_product_id: hw_product_id,
                        total_fil: total_fil,
                        status: "active"
                    }),
                    success: function(data) {
                               $("#detail_product_modal").modal('hide');	 
									if(data=='success'){
										$('#success_alert').modal('show');
			                        }
			                        else if(data='failed:session_closed'){
										$('#fail_alert').text('로그인을 다시해 주십시오.');
										$("#fail_alert").modal('show');	 	
									}
			                        else{
										$('#success_alert_title').text('보상 지급 실패');
										$("#fail_alert").modal('show');	 
									}
                            },
                    error: function(error) {
                        // 요청에 실패했을 때 수행할 동작
                        console.error('승인 요청 실패:', error);
                    },   
                });
        });
 });
