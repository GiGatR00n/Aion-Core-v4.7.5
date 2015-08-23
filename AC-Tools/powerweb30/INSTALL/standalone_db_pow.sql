/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50616
Source Host           : 192.168.2.20:3306
Source Database       : pow

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2015-04-30 17:33:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for log_billing
-- ----------------------------
DROP TABLE IF EXISTS `log_billing`;
CREATE TABLE `log_billing` (
  `pay_id` varchar(16) NOT NULL,
  `sum` int(11) NOT NULL,
  `account` varchar(16) NOT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(16) NOT NULL DEFAULT 'unpaid',
  `system` varchar(16) NOT NULL,
  PRIMARY KEY (`pay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_billing
-- ----------------------------

-- ----------------------------
-- Table structure for log_membership
-- ----------------------------
DROP TABLE IF EXISTS `log_membership`;
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

-- ----------------------------
-- Records of log_membership
-- ----------------------------

-- ----------------------------
-- Table structure for log_points
-- ----------------------------
DROP TABLE IF EXISTS `log_points`;
CREATE TABLE `log_points` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender` varchar(32) NOT NULL,
  `recipient` varchar(32) NOT NULL,
  `sum` int(11) NOT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_points
-- ----------------------------

-- ----------------------------
-- Table structure for log_referals
-- ----------------------------
DROP TABLE IF EXISTS `log_referals`;
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

-- ----------------------------
-- Records of log_referals
-- ----------------------------

-- ----------------------------
-- Table structure for log_vote_aiontop
-- ----------------------------
DROP TABLE IF EXISTS `log_vote_aiontop`;
CREATE TABLE `log_vote_aiontop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `type` int(1) NOT NULL DEFAULT '1',
  `date` varchar(32) NOT NULL,
  `status` varchar(16) NOT NULL DEFAULT 'unpaid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_vote_aiontop
-- ----------------------------

-- ----------------------------
-- Table structure for log_vote_gamesites200
-- ----------------------------
DROP TABLE IF EXISTS `log_vote_gamesites200`;
CREATE TABLE `log_vote_gamesites200` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `type` int(1) NOT NULL DEFAULT '1',
  `date` varchar(32) NOT NULL,
  `status` varchar(16) NOT NULL DEFAULT 'unpaid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_vote_gamesites200
-- ----------------------------

-- ----------------------------
-- Table structure for log_vote_gtop100
-- ----------------------------
DROP TABLE IF EXISTS `log_vote_gtop100`;
CREATE TABLE `log_vote_gtop100` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `type` int(1) NOT NULL DEFAULT '1',
  `date` varchar(32) NOT NULL,
  `status` varchar(16) NOT NULL DEFAULT 'unpaid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_vote_gtop100
-- ----------------------------

-- ----------------------------
-- Table structure for log_vote_l2top
-- ----------------------------
DROP TABLE IF EXISTS `log_vote_l2top`;
CREATE TABLE `log_vote_l2top` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `type` int(1) NOT NULL DEFAULT '1',
  `date` varchar(32) NOT NULL,
  `status` varchar(16) NOT NULL DEFAULT 'unpaid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_vote_l2top
-- ----------------------------

-- ----------------------------
-- Table structure for log_vote_mmotop
-- ----------------------------
DROP TABLE IF EXISTS `log_vote_mmotop`;
CREATE TABLE `log_vote_mmotop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `ip` varchar(32) DEFAULT NULL,
  `type` int(1) NOT NULL DEFAULT '1',
  `date` varchar(32) NOT NULL,
  `status` varchar(16) NOT NULL DEFAULT 'unpaid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_vote_mmotop
-- ----------------------------

-- ----------------------------
-- Table structure for log_vote_xtremetop
-- ----------------------------
DROP TABLE IF EXISTS `log_vote_xtremetop`;
CREATE TABLE `log_vote_xtremetop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `type` int(1) NOT NULL DEFAULT '1',
  `date` varchar(32) NOT NULL,
  `status` varchar(16) NOT NULL DEFAULT 'unpaid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_vote_xtremetop
-- ----------------------------

-- ----------------------------
-- Table structure for log_webshop
-- ----------------------------
DROP TABLE IF EXISTS `log_webshop`;
CREATE TABLE `log_webshop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `item` int(11) NOT NULL,
  `amount` int(8) NOT NULL,
  `price` int(8) NOT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_webshop
-- ----------------------------

-- ----------------------------
-- Table structure for membership
-- ----------------------------
DROP TABLE IF EXISTS `membership`;
CREATE TABLE `membership` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) NOT NULL,
  `type` int(4) NOT NULL,
  `duration` int(4) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of membership
-- ----------------------------
INSERT INTO `membership` VALUES ('1', 'Premium #1', '1', '7', '50');
INSERT INTO `membership` VALUES ('2', 'VIP #1', '2', '7', '75');
INSERT INTO `membership` VALUES ('3', 'Premium #2', '1', '30', '170');
INSERT INTO `membership` VALUES ('4', 'VIP #2', '2', '30', '250');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('1', '1', 'Welcome to Aion Core NA development', '1', '<p><strong>Discover the Stunning World of Aion</strong><br><br><em>Explore\r\n one of the most amazingly beautiful and detailed MMORPGs ever as you \r\nfight&nbsp; to save a vibrant world ripped asunder by a celestial war.<br>\r\nChoose between two divine factions, the Asmodians or the Elyos, and save\r\n your people from an ancient evil that threatens to destroy everything \r\nin its path. Over 2000 story-driven quests lead you through a wondrous \r\nand expansive world like no other.</em></p>\r\n<p>&nbsp;</p>\r\n<p><em>In\r\n Aion™, you are a winged Elyos or Asmodian, sworn enemy races, exploring\r\n a world of breathtaking beauty ripped asunder by a celestial war. In \r\nthis stunning massively multiplayer online roleplaying game, abundant \r\nsolo adventures, intriguing epic group quests, challenging dungeons, and\r\n massive Legion raids hone your battle skills to new heights. Take the \r\nfight against the dragonlike Balaur invaders into Balaurea, your common \r\nenemy\'s homeland, to save the shattered world you love.</em></p>\r\n<p>&nbsp;<br><strong>&nbsp;Game Features:</strong></p>\r\n<ul><li>&nbsp;A visually astonishing game world</li><li>&nbsp;Soar through the skies with thrilling character flight</li><li>&nbsp;Intense PvE and PvP combat</li><li>&nbsp;Thousands of hours of story-driven content</li></ul><p><strong>Welcome to our new Aion Core Community</strong><br><br>\r\nWe invite you, if you wish, to take a part of our project, to develop \r\ntogether in a big community, like Aion Unique, a new and stable Emulator\r\n based on Aion Lightning Core.<br>\r\nNote: This project is only for developers, so you will need to apply \r\nfor. The source will be free only for the community developers.<br>\r\nFor the public we will offer a weekly compiled release of our work in our Download section.<br><br>\r\nAion Emu development current state:</p>\r\n<ul><li>Full AION -<strong> 4.7.5.12</strong> - <strong>NA</strong>&nbsp; Client support</li><li>&nbsp;</li><li>All patch features until 4.7 are integrated</li><li>&nbsp;</li><li>New Godstone System integrated</li><li>Amplification System integrated</li><li>Plume integrated</li><li>Tempering System integrated</li><li>Item Purification integrated</li><li>Atreian Passport integrated</li><li>Upgrade Arcade (Coming Soon)</li><li>Hotspot Teleportation integrated</li><li>Multi Return Scrolls&nbsp;Teleportation integrated</li><li>Hall of Fame integrated</li><li>Bases up to 4.7 integrated</li><li>Sieges up to 4.7 integrated</li><li>Invasion Raid integrated</li><li>All Retail Instances AI Assisted 4.7</li><li>Retail Abyss Prestige Glory System</li><li>New Brocker System &amp; UI integrated</li><li>Fast Track Server emulated (Coming Soon)</li><li>All Classes &amp; Skills works retail</li><li>Retail Crafting</li><li>Retail Wings &amp; Pets&nbsp;&amp; Mounts</li><li>Retail Emotes</li><li>GeoData Integrated</li><li>New WDS (Worls Drop System)&nbsp;(Coming Soon)</li><li>And all previous retail features (Quests,Instances,Items,Housing, etc)</li></ul>', '', '2015-04-30 13:46:47', '', '');

-- ----------------------------
-- Table structure for news_category
-- ----------------------------
DROP TABLE IF EXISTS `news_category`;
CREATE TABLE `news_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `alt_name` varchar(32) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news_category
-- ----------------------------
INSERT INTO `news_category` VALUES ('1', 'News', 'news', 'News', '', '');

-- ----------------------------
-- Table structure for pages
-- ----------------------------
DROP TABLE IF EXISTS `pages`;
CREATE TABLE `pages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `title` varchar(128) NOT NULL,
  `text` text NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pages
-- ----------------------------

-- ----------------------------
-- Table structure for session
-- ----------------------------
DROP TABLE IF EXISTS `session`;
CREATE TABLE `session` (
  `id` char(32) NOT NULL,
  `ip_address` int(10) unsigned NOT NULL DEFAULT '0',
  `user_agent` char(32) DEFAULT NULL,
  `expire` int(11) DEFAULT NULL,
  `data` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of session
-- ----------------------------

-- ----------------------------
-- Table structure for settings
-- ----------------------------
DROP TABLE IF EXISTS `settings`;
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

-- ----------------------------
-- Records of settings
-- ----------------------------
INSERT INTO `settings` VALUES ('1', '3', '3', '6', '10', '25', '50', '100', '50', '1', '25', '3', '1', '1', '1');

-- ----------------------------
-- Table structure for settings_pay
-- ----------------------------
DROP TABLE IF EXISTS `settings_pay`;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of settings_pay
-- ----------------------------

-- ----------------------------
-- Table structure for settings_vote
-- ----------------------------
DROP TABLE IF EXISTS `settings_vote`;
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

-- ----------------------------
-- Records of settings_vote
-- ----------------------------
INSERT INTO `settings_vote` VALUES ('1', 'http://aion-top.info/', '10', 'http://aion.l2top.ru/', '10', 'http://aion.mmotop.ru/', '10', '25', 'http://gtop100.com/', '5', 'http://gamesites200.com/', '5', 'http://xtremetop100.com/', '5');

-- ----------------------------
-- Table structure for webshop
-- ----------------------------
DROP TABLE IF EXISTS `webshop`;
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

-- ----------------------------
-- Records of webshop
-- ----------------------------
INSERT INTO `webshop` VALUES ('110900070', '1', 'High Class Costume Luxury (Elyos NPC)', '3', '1', '1200', '0');

-- ----------------------------
-- Table structure for webshop_category
-- ----------------------------
DROP TABLE IF EXISTS `webshop_category`;
CREATE TABLE `webshop_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `alt_name` varchar(32) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of webshop_category
-- ----------------------------
INSERT INTO `webshop_category` VALUES ('1', 'Accessory', 'accessory');
INSERT INTO `webshop_category` VALUES ('2', 'Dye', 'dye');
INSERT INTO `webshop_category` VALUES ('3', 'Dresses', 'dresses');
INSERT INTO `webshop_category` VALUES ('4', 'Emotes', 'emotes');
INSERT INTO `webshop_category` VALUES ('5', 'Crafting', 'crafting');
