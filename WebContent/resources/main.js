(function() {
	
	'use strict';
	
	function delayKill() {
		console.log('contacting drone');
		$.ajax({
		    url: './rest/status/report',
		    type: 'post',
		    error: function(){
		    	$('#status').html('Cannot contact drone!');
		    	console.log('contacting drone failed');
		    },
		    success: function(){
		        $('#status').html();
		        console.log('contacting drone successful');
		    },
		    timeout: 100
		});
	}
	setInterval(delayKill,1000);
	
	function move(event) {
		$('#cursor').css('left', (event.pageX - 10) + 'px');
		$('#cursor').css('top', (event.pageY - 20) + 'px');
		
		var left = (event.pageX < 200) ? (200 - event.pageX) / 200 : 0;
		var right = (event.pageX > 200) ? (event.pageX - 200) / 200 : 0;;
		var forward = (event.pageY < 200) ? (200 - event.pageY) / 200 : 0;;
		var backward = (event.pageY > 200) ? (event.pageY - 200) / 200 : 0;;
		
		$("#dynamicDirection").html(
			'FW:'+forward+', BW:'+backward+', LF:'+left+', RT: '+right 
		);
	}
	
	var _isDragging = false;
	$('#dynamicControls').mousedown(function(event) {
		_isDragging = true;
		move(event);
	});
	
	$('body').mouseup(function() {
		_isDragging = false;
		$('#cursor').css('left', '50%');
		$('#cursor').css('top', '50%');
		$("#dynamicDirection").html('FW:0, BW:0, LF:0, RT:0');
	});
	
	$('#dynamicControls').mousemove(function(event) {
		if (_isDragging) {
			move(event);
		}
	});
	
})();