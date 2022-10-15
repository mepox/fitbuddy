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
	let url = "/user/history";
    let xhr = new XMLHttpRequest();
    
    let data = historyId;
    
	xhr.open("DELETE", url);
	
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