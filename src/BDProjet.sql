-- Adminer 4.3.1 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP TABLE IF EXISTS `fil_de_discussion`;
CREATE TABLE `fil_de_discussion` (
  `fil_id` int(11) NOT NULL,
  `fil_nom` varchar(45) NOT NULL,
  `fil_groupe_id` int(11) NOT NULL,
  `fil_premier_msg_id` int(11) NOT NULL,
  `fil_date_dernier_msg` datetime NOT NULL,
  PRIMARY KEY (`fil_id`,`fil_groupe_id`),
  KEY `fk_fil de discussion_groupe1_idx` (`fil_groupe_id`),
  KEY `fk_fil de discussion_message_idx` (`fil_premier_msg_id`),
  CONSTRAINT `fk_fil de discussion_groupe1` FOREIGN KEY (`fil_groupe_id`) REFERENCES `groupe` (`grp_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `groupe`;
CREATE TABLE `groupe` (
  `grp_id` int(11) NOT NULL,
  `grp_nom` varchar(45) NOT NULL,
  PRIMARY KEY (`grp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `msg_id` int(11) NOT NULL,
  `msg_date` datetime NOT NULL,
  `msg_contenu` varchar(512) NOT NULL,
  `msg_fil_id` int(11) NOT NULL,
  `msg_groupe_id` int(11) NOT NULL,
  PRIMARY KEY (`msg_id`,`msg_fil_id`,`msg_groupe_id`),
  KEY `fk_message_fil de discussion2_idx` (`msg_fil_id`,`msg_groupe_id`),
  CONSTRAINT `fk_message_fil de discussion2` FOREIGN KEY (`msg_fil_id`, `msg_groupe_id`) REFERENCES `fil_de_discussion` (`fil_id`, `fil_groupe_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE `utilisateur` (
  `uti_id` int(11) NOT NULL,
  `uti_nom` varchar(45) NOT NULL,
  `uti_prenom` varchar(45) NOT NULL,
  `uti_password` varchar(45) NOT NULL,
  PRIMARY KEY (`uti_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `utilisateur_has_groupe`;
CREATE TABLE `utilisateur_has_groupe` (
  `utilisateur_uti_id` int(11) NOT NULL,
  `groupe_grp_id` int(11) NOT NULL,
  PRIMARY KEY (`utilisateur_uti_id`,`groupe_grp_id`),
  KEY `fk_utilisateur_has_groupe_groupe1_idx` (`groupe_grp_id`),
  KEY `fk_utilisateur_has_groupe_utilisateur_idx` (`utilisateur_uti_id`),
  CONSTRAINT `fk_utilisateur_has_groupe_groupe1` FOREIGN KEY (`groupe_grp_id`) REFERENCES `groupe` (`grp_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_utilisateur_has_groupe_utilisateur` FOREIGN KEY (`utilisateur_uti_id`) REFERENCES `utilisateur` (`uti_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 2018-01-06 17:24:51
