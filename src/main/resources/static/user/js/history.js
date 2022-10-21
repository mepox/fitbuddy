/* CALENDAR functions */

function resetCalendar() {
	// Set calendar for the current date    
    var today = new Date();
	var dd = String(today.getDate()).padStart(2, '0');
	var mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
	var yyyy = today.getFullYear();
	today = yyyy + "-" + mm + "-" + dd;
	
    document.getElementById("calendar").value = today;
}

function onCalendarChange() {
	showHistory();
}

function stepUpCalendar() {
	document.getElementById("calendar").stepUp();
	onCalendarChange();
}

function stepDownCalendar() {
	document.getElementById("calendar").stepDown();
	onCalendarChange();
}

/* WORKOUT LOG functions */

function showHistory() {
	refreshExerciseOptions();
	
	let date = document.getElementById("calendar").value;
	
	let url = "/user/history/" + date;	
	let xhr = new XMLHttpRequest();	
	xhr.open("GET", url);
	xhr.send();
	
	xhr.onreadystatechange = function() {
        if (this.readyState == XMLHttpRequest.DONE) {
			if (this.status == 200) {
				// SUCCESS
				let data = JSON.parse(this.responseText);
				
				let tbody = document.getElementById("history-table").getElementsByTagName("tbody")[0];
								
				while (tbody.firstChild) {
					tbody.removeChild(tbody.firstChild);
				}
				
				let add = "";
				
				for (let i = 0; i < data.length; i++) {
					add += "<tr><th>" + (i+1) + "</th>" +
						"<td>" + data[i].exerciseName + "</td>" + 
						"<td>" + data[i].weight + "</td>" +
						"<td>" + data[i].reps + "</td>" +
						"<td>" + data[i].createdOn + "</td>" +
						"<td><input type='button' value='Delete' onclick=deleteHistory(" + data[i].id + ")></td></tr>";											 
				}
				
				tbody.innerHTML += add;
				
			} else {
				// ERROR				
				console.log("ERROR: " + this.responseText);
			}
		}        
    };
}

function refreshExerciseOptions() {
	let url = "/user/exercises";	
    let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
    xhr.send();
    
    xhr.onreadystatechange = function() {
        if (this.readyState == XMLHttpRequest.DONE) {
			if (this.status == 200) {
				// SUCCESS
				let data = JSON.parse(this.responseText);
				
				let exerciseSelect = document.getElementById("exercise-select");
				
				while(exerciseSelect.firstChild) {
					exerciseSelect.removeChild(exerciseSelect.firstChild);
				}	
				
				let add = "";		
				
				for (let i = 0; i < data.length; i++) {
					add += "<option value='" + data[i].id + "'>" + data[i].name + "</option>"; 
				}
				
				exerciseSelect.innerHTML += add;			
			} else {
				// ERROR				
				console.log("ERROR: " + this.responseText);
			}
		}        
    };
}


function deleteHistory(historyId) {
	let url = "/user/history/" + historyId;	
    let xhr = new XMLHttpRequest();
	xhr.open("DELETE", url);
	xhr.send();    
    
    xhr.onreadystatechange = function() {
		if (this.readyState == XMLHttpRequest.DONE) {
			if (this.status == 200) {
				// SUCCESS				
				console.log("OK: " + this.responseText);				
			} else {
				// ERROR
				console.log("ERROR: " + this.responseText);
			}
			//showStatus(this.responseText);
			showHistory();
		}		
	}	
}

function onAddHistory() {
	let exerciseId = document.getElementById("exercise-select").value;
	let weight = document.forms["new-history-form"]["weight"].value;
	let reps = document.forms["new-history-form"]["reps"].value;	
	let createdOn = document.getElementById("calendar").value;	
	
	exerciseId = exerciseId.trim();
	weight = weight.trim();
	reps = reps.trim();
	createdOn = createdOn.trim();
	
	let data = { 	"exerciseId" : exerciseId,
					"weight" : weight,
					"reps" : reps,
					"createdOn" : createdOn };	
	
	let url = "/user/history";
	let xhr = new XMLHttpRequest();
	xhr.open("POST", url);
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
			//showStatus(this.responseText);
			showHistory();
		}		
	}			
	
	document.forms["new-history-form"]["weight"].value = "";
	document.forms["new-history-form"]["reps"].value = "";	
}

function onTestUpdateHistory() {
	let historyId = 1;
	
	let exerciseId = 2;
	let weight = 222;
	let reps = 22;
	let createdOn = "2022-10-15";
	
	let data = { 	"exerciseId" : exerciseId,
					"weight" : weight,
					"reps" : reps,
					"createdOn" : createdOn };
					
	let url = "/user/history/" + historyId;	
	let xhr = new XMLHttpRequest();	
	xhr.open("PUT", url);	
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
			//showStatus(this.responseText);
			showHistory();
		}		
	}
}