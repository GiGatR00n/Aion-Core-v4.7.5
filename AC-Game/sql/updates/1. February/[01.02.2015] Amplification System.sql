ALTER TABLE `inventory` ADD COLUMN `is_amplified` TINYINT NOT NULL DEFAULT 0 AFTER `is_packed`;
ALTER TABLE `inventory` ADD COLUMN `buff_skill` INT NOT NULL DEFAULT 0 AFTER `is_amplified`;