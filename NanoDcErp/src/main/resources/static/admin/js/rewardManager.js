$(document).ready(function() {
		$('#dataTableContainer').show();
						 $('#alert_modal_payout').on('hidden.bs.modal', function (e) {
							 if ($('#alert_header_payout').hasClass("bg-success")){
								 location.reload(true);
							 } 
					      });			
						$('.detail_reward').on('click', function() {
							var reward_sharing_id = $(this).val();
							$.ajax({
			                    type: "POST",
			                    url: "/admin/selectRewardSharingDetailListById",
			                    contentType: "application/json",
			                    data: reward_sharing_id,
			                    success: function (data) {
									debugger;
									$("#reward_detail_table").find('tbody').empty();
									data.forEach(function(item) {
									    var newRow = '<tr>' +
									        '<td align="center" id="update_purchase_date">' + item.userInfoVO.user_email + '</td>' +
									        '<td align="center" id="update_purchase_size">' + item.userInfoVO.user_name + '</td>' +
									        '<td align="center" id="update_fil_invested">' + item.reward_fil + '</td>';
									    $("#reward_detail_table").append(newRow);
									});
									
			                    }
			                });
					       $('#reward_detail_list_modal').modal('show');
					    });	
					    $('.payout_update').on('click', function() {
							$("#payout_update_confirm_btn").val($(this).val());
							$('#payout_fil_per_tb').val($(this).parent().parent().find('#paid_per_tb_td').text());
							$('#original_fil_per_tb').val($('#payout_fil_per_tb').val());
							if($(this).parent().parent().find('#status_td').text()=='정상'){
								$('input[name="payout_status"][value="정상"]').prop('checked', true);
							}else{
								$('input[name="payout_status"][value="취소"]').prop('checked', true);
							}		
					       $('#payout_update_modal').modal('show');
					    });	
						
});
