$(document).ready(function() {
		$('#dataTableContainer').show();
						 $('#alert_modal_payout').on('hidden.bs.modal', function (e) {
							 if ($('#alert_header_payout').hasClass("bg-success")){
								 location.reload(true);
							 } 
					      });			
						$('.payout_detail').on('click', function() {
							var token_paid_id = $(this).val();
							$('#payout_detail_table').DataTable({
						        "order": [[0, 'desc']],
						        "destroy": true,
						        "ajax": {
						            "type": "POST",
						            "url": "/selectTokenDetailList",
						            "contentType": "application/json",
						            "data": function (d) {
						                return JSON.stringify({
						                    token_paid_id: token_paid_id
						                });
						            },
						            "dataSrc": function (data) {
						                return data;
						            }
						        },
						        "columns": [
						            { "data": "user_email" },
						            { "data": "user_name" },
						            { "data": "investment_category_name" },
						            { "data": "paid_fil" },
						        ]
						    });
						    $('.table-responsive').addClass('p-2');
					       $('#payout_detail_list_modal').modal('show');
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
