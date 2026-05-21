-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: p81diego
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
-- Table structure for table `mascota`
--

DROP TABLE IF EXISTS `mascota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mascota` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero_chip` varchar(50) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `peso` decimal(5,2) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `tipo` enum('perro','gato','otros') NOT NULL,
  `veterinario_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero_chip` (`numero_chip`),
  KEY `veterinario_id` (`veterinario_id`),
  CONSTRAINT `mascota_ibfk_1` FOREIGN KEY (`veterinario_id`) REFERENCES `veterinario` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mascota`
--

LOCK TABLES `mascota` WRITE;
/*!40000 ALTER TABLE `mascota` DISABLE KEYS */;
INSERT INTO `mascota` VALUES (1,'CHIP001','Rex',12.50,'2018-05-20','perro',1),(2,'CHIP002','Michi',4.30,'2020-08-15','gato',2),(3,'CHIP003','Firulais',18.70,'2016-11-03','perro',1),(4,'CHIP004','Pelusa',3.20,'2019-02-10','gato',3),(5,'CHIP005','Bobby',22.10,'2015-06-25','perro',2);
/*!40000 ALTER TABLE `mascota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `veterinario`
--

DROP TABLE IF EXISTS `veterinario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `veterinario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nif` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nif` (`nif`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veterinario`
--

LOCK TABLES `veterinario` WRITE;
/*!40000 ALTER TABLE `veterinario` DISABLE KEYS */;
INSERT INTO `veterinario` VALUES (1,'12345678A','Dr. Diego Si','Calle Principal 123','965432312','diego@email.com'),(2,'87654321B','Dra. Ana No','Calle ahi 22','987563524','ana@email.com'),(3,'45678912C','Dr. Carlos Ramírez','Plaza Mayor 789','912345678','carlos.ramirez@email.com');
/*!40000 ALTER TABLE `veterinario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-21 17:31:10
