$(document).ready(function() {
	
	 var user_name; 
        var user_email; 
        var user_phone_number; 
    $('#create_user').click(function(){
        $('#upload_user').modal('show');
    });
    
   $('.detail_user').on('click', function() {
    	$('#detail_user_modal').modal('show');
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
