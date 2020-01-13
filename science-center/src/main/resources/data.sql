INSERT INTO authority(id, role) VALUES (1, 'ROLE_USER');
INSERT INTO authority(id, role) VALUES (2, 'ROLE_ADMINISTRATOR');
INSERT INTO authority(id, role) VALUES (3, 'ROLE_REVIEWER');

INSERT INTO registered_user(id, first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES (0, 'Admin', 'Admin', 'Novi Sad', 'Serbia', 'Mr', 'admin@gmail.com', 'admin', '$2a$10$KDRBT6iDgLyeLtvJk4/zAO92EfmUUAdnLDiAR9jAAQW6HFlyf6lp.', 2, true);


INSERT INTO scientific_area(id, name) VALUES (1, 'Quantum Physics');
INSERT INTO scientific_area(id, name) VALUES (2, 'Chemistry');
INSERT INTO scientific_area(id, name) VALUES (3, 'Astronomy');
INSERT INTO scientific_area(id, name) VALUES (4, 'Mathematics');
INSERT INTO scientific_area(id, name) VALUES (5, 'Molecular Biology');
INSERT INTO scientific_area(id, name) VALUES (6, 'Zoology');