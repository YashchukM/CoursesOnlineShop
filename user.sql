CREATE TABLE `shop_schema`.`user` (
  `id_user` INT NOT NULL,
  `fir_name` VARCHAR(30) NULL,
  `sec_name` VARCHAR(30) NULL,
  `email` VARCHAR(40) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_user`));

ALTER TABLE `shop_schema`.`user`
ADD COLUMN `admin` VARCHAR(1) NOT NULL AFTER `password`,
ADD COLUMN `blacklist` VARCHAR(1) NOT NULL AFTER `admin`;

ALTER TABLE `shop_schema`.`user`
CHANGE COLUMN `blacklist` `blacklist` TINYINT(1) NOT NULL ;

ALTER TABLE `shop_schema`.`user`
CHANGE COLUMN `id_user` `id_user` INT(11) NOT NULL AUTO_INCREMENT ,
CHANGE COLUMN `email` `email` VARCHAR(40) NULL ,
CHANGE COLUMN `password` `password` VARCHAR(45) NULL ,
CHANGE COLUMN `admin` `admin` TINYINT(1) NULL ,
CHANGE COLUMN `blacklist` `blacklist` TINYINT(1) NULL ;
