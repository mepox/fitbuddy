CREATE TABLE role (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(16) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE app_user (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(16) NOT NULL,
	password VARCHAR(72) NOT NULL,
	role_id INT NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE exercise (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(32) NOT NULL,
	app_user_id INT NOT NULL,	
	PRIMARY KEY (id),
	FOREIGN KEY (app_user_id) REFERENCES app_user(id)
);

CREATE TABLE history (
	id INT NOT NULL AUTO_INCREMENT,
	app_user_id INT NOT NULL,
	exercise_id INT NOT NULL,
	weight INT,
	reps INT NOT NULL,
	created_on VARCHAR(16) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (app_user_id) REFERENCES app_user(id),
	FOREIGN KEY (exercise_id) REFERENCES exercise(id)
);

CREATE TABLE default_exercise (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(32) NOT NULL,
	PRIMARY KEY (id)
);