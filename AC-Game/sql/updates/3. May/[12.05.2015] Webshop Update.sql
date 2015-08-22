ALTER TABLE `webshop`
 DROP COLUMN `kinah`,
 CHANGE COLUMN dinar toll
    int(11) NOT NULL DEFAULT '0';