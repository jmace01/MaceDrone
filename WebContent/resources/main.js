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
	
	
	function move(pageX, pageY) {
		if (!_isDragging) {
			return;
		}
		
		$('#cursor').css('left', (pageX - 10) + 'px');
		$('#cursor').css('top', (pageY - 20) + 'px');
		
		var left = (pageX < 200) ? (200 - pageX) / 2 : 0;
		var right = (pageX > 200) ? (pageX - 200) / 2 : 0;;
		var forward = (pageY < 200) ? (200 - pageY) / 2 : 0;;
		var backward = (pageY > 200) ? (pageY - 200) / 2 : 0;;
		
		$("#dynamicDirection").html(
			'FW:'+forward+', BW:'+backward+', LF:'+left+', RT: '+right 
		);
	}
	
	function moveStart(xPos, yPos) {
		_isDragging = true;
		move(xPos, yPos);
	}
	
	function moveEnd() {
		_isDragging = false;
		$('#cursor').css('left', '50%');
		$('#cursor').css('top', '50%');
		$("#dynamicDirection").html('FW:0, BW:0, LF:0, RT:0');
	}
	
	var _isDragging = false;
	$('#dynamicControls').mousedown(function(event) {
		var xPos = event.pageX;
		var yPos = event.pageY;
		moveStart(xPos, yPos)
	});
	
	$('body').mouseup(function(event) { moveEnd(); });
	
	$('#dynamicControls').mousemove(function(event) {
		var xPos = event.pageX;
		var yPos = event.pageY;
		move(xPos, yPos);
	});
	
	$(document).on('touchstart', '#dynamicControls', function(event) {
		event.preventDefault();
		var xPos = event.originalEvent.touches[0].pageX;
		var yPos = event.originalEvent.touches[0].pageY;
		moveStart(xPos, yPos);
	});
	
	$(document).on('touchmove', '#dynamicControls', function(event) {
		event.preventDefault();
		var xPos = event.originalEvent.touches[0].pageX;
		var yPos = event.originalEvent.touches[0].pageY;
		move(xPos, yPos);
	});
	
	$(document).on('touchend', '#dynamicControls', function(event) {
		event.preventDefault();
		moveEnd();
	});
	
})();