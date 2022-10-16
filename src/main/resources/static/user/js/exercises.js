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
				
				let exerciseList = document.getElementById("ExerciseList");
				
				while (exerciseList.firstChild) {
					exerciseList.removeChild(exerciseList.firstChild);
				}
				
				let add = "";				
				
				for (let i = 0; i < data.length; i++) {
					//console.log("ID: " + data[i].id, " ", "NAME: " + data[i].name);
					add += "<li>" + data[i].id + " " + data[i].name +
						"<input type='button' value='Delete' onclick=deleteExercise(" + data[i].id + ")></li>";					 
				}
				
				exerciseList.innerHTML += add;
				
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
	let name = document.forms["ExerciseForm"]["name"].value;	
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
			
	document.forms["ExerciseForm"]["name"].value = "";	
}

function onTestUpdateExercise() {
	let name = "testupdate";
	let testId = "1";
	
	let data = { "name" : name };
		
	let url = "/user/exercises/" + testId;	
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