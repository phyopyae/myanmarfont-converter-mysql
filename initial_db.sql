CREATE DATABASE `mmfontconverter` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mmfontconverter`;
CREATE TABLE `config` (
  `id` int(11) NOT NULL,
  `databaseName` varchar(200) DEFAULT NULL,
  `configName` varchar(100) NOT NULL,
  `isDeleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
