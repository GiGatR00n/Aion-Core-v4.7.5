SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for webshop
-- ----------------------------
DROP TABLE IF EXISTS `webshop`;
CREATE TABLE `webshop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recipient` varchar(11) NOT NULL,
  `item_desc` varchar(50) NOT NULL,
  `item_id` int(9) NOT NULL,
  `count` int(11) NOT NULL,
  `toll` int(11) NOT NULL DEFAULT '0',
  `balanced` int(11) NOT NULL DEFAULT '0',
  `send` varchar(5) NOT NULL DEFAULT 'FALSE',
  `shop_type` varchar(7) NOT NULL DEFAULT 'NONE',
  `time_received` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of webshop
-- ----------------------------
INSERT INTO `webshop` VALUES ('1', 'Admin', '', '122001654', '1', '0', '0', '0', 'TRUE', 'NONE', '0000-00-00 00:00:00');
