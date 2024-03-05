$(document).ready(function() {
	
        $('#payout_btn').on('click', function() {
			$('#payout_modal').modal('show');
		});
        
         $('#payout_confirm_button').on('click', function() {
							$('#loadingModal_investment').modal('show');
							 $('#payout_confirm_button').hide();
							  $.ajax({
                    type: 'POST',
                    url: '/admin/payout',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        transaction_id: tx_id,
                        status: "출금승인",
                        fil_amount: fil_amount,
                        wallet_address :wallet_address
                    }),
                    success: function(data) {
                                if (data === 'success') {	
                                    // 성공 시 모달 내용 변경
                                    $('#success_header_fundRequest').removeClass("bg-danger").addClass("bg-success");
                                    $('#success_title_fundRequest').text("승인 되었습니다");
                                    $('#confirmSuccessModal').modal('show'); 
                                    // 승인 완료 모달 표시
                                } else if (data === 'failed:session_closed') {
                                    // 세션 종료 시 다른 처리
                                    $('#session_alert_user').modal('show');
                                    
                                } else {
                                    // 실패 시 모달 내용 변경
                                    $('#success_header_fundRequest').removeClass("bg-success").addClass("bg-danger");
									$('#success_body_fundRequest').text(data);
                                    $('#success_title_fundRequest').text("승인 실패하였습니다");
                                    $('#confirmSuccessModal').modal('show');
                                }
                            },
                    error: function(error) {
                        // 요청에 실패했을 때 수행할 동작
                        console.error('승인 요청 실패:', error);
                    },   
                });
        });
 });
