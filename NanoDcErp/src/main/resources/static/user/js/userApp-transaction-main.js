	var user_id;
	$(document).ready(function() {
	user_id = 1; //$('#user_id').val();

	$('#addNewTransactionPayout').on('click', function() {
			$('#addNewTransactionPayout_modal').modal('show');
		});
		$('#walletManager').on('click', function() {
			$('#walletManager_modal').modal('show');
		});
		
        $('#addNewTransactionPayout_confirm').click(function(){
						var wallet=$('#new_transaction_wallet').val();
						var fil_amount=$('#new_transaction_fil_amount').val();
            			$.ajax({
			                    type: "POST",
			                    url: "/user/addNewTransaction",
			                    contentType: "application/json",
			                    data: JSON.stringify({
							        fil_amount: fil_amount,
							        user_id: user_id,
							        status: '출금신청',
							        wallet : wallet,
							        type  : '출금신청'
							    }),
			                    success: function (data) {
										

			                    	}
			                	});  
        });
        $('#addNewWallet_confirm').click(function(){
						var address=$('#new_wallet_address').val();
            			$.ajax({
			                    type: "POST",
			                    url: "/user/addNewWallet",
			                    contentType: "application/json",
			                    data: JSON.stringify({
							        address: address,
							        user_id: user_id
							    }),
			                    success: function (data) {
			                    	}
			                	});  
        });                


  
	});						
						
						
					