	var user_id;
	$(document).ready(function() {
		  $('iframe').on('load', function() {
	        var iframeDocument = $(this).contents();
	        var iframeHeight = iframeDocument.height();
	       debugger;
	       $('#status_iframe').height(iframeHeight);
	       
	    });
  
	});						
						
						
					