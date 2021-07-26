/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.5.62 : Database - hotel
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hotel` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `hotel`;

/*Table structure for table `list_hotel` */

DROP TABLE IF EXISTS `list_hotel`;

CREATE TABLE `list_hotel` (
  `ID` int(3) NOT NULL AUTO_INCREMENT,
  `nama_hotel` varchar(30) DEFAULT NULL,
  `location` varchar(30) DEFAULT NULL,
  `hotel_id` varchar(8) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `ratings` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `list_hotel` */

insert  into `list_hotel`(`ID`,`nama_hotel`,`location`,`hotel_id`,`status`,`ratings`) values 
(1,'JW Marriot','Jakarta','JKT001','Available','5.0 Star'),
(2,'Maple Hotel Grogo','Jakarta','JKT002','FULL','3.0 Star'),
(3,'Kytos Hotel','Bandung','BDG001','Available','4.5 Star'),
(4,'Atlantic City Hotel','Bandung','BDG002','Available','4.0 Star'),
(5,'Aryaduta','Medan','MDN001','Available','2.0 Star');

/*Table structure for table `room_availability` */

DROP TABLE IF EXISTS `room_availability`;

CREATE TABLE `room_availability` (
  `ID` int(3) NOT NULL AUTO_INCREMENT,
  `hotel_id` varchar(8) DEFAULT NULL,
  `suite_room` bigint(11) DEFAULT NULL,
  `deluxe_room` bigint(11) DEFAULT NULL,
  `standard_room` bigint(11) DEFAULT NULL,
  `room_date` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `room_availability` */

insert  into `room_availability`(`ID`,`hotel_id`,`suite_room`,`deluxe_room`,`standard_room`,`room_date`) values 
(1,'JKT001',9,15,25,'2021-07-12 00:31:33'),
(2,'JKT002',0,0,0,'2021-06-16 00:00:00'),
(3,'BDG001',3,10,15,'2021-06-28 00:00:00'),
(4,'BDG002',7,17,20,'2021-06-30 00:00:00'),
(5,'MDN001',5,15,23,'2021-07-12 00:00:00');

/*Table structure for table `room_price` */

DROP TABLE IF EXISTS `room_price`;

CREATE TABLE `room_price` (
  `ID` int(3) NOT NULL AUTO_INCREMENT,
  `hotel_id` varchar(8) DEFAULT NULL,
  `suite_price` bigint(100) DEFAULT NULL,
  `deluxe_price` bigint(100) DEFAULT NULL,
  `standard_price` bigint(100) DEFAULT NULL,
  `price_date` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `room_price` */

insert  into `room_price`(`ID`,`hotel_id`,`suite_price`,`deluxe_price`,`standard_price`,`price_date`) values 
(1,'JKT001',2450000,1950000,1450000,'2021-07-06'),
(2,'JKT002',2350000,1850000,1350000,'2021-07-06'),
(3,'BDG001',2500000,2050000,1250000,'2021-07-06'),
(4,'BDG002',3200000,2550000,1550000,'2021-07-06'),
(5,'MDN001',2150000,1750000,750000,'2021-07-06');

/* Trigger structure for table `list_hotel` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `trigger_hotel` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `trigger_hotel` BEFORE INSERT ON `list_hotel` FOR EACH ROW 
BEGIN
  IF (NEW.hotel_id IS NULL) THEN
    -- Find max existed label for specified sensor type
    SELECT
      MAX(hotel_id) INTO @max_hotel_id
    FROM
      list_hotel
    WHERE
      LOCATION = NEW.location;

    IF (@max_hotel_id IS NULL) THEN
      SET @hotel_id =
        CASE NEW.location
        WHEN 'Jakarta' THEN 'JKT'
        WHEN 'Bandung' THEN 'BDG'
        WHEN 'MEDAN' THEN 'MDN'
        ELSE 'UNKNOWN'
      END;

      -- Set first sensor label
      SET NEW.hotel_id = CONCAT(@hotel_id, '001');
    ELSE
      -- Set next sensor label
      SET NEW.hotel_id = CONCAT(SUBSTR(@max_hotel_id, 1, 3), LPAD(SUBSTR(@max_hotel_id, 4) + 1, 3, '0'));
    END IF;
  END IF;
END */$$


DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
