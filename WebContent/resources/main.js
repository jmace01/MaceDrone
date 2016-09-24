$.settings = Object({
	TEMP_INTERVAL_MS       : 5000,
	GPS_INTERVAL_MS        : 1000,
	MAX_CONNECTION_TIME_MS : 750
});


(function() {

	'use strict';


	var _draggingLeft  = false;
	var _draggingRight = false;
	var _startX1       = 0;
	var _startY1       = 0;
	var _startX2       = 0;
	var _startY2       = 0;


	function temp() {
		$.ajax({
			url: './rest/information/temperature',
			type: 'get',
			dataType: 'json',
			error: function(){
				$('#status').show();
				$('#status').html('Cannot contact drone!');
			},
			success: function(data){
				$('#status').hide();
				$('#temp').html('Temp: '+data['temp']);
			},
			timeout: $.settings.MAX_CONNECTION_TIME_MS
		});
	}


	function gps() {
		$.ajax({
			url: './rest/information/gps',
			type: 'get',
			dataType: 'json',
			error: function(){
				$('#status').show();
				$('#status').html('Cannot contact drone!');
			},
			success: function(data){
				$('#sat').html(data['satellites']);
				$('#long').html(data['longitude']);
				$('#lat').html(data['latitude']);
				$('#alt').html(data['altitude']);
				$('#speed').html(Math.round(parseFloat(data['speed']) * 1000000) / 1000000);
			},
			timeout: $.settings.MAX_CONNECTION_TIME_MS
		});
	}


	setInterval(temp, $.settings.TEMP_INTERVAL_MS);
	setInterval(gps, $.settings.GPS_INTERVAL_MS);


	function moveStart(id, posX, posY, isLeft) {
		if (isLeft) {
			if (_draggingLeft) return;
			var ring       = '#leftRing';
			_startX1       = posX;
			_startY1       = posY;
			_draggingLeft  = id;
		} else {
			if (_draggingRight) return;
			var ring       = '#rightRing';
			_startX2       = posX;
			_startY2       = posY;
			_draggingRight = id;
		}
		$(ring).css('left', (posX - 45) + 'px');
		$(ring).css('top', (posY - 45) + 'px');
		$(ring).show();
	}


	function move(posX, posY, isLeft) {
		if (isLeft) {
			if (!_draggingLeft) return;
			var ring = '#leftRing';
		} else {
			if (!_draggingRight) return;
			var ring = '#rightRing';
		}

		$(ring).css('left', (posX - 45) + 'px');
		$(ring).css('top', (posY - 45) + 'px');
	}


	function moveEnd(isLeft) {
		if (isLeft) {
			_draggingLeft  = false;
			var ring       = '#leftRing';
		} else {
			_draggingRight = false;
			var ring       = '#rightRing';
		}
		$(ring).hide();
	}


	$(document).on('touchstart', 'body', function(event) {
		if (event.target.id != 'controlsLeft' && event.target.id != 'controlsRight') {
			return;
		}
		
		event.preventDefault();

		var touch = event.changedTouches[0];

		var xPos = touch.pageX;
		var yPos = touch.pageY;
		moveStart(touch.identifier, xPos, yPos, (event.target.id == 'controlsLeft'));
	});


	$(document).on('touchmove', 'body', function(event) {
		if (event.target.id != 'controlsLeft' && event.target.id != 'controlsRight') {
			return;
		}
		
		event.preventDefault();

		var touches = event.changedTouches;
		for (var i = 0; i < touches.length; i++) {
			var xPos = touches[i].pageX;
			var yPos = touches[i].pageY;
			if (touches[i].identifier === _draggingLeft) {
				move(xPos, yPos, true);
			} else if (touches[i].identifier === _draggingRight) {
				move(xPos, yPos, false);
			}
		}
	});


	$(document).on('touchend', 'body', function(event) {
		if (event.target.id != 'controlsLeft' && event.target.id != 'controlsRight') {
			return;
		}
		
		event.preventDefault();

		var touch = event.changedTouches[0];
		if (touch.identifier != _draggingLeft && touch.identifier != _draggingRight) {
			return;
		}

		var isLeft = (touch.identifier == _draggingLeft);
		moveEnd(isLeft);
	});


})();