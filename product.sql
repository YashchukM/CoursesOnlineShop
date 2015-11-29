CREATE TABLE `shop_schema`.`product` (
  `id_product` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(100) NOT NULL,
  `available` VARCHAR(1) NOT NULL,
  `price` FLOAT NULL,
  PRIMARY KEY (`id_product`));

ALTER TABLE `shop_schema`.`product`
CHANGE COLUMN `available` `available` TINYINT(1) NOT NULL ;
