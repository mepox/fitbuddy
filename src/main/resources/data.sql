INSERT INTO default_exercise
(name)
VALUES
('Flat Barbell Bench Press'),
('Incline Barbell Bench Press'),
('Flat Dumbbell Bench Press'),
('Incline Dumbbell Bench Press');

INSERT INTO role
(name)
VALUES
('ADMIN'),
('USER');

INSERT INTO app_user
(name, password, role_id)
VALUES
('admin', '$2a$12$v/mRrRfQ18gpz5ekVluQ8eEEUiN.7gNJ4MtxQCbmWWCavtagHl6cy', 01);