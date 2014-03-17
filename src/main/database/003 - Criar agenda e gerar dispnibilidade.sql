/* 
	4 - POPULAR CONFIGURACAO DA ESCALA DE ATENDIMENTO DO MEDICO 
*/

TRUNCATE TABLE tads_pds_scheduler.schedule;
DELETE FROM tads_pds_scheduler.availability;

-- 4.1 - ATNEDIMENTO EM OFTLAMOLOGIA NOS DIAS: SEG, TER E QUA A TARDE (14:00); 7 PESSOAS
	
INSERT INTO tads_pds_scheduler.schedule 
	(id, doctor_id, expertise_id, office_id, 
	sunday, monday, tuesday, wednesday, thursday, friday, saturday, 
	schedule_time, amount)
	VALUES (1, 1, 3, 1,
	0, 1, 1, 0, 0, 1, 0, '14:00', 7);

-- 4.2 - APOS INSERIR REGISTRO ACIMA, CHECAR SE AS DISPONIBILIDADES FORAM GERADAS

SELECT * FROM tads_pds_scheduler.availability ORDER BY 1 DESC;

-- 4.3 - ATNEDIMENTO EM CARDIOLOGIA NOS DIAS: QUA E QUI PELA MANHA (09:00); 12 PESSOAS

INSERT INTO tads_pds_scheduler.schedule 
	(id, doctor_id, expertise_id, office_id, 
	sunday, monday, tuesday, wednesday, thursday, friday, saturday, 
	schedule_time, amount)
	VALUES (2, 2, 1, 2,
	0, 0, 0, 1, 1, 0, 0, '09:00', 12);

-- 4.4 - APOS INSERIR REGISTRO ACIMA, CHECAR SE AS DISPONIBILIDADES FORAM GERADAS

SELECT * FROM tads_pds_scheduler.availability ORDER BY 1 DESC;