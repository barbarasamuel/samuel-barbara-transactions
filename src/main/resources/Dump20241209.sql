-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: paymybuddy
-- ------------------------------------------------------
-- Server version	8.0.39

--
-- Table structure for table `bank_account`
--

DROP TABLE IF EXISTS `bank_account`;
CREATE TABLE `bank_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `balance` double NOT NULL,
  `id_connection_bank` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKid_connection_bank` (`id_connection_bank`),
  CONSTRAINT `FKid_connection_bank` FOREIGN KEY (`id_connection_bank`) REFERENCES `connection` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `bank_account`
--

LOCK TABLES `bank_account` WRITE; 
INSERT INTO `bank_account` VALUES (1,175,1),(2,869.37,2),(3,1562,3),(4,200,4),(5,310,5),(6,251.5,6),(7,142,7),(8,50,28),(9,65,31),(10,50,50),(23,483.82,2),(24,57,3),(25,46.99,54),(26,90,55),(31,53,54),(44,50,83); 
UNLOCK TABLES;

--
-- Table structure for table `connection`
--

DROP TABLE IF EXISTS `connection`;
CREATE TABLE `connection` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(75) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `UK50pd0dwqkarwp7udn0yeohjo` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci; 

--
-- Dumping data for table `connection`
--

LOCK TABLES `connection` WRITE; 
INSERT INTO `connection` VALUES (1,'auguste@email.com','Auguste','Mo@depa1'),(2,'gerard@email.fr','Gerard','Mo@depa2'),(3,'amandine@email.com','Amandine','Mo@depa3'),(4,'georgette@email.com','Georgette','Mo@depa4'),(5,'alceste@email.com','Alceste','Mo@depa5'),(6,'jimmy@email.com','Jimmy','Mo@depa6'),(7,'coralie@email.com','Coralie','Mo@depa7'),(28,'helene@monemail.fr','Hélène','Mo@depa3'),(31,'herve@monemail.fr','Hervé','Mo@depa3'),(50,'test1@monemail.fr','test','Mo@test1'),(54,'serge@monemail.fr','Serge','Mo@depa2'),(55,'damien@monemail.fr','Damien','Mo@depa2'),(83,'mathieu@email.com','Mathieu','Mo@depa2'); 

UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `message` varchar(400) NOT NULL,
  `id_sender` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKid_sender` (`id_sender`),
  CONSTRAINT `FKid_sender` FOREIGN KEY (`id_sender`) REFERENCES `connection` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci; 

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE; 
INSERT INTO `contact` VALUES (9,'Ceci est un essai.',2),(10,'Bonjour',2),(11,'New message',2),(17,'Test du 22 octobre',2),(19,'Message de prouction',54); 
UNLOCK TABLES;

--
-- Table structure for table `relation`
--

DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_connection_friend` bigint NOT NULL,
  `id_user` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKid_connection_friend` (`id_connection_friend`),
  KEY `FKid_user` (`id_user`),
  CONSTRAINT `FKid_connection_friend` FOREIGN KEY (`id_connection_friend`) REFERENCES `connection` (`id`),
  CONSTRAINT `FKid_user` FOREIGN KEY (`id_user`) REFERENCES `connection` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci; 

--
-- Dumping data for table `relation`
--

LOCK TABLES `relation` WRITE;
INSERT INTO `relation` VALUES (1,2,1),(2,3,2),(3,4,3),(4,1,2),(5,4,2),(12,2,2),(13,1,54),(17,1,55),(28,54,54);
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
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
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
INSERT INTO `transactions` VALUES (1,15,'2024-01-02 17:51:04.789463','Restaurant',1,2),(11,10,'2024-02-14 19:30:01.800003','Cinema',3,2),(12,25,'2024-03-21 20:54:14.678946','Anniversaire',4,2),(13,100,'2024-04-02 10:43:04.789463','Travaux',4,2),(28,25,'2024-08-23 10:35:27.631000','Anniversaire',1,2),(29,40,'2024-09-17 11:57:20.690000','Restaurant',3,2),(34,12,'2024-09-17 15:49:49.668000','Coque pour mobile',3,2),(36,20,'2024-09-17 21:21:54.933000','Recharge',2,2),(37,10,'2024-09-23 21:19:28.860000','Cinema',3,2),(41,12,'2024-10-22 16:30:18.199000','Restaurant',3,2),(42,24,'2024-10-22 16:59:14.932000','Restaurant',3,2),(49,3,'2024-11-05 15:10:54.201000','Test',54,54);
UNLOCK TABLES;


-- Dump completed on 2024-12-09 17:29:14
