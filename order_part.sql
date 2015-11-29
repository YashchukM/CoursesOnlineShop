CREATE TABLE `shop_schema`.`order_part` (
  `id_order_part` INT NOT NULL,
  `id_order` INT NOT NULL,
  `id_product` INT NOT NULL,
  `number` INT NOT NULL,
  PRIMARY KEY (`id_order_part`),
  INDEX `id_order_idx` (`id_order` ASC),
  INDEX `id_product_idx` (`id_product` ASC),
  CONSTRAINT `id_order`
    FOREIGN KEY (`id_order`)
    REFERENCES `shop_schema`.`order` (`id_order`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_product`
    FOREIGN KEY (`id_product`)
    REFERENCES `shop_schema`.`product` (`id_product`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
