var resizeWebViewCalled = false;
var href = '/user/'; 
var src = "/assets/img/filmountain/";
var isRunning = false;
var isClicked = false;
var investment_img = [
        '/assets/img/filmountain/serverImg/investment1.png',
        '/assets/img/filmountain/serverImg/investment2.png',
        '/assets/img/filmountain/serverImg/investment3.png',
        '/assets/img/filmountain/serverImg/investment4.png',
        '/assets/img/filmountain/serverImg/investment5.png'
      ];
var price_img = [
        '/assets/img/filmountain/serverImg/price1.png',
        '/assets/img/filmountain/serverImg/price2.png',
        '/assets/img/filmountain/serverImg/price3.png',
        '/assets/img/filmountain/serverImg/price4.png',
        '/assets/img/filmountain/serverImg/price5.png'
      ];
var status_img = [
        '/assets/img/filmountain/serverImg/status1.png',
        '/assets/img/filmountain/serverImg/status2.png',
        '/assets/img/filmountain/serverImg/status3.png',
        '/assets/img/filmountain/serverImg/status4.png',
        '/assets/img/filmountain/serverImg/status5.png'
      ];
var cash_img = [
        '/assets/img/filmountain/serverImg/cash1.png',
        '/assets/img/filmountain/serverImg/cash2.png',
        '/assets/img/filmountain/serverImg/cash3.png',
        '/assets/img/filmountain/serverImg/cash4.png',
        '/assets/img/filmountain/serverImg/cash5.png'
      ];  
var reward_img = [
        '/assets/img/filmountain/serverImg/reward1.png',
        '/assets/img/filmountain/serverImg/reward2.png',
        '/assets/img/filmountain/serverImg/reward3.png',
        '/assets/img/filmountain/serverImg/reward4.png',
        '/assets/img/filmountain/serverImg/reward5.png'
      ];  
    
 var currentStage = 0;

function resizeWebView() {
						    var imageHeight = $('.custom_container').height();
						    var imageWidth = $('.custom_container').width();	    
						    $('.userApp_buttons').css('margin-left',imageWidth/3);
						    $('.userApp_buttons').css('padding-left',imageWidth/15);  
						    if(imageHeight<700){
								$('#app_button1').css('padding-top',imageHeight/40);
							     $('#app_button2').css('padding-top',imageHeight/18);
							     $('#app_button3').css('padding-top',imageHeight/18);
							     $('#app_button4').css('padding-top',imageHeight/18);
							     $('#app_button5').css('padding-top',imageHeight/18);
							     $('.app_text').css('font-size',imageHeight/50);
							}else if (imageHeight<800){
								$('#app_button1').css('padding-top',imageHeight/50);
							     $('#app_button2').css('padding-top',imageHeight/17.5);
							     $('#app_button3').css('padding-top',imageHeight/17.5);
							     $('#app_button4').css('padding-top',imageHeight/17.5);
							     $('#app_button5').css('padding-top',imageHeight/17.5);
							     $('.app_text').css('font-size',imageHeight/50);
							}else{
								$('#app_button1').css('padding-top',imageHeight/55);
							     $('#app_button2').css('padding-top',imageHeight/16.5);
							     $('#app_button3').css('padding-top',imageHeight/16.5);
							     $('#app_button4').css('padding-top',imageHeight/16.5);
							     $('#app_button5').css('padding-top',imageHeight/16.5);
							     $('.app_text').css('font-size',imageHeight/50);
							}
						    resizeWebViewCalled = true;
						    $('#userApp_view').css('visibility', 'visible').show();	  
						    $('#power_button').css('visibility', 'visible').show();	  
						    $('#debug').text('width: ' +imageWidth +'height: ' + imageHeight); 
						    $('.panel_buttons').css('filter', 'brightness(0.50)');
						    $('.app_text').css('filter', 'brightness(0.50)');
						}	
						
						
$(document).ready(function() {
	function buttonAnimation(button,img_list){
		var intervalId = setInterval(function() {
		          button.attr('src', img_list[currentStage]);
		          currentStage++;
		          if (currentStage >= img_list.length) {
		            clearInterval(intervalId);
		          }
		        }, 200);
	  }
	  $(window).resize(function() {
    		resizeWebView();
    console.log('창 크기가 변경되었습니다.');
	});
	  function handleButtonClick(clickedObject) {
         
         if(!isRunning){
				runServer();
				isRunning = true;
				return;
			}
			if(isClicked){
				return;
			}
			isClicked=true;
			href = '/user/';
			 playButtonSound();
		if($(clickedObject).attr('id') == 'app_button1'){
			src +="investment.png";
			href += 'investment'; 
			currentStage =1;
			$('#panel_investment').css('filter', 'brightness(1.5)'); 
			buttonAnimation($("#panel_investment"),investment_img);
			
		}else if($(clickedObject).attr('id') == 'app_button2'){
			src +="reward.png";
			href += 'reward';
			currentStage =1;
			$('#panel_reward').css('filter', 'brightness(1.5)'); 
			buttonAnimation($("#panel_reward"),reward_img);
		}else if($(clickedObject).attr('id') == 'app_button3'){
			src +="cash.png";
			href += 'cash';
			currentStage =1;
			$('#panel_cash').css('filter', 'brightness(1.5)'); 
			buttonAnimation($("#panel_cash"),cash_img);
		}else if($(clickedObject).attr('id') == 'app_button4'){
			src +="status.png";
			href = 'https://filfox.info/ko/address/f02368818';
			currentStage =1;
			$('#panel_status').css('filter', 'brightness(1.5)'); 
			buttonAnimation($("#panel_status"),status_img);
		}
		else if($(clickedObject).attr('id') == 'app_button5'){
			src +="price.png";
			href = 'https://lightning.korbit.co.kr/trade/?market=fil-krw';
			currentStage =1;
			$('#panel_price').css('filter', 'brightness(1.5)'); 
			buttonAnimation($("#panel_price"),price_img);
		}
		 $('#panel').attr('src', src);
		$('#panel').css('visibility', 'visible');
        setTimeout(function() {
          window.location.href = href;
        }, 900);
         
      }
      function resetButton(clickedObject) {
		  if(!isRunning){
				return;
			}
         if($(clickedObject).attr('id') == 'app_button1'){
			$('#panel_investment').css('filter', 'brightness(1)'); 
			
		}else if($(clickedObject).attr('id') == 'app_button2'){
			$('#panel_reward').css('filter', 'brightness(1)'); 
		}else if($(clickedObject).attr('id') == 'app_button3'){
			$('#panel_cash').css('filter', 'brightness(1)'); 
		}else if($(clickedObject).attr('id') == 'app_button4'){
			$('#panel_status').css('filter', 'brightness(1)'); 
		}
		else if($(clickedObject).attr('id') == 'app_button5'){
			$('#panel_price').css('filter', 'brightness(1)'); 
		}
		
      }
      
      function handlePowerClick() {
		  if(!isRunning){
			runServer();
			isRunning = true;
			}
			else{
				offServer();
				playServerOnSound();
				isRunning = false;
			}
			$('#power_button').css('filter', 'brightness(1.5)'); 
		}
		 function resetPowerButton() {
		  $('#power_button').css('filter', 'brightness(1)'); 
		}
      
      
      
      
	 $('.userApp_buttons')
    .on('mousedown touchstart', function(event) {
        event.preventDefault(this); 
        handleButtonClick(this);   
    })
    .on('mouseup mouseleave touchend touchcancel', function(event) {
        event.preventDefault(this);
        resetButton(this);
    });

$('#power_button')
    .on('mousedown touchstart', function(event) {
        event.preventDefault();
        handlePowerClick();
    })
    .on('mouseup mouseleave touchend touchcancel', function(event) {
        event.preventDefault();
        resetPowerButton();
    });
	
	
	 setTimeout(function () {
        if (!resizeWebViewCalled) {
            resizeWebView();
        }
    }, 1000);
		 function runServer() {
         playBGM();
         playServerOnSound();
			var elements = $('.panel_buttons');
			var textElements = $('.app_text');
			
			elements.each(function(index) {
			    var currentElement = $(this);
			    var currentTextElement = textElements.eq(index);
			
			    // Set a delay for each element
			    setTimeout(function() {
			        currentElement.css('filter', 'brightness(1)');
			        currentTextElement.css('filter', 'brightness(1)');
			    }, index * 200);
			});
      }
      function offServer() {
         stopBGM();
			var elements = $('.panel_buttons');
			var textElements = $('.app_text');
			
			elements.each(function(index) {
			    var currentElement = $(this);
			    var currentTextElement = textElements.eq(index);
			
			    // Set a delay for each element
			    setTimeout(function() {
			        currentElement.css('filter', 'brightness(0.75)');
			        currentTextElement.css('filter', 'brightness(0.75)');
			    }, index * 200);
			});
      }
	
  function playBGM() {
    $('#bgm')[0].play();
  }
  function stopBGM() {
    $('#bgm')[0].pause();
    $('#bgm')[0].currentTime = 0;
  }
  function playButtonSound(){
	   $('#buttonSound')[0].play();
  }
   function playServerOnSound(){
	   $('#serverOnSound')[0].currentTime = 0;
	   $('#serverOnSound')[0].play();
  }
  
  
  
	});						
						
						
					