const ACCOUNT_API_PATH = "/user/account";

function showUserName() {	
	let xhr = new XMLHttpRequest();
	xhr.open("GET", ACCOUNT_API_PATH);
    xhr.send();
    
    xhr.onreadystatechange = function() {
        if (this.readyState == XMLHttpRequest.DONE) {
			if (this.status == 200) {
				// SUCCESS
				let data = JSON.parse(this.responseText);
				
				document.getElementById("logged-in-name").innerHTML += data.name;	
			} else {
				// ERROR				
				console.log("ERROR: " + this.responseText);				
			}
		}
	};        
}

function showAccount() {
	let xhr = new XMLHttpRequest();
	xhr.open("GET", ACCOUNT_API_PATH);
    xhr.send();
    
    xhr.onreadystatechange = function() {
        if (this.readyState == XMLHttpRequest.DONE) {
			if (this.status == 200) {
				// SUCCESS
				let data = JSON.parse(this.responseText);
				
				clearFormValue("account-form", "old-password");
				clearFormValue("account-form", "new-password");
				clearFormValue("account-form", "confirm-new-password");																	
			} else {
				// ERROR				
				console.log("ERROR: " + this.responseText);				
			}
		}
	};      
}

function updateAccount() {	
	let oldPassword = document.forms["account-form"]["old-password"].value;
	let newPassword = document.forms["account-form"]["new-password"].value;
	let confirmNewPassword = document.forms["account-form"]["confirm-new-password"].value;
	
	oldPassword = oldPassword.trim();
	newPassword = newPassword.trim();
	confirmNewPassword = confirmNewPassword.trim();
	
	if (newPassword !== confirmNewPassword) {
		let message = "New password and confirm new password doesn't match.";
		console.log("ERROR: " + message);
		showStatus(message);
		return;
	}
	
	let data = { "oldPassword" : oldPassword,
				"newPassword" : newPassword };			
	
	let xhr = new XMLHttpRequest();	
	xhr.open("PUT", ACCOUNT_API_PATH);	
	xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
	xhr.send(JSON.stringify(data));
	
	xhr.onreadystatechange = function() {
		if (this.readyState == XMLHttpRequest.DONE) {
			if (this.status == 200) {
				// SUCCESS				
				console.log("OK: " + this.responseText);				
			} else {
				// ERROR
				console.log("ERROR: " + this.responseText);
				showStatus(this.responseText);
			}
			showAccount();
		}		
	}
}