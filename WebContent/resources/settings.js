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
					html += '<p><label for="' + key + '">' + key + '</label> ';
					html += '<input type="text" id="' + key + '" name="' + key + '" value="' + data[key] + '" /></p>';
				}
				$('#droneSettings').html(html);
			}
		});
	});
	
})();


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
	
	elem.style.background = '#3333AA';
}

openPage(document.getElementById("gps_menu"));