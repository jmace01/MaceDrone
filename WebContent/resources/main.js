(function() {
	
	'use strict';
	
	var timer;
	function delayKill() {
		ajaxSend('./rest/status/report', '', 'POST', handleDelayKillResponse);
		timer = setTimeout("reportTimeout()",100);
	}
	
	function handleDelayKillResponse() {
		if (connection.readyState == 4 && connection.status == 200) {
		    clearTimeout(timer);
		    document.getElementById("status").innerHTML = '';
		}
	}
	
	setInterval(delayKill,2000);
	
})();

var connection;
function reportTimeout() {
	document.getElementById("status").innerHTML = 'CANNOT CONTACT DRONE';
	connection.abort();
}

var connection;
function ajaxSend(url, data, method, callbackFunction){
    var ready = false;

    try{// Opera 8.0+, Firefox, Safari
        connection = new XMLHttpRequest();}
    catch (e){// Internet Explorer Browsers
        try{
            connection = new ActiveXObject("Msxml2.XMLHTTP");}
        catch (e){
            try{
                connection = new ActiveXObject("Microsoft.XMLHTTP");}
            catch (e){// Something went wrong
                ready = true;
                return false;
            }
        }
    }

    connection.open(method, url, true);
    connection.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    connection.onreadystatechange = callbackFunction;
    connection.send(data);
    return true;
}