-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mess
-- ------------------------------------------------------
-- Server version	8.0.30employees
select * from employees;

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
  CONSTRAINT `fk_6` FOREIGN KEY (`mess_id`, `section_id`) REFERENCES `section` (`mess_id`, `section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `custo_phn`
--

DROP TABLE IF EXISTS `custo_phn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `custo_phn` (
  `Cu_id` int NOT NULL,
  `phn` varchar(20) NOT NULL,
  PRIMARY KEY (`Cu_id`,`phn`),
  CONSTRAINT `fk_21` FOREIGN KEY (`Cu_id`) REFERENCES `customer` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `active` int DEFAULT NULL,
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
  CONSTRAINT `fk_4` FOREIGN KEY (`mess_id`, `section_id`) REFERENCES `section` (`mess_id`, `section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `emplo_phn`
--

DROP TABLE IF EXISTS `emplo_phn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emplo_phn` (
  `eid` int NOT NULL,
  `phn` varchar(20) NOT NULL,
  PRIMARY KEY (`eid`,`phn`),
  CONSTRAINT `fk_22` FOREIGN KEY (`eid`) REFERENCES `employees` (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `active` int DEFAULT NULL,
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
  CONSTRAINT `fk_3` FOREIGN KEY (`mess_id`, `section_id`) REFERENCES `section` (`mess_id`, `section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  CONSTRAINT `fk_5` FOREIGN KEY (`roll_no`) REFERENCES `student` (`roll_no`),
  CONSTRAINT `fk_24` FOREIGN KEY (`mess_id`, `section_id`) REFERENCES `section` (`mess_id`, `section_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  CONSTRAINT `fk_8` FOREIGN KEY (`mess_id`, `section_id`) REFERENCES `section` (`mess_id`, `section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  CONSTRAINT `fk_7` FOREIGN KEY (`Head_id`) REFERENCES `employees` (`eid`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mess_phn`
--

DROP TABLE IF EXISTS `mess_phn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mess_phn` (
  `Mess_id` int NOT NULL,
  `phn` varchar(20) NOT NULL,
  PRIMARY KEY (`Mess_id`,`phn`),
  CONSTRAINT `fk_20` FOREIGN KEY (`Mess_id`) REFERENCES `mess` (`mess_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  CONSTRAINT `fk_13` FOREIGN KEY (`roll_no`) REFERENCES `student` (`roll_no`) ON DELETE cascade,
  CONSTRAINT `fk_14` FOREIGN KEY (`Q_id`, `Opt_id`) REFERENCES `options` (`Q_id`, `optionid`) ON DELETE cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

select * from opt_student;


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
  CONSTRAINT `fk_12` FOREIGN KEY (`Q_id`) REFERENCES `questions` (`questionid`)  ON DELETE cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



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
  `Finished` int DEFAULT 0,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`questionid`),
  KEY `fk_11` (`mess_id`,`section_id`),
  CONSTRAINT `fk_11` FOREIGN KEY (`mess_id`, `section_id`) REFERENCES `section` (`mess_id`, `section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


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
-- Table structure for table `student`
--
select * from employees;
DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `roll_no` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `active` int DEFAULT NULL,
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
  CONSTRAINT `fk_2` FOREIGN KEY (`Mess_Id`, `section_id`) REFERENCES `section` (`mess_id`, `section_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9879880 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `student_phn`
--

DROP TABLE IF EXISTS `student_phn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_phn` (
  `Rollno` int NOT NULL,
  `phn` varchar(20) NOT NULL,
  PRIMARY KEY (`Rollno`,`phn`),
  CONSTRAINT `fk_23` FOREIGN KEY (`Rollno`) REFERENCES `student` (`roll_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `supervisor`
--

DROP TABLE IF EXISTS `supervisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supervisor` (
  `E_id` int NOT NULL,
  `Manager_id` int NOT NULL,
  PRIMARY KEY (`E_id`,`Manager_id`),
  KEY `fk_16` (`Manager_id`),
  CONSTRAINT `fk_15` FOREIGN KEY (`E_id`) REFERENCES `employees` (`eid`),
  CONSTRAINT `fk_16` FOREIGN KEY (`Manager_id`) REFERENCES `employees` (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `date` date DEFAULT NULL,
  `mode_of_payment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`t_id`),
  KEY `fk_9` (`roll_no`),
  KEY `fk_10` (`C_id`),
  CONSTRAINT `fk_10` FOREIGN KEY (`C_id`) REFERENCES `customer` (`cid`),
  CONSTRAINT `fk_9` FOREIGN KEY (`roll_no`) REFERENCES `student` (`roll_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-05 15:22:38
