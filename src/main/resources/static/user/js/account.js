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
				
				document.forms["account-form"]["username"].value = data.name;
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
	let name = document.forms["account-form"]["username"].value;
	let newPassword = document.forms["account-form"]["new-password"].value;
	let confirmNewPassword = document.forms["account-form"]["confirm-new-password"].value;
	
	name = name.trim();
	newPassword = newPassword.trim();
	confirmNewPassword = confirmNewPassword.trim();
	
	if (newPassword !== confirmNewPassword) {
		console.log("ERROR: Passwords doesn't match.");
		return;
	}
	
	let data = { "name" : name,
				"password" : newPassword };
	
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
			}
			showAccount();
		}		
	}
}