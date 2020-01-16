INSERT INTO authority(id, role) VALUES (1, 'ROLE_USER');
INSERT INTO authority(id, role) VALUES (2, 'ROLE_ADMINISTRATOR');
INSERT INTO authority(id, role) VALUES (3, 'ROLE_REVIEWER');
INSERT INTO authority(id, role) VALUES (4, 'ROLE_EDITOR');


INSERT INTO registered_user(id, first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES (1000, 'Admin', 'Admin', 'Novi Sad', 'Serbia', 'Mr', 'admin@gmail.com', 'admin', '$2a$10$KDRBT6iDgLyeLtvJk4/zAO92EfmUUAdnLDiAR9jAAQW6HFlyf6lp.', 2, true);
INSERT INTO registered_user(id, first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES (1001, 'Editor1', 'Editor1', 'Novi Sad', 'Serbia', 'Mr', 'editor1@gmail.com', 'editor1', '$2a$10$cc5sDtrf6guDE/BKV76DAOu645s7ixRAGXlAZ9JcLZFl1UO5mDZOS', 4, true);
INSERT INTO registered_user(id, first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES (1002, 'Editor2', 'Editor2', 'Novi Sad', 'Serbia', 'Mr', 'editor2@gmail.com', 'editor2', '$2a$10$cc5sDtrf6guDE/BKV76DAOu645s7ixRAGXlAZ9JcLZFl1UO5mDZOS', 4, true);
INSERT INTO registered_user(id, first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES (1003, 'Editor3', 'Editor3', 'Novi Sad', 'Serbia', 'Mr', 'editor3@gmail.com', 'editor3', '$2a$10$cc5sDtrf6guDE/BKV76DAOu645s7ixRAGXlAZ9JcLZFl1UO5mDZOS', 4, true);
INSERT INTO registered_user(id, first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES (1004, 'Editor4', 'Editor4', 'Novi Sad', 'Serbia', 'Mr', 'editor4@gmail.com', 'editor4', '$2a$10$cc5sDtrf6guDE/BKV76DAOu645s7ixRAGXlAZ9JcLZFl1UO5mDZOS', 4, true);
INSERT INTO registered_user(id, first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES (1005, 'Reviewer1', 'Reviewer1', 'Novi Sad', 'Serbia', 'Mr', 'reviewer1@gmail.com', 'reviewer1', '$2a$10$KDDI7qWCqaeP0hmGQDQhgubwDyFILTsoNf1CJYRxK2WCKJftetcWy', 3, true);				    
INSERT INTO registered_user(id, first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES (1006, 'Reviewer2', 'Reviewer2', 'Novi Sad', 'Serbia', 'Mr', 'reviewer2@gmail.com', 'reviewer2', '$2a$10$KDDI7qWCqaeP0hmGQDQhgubwDyFILTsoNf1CJYRxK2WCKJftetcWy', 3, true);				    
INSERT INTO registered_user(id, first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES (1007, 'Reviewer3', 'Reviewer3', 'Novi Sad', 'Serbia', 'Mr', 'reviewer3@gmail.com', 'reviewer3', '$2a$10$KDDI7qWCqaeP0hmGQDQhgubwDyFILTsoNf1CJYRxK2WCKJftetcWy', 3, true);				    
INSERT INTO registered_user(id, first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES (1008, 'Reviewer4', 'Reviewer4', 'Novi Sad', 'Serbia', 'Mr', 'reviewer4@gmail.com', 'reviewer4', '$2a$10$KDDI7qWCqaeP0hmGQDQhgubwDyFILTsoNf1CJYRxK2WCKJftetcWy', 3, true);				    

INSERT INTO scientific_area(id, name) VALUES (1, 'Quantum Physics');
INSERT INTO scientific_area(id, name) VALUES (2, 'Chemistry');
INSERT INTO scientific_area(id, name) VALUES (3, 'Astronomy');
INSERT INTO scientific_area(id, name) VALUES (4, 'Mathematics');
INSERT INTO scientific_area(id, name) VALUES (5, 'Molecular Biology');
INSERT INTO scientific_area(id, name) VALUES (6, 'Zoology');