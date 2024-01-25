$(document).ready(function() {
	
	 	var user_name; 
        var user_email; 
        var user_phone_number; 
  		$('.detail_product').on('click', function() {
			$('#detail_product_modal').modal('show');
		});
		$('#add_product_button').on('click', function() {
			$('#add_product_modal').modal('show');
		});
		$(document).on('click', '.delete_user_investment_button', function() {
			$('#investment_delete').val($(this).val());
			$('#delete_user_investment').modal('show'); 
		});
		$(document).on('click', '.update_user_investment_button', function() {
			var superParent = $(this).parent().parent();
			$('#datepicker_update').val(superParent.find('#update_purchase_date').text());
			$('#selectBox_product_update').val(superParent.find('#update_product_name').data('user_id'));
			$('#node_tib_update').val(superParent.find('#update_purchase_size').text());
			$('#node_fil_update').val(superParent.find('#update_fil_invested').text());
			$('#user_investment_add').val($(this).val());
			$('#update_user_investment').modal('show');
			$('#user_investment_update').val($(this).val());
		});	
		$('#user_pw_reset_btn').on('click', function() {
			$('#alert_modal_user_pw_reset').modal('show'); 
		});
		$('#user_edit_1_btn').on('click', function() {		 
				$('#alert_modal_user_update').modal('show'); 
		});
		$('#user_pw_reset_confirm').on('click', function() {
							 var user_id = $('#user_edit_1_btn').val(); 
							  $.ajax({
								type: "POST",
							    url: "/userPwReset",
							    contentType: "application/json",
			                    data: JSON.stringify({
							        user_id: user_id,
							    }),
							    success: function (data) {
									$('#detail_user_modal').modal('hide');
									if(data=='success'){
										if ($('#alert_header_user').hasClass("bg-danger")) 
											{
							            		$('#alert_header_user').removeClass("bg-danger").addClass("bg-success");
							       		 	} 
										$('#alert_title_user').text("회원 비밀번호 초기화 완료");
										$('#alert_modal_user').modal('show');
			                        }
			                        else if(data='failed:session_closed'){
										
										$('#session_alert_user').modal('show');
									}
			                        else{
										if ($('#alert_header_user').hasClass("bg-success")) 
										{
								            $('#alert_header_user').removeClass("bg-success").addClass("bg-danger");
							       		} 			
							       		 $('#alert_title_user').text("회원 비밀번호 초기화 실패");
								            $('#alert_modal_user').modal('show');
									}
			                    }
			                });
						});
    
        $('#register_user').click(function(){
			user_name = $('#user_name').val(); 
	        user_email = $('#user_email').val(); 
	        user_phone_number = $('#user_phone').val(); 
            $.ajax({
                type: "POST",
                url: "/admin/addNewUser",
                contentType: "application/json",
                data: JSON.stringify({
                    user_name: user_name,
                    user_email: user_email,
                    phone_number: user_phone_number,
                    user_status: "active",
                    password: "123123"
                }),
                success: function (data) {
                    $('#upload_user').modal('hide');
                    if (data == 'success') {
                        if ($('#alert_header_user').hasClass("bg-danger")) {
                            $('#alert_header_user').removeClass("bg-danger").addClass("bg-success");
                        } 
                        $('#alert_title_user').text("회원 등록 완료");
                        $('#alert_modal_user').modal('show');
                    } else if (data == 'failed:session_closed') {
                        $('#session_alert_user').modal('show');
                    } else {
                        if ($('#alert_header_user').hasClass("bg-success")) {
                            $('#alert_header_user').removeClass("bg-success").addClass("bg-danger");
                        } 
                        $('#alert_title_user').text("회원 등록 실패");
                        $('#alert_modal_user').modal('show');
                    }
                }
            });
        });
});
