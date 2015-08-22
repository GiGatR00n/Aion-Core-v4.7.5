-- ----------------------------
-- Table structure for `myshop`
-- ----------------------------
DROP TABLE IF EXISTS `myshop`;
CREATE TABLE IF NOT EXISTS `myshop` (
  `object_id` int(11) NOT NULL auto_increment,
  `item` int(11) NOT NULL,
  `nb` bigint(13) NOT NULL DEFAULT '0',
  `player_id` int(11) NOT NULL,
  PRIMARY KEY (`object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;