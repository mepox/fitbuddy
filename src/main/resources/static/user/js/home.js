document.addEventListener("DOMContentLoaded", () => {
	onLoaded();
});

function onLoaded() {
	console.log("Page loaded.");
}

function onLogout() {	
	window.location += "logout";	
}
