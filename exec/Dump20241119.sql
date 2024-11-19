-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: k11e202.p.ssafy.io    Database: losszero
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `date_prod`
--

DROP TABLE IF EXISTS `date_prod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `date_prod` (
  `date_prod_id` int NOT NULL AUTO_INCREMENT,
  `line_id` int NOT NULL,
  `sum_normal` bigint NOT NULL DEFAULT '0',
  `sum_defective` bigint NOT NULL DEFAULT '0',
  `sum_reusable` bigint NOT NULL DEFAULT '0',
  `date` date NOT NULL DEFAULT (curdate()),
  PRIMARY KEY (`date_prod_id`),
  KEY `line_id` (`line_id`),
  CONSTRAINT `date_prod_ibfk_1` FOREIGN KEY (`line_id`) REFERENCES `line` (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `date_circumstance`
--

DROP TABLE IF EXISTS `date_circumstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `date_circumstance` (
  `date_circumstance_id` int NOT NULL AUTO_INCREMENT,
  `line_id` int NOT NULL,
  `max_temp` float NOT NULL DEFAULT '0',
  `min_temp` float NOT NULL DEFAULT '0',
  `max_humid` float NOT NULL DEFAULT '0',
  `min_humid` float NOT NULL DEFAULT '0',
  `date` date NOT NULL DEFAULT (curdate()),
  PRIMARY KEY (`date_circumstance_id`),
  KEY `line_id` (`line_id`),
  CONSTRAINT `date_circumstance_ibfk_1` FOREIGN KEY (`line_id`) REFERENCES `line` (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `line`
--

DROP TABLE IF EXISTS `line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `line` (
  `line_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `target_product` int DEFAULT NULL,
  `robot_arms_status` tinyint(1) NOT NULL DEFAULT '0',
  `camera_status` tinyint(1) NOT NULL DEFAULT '0',
  `conveyor_status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`line_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `line_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `operation_time`
--

DROP TABLE IF EXISTS `operation_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operation_time` (
  `operation_time_id` int NOT NULL AUTO_INCREMENT,
  `line_id` int NOT NULL,
  `operation_date` date DEFAULT NULL,
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `accumulated_time` bigint DEFAULT NULL,
  PRIMARY KEY (`operation_time_id`),
  UNIQUE KEY `unique_operation` (`line_id`,`operation_date`),
  CONSTRAINT `operation_time_ibfk_1` FOREIGN KEY (`line_id`) REFERENCES `line` (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `realtime_circumstance`
--

DROP TABLE IF EXISTS `realtime_circumstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `realtime_circumstance` (
  `realtime_circumstance_id` int NOT NULL AUTO_INCREMENT,
  `line_id` int NOT NULL,
  `temperature` float NOT NULL DEFAULT '0',
  `humidity` float NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`realtime_circumstance_id`),
  KEY `line_id` (`line_id`),
  CONSTRAINT `realtime_circumstance_ibfk_1` FOREIGN KEY (`line_id`) REFERENCES `line` (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2963 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `realtime_prod`
--

DROP TABLE IF EXISTS `realtime_prod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `realtime_prod` (
  `realtime_prod_id` int NOT NULL AUTO_INCREMENT,
  `line_id` int NOT NULL,
  `normal` tinyint NOT NULL DEFAULT '0',
  `defective` tinyint NOT NULL DEFAULT '0',
  `reusable` tinyint NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`realtime_prod_id`),
  KEY `line_id` (`line_id`),
  CONSTRAINT `realtime_prod_ibfk_1` FOREIGN KEY (`line_id`) REFERENCES `line` (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=774 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `refresh`
--

DROP TABLE IF EXISTS `refresh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `refresh` varchar(255) NOT NULL,
  `expiration` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `team` varchar(20) DEFAULT NULL,
  `picture` text,
  `role` varchar(50) DEFAULT 'USER',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-19 10:44:59
