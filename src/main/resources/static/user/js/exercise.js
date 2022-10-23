function showExercises() {	
	let url = "/user/exercises";	
    let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
    xhr.send();
    
    xhr.onreadystatechange = function() {
        if (this.readyState == XMLHttpRequest.DONE) {
			if (this.status == 200) {
				// SUCCESS
				let data = JSON.parse(this.responseText);				
				let tbody = document.getElementById("exercise-table").getElementsByTagName("tbody")[0];				

				while (tbody.firstChild) {
					tbody.removeChild(tbody.firstChild);
				}
				
				let add = "";				
				
				for (let i = 0; i < data.length; i++) {
					let exerciseId = data[i].id;
					let exerciseNameId = "exercise-name-" + exerciseId;
					let actionsId = "exercise-actions-" + exerciseId;			
					add += 	"<tr><th>" + (i+1) + "</th>"	+ 
							"<td id='" + exerciseNameId + "'>" + data[i].name + "</td>" + 
							"<td id='" + actionsId + "'>" +
							"<input type='button' value='Edit' onclick=editExercise('" + exerciseId + "')>" +
							"<input type='button' value='Delete' onclick=deleteExercise('" + exerciseId + "')></td></tr>";					
				}
				
				tbody.innerHTML += add;				
			} else {
				// ERROR				
				console.log("ERROR: " + this.responseText);
			}
		}        
    };
}

function deleteExercise(exerciseId) {	
	let url = "/user/exercises/" + exerciseId;	
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
			showExercises();
		}		
	}	
}

function onAddExercise() {
	let name = document.forms["new-exercise-form"]["name"].value;	
	name = name.trim();
	
	let data = { "name" : name };
		
	let url = "/user/exercises";	
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
			showExercises();
		}		
	}
			
	document.forms["new-exercise-form"]["name"].value = "";	
}

function editExercise(exerciseId) {
	let exerciseNameElement = document.getElementById("exercise-name-" + exerciseId);
	let actionsElement = document.getElementById("exercise-actions-" + exerciseId);
	// read exercise name
	let exerciseName = exerciseNameElement.textContent;	
	// add input field
	exerciseNameElement.innerHTML = "<input type='text' value='" + exerciseName + "' autofocus required>";
	// remove edit button
	actionsElement.removeChild(actionsElement.firstChild);
	// add save button
	actionsElement.innerHTML = 	"<input type='button' value='Save' onclick=saveExercise('" + exerciseId + "')>" 
								+ actionsElement.innerHTML;
}

function saveExercise(exerciseId) {
	// read the exercise name
	let exerciseNameElement = document.getElementById("exercise-name-" + exerciseId);
	let exerciseName = exerciseNameElement.firstChild.value;
	exerciseName = exerciseName.trim();	
	// remove input field and add text
	exerciseNameElement.innerHTML = exerciseName;	
	// remove save button
	let actionsElement = document.getElementById("exercise-actions-" + exerciseId);
	actionsElement.removeChild(actionsElement.firstChild);
	// add edit button
	actionsElement.innerHTML = 	"<input type='button' value='Edit' onclick=editExercise('" + exerciseId + "')>" 
								+ actionsElement.innerHTML;
	
	let data = { "name" : exerciseName };
	
	let url = "/user/exercises/" + exerciseId;	
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
			showExercises();
		}		
	}
}