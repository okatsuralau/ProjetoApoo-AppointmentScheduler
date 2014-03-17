/*
SQLyog Ultimate - MySQL GUI v8.2
MySQL - 5.6.11 : Database - tads_pds_scheduler
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*Table structure for table `availability` */

DROP TABLE IF EXISTS `availability`;

CREATE TABLE `availability` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` INT(11) NOT NULL COMMENT 'Medico',
  `expertise_id` INT(11) NOT NULL COMMENT 'Especialidade',
  `office_id` INT(11) NOT NULL COMMENT 'Sala',
  `availability_date` DATE NOT NULL COMMENT 'Data',
  `availability_time` TIME NOT NULL COMMENT 'Hora',
  `available_amount` TINYINT(4) NOT NULL COMMENT 'Disponivel',
  `scheduled_amount` TINYINT(4) NOT NULL DEFAULT '0' COMMENT 'Agendado',
  PRIMARY KEY (`id`),
  KEY `FK_availability_doctor` (`doctor_id`),
  KEY `FK_availability_office` (`office_id`),
  KEY `FK_availability_expertise` (`expertise_id`),
  CONSTRAINT `FK_availability_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`),
  CONSTRAINT `FK_availability_expertise` FOREIGN KEY (`expertise_id`) REFERENCES `expertise` (`id`),
  CONSTRAINT `FK_availability_office` FOREIGN KEY (`office_id`) REFERENCES `office` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='Disponibilidade dos dias e horarios de atendimento dos medicos';

/*Table structure for table `datebook` */

DROP TABLE IF EXISTS `datebook`;

CREATE TABLE `datebook` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `availability_id` INT(11) NOT NULL COMMENT 'Disponibilidade',
  `patient_id` INT(11) NOT NULL COMMENT 'Paciente',
  `exam_id` INT(11) NOT NULL COMMENT 'Exame',
  `health_plan_id` INT(11) DEFAULT NULL COMMENT 'Plano de saude',
  `note` TEXT COMMENT 'Observação (opcional)',
  `checkin` TINYINT(1) DEFAULT '0' COMMENT 'Se chegou',
  `datacheckin` DATETIME DEFAULT NULL COMMENT 'Hora do checkin',
  `displayed` TINYINT(1) DEFAULT '0' COMMENT 'Se foi mostrado/carregado',
  PRIMARY KEY (`id`),
  KEY `fk_datebook_patients1_idx` (`patient_id`),
  KEY `fk_datebook_health_plan1_idx` (`health_plan_id`),
  KEY `fk_datebook_exam1_idx` (`exam_id`),
  KEY `FK_datebook_availability` (`availability_id`),
  CONSTRAINT `FK_datebook_availability` FOREIGN KEY (`availability_id`) REFERENCES `availability` (`id`),
  CONSTRAINT `fk_datebook_exam1` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`),
  CONSTRAINT `fk_datebook_patients1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='Agenda de consultas';

/*Table structure for table `schedule` */

DROP TABLE IF EXISTS `schedule`;

CREATE TABLE `schedule` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` INT(11) NOT NULL COMMENT 'Medico',
  `expertise_id` INT(11) NOT NULL COMMENT 'Especialidade',
  `office_id` INT(11) NOT NULL COMMENT 'Sala',
  `sunday` TINYINT(1) NOT NULL DEFAULT '0' COMMENT 'Domingo',
  `monday` TINYINT(1) NOT NULL DEFAULT '0' COMMENT 'Segunda',
  `tuesday` TINYINT(1) NOT NULL DEFAULT '0' COMMENT 'Terca',
  `wednesday` TINYINT(1) NOT NULL DEFAULT '0' COMMENT 'Quarta',
  `thursday` TINYINT(1) NOT NULL DEFAULT '0' COMMENT 'Quinta',
  `friday` TINYINT(1) NOT NULL DEFAULT '0' COMMENT 'Sexta',
  `saturday` TINYINT(1) NOT NULL DEFAULT '0' COMMENT 'Sabado',
  `schedule_time` TIME NOT NULL COMMENT 'Horario',
  `amount` TINYINT(4) NOT NULL COMMENT 'Qtd de pacientes',
  PRIMARY KEY (`id`),
  KEY `FK_schedule_doctor` (`doctor_id`),
  KEY `FK_schedule_office` (`office_id`),
  KEY `FK_schedule_expertise` (`expertise_id`),
  CONSTRAINT `FK_schedule_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`),
  CONSTRAINT `FK_schedule_expertise` FOREIGN KEY (`expertise_id`) REFERENCES `expertise` (`id`),
  CONSTRAINT `FK_schedule_office` FOREIGN KEY (`office_id`) REFERENCES `office` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='Horario e dias de atendimento dos medicos';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
