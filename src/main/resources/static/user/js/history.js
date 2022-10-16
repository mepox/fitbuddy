function showHistory() {
	let date = "2022-10-15";
	
	let url = "/user/history/" + date;	
	let xhr = new XMLHttpRequest();	
	xhr.open("GET", url);
	xhr.send();
	
	xhr.onreadystatechange = function() {
        if (this.readyState == XMLHttpRequest.DONE) {
			if (this.status == 200) {
				// SUCCESS
				let data = JSON.parse(this.responseText);
				
				let historyList = document.getElementById("HistoryList");
				
				while (historyList.firstChild) {
					historyList.removeChild(historyList.firstChild);
				}
				
				let add = "";				
				
				for (let i = 0; i < data.length; i++) {					
					add += "<li>" + data[i].id + " " + data[i].weight + " " + data[i].reps + " " + 
							data[i].appUserId + " " + data[i].exerciseId + " " + data[i].createdOn + " " +
						"<input type='button' value='Delete' onclick=deleteHistory(" + data[i].id + ")></li>";					 
				}
				
				historyList.innerHTML += add;
				
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
	let exerciseId = document.forms["HistoryForm"]["exerciseId"].value;
	let weight = document.forms["HistoryForm"]["weight"].value;
	let reps = document.forms["HistoryForm"]["reps"].value;
	let createdOn = document.forms["HistoryForm"]["createdOn"].value;
	
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
			
	document.forms["HistoryForm"]["exerciseId"].value = "";
	document.forms["HistoryForm"]["weight"].value = "";
	document.forms["HistoryForm"]["reps"].value = "";
	document.forms["HistoryForm"]["createdOn"].value = "";
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