CREATE TABLE `player_login_reward` (
  `player_id` int(11) NOT NULL,
  `activated_id` int(11) NOT NULL,
  `login_count` int(11) NOT NULL,
  `next_login_count` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`player_id`,`activated_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE `player_passports`;

ALTER TABLE `players`
DROP COLUMN `rewarded_pass`,
DROP COLUMN `last_stamp`,
DROP COLUMN `stamps`;