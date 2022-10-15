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