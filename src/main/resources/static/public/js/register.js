document.addEventListener("DOMContentLoaded", () => {
	onLoaded();
});

function onLoaded() {
	clearRegisterForm();
	console.log("Page loaded.");
}

function onRegister() {
	let name = document.forms["registerForm"]["name"].value;
	let password = document.forms["registerForm"]["password"].value;
	let passwordConfirm = document.forms["registerForm"]["passwordConfirm"].value;
	
	name = name.trim();
	password = password.trim();
	passwordConfirm = passwordConfirm.trim();
	
	if (password !== passwordConfirm) {
		let message = "Password and confirm password doesn't match.";
		console.log("ERROR: " + message);
		showStatus(message);
		return;
	}
	
	let url = "/register";
	let data = { 	name : name,
					password : password };
					
	let xhr = new XMLHttpRequest();
	xhr.open("POST", url);
	xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");			
	xhr.send(JSON.stringify(data));
	
	xhr.onreadystatechange = function() {
		if (this.readyState == XMLHttpRequest.DONE) {
			if (this.status == 200) {
				// SUCCESS			
				showStatus("Registered successfully.")
			} else {
				// ERROR
				showStatus(this.responseText);	
			}			
			clearRegisterForm();		
		}		
	}
}

function clearRegisterForm() {
	clearFormValue("registerForm", "name");
	clearFormValue("registerForm", "password");
	clearFormValue("registerForm", "passwordConfirm");	
}