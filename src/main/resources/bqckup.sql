-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: web2
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `post_id` int NOT NULL,
  `comment_content` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,'첫 번째 게시글의 첫 번째 댓글입니다.'),(2,1,'첫 번째 게시글의 두 번째 댓글입니다.'),(3,2,'Spring Boot 공부 게시글의 첫 번째 댓글입니다.'),(4,2,'Spring Boot 정말 재밌어요!'),(5,3,'REST API 설계 글 잘 보고 갑니다.'),(6,3,'RESTful은 직관적인 URI가 핵심이죠.'),(7,4,'JPA와 MyBatis 설명 감사합니다.'),(8,4,'둘 다 장단점이 분명하죠.');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(50) COLLATE utf8mb3_unicode_ci NOT NULL,
  `customer_phone` varchar(20) COLLATE utf8mb3_unicode_ci NOT NULL,
  `customer_address` varchar(100) COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `customer_phone` (`customer_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'김철수','010-1234-5678','서울시 강남구'),(2,'이영희','010-2345-6789','부산시 해운대구'),(3,'박화목','010-1222-3333','부산시 금정구'),(4,'홍길동','010-4545-1231','부산시 연제구');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth2_user`
--

DROP TABLE IF EXISTS `oauth2_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_user` (
  `oauth2_user_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `provider` varchar(50) COLLATE utf8mb3_unicode_ci NOT NULL,
  `provider_user_id` varchar(200) COLLATE utf8mb3_unicode_ci NOT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`oauth2_user_id`),
  UNIQUE KEY `uq_provider_provider_user_id` (`provider`,`provider_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_user`
--

LOCK TABLES `oauth2_user` WRITE;
/*!40000 ALTER TABLE `oauth2_user` DISABLE KEYS */;
INSERT INTO `oauth2_user` VALUES (1,2,'google','117957630578445428553',NULL,NULL);
/*!40000 ALTER TABLE `oauth2_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `order_detail_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`order_detail_id`),
  KEY `fk_od_product` (`product_id`),
  KEY `fk_od_order` (`order_id`),
  CONSTRAINT `fk_od_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_od_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `order_details_chk_1` CHECK ((`quantity` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (9,1,1,1),(10,1,2,2),(11,2,3,1),(12,3,4,1);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `order_date` datetime NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `fk_orders_customer` (`customer_id`),
  CONSTRAINT `fk_orders_customer` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,'2024-01-10 00:00:00'),(2,2,'2024-01-12 00:00:00'),(3,1,'2024-01-15 00:00:00'),(4,3,'2024-02-01 00:00:00'),(5,2,'2024-02-02 00:00:00');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `post_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'테스트입니다2','콘텐츠입니다2'),(2,'Spring Boot 공부','Spring Boot를 공부하고 있습니다.'),(3,'REST API 설계','RESTful API 설계 방법에 대해 알아봅시다.'),(4,'JPA vs MyBatis','JPA와 MyBatis의 차이점은 무엇일까요?');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `product_price` int DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'노트북',1500000),(2,'키보드',80000),(3,'마우스',30000),(4,'웹캠',50000),(5,'USB 메모리',15000),(6,'HDMI 케이블',8000),(7,'마이크',45000),(8,'헤드셋',95000),(9,'스피커',65000),(10,'프린터',120000),(11,'스피커',65000),(12,'프린터',120000),(13,'상품A',10000),(14,'상품B',25000),(15,'상품C',5000);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `raw_table`
--

DROP TABLE IF EXISTS `raw_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `raw_table` (
  `order_id` int DEFAULT NULL,
  `customer_name` varchar(50) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `customer_phone` varchar(20) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `customer_address` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `product_names` varchar(200) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `product_prices` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `quantities` varchar(50) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `order_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `raw_table`
--

LOCK TABLES `raw_table` WRITE;
/*!40000 ALTER TABLE `raw_table` DISABLE KEYS */;
INSERT INTO `raw_table` VALUES (1,'김철수','010-1234-5678','서울시 강남구','노트북, 마우스','1500000, 30000','1, 2','2025-11-15 00:00:00'),(2,'이영희','010-2345-6789','부산시 해운대구','키보드','80000','1','2025-11-16 00:00:00'),(3,'김철수','010-1234-5678','서울시 강남구','모니터','400000','1','2025-11-17 00:00:00');
/*!40000 ALTER TABLE `raw_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
  `refresh_token_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `token` varchar(255) COLLATE utf8mb3_unicode_ci NOT NULL,
  `expire_at` datetime NOT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`refresh_token_id`),
  UNIQUE KEY `token_UNIQUE` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
INSERT INTO `refresh_token` VALUES (16,4,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0IiwiaWF0IjoxNzY1MzU0NDIwLCJleHAiOjE3NjU0NDA4MjAsInR5cGUiOiJSRUZSRVNIIn0.6g36quVnRsEDZ044_jmorLSSmP4MyZ6NB8w7PVG4-vg','2025-12-11 17:13:41','2025-12-10 17:13:40','2025-12-10 17:13:40'),(40,2,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNzY3NTY4NDk1LCJleHAiOjE3Njc2NTQ4OTUsInR5cGUiOiJSRUZSRVNIIn0.qHHMRlwsGMAGt_Y-TLj6CkVXdChcf7xUBNBwqAgLgy8','2026-01-06 08:14:56','2026-01-05 08:14:55','2026-01-05 08:14:55'),(41,2,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNzY3NzczMzQxLCJleHAiOjE3Njc4NTk3NDEsInR5cGUiOiJSRUZSRVNIIn0.9SPyTiAWfAw1PRsexRas0NUVXIkHTMIrYDN1z9wrTH0','2026-01-08 17:09:02','2026-01-07 17:09:01','2026-01-07 17:09:01');
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) COLLATE utf8mb3_unicode_ci NOT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'USER','2025-12-03 04:02:05','2025-12-03 04:02:05'),(2,'ADMIN','2025-12-03 04:02:05','2025-12-03 04:02:05');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb3_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `role_id` int NOT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'user1234','$2a$10$I45JhPZ7tXbYxiTJIbxEj.WYrKAZsBlGExyMTNERxsRbyXXxIuFr.','김철수','user@gmail.com',1,'2025-12-03 04:46:53','2025-12-03 04:46:53'),(2,'elikese','$2a$10$A5tnPtOyCYRHcwZlrx0/QuegH/6zCDpwXzDpYcFi8y8d/HfbZBRu2','박화목','elikese@gmail.com',1,'2025-12-03 05:09:58','2025-12-03 05:09:58'),(3,'java2','$2a$10$sJ9fA3iYmpAV9.7JQcE1T.l0sl3xfFYLGl2x/Oj/LjPUkhcZE744m','자바맨','java2@naver.com',1,'2025-12-09 23:13:33','2025-12-09 23:13:33'),(4,'aaaa','$2a$10$OBh2EVyl4.Q/0cIoNx4ISe7YpvR91QPOR4jmKZ499/qizyiVhu9Aa','자바맨','java3@naver.com',1,'2025-12-10 17:13:12','2025-12-10 17:13:12'),(5,'ellikese','$2a$10$W2zuBk1HZDl2xQq/o.t84eio2iAn7fr7s0//NUtqw0OR06S40m1Ka','박화목','qkrghk2@naver.com',1,'2026-01-07 16:59:51','2026-01-07 16:59:51'),(6,'admin','$2a$10$PkOx8U.3nW5p2fs6N729fuOnF0en2W8BdIyRlG7jouBDsmq.txHu.','테스트','test@sample.com',1,'2026-01-07 17:02:15','2026-01-07 17:02:15');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-07 18:26:35
