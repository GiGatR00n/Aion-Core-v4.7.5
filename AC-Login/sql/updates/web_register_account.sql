ALTER TABLE `account_data` ADD COLUMN `email` varchar(50) NULL AFTER `toll`;
ALTER TABLE `account_data` ADD COLUMN `question` varchar(50) NULL AFTER `email`;
ALTER TABLE `account_data` ADD COLUMN `answer` varchar(50) NULL AFTER `question`;
