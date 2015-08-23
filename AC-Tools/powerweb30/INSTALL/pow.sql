CREATE TABLE `log_billing` (
  `pay_id` varchar(16) NOT NULL,
  `sum` int(11) NOT NULL,
  `account` varchar(16) NOT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(16) NOT NULL DEFAULT 'unpaid',
  `system` varchar(16) NOT NULL,
  PRIMARY KEY (`pay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `log_membership` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(32) NOT NULL,
  `title` varchar(64) NOT NULL,
  `type` varchar(32) NOT NULL,
  `duration` int(4) NOT NULL,
  `price` int(11) NOT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `log_points` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender` varchar(32) NOT NULL,
  `recipient` varchar(32) NOT NULL,
  `sum` int(11) NOT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `log_referals` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `master` varchar(32) NOT NULL,
  `master_id` int(11) NOT NULL,
  `slave` varchar(32) NOT NULL,
  `slave_id` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(8) NOT NULL DEFAULT 'unpaid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `log_vote_aiontop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `type` int(1) NOT NULL DEFAULT '1',
  `date` varchar(32) NOT NULL,
  `status` varchar(16) NOT NULL DEFAULT 'unpaid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `log_vote_gamesites200` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `type` int(1) NOT NULL DEFAULT '1',
  `date` varchar(32) NOT NULL,
  `status` varchar(16) NOT NULL DEFAULT 'unpaid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `log_vote_gtop100` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `type` int(1) NOT NULL DEFAULT '1',
  `date` varchar(32) NOT NULL,
  `status` varchar(16) NOT NULL DEFAULT 'unpaid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `log_vote_l2top` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `type` int(1) NOT NULL DEFAULT '1',
  `date` varchar(32) NOT NULL,
  `status` varchar(16) NOT NULL DEFAULT 'unpaid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `log_vote_mmotop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `ip` varchar(32) DEFAULT NULL,
  `type` int(1) NOT NULL DEFAULT '1',
  `date` varchar(32) NOT NULL,
  `status` varchar(16) NOT NULL DEFAULT 'unpaid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `log_vote_xtremetop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `type` int(1) NOT NULL DEFAULT '1',
  `date` varchar(32) NOT NULL,
  `status` varchar(16) NOT NULL DEFAULT 'unpaid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `log_webshop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `item` int(11) NOT NULL,
  `amount` int(8) NOT NULL,
  `price` int(8) NOT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `membership` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) NOT NULL,
  `type` int(4) NOT NULL,
  `duration` int(4) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
INSERT INTO `membership` VALUES ('1', 'Premium #1', '1', '7', '50');
INSERT INTO `membership` VALUES ('2', 'VIP #1', '2', '7', '75');
INSERT INTO `membership` VALUES ('3', 'Premium #2', '1', '30', '170');
INSERT INTO `membership` VALUES ('4', 'VIP #2', '2', '30', '250');


CREATE TABLE `news` (
  `news_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `title` varchar(128) NOT NULL,
  `category_id` int(4) NOT NULL,
  `short_story` text NOT NULL,
  `full_story` text,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`news_id`),
  KEY `category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `news_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `alt_name` varchar(32) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `news_category` VALUES ('1', 'News', 'news', 'News', null, null);


CREATE TABLE `pages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `title` varchar(128) NOT NULL,
  `text` text NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `session` (
  `id` char(32) NOT NULL,
  `ip_address` int(10) unsigned NOT NULL DEFAULT '0',
  `user_agent` char(32) DEFAULT NULL,
  `expire` int(11) DEFAULT NULL,
  `data` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `access_level_admin` int(4) NOT NULL,
  `access_level_editor` int(4) NOT NULL,
  `hide_top` int(4) NOT NULL,
  `page_news` int(4) NOT NULL,
  `page_shop` int(4) NOT NULL,
  `page_top` int(4) NOT NULL,
  `referal_bonus` int(4) NOT NULL,
  `referal_bonus_ref` int(4) NOT NULL,
  `referal_enable` int(4) NOT NULL,
  `referal_level` int(4) NOT NULL,
  `trial_days` int(4) NOT NULL,
  `trial_enable` int(4) NOT NULL,
  `trial_type` int(4) NOT NULL,
  `email_activation` int(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `settings` VALUES ('1', '3', '3', '6', '10', '25', '50', '100', '50', '1', '25', '3', '1', '1', '1');


CREATE TABLE `settings_pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mrh_login` varchar(128) DEFAULT '',
  `mrh_pass1` varchar(128) DEFAULT '',
  `mrh_pass2` varchar(128) DEFAULT '',
  `inv_desc` varchar(255) DEFAULT '',
  `ik_shop_id` varchar(128) DEFAULT '',
  `secret_key` varchar(128) DEFAULT '',
  `ik_payment_desc` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `settings_pay` VALUES ('1', 'Login', 'password1', 'password2', 'Описание заказа', '0FE82795-DE03-8969-C446-1F4344EBB6EF', 'uqx1dBsSnjbPaGaC', 'Пополнение баланса');


CREATE TABLE `settings_vote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aiontop_link` varchar(128) DEFAULT NULL,
  `aiontop_reg` int(4) DEFAULT NULL,
  `l2top_link` varchar(128) DEFAULT NULL,
  `l2top_reg` int(4) DEFAULT NULL,
  `mmotop_link` varchar(128) DEFAULT NULL,
  `mmotop_reg` int(4) DEFAULT NULL,
  `mmotop_sms` int(4) DEFAULT NULL,
  `gtop_link` varchar(128) DEFAULT NULL,
  `gtop_reg` int(4) DEFAULT NULL,
  `gamesites_link` varchar(128) DEFAULT NULL,
  `gamesites_reg` int(4) DEFAULT NULL,
  `xtremetop_link` varchar(128) DEFAULT NULL,
  `xtremetop_reg` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `settings_vote` VALUES ('1', 'http://aion-top.info/', '10', 'http://aion.l2top.ru/', '10', 'http://aion.mmotop.ru/', '10', '25', 'http://gtop100.com/', '5', 'http://gamesites200.com/', '5', 'http://xtremetop100.com/', '5');


CREATE TABLE `webshop` (
  `item_id` int(11) NOT NULL,
  `level` int(2) DEFAULT NULL,
  `name` varchar(128) NOT NULL,
  `category_id` int(4) NOT NULL,
  `amount` int(11) NOT NULL DEFAULT '1',
  `price` int(11) NOT NULL,
  `edit` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `webshop_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `alt_name` varchar(32) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;