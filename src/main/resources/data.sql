INSERT INTO role
(name)
VALUES
('ADMIN'),
('USER');

INSERT INTO app_user
(name, password, role_id)
VALUES
('admin', 'admin', 01),
('user', 'user', 02);

INSERT INTO exercise
(name, app_user_id)
VALUES
('Flat Barbell Bench Press', 2),
('Incline Barbell Bench Press', 2),
('Flat Dumbbell Bench Press', 2),
('Incline Dumbbell Bench Press', 2);

INSERT INTO history
(app_user_id, exercise_id, weight, reps, created_on)
VALUES
(2, 1, 100, 10, '2022-10-15'),
(2, 1, 100, 10, '2022-10-15'),
(2, 1, 100, 10, '2022-10-15'),
(2, 2, 60, 10, '2022-10-15'),
(2, 2, 60, 10, '2022-10-15'),
(2, 2, 60, 10, '2022-10-15');