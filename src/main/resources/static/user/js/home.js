document.addEventListener("DOMContentLoaded", () => {
	onLoaded();
	onHistory();
});

function onLoaded() {
	console.log("Page loaded.");
	showUserName();
}

function onLogout() {	
	window.location = "/logout";
}

function onExercises() {
	hideStatus();
	hideDiv("History");
	hideDiv("Account");
	showDiv("Exercises");	
	showExercises();
}

function onHistory() {
	hideStatus();
	hideDiv("Exercises");
	hideDiv("Account");
	showDiv("History");
	resetCalendar();	
	showHistory();
}

function onAccount() {
	hideStatus();
	hideDiv("History");
	hideDiv("Exercises");
	showDiv("Account");
	showAccount();
}