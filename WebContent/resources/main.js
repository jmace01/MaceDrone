$.settings = Object({
	PING_INTERVAL_MS : 1000,
	MAX_PING_CONNECTION_TIME_MS : 250
});


(function() {
	
	'use strict';
	
	function ping() {
		$.ajax({
		    url: './rest/status/report',
		    type: 'post',
		    error: function(){
		    	$('#status').html('Cannot contact drone!');
		    },
		    success: function(){
		        $('#status').html('');
		    },
		    timeout: $.settings.MAX_PING_CONNECTION_TIME_MS
		});
	}
	
	setInterval(ping, $.settings.PING_INTERVAL_MS);
	
	function move(event) {
		$('#cursor').css('left', (event.pageX - 10) + 'px');
		$('#cursor').css('top', (event.pageY - 20) + 'px');
		
		var left = (event.pageX < 200) ? (200 - event.pageX) / 2 : 0;
		var right = (event.pageX > 200) ? (event.pageX - 200) / 2 : 0;;
		var forward = (event.pageY < 200) ? (200 - event.pageY) / 2 : 0;;
		var backward = (event.pageY > 200) ? (event.pageY - 200) / 2 : 0;;
		
		$("#dynamicDirection").html(
			'FW:'+forward+', BW:'+backward+', LF:'+left+', RT: '+right 
		);
	}
	
	var _isDragging = false;
	$('#dynamicControls').mousedown(function(event) {
		event.preventDefault();
		_isDragging = true;
		move(event);
	});
	
	$('body').mouseup(function() {
		event.preventDefault();
		_isDragging = false;
		$('#cursor').css('left', '50%');
		$('#cursor').css('top', '50%');
		$("#dynamicDirection").html('FW:0, BW:0, LF:0, RT:0');
	});
	
	$('#dynamicControls').mousemove(function(event) {
		event.preventDefault();
		if (_isDragging) {
			move(event);
		}
	});
	
})();