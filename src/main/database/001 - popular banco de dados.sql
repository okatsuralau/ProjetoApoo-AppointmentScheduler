
/* 
	1 - POPULAR TABELAS AUXILIARES 
*/

INSERT INTO tads_pds_scheduler.skin (id, title) VALUES (1, 'preto');
INSERT INTO tads_pds_scheduler.skin (id, title) VALUES (2, 'branco');
INSERT INTO tads_pds_scheduler.skin (id, title) VALUES (3, 'indio');

INSERT INTO tads_pds_scheduler.educationlevel (id, title) VALUES (1, 'fundamental');
INSERT INTO tads_pds_scheduler.educationlevel (id, title) VALUES (2, 'medio');
INSERT INTO tads_pds_scheduler.educationlevel (id, title) VALUES (3, 'superior');

INSERT INTO tads_pds_scheduler.civilstatus (id, title) VALUES (1, 'solteiro');
INSERT INTO tads_pds_scheduler.civilstatus (id, title) VALUES (2, 'casado');
INSERT INTO tads_pds_scheduler.civilstatus (id, title) VALUES (3, 'viuvo');

INSERT INTO tads_pds_scheduler.expertise (id, title) VALUES (1, 'cardiologia');
INSERT INTO tads_pds_scheduler.expertise (id, title) VALUES (2, 'pediatria');
INSERT INTO tads_pds_scheduler.expertise (id, title) VALUES (3, 'oftalmologia');

INSERT INTO tads_pds_scheduler.office (id, title) VALUES (1, 'sala 1');
INSERT INTO tads_pds_scheduler.office (id, title) VALUES (2, 'consultario 2');

/* 
	2 - POPULAR CADASTRO DE PESSOA FISICA
*/

INSERT INTO tads_pds_scheduler.individual (id, skin_id, educationlevel_id, civilstatus_id, cpf, first_name, last_name, birthday, email)
VALUES (1, 1, 2, 3, 123456, 'joao', 'silva', '2010-01-01', 'joao@email.com');

INSERT INTO tads_pds_scheduler.individual (id, skin_id, educationlevel_id, civilstatus_id, cpf, first_name, last_name, birthday, email)
VALUES (2, 2, 2, 1, 98712, 'antonio', 'souza', '2011-10-07', 'antnio@email.com');

INSERT INTO tads_pds_scheduler.individual (id, skin_id, educationlevel_id, civilstatus_id, cpf, first_name, last_name, birthday, email)
VALUES (3, 3, 3, 2, 5531, 'maria', 'dantas', '2002-12-18', 'maria@email.com');

INSERT INTO tads_pds_scheduler.individual (id, skin_id, educationlevel_id, civilstatus_id, cpf, first_name, last_name, birthday, email)
VALUES (4, 2, 3, 1, 924142511, 'dr carneiro', 'sobrinho', '1997-02-10', 'carneiro@email.com');

/* 
	3 - POPULAR CADASTRO MEDICO 
	Adicionando Dr Carneiro (ID 4) a atender Oftalmologia e Cardiologia
*/

INSERT INTO tads_pds_scheduler.doctor (id, individual_id, expertise_id) VALUES (1, 4, 3);
INSERT INTO tads_pds_scheduler.doctor (id, individual_id, expertise_id) VALUES (2, 4, 1);