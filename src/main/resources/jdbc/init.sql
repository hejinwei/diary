CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '账号',
  `password` varchar(45) NOT NULL,
  `nickname` varchar(90) NOT NULL,
  `head_image` varchar(255) DEFAULT NULL COMMENT '头像',
  `sign` varchar(1024) DEFAULT NULL COMMENT '个性签名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
