/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.5.62 : Database - user
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`user` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `user`;

/*Table structure for table `pemesanan` */

DROP TABLE IF EXISTS `pemesanan`;

CREATE TABLE `pemesanan` (
  `ID` int(3) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `nama_pemesan` varchar(30) DEFAULT NULL,
  `booking_id` varchar(30) DEFAULT NULL,
  `hotel_name` varchar(30) DEFAULT NULL,
  `room_type` varchar(30) DEFAULT NULL,
  `quantity` int(3) DEFAULT NULL,
  `jumlah_malam` int(30) DEFAULT NULL,
  `total_payment` int(30) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `tanggal_pemesanan` datetime DEFAULT NULL,
  `tanggal_checkin` datetime DEFAULT NULL,
  `tanggal_checkout` datetime DEFAULT NULL,
  `tanggal_pembayaran` datetime DEFAULT NULL,
  `tanggal_pembatalan` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Data for the table `pemesanan` */

insert  into `pemesanan`(`ID`,`username`,`nama_pemesan`,`booking_id`,`hotel_name`,`room_type`,`quantity`,`jumlah_malam`,`total_payment`,`status`,`tanggal_pemesanan`,`tanggal_checkin`,`tanggal_checkout`,`tanggal_pembayaran`,`tanggal_pembatalan`) values 
(7,'Jansen24@gmail.com','Jansen','SEv9gQrl','JW Marriot','deluxe',10,10,195000000,'Canceled','2021-07-10 11:41:09','2021-07-10 14:00:00','2021-07-20 12:00:00',NULL,'2021-07-10 11:41:30'),
(12,'LordCahyanto@gmail.com','Lord Eko','LUC3Tteb','JW Marriot','suite',5,15,183750000,'Canceled','2021-07-12 00:31:13','2021-07-10 14:00:00','2021-07-25 12:00:00',NULL,'2021-07-12 00:31:33');

/*Table structure for table `user_travel` */

DROP TABLE IF EXISTS `user_travel`;

CREATE TABLE `user_travel` (
  `id` bigint(3) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `user_travel` */

insert  into `user_travel`(`id`,`username`,`password`) values 
(1,'jansenstanh24@gmail.com','HesOyam_11'),
(2,'Evitadewi88@gmail.com','E_vita@12'),
(3,'Johannes_55@gmail.com','Jo@han_72'),
(4,'Jansen24@gmail.com','Jo@han_72'),
(5,'Jansen455@gmail.com','J_axaA12'),
(6,'LordCahyanto@gmail.com','Lord90_as');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
