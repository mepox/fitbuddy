document.addEventListener("DOMContentLoaded", () => {
	onLoaded();
	onHistory();
});

function onLoaded() {
	console.log("Page loaded.");
}

function onLogout() {	
	window.location += "logout";	
}

function onExercises() {
	hideDiv("History");
	showDiv("Exercises");
	showExercises();
}

function onHistory() {
	hideDiv("Exercises");
	showDiv("History");
	resetCalendar();
	showHistory();
}