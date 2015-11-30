CREATE TABLE `shop_schema`.`user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fir_name` varchar(30) DEFAULT NULL,
  `sec_name` varchar(30) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `admin` tinyint(1) DEFAULT NULL,
  `blacklist` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;