$(document).ready(function() {
	
	 var user_name; 
        var user_email; 
        var user_phone_number; 
    // jQuery code executes after the document is fully loaded
    
    // Click event for the button with id 'create_user'
    $('#create_user').click(function(){
        // Show the modal with id 'upload_user'
        $('#upload_user').modal('show');
        
        // Get values from input fields
       
		
       
    });
    
  $('.detail_user').on('click', function() {
							var clickedButton = $(this);
							$('#join_date_p').text(clickedButton.parent().parent().find('.user_reg_date').text());
							$('#user_name_span').text(clickedButton.parent().parent().find('.user_name').text());
							$('#user_email_span').text(clickedButton.parent().parent().find('.user_email').text());
							$('#user_phone_span').text(clickedButton.parent().parent().find('.user_phone').text());
							$('#user_name').val(clickedButton.parent().parent().find('.user_name').text());
							$('#user_email').val(clickedButton.parent().parent().find('.user_email').text());
							$('#user_phone').val(clickedButton.parent().parent().find('.user_phone').text());
							$('#profilePicture').attr('src', '/uploads'+clickedButton.parent().parent().attr('data-picture'));
							var user_id = clickedButton.parent().parent().find('.detail_user').val();
							$('#user_edit_1_btn').val(user_id);
	
	
							
						$('#wallet_address_balance_a').text(clickedButton.attr('data-filAmount')+" FIL");
						$('#wallet_address_balance_b').text(clickedButton.attr('data-filAmount')+" FIL");
						$('#wallet_address_balance_c').text(clickedButton.attr('data-filAmount')+" FIL");
						
							if(clickedButton.parent().parent().find('.user_status').text()=='acticve'){
								$('input[name="user_status"][value="active"]').prop('checked', true);
							}else{
								$('input[name="user_status"][value="inactive"]').prop('checked', true);
							}				
							/* $.ajax({
			                    type: "POST",
			                    url: "/selectInvestmentListForUser",
			                    contentType: "application/json",
			                    data: user_id,
			                    success: function (data) {
									$("#investment_user_table").find('tbody').empty();
									data.forEach(function(item) {
										var date = new Date(item.purchase_date);
									    var formattedDate = date.getFullYear() + '-' +
									                        ('0' + (date.getMonth() + 1)).slice(-2) + '-' +
									                        ('0' + date.getDate()).slice(-2);
									    var newRow = '<tr>' +
									        '<td align="center" id="update_purchase_date">' + formattedDate + '</td>' +
									        '<td align="center" id="update_product_name" data-user_id="'+  item.investment_category_id + '">' + item.product_name + '</td>' +
									        '<<td align="center" id="update_purchase_size">' + item.purchase_size + '</td>' +
									        '<td align="center" id="update_fil_invested">' + item.fil_invested + '</td>' +
									        '<td align="center"><button type="button" class="btn btn-secondary btn-sm m-1 update_user_investment_button" value="' + item.investment_id + '">수정</button>'+
									        '<button type="button" class="btn btn-secondary btn-sm m-1 delete_user_investment_button" value="'+ item.investment_id + '">삭제</button></td></tr>';
									    $("#investment_user_table").append(newRow);
									});
									
			                    }
			                });*/ 
							$('#detail_user_modal').modal('show');
					    });
					    $('#add_user_investment_button').on('click', function() {
					       $('#add_user_investment').modal('show');
					    });
					    $('#add_user_button').on('click', function() {
					       $('#add_user_modal').modal('show');
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
    
    
    
     // Click event for the button with id 'register_user' inside the 'create_user' click event
        $('#register_user').click(function(){

			 user_name = $('#user_name').val(); 
        user_email = $('#user_email').val(); 
        user_phone_number = $('#user_phone').val(); 
            // AJAX request
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
                    // Hide the 'upload_user' modal
                    $('#upload_user').modal('hide');

                    // Check the response and update modal accordingly
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
