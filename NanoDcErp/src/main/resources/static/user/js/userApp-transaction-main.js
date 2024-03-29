
	$(document).ready(function() {
		var user_id =$("#user_id").val();
		
		 $('#back_button').css('display', 'block');
		var total_reward_fil = Math.floor($('#total_reward_fil').text());
		$('#total_reward_fil').text(total_reward_fil);
		$('#total_reward_fil').show();

		 $(document).on('click', '#back_button', function() {
		    window.history.back();});	
		    
				$('#success_alert').on('hidden.bs.modal', function (e) {
								 window.location.reload();});  
				$('#addNewTransactionPayout').on('click', function() {
					$('#addNewTransactionPayout_modal').modal('show');});
				$('#walletManager').on('click', function() {
					$('#walletManager_modal').modal('show');});
        			$('#addNewTransactionPayout_confirm').click(function(){
						var wallet=$('#wallet_new_transaction_option').val();
						var fil_amount=$('#new_transaction_fil_amount').val();
	
            			$.ajax({
						    type: "POST",
						    url: "/user/addNewTransaction",
						    contentType: "application/json", 
						    data: JSON.stringify({
						        fil_amount: fil_amount,
						        user_id: user_id,
						        status: "출금신청",
						        wallet_id: wallet,
						        type: "출금신청"
						    }),
						    success: function (data) {
								
						        $("#detail_product_modal").modal('hide');
						        if (data === 'success') {
						            $('#success_alert_title').text('송금신청 완료');
						            $('#success_alert').modal('show');
						        } else if (data === 'failed:session_closed') {
						            $('#fail_alert').text('로그인을 다시해 주십시오.');
						            $("#fail_alert").modal('show');
						        } else {
						            $('#success_alert_title').text('송금신청 실패');
						            $("#fail_alert").modal('show');
						        }
						    }
						});

        			});
        			 $('#deleteWallet_confirm').click(function(){
						var wallet_id=$('#wallet_delete_option').val();
            			$.ajax({
			                    type: "POST",
			                    url: "/user/deleteWalletByWalletId",
			                    contentType: "application/json",
			                    data: JSON.stringify({
							        wallet_id: wallet_id
							    }),
			                    success: function (data) {
										$("#detail_product_modal").modal('hide');	 
									if(data=='success'){
										$('#success_alert_title').text('지갑삭제 완료');
										$('#success_alert').modal('show');}
			                        else if(data='failed:session_closed'){
										$('#fail_alert').text('로그인을 다시해 주십시오.');
										$("#fail_alert").modal('show');}
			                        else{
										$('#success_alert_title').text('지갑삭제 실패');
										$("#fail_alert").modal('show');}}
			                	});  
       						});
        			$('#addNewWallet_confirm').click(function(){
						var hw_product_id=$('#hw_product_id').val();
            			$.ajax({
			                    type: "POST",
			                    url: "/user/userAppMainInfoBuilder",
			                    contentType: "application/json",
			                    data: JSON.stringify({
							        hw_product_id: hw_product_id
							    }),    
			                    success: function (data) {	
									$("#detail_product_modal").modal('hide');	 
									if(data=='success'){
										$('#success_alert_title').text('유저 새지갑 등록 성공');
										$('#success_alert').modal('show');}
			                        else{
										$('#success_alert_title').text('유저 새지갑 등록 실패');
										$("#fail_alert").modal('show');}
			                    	}
			                	});  
					        });                
						});						
						
						
					