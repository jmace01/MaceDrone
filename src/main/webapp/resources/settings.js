(function(){
	
	'use strict';
	
	$('document').ready(function() {
		$.ajax({
			url: './rest/settings',
			type: 'get',
			dataType: 'json',
			success: function(data){
				var html = '';
				for (var key in data) {
					//Turn "LABEL_TEXT" into "Label Text"
					var labelText = key.replace(new RegExp('_', 'g'), ' ').toLowerCase();
					html += '<p><label for="' + key + '" style="text-transform:capitalize;">' + labelText + '</label> ';
					html += '<input type="text" id="' + key + '" onblur="changeDroneSetting(this)" value="' + data[key] + '" /></p>';
				}
				$('#droneSettings').html(html);
			}
		});
		
		var html = '';
		for (var key in $.settings) {
			//Turn "LABEL_TEXT" into "Label Text"
			var labelText = key.replace(new RegExp('_', 'g'), ' ').toLowerCase();
			html += '<p><label for="' + key + '" style="text-transform:capitalize;">' + labelText + '</label> ';
			html += '<input type="text" id="' + key + '" value="' + $.settings[key] + '" onblur="changeControllerSetting(this)" /></p>';
		}
		$('#controllerSettings').html(html);
		
		$('#portraitMenu div').on('touchstart click', function(){
			openPage(this);
		});
		
		openPage(document.getElementById("gps_menu"));
		resetSettings();
	});
	
	
	function openPage(elem) {
		if (elem.id == 'gps_menu') {
			var show = '#gps';
		} else if (elem.id == 'droneSettings_menu') {
			var show = '#droneSettings';
		} else {
			var show = '#controllerSettings';
		}
		
		$('#gps').hide();
		$('#droneSettings').hide();
		$('#controllerSettings').hide();
		
		$(show).show();
		
		$('#gps_menu').css('background', '');
		$('#droneSettings_menu').css('background', '');
		$('#controllerSettings_menu').css('background', '');
		
		$('#gps_menu').css('color', '');
		$('#droneSettings_menu').css('color', '');
		$('#controllerSettings_menu').css('color', '');
		
		elem.style.background = '#DDDDDD';
		elem.style.color = '#333333';
	}
	
})();


var _temp_interval = null;
var _gps_interval = null;
function resetSettings() {
	if (_temp_interval)
		clearInterval(_temp_interval);
	if (_gps_interval)
		clearInterval(_gps_interval);
	_temp_interval = setInterval(temp, $.settings.TEMP_INTERVAL_MS);
	_gps_interval = setInterval(gps, $.settings.GPS_INTERVAL_MS);
}


function changeDroneSetting(elem) {
	$.ajax({
		url: './rest/settings/value',
		type: 'post',
		dataType: 'json',
		data: { name: elem.id, value: elem.value },
		success: function(data){
			elem.value = data;
		},
		timeout: $.settings.MAX_CONNECTION_TIME_MS
	});
}


function changeControllerSetting(elem) {
	$.settings[elem.id] = elem.value;
	resetSettings();
}