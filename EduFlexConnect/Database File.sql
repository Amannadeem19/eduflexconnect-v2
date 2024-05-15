-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: fyp
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `id` int NOT NULL,
  `department_code` varchar(255) DEFAULT NULL,
  `department_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'SE','Software Engineering'),(2,'CS','Computer Science'),(3,'AI','Artificial Intelligence'),(4,'DS','Data Science'),(5,'CYD','Cyber Security'),(6,'EE','Electrical Engineering');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department_id_generator`
--

DROP TABLE IF EXISTS `department_id_generator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department_id_generator` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department_id_generator`
--

LOCK TABLES `department_id_generator` WRITE;
/*!40000 ALTER TABLE `department_id_generator` DISABLE KEYS */;
INSERT INTO `department_id_generator` VALUES (7);
/*!40000 ALTER TABLE `department_id_generator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration`
--

DROP TABLE IF EXISTS `registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registration` (
  `arn` int NOT NULL,
  `campus` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `cnic` varchar(255) DEFAULT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `father_cnic` varchar(255) DEFAULT NULL,
  `father_name` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `home_phone` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `street_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`arn`),
  UNIQUE KEY `UK_q2oehbvht8xdunptm95fwq2ha` (`cnic`),
  UNIQUE KEY `UK_pqp6404l2ndskpsr1xx8eaa68` (`email`),
  UNIQUE KEY `UK_r7jyatrherak5ba10qhuduxfs` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration`
--

LOCK TABLES `registration` WRITE;
/*!40000 ALTER TABLE `registration` DISABLE KEYS */;
INSERT INTO `registration` VALUES (2370012,'Karachi','Karachi','4220112345676','Software Engineering','2002-08-14','anticlash06@gmail.com','4220100987654','Asghar Ali','Syed Ajwad Ali','male','03152214511','03082212577','Gulistan e Jauhar Block 14'),(2381632,'Karachi','Karachi','4220166154673','Software Engineering','2002-04-24','AbdullahAftab2403@gmail.com','4220186254577','Aftab Ahmed','Abdullah Aftab','male','03152214530','03156942530','House No L1 Gulshan E Iqbal 13D3');
/*!40000 ALTER TABLE `registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration_preference`
--

DROP TABLE IF EXISTS `registration_preference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registration_preference` (
  `arn` int NOT NULL,
  `preference1` varchar(255) DEFAULT NULL,
  `preference2` varchar(255) DEFAULT NULL,
  `preference3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`arn`),
  CONSTRAINT `FKjdxobien0g235amyoy6t34c6r` FOREIGN KEY (`arn`) REFERENCES `registration` (`arn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration_preference`
--

LOCK TABLES `registration_preference` WRITE;
/*!40000 ALTER TABLE `registration_preference` DISABLE KEYS */;
INSERT INTO `registration_preference` VALUES (2370012,'Computer Science','Artificial Intelligence','Software Engineering'),(2381632,'Software Engineering','Computer Science','Artificial Intelligence');
/*!40000 ALTER TABLE `registration_preference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `section` (
  `id` varchar(255) NOT NULL,
  `department` varchar(255) DEFAULT NULL,
  `enroll_year` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
INSERT INTO `section` VALUES ('23CSA','Computer Science',2023),('23CSB','Computer Science',2023),('23SEA','Software Engineering',2023),('23SEB','Software Engineering',2023),('23SEC','Software Engineering',2023);
/*!40000 ALTER TABLE `section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` varchar(255) NOT NULL,
  `batch` varchar(255) DEFAULT NULL,
  `campus` varchar(255) DEFAULT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `login_password` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `section_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj1c0de75pterue04btw6w5mh8` (`section_id`),
  CONSTRAINT `FKj1c0de75pterue04btw6w5mh8` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('23KSE7140','2023','Karachi','Software Engineering','=%uwS8nkO)','Current','23CSA'),('23KSE7669','2023','Karachi','Software Engineering','!tQzl@e=B!','Current','23SEA');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_contact_info`
--

DROP TABLE IF EXISTS `student_contact_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_contact_info` (
  `id` varchar(255) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `father_cnic` varchar(255) DEFAULT NULL,
  `father_name` varchar(255) DEFAULT NULL,
  `home_phone` varchar(255) DEFAULT NULL,
  `street_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK5j918k9g5didterfy9o1e7116` FOREIGN KEY (`id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_contact_info`
--

LOCK TABLES `student_contact_info` WRITE;
/*!40000 ALTER TABLE `student_contact_info` DISABLE KEYS */;
INSERT INTO `student_contact_info` VALUES ('23KSE7140','Karachi','4220100987654','Asghar Ali','03152214511','Gulistan e Jauhar Block 14'),('23KSE7669','Karachi','4220186254577','Aftab Ahmed','03152214530','House No L1 Gulshan E Iqbal 13D3');
/*!40000 ALTER TABLE `student_contact_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_personal_info`
--

DROP TABLE IF EXISTS `student_personal_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_personal_info` (
  `id` varchar(255) NOT NULL,
  `cnic` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKne99gevvahp5weus1sgbbs0ok` FOREIGN KEY (`id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_personal_info`
--

LOCK TABLES `student_personal_info` WRITE;
/*!40000 ALTER TABLE `student_personal_info` DISABLE KEYS */;
INSERT INTO `student_personal_info` VALUES ('23KSE7140','4220112345676','2002-08-14','anticlash06@gmail.com','Syed Ajwad Ali','male','03082212577'),('23KSE7669','4220166154673','2002-04-24','AbdullahAftab2403@gmail.com','Abdullah Aftab','male','03156942530');
/*!40000 ALTER TABLE `student_personal_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
