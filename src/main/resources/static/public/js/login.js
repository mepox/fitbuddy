document.addEventListener("DOMContentLoaded", () => {
	onLoaded();
});

function onLoaded() {
	clearLoginForm();
	console.log("Page loaded.");	
}

function onLogin() {
	let name = document.forms["loginForm"]["name"].value.trim();
	let password = document.forms["loginForm"]["password"].value;
	
	name = name.trim();
	password = password.trim();
	
	let url = API + "/login/perform_login";	
	
	let data = {	"name" : name,
					"password" : password };
											
	let xhr = new XMLHttpRequest();
	xhr.open("POST", url);
	xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
	xhr.send(JSON.stringify(data));
	
	xhr.onreadystatechange = function() {
		if (this.readyState == XMLHttpRequest.DONE) {
			if (this.status == 200) {
				// SUCCESS				
				showStatus("Logged in successfully.")
				window.open("/", "_top");
			} else {
				// ERROR				
				showStatus(JSON.parse(this.responseText).message);
				clearLoginForm();
			}
		}		
	}
}

function clearLoginForm() {
	clearFormValue("loginForm", "name");
	clearFormValue("loginForm", "password");
}