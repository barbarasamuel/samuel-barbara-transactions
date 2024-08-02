-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: paymybuddytest
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `bank_account`
--

DROP TABLE IF EXISTS `bank_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `balance` double NOT NULL,
  `id_connection_bank` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKid_connection_bank` (`id_connection_bank`),
  CONSTRAINT `FKid_connection_bank` FOREIGN KEY (`id_connection_bank`) REFERENCES `connection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


LOCK TABLES `bank_account` WRITE;
/*!40000 ALTER TABLE `bank_account` DISABLE KEYS */;
INSERT INTO `bank_account` VALUES (1,'150.00',1),(2,'1000.00',2),(3,'1500.00',3),(4,'200.00',4),(5,'300.00',5),(6,'250.50',6),(7,'140.00',7);
/*!40000 ALTER TABLE `bank_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `connection`
--

DROP TABLE IF EXISTS `connection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `connection` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(75) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `connection`
--

LOCK TABLES `connection` WRITE;
/*!40000 ALTER TABLE `connection` DISABLE KEYS */;
INSERT INTO `connection` VALUES (1,'auguste@email.com','Auguste','motdepa1'),(2,'gerard@email.com','Gerard','motdepa2'),(3,'amandine@email.com','Amandine','motdepa3'),(4,'georgette@email.com','Georgette','motdepa4'),(5,'alceste@email.com','Alceste','motdepa5'),(6,'jimmy@email.com','Jimmy','motdepa6'),(7,'coralie@email.com','Coralie','motdepa7'),(8,'celine@email.com','Celine','motdepa8'),(9,'andre@email.com','Andre','motdepa9'),(10,'annie@email.com','Annie','motdepa1'),(11,'bertrand@myemail.fr','Bertrand','motdepa2'),(12,'anita@monemail.fr','Anita','motdepa3'),(13,'lilian@myemail.com','Lilian','motdepa3'),(14,'rudy@myemail.com','Rudy','motdepa4'),(15,'angelina@email.fr','Angelina','motdepa5');
/*!40000 ALTER TABLE `connection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relation`
--

DROP TABLE IF EXISTS `relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_connection_friend` bigint NOT NULL,
  `id_user` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKid_connection_friend` (`id_connection_friend`),
  KEY `FKid_user` (`id_user`),
  CONSTRAINT `FKid_connection_friend` FOREIGN KEY (`id_connection_friend`) REFERENCES `connection` (`id`),
  CONSTRAINT `FKid_user` FOREIGN KEY (`id_user`) REFERENCES `connection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


LOCK TABLES `relation` WRITE;
/*!40000 ALTER TABLE `relation` DISABLE KEYS */;
INSERT INTO `relation` VALUES (1,2,1),(2,3,2),(3,4,3);
/*!40000 ALTER TABLE `relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `transaction_date` datetime(6) NOT NULL,
  `description` varchar(100) NOT NULL,
  `id_creditor` bigint NOT NULL,
  `id_debtor` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKid_creditor` (`id_creditor`),
  KEY `FKid_debtor` (`id_debtor`),
  CONSTRAINT `FKid_creditor` FOREIGN KEY (`id_creditor`) REFERENCES `connection` (`id`),
  CONSTRAINT `FKid_debtor` FOREIGN KEY (`id_debtor`) REFERENCES `connection` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,'15.00','2024-01-02 17:51:04.789463','Restaurant',1,2);
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;
