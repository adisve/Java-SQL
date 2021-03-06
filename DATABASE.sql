-- MySQL Script generated by MySQL Workbench
-- Sun Feb 27 22:57:44 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema pillow_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema pillow_db
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pillow_db`;
CREATE SCHEMA `pillow_db` DEFAULT CHARACTER SET utf8 ;
USE `pillow_db` ;

-- -----------------------------------------------------
-- Table `pillow_db`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pillow_db`.`user` ;

CREATE TABLE IF NOT EXISTS `pillow_db`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(40) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pillow_db`.`phone`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pillow_db`.`phone` ;

CREATE TABLE IF NOT EXISTS `pillow_db`.`phone` (
  `imei` CHAR(15) NOT NULL,
  `uuid` CHAR(38) NOT NULL,
  `mac` CHAR(12) NOT NULL,
  `brand` VARCHAR(45) NOT NULL,
  `model` VARCHAR(30) NOT NULL,
  `manufacturer` VARCHAR(30) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`imei`),
  INDEX `fk_Phone_User_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_Phone_User`
    FOREIGN KEY (`user_id`)
    REFERENCES `pillow_db`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pillow_db`.`schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pillow_db`.`schedule` ;

CREATE TABLE IF NOT EXISTS `pillow_db`.`schedule` (
  `schedule_id` INT NOT NULL AUTO_INCREMENT,
  `alarm_date` VARCHAR(40) NULL,
  `user_id` INT NOT NULL,
  `schedule_name` VARCHAR(45) NULL,
  PRIMARY KEY (`schedule_id`),
  INDEX `fk_Schedule_User1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_Schedule_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `pillow_db`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pillow_db`.`theme`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pillow_db`.`theme` ;

CREATE TABLE IF NOT EXISTS `pillow_db`.`theme` (
  `theme_id` INT NOT NULL AUTO_INCREMENT,
  `color` VARCHAR(7) NULL,
  `pattern` VARCHAR(45) NULL,
  `movement` VARCHAR(45) NULL,
  `speed` INT NULL,
  `schedule_id` INT NOT NULL,
  PRIMARY KEY (`theme_id`, `schedule_id`),
  INDEX `fk_Theme_Schedule1_idx` (`schedule_id` ASC) VISIBLE,
  CONSTRAINT `fk_Theme_Schedule1`
    FOREIGN KEY (`schedule_id`)
    REFERENCES `pillow_db`.`schedule` (`schedule_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pillow_db`.`pillow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pillow_db`.`pillow` ;

CREATE TABLE IF NOT EXISTS `pillow_db`.`pillow` (
  `pillow_id` INT NOT NULL AUTO_INCREMENT,
  `model` VARCHAR(30) NULL,
  `user_id` INT NOT NULL,
  `version_number` FLOAT NULL,
  PRIMARY KEY (`pillow_id`),
  INDEX `fk_Pillow_User1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_Pillow_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `pillow_db`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pillow_db`.`bluetooth_module`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pillow_db`.`bluetooth_module` ;

CREATE TABLE IF NOT EXISTS `pillow_db`.`bluetooth_module` (
  `mac` VARCHAR(48) NOT NULL,
  `uuid` VARCHAR(36) NULL,
  `module_id` INT NOT NULL AUTO_INCREMENT,
  `pillow_id` INT NOT NULL,
  PRIMARY KEY (`module_id`, `pillow_id`),
  INDEX `fk_Bluetooth Module_Pillow1_idx` (`pillow_id` ASC) VISIBLE,
  CONSTRAINT `fk_Bluetooth Module_Pillow1`
    FOREIGN KEY (`pillow_id`)
    REFERENCES `pillow_db`.`pillow` (`pillow_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Insertions for database
-- -----------------------------------------------------


-- User 1
INSERT INTO user(`email`, `password`, `name`) VALUES('john.appleseed@apple.com', 'appleisthebest', 'John');
INSERT INTO phone(`imei`, `uuid`, `mac`, `brand`, `model`, `manufacturer`, `user_id`)
  VALUES('505857561254007', 'b0adf6b8-5ffb-468a-90bf-e1c08afa460c', 'F09BCA844E51', 'Apple', 'iPhone XS', 'Apple Inc.', 1);
INSERT INTO pillow(`model`, `user_id`, `version_number`) VALUES('Helios', '1', '1.0');
INSERT INTO bluetooth_module(`mac`, `uuid`, `pillow_id`) VALUES('E08FGK388R32', 'b47fgd98-5ffc-365a-43bf-a3c08tya420c', 1);
INSERT INTO schedule(`alarm_date`, `user_id`, `schedule_name`) VALUES('Thu Mar 227 2022 08:00:00 GMT+0000', '1', 'Calm friday morning');
INSERT INTO theme(`color`, `pattern`, `movement`, `speed`, `schedule_id`) VALUES('#34baeb', 'circular', 'slow', '3','1');


-- User 2
INSERT INTO user(`email`, `password`, `name`) VALUES('mike@yahoo.com', 'nancy4124', 'Michael');

INSERT INTO phone(`imei`, `uuid`, `mac`, `brand`, `model`, `manufacturer`, `user_id`)
  VALUES('869857536482507', 'b0c7face-9fee-11ec-b909-0242ac120002', '023F9028198E', 'Apple', 'iPhone 5s', 'Apple Inc.', 2);

INSERT INTO pillow(`model`, `user_id`, `version_number`) VALUES('Kuperniko', '2', '3.0');

INSERT INTO bluetooth_module(`mac`, `uuid`, `pillow_id`) VALUES('8B59E2B0B87F', 'b47fgd98-5ffc-365a-43bf-a3c08tya420c', 2);

INSERT INTO schedule(`alarm_date`, `user_id`, `schedule_name`) VALUES('Fri Mar 25 2022 06:00:00 GMT+0000', '2', 'Rise and shine');
INSERT INTO schedule(`alarm_date`, `user_id`, `schedule_name`) VALUES('Mon Mar 28 2022 06:00:00 GMT+0000', '2', 'Rise and shine');

INSERT INTO theme(`color`, `pattern`, `movement`, `speed`, `schedule_id`) VALUES('#fcba03', 'triangle', 'medium', '1','2');
INSERT INTO theme(`color`, `pattern`, `movement`, `speed`, `schedule_id`) VALUES('#fcba03', 'rapidfire', 'fast', '2','2');


-- User 3
INSERT INTO user(`email`, `password`, `name`) VALUES('emelie.cool@gmail.com', 'horses123', 'Emelie');

INSERT INTO phone(`imei`, `uuid`, `mac`, `brand`, `model`, `manufacturer`, `user_id`)
  VALUES('996828562398966', 'b9da6d04-9fee-11ec-b909-0242ac120002', '763A6246AAEB', 'Google', 'Google Pixel 5.1', 'Google.', 3);

INSERT INTO pillow(`model`, `user_id`, `version_number`) VALUES('Helios', '3', '4.0');

INSERT INTO bluetooth_module(`mac`, `uuid`, `pillow_id`) VALUES('B65D4FF0E9B2', 'b47fgd98-5ffc-365a-43bf-a3c08tya420c', 3);

INSERT INTO schedule(`alarm_date`, `user_id`, `schedule_name`) VALUES('Tuesday Mar 22 2022 10:00:00 GMT+0000', '3', 'School');

INSERT INTO theme(`color`, `pattern`, `movement`, `speed`, `schedule_id`) VALUES('#03befc', 'pyramids', 'slow', '2', '4');


-- User 4
INSERT INTO user(`email`, `password`, `name`) VALUES('alberto@icloud.com', 'mechanic_robot234', 'Alberto');

INSERT INTO phone(`imei`, `uuid`, `mac`, `brand`, `model`, `manufacturer`, `user_id`)

  VALUES('540767274537308', 'd4696056-96d9-4fc7-8123-1c149364c124', '64BC95E4BBFF', 'Samsung', 'Galaxy Note 2', 'Samsung Inc.', 4);

INSERT INTO pillow(`model`, `user_id`, `version_number`) VALUES('Lavaetta', '4', '2.0');

INSERT INTO bluetooth_module(`mac`, `uuid`, `pillow_id`) VALUES('F45D0D3582A0', 'b47fgd98-5ffc-365a-43bf-a3c08tya420c', 4);

INSERT INTO schedule(`alarm_date`, `user_id`, `schedule_name`) VALUES('Fri Mar 25 2022 14:00:00 GMT+0000', '5', 'Nightshift');

INSERT INTO theme(`color`, `pattern`, `movement`, `speed`, `schedule_id`) VALUES('#9403fc', 'circular', 'slow', '3', '5');


-- User 5
INSERT INTO user(`email`, `password`, `name`) VALUES('katherine.1234as@gmail.com', 'juggernaut_boots67', 'Kathy');

INSERT INTO phone(`imei`, `uuid`, `mac`, `brand`, `model`, `manufacturer`, `user_id`)

  VALUES('510401653310808', '68895fa7-8123-4dbd-9aea-04e9f873f010', 'FDF2B82B9214', 'Apple', 'iPhone 13 Max', 'Apple Inc.', 5);

INSERT INTO pillow(`model`, `user_id`, `version_number`) VALUES('Helios', '5', '2.0');

INSERT INTO bluetooth_module(`mac`, `uuid`, `pillow_id`) VALUES('E20FFE45D3C5', 'b47fgd98-5ffc-365a-43bf-a3c08tya420c', 5);

INSERT INTO schedule(`alarm_date`, `user_id`, `schedule_name`) VALUES('Mon Mar 21 2022 06:00:00 GMT+0000', '5', 'Calm friday morning');

INSERT INTO theme(`color`, `pattern`, `movement`, `speed`, `schedule_id`) VALUES('#d703fc', 'circular', 'fast', '6', '6');


-- User 6
INSERT INTO user(`email`, `password`, `name`) VALUES('james.bondis007@icloud.com', 'runnergunner_badShoT132', 'James');

INSERT INTO phone(`imei`, `uuid`, `mac`, `brand`, `model`, `manufacturer`, `user_id`)
    VALUES('511214270569931', 'fda1335e-7dc5-410f-a2a5-e7a70555928d', '11EA5E21670C', 'Apple', 'iPhone 8', 'Apple Inc.', 6);

INSERT INTO pillow(`model`, `user_id`, `version_number`) VALUES('NightCrawler', '6', '2.0');

INSERT INTO bluetooth_module(`mac`, `uuid`, `pillow_id`) VALUES('5E4A67F16103', 'b47fgd98-5ffc-365a-43bf-a3c08tya420c', 6);

INSERT INTO schedule(`alarm_date`, `user_id`, `schedule_name`) VALUES('Tue Mar 29 2022 04:00:00 GMT+0000', '6', 'Early Bird');

INSERT INTO theme(`color`, `pattern`, `movement`, `speed`, `schedule_id`) VALUES('#03fcce', 'rapidfire', 'fast', '25', '7');




SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
