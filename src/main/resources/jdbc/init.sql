CREATE TABLE `comments` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `diary_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL COMMENT '这条评论的评论者',
  `to_user_id` bigint(20) DEFAULT NULL COMMENT '用于评论的评论',
  `to_comment_id` bigint(20) DEFAULT NULL COMMENT '用于评论的评论',
  `content` varchar(2048) DEFAULT NULL,
  `to_nickname` varchar(90) DEFAULT NULL COMMENT '用于评论的评论',
  `nickname` varchar(90) DEFAULT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0正常，1删除',
  `add_time` bigint(13) DEFAULT NULL,
  `head_image` varchar(255) DEFAULT NULL COMMENT '评论者头像',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `diaries` (
  `diary_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `title` varchar(100) NOT NULL,
  `date` bigint(13) NOT NULL,
  `type` tinyint(4) NOT NULL,
  `weather` varchar(20) NOT NULL,
  `private_type` varchar(10) NOT NULL,
  `content` text,
  `status` tinyint(2) DEFAULT '0' COMMENT '0代表正常，1代表删除',
  PRIMARY KEY (`diary_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `diary_passwords` (
  `diary_id` bigint(20) NOT NULL,
  `password` varchar(45) NOT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0代表正常，1代表删除',
  PRIMARY KEY (`diary_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `statistics` (
  `diary_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `view_number` int(11) DEFAULT '0',
  `comment_number` int(11) DEFAULT '0',
  `like_number` int(11) DEFAULT '0',
  PRIMARY KEY (`diary_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '账号',
  `password` varchar(45) NOT NULL,
  `nickname` varchar(90) NOT NULL,
  `head_image` varchar(255) DEFAULT NULL COMMENT '头像',
  `sign` varchar(1024) DEFAULT NULL COMMENT '个性签名',
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
