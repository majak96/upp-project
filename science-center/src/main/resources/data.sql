INSERT INTO authority(id, role) VALUES (1, 'ROLE_USER');
INSERT INTO authority(id, role) VALUES (2, 'ROLE_ADMINISTRATOR');
INSERT INTO authority(id, role) VALUES (3, 'ROLE_REVIEWER');
INSERT INTO authority(id, role) VALUES (4, 'ROLE_EDITOR');

INSERT INTO scientific_area(id, name) VALUES (1, 'Quantum Physics');
INSERT INTO scientific_area(id, name) VALUES (2, 'Chemistry');
INSERT INTO scientific_area(id, name) VALUES (3, 'Astronomy');
INSERT INTO scientific_area(id, name) VALUES (4, 'Mathematics');
INSERT INTO scientific_area(id, name) VALUES (5, 'Molecular Biology');
INSERT INTO scientific_area(id, name) VALUES (6, 'Zoology');

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

INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1001, 1);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1001, 4);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1002, 1);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1002, 3);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1002, 4);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1003, 2);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1003, 5);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1004, 5);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1004, 6);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1004, 1);

INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1005, 1);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1005, 3);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1005, 4);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1006, 5);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1006, 6);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1006, 2);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1007, 1);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1007, 2);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1007, 3);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1008, 5);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1008, 3);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1008, 4);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (1008, 2);


INSERT INTO magazine(id, issn, active, name, payment_type, editor_in_chief_id) VALUES (1000, 38475938, true, 'Magazine One', 'READERS', 1001);
INSERT INTO magazine(id, issn, active, name, payment_type, editor_in_chief_id) VALUES (1001, 48594837, true, 'Magazine Two', 'READERS', 1002);
INSERT INTO magazine(id, issn, active, name, payment_type, editor_in_chief_id) VALUES (1002, 67582948, true, 'Magazine Three', 'READERS', 1003);
INSERT INTO magazine(id, issn, active, name, payment_type, editor_in_chief_id) VALUES (1003, 68574930, true, 'Magazine Four', 'AUTHORS', 1004);
INSERT INTO magazine(id, issn, active, name, payment_type, editor_in_chief_id) VALUES (1004, 39485837, true, 'Magazine Five', 'AUTHORS', 1001);

INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (1000, 1);
INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (1000, 3);
INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (1001, 2);
INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (1003, 5);
INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (1003, 2);
INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (1004, 6);
INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (1002, 4);




