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
					add += "<tr><th>" + (i+1) + "</th>"	+ "<td>" + data[i].name + "</td>" + "<td>" + 
						"<input type='button' value='Delete' onclick=deleteExercise(" + data[i].id + ")></td></tr>";					
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