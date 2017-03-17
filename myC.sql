-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema cwork
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cwork
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `work` DEFAULT CHARACTER SET utf8 ;
USE `work` ;

-- -----------------------------------------------------
-- Table `cwork`.`exports`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `work`.`exports` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `called` VARCHAR(30) NOT NULL,
  `material` VARCHAR(30) NOT NULL,
  `quantity` INT(11) NOT NULL,
  `date` DATE NOT NULL,
  `factory` VARCHAR(30) NOT NULL,
  `sumexports` INT(10) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB

DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cwork`.`imports`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `work`.`imports` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `called` VARCHAR(30) NOT NULL,
  `material` VARCHAR(30) NOT NULL,
  `quantity` INT(11) NOT NULL,
  `date` DATE NOT NULL,
  `factory` VARCHAR(15) NULL DEFAULT NULL,
  `sumimports` INT(10) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB

DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cwork`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `work`.`users` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `password` INT(11) NOT NULL,
  `is_admin` TINYINT(1) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `username` (`username` ASC))
ENGINE = InnoDB

DEFAULT CHARACTER SET = utf8;

INSERT INTO work.users (username, password,is_admin)
VALUES('user','1234','0'),('ilya','1234','1');


INSERT INTO work.imports (called, material, quantity,date,factory,sumimports)
VALUES('Корпус', 'Железо','250',"2013-08-05",'Апгрейд','2500'),('Платы', 'Пластмасса','500',"2014-12-18",'Регард','1500'),('Кнопки', 'Пластмасса','3000',"2013-02-01",'KeyInc','1000'),('Процессоры', 'Кремний','300',"2016-04-13",'Intel','5000');

INSERT INTO work.exports (called, material, quantity,date,factory,sumexports)
VALUES('PC2000', 'Железо','70',"2014-05-15",'АйтиОН','21000'),('IComp', 'Алюминий','40',"2012-05-15",'IT Lab','30000'),('Inote', 'Алюминий','100',"2015-12-01",'GameCentre','42000'),('FastComp', ' ','200',"2016-11-11",'GlobalIT','200000');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;