$(document).ready(function() {
        // jQuery code executes after the document is fully loaded
        $('#create_user').click(function(){
            $('#upload_user').modal('show');
            var user_name = $('#user_name').val(); 
            var user_email = $('#user_email').val(); 
            var user_phone_number = $('#user_phone').val(); 
            
            	 $('#register_user').click(function(){
					 $.ajax({
			                    type: "POST",
			                    url: "/regMemo",
			                    contentType: "application/json",
			                    data: JSON.stringify({
									user_id: user_name,
									memo  : user_email,
									password : "123123"
							    }),
			                    success: function (data) {
									$('#add_user_modal').modal('hide');
									if(data=='success'){
										if ($('#alert_header_user').hasClass("bg-danger")) 
											{
							            		$('#alert_header_user').removeClass("bg-danger").addClass("bg-success");
							       		 	} 
										$('#alert_title_user').text("메모 등록 성공");
										$('#alert_modal_user').modal('show');
			                        }else if(data='failed:session_closed'){
										$('#session_alert_user').modal('show');
									}
			                        else{
										if ($('#alert_header_user').hasClass("bg-success")) 
										{
								            $('#alert_header_user').removeClass("bg-success").addClass("bg-danger");
							       		} 			
							       		 $('#alert_title_user').text("메모 등록 실패");
								            $('#alert_modal_user').modal('show');
									}
			                    	}
			                	});  
					 
					 
					 )};
            
           });
    });