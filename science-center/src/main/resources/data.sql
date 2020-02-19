INSERT INTO authority(id, role) VALUES (1, 'ROLE_ADMINISTRATOR');
INSERT INTO authority(id, role) VALUES (2, 'ROLE_REVIEWER');
INSERT INTO authority(id, role) VALUES (3, 'ROLE_EDITOR');
INSERT INTO authority(id, role) VALUES (4, 'ROLE_AUTHOR');
INSERT INTO authority(id, role) VALUES (5, 'ROLE_USER');

INSERT INTO scientific_area(id, name) VALUES (1, 'Quantum Physics');
INSERT INTO scientific_area(id, name) VALUES (2, 'Chemistry');
INSERT INTO scientific_area(id, name) VALUES (3, 'Astronomy');
INSERT INTO scientific_area(id, name) VALUES (4, 'Mathematics');
INSERT INTO scientific_area(id, name) VALUES (5, 'Molecular Biology');
INSERT INTO scientific_area(id, name) VALUES (6, 'Zoology');

INSERT INTO registered_user(first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES ('Admin', 'Admin', 'Novi Sad', 'Serbia', 'Mr', 'admin@gmail.com', 'admin', '$2a$10$KDRBT6iDgLyeLtvJk4/zAO92EfmUUAdnLDiAR9jAAQW6HFlyf6lp.', 1, true);

INSERT INTO registered_user(first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES ('Editor1', 'Editor1', 'Novi Sad', 'Serbia', 'Mr', 'upp.majak@gmail.com', 'editor1', '$2a$10$cc5sDtrf6guDE/BKV76DAOu645s7ixRAGXlAZ9JcLZFl1UO5mDZOS', 3, true);
INSERT INTO registered_user(first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES ('Editor2', 'Editor2', 'Novi Sad', 'Serbia', 'Mr', 'upp.majak@gmail.com', 'editor2', '$2a$10$cc5sDtrf6guDE/BKV76DAOu645s7ixRAGXlAZ9JcLZFl1UO5mDZOS', 3, true);
INSERT INTO registered_user(first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES ('Editor3', 'Editor3', 'Novi Sad', 'Serbia', 'Mr', 'upp.majak@gmail.com', 'editor3', '$2a$10$cc5sDtrf6guDE/BKV76DAOu645s7ixRAGXlAZ9JcLZFl1UO5mDZOS', 3, true);
INSERT INTO registered_user(first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES ('Editor4', 'Editor4', 'Novi Sad', 'Serbia', 'Mr', 'upp.majak@gmail.com', 'editor4', '$2a$10$cc5sDtrf6guDE/BKV76DAOu645s7ixRAGXlAZ9JcLZFl1UO5mDZOS', 3, true);

INSERT INTO registered_user(first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES ('Reviewer1', 'Reviewer1', 'Novi Sad', 'Serbia', 'Mr', 'upp.majak@gmail.com', 'reviewer1', '$2a$10$KDDI7qWCqaeP0hmGQDQhgubwDyFILTsoNf1CJYRxK2WCKJftetcWy', 2, true);				    
INSERT INTO registered_user(first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES ('Reviewer2', 'Reviewer2', 'Novi Sad', 'Serbia', 'Mr', 'upp.majak@gmail.com', 'reviewer2', '$2a$10$KDDI7qWCqaeP0hmGQDQhgubwDyFILTsoNf1CJYRxK2WCKJftetcWy', 2, true);				    
INSERT INTO registered_user(first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES ('Reviewer3', 'Reviewer3', 'Novi Sad', 'Serbia', 'Mr', 'upp.majak@gmail.com', 'reviewer3', '$2a$10$KDDI7qWCqaeP0hmGQDQhgubwDyFILTsoNf1CJYRxK2WCKJftetcWy', 2, true);				    
INSERT INTO registered_user(first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES ('Reviewer4', 'Reviewer4', 'Novi Sad', 'Serbia', 'Mr', 'upp.majak@gmail.com', 'reviewer4', '$2a$10$KDDI7qWCqaeP0hmGQDQhgubwDyFILTsoNf1CJYRxK2WCKJftetcWy', 2, true);				    

INSERT INTO registered_user(first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
			    VALUES ('Author1', 'Author1', 'Novi Sad', 'Serbia', 'Mr', 'upp.majak@gmail.com', 'author1', '$2a$10$d2AeAoty0SVy8Egoxpf52uo1lc/FQGJP6qF.HsAU3Mw3BXCMyC9E.', 4, true);				    

INSERT INTO registered_user(first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES ('User1', 'User1', 'Novi Sad', 'Serbia', 'Mr', 'upp.majak@gmail.com', 'user1', '$2a$10$DS1RTHPqCQ8RfU/ptzMMt.h2jvkQkmQP7J0mD1lZmSeFE.zZEcV3.', 5, true);
INSERT INTO registered_user(first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES ('User2', 'User2', 'Novi Sad', 'Serbia', 'Mr', 'upp.majak@gmail.com', 'user2', '$2a$10$DS1RTHPqCQ8RfU/ptzMMt.h2jvkQkmQP7J0mD1lZmSeFE.zZEcV3.', 5, true);
INSERT INTO registered_user(first_name, last_name, city, country, title, email, username, password, authority_id, confirmed)
				    VALUES ('User3', 'User3', 'Novi Sad', 'Serbia', 'Mr', 'upp.majak@gmail.com', 'user3', '$2a$10$DS1RTHPqCQ8RfU/ptzMMt.h2jvkQkmQP7J0mD1lZmSeFE.zZEcV3.', 5, true);
			    
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (2, 1);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (2, 4);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (3, 1);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (3, 3);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (3, 4);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (4, 2);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (4, 5);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (5, 5);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (5, 6);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (5, 1);

INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (6, 1);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (6, 3);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (6, 4);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (7, 5);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (7, 6);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (7, 2);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (8, 1);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (8, 2);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (8, 3);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (9, 5);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (9, 3);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (9, 4);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (9, 2);

INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (10, 2);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (10, 3);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (10, 5);

INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (11, 2);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (11, 4);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (11, 5);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (12, 6);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (12, 3);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (12, 2);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (13, 4);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (13, 1);
INSERT INTO registered_user_scientific_areas(registered_user_id, scientific_areas_id) VALUES (13, 5);

--Magazine One
INSERT INTO magazine(issn, active, name, payment_type, editor_in_chief_id, monthly_membership_price, email, registered_on_payment_hub, paper_price, issue_price) VALUES (14859382, true, 'Magazine One', 'READERS', 2, 11, 'magazine-one@upp.com', true, 6, 3);

INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (1, 1);
INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (1, 3);
INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (1, 4);

INSERT INTO magazine_editors(magazine_id, editors_id) VALUES (1, 2);
INSERT INTO magazine_editors(magazine_id, editors_id) VALUES (1, 3);

INSERT INTO magazine_reviewers(magazine_id, reviewers_id) VALUES (1, 6);
INSERT INTO magazine_reviewers(magazine_id, reviewers_id) VALUES (1, 7);
INSERT INTO magazine_reviewers(magazine_id, reviewers_id) VALUES (1, 8);

INSERT INTO issue(number, price, published, publishing_date, magazine_id) VALUES (4, 6, false, '2020-03-07 13:56:58', 1);
INSERT INTO issue(number, price, published, publishing_date, magazine_id) VALUES (1, 6, true, '2019-02-07 13:56:58', 1);
INSERT INTO issue(number, price, published, publishing_date, magazine_id) VALUES (2, 6, true, '2019-03-07 13:56:58', 1);
INSERT INTO issue(number, price, published, publishing_date, magazine_id) VALUES (3, 6, true, '2019-04-07 13:56:58', 1);

INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('9845839', true, 'something; something', 'bla bla', 'path', 3, 'Paper Title 1', 10, 2);
INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('4739204', true, 'something; something', 'bla bla', 'path', 3, 'Paper Title 2', 10, 2);
INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('2749302', true, 'something; something', 'bla bla', 'path', 3, 'Paper Title 1', 10, 3);
INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('4730385', true, 'something; something', 'bla bla', 'path', 3, 'Paper Title 2', 10, 3);
INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('5840340', true, 'something; something', 'bla bla', 'path', 3, 'Paper Title 1', 10, 4);
INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('2584932', true, 'something; something', 'bla bla', 'path', 3, 'Paper Title 2', 10, 4);
	

--Magazine Two
INSERT INTO magazine(issn, active, name, payment_type, editor_in_chief_id, monthly_membership_price, email, registered_on_payment_hub, paper_price, issue_price) VALUES (38475938, true, 'Magazine Two', 'READERS', 3, 13, 'magazine-two@upp.com', true, 5, 2);

INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (2, 2);
INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (2, 5);
INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (2, 6);

INSERT INTO magazine_editors(magazine_id, editors_id) VALUES (2, 2);
INSERT INTO magazine_editors(magazine_id, editors_id) VALUES (2, 3);

INSERT INTO magazine_reviewers(magazine_id, reviewers_id) VALUES (2, 9);
INSERT INTO magazine_reviewers(magazine_id, reviewers_id) VALUES (2, 7);
INSERT INTO magazine_reviewers(magazine_id, reviewers_id) VALUES (2, 8);
INSERT INTO magazine_reviewers(magazine_id, reviewers_id) VALUES (2, 6);

INSERT INTO issue(number, price, published, publishing_date, magazine_id) VALUES (4, 5, false, '2020-03-07 13:56:58', 2);
INSERT INTO issue(number, price, published, publishing_date, magazine_id) VALUES (1, 5, true, '2019-02-07 13:56:58', 2);
INSERT INTO issue(number, price, published, publishing_date, magazine_id) VALUES (2, 5, true, '2019-03-07 13:56:58', 2);
INSERT INTO issue(number, price, published, publishing_date, magazine_id) VALUES (3, 5, true, '2019-04-07 13:56:58', 2);

INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('9845839', true, 'something; something', 'bla bla', 'path', 2, 'Paper Title 1', 10, 6);
INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('4739204', true, 'something; something', 'bla bla', 'path', 2, 'Paper Title 2', 10, 6);
INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('2749302', true, 'something; something', 'bla bla', 'path', 2, 'Paper Title 1', 10, 7);
INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('4730385', true, 'something; something', 'bla bla', 'path', 2, 'Paper Title 2', 10, 7);
INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('5840340', true, 'something; something', 'bla bla', 'path', 2, 'Paper Title 1', 10, 8);
INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('2584932', true, 'something; something', 'bla bla', 'path', 2, 'Paper Title 2', 10, 8);
	
--Magazine Three
INSERT INTO magazine(issn, active, name, payment_type, editor_in_chief_id, monthly_membership_price, email, registered_on_payment_hub, paper_price, issue_price) VALUES (35830293, true, 'Magazine Three', 'READERS', 4, 10, 'magazine-three@upp.com', true, 4, 2);

INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (3, 2);
INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (3, 3);
INSERT INTO magazine_scientific_areas(magazine_id, scientific_areas_id) VALUES (3, 5);

INSERT INTO magazine_editors(magazine_id, editors_id) VALUES (3, 3);
INSERT INTO magazine_editors(magazine_id, editors_id) VALUES (3, 2);

INSERT INTO magazine_reviewers(magazine_id, reviewers_id) VALUES (3, 9);
INSERT INTO magazine_reviewers(magazine_id, reviewers_id) VALUES (3, 7);
INSERT INTO magazine_reviewers(magazine_id, reviewers_id) VALUES (3, 8);
INSERT INTO magazine_reviewers(magazine_id, reviewers_id) VALUES (3, 6);

INSERT INTO issue(number, price, published, publishing_date, magazine_id) VALUES (4, 4, false, '2020-03-07 13:56:58', 3);
INSERT INTO issue(number, price, published, publishing_date, magazine_id) VALUES (1, 4, true, '2019-02-07 13:56:58', 3);
INSERT INTO issue(number, price, published, publishing_date, magazine_id) VALUES (2, 4, true, '2019-03-07 13:56:58', 3);
INSERT INTO issue(number, price, published, publishing_date, magazine_id) VALUES (3, 4, true, '2019-04-07 13:56:58', 3);

INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('9845839', true, 'something; something', 'bla bla', 'path', 2, 'Paper Title 1', 10, 10);
INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('4739204', true, 'something; something', 'bla bla', 'path', 2, 'Paper Title 2', 10, 10);
INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('2749302', true, 'something; something', 'bla bla', 'path', 2, 'Paper Title 1', 10, 11);
INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('4730385', true, 'something; something', 'bla bla', 'path', 2, 'Paper Title 2', 10, 11);
INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('5840340', true, 'something; something', 'bla bla', 'path', 2, 'Paper Title 1', 10, 12);
INSERT INTO paper(doi, active, keywords, paper_abstract, pdf, price, title, author_id, issue_id)
	VALUES ('2584932', true, 'something; something', 'bla bla', 'path', 2, 'Paper Title 2', 10, 12);
	
--INSERT INTO membership(valid_until, magazine_id, user_id) VALUES ('2020-05-07 13:56:58', 1002, 1009);

