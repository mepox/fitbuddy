const EXERCISES_API_PATH = "/user/exercises";
 
function showExercises() {
    let xhr = new XMLHttpRequest();
	xhr.open("GET", EXERCISES_API_PATH);
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
							"<a href='#' title='Edit'><i class='bi bi-pencil-fill icon-grey me-3 fs-5' onclick=editExercise('" + exerciseId + "')></i></a>" +
							"<a href='#' title='Delete'><i class='bi bi-trash-fill icon-red fs-5' onclick=deleteExercise('" + exerciseId + "')></i></a>" +
							"</td></tr>";			
				}
				
				tbody.innerHTML = add;								
			} else {
				// ERROR				
				console.log("ERROR: " + this.responseText);
			}
		}        
    };
}

function deleteExercise(exerciseId) {
	hideStatus();
    let xhr = new XMLHttpRequest();
	xhr.open("DELETE", EXERCISES_API_PATH + "/" + exerciseId);
	xhr.send();  
    
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
			showExercises();
		}		
	}	
}

function onAddExercise() {
	hideStatus();
	let name = document.forms["new-exercise-form"]["name"].value;	
	name = name.trim();
	
	let data = { "name" : name };
		
	let xhr = new XMLHttpRequest();		
	xhr.open("POST", EXERCISES_API_PATH);
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
	actionsElement.innerHTML = 	"<a href='#' title='Save'><i class='bi bi-check-square-fill icon-green me-3 fs-5' onclick=saveExercise('" + exerciseId + "')></i></a>" + 
								actionsElement.innerHTML;
}

function saveExercise(exerciseId) {
	hideStatus();
	// read the exercise name
	let exerciseNameElement = document.getElementById("exercise-name-" + exerciseId);
	let exerciseName = exerciseNameElement.firstChild.value;
	exerciseName = exerciseName.trim();		
	
	let data = { "name" : exerciseName };
	
	let xhr = new XMLHttpRequest();	
	xhr.open("PUT", EXERCISES_API_PATH + "/" + exerciseId);	
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
			showExercises();
		}		
	}
}