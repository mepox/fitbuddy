function hideStatus() {
    var statusMessage = document.getElementById("status-message");
    statusMessage.hidden = true;    	
}

function showStatus(message) {
    var statusMessage = document.getElementById("status-message");
    statusMessage.hidden = false;
    statusMessage.innerHTML = message;    
}

function clearFormValue(formId, valueName) {
	document.forms[formId][valueName].value = "";	
}

function hideDiv(id) {
	document.getElementById(id).style = "display:none";
	document.getElementById(id).classList.add("d-none");
}

function showDiv(id) {
	document.getElementById(id).style = "display:block";
	document.getElementById(id).classList.remove("d-none");
}