DROP TRIGGER IF EXISTS tgr_GerarDatasDisponiveis;

DELIMITER $$

CREATE TRIGGER tgr_GerarDatasDisponiveis AFTER INSERT ON tads_pds_scheduler.schedule
FOR EACH ROW 
BEGIN

	SELECT	NOW() 	INTO @strData;
	SELECT 	30 	INTO @QtdDias;		-- Gerar para os proximos 30 dias
	SELECT 	0	INTO @iDia;		-- incrementar o dia
	
	WHILE 	(@iDia < @QtdDias) DO
	BEGIN

		SELECT 	(SELECT DAYOFWEEK(@strData)) INTO @DiaSemana;

		IF 	(NEW.sunday = 1 AND @DiaSemana = 1) OR
			(NEW.monday = 1 AND @DiaSemana = 2) OR
			(NEW.tuesday = 1 AND @DiaSemana = 3) OR
			(NEW.wednesday = 1 AND @DiaSemana = 4) OR
			(NEW.thursday = 1 AND @DiaSemana = 5) OR
			(NEW.friday = 1 AND @DiaSemana = 6) OR
			(NEW.saturday = 1  AND @DiaSemana = 7) THEN

			INSERT INTO tads_pds_scheduler.availability 
				(doctor_id, expertise_id, office_id, 
				availability_date, availability_time, 
				available_amount, scheduled_amount)
			VALUES	(NEW.doctor_id, NEW.expertise_id, NEW.office_id,
				@strData, NEW.schedule_time, NEW.amount, 0);
			
		END IF;
		
		SELECT 	(SELECT DATE_ADD(@strData, INTERVAL 1 DAY)) 
		INTO 	@strData;
		
		SELECT 	@iDia + 1 
		INTO 	@iDia;
	
	END;

	END WHILE;

END$$
DELIMITER ;