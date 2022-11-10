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
	hideDiv("History");
	hideDiv("Account");
	showDiv("Exercises");
	showExercises();
}

function onHistory() {
	hideDiv("Exercises");
	hideDiv("Account");
	showDiv("History");
	resetCalendar();
	showHistory();
}

function onAccount() {
	hideDiv("History");
	hideDiv("Exercises");
	showDiv("Account");
	showAccount();
}