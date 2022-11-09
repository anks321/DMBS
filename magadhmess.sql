-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: mess
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `announcements`
--
create database magadhmess;
use magadhmess;
DROP TABLE IF EXISTS `announcements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `announcements` (
  `A_id` int NOT NULL,
  `mess_id` int DEFAULT NULL,
  `section_id` int DEFAULT NULL,
  `Date_time` datetime DEFAULT NULL,
  `announce_text` varchar(255) DEFAULT NULL,
  `date_and_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`A_id`),
  KEY `fk_6` (`mess_id`,`section_id`),
  CONSTRAINT `fk_6` FOREIGN KEY (`mess_id`, `section_id`) REFERENCES `section` (`mess_id`, `section_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcements`
--

LOCK TABLES `announcements` WRITE;
/*!40000 ALTER TABLE `announcements` DISABLE KEYS */;
INSERT INTO `announcements` VALUES (1,1,1,NULL,'good morning','12/08/2009');
/*!40000 ALTER TABLE `announcements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `cid` int NOT NULL,
  `mess_id` int DEFAULT NULL,
  `section_id` int DEFAULT NULL,
  `balance` int DEFAULT NULL,
  `pin` int DEFAULT NULL,
  `phone_no` varchar(255) DEFAULT NULL,
  `c_aadhar_number` varchar(255) DEFAULT NULL,
  `account_no` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `ifsc` varchar(255) DEFAULT NULL,
  `dob` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cid`),
  UNIQUE KEY `username` (`username`),
  KEY `fk_4` (`mess_id`,`section_id`),
  CONSTRAINT `fk_4` FOREIGN KEY (`mess_id`, `section_id`) REFERENCES `section` (`mess_id`, `section_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('cust','$2a$10$HW/350nL8JnH2P.ki01FiOpQq7k2HUYePeXyFN.MZ3SoSwFS5utI2',1,1,1,1,1,'1234567999','1','1','M','12345',NULL,'shrivardhanpatil31@gmail.com','Arsh','Kumar','dgd','12');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `eid` int NOT NULL,
  `mess_id` int DEFAULT NULL,
  `section_id` int DEFAULT NULL,
  `salary` int DEFAULT NULL,
  `age` int DEFAULT NULL,
  `phone_no` varchar(15) DEFAULT NULL,
  `pin` int DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `ifsc` varchar(255) DEFAULT NULL,
  `account_no` varchar(255) DEFAULT NULL,
  `e_aadhar_number` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `Designation` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`eid`),
  UNIQUE KEY `username` (`username`),
  KEY `fk_3` (`mess_id`,`section_id`),
  CONSTRAINT `fk_3` FOREIGN KEY (`mess_id`, `section_id`) REFERENCES `section` (`mess_id`, `section_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES ('martys','$2a$10$yoeGW1yVtUclA2WeYLQ5Y.qUwfnKd5IqqSXQKmJQAjGl4kLN0yo8S',2,1,1,10000,NULL,'1234567890',824211,NULL,'12345','1234567890','123456789070','ayus','agar','mess_head','xbsuxjs@gmail.com','xbgwsyhdcw','bcuwc'),('arsh','$2a$10$cqWC7cQQSfk8C2Heg2GMZuMQMuVTof5XgVjQoPa.FN4gRn8FRF6iG',6,1,1,12345,NULL,'1234-567-890',567890,NULL,'34325','1234567890','123456789080','sa1 ','kumar','section_admin','gfvwdye@gmail.com','sdfg','fvffd'),('ashu','1234',9,1,1,10000,NULL,'2323454513',9869,NULL,'7809','234567890','0987654','ashu','dubey','section head','shri@1.com','banaras','23'),('Ayush','$2a$10$EwXOLtpYQ3K9EcocsS1u5.jYqtett1V31OlH5vswzxuYHH7Lqa/NO',98,1,1,123455,NULL,'348563984',56904857,NULL,'345346','098765432','1234567','Ayush','Agrawal','cook','ayush@gmail.com','kumar','2'),('s3','$2a$10$EB8SGg/knVsQRtR3IXJUhONyakVLQpUjrL15Lq/esnA3Z0AL64B0i',11232,1,3,124809,NULL,'1234-567-890',122112,NULL,'34325','23445645','342512','s3','kumar','section_admin','shrivardhanpatil31@gmail.com','dsfsd','ccde');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forum`
--

DROP TABLE IF EXISTS `forum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forum` (
  `C_id` int NOT NULL AUTO_INCREMENT,
  `date_time` varchar(255) DEFAULT NULL,
  `roll_no` int DEFAULT NULL,
  `resolved` int DEFAULT NULL,
  `complaint` varchar(255) DEFAULT NULL,
  `mess_id` int DEFAULT NULL,
  `section_id` int DEFAULT NULL,
  PRIMARY KEY (`C_id`),
  KEY `fk_5` (`roll_no`),
  KEY `fk_24` (`mess_id`,`section_id`),
  CONSTRAINT `fk_24` FOREIGN KEY (`mess_id`, `section_id`) REFERENCES `section` (`mess_id`, `section_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_5` FOREIGN KEY (`roll_no`) REFERENCES `student` (`roll_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum`
--

LOCK TABLES `forum` WRITE;
/*!40000 ALTER TABLE `forum` DISABLE KEYS */;
/*!40000 ALTER TABLE `forum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `item_id` int NOT NULL,
  `mess_id` int DEFAULT NULL,
  `section_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `cost` int DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `fk_8` (`mess_id`,`section_id`),
  CONSTRAINT `fk_8` FOREIGN KEY (`mess_id`, `section_id`) REFERENCES `section` (`mess_id`, `section_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (2,1,1,1,22,NULL,'atta');
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mess`
--

DROP TABLE IF EXISTS `mess`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mess` (
  `mess_id` int NOT NULL,
  `Head_id` int DEFAULT NULL,
  `mess_no` int DEFAULT NULL,
  `m_name` varchar(255) DEFAULT NULL,
  `hostel_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mess_id`),
  KEY `fk_7` (`Head_id`),
  CONSTRAINT `fk_7` FOREIGN KEY (`Head_id`) REFERENCES `employees` (`eid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mess`
--

LOCK TABLES `mess` WRITE;
/*!40000 ALTER TABLE `mess` DISABLE KEYS */;
INSERT INTO `mess` VALUES (1,2,NULL,'m1','H1'),(2,NULL,NULL,'m2','H2');
/*!40000 ALTER TABLE `mess` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opt_student`
--

DROP TABLE IF EXISTS `opt_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `opt_student` (
  `roll_no` int NOT NULL,
  `Q_id` int NOT NULL,
  `Opt_id` int NOT NULL,
  PRIMARY KEY (`roll_no`,`Q_id`,`Opt_id`),
  KEY `fk_14` (`Q_id`,`Opt_id`),
  CONSTRAINT `fk_13` FOREIGN KEY (`roll_no`) REFERENCES `student` (`roll_no`) ON DELETE CASCADE,
  CONSTRAINT `fk_14` FOREIGN KEY (`Q_id`, `Opt_id`) REFERENCES `options` (`Q_id`, `optionid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opt_student`
--

LOCK TABLES `opt_student` WRITE;
/*!40000 ALTER TABLE `opt_student` DISABLE KEYS */;
INSERT INTO `opt_student` VALUES (1,3,4);
/*!40000 ALTER TABLE `opt_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `options`
--

DROP TABLE IF EXISTS `options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `options` (
  `optionid` int NOT NULL,
  `Q_id` int NOT NULL,
  `OptionText` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Q_id`,`optionid`),
  CONSTRAINT `fk_12` FOREIGN KEY (`Q_id`) REFERENCES `questions` (`questionid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `options`
--

LOCK TABLES `options` WRITE;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
INSERT INTO `options` VALUES (1,1,'fwefs'),(2,1,'gxg4e4w'),(3,3,'good'),(4,3,'bad');
/*!40000 ALTER TABLE `options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `questionid` int NOT NULL,
  `mess_id` int DEFAULT NULL,
  `section_id` int DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `Finished` int DEFAULT '0',
  PRIMARY KEY (`questionid`),
  KEY `fk_11` (`mess_id`,`section_id`),
  CONSTRAINT `fk_11` FOREIGN KEY (`mess_id`, `section_id`) REFERENCES `section` (`mess_id`, `section_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,1,1,'brthrtd',0),(2,1,1,'gtfrxyt',0),(3,1,1,'how are you doing',1);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `section` (
  `mess_id` int NOT NULL,
  `section_id` int NOT NULL,
  `breakfast` varchar(255) DEFAULT NULL,
  `lunch` varchar(255) DEFAULT NULL,
  `dinner` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mess_id`,`section_id`),
  CONSTRAINT `fk_1` FOREIGN KEY (`mess_id`) REFERENCES `mess` (`mess_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
INSERT INTO `section` VALUES (1,1,'idli','poori','poha'),(1,2,NULL,NULL,NULL),(1,3,'vfdveii','dgegs','cfhjiyg'),(2,1,'Null','Null','Null');
/*!40000 ALTER TABLE `section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `roll_no` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `Age` int DEFAULT NULL,
  `room_no` int DEFAULT NULL,
  `Balance` int DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `f_name` varchar(255) DEFAULT NULL,
  `l_name` varchar(255) DEFAULT NULL,
  `hostel_name` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `parent` varchar(255) DEFAULT NULL,
  `phone_no` varchar(255) DEFAULT NULL,
  `s_email` varchar(255) DEFAULT NULL,
  `localGaurdian` varchar(255) DEFAULT NULL,
  `aadhar_no` varchar(255) DEFAULT NULL,
  `s_account_no` varchar(255) DEFAULT NULL,
  `s_ifsc` varchar(255) DEFAULT NULL,
  `Mess_Id` int DEFAULT NULL,
  `section_id` int DEFAULT NULL,
  PRIMARY KEY (`roll_no`),
  UNIQUE KEY `username` (`username`),
  KEY `fk_2` (`Mess_Id`,`section_id`),
  CONSTRAINT `fk_2` FOREIGN KEY (`Mess_Id`, `section_id`) REFERENCES `section` (`mess_id`, `section_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9879880 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'c1','$2a$10$Xy8zQguGCV5n2S2u8Nqg1efG9hTJablE//0qY9.CLXMsW2wp3QjWK',1,1,-550,NULL,'c1','kumar','sqxsx','M','xcxdcxs','7890-890-890','w@gmail.com','wdcc','1234567880','123456789070','325',1,1);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `t_id` int NOT NULL,
  `roll_no` int DEFAULT NULL,
  `C_id` int DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `mode_of_payment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`t_id`),
  KEY `fk_9` (`roll_no`),
  KEY `fk_10` (`C_id`),
  CONSTRAINT `fk_10` FOREIGN KEY (`C_id`) REFERENCES `customer` (`cid`) ON DELETE CASCADE,
  CONSTRAINT `fk_9` FOREIGN KEY (`roll_no`) REFERENCES `student` (`roll_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,1,NULL,100,1,'12/09/2000','bank'),(2,1,NULL,200,0,'25/07/2010','bankwd');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-09 10:51:10
