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

CREATE TABLE `diary`.`diaries` (
  `diary_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `date` BIGINT(13) NOT NULL,
  `type` TINYINT(4) NOT NULL,
  `weather` VARCHAR(10) NOT NULL,
  `private` VARCHAR(10) NOT NULL,
  `password` VARCHAR(45) NULL,
  `content` TEXT NULL,
  `status` TINYINT(2) NULL DEFAULT 0 COMMENT '0代表正常，1代表删除',
  PRIMARY KEY (`diary_id`))
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE `statics` (
  `diary_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `view_number` int(11) DEFAULT '0',
  `comment_number` int(11) DEFAULT '0',
  `like_number` int(11) DEFAULT '0',
  PRIMARY KEY (`diary_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

