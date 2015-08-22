ALTER TABLE `mail`
MODIFY COLUMN `sender_name`  varchar(64) NOT NULL AFTER `mail_recipient_id`,
MODIFY COLUMN `mail_title`  varchar(64) NOT NULL AFTER `sender_name`;