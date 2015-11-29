CREATE TABLE `shop_schema`.`order` (
  `id_order` INT NOT NULL,
  `id_user` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `paid` VARCHAR(1) NOT NULL,
  PRIMARY KEY (`id_order`));

ALTER TABLE `shop_schema`.`order`
ADD INDEX `id_user_idx` (`id_user` ASC);
ALTER TABLE `shop_schema`.`order`
ADD CONSTRAINT `id_user`
FOREIGN KEY (`id_user`)
REFERENCES `shop_schema`.`user` (`id_user`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `shop_schema`.`order`
ADD COLUMN `price` FLOAT NOT NULL AFTER `paid`;

ALTER TABLE `shop_schema`.`order`
CHANGE COLUMN `paid` `paid` TINYINT(1) NOT NULL ;
