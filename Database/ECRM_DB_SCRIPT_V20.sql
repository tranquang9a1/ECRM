-- MySQL dump 10.13  Distrib 5.6.24, for Win32 (x86)
--
-- Host: localhost    Database: ecrm
-- ------------------------------------------------------
-- Server version	5.6.25-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sysdiagrams`
--

DROP TABLE IF EXISTS `sysdiagrams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sysdiagrams` (
  `name` varchar(160) NOT NULL,
  `principal_id` int(11) NOT NULL,
  `diagram_id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `definition` longblob,
  PRIMARY KEY (`diagram_id`),
  UNIQUE KEY `UK_principal_name` (`principal_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sysdiagrams`
--

LOCK TABLES `sysdiagrams` WRITE;
/*!40000 ALTER TABLE `sysdiagrams` DISABLE KEYS */;
/*!40000 ALTER TABLE `sysdiagrams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblclassroom`
--

DROP TABLE IF EXISTS `tblclassroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblclassroom` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `CreateTime` datetime(6) NOT NULL,
  `IsDelete` tinyint(1) DEFAULT NULL,
  `UpdateTime` datetime(6) DEFAULT NULL,
  `IsAllInformation` tinyint(1) DEFAULT NULL,
  `DamagedLevel` int(11) DEFAULT NULL,
  `RoomTypeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_tblClassroom_tblRoomType2` (`RoomTypeId`),
  KEY `FK1F2E435B2E33001` (`RoomTypeId`),
  CONSTRAINT `FK1F2E435B2E33001` FOREIGN KEY (`RoomTypeId`) REFERENCES `tblroomtype` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1086 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblclassroom`
--

LOCK TABLES `tblclassroom` WRITE;
/*!40000 ALTER TABLE `tblclassroom` DISABLE KEYS */;
INSERT INTO `tblclassroom` VALUES (1049,'101','2015-07-01 12:45:34.000000',0,'2015-08-14 13:36:18.417000',1,100,3),(1050,'213','2015-07-01 12:45:50.000000',0,'2015-08-11 07:04:00.500000',1,0,3),(1051,'310','2015-07-01 12:48:22.000000',0,'2015-07-25 17:02:22.706000',1,0,3),(1052,'312','2015-07-01 12:48:43.000000',0,'2015-08-10 07:26:01.223000',1,0,3),(1053,'403','2015-07-01 12:49:00.000000',0,'2015-07-01 12:49:00.000000',1,0,3),(1054,'407','2015-07-01 12:49:19.000000',0,'2015-07-01 12:49:19.000000',1,0,3),(1055,'102','2015-07-13 13:18:50.869000',0,'2015-08-18 12:21:35.770000',1,0,3),(1056,'202','2015-07-06 15:45:43.322000',0,'2015-08-11 07:01:00.593000',1,0,3),(1057,'203','2015-07-07 06:05:33.362000',0,'2015-07-07 06:05:33.362000',1,0,3),(1058,'103','2015-07-23 22:09:21.000000',0,'2015-07-23 22:09:21.000000',1,0,3),(1059,'113','2015-07-23 22:09:49.243000',0,'2015-08-18 12:22:48.050000',1,0,3),(1060,'115','2015-07-23 22:10:10.055000',0,'2015-08-14 13:29:32.965000',1,0,3),(1061,'209','2015-07-23 22:10:20.964000',0,'2015-07-23 22:10:20.964000',1,0,3),(1062,'306','2015-07-23 22:10:53.242000',0,'2015-07-23 22:10:53.242000',1,0,3),(1063,'308','2015-07-23 22:11:05.818000',0,'2015-08-10 07:04:02.647000',1,0,3),(1064,'307','2015-07-23 22:11:19.243000',0,'2015-08-10 07:25:00.832000',1,0,3),(1065,'313','2015-07-23 22:11:46.169000',0,'2015-08-10 07:28:04.570000',1,0,3),(1066,'410','2015-07-23 22:11:57.403000',0,'2015-07-23 22:11:57.403000',1,0,3),(1067,'405','2015-07-23 22:12:17.891000',0,'2015-07-23 22:12:17.891000',1,0,3),(1068,'401','2015-07-23 22:12:34.867000',0,'2015-07-23 22:12:34.867000',1,0,3),(1069,'412','2015-07-23 22:12:47.794000',0,'2015-08-14 11:37:46.823000',1,0,3),(1070,'414','2015-07-23 22:13:00.067000',0,'2015-07-23 22:13:00.067000',1,0,3),(1071,'504','2015-07-23 22:13:13.278000',0,'2015-07-23 22:13:13.278000',1,0,3),(1072,'508','2015-07-23 22:13:25.547000',0,'2015-07-23 22:13:25.547000',1,0,3),(1073,'234234','2015-07-25 16:12:31.376000',1,'2015-07-25 16:12:31.376000',1,0,3),(1074,'1','2015-07-26 13:03:53.521000',1,'2015-07-31 05:58:10.921000',1,0,5),(1076,'512','2015-07-31 23:05:53.496000',0,'2015-08-14 13:39:15.965000',1,0,5),(1077,'513','2015-08-01 01:19:05.383000',0,'2015-08-14 13:39:42.396000',1,0,5),(1078,'556','2015-08-01 01:32:37.129000',0,'2015-08-01 01:33:10.800000',1,0,7),(1079,'600','2015-08-02 13:30:57.648000',0,'2015-08-08 00:28:44.704000',1,0,15),(1080,'601','2015-08-02 13:41:20.238000',0,'2015-08-02 13:41:20.238000',1,0,3),(1081,'511','2015-08-08 00:29:05.529000',0,'2015-08-14 13:37:28.464000',1,0,15),(1082,'514','2015-08-08 00:31:04.081000',0,'2015-08-08 00:31:04.081000',0,0,3),(1083,'700','2015-08-09 23:37:59.512000',0,'2015-08-09 23:38:11.010000',1,0,6),(1084,'204','2015-08-11 13:44:18.817000',0,'2015-08-14 13:38:27.605000',1,0,12),(1085,'205','2015-08-11 13:47:06.429000',0,'2015-08-14 13:38:31.711000',1,0,15);
/*!40000 ALTER TABLE `tblclassroom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblequipment`
--

DROP TABLE IF EXISTS `tblequipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblequipment` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `CategoryId` int(11) NOT NULL,
  `ClassroomId` int(11) DEFAULT NULL,
  `Position` char(10) DEFAULT NULL,
  `Status` tinyint(1) NOT NULL,
  `TimeRemain` double DEFAULT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `SerialNumber` varchar(255) DEFAULT NULL,
  `IsDelete` tinyint(1) DEFAULT NULL,
  `UsingTime` double DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_tblEquipment_tblEquipmentCategory` (`CategoryId`),
  KEY `FK_tblEquipment_tblClassroom` (`ClassroomId`),
  CONSTRAINT `FK_tblEquipment_tblClassroom` FOREIGN KEY (`ClassroomId`) REFERENCES `tblclassroom` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_tblEquipment_tblEquipmentCategory` FOREIGN KEY (`CategoryId`) REFERENCES `tblequipmentcategory` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=501 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblequipment`
--

LOCK TABLES `tblequipment` WRITE;
/*!40000 ALTER TABLE `tblequipment` DISABLE KEYS */;
INSERT INTO `tblequipment` VALUES (211,1,1049,'[1]',1,58.5,'Toshiba','2131231231',0,3000),(212,3,1049,'[3]',1,0,'Hitachi','4564564645',0,0),(213,3,1049,'[3]',1,0,'Hitachi','8798798798',0,0),(214,2,1049,'[2]',1,0,'Samsung','8767678678',0,0),(215,1,1050,'[1]',1,3000,'Toshiba','7857858758',0,3000),(216,3,1050,'[3]',1,0,'Hitachi','7487484784',0,0),(217,3,1050,'[3]',1,0,'Hitachi','5494949959',0,0),(218,1,1051,'[1]',1,2798.5,'Toshiba','2166122126',0,3000),(219,3,1051,'[3]',1,0,'Hitachi','5435434343',0,0),(220,3,1055,'[3]',1,0,'Hitachi','5616566156',0,0),(221,2,1055,'[2]',1,0,'Samsung','6876876242',0,0),(222,1,1052,'[1]',1,2763.5,'Toshiba','2192192919',0,3000),(223,3,1052,'[3]',1,0,'Hitachi','2818218287',0,0),(224,3,1052,'[3]',1,0,'Hitachi','1731737137',0,0),(225,1,1053,'[1]',1,2914.5,'Toshiba','9349493943',0,3000),(226,3,1053,'[3]',1,0,'Hitachi','6346437437',0,0),(227,3,1053,'[3]',1,0,'Hitachi','2182181288',0,0),(228,2,1053,'[2]',1,0,'Samsung','3163163637',0,0),(229,1,1054,'[1]',1,2799.5,'Toshiba','3783735731',0,3000),(230,3,1054,'[3]',1,0,'Hitachi','0212012334',0,0),(231,3,1054,'[3]',1,0,'Hitachi','5064506504',0,0),(232,2,1054,'[2]',1,0,'Samsung','3700738078',0,0),(261,1,1055,'[1]',1,2968.5,'Sony','98392385123',0,3000),(262,3,1080,'[3]',1,0,'DAIKIN','1239382454',0,0),(263,3,1080,'[3]',1,0,'DAIKIN','1231924872',0,0),(264,2,1051,'[2]',1,0,'SAMSUNG','3423423545',0,0),(280,1,1056,'[1]',1,2968.5,'SamSung','97238460123',0,3000),(281,3,1056,'[3]',1,0,'DAIKIN','37412030822',0,0),(282,3,1056,'[3]',1,0,'DAIKIN','34597233474',0,0),(283,2,1056,'[2]',1,0,'SONY','32445701283',0,0),(289,1,1057,'[1]',1,3000,'Sony','30487345123',0,3000),(290,3,1057,'[3]',1,0,'DAIKIN','97980982131',0,0),(291,3,1057,'[3]',1,0,'DAIKIN','12309845666',0,0),(292,2,1057,'[2]',1,0,'LG','97454802938',0,0),(334,1,1058,'[1]',1,2996,'PROJECTOR','23325314234',0,3000),(335,3,1058,'[3]',1,0,'AIR','32545653756',0,0),(336,3,1058,'[3]',1,0,'AIR','23434241231',0,0),(337,2,1058,'[2]',1,0,'TIVI','43524624632',0,0),(338,1,1059,'[1]',1,2958.5,'PROJECTOR','34546345654',0,3000),(339,3,1059,'[3]',1,0,'AIR','12312334345',0,0),(340,3,1059,'[3]',1,0,'AIR','5464879785',0,0),(341,2,1059,'[2]',1,0,'TIVI','23434645654',0,0),(342,1,1060,'[1]',1,2982,'PROJECTOR','354632343',0,3000),(343,3,1060,'[3]',1,0,'AIR','45635546546',0,0),(344,3,1060,'[3]',1,0,'AIR','1231234556',0,0),(345,2,1060,'[2]',1,0,'TIVI','2341231436',0,0),(346,1,1061,'[1]',1,2891.5,'PROJECTOR','4575687967',0,3000),(347,3,1061,'[3]',1,0,'AIR','1231232353',0,0),(348,3,1061,'[3]',1,0,'AIR','89676574535',0,0),(349,2,1061,'[2]',1,0,'TIVI','5476583452',0,0),(350,1,1062,'[1]',1,2868.5,'PROJECTOR','2343546356',0,3000),(351,3,1062,'[3]',1,0,'AIR','234346765722',0,0),(352,3,1062,'[3]',1,0,'AIR','32434673474',0,0),(353,2,1062,'[2]',1,0,'TIVI','76867956754',0,0),(354,1,1063,'[1]',1,2878,'PROJECTOR','3224235457',0,3000),(355,3,1063,'[3]',1,0,'AIR','45675683234',0,0),(356,3,1063,'[3]',1,0,'AIR','3454573452',0,0),(357,2,1063,'[2]',1,0,'TIVI','2354657658',0,0),(358,1,1064,'[1]',1,2966.5,'PROJECTOR','5685435234',0,3000),(359,3,1064,'[3]',1,0,'AIR','457686557645',0,0),(360,3,1064,'[3]',1,0,'AIR','576584534',0,0),(361,2,1064,'[2]',1,0,'TIVI','464578685456',0,0),(362,1,1065,'[1]',1,2933,'PROJECTOR','23424534646',0,3000),(363,3,1065,'[3]',1,0,'AIR','345457443523',0,0),(364,3,1065,'[3]',1,0,'AIR','234264563345',0,0),(365,2,1065,'[2]',1,0,'TIVI','2343457567324',0,0),(366,1,1066,'[1]',1,2923.5,'PROJECTOR','2345346435',0,3000),(367,3,1066,'[3]',1,0,'AIR','2353465456',0,0),(368,3,1066,'[3]',1,0,'AIR','678678565',0,0),(369,2,1066,'[2]',1,0,'TIVI','78089068755',0,0),(370,1,1067,'[1]',1,2936.5,'PROJECTOR','657578679',0,3000),(371,3,1067,'[3]',1,0,'AIR','7890786875',0,0),(372,3,1067,'[3]',1,0,'AIR','566456878',0,0),(373,2,1067,'[2]',1,0,'TIVI','45768978976',0,0),(374,1,1068,'[1]',1,2928.5,'PROJECTOR','57686979856',0,3000),(375,3,1068,'[3]',1,0,'AIR','53454356744',0,0),(376,3,1068,'[3]',1,0,'AIR','34423567546',0,0),(377,2,1068,'[2]',1,0,'TIVI','23454576433',0,0),(378,1,1069,'[1]',1,2936.5,'PROJECTOR','7457667867',0,3000),(379,3,1069,'[3]',1,0,'AIR','23421454545',0,0),(380,3,1069,'[3]',1,0,'AIR','1242565687',0,0),(381,2,1069,'[2]',1,0,'TIVI','58564643457',0,0),(382,1,1070,'[1]',1,2898,'PROJECTOR','78796565534',0,3000),(383,3,1070,'[3]',1,0,'AIR','35346567885',0,0),(384,3,1070,'[3]',1,0,'AIR','23434657736',0,0),(385,2,1070,'[2]',1,0,'TIVI','21434575464',0,0),(386,1,1071,'[1]',1,2898,'PROJECTOR','89968575456',0,3000),(387,3,1071,'[3]',1,0,'AIR','4352346577',0,0),(388,3,1071,'[3]',1,0,'AIR','2456457567',0,0),(389,2,1071,'[2]',1,0,'TIVI','79867465645',0,0),(390,1,1072,'[1]',1,2929,'PROJECTOR','457568688676',0,3000),(391,3,1072,'[3]',1,0,'AIR','3454575674',0,0),(392,3,1072,'[3]',1,0,'AIR','2454675568',0,0),(393,2,1072,'[2]',1,0,'TIVI','45688796575',0,0),(403,4,1069,'[4]',1,NULL,NULL,NULL,0,NULL),(404,6,1069,'[6]',1,NULL,NULL,NULL,0,NULL),(405,1,1062,'[1]',1,NULL,NULL,NULL,0,3000),(406,3,1062,'[3]',1,NULL,NULL,NULL,0,NULL),(407,1,1063,'[1]',1,NULL,NULL,NULL,0,3000),(408,4,1063,'[4]',1,NULL,NULL,NULL,0,NULL),(409,6,1063,'[6]',1,NULL,NULL,NULL,0,NULL),(410,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(411,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(412,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(413,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(415,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(416,3,NULL,'[3]',1,0,NULL,NULL,0,0),(417,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(418,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(419,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(420,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(421,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(422,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(423,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(424,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(425,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(426,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(427,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(428,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(429,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(430,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(431,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(432,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(433,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(434,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(435,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(436,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(437,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(438,1,1080,NULL,1,NULL,'Projector','22222222222',0,3000),(439,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(440,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(441,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(442,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(443,1,1059,'[1]',1,NULL,NULL,NULL,0,3000),(444,3,1059,'[3]',1,NULL,NULL,NULL,0,NULL),(446,6,1049,'[6]',1,NULL,NULL,NULL,0,NULL),(447,1,1052,'[1]',1,NULL,NULL,NULL,0,3000),(448,4,1052,'[4]',1,NULL,NULL,NULL,0,NULL),(449,1,1053,'[1]',1,NULL,NULL,NULL,0,3000),(450,4,1053,'[4]',1,NULL,NULL,NULL,0,NULL),(451,1,1054,'[1]',1,NULL,NULL,NULL,0,3000),(452,4,1054,'[4]',1,NULL,NULL,NULL,0,NULL),(453,1,1050,'[1]',1,NULL,NULL,NULL,0,3000),(454,4,1050,'[4]',1,NULL,NULL,NULL,0,NULL),(455,1,1055,'[1]',1,NULL,NULL,NULL,0,3000),(456,4,1055,'[4]',1,NULL,NULL,NULL,0,NULL),(457,1,1056,'[1]',1,NULL,NULL,NULL,0,3000),(458,4,1056,'[4]',1,NULL,NULL,NULL,0,NULL),(459,1,1057,'[1]',1,NULL,NULL,NULL,0,3000),(460,4,1057,'[4]',1,NULL,NULL,NULL,0,NULL),(461,7,1051,'[1,1]',1,NULL,NULL,NULL,0,NULL),(462,1,NULL,'[1]',1,0,NULL,NULL,0,0),(463,5,1069,'[5]',1,NULL,NULL,NULL,NULL,NULL),(464,3,1060,'[3]',1,NULL,NULL,NULL,NULL,NULL),(465,1,1060,'[1]',1,NULL,NULL,NULL,NULL,NULL),(466,1,1079,'[1]',1,3333,'schweinsteiger','1231232131332',0,3333),(467,1,1078,'[1]',1,3000,'rưerqwe','12313435234',0,3000),(481,5,1063,'[5]',1,NULL,NULL,NULL,NULL,NULL),(482,3,1064,'[3]',1,NULL,NULL,NULL,NULL,NULL),(483,1,1064,'[1]',1,NULL,NULL,NULL,NULL,NULL),(484,3,1052,'[3]',1,NULL,NULL,NULL,NULL,NULL),(485,3,1065,'[3]',1,NULL,NULL,NULL,NULL,NULL),(486,1,1065,'[1]',1,NULL,NULL,NULL,NULL,NULL),(487,3,1056,'[3]',1,NULL,NULL,NULL,NULL,NULL),(488,1,1081,'[1]',1,3000,'cc','12312342442',0,3000),(489,1,1071,'[1]',1,NULL,NULL,NULL,NULL,NULL),(490,1,1069,'[1]',1,NULL,NULL,NULL,NULL,NULL),(491,3,1049,'[3]',1,NULL,NULL,NULL,NULL,NULL),(492,1,1049,'[1]',0,NULL,NULL,NULL,NULL,NULL),(493,1,1085,'[1]',1,0,'Projector2015','21435345213',0,0),(494,1,1076,'[1]',1,3000,'Projector2015','57568435234',0,3000),(495,1,1077,'[1]',1,3000,'Projector2015','234657345224',0,3000),(496,1,1082,'[1]',1,3000,'Projector2015','325349793242',0,3000),(497,1,NULL,'[1]',1,3000,'Toshiba','4353452342352',0,3000),(498,1,NULL,'[1]',1,0,'Toshiba','3249203852',0,0),(499,3,1055,'[3]',1,NULL,NULL,NULL,NULL,NULL),(500,5,1049,'[5]',0,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tblequipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblequipmentcategory`
--

DROP TABLE IF EXISTS `tblequipmentcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblequipmentcategory` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `IsManaged` tinyint(1) DEFAULT NULL,
  `ImageUrl` varchar(100) DEFAULT NULL,
  `IsDelete` tinyint(1) DEFAULT NULL,
  `UpdateTime` bigint(8) DEFAULT NULL,
  `ExpiredTime` double DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblequipmentcategory`
--

LOCK TABLES `tblequipmentcategory` WRITE;
/*!40000 ALTER TABLE `tblequipmentcategory` DISABLE KEYS */;
INSERT INTO `tblequipmentcategory` VALUES (1,'Máy chiếu',1,'projector.png',0,1438291042460,50),(2,'Tivi',1,'widescreentv.png',0,1438291042460,0),(3,'Máy lạnh',1,'air512.png',0,1438291042460,0),(4,'Máy quạt',0,'fan512.png',0,1438291042460,0),(5,'Loa',0,'003386glossyblackiconmediamusicspeaker.png',0,1438291042460,0),(6,'Bóng đèn',0,'lightbulb7icon270x250.png',0,1438291042460,0),(7,'Bàn',0,'HouseholdTableicon.png',0,1438291042460,0),(8,'Ghế',0,'chairoffice512.png',0,1438291042460,0),(12,'Empty',1,'lollipop.png',0,1438291042460,0),(13,'test',1,'LogoNew.PNG',0,1439272579595,50),(14,'test2',0,'lollipop.png',0,1439444623947,0);
/*!40000 ALTER TABLE `tblequipmentcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblequipmentquantity`
--

DROP TABLE IF EXISTS `tblequipmentquantity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblequipmentquantity` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `RoomTypeId` int(11) NOT NULL,
  `EquipmentCategoryId` int(11) NOT NULL,
  `Quantity` int(11) DEFAULT NULL,
  `Priority` int(11) DEFAULT NULL,
  `IsDelete` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`Id`,`RoomTypeId`,`EquipmentCategoryId`),
  KEY `FKEA4EA83B67FE6D5B` (`EquipmentCategoryId`),
  KEY `FKEA4EA83B975A2EAF` (`RoomTypeId`),
  KEY `FKDE89741B67FE6D5B` (`EquipmentCategoryId`),
  KEY `FKDE89741B975A2EAF` (`RoomTypeId`),
  CONSTRAINT `FKDE89741B67FE6D5B` FOREIGN KEY (`EquipmentCategoryId`) REFERENCES `tblequipmentcategory` (`Id`),
  CONSTRAINT `FKDE89741B975A2EAF` FOREIGN KEY (`RoomTypeId`) REFERENCES `tblroomtype` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblequipmentquantity`
--

LOCK TABLES `tblequipmentquantity` WRITE;
/*!40000 ALTER TABLE `tblequipmentquantity` DISABLE KEYS */;
INSERT INTO `tblequipmentquantity` VALUES (1,3,1,1,3,0),(2,3,3,2,2,0),(3,3,5,1,1,0),(4,3,6,1,3,0),(5,7,1,1,1,0),(6,8,1,1,3,1),(7,8,3,2,3,1),(8,8,5,1,1,1),(9,8,6,1,3,1),(10,8,1,1,3,0),(11,8,3,2,3,0),(12,9,1,1,3,1),(13,9,3,2,3,1),(14,9,5,1,1,1),(15,9,6,1,3,1),(16,5,1,1,3,0),(17,9,1,1,3,1),(18,10,1,1,3,0),(19,10,3,2,3,0),(20,10,5,1,1,0),(21,10,6,1,3,0),(22,9,1,1,1,1),(23,9,1,2,1,0),(24,14,1,1,1,1),(25,14,1,1,1,0),(26,14,3,2,1,0),(27,15,1,1,3,1),(28,15,3,2,1,1),(29,15,4,1,1,1),(30,15,1,1,3,0),(31,16,1,1,1,0),(32,16,2,1,1,0),(33,16,3,1,1,0);
/*!40000 ALTER TABLE `tblequipmentquantity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblmessage`
--

DROP TABLE IF EXISTS `tblmessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblmessage` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(30) NOT NULL,
  `Body` varchar(255) DEFAULT NULL,
  `IsRead` bit(1) DEFAULT NULL,
  `Phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FKE29C46693EAC153F` (`Username`),
  CONSTRAINT `FKE29C46693EAC153F` FOREIGN KEY (`Username`) REFERENCES `tbluser` (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblmessage`
--

LOCK TABLES `tblmessage` WRITE;
/*!40000 ALTER TABLE `tblmessage` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblmessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblnotification`
--

DROP TABLE IF EXISTS `tblnotification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblnotification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classroomId` int(11) NOT NULL,
  `message` longtext COLLATE utf8_unicode_ci,
  `createTime` datetime NOT NULL,
  `redirectLink` mediumtext COLLATE utf8_unicode_ci,
  `messageType` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `classroomId_idx` (`classroomId`),
  CONSTRAINT `tblnotification_tblclassroom` FOREIGN KEY (`classroomId`) REFERENCES `tblclassroom` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblnotification`
--

LOCK TABLES `tblnotification` WRITE;
/*!40000 ALTER TABLE `tblnotification` DISABLE KEYS */;
INSERT INTO `tblnotification` VALUES (9,1049,'KhanhKT báo cáo hư hại phòng 101','2015-08-22 18:12:45','/bao-cao/hu-hai?phong=1049',1,0);
/*!40000 ALTER TABLE `tblnotification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblreport`
--

DROP TABLE IF EXISTS `tblreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblreport` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(30) NOT NULL,
  `ClassRoomId` int(11) NOT NULL,
  `CreateTime` datetime(6) NOT NULL,
  `Evaluate` longtext NOT NULL,
  `Status` int(11) NOT NULL,
  `ChangedRoom` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_tblReport_tblUser1` (`Username`),
  KEY `FK_tblReport_tblClassroom` (`ClassRoomId`),
  CONSTRAINT `FK_tblReport_tblClassroom` FOREIGN KEY (`ClassRoomId`) REFERENCES `tblclassroom` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_tblReport_tblUser1` FOREIGN KEY (`Username`) REFERENCES `tbluser` (`Username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblreport`
--

LOCK TABLES `tblreport` WRITE;
/*!40000 ALTER TABLE `tblreport` DISABLE KEYS */;
INSERT INTO `tblreport` VALUES (34,'KhanhKT',1060,'2015-08-14 13:25:45.237000','Vẫn dạy được',3,'101'),(35,'KhanhKT',1049,'2015-08-14 13:31:22.215000','Vẫn dạy được',3,'102'),(36,'KhanhKT',1055,'2015-08-14 15:06:41.971000','Phải đổi phòng',3,'101'),(37,'HoangNT',1059,'2015-08-17 08:45:59.301000','Vẫn dạy được',3,'101'),(38,'KhanhKT',1049,'2015-08-22 18:12:43.039000','Không cần đổi phòng',1,NULL),(39,'ChungKTT',1049,'2015-08-22 18:13:37.492000','Không cần đổi phòng',1,NULL);
/*!40000 ALTER TABLE `tblreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblreportdetail`
--

DROP TABLE IF EXISTS `tblreportdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblreportdetail` (
  `EquipmentId` int(11) NOT NULL,
  `ReportId` int(11) NOT NULL,
  `DamagedLevel` varchar(250) NOT NULL,
  `Position` char(10) DEFAULT NULL,
  `Description` longtext,
  `Solution` longtext,
  `ResolveTime` datetime(6) DEFAULT NULL,
  `Status` tinyint(1) NOT NULL,
  PRIMARY KEY (`EquipmentId`,`ReportId`),
  KEY `FK_tblReportDetail_tblReport` (`ReportId`),
  CONSTRAINT `FK_tblReportDetail_tblEquipment1` FOREIGN KEY (`EquipmentId`) REFERENCES `tblequipment` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_tblReportDetail_tblReport` FOREIGN KEY (`ReportId`) REFERENCES `tblreport` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblreportdetail`
--

LOCK TABLES `tblreportdetail` WRITE;
/*!40000 ALTER TABLE `tblreportdetail` DISABLE KEYS */;
INSERT INTO `tblreportdetail` VALUES (443,37,'1','[1]','Không sử dụng được',NULL,NULL,1),(455,36,'1','[1]','Không sử dụng được',NULL,NULL,1),(464,34,'1','[3]','Không sử dụng được',NULL,NULL,1),(465,34,'1','[1]','Không sử dụng được',NULL,NULL,1),(491,35,'1','[3]','Không sử dụng được',NULL,NULL,1),(492,35,'1','[1]','Không sử dụng được',NULL,NULL,1),(492,38,'1','[1]','mnmn',NULL,NULL,0),(492,39,'1','[1]','vbv',NULL,NULL,0),(499,36,'1','[3]','Không sử dụng được',NULL,NULL,1),(500,39,'3','[5]','',NULL,NULL,0);
/*!40000 ALTER TABLE `tblreportdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblrole`
--

DROP TABLE IF EXISTS `tblrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblrole` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblrole`
--

LOCK TABLES `tblrole` WRITE;
/*!40000 ALTER TABLE `tblrole` DISABLE KEYS */;
INSERT INTO `tblrole` VALUES (1,'Admin'),(2,'Staff'),(3,'Teacher');
/*!40000 ALTER TABLE `tblrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblroomtype`
--

DROP TABLE IF EXISTS `tblroomtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblroomtype` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Slots` int(11) NOT NULL,
  `VerticalRows` int(11) NOT NULL,
  `HorizontalRows` varchar(45) NOT NULL,
  `NumberOfSlotsEachHRows` varchar(50) NOT NULL,
  `CreateTime` datetime NOT NULL,
  `IsDelete` tinyint(1) DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblroomtype`
--

LOCK TABLES `tblroomtype` WRITE;
/*!40000 ALTER TABLE `tblroomtype` DISABLE KEYS */;
INSERT INTO `tblroomtype` VALUES (3,'Loại 25 chỗ',25,2,'5-5','3-2','2015-07-29 07:31:03',0,'2015-07-29 07:31:03'),(4,'Loại 30 chỗ',30,2,'5-5','3-3','2015-07-29 07:35:48',0,'2015-07-29 07:35:48'),(5,'test',2,2,'1-1','1-1','2015-07-30 06:56:27',0,'2015-07-31 06:01:16'),(6,'test2',50,2,'5-5','5-5','2015-07-30 09:12:00',0,'2015-07-30 09:12:00'),(7,'test3',6,2,'5-1','1-1','2015-07-30 09:15:58',0,'2015-07-30 14:02:31'),(8,'test 4',25,2,'5-5','3-2','2015-07-30 17:06:21',0,'2015-07-30 17:38:16'),(9,'Test5',6,2,'5-1','1-1','2015-07-31 02:12:33',0,'2015-07-31 23:05:06'),(10,'Chi',25,2,'5-5','3-2','2015-07-31 10:27:16',0,'2015-07-31 10:27:16'),(11,'test6',2,2,'1-1','1-1','2015-07-31 23:05:24',0,'2015-07-31 23:05:24'),(12,'test67',11,2,'1-1','10-1','2015-08-01 01:35:31',0,'2015-08-02 13:57:18'),(13,'tes67',30,4,'9-1-1-1','3-1-1-1','2015-08-02 13:45:20',0,'2015-08-02 13:45:20'),(14,'aaaa',2,2,'1-1','1-1','2015-08-05 10:08:56',0,'2015-08-06 23:08:59'),(15,'zxzc',3,3,'1-1-1','1-1-1','2015-08-07 14:46:00',0,'2015-08-08 00:27:26'),(16,',mn',3,3,'1-1-1','1-1-1','2015-08-14 12:56:13',0,'2015-08-14 12:56:13');
/*!40000 ALTER TABLE `tblroomtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblschedule`
--

DROP TABLE IF EXISTS `tblschedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblschedule` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(30) NOT NULL,
  `ClassroomId` int(11) NOT NULL,
  `NumberOfStudents` int(11) NOT NULL,
  `Note` varchar(250) DEFAULT NULL,
  `Date` date NOT NULL,
  `IsActive` tinyint(1) NOT NULL,
  `ScheduleConfigId` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_tblSchedule_tblUser1` (`Username`),
  KEY `FK_tblSchedule_tblClassroom` (`ClassroomId`),
  KEY `FK_tblSchedule_tblScheduleConfig_idx` (`ScheduleConfigId`),
  CONSTRAINT `FK_tblSchedule_tblClassroom` FOREIGN KEY (`ClassroomId`) REFERENCES `tblclassroom` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_tblSchedule_tblScheduleConfig` FOREIGN KEY (`ScheduleConfigId`) REFERENCES `tblscheduleconfig` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_tblSchedule_tblUser1` FOREIGN KEY (`Username`) REFERENCES `tbluser` (`Username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9410 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblschedule`
--

LOCK TABLES `tblschedule` WRITE;
/*!40000 ALTER TABLE `tblschedule` DISABLE KEYS */;
INSERT INTO `tblschedule` VALUES (8139,'PhuongLHK',1049,25,NULL,'2015-08-04',1,1),(8140,'PhuongLHK',1049,25,NULL,'2015-08-06',1,1),(8141,'PhuongLHK',1049,25,NULL,'2015-08-11',1,1),(8142,'PhuongLHK',1049,25,NULL,'2015-08-13',1,1),(8143,'PhuongLHK',1049,25,NULL,'2015-08-04',1,2),(8144,'PhuongLHK',1049,25,NULL,'2015-08-06',1,2),(8145,'PhuongLHK',1049,25,NULL,'2015-08-11',1,2),(8146,'PhuongLHK',1049,25,NULL,'2015-08-13',1,2),(8147,'HungNH',1049,25,NULL,'2015-08-04',1,4),(8148,'HungNH',1049,25,NULL,'2015-08-06',1,4),(8149,'HungNH',1049,25,NULL,'2015-08-11',1,4),(8150,'HungNH',1049,25,NULL,'2015-08-13',1,4),(8151,'HungNH',1049,25,NULL,'2015-08-04',1,5),(8152,'HungNH',1049,25,NULL,'2015-08-06',1,5),(8153,'HungNH',1049,25,NULL,'2015-08-11',1,5),(8154,'HungNH',1049,25,NULL,'2015-08-13',1,5),(8155,'HungLD',1058,25,NULL,'2015-08-04',1,5),(8156,'HungLD',1058,25,NULL,'2015-08-06',1,5),(8157,'HungLD',1058,25,NULL,'2015-08-11',1,5),(8158,'HungLD',1058,25,NULL,'2015-08-13',1,5),(8159,'HoangNT',1059,25,NULL,'2015-08-03',1,2),(8160,'HoangNT',1059,25,NULL,'2015-08-04',1,2),(8161,'HoangNT',1059,25,NULL,'2015-08-05',1,2),(8162,'HoangNT',1059,25,NULL,'2015-08-06',1,2),(8163,'HoangNT',1059,25,NULL,'2015-08-07',1,2),(8164,'HoangNT',1059,25,NULL,'2015-08-10',1,2),(8165,'HoangNT',1059,25,NULL,'2015-08-11',1,2),(8166,'HoangNT',1059,25,NULL,'2015-08-12',1,2),(8167,'HoangNT',1059,25,NULL,'2015-08-13',1,2),(8168,'HoangNT',1059,25,NULL,'2015-08-14',1,2),(8169,'TaiNT',1059,25,NULL,'2015-08-04',1,5),(8170,'TaiNT',1059,25,NULL,'2015-08-05',1,5),(8171,'TaiNT',1059,25,NULL,'2015-08-06',1,5),(8172,'TaiNT',1059,25,NULL,'2015-08-07',1,5),(8173,'TaiNT',1059,25,NULL,'2015-08-11',1,5),(8174,'TaiNT',1059,25,NULL,'2015-08-12',1,5),(8175,'TaiNT',1059,25,NULL,'2015-08-13',1,5),(8176,'TaiNT',1059,25,NULL,'2015-08-14',1,5),(8177,'HungLD',1060,25,NULL,'2015-08-04',1,1),(8178,'HungLD',1060,25,NULL,'2015-08-06',1,1),(8179,'HungLD',1060,25,NULL,'2015-08-07',1,1),(8180,'HungLD',1060,25,'Đổi sang phòng 102','2015-08-11',0,1),(8181,'HungLD',1060,25,NULL,'2015-08-13',1,1),(8182,'HungLD',1060,25,NULL,'2015-08-14',1,1),(8183,'HiepNT',1060,25,NULL,'2015-08-03',1,2),(8184,'HiepNT',1060,25,NULL,'2015-08-04',1,2),(8185,'HiepNT',1060,25,NULL,'2015-08-05',1,2),(8186,'HiepNT',1060,25,NULL,'2015-08-06',1,2),(8187,'HiepNT',1060,25,NULL,'2015-08-07',1,2),(8188,'HiepNT',1060,25,'Đổi sang phòng 101','2015-08-10',0,2),(8189,'HiepNT',1060,25,'Đổi sang phòng 102','2015-08-11',0,2),(8190,'HiepNT',1060,25,NULL,'2015-08-12',1,2),(8191,'HiepNT',1060,25,NULL,'2015-08-13',1,2),(8192,'HiepNT',1060,25,NULL,'2015-08-14',1,2),(8193,'ChungKTT',1060,25,NULL,'2015-08-03',1,3),(8194,'ChungKTT',1060,25,NULL,'2015-08-04',1,3),(8195,'ChungKTT',1060,25,NULL,'2015-08-05',1,3),(8196,'ChungKTT',1060,25,NULL,'2015-08-06',1,3),(8197,'ChungKTT',1060,25,NULL,'2015-08-07',1,3),(8198,'ChungKTT',1060,25,'Đổi sang phòng 101','2015-08-10',0,3),(8199,'ChungKTT',1060,25,'Đổi sang phòng 102','2015-08-11',0,3),(8200,'ChungKTT',1060,25,NULL,'2015-08-12',1,3),(8201,'ChungKTT',1060,25,NULL,'2015-08-13',1,3),(8202,'ChungKTT',1060,25,NULL,'2015-08-14',1,3),(8203,'KhanhKT',1060,25,NULL,'2015-08-03',1,4),(8204,'KhanhKT',1060,25,NULL,'2015-08-07',1,4),(8205,'KhanhKT',1060,25,NULL,'2015-08-10',1,4),(8206,'KhanhKT',1060,25,'Đổi sang phòng 101','2015-08-14',0,4),(8207,'KhanhKT',1060,25,NULL,'2015-08-03',1,5),(8208,'KhanhKT',1060,25,NULL,'2015-08-07',1,5),(8209,'KhanhKT',1060,25,NULL,'2015-08-10',1,5),(8210,'KhanhKT',1060,25,'Đổi sang phòng 101','2015-08-14',0,5),(8211,'LienTTH',1061,25,NULL,'2015-08-03',1,2),(8212,'HuongPTT',1061,25,NULL,'2015-08-04',1,2),(8213,'LienTTH',1061,25,NULL,'2015-08-05',1,2),(8214,'HuongPTT',1061,25,NULL,'2015-08-06',1,2),(8215,'LienTTH',1061,25,NULL,'2015-08-07',1,2),(8216,'LienTTH',1061,25,NULL,'2015-08-10',1,2),(8217,'HuongPTT',1061,25,NULL,'2015-08-11',1,2),(8218,'LienTTH',1061,25,NULL,'2015-08-12',1,2),(8219,'HuongPTT',1061,25,NULL,'2015-08-13',1,2),(8220,'LienTTH',1061,25,NULL,'2015-08-14',1,2),(8221,'LienTTH',1061,25,NULL,'2015-08-03',1,3),(8222,'HuongPTT',1061,25,NULL,'2015-08-04',1,3),(8223,'LienTTH',1061,25,NULL,'2015-08-05',1,3),(8224,'HuongPTT',1061,25,NULL,'2015-08-06',1,3),(8225,'LienTTH',1061,25,NULL,'2015-08-07',1,3),(8226,'LienTTH',1061,25,NULL,'2015-08-10',1,3),(8227,'HuongPTT',1061,25,NULL,'2015-08-11',1,3),(8228,'LienTTH',1061,25,NULL,'2015-08-12',1,3),(8229,'HuongPTT',1061,25,NULL,'2015-08-13',1,3),(8230,'LienTTH',1061,25,NULL,'2015-08-14',1,3),(8231,'AnNDH',1061,25,NULL,'2015-08-03',1,4),(8232,'AnNDH',1061,25,NULL,'2015-08-05',1,4),(8233,'AnNDH',1061,25,NULL,'2015-08-10',1,4),(8234,'AnNDH',1061,25,NULL,'2015-08-12',1,4),(8235,'SuTV',1061,25,NULL,'2015-08-03',1,5),(8236,'SuTV',1061,25,NULL,'2015-08-04',1,5),(8237,'SuTV',1061,25,NULL,'2015-08-05',1,5),(8238,'SuTV',1061,25,NULL,'2015-08-06',1,5),(8239,'SuTV',1061,25,NULL,'2015-08-07',1,5),(8240,'SuTV',1061,25,NULL,'2015-08-10',1,5),(8241,'SuTV',1061,25,NULL,'2015-08-11',1,5),(8242,'SuTV',1061,25,NULL,'2015-08-12',1,5),(8243,'SuTV',1061,25,NULL,'2015-08-13',1,5),(8244,'SuTV',1061,25,NULL,'2015-08-14',1,5),(8245,'KieuTA',1061,25,NULL,'2015-08-03',1,6),(8246,'KieuTA',1061,25,NULL,'2015-08-04',1,6),(8247,'KieuTA',1061,25,NULL,'2015-08-05',1,6),(8248,'KieuTA',1061,25,NULL,'2015-08-06',1,6),(8249,'KieuTA',1061,25,NULL,'2015-08-07',1,6),(8250,'KieuTA',1061,25,NULL,'2015-08-10',1,6),(8251,'KieuTA',1061,25,NULL,'2015-08-11',1,6),(8252,'KieuTA',1061,25,NULL,'2015-08-12',1,6),(8253,'KieuTA',1061,25,NULL,'2015-08-13',1,6),(8254,'KieuTA',1061,25,NULL,'2015-08-14',1,6),(8255,'HiepTT',1050,25,NULL,'2015-08-03',1,1),(8256,'HiepTT',1050,25,NULL,'2015-08-04',1,1),(8257,'HiepTT',1050,25,NULL,'2015-08-05',1,1),(8258,'HiepTT',1050,25,NULL,'2015-08-06',1,1),(8259,'HiepTT',1050,25,NULL,'2015-08-07',1,1),(8260,'HiepTT',1050,25,NULL,'2015-08-10',1,1),(8261,'HiepTT',1050,25,'Đổi sang phòng 202','2015-08-11',0,1),(8262,'HiepTT',1050,25,NULL,'2015-08-12',1,1),(8263,'HiepTT',1050,25,NULL,'2015-08-13',1,1),(8264,'HiepTT',1050,25,NULL,'2015-08-14',1,1),(8265,'HungLD',1050,25,NULL,'2015-08-03',1,2),(8266,'HungLD',1050,25,NULL,'2015-08-04',1,2),(8267,'HungLD',1050,25,NULL,'2015-08-05',1,2),(8268,'HungLD',1050,25,NULL,'2015-08-06',1,2),(8269,'HungLD',1050,25,NULL,'2015-08-07',1,2),(8270,'HungLD',1050,25,NULL,'2015-08-10',1,2),(8271,'HungLD',1050,25,'Đổi sang phòng 202','2015-08-11',0,2),(8272,'HungLD',1050,25,NULL,'2015-08-12',1,2),(8273,'HungLD',1050,25,NULL,'2015-08-13',1,2),(8274,'HungLD',1050,25,NULL,'2015-08-14',1,2),(8275,'KieuTA',1050,25,NULL,'2015-08-04',1,3),(8276,'KieuTA',1050,25,NULL,'2015-08-06',1,3),(8277,'KieuTA',1050,25,NULL,'2015-08-07',1,3),(8278,'KieuTA',1050,25,'Đổi sang phòng 202','2015-08-11',0,3),(8279,'KieuTA',1050,25,NULL,'2015-08-13',1,3),(8280,'KieuTA',1050,25,NULL,'2015-08-14',1,3),(8281,'AnNDH',1062,25,NULL,'2015-08-03',1,1),(8282,'AnNDH',1062,25,NULL,'2015-08-04',1,1),(8283,'AnNDH',1062,25,NULL,'2015-08-05',1,1),(8284,'AnNDH',1062,25,NULL,'2015-08-06',1,1),(8285,'AnNDH',1062,25,NULL,'2015-08-07',1,1),(8286,'AnNDH',1062,25,NULL,'2015-08-10',1,1),(8287,'AnNDH',1062,25,NULL,'2015-08-11',1,1),(8288,'AnNDH',1062,25,NULL,'2015-08-12',1,1),(8289,'AnNDH',1062,25,NULL,'2015-08-13',1,1),(8290,'AnNDH',1062,25,NULL,'2015-08-14',1,1),(8291,'KieuTA',1062,25,NULL,'2015-08-03',1,2),(8292,'KieuTA',1062,25,NULL,'2015-08-05',1,2),(8293,'KieuTA',1062,25,NULL,'2015-08-10',1,2),(8294,'KieuTA',1062,25,NULL,'2015-08-12',1,2),(8295,'TuDT',1062,25,NULL,'2015-08-03',1,3),(8296,'TuDT',1062,25,NULL,'2015-08-04',1,3),(8297,'TuDT',1062,25,NULL,'2015-08-05',1,3),(8298,'TuDT',1062,25,NULL,'2015-08-06',1,3),(8299,'TuDT',1062,25,NULL,'2015-08-07',1,3),(8300,'TuDT',1062,25,NULL,'2015-08-10',1,3),(8301,'TuDT',1062,25,NULL,'2015-08-11',1,3),(8302,'TuDT',1062,25,NULL,'2015-08-12',1,3),(8303,'TuDT',1062,25,NULL,'2015-08-13',1,3),(8304,'TuDT',1062,25,NULL,'2015-08-14',1,3),(8305,'HiepNT',1062,25,NULL,'2015-08-04',1,4),(8306,'HiepNT',1062,25,NULL,'2015-08-06',1,4),(8307,'HiepNT',1062,25,NULL,'2015-08-07',1,4),(8308,'HiepNT',1062,25,NULL,'2015-08-11',1,4),(8309,'HiepNT',1062,25,NULL,'2015-08-13',1,4),(8310,'HiepNT',1062,25,NULL,'2015-08-14',1,4),(8311,'BinhDTH',1062,25,NULL,'2015-08-03',1,5),(8312,'BinhDTH',1062,25,NULL,'2015-08-04',1,5),(8313,'BinhDTH',1062,25,NULL,'2015-08-05',1,5),(8314,'BinhDTH',1062,25,NULL,'2015-08-06',1,5),(8315,'BinhDTH',1062,25,NULL,'2015-08-07',1,5),(8316,'BinhDTH',1062,25,NULL,'2015-08-10',1,5),(8317,'BinhDTH',1062,25,NULL,'2015-08-11',1,5),(8318,'BinhDTH',1062,25,NULL,'2015-08-12',1,5),(8319,'BinhDTH',1062,25,NULL,'2015-08-13',1,5),(8320,'BinhDTH',1062,25,NULL,'2015-08-14',1,5),(8321,'KhanhKT',1062,25,NULL,'2015-08-03',1,6),(8322,'KhanhKT',1062,25,NULL,'2015-08-04',1,6),(8323,'KhanhKT',1062,25,NULL,'2015-08-05',1,6),(8324,'KhanhKT',1062,25,NULL,'2015-08-06',1,6),(8325,'KhanhKT',1062,25,NULL,'2015-08-07',1,6),(8326,'KhanhKT',1062,25,NULL,'2015-08-10',1,6),(8327,'KhanhKT',1062,25,NULL,'2015-08-11',1,6),(8328,'KhanhKT',1062,25,NULL,'2015-08-12',1,6),(8329,'KhanhKT',1062,25,NULL,'2015-08-13',1,6),(8330,'KhanhKT',1062,25,NULL,'2015-08-14',1,6),(8331,'TaiNT',1063,25,NULL,'2015-08-03',1,1),(8332,'TaiNT',1063,25,NULL,'2015-08-04',1,1),(8333,'TaiNT',1063,25,NULL,'2015-08-05',1,1),(8334,'TaiNT',1063,25,NULL,'2015-08-06',1,1),(8335,'TaiNT',1063,25,NULL,'2015-08-07',1,1),(8336,'TaiNT',1063,25,'Đổi sang phòng 307','2015-08-10',0,1),(8337,'TaiNT',1063,25,NULL,'2015-08-11',1,1),(8338,'TaiNT',1063,25,NULL,'2015-08-12',1,1),(8339,'TaiNT',1063,25,NULL,'2015-08-13',1,1),(8340,'TaiNT',1063,25,NULL,'2015-08-14',1,1),(8341,'KieuTA',1063,25,NULL,'2015-08-04',1,2),(8342,'KieuTA',1063,25,NULL,'2015-08-06',1,2),(8343,'KieuTA',1063,25,NULL,'2015-08-07',1,2),(8344,'KieuTA',1063,25,NULL,'2015-08-11',1,2),(8345,'KieuTA',1063,25,NULL,'2015-08-13',1,2),(8346,'KieuTA',1063,25,NULL,'2015-08-14',1,2),(8347,'AnNDH',1063,25,NULL,'2015-08-03',1,3),(8348,'AnNDH',1063,25,NULL,'2015-08-04',1,3),(8349,'AnNDH',1063,25,NULL,'2015-08-05',1,3),(8350,'AnNDH',1063,25,NULL,'2015-08-06',1,3),(8351,'AnNDH',1063,25,NULL,'2015-08-07',1,3),(8352,'AnNDH',1063,25,'Đổi sang phòng 307','2015-08-10',0,3),(8353,'AnNDH',1063,25,NULL,'2015-08-11',1,3),(8354,'AnNDH',1063,25,NULL,'2015-08-12',1,3),(8355,'AnNDH',1063,25,NULL,'2015-08-13',1,3),(8356,'AnNDH',1063,25,NULL,'2015-08-14',1,3),(8357,'CamDTH',1063,25,NULL,'2015-08-04',1,4),(8358,'CamDTH',1063,25,NULL,'2015-08-06',1,4),(8359,'CamDTH',1063,25,NULL,'2015-08-07',1,4),(8360,'CamDTH',1063,25,NULL,'2015-08-11',1,4),(8361,'CamDTH',1063,25,NULL,'2015-08-13',1,4),(8362,'CamDTH',1063,25,NULL,'2015-08-14',1,4),(8363,'TuDT',1063,25,NULL,'2015-08-03',1,5),(8364,'TuDT',1063,25,NULL,'2015-08-04',1,5),(8365,'TuDT',1063,25,NULL,'2015-08-05',1,5),(8366,'TuDT',1063,25,NULL,'2015-08-06',1,5),(8367,'TuDT',1063,25,NULL,'2015-08-07',1,5),(8368,'TuDT',1063,25,'Đổi sang phòng 307','2015-08-10',0,5),(8369,'TuDT',1063,25,NULL,'2015-08-11',1,5),(8370,'TuDT',1063,25,NULL,'2015-08-12',1,5),(8371,'TuDT',1063,25,NULL,'2015-08-13',1,5),(8372,'TuDT',1063,25,NULL,'2015-08-14',1,5),(8373,'AnNDH',1063,25,NULL,'2015-08-03',1,6),(8374,'AnNDH',1063,25,NULL,'2015-08-04',1,6),(8375,'AnNDH',1063,25,NULL,'2015-08-05',1,6),(8376,'AnNDH',1063,25,NULL,'2015-08-06',1,6),(8377,'AnNDH',1063,25,NULL,'2015-08-07',1,6),(8378,'AnNDH',1063,25,'Đổi sang phòng 307','2015-08-10',0,6),(8379,'AnNDH',1063,25,NULL,'2015-08-11',1,6),(8380,'AnNDH',1063,25,NULL,'2015-08-12',1,6),(8381,'AnNDH',1063,25,NULL,'2015-08-13',1,6),(8382,'AnNDH',1063,25,NULL,'2015-08-14',1,6),(8383,'PhuongLHK',1064,25,NULL,'2015-08-03',1,4),(8384,'PhuongLHK',1064,25,NULL,'2015-08-04',1,4),(8385,'PhuongLHK',1064,25,NULL,'2015-08-05',1,4),(8386,'PhuongLHK',1064,25,NULL,'2015-08-06',1,4),(8387,'PhuongLHK',1064,25,NULL,'2015-08-07',1,4),(8388,'PhuongLHK',1064,25,NULL,'2015-08-10',1,4),(8389,'PhuongLHK',1064,25,NULL,'2015-08-11',1,4),(8390,'PhuongLHK',1064,25,NULL,'2015-08-12',1,4),(8391,'PhuongLHK',1064,25,NULL,'2015-08-13',1,4),(8392,'PhuongLHK',1064,25,NULL,'2015-08-14',1,4),(8393,'KieuTA',1051,25,NULL,'2015-08-03',1,1),(8394,'KieuTA',1051,25,NULL,'2015-08-05',1,1),(8395,'KieuTA',1051,25,NULL,'2015-08-10',1,1),(8396,'KieuTA',1051,25,NULL,'2015-08-12',1,1),(8397,'HungNH',1051,25,NULL,'2015-08-03',1,2),(8398,'HungNH',1051,25,NULL,'2015-08-04',1,2),(8399,'HungNH',1051,25,NULL,'2015-08-05',1,2),(8400,'HungNH',1051,25,NULL,'2015-08-06',1,2),(8401,'HungNH',1051,25,NULL,'2015-08-07',1,2),(8402,'HungNH',1051,25,NULL,'2015-08-10',1,2),(8403,'HungNH',1051,25,NULL,'2015-08-11',1,2),(8404,'HungNH',1051,25,NULL,'2015-08-12',1,2),(8405,'HungNH',1051,25,NULL,'2015-08-13',1,2),(8406,'HungNH',1051,25,NULL,'2015-08-14',1,2),(8407,'ThuyetDTM',1051,25,NULL,'2015-08-03',1,3),(8408,'ThuyetDTM',1051,25,NULL,'2015-08-04',1,3),(8409,'ThuyetDTM',1051,25,NULL,'2015-08-05',1,3),(8410,'ThuyetDTM',1051,25,NULL,'2015-08-06',1,3),(8411,'ThuyetDTM',1051,25,NULL,'2015-08-07',1,3),(8412,'ThuyetDTM',1051,25,NULL,'2015-08-10',1,3),(8413,'ThuyetDTM',1051,25,NULL,'2015-08-11',1,3),(8414,'ThuyetDTM',1051,25,NULL,'2015-08-12',1,3),(8415,'ThuyetDTM',1051,25,NULL,'2015-08-13',1,3),(8416,'ThuyetDTM',1051,25,NULL,'2015-08-14',1,3),(8417,'Cecile',1051,25,NULL,'2015-08-03',1,4),(8418,'Cecile',1051,25,NULL,'2015-08-04',1,4),(8419,'Cecile',1051,25,NULL,'2015-08-05',1,4),(8420,'Cecile',1051,25,NULL,'2015-08-06',1,4),(8421,'Cecile',1051,25,NULL,'2015-08-07',1,4),(8422,'Cecile',1051,25,NULL,'2015-08-10',1,4),(8423,'Cecile',1051,25,NULL,'2015-08-11',1,4),(8424,'Cecile',1051,25,NULL,'2015-08-12',1,4),(8425,'Cecile',1051,25,NULL,'2015-08-13',1,4),(8426,'Cecile',1051,25,NULL,'2015-08-14',1,4),(8427,'CamDTH',1051,25,NULL,'2015-08-03',1,5),(8428,'CamDTH',1051,25,NULL,'2015-08-05',1,5),(8429,'CamDTH',1051,25,NULL,'2015-08-10',1,5),(8430,'CamDTH',1051,25,NULL,'2015-08-12',1,5),(8431,'HungNH',1051,25,NULL,'2015-08-03',1,6),(8432,'HungNH',1051,25,NULL,'2015-08-04',1,6),(8433,'HungNH',1051,25,NULL,'2015-08-05',1,6),(8434,'HungNH',1051,25,NULL,'2015-08-06',1,6),(8435,'HungNH',1051,25,NULL,'2015-08-07',1,6),(8436,'HungNH',1051,25,NULL,'2015-08-10',1,6),(8437,'HungNH',1051,25,NULL,'2015-08-11',1,6),(8438,'HungNH',1051,25,NULL,'2015-08-12',1,6),(8439,'HungNH',1051,25,NULL,'2015-08-13',1,6),(8440,'HungNH',1051,25,NULL,'2015-08-14',1,6),(8441,'HungLD',1052,25,NULL,'2015-08-03',1,4),(8442,'HungLD',1052,25,NULL,'2015-08-04',1,4),(8443,'HungLD',1052,25,NULL,'2015-08-05',1,4),(8444,'HungLD',1052,25,NULL,'2015-08-06',1,4),(8445,'HungLD',1052,25,NULL,'2015-08-07',1,4),(8446,'HungLD',1052,25,NULL,'2015-08-10',1,4),(8447,'HungLD',1052,25,NULL,'2015-08-11',1,4),(8448,'HungLD',1052,25,NULL,'2015-08-12',1,4),(8449,'HungLD',1052,25,NULL,'2015-08-13',1,4),(8450,'HungLD',1052,25,NULL,'2015-08-14',1,4),(8451,'CamDTH',1052,25,NULL,'2015-08-04',1,5),(8452,'CamDTH',1052,25,NULL,'2015-08-06',1,5),(8453,'CamDTH',1052,25,NULL,'2015-08-07',1,5),(8454,'CamDTH',1052,25,NULL,'2015-08-11',1,5),(8455,'CamDTH',1052,25,NULL,'2015-08-13',1,5),(8456,'CamDTH',1052,25,NULL,'2015-08-14',1,5),(8457,'TaiNT',1052,25,NULL,'2015-08-03',1,6),(8458,'TaiNT',1052,25,NULL,'2015-08-04',1,6),(8459,'TaiNT',1052,25,NULL,'2015-08-05',1,6),(8460,'TaiNT',1052,25,NULL,'2015-08-06',1,6),(8461,'TaiNT',1052,25,NULL,'2015-08-07',1,6),(8462,'TaiNT',1052,25,NULL,'2015-08-10',1,6),(8463,'TaiNT',1052,25,NULL,'2015-08-11',1,6),(8464,'TaiNT',1052,25,NULL,'2015-08-12',1,6),(8465,'TaiNT',1052,25,NULL,'2015-08-13',1,6),(8466,'TaiNT',1052,25,NULL,'2015-08-14',1,6),(8467,'ThanhDT',1065,25,NULL,'2015-08-03',1,4),(8468,'ThanhDT',1065,25,NULL,'2015-08-04',1,4),(8469,'ThanhDT',1065,25,NULL,'2015-08-05',1,4),(8470,'ThanhDT',1065,25,NULL,'2015-08-06',1,4),(8471,'ThanhDT',1065,25,NULL,'2015-08-07',1,4),(8472,'ThanhDT',1065,25,NULL,'2015-08-10',1,4),(8473,'ThanhDT',1065,25,NULL,'2015-08-11',1,4),(8474,'ThanhDT',1065,25,NULL,'2015-08-12',1,4),(8475,'ThanhDT',1065,25,NULL,'2015-08-13',1,4),(8476,'ThanhDT',1065,25,NULL,'2015-08-14',1,4),(8477,'AnhNT',1065,25,NULL,'2015-08-03',1,5),(8478,'AnhNT',1065,25,NULL,'2015-08-04',1,5),(8479,'AnhNT',1065,25,NULL,'2015-08-05',1,5),(8480,'AnhNT',1065,25,NULL,'2015-08-06',1,5),(8481,'AnhNT',1065,25,NULL,'2015-08-07',1,5),(8482,'AnhNT',1065,25,NULL,'2015-08-10',1,5),(8483,'AnhNT',1065,25,NULL,'2015-08-11',1,5),(8484,'AnhNT',1065,25,NULL,'2015-08-12',1,5),(8485,'AnhNT',1065,25,NULL,'2015-08-13',1,5),(8486,'AnhNT',1065,25,NULL,'2015-08-14',1,5),(8487,'PhuongLHK',1065,25,NULL,'2015-08-04',1,6),(8488,'PhuongLHK',1065,25,NULL,'2015-08-06',1,6),(8489,'PhuongLHK',1065,25,NULL,'2015-08-07',1,6),(8490,'PhuongLHK',1065,25,NULL,'2015-08-11',1,6),(8491,'PhuongLHK',1065,25,NULL,'2015-08-13',1,6),(8492,'PhuongLHK',1065,25,NULL,'2015-08-14',1,6),(8493,'TriBD',1068,25,NULL,'2015-08-04',1,1),(8494,'TriBD',1068,25,NULL,'2015-08-06',1,1),(8495,'TriBD',1068,25,NULL,'2015-08-11',1,1),(8496,'TriBD',1068,25,NULL,'2015-08-13',1,1),(8497,'TriBD',1068,25,NULL,'2015-08-04',1,2),(8498,'TriBD',1068,25,NULL,'2015-08-06',1,2),(8499,'TriBD',1068,25,NULL,'2015-08-11',1,2),(8500,'TriBD',1068,25,NULL,'2015-08-13',1,2),(8501,'LoiND',1068,25,NULL,'2015-08-03',1,4),(8502,'LoiND',1068,25,NULL,'2015-08-04',1,4),(8503,'LoiND',1068,25,NULL,'2015-08-05',1,4),(8504,'LoiND',1068,25,NULL,'2015-08-06',1,4),(8505,'LoiND',1068,25,NULL,'2015-08-07',1,4),(8506,'LoiND',1068,25,NULL,'2015-08-10',1,4),(8507,'LoiND',1068,25,NULL,'2015-08-11',1,4),(8508,'LoiND',1068,25,NULL,'2015-08-12',1,4),(8509,'LoiND',1068,25,NULL,'2015-08-13',1,4),(8510,'LoiND',1068,25,NULL,'2015-08-14',1,4),(8511,'ChungKTT',1068,25,NULL,'2015-08-03',1,5),(8512,'ChungKTT',1068,25,NULL,'2015-08-04',1,5),(8513,'ChungKTT',1068,25,NULL,'2015-08-05',1,5),(8514,'ChungKTT',1068,25,NULL,'2015-08-06',1,5),(8515,'ChungKTT',1068,25,NULL,'2015-08-07',1,5),(8516,'ChungKTT',1068,25,NULL,'2015-08-10',1,5),(8517,'ChungKTT',1068,25,NULL,'2015-08-11',1,5),(8518,'ChungKTT',1068,25,NULL,'2015-08-12',1,5),(8519,'ChungKTT',1068,25,NULL,'2015-08-13',1,5),(8520,'ChungKTT',1068,25,NULL,'2015-08-14',1,5),(8521,'PhuongLHK',1068,25,NULL,'2015-08-03',1,6),(8522,'PhuongLHK',1068,25,NULL,'2015-08-05',1,6),(8523,'PhuongLHK',1068,25,NULL,'2015-08-10',1,6),(8524,'PhuongLHK',1068,25,NULL,'2015-08-12',1,6),(8525,'ChauLH',1067,25,NULL,'2015-08-03',1,1),(8526,'ChauLH',1067,25,NULL,'2015-08-04',1,1),(8527,'ChauLH',1067,25,NULL,'2015-08-05',1,1),(8528,'ChauLH',1067,25,NULL,'2015-08-06',1,1),(8529,'ChauLH',1067,25,NULL,'2015-08-07',1,1),(8530,'ChauLH',1067,25,NULL,'2015-08-10',1,1),(8531,'ChauLH',1067,25,NULL,'2015-08-11',1,1),(8532,'ChauLH',1067,25,NULL,'2015-08-12',1,1),(8533,'ChauLH',1067,25,NULL,'2015-08-13',1,1),(8534,'ChauLH',1067,25,NULL,'2015-08-14',1,1),(8535,'PhuongLHK',1067,25,NULL,'2015-08-03',1,2),(8536,'PhuongLHK',1067,25,NULL,'2015-08-05',1,2),(8537,'PhuongLHK',1067,25,NULL,'2015-08-10',1,2),(8538,'PhuongLHK',1067,25,NULL,'2015-08-12',1,2),(8539,'HiepNT',1067,25,NULL,'2015-08-03',1,3),(8540,'HiepNT',1067,25,NULL,'2015-08-04',1,3),(8541,'HiepNT',1067,25,NULL,'2015-08-05',1,3),(8542,'HiepNT',1067,25,NULL,'2015-08-06',1,3),(8543,'HiepNT',1067,25,NULL,'2015-08-07',1,3),(8544,'HiepNT',1067,25,NULL,'2015-08-10',1,3),(8545,'HiepNT',1067,25,NULL,'2015-08-11',1,3),(8546,'HiepNT',1067,25,NULL,'2015-08-12',1,3),(8547,'HiepNT',1067,25,NULL,'2015-08-13',1,3),(8548,'HiepNT',1067,25,NULL,'2015-08-14',1,3),(8549,'VinhDP',1054,25,NULL,'2015-08-03',1,1),(8550,'VinhDP',1054,25,NULL,'2015-08-04',1,1),(8551,'VinhDP',1054,25,NULL,'2015-08-05',1,1),(8552,'VinhDP',1054,25,NULL,'2015-08-06',1,1),(8553,'VinhDP',1054,25,NULL,'2015-08-07',1,1),(8554,'VinhDP',1054,25,NULL,'2015-08-10',1,1),(8555,'VinhDP',1054,25,NULL,'2015-08-11',1,1),(8556,'VinhDP',1054,25,NULL,'2015-08-12',1,1),(8557,'VinhDP',1054,25,NULL,'2015-08-13',1,1),(8558,'VinhDP',1054,25,NULL,'2015-08-14',1,1),(8559,'AnNDH',1054,25,NULL,'2015-08-03',1,2),(8560,'AnNDH',1054,25,NULL,'2015-08-04',1,2),(8561,'AnNDH',1054,25,NULL,'2015-08-05',1,2),(8562,'AnNDH',1054,25,NULL,'2015-08-06',1,2),(8563,'AnNDH',1054,25,NULL,'2015-08-07',1,2),(8564,'AnNDH',1054,25,NULL,'2015-08-10',1,2),(8565,'AnNDH',1054,25,NULL,'2015-08-11',1,2),(8566,'AnNDH',1054,25,NULL,'2015-08-12',1,2),(8567,'AnNDH',1054,25,NULL,'2015-08-13',1,2),(8568,'AnNDH',1054,25,NULL,'2015-08-14',1,2),(8569,'HoangNT',1054,25,NULL,'2015-08-03',1,3),(8570,'HoangNT',1054,25,NULL,'2015-08-05',1,3),(8571,'HoangNT',1054,25,NULL,'2015-08-10',1,3),(8572,'HoangNT',1054,25,NULL,'2015-08-12',1,3),(8573,'HiepNT',1054,25,NULL,'2015-08-03',1,4),(8574,'HiepNT',1054,25,NULL,'2015-08-05',1,4),(8575,'HiepNT',1054,25,NULL,'2015-08-10',1,4),(8576,'HiepNT',1054,25,NULL,'2015-08-12',1,4),(8577,'HiepTT',1054,25,NULL,'2015-08-03',1,5),(8578,'HiepTT',1054,25,NULL,'2015-08-04',1,5),(8579,'HiepTT',1054,25,NULL,'2015-08-05',1,5),(8580,'HiepTT',1054,25,NULL,'2015-08-06',1,5),(8581,'HiepTT',1054,25,NULL,'2015-08-07',1,5),(8582,'HiepTT',1054,25,NULL,'2015-08-10',1,5),(8583,'HiepTT',1054,25,NULL,'2015-08-11',1,5),(8584,'HiepTT',1054,25,NULL,'2015-08-12',1,5),(8585,'HiepTT',1054,25,NULL,'2015-08-13',1,5),(8586,'HiepTT',1054,25,NULL,'2015-08-14',1,5),(8587,'BinhDTH',1054,25,NULL,'2015-08-03',1,6),(8588,'BinhDTH',1054,25,NULL,'2015-08-04',1,6),(8589,'BinhDTH',1054,25,NULL,'2015-08-05',1,6),(8590,'BinhDTH',1054,25,NULL,'2015-08-06',1,6),(8591,'BinhDTH',1054,25,NULL,'2015-08-07',1,6),(8592,'BinhDTH',1054,25,NULL,'2015-08-10',1,6),(8593,'BinhDTH',1054,25,NULL,'2015-08-11',1,6),(8594,'BinhDTH',1054,25,NULL,'2015-08-12',1,6),(8595,'BinhDTH',1054,25,NULL,'2015-08-13',1,6),(8596,'BinhDTH',1054,25,NULL,'2015-08-14',1,6),(8597,'NhuLTL',1066,25,NULL,'2015-08-03',1,3),(8598,'NhuLTL',1066,25,NULL,'2015-08-04',1,3),(8599,'NhuLTL',1066,25,NULL,'2015-08-05',1,3),(8600,'NhuLTL',1066,25,NULL,'2015-08-06',1,3),(8601,'NhuLTL',1066,25,NULL,'2015-08-07',1,3),(8602,'NhuLTL',1066,25,NULL,'2015-08-10',1,3),(8603,'NhuLTL',1066,25,NULL,'2015-08-11',1,3),(8604,'NhuLTL',1066,25,NULL,'2015-08-12',1,3),(8605,'NhuLTL',1066,25,NULL,'2015-08-13',1,3),(8606,'NhuLTL',1066,25,NULL,'2015-08-14',1,3),(8607,'HoangNT',1066,25,NULL,'2015-08-03',1,4),(8608,'HoangNT',1066,25,NULL,'2015-08-04',1,4),(8609,'HoangNT',1066,25,NULL,'2015-08-05',1,4),(8610,'HoangNT',1066,25,NULL,'2015-08-06',1,4),(8611,'HoangNT',1066,25,NULL,'2015-08-07',1,4),(8612,'HoangNT',1066,25,NULL,'2015-08-10',1,4),(8613,'HoangNT',1066,25,NULL,'2015-08-11',1,4),(8614,'HoangNT',1066,25,NULL,'2015-08-12',1,4),(8615,'HoangNT',1066,25,NULL,'2015-08-13',1,4),(8616,'HoangNT',1066,25,NULL,'2015-08-14',1,4),(8617,'TuDT',1066,25,NULL,'2015-08-03',1,6),(8618,'TuDT',1066,25,NULL,'2015-08-04',1,6),(8619,'TuDT',1066,25,NULL,'2015-08-05',1,6),(8620,'TuDT',1066,25,NULL,'2015-08-06',1,6),(8621,'TuDT',1066,25,NULL,'2015-08-07',1,6),(8622,'TuDT',1066,25,NULL,'2015-08-10',1,6),(8623,'TuDT',1066,25,NULL,'2015-08-11',1,6),(8624,'TuDT',1066,25,NULL,'2015-08-12',1,6),(8625,'TuDT',1066,25,NULL,'2015-08-13',1,6),(8626,'TuDT',1066,25,NULL,'2015-08-14',1,6),(8627,'HungLD',1069,25,NULL,'2015-08-03',1,1),(8628,'HungLD',1069,25,NULL,'2015-08-05',1,1),(8629,'HungLD',1069,25,NULL,'2015-08-10',1,1),(8630,'HungLD',1069,25,NULL,'2015-08-12',1,1),(8631,'KhanhKT',1069,25,NULL,'2015-08-03',1,2),(8632,'KhanhKT',1069,25,NULL,'2015-08-04',1,2),(8633,'KhanhKT',1069,25,NULL,'2015-08-05',1,2),(8634,'KhanhKT',1069,25,NULL,'2015-08-06',1,2),(8635,'KhanhKT',1069,25,NULL,'2015-08-07',1,2),(8636,'KhanhKT',1069,25,NULL,'2015-08-10',1,2),(8637,'KhanhKT',1069,25,NULL,'2015-08-11',1,2),(8638,'KhanhKT',1069,25,NULL,'2015-08-12',1,2),(8639,'KhanhKT',1069,25,NULL,'2015-08-13',1,2),(8640,'KhanhKT',1069,25,NULL,'2015-08-14',1,2),(8641,'TaiNT',1069,25,NULL,'2015-08-03',1,3),(8642,'TaiNT',1069,25,NULL,'2015-08-04',1,3),(8643,'TaiNT',1069,25,NULL,'2015-08-05',1,3),(8644,'TaiNT',1069,25,NULL,'2015-08-06',1,3),(8645,'TaiNT',1069,25,NULL,'2015-08-07',1,3),(8646,'TaiNT',1069,25,NULL,'2015-08-10',1,3),(8647,'TaiNT',1069,25,NULL,'2015-08-11',1,3),(8648,'TaiNT',1069,25,NULL,'2015-08-12',1,3),(8649,'TaiNT',1069,25,NULL,'2015-08-13',1,3),(8650,'TaiNT',1069,25,NULL,'2015-08-14',1,3),(8651,'VanTTN',1070,25,NULL,'2015-08-03',1,2),(8652,'VanTTN',1070,25,NULL,'2015-08-04',1,2),(8653,'VanTTN',1070,25,NULL,'2015-08-05',1,2),(8654,'VanTTN',1070,25,NULL,'2015-08-06',1,2),(8655,'VanTTN',1070,25,NULL,'2015-08-07',1,2),(8656,'VanTTN',1070,25,NULL,'2015-08-10',1,2),(8657,'VanTTN',1070,25,NULL,'2015-08-11',1,2),(8658,'VanTTN',1070,25,NULL,'2015-08-12',1,2),(8659,'VanTTN',1070,25,NULL,'2015-08-13',1,2),(8660,'VanTTN',1070,25,NULL,'2015-08-14',1,2),(8661,'VinhDP',1070,25,NULL,'2015-08-03',1,3),(8662,'VinhDP',1070,25,NULL,'2015-08-04',1,3),(8663,'VinhDP',1070,25,NULL,'2015-08-05',1,3),(8664,'VinhDP',1070,25,NULL,'2015-08-06',1,3),(8665,'VinhDP',1070,25,NULL,'2015-08-07',1,3),(8666,'VinhDP',1070,25,NULL,'2015-08-10',1,3),(8667,'VinhDP',1070,25,NULL,'2015-08-11',1,3),(8668,'VinhDP',1070,25,NULL,'2015-08-12',1,3),(8669,'VinhDP',1070,25,NULL,'2015-08-13',1,3),(8670,'VinhDP',1070,25,NULL,'2015-08-14',1,3),(8671,'VanTTN',1070,25,NULL,'2015-08-03',1,4),(8672,'VanTTN',1070,25,NULL,'2015-08-04',1,4),(8673,'VanTTN',1070,25,NULL,'2015-08-05',1,4),(8674,'VanTTN',1070,25,NULL,'2015-08-06',1,4),(8675,'VanTTN',1070,25,NULL,'2015-08-07',1,4),(8676,'VanTTN',1070,25,NULL,'2015-08-10',1,4),(8677,'VanTTN',1070,25,NULL,'2015-08-11',1,4),(8678,'VanTTN',1070,25,NULL,'2015-08-12',1,4),(8679,'VanTTN',1070,25,NULL,'2015-08-13',1,4),(8680,'VanTTN',1070,25,NULL,'2015-08-14',1,4),(8681,'VinhDP',1070,25,NULL,'2015-08-03',1,6),(8682,'VinhDP',1070,25,NULL,'2015-08-04',1,6),(8683,'VinhDP',1070,25,NULL,'2015-08-05',1,6),(8684,'VinhDP',1070,25,NULL,'2015-08-06',1,6),(8685,'VinhDP',1070,25,NULL,'2015-08-07',1,6),(8686,'VinhDP',1070,25,NULL,'2015-08-10',1,6),(8687,'VinhDP',1070,25,NULL,'2015-08-11',1,6),(8688,'VinhDP',1070,25,NULL,'2015-08-12',1,6),(8689,'VinhDP',1070,25,NULL,'2015-08-13',1,6),(8690,'VinhDP',1070,25,NULL,'2015-08-14',1,6),(8691,'SuTV',1071,25,NULL,'2015-08-03',1,1),(8692,'SuTV',1071,25,NULL,'2015-08-04',1,1),(8693,'SuTV',1071,25,NULL,'2015-08-05',1,1),(8694,'SuTV',1071,25,NULL,'2015-08-06',1,1),(8695,'SuTV',1071,25,NULL,'2015-08-07',1,1),(8696,'SuTV',1071,25,NULL,'2015-08-10',1,1),(8697,'SuTV',1071,25,NULL,'2015-08-11',1,1),(8698,'SuTV',1071,25,NULL,'2015-08-12',1,1),(8699,'SuTV',1071,25,NULL,'2015-08-13',1,1),(8700,'SuTV',1071,25,NULL,'2015-08-14',1,1),(8701,'TaiNT',1071,25,NULL,'2015-08-03',1,2),(8702,'TaiNT',1071,25,NULL,'2015-08-04',1,2),(8703,'TaiNT',1071,25,NULL,'2015-08-05',1,2),(8704,'TaiNT',1071,25,NULL,'2015-08-06',1,2),(8705,'TaiNT',1071,25,NULL,'2015-08-07',1,2),(8706,'TaiNT',1071,25,NULL,'2015-08-10',1,2),(8707,'TaiNT',1071,25,NULL,'2015-08-11',1,2),(8708,'TaiNT',1071,25,NULL,'2015-08-12',1,2),(8709,'TaiNT',1071,25,NULL,'2015-08-13',1,2),(8710,'TaiNT',1071,25,NULL,'2015-08-14',1,2),(8711,'TaiNT',1071,25,NULL,'2015-08-03',1,4),(8712,'TaiNT',1071,25,NULL,'2015-08-04',1,4),(8713,'TaiNT',1071,25,NULL,'2015-08-05',1,4),(8714,'TaiNT',1071,25,NULL,'2015-08-06',1,4),(8715,'TaiNT',1071,25,NULL,'2015-08-07',1,4),(8716,'TaiNT',1071,25,NULL,'2015-08-10',1,4),(8717,'TaiNT',1071,25,NULL,'2015-08-11',1,4),(8718,'TaiNT',1071,25,NULL,'2015-08-12',1,4),(8719,'TaiNT',1071,25,NULL,'2015-08-13',1,4),(8720,'TaiNT',1071,25,NULL,'2015-08-14',1,4),(8721,'HoangNT',1071,25,NULL,'2015-08-03',1,5),(8722,'HoangNT',1071,25,NULL,'2015-08-04',1,5),(8723,'HoangNT',1071,25,NULL,'2015-08-05',1,5),(8724,'HoangNT',1071,25,NULL,'2015-08-06',1,5),(8725,'HoangNT',1071,25,NULL,'2015-08-07',1,5),(8726,'HoangNT',1071,25,NULL,'2015-08-10',1,5),(8727,'HoangNT',1071,25,NULL,'2015-08-11',1,5),(8728,'HoangNT',1071,25,NULL,'2015-08-12',1,5),(8729,'HoangNT',1071,25,NULL,'2015-08-13',1,5),(8730,'HoangNT',1071,25,NULL,'2015-08-14',1,5),(8731,'HungNH',1072,25,NULL,'2015-08-03',1,4),(8732,'HungNH',1072,25,NULL,'2015-08-05',1,4),(8733,'HungNH',1072,25,NULL,'2015-08-07',1,4),(8734,'HungNH',1072,25,NULL,'2015-08-10',1,4),(8735,'HungNH',1072,25,NULL,'2015-08-12',1,4),(8736,'HungNH',1072,25,NULL,'2015-08-14',1,4),(8737,'VinhDP',1072,25,NULL,'2015-08-03',1,5),(8738,'VinhDP',1072,25,NULL,'2015-08-04',1,5),(8739,'VinhDP',1072,25,NULL,'2015-08-05',1,5),(8740,'VinhDP',1072,25,NULL,'2015-08-06',1,5),(8741,'VinhDP',1072,25,NULL,'2015-08-07',1,5),(8742,'VinhDP',1072,25,NULL,'2015-08-10',1,5),(8743,'VinhDP',1072,25,NULL,'2015-08-11',1,5),(8744,'VinhDP',1072,25,NULL,'2015-08-12',1,5),(8745,'VinhDP',1072,25,NULL,'2015-08-13',1,5),(8746,'VinhDP',1072,25,NULL,'2015-08-14',1,5),(8747,'SuTV',1072,25,NULL,'2015-08-03',1,6),(8748,'SuTV',1072,25,NULL,'2015-08-04',1,6),(8749,'SuTV',1072,25,NULL,'2015-08-05',1,6),(8750,'SuTV',1072,25,NULL,'2015-08-06',1,6),(8751,'SuTV',1072,25,NULL,'2015-08-07',1,6),(8752,'SuTV',1072,25,NULL,'2015-08-10',1,6),(8753,'SuTV',1072,25,NULL,'2015-08-11',1,6),(8754,'SuTV',1072,25,NULL,'2015-08-12',1,6),(8755,'SuTV',1072,25,NULL,'2015-08-13',1,6),(8756,'SuTV',1072,25,NULL,'2015-08-14',1,6),(8757,'TaiNT',1064,25,'Thay đổi phòng từ phòng 308 sang phòng 307','2015-08-10',1,1),(8758,'AnNDH',1064,25,'Thay đổi phòng từ phòng 308 sang phòng 307','2015-08-10',1,3),(8759,'TuDT',1064,25,'Thay đổi phòng từ phòng 308 sang phòng 307','2015-08-10',1,5),(8760,'AnNDH',1064,25,'Thay đổi phòng từ phòng 308 sang phòng 307','2015-08-10',1,6),(8761,'HiepNT',1049,25,'Thay đổi phòng từ phòng 115 sang phòng 101','2015-08-10',1,2),(8762,'ChungKTT',1049,25,'Thay đổi phòng từ phòng 115 sang phòng 101','2015-08-10',1,3),(8763,'HiepTT',1056,25,'Thay đổi phòng từ phòng 213 sang phòng 202','2015-08-11',1,1),(8764,'HungLD',1056,25,'Thay đổi phòng từ phòng 213 sang phòng 202','2015-08-11',1,2),(8765,'KieuTA',1056,25,'Thay đổi phòng từ phòng 213 sang phòng 202','2015-08-11',1,3),(8766,'HungLD',1055,25,'Thay đổi phòng từ phòng 115 sang phòng 102','2015-08-11',1,1),(8767,'HiepNT',1055,25,'Thay đổi phòng từ phòng 115 sang phòng 102','2015-08-11',1,2),(8768,'ChungKTT',1055,25,'Thay đổi phòng từ phòng 115 sang phòng 102','2015-08-11',1,3),(8769,'PhuongLHK',1049,25,NULL,'2015-08-18',1,1),(8770,'PhuongLHK',1049,25,NULL,'2015-08-20',1,1),(8771,'PhuongLHK',1049,25,'Đã thay đổi','2015-08-25',0,1),(8772,'PhuongLHK',1049,25,NULL,'2015-08-27',1,1),(8773,'PhuongLHK',1049,25,NULL,'2015-08-18',1,2),(8774,'PhuongLHK',1049,25,NULL,'2015-08-20',1,2),(8775,'PhuongLHK',1049,25,'Đã thay đổi','2015-08-25',0,2),(8776,'PhuongLHK',1049,25,NULL,'2015-08-27',1,2),(8777,'HungNH',1049,25,NULL,'2015-08-18',1,4),(8778,'HungNH',1049,25,NULL,'2015-08-20',1,4),(8779,'HungNH',1049,25,NULL,'2015-08-25',1,4),(8780,'HungNH',1049,25,NULL,'2015-08-27',1,4),(8781,'HungNH',1049,25,NULL,'2015-08-18',1,5),(8782,'HungNH',1049,25,NULL,'2015-08-20',1,5),(8783,'HungNH',1049,25,NULL,'2015-08-25',1,5),(8784,'HungNH',1049,25,NULL,'2015-08-27',1,5),(8785,'HungLD',1058,25,NULL,'2015-08-18',1,5),(8786,'HungLD',1058,25,NULL,'2015-08-20',1,5),(8787,'HungLD',1058,25,NULL,'2015-08-25',1,5),(8788,'HungLD',1058,25,NULL,'2015-08-27',1,5),(8789,'HoangNT',1059,25,'Đổi sang phòng 101','2015-08-17',0,2),(8790,'HoangNT',1059,25,'Đổi sang phòng 103','2015-08-18',0,2),(8791,'HoangNT',1059,25,NULL,'2015-08-19',1,2),(8792,'HoangNT',1059,25,NULL,'2015-08-20',1,2),(8793,'HoangNT',1059,25,NULL,'2015-08-21',1,2),(8794,'HoangNT',1059,25,NULL,'2015-08-24',1,2),(8795,'HoangNT',1059,25,NULL,'2015-08-25',1,2),(8796,'HoangNT',1059,25,NULL,'2015-08-26',1,2),(8797,'HoangNT',1059,25,NULL,'2015-08-27',1,2),(8798,'HoangNT',1059,25,NULL,'2015-08-28',1,2),(8799,'TaiNT',1059,25,'Đổi sang phòng 115','2015-08-18',0,5),(8800,'TaiNT',1059,25,NULL,'2015-08-19',1,5),(8801,'TaiNT',1059,25,NULL,'2015-08-20',1,5),(8802,'TaiNT',1059,25,NULL,'2015-08-21',1,5),(8803,'TaiNT',1059,25,NULL,'2015-08-25',1,5),(8804,'TaiNT',1059,25,NULL,'2015-08-26',1,5),(8805,'TaiNT',1059,25,NULL,'2015-08-27',1,5),(8806,'TaiNT',1059,25,NULL,'2015-08-28',1,5),(8807,'HungLD',1060,25,NULL,'2015-08-18',1,1),(8808,'HungLD',1060,25,NULL,'2015-08-20',1,1),(8809,'HungLD',1060,25,NULL,'2015-08-21',1,1),(8810,'HungLD',1060,25,NULL,'2015-08-25',1,1),(8811,'HungLD',1060,25,NULL,'2015-08-27',1,1),(8812,'HungLD',1060,25,NULL,'2015-08-28',1,1),(8813,'HiepNT',1060,25,NULL,'2015-08-17',1,2),(8814,'HiepNT',1060,25,NULL,'2015-08-18',1,2),(8815,'HiepNT',1060,25,NULL,'2015-08-19',1,2),(8816,'HiepNT',1060,25,NULL,'2015-08-20',1,2),(8817,'HiepNT',1060,25,NULL,'2015-08-21',1,2),(8818,'HiepNT',1060,25,NULL,'2015-08-24',1,2),(8819,'HiepNT',1060,25,NULL,'2015-08-25',1,2),(8820,'HiepNT',1060,25,NULL,'2015-08-26',1,2),(8821,'HiepNT',1060,25,NULL,'2015-08-27',1,2),(8822,'HiepNT',1060,25,NULL,'2015-08-28',1,2),(8823,'ChungKTT',1060,25,NULL,'2015-08-17',1,3),(8824,'ChungKTT',1060,25,NULL,'2015-08-18',1,3),(8825,'ChungKTT',1060,25,NULL,'2015-08-19',1,3),(8826,'ChungKTT',1060,25,NULL,'2015-08-20',1,3),(8827,'ChungKTT',1060,25,NULL,'2015-08-21',1,3),(8828,'ChungKTT',1060,25,NULL,'2015-08-24',1,3),(8829,'ChungKTT',1060,25,NULL,'2015-08-25',1,3),(8830,'ChungKTT',1060,25,NULL,'2015-08-26',1,3),(8831,'ChungKTT',1060,25,NULL,'2015-08-27',1,3),(8832,'ChungKTT',1060,25,NULL,'2015-08-28',1,3),(8833,'KhanhKT',1060,25,NULL,'2015-08-17',1,4),(8834,'KhanhKT',1060,25,NULL,'2015-08-21',1,4),(8835,'KhanhKT',1060,25,NULL,'2015-08-24',1,4),(8836,'KhanhKT',1060,25,NULL,'2015-08-28',1,4),(8837,'KhanhKT',1060,25,NULL,'2015-08-17',1,5),(8838,'KhanhKT',1060,25,NULL,'2015-08-21',1,5),(8839,'KhanhKT',1060,25,NULL,'2015-08-24',1,5),(8840,'KhanhKT',1060,25,NULL,'2015-08-28',1,5),(8841,'LienTTH',1061,25,NULL,'2015-08-17',1,2),(8842,'HuongPTT',1061,25,NULL,'2015-08-18',1,2),(8843,'LienTTH',1061,25,NULL,'2015-08-19',1,2),(8844,'HuongPTT',1061,25,NULL,'2015-08-20',1,2),(8845,'LienTTH',1061,25,NULL,'2015-08-21',1,2),(8846,'LienTTH',1061,25,NULL,'2015-08-24',1,2),(8847,'HuongPTT',1061,25,NULL,'2015-08-25',1,2),(8848,'LienTTH',1061,25,NULL,'2015-08-26',1,2),(8849,'HuongPTT',1061,25,NULL,'2015-08-27',1,2),(8850,'LienTTH',1061,25,NULL,'2015-08-28',1,2),(8851,'LienTTH',1061,25,NULL,'2015-08-17',1,3),(8852,'HuongPTT',1061,25,NULL,'2015-08-18',1,3),(8853,'LienTTH',1061,25,NULL,'2015-08-19',1,3),(8854,'HuongPTT',1061,25,NULL,'2015-08-20',1,3),(8855,'LienTTH',1061,25,NULL,'2015-08-21',1,3),(8856,'LienTTH',1061,25,NULL,'2015-08-24',1,3),(8857,'HuongPTT',1061,25,NULL,'2015-08-25',1,3),(8858,'LienTTH',1061,25,NULL,'2015-08-26',1,3),(8859,'HuongPTT',1061,25,NULL,'2015-08-27',1,3),(8860,'LienTTH',1061,25,NULL,'2015-08-28',1,3),(8861,'AnNDH',1061,25,NULL,'2015-08-17',1,4),(8862,'AnNDH',1061,25,NULL,'2015-08-19',1,4),(8863,'AnNDH',1061,25,NULL,'2015-08-24',1,4),(8864,'AnNDH',1061,25,NULL,'2015-08-26',1,4),(8865,'SuTV',1061,25,NULL,'2015-08-17',1,5),(8866,'SuTV',1061,25,NULL,'2015-08-18',1,5),(8867,'SuTV',1061,25,NULL,'2015-08-19',1,5),(8868,'SuTV',1061,25,NULL,'2015-08-20',1,5),(8869,'SuTV',1061,25,NULL,'2015-08-21',1,5),(8870,'SuTV',1061,25,NULL,'2015-08-24',1,5),(8871,'SuTV',1061,25,NULL,'2015-08-25',1,5),(8872,'SuTV',1061,25,NULL,'2015-08-26',1,5),(8873,'SuTV',1061,25,NULL,'2015-08-27',1,5),(8874,'SuTV',1061,25,NULL,'2015-08-28',1,5),(8875,'KieuTA',1061,25,NULL,'2015-08-17',1,6),(8876,'KieuTA',1061,25,NULL,'2015-08-18',1,6),(8877,'KieuTA',1061,25,NULL,'2015-08-19',1,6),(8878,'KieuTA',1061,25,NULL,'2015-08-20',1,6),(8879,'KieuTA',1061,25,NULL,'2015-08-21',1,6),(8880,'KieuTA',1061,25,NULL,'2015-08-24',1,6),(8881,'KieuTA',1061,25,NULL,'2015-08-25',1,6),(8882,'KieuTA',1061,25,NULL,'2015-08-26',1,6),(8883,'KieuTA',1061,25,NULL,'2015-08-27',1,6),(8884,'KieuTA',1061,25,NULL,'2015-08-28',1,6),(8885,'HiepTT',1050,25,NULL,'2015-08-17',1,1),(8886,'HiepTT',1050,25,NULL,'2015-08-18',1,1),(8887,'HiepTT',1050,25,NULL,'2015-08-19',1,1),(8888,'HiepTT',1050,25,NULL,'2015-08-20',1,1),(8889,'HiepTT',1050,25,NULL,'2015-08-21',1,1),(8890,'HiepTT',1050,25,NULL,'2015-08-24',1,1),(8891,'HiepTT',1050,25,NULL,'2015-08-25',1,1),(8892,'HiepTT',1050,25,NULL,'2015-08-26',1,1),(8893,'HiepTT',1050,25,NULL,'2015-08-27',1,1),(8894,'HiepTT',1050,25,NULL,'2015-08-28',1,1),(8895,'HungLD',1050,25,NULL,'2015-08-17',1,2),(8896,'HungLD',1050,25,NULL,'2015-08-18',1,2),(8897,'HungLD',1050,25,NULL,'2015-08-19',1,2),(8898,'HungLD',1050,25,NULL,'2015-08-20',1,2),(8899,'HungLD',1050,25,NULL,'2015-08-21',1,2),(8900,'HungLD',1050,25,NULL,'2015-08-24',1,2),(8901,'HungLD',1050,25,NULL,'2015-08-25',1,2),(8902,'HungLD',1050,25,NULL,'2015-08-26',1,2),(8903,'HungLD',1050,25,NULL,'2015-08-27',1,2),(8904,'HungLD',1050,25,NULL,'2015-08-28',1,2),(8905,'KieuTA',1050,25,NULL,'2015-08-18',1,3),(8906,'KieuTA',1050,25,NULL,'2015-08-20',1,3),(8907,'KieuTA',1050,25,NULL,'2015-08-21',1,3),(8908,'KieuTA',1050,25,NULL,'2015-08-25',1,3),(8909,'KieuTA',1050,25,NULL,'2015-08-27',1,3),(8910,'KieuTA',1050,25,NULL,'2015-08-28',1,3),(8911,'AnNDH',1062,25,NULL,'2015-08-17',1,1),(8912,'AnNDH',1062,25,NULL,'2015-08-18',1,1),(8913,'AnNDH',1062,25,NULL,'2015-08-19',1,1),(8914,'AnNDH',1062,25,NULL,'2015-08-20',1,1),(8915,'AnNDH',1062,25,NULL,'2015-08-21',1,1),(8916,'AnNDH',1062,25,NULL,'2015-08-24',1,1),(8917,'AnNDH',1062,25,NULL,'2015-08-25',1,1),(8918,'AnNDH',1062,25,NULL,'2015-08-26',1,1),(8919,'AnNDH',1062,25,NULL,'2015-08-27',1,1),(8920,'AnNDH',1062,25,NULL,'2015-08-28',1,1),(8921,'KieuTA',1062,25,NULL,'2015-08-17',1,2),(8922,'KieuTA',1062,25,NULL,'2015-08-19',1,2),(8923,'KieuTA',1062,25,NULL,'2015-08-24',1,2),(8924,'KieuTA',1062,25,NULL,'2015-08-26',1,2),(8925,'TuDT',1062,25,NULL,'2015-08-17',1,3),(8926,'TuDT',1062,25,NULL,'2015-08-18',1,3),(8927,'TuDT',1062,25,NULL,'2015-08-19',1,3),(8928,'TuDT',1062,25,NULL,'2015-08-20',1,3),(8929,'TuDT',1062,25,NULL,'2015-08-21',1,3),(8930,'TuDT',1062,25,NULL,'2015-08-24',1,3),(8931,'TuDT',1062,25,NULL,'2015-08-25',1,3),(8932,'TuDT',1062,25,NULL,'2015-08-26',1,3),(8933,'TuDT',1062,25,NULL,'2015-08-27',1,3),(8934,'TuDT',1062,25,NULL,'2015-08-28',1,3),(8935,'HiepNT',1062,25,NULL,'2015-08-18',1,4),(8936,'HiepNT',1062,25,NULL,'2015-08-20',1,4),(8937,'HiepNT',1062,25,NULL,'2015-08-21',1,4),(8938,'HiepNT',1062,25,NULL,'2015-08-25',1,4),(8939,'HiepNT',1062,25,NULL,'2015-08-27',1,4),(8940,'HiepNT',1062,25,NULL,'2015-08-28',1,4),(8941,'BinhDTH',1062,25,NULL,'2015-08-17',1,5),(8942,'BinhDTH',1062,25,NULL,'2015-08-18',1,5),(8943,'BinhDTH',1062,25,NULL,'2015-08-19',1,5),(8944,'BinhDTH',1062,25,NULL,'2015-08-20',1,5),(8945,'BinhDTH',1062,25,NULL,'2015-08-21',1,5),(8946,'BinhDTH',1062,25,NULL,'2015-08-24',1,5),(8947,'BinhDTH',1062,25,NULL,'2015-08-25',1,5),(8948,'BinhDTH',1062,25,NULL,'2015-08-26',1,5),(8949,'BinhDTH',1062,25,NULL,'2015-08-27',1,5),(8950,'BinhDTH',1062,25,NULL,'2015-08-28',1,5),(8951,'KhanhKT',1062,25,NULL,'2015-08-17',1,6),(8952,'KhanhKT',1062,25,NULL,'2015-08-18',1,6),(8953,'KhanhKT',1062,25,NULL,'2015-08-19',1,6),(8954,'KhanhKT',1062,25,NULL,'2015-08-20',1,6),(8955,'KhanhKT',1062,25,NULL,'2015-08-21',1,6),(8956,'KhanhKT',1062,25,NULL,'2015-08-24',1,6),(8957,'KhanhKT',1062,25,NULL,'2015-08-25',1,6),(8958,'KhanhKT',1062,25,NULL,'2015-08-26',1,6),(8959,'KhanhKT',1062,25,NULL,'2015-08-27',1,6),(8960,'KhanhKT',1062,25,NULL,'2015-08-28',1,6),(8961,'TaiNT',1063,25,NULL,'2015-08-17',1,1),(8962,'TaiNT',1063,25,NULL,'2015-08-18',1,1),(8963,'TaiNT',1063,25,NULL,'2015-08-19',1,1),(8964,'TaiNT',1063,25,NULL,'2015-08-20',1,1),(8965,'TaiNT',1063,25,NULL,'2015-08-21',1,1),(8966,'TaiNT',1063,25,NULL,'2015-08-24',1,1),(8967,'TaiNT',1063,25,NULL,'2015-08-25',1,1),(8968,'TaiNT',1063,25,NULL,'2015-08-26',1,1),(8969,'TaiNT',1063,25,NULL,'2015-08-27',1,1),(8970,'TaiNT',1063,25,NULL,'2015-08-28',1,1),(8971,'KieuTA',1063,25,NULL,'2015-08-18',1,2),(8972,'KieuTA',1063,25,NULL,'2015-08-20',1,2),(8973,'KieuTA',1063,25,NULL,'2015-08-21',1,2),(8974,'KieuTA',1063,25,NULL,'2015-08-25',1,2),(8975,'KieuTA',1063,25,NULL,'2015-08-27',1,2),(8976,'KieuTA',1063,25,NULL,'2015-08-28',1,2),(8977,'AnNDH',1063,25,NULL,'2015-08-17',1,3),(8978,'AnNDH',1063,25,NULL,'2015-08-18',1,3),(8979,'AnNDH',1063,25,NULL,'2015-08-19',1,3),(8980,'AnNDH',1063,25,NULL,'2015-08-20',1,3),(8981,'AnNDH',1063,25,NULL,'2015-08-21',1,3),(8982,'AnNDH',1063,25,NULL,'2015-08-24',1,3),(8983,'AnNDH',1063,25,NULL,'2015-08-25',1,3),(8984,'AnNDH',1063,25,NULL,'2015-08-26',1,3),(8985,'AnNDH',1063,25,NULL,'2015-08-27',1,3),(8986,'AnNDH',1063,25,NULL,'2015-08-28',1,3),(8987,'CamDTH',1063,25,NULL,'2015-08-18',1,4),(8988,'CamDTH',1063,25,NULL,'2015-08-20',1,4),(8989,'CamDTH',1063,25,NULL,'2015-08-21',1,4),(8990,'CamDTH',1063,25,NULL,'2015-08-25',1,4),(8991,'CamDTH',1063,25,NULL,'2015-08-27',1,4),(8992,'CamDTH',1063,25,NULL,'2015-08-28',1,4),(8993,'TuDT',1063,25,NULL,'2015-08-17',1,5),(8994,'TuDT',1063,25,NULL,'2015-08-18',1,5),(8995,'TuDT',1063,25,NULL,'2015-08-19',1,5),(8996,'TuDT',1063,25,NULL,'2015-08-20',1,5),(8997,'TuDT',1063,25,NULL,'2015-08-21',1,5),(8998,'TuDT',1063,25,NULL,'2015-08-24',1,5),(8999,'TuDT',1063,25,NULL,'2015-08-25',1,5),(9000,'TuDT',1063,25,NULL,'2015-08-26',1,5),(9001,'TuDT',1063,25,NULL,'2015-08-27',1,5),(9002,'TuDT',1063,25,NULL,'2015-08-28',1,5),(9003,'AnNDH',1063,25,NULL,'2015-08-17',1,6),(9004,'AnNDH',1063,25,NULL,'2015-08-18',1,6),(9005,'AnNDH',1063,25,NULL,'2015-08-19',1,6),(9006,'AnNDH',1063,25,NULL,'2015-08-20',1,6),(9007,'AnNDH',1063,25,NULL,'2015-08-21',1,6),(9008,'AnNDH',1063,25,NULL,'2015-08-24',1,6),(9009,'AnNDH',1063,25,NULL,'2015-08-25',1,6),(9010,'AnNDH',1063,25,NULL,'2015-08-26',1,6),(9011,'AnNDH',1063,25,NULL,'2015-08-27',1,6),(9012,'AnNDH',1063,25,NULL,'2015-08-28',1,6),(9013,'PhuongLHK',1064,25,NULL,'2015-08-17',1,4),(9014,'PhuongLHK',1064,25,NULL,'2015-08-18',1,4),(9015,'PhuongLHK',1064,25,NULL,'2015-08-19',1,4),(9016,'PhuongLHK',1064,25,NULL,'2015-08-20',1,4),(9017,'PhuongLHK',1064,25,NULL,'2015-08-21',1,4),(9018,'PhuongLHK',1064,25,NULL,'2015-08-24',1,4),(9019,'PhuongLHK',1064,25,NULL,'2015-08-25',1,4),(9020,'PhuongLHK',1064,25,NULL,'2015-08-26',1,4),(9021,'PhuongLHK',1064,25,NULL,'2015-08-27',1,4),(9022,'PhuongLHK',1064,25,NULL,'2015-08-28',1,4),(9023,'KieuTA',1051,25,NULL,'2015-08-17',1,1),(9024,'KieuTA',1051,25,NULL,'2015-08-19',1,1),(9025,'KieuTA',1051,25,NULL,'2015-08-24',1,1),(9026,'KieuTA',1051,25,NULL,'2015-08-26',1,1),(9027,'HungNH',1051,25,NULL,'2015-08-17',1,2),(9028,'HungNH',1051,25,NULL,'2015-08-18',1,2),(9029,'HungNH',1051,25,NULL,'2015-08-19',1,2),(9030,'HungNH',1051,25,NULL,'2015-08-20',1,2),(9031,'HungNH',1051,25,NULL,'2015-08-21',1,2),(9032,'HungNH',1051,25,NULL,'2015-08-24',1,2),(9033,'HungNH',1051,25,NULL,'2015-08-25',1,2),(9034,'HungNH',1051,25,NULL,'2015-08-26',1,2),(9035,'HungNH',1051,25,NULL,'2015-08-27',1,2),(9036,'HungNH',1051,25,NULL,'2015-08-28',1,2),(9037,'ThuyetDTM',1051,25,NULL,'2015-08-17',1,3),(9038,'ThuyetDTM',1051,25,NULL,'2015-08-18',1,3),(9039,'ThuyetDTM',1051,25,NULL,'2015-08-19',1,3),(9040,'ThuyetDTM',1051,25,NULL,'2015-08-20',1,3),(9041,'ThuyetDTM',1051,25,NULL,'2015-08-21',1,3),(9042,'ThuyetDTM',1051,25,NULL,'2015-08-24',1,3),(9043,'ThuyetDTM',1051,25,NULL,'2015-08-25',1,3),(9044,'ThuyetDTM',1051,25,NULL,'2015-08-26',1,3),(9045,'ThuyetDTM',1051,25,NULL,'2015-08-27',1,3),(9046,'ThuyetDTM',1051,25,NULL,'2015-08-28',1,3),(9047,'Cecile',1051,25,NULL,'2015-08-17',1,4),(9048,'Cecile',1051,25,NULL,'2015-08-18',1,4),(9049,'Cecile',1051,25,NULL,'2015-08-19',1,4),(9050,'Cecile',1051,25,NULL,'2015-08-20',1,4),(9051,'Cecile',1051,25,NULL,'2015-08-21',1,4),(9052,'Cecile',1051,25,NULL,'2015-08-24',1,4),(9053,'Cecile',1051,25,NULL,'2015-08-25',1,4),(9054,'Cecile',1051,25,NULL,'2015-08-26',1,4),(9055,'Cecile',1051,25,NULL,'2015-08-27',1,4),(9056,'Cecile',1051,25,NULL,'2015-08-28',1,4),(9057,'CamDTH',1051,25,NULL,'2015-08-17',1,5),(9058,'CamDTH',1051,25,NULL,'2015-08-19',1,5),(9059,'CamDTH',1051,25,NULL,'2015-08-24',1,5),(9060,'CamDTH',1051,25,NULL,'2015-08-26',1,5),(9061,'HungNH',1051,25,NULL,'2015-08-17',1,6),(9062,'HungNH',1051,25,NULL,'2015-08-18',1,6),(9063,'HungNH',1051,25,NULL,'2015-08-19',1,6),(9064,'HungNH',1051,25,NULL,'2015-08-20',1,6),(9065,'HungNH',1051,25,NULL,'2015-08-21',1,6),(9066,'HungNH',1051,25,NULL,'2015-08-24',1,6),(9067,'HungNH',1051,25,NULL,'2015-08-25',1,6),(9068,'HungNH',1051,25,NULL,'2015-08-26',1,6),(9069,'HungNH',1051,25,NULL,'2015-08-27',1,6),(9070,'HungNH',1051,25,NULL,'2015-08-28',1,6),(9071,'HungLD',1052,25,NULL,'2015-08-17',1,4),(9072,'HungLD',1052,25,NULL,'2015-08-18',1,4),(9073,'HungLD',1052,25,NULL,'2015-08-19',1,4),(9074,'HungLD',1052,25,NULL,'2015-08-20',1,4),(9075,'HungLD',1052,25,NULL,'2015-08-21',1,4),(9076,'HungLD',1052,25,NULL,'2015-08-24',1,4),(9077,'HungLD',1052,25,NULL,'2015-08-25',1,4),(9078,'HungLD',1052,25,NULL,'2015-08-26',1,4),(9079,'HungLD',1052,25,NULL,'2015-08-27',1,4),(9080,'HungLD',1052,25,NULL,'2015-08-28',1,4),(9081,'CamDTH',1052,25,NULL,'2015-08-18',1,5),(9082,'CamDTH',1052,25,NULL,'2015-08-20',1,5),(9083,'CamDTH',1052,25,NULL,'2015-08-21',1,5),(9084,'CamDTH',1052,25,NULL,'2015-08-25',1,5),(9085,'CamDTH',1052,25,NULL,'2015-08-27',1,5),(9086,'CamDTH',1052,25,NULL,'2015-08-28',1,5),(9087,'TaiNT',1052,25,NULL,'2015-08-17',1,6),(9088,'TaiNT',1052,25,NULL,'2015-08-18',1,6),(9089,'TaiNT',1052,25,NULL,'2015-08-19',1,6),(9090,'TaiNT',1052,25,NULL,'2015-08-20',1,6),(9091,'TaiNT',1052,25,NULL,'2015-08-21',1,6),(9092,'TaiNT',1052,25,NULL,'2015-08-24',1,6),(9093,'TaiNT',1052,25,NULL,'2015-08-25',1,6),(9094,'TaiNT',1052,25,NULL,'2015-08-26',1,6),(9095,'TaiNT',1052,25,NULL,'2015-08-27',1,6),(9096,'TaiNT',1052,25,NULL,'2015-08-28',1,6),(9097,'ThanhDT',1065,25,NULL,'2015-08-17',1,4),(9098,'ThanhDT',1065,25,NULL,'2015-08-18',1,4),(9099,'ThanhDT',1065,25,NULL,'2015-08-19',1,4),(9100,'ThanhDT',1065,25,NULL,'2015-08-20',1,4),(9101,'ThanhDT',1065,25,NULL,'2015-08-21',1,4),(9102,'ThanhDT',1065,25,NULL,'2015-08-24',1,4),(9103,'ThanhDT',1065,25,NULL,'2015-08-25',1,4),(9104,'ThanhDT',1065,25,NULL,'2015-08-26',1,4),(9105,'ThanhDT',1065,25,NULL,'2015-08-27',1,4),(9106,'ThanhDT',1065,25,NULL,'2015-08-28',1,4),(9107,'AnhNT',1065,25,NULL,'2015-08-17',1,5),(9108,'AnhNT',1065,25,NULL,'2015-08-18',1,5),(9109,'AnhNT',1065,25,NULL,'2015-08-19',1,5),(9110,'AnhNT',1065,25,NULL,'2015-08-20',1,5),(9111,'AnhNT',1065,25,NULL,'2015-08-21',1,5),(9112,'AnhNT',1065,25,NULL,'2015-08-24',1,5),(9113,'AnhNT',1065,25,NULL,'2015-08-25',1,5),(9114,'AnhNT',1065,25,NULL,'2015-08-26',1,5),(9115,'AnhNT',1065,25,NULL,'2015-08-27',1,5),(9116,'AnhNT',1065,25,NULL,'2015-08-28',1,5),(9117,'PhuongLHK',1065,25,NULL,'2015-08-18',1,6),(9118,'PhuongLHK',1065,25,NULL,'2015-08-20',1,6),(9119,'PhuongLHK',1065,25,NULL,'2015-08-21',1,6),(9120,'PhuongLHK',1065,25,NULL,'2015-08-25',1,6),(9121,'PhuongLHK',1065,25,NULL,'2015-08-27',1,6),(9122,'PhuongLHK',1065,25,NULL,'2015-08-28',1,6),(9123,'TriBD',1068,25,NULL,'2015-08-18',1,1),(9124,'TriBD',1068,25,NULL,'2015-08-20',1,1),(9125,'TriBD',1068,25,NULL,'2015-08-25',1,1),(9126,'TriBD',1068,25,NULL,'2015-08-27',1,1),(9127,'TriBD',1068,25,NULL,'2015-08-18',1,2),(9128,'TriBD',1068,25,NULL,'2015-08-20',1,2),(9129,'TriBD',1068,25,NULL,'2015-08-25',1,2),(9130,'TriBD',1068,25,NULL,'2015-08-27',1,2),(9131,'LoiND',1068,25,NULL,'2015-08-17',1,4),(9132,'LoiND',1068,25,NULL,'2015-08-18',1,4),(9133,'LoiND',1068,25,NULL,'2015-08-19',1,4),(9134,'LoiND',1068,25,NULL,'2015-08-20',1,4),(9135,'LoiND',1068,25,NULL,'2015-08-21',1,4),(9136,'LoiND',1068,25,NULL,'2015-08-24',1,4),(9137,'LoiND',1068,25,NULL,'2015-08-25',1,4),(9138,'LoiND',1068,25,NULL,'2015-08-26',1,4),(9139,'LoiND',1068,25,NULL,'2015-08-27',1,4),(9140,'LoiND',1068,25,NULL,'2015-08-28',1,4),(9141,'ChungKTT',1068,25,NULL,'2015-08-17',1,5),(9142,'ChungKTT',1068,25,NULL,'2015-08-18',1,5),(9143,'ChungKTT',1068,25,NULL,'2015-08-19',1,5),(9144,'ChungKTT',1068,25,NULL,'2015-08-20',1,5),(9145,'ChungKTT',1068,25,NULL,'2015-08-21',1,5),(9146,'ChungKTT',1068,25,NULL,'2015-08-24',1,5),(9147,'ChungKTT',1068,25,NULL,'2015-08-25',1,5),(9148,'ChungKTT',1068,25,NULL,'2015-08-26',1,5),(9149,'ChungKTT',1068,25,NULL,'2015-08-27',1,5),(9150,'ChungKTT',1068,25,NULL,'2015-08-28',1,5),(9151,'PhuongLHK',1068,25,NULL,'2015-08-17',1,6),(9152,'PhuongLHK',1068,25,NULL,'2015-08-19',1,6),(9153,'PhuongLHK',1068,25,NULL,'2015-08-24',1,6),(9154,'PhuongLHK',1068,25,NULL,'2015-08-26',1,6),(9155,'ChauLH',1067,25,NULL,'2015-08-17',1,1),(9156,'ChauLH',1067,25,NULL,'2015-08-18',1,1),(9157,'ChauLH',1067,25,NULL,'2015-08-19',1,1),(9158,'ChauLH',1067,25,NULL,'2015-08-20',1,1),(9159,'ChauLH',1067,25,NULL,'2015-08-21',1,1),(9160,'ChauLH',1067,25,NULL,'2015-08-24',1,1),(9161,'ChauLH',1067,25,NULL,'2015-08-25',1,1),(9162,'ChauLH',1067,25,NULL,'2015-08-26',1,1),(9163,'ChauLH',1067,25,NULL,'2015-08-27',1,1),(9164,'ChauLH',1067,25,NULL,'2015-08-28',1,1),(9165,'PhuongLHK',1067,25,NULL,'2015-08-17',1,2),(9166,'PhuongLHK',1067,25,NULL,'2015-08-19',1,2),(9167,'PhuongLHK',1067,25,NULL,'2015-08-24',1,2),(9168,'PhuongLHK',1067,25,NULL,'2015-08-26',1,2),(9169,'HiepNT',1067,25,NULL,'2015-08-17',1,3),(9170,'HiepNT',1067,25,NULL,'2015-08-18',1,3),(9171,'HiepNT',1067,25,NULL,'2015-08-19',1,3),(9172,'HiepNT',1067,25,NULL,'2015-08-20',1,3),(9173,'HiepNT',1067,25,NULL,'2015-08-21',1,3),(9174,'HiepNT',1067,25,NULL,'2015-08-24',1,3),(9175,'HiepNT',1067,25,NULL,'2015-08-25',1,3),(9176,'HiepNT',1067,25,NULL,'2015-08-26',1,3),(9177,'HiepNT',1067,25,NULL,'2015-08-27',1,3),(9178,'HiepNT',1067,25,NULL,'2015-08-28',1,3),(9179,'VinhDP',1054,25,NULL,'2015-08-17',1,1),(9180,'VinhDP',1054,25,NULL,'2015-08-18',1,1),(9181,'VinhDP',1054,25,NULL,'2015-08-19',1,1),(9182,'VinhDP',1054,25,NULL,'2015-08-20',1,1),(9183,'VinhDP',1054,25,NULL,'2015-08-21',1,1),(9184,'VinhDP',1054,25,NULL,'2015-08-24',1,1),(9185,'VinhDP',1054,25,NULL,'2015-08-25',1,1),(9186,'VinhDP',1054,25,NULL,'2015-08-26',1,1),(9187,'VinhDP',1054,25,NULL,'2015-08-27',1,1),(9188,'VinhDP',1054,25,NULL,'2015-08-28',1,1),(9189,'AnNDH',1054,25,NULL,'2015-08-17',1,2),(9190,'AnNDH',1054,25,NULL,'2015-08-18',1,2),(9191,'AnNDH',1054,25,NULL,'2015-08-19',1,2),(9192,'AnNDH',1054,25,NULL,'2015-08-20',1,2),(9193,'AnNDH',1054,25,NULL,'2015-08-21',1,2),(9194,'AnNDH',1054,25,NULL,'2015-08-24',1,2),(9195,'AnNDH',1054,25,NULL,'2015-08-25',1,2),(9196,'AnNDH',1054,25,NULL,'2015-08-26',1,2),(9197,'AnNDH',1054,25,NULL,'2015-08-27',1,2),(9198,'AnNDH',1054,25,NULL,'2015-08-28',1,2),(9199,'HoangNT',1054,25,NULL,'2015-08-17',1,3),(9200,'HoangNT',1054,25,NULL,'2015-08-19',1,3),(9201,'HoangNT',1054,25,NULL,'2015-08-24',1,3),(9202,'HoangNT',1054,25,NULL,'2015-08-26',1,3),(9203,'HiepNT',1054,25,NULL,'2015-08-17',1,4),(9204,'HiepNT',1054,25,NULL,'2015-08-19',1,4),(9205,'HiepNT',1054,25,NULL,'2015-08-24',1,4),(9206,'HiepNT',1054,25,NULL,'2015-08-26',1,4),(9207,'HiepTT',1054,25,NULL,'2015-08-17',1,5),(9208,'HiepTT',1054,25,NULL,'2015-08-18',1,5),(9209,'HiepTT',1054,25,NULL,'2015-08-19',1,5),(9210,'HiepTT',1054,25,NULL,'2015-08-20',1,5),(9211,'HiepTT',1054,25,NULL,'2015-08-21',1,5),(9212,'HiepTT',1054,25,NULL,'2015-08-24',1,5),(9213,'HiepTT',1054,25,NULL,'2015-08-25',1,5),(9214,'HiepTT',1054,25,NULL,'2015-08-26',1,5),(9215,'HiepTT',1054,25,NULL,'2015-08-27',1,5),(9216,'HiepTT',1054,25,NULL,'2015-08-28',1,5),(9217,'BinhDTH',1054,25,NULL,'2015-08-17',1,6),(9218,'BinhDTH',1054,25,NULL,'2015-08-18',1,6),(9219,'BinhDTH',1054,25,NULL,'2015-08-19',1,6),(9220,'BinhDTH',1054,25,NULL,'2015-08-20',1,6),(9221,'BinhDTH',1054,25,NULL,'2015-08-21',1,6),(9222,'BinhDTH',1054,25,NULL,'2015-08-24',1,6),(9223,'BinhDTH',1054,25,NULL,'2015-08-25',1,6),(9224,'BinhDTH',1054,25,NULL,'2015-08-26',1,6),(9225,'BinhDTH',1054,25,NULL,'2015-08-27',1,6),(9226,'BinhDTH',1054,25,NULL,'2015-08-28',1,6),(9227,'NhuLTL',1066,25,NULL,'2015-08-17',1,3),(9228,'NhuLTL',1066,25,NULL,'2015-08-18',1,3),(9229,'NhuLTL',1066,25,NULL,'2015-08-19',1,3),(9230,'NhuLTL',1066,25,NULL,'2015-08-20',1,3),(9231,'NhuLTL',1066,25,NULL,'2015-08-21',1,3),(9232,'NhuLTL',1066,25,NULL,'2015-08-24',1,3),(9233,'NhuLTL',1066,25,NULL,'2015-08-25',1,3),(9234,'NhuLTL',1066,25,NULL,'2015-08-26',1,3),(9235,'NhuLTL',1066,25,NULL,'2015-08-27',1,3),(9236,'NhuLTL',1066,25,NULL,'2015-08-28',1,3),(9237,'HoangNT',1066,25,NULL,'2015-08-17',1,4),(9238,'HoangNT',1066,25,NULL,'2015-08-18',1,4),(9239,'HoangNT',1066,25,NULL,'2015-08-19',1,4),(9240,'HoangNT',1066,25,NULL,'2015-08-20',1,4),(9241,'HoangNT',1066,25,NULL,'2015-08-21',1,4),(9242,'HoangNT',1066,25,NULL,'2015-08-24',1,4),(9243,'HoangNT',1066,25,NULL,'2015-08-25',1,4),(9244,'HoangNT',1066,25,NULL,'2015-08-26',1,4),(9245,'HoangNT',1066,25,NULL,'2015-08-27',1,4),(9246,'HoangNT',1066,25,NULL,'2015-08-28',1,4),(9247,'TuDT',1066,25,NULL,'2015-08-17',1,6),(9248,'TuDT',1066,25,NULL,'2015-08-18',1,6),(9249,'TuDT',1066,25,NULL,'2015-08-19',1,6),(9250,'TuDT',1066,25,NULL,'2015-08-20',1,6),(9251,'TuDT',1066,25,NULL,'2015-08-21',1,6),(9252,'TuDT',1066,25,NULL,'2015-08-24',1,6),(9253,'TuDT',1066,25,NULL,'2015-08-25',1,6),(9254,'TuDT',1066,25,NULL,'2015-08-26',1,6),(9255,'TuDT',1066,25,NULL,'2015-08-27',1,6),(9256,'TuDT',1066,25,NULL,'2015-08-28',1,6),(9257,'HungLD',1069,25,NULL,'2015-08-17',1,1),(9258,'HungLD',1069,25,NULL,'2015-08-19',1,1),(9259,'HungLD',1069,25,NULL,'2015-08-24',1,1),(9260,'HungLD',1069,25,NULL,'2015-08-26',1,1),(9261,'KhanhKT',1069,25,NULL,'2015-08-17',1,2),(9262,'KhanhKT',1069,25,NULL,'2015-08-18',1,2),(9263,'KhanhKT',1069,25,NULL,'2015-08-19',1,2),(9264,'KhanhKT',1069,25,NULL,'2015-08-20',1,2),(9265,'KhanhKT',1069,25,NULL,'2015-08-21',1,2),(9266,'KhanhKT',1069,25,'Đã đổi sang phòng: 101','2015-08-24',0,2),(9267,'KhanhKT',1069,25,'Đã đổi sang phòng: 101','2015-08-25',0,2),(9268,'KhanhKT',1069,25,NULL,'2015-08-26',1,2),(9269,'KhanhKT',1069,25,NULL,'2015-08-27',1,2),(9270,'KhanhKT',1069,25,NULL,'2015-08-28',1,2),(9271,'TaiNT',1069,25,NULL,'2015-08-17',1,3),(9272,'TaiNT',1069,25,NULL,'2015-08-18',1,3),(9273,'TaiNT',1069,25,NULL,'2015-08-19',1,3),(9274,'TaiNT',1069,25,NULL,'2015-08-20',1,3),(9275,'TaiNT',1069,25,NULL,'2015-08-21',1,3),(9276,'TaiNT',1069,25,NULL,'2015-08-24',1,3),(9277,'TaiNT',1069,25,NULL,'2015-08-25',1,3),(9278,'TaiNT',1069,25,NULL,'2015-08-26',1,3),(9279,'TaiNT',1069,25,NULL,'2015-08-27',1,3),(9280,'TaiNT',1069,25,NULL,'2015-08-28',1,3),(9281,'VanTTN',1070,25,NULL,'2015-08-17',1,2),(9282,'VanTTN',1070,25,NULL,'2015-08-18',1,2),(9283,'VanTTN',1070,25,NULL,'2015-08-19',1,2),(9284,'VanTTN',1070,25,NULL,'2015-08-20',1,2),(9285,'VanTTN',1070,25,NULL,'2015-08-21',1,2),(9286,'VanTTN',1070,25,NULL,'2015-08-24',1,2),(9287,'VanTTN',1070,25,NULL,'2015-08-25',1,2),(9288,'VanTTN',1070,25,NULL,'2015-08-26',1,2),(9289,'VanTTN',1070,25,NULL,'2015-08-27',1,2),(9290,'VanTTN',1070,25,NULL,'2015-08-28',1,2),(9291,'VinhDP',1070,25,NULL,'2015-08-17',1,3),(9292,'VinhDP',1070,25,NULL,'2015-08-18',1,3),(9293,'VinhDP',1070,25,NULL,'2015-08-19',1,3),(9294,'VinhDP',1070,25,NULL,'2015-08-20',1,3),(9295,'VinhDP',1070,25,NULL,'2015-08-21',1,3),(9296,'VinhDP',1070,25,NULL,'2015-08-24',1,3),(9297,'VinhDP',1070,25,NULL,'2015-08-25',1,3),(9298,'VinhDP',1070,25,NULL,'2015-08-26',1,3),(9299,'VinhDP',1070,25,NULL,'2015-08-27',1,3),(9300,'VinhDP',1070,25,NULL,'2015-08-28',1,3),(9301,'VanTTN',1070,25,NULL,'2015-08-17',1,4),(9302,'VanTTN',1070,25,NULL,'2015-08-18',1,4),(9303,'VanTTN',1070,25,NULL,'2015-08-19',1,4),(9304,'VanTTN',1070,25,NULL,'2015-08-20',1,4),(9305,'VanTTN',1070,25,NULL,'2015-08-21',1,4),(9306,'VanTTN',1070,25,NULL,'2015-08-24',1,4),(9307,'VanTTN',1070,25,NULL,'2015-08-25',1,4),(9308,'VanTTN',1070,25,NULL,'2015-08-26',1,4),(9309,'VanTTN',1070,25,NULL,'2015-08-27',1,4),(9310,'VanTTN',1070,25,NULL,'2015-08-28',1,4),(9311,'VinhDP',1070,25,NULL,'2015-08-17',1,6),(9312,'VinhDP',1070,25,NULL,'2015-08-18',1,6),(9313,'VinhDP',1070,25,NULL,'2015-08-19',1,6),(9314,'VinhDP',1070,25,NULL,'2015-08-20',1,6),(9315,'VinhDP',1070,25,NULL,'2015-08-21',1,6),(9316,'VinhDP',1070,25,NULL,'2015-08-24',1,6),(9317,'VinhDP',1070,25,NULL,'2015-08-25',1,6),(9318,'VinhDP',1070,25,NULL,'2015-08-26',1,6),(9319,'VinhDP',1070,25,NULL,'2015-08-27',1,6),(9320,'VinhDP',1070,25,NULL,'2015-08-28',1,6),(9321,'SuTV',1071,25,NULL,'2015-08-17',1,1),(9322,'SuTV',1071,25,NULL,'2015-08-18',1,1),(9323,'SuTV',1071,25,NULL,'2015-08-19',1,1),(9324,'SuTV',1071,25,NULL,'2015-08-20',1,1),(9325,'SuTV',1071,25,NULL,'2015-08-21',1,1),(9326,'SuTV',1071,25,NULL,'2015-08-24',1,1),(9327,'SuTV',1071,25,NULL,'2015-08-25',1,1),(9328,'SuTV',1071,25,NULL,'2015-08-26',1,1),(9329,'SuTV',1071,25,NULL,'2015-08-27',1,1),(9330,'SuTV',1071,25,NULL,'2015-08-28',1,1),(9331,'TaiNT',1071,25,NULL,'2015-08-17',1,2),(9332,'TaiNT',1071,25,NULL,'2015-08-18',1,2),(9333,'TaiNT',1071,25,NULL,'2015-08-19',1,2),(9334,'TaiNT',1071,25,NULL,'2015-08-20',1,2),(9335,'TaiNT',1071,25,NULL,'2015-08-21',1,2),(9336,'TaiNT',1071,25,NULL,'2015-08-24',1,2),(9337,'TaiNT',1071,25,NULL,'2015-08-25',1,2),(9338,'TaiNT',1071,25,NULL,'2015-08-26',1,2),(9339,'TaiNT',1071,25,NULL,'2015-08-27',1,2),(9340,'TaiNT',1071,25,NULL,'2015-08-28',1,2),(9341,'TaiNT',1071,25,NULL,'2015-08-17',1,4),(9342,'TaiNT',1071,25,NULL,'2015-08-18',1,4),(9343,'TaiNT',1071,25,NULL,'2015-08-19',1,4),(9344,'TaiNT',1071,25,NULL,'2015-08-20',1,4),(9345,'TaiNT',1071,25,NULL,'2015-08-21',1,4),(9346,'TaiNT',1071,25,NULL,'2015-08-24',1,4),(9347,'TaiNT',1071,25,NULL,'2015-08-25',1,4),(9348,'TaiNT',1071,25,NULL,'2015-08-26',1,4),(9349,'TaiNT',1071,25,NULL,'2015-08-27',1,4),(9350,'TaiNT',1071,25,NULL,'2015-08-28',1,4),(9351,'HoangNT',1071,25,NULL,'2015-08-17',1,5),(9352,'HoangNT',1071,25,NULL,'2015-08-18',1,5),(9353,'HoangNT',1071,25,NULL,'2015-08-19',1,5),(9354,'HoangNT',1071,25,NULL,'2015-08-20',1,5),(9355,'HoangNT',1071,25,NULL,'2015-08-21',1,5),(9356,'HoangNT',1071,25,NULL,'2015-08-24',1,5),(9357,'HoangNT',1071,25,NULL,'2015-08-25',1,5),(9358,'HoangNT',1071,25,NULL,'2015-08-26',1,5),(9359,'HoangNT',1071,25,NULL,'2015-08-27',1,5),(9360,'HoangNT',1071,25,NULL,'2015-08-28',1,5),(9361,'HungNH',1072,25,NULL,'2015-08-17',1,4),(9362,'HungNH',1072,25,NULL,'2015-08-19',1,4),(9363,'HungNH',1072,25,NULL,'2015-08-21',1,4),(9364,'HungNH',1072,25,NULL,'2015-08-24',1,4),(9365,'HungNH',1072,25,NULL,'2015-08-26',1,4),(9366,'HungNH',1072,25,NULL,'2015-08-28',1,4),(9367,'VinhDP',1072,25,NULL,'2015-08-17',1,5),(9368,'VinhDP',1072,25,NULL,'2015-08-18',1,5),(9369,'VinhDP',1072,25,NULL,'2015-08-19',1,5),(9370,'VinhDP',1072,25,NULL,'2015-08-20',1,5),(9371,'VinhDP',1072,25,NULL,'2015-08-21',1,5),(9372,'VinhDP',1072,25,NULL,'2015-08-24',1,5),(9373,'VinhDP',1072,25,NULL,'2015-08-25',1,5),(9374,'VinhDP',1072,25,NULL,'2015-08-26',1,5),(9375,'VinhDP',1072,25,NULL,'2015-08-27',1,5),(9376,'VinhDP',1072,25,NULL,'2015-08-28',1,5),(9377,'SuTV',1072,25,NULL,'2015-08-17',1,6),(9378,'SuTV',1072,25,NULL,'2015-08-18',1,6),(9379,'SuTV',1072,25,NULL,'2015-08-19',1,6),(9380,'SuTV',1072,25,NULL,'2015-08-20',1,6),(9381,'SuTV',1072,25,NULL,'2015-08-21',1,6),(9382,'SuTV',1072,25,NULL,'2015-08-24',1,6),(9383,'SuTV',1072,25,NULL,'2015-08-25',1,6),(9384,'SuTV',1072,25,NULL,'2015-08-26',1,6),(9385,'SuTV',1072,25,NULL,'2015-08-27',1,6),(9386,'SuTV',1072,25,NULL,'2015-08-28',1,6),(9387,'KhanhKT',1049,25,'Đổi sang phòng 102','2015-08-14',0,4),(9388,'KhanhKT',1049,25,'Đổi sang phòng 102','2015-08-14',0,5),(9389,'KhanhKT',1055,25,'Thay đổi phòng từ phòng 101 sang phòng 102','2015-08-14',1,4),(9390,'KhanhKT',1055,25,'Đổi sang phòng 101','2015-08-14',0,5),(9391,'KhanhKT',1049,25,'Thay đổi phòng từ phòng 102 sang phòng 101','2015-08-14',1,5),(9392,'HoangNT',1049,25,'Thay đổi phòng từ phòng 113 sang phòng 101','2015-08-17',1,2),(9393,'HoangNT',1058,25,'Thay đổi phòng từ phòng 113 sang phòng 103','2015-08-18',1,2),(9394,'TaiNT',1060,25,'Thay đổi phòng từ phòng 113 sang phòng 115','2015-08-18',1,5),(9400,'KhanhKT',1049,12,'Nhập tay','2015-08-24',1,1),(9401,'KhanhKT',1049,12,'Nhập tay','2015-08-25',1,1),(9402,'KhanhKT',1049,12,'Nhập tay','2015-08-24',1,2),(9403,'KhanhKT',1049,12,'Nhập tay','2015-08-25',1,2),(9404,'KhanhKT',1049,21,'Nhập tay','2015-08-22',1,1),(9405,'KhanhKT',1049,21,'Nhập tay','2015-08-22',1,2),(9406,'KhanhKT',1049,21,'Nhập tay','2015-08-22',1,3),(9407,'KhanhKT',1049,21,'Nhập tay','2015-08-22',1,4),(9408,'KhanhKT',1049,21,'Nhập tay','2015-08-22',1,5),(9409,'ChungKTT',1049,21,'Nhập tay','2015-08-22',1,6);
/*!40000 ALTER TABLE `tblschedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblscheduleconfig`
--

DROP TABLE IF EXISTS `tblscheduleconfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblscheduleconfig` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Slot` int(11) NOT NULL,
  `TimeFrom` time NOT NULL,
  `TimeTo` time NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblscheduleconfig`
--

LOCK TABLES `tblscheduleconfig` WRITE;
/*!40000 ALTER TABLE `tblscheduleconfig` DISABLE KEYS */;
INSERT INTO `tblscheduleconfig` VALUES (1,1,'07:00:00','08:30:00'),(2,2,'08:45:00','10:15:00'),(3,3,'10:30:00','12:00:00'),(4,4,'12:30:00','14:00:00'),(5,5,'14:15:00','15:45:00'),(6,6,'16:00:00','17:30:00');
/*!40000 ALTER TABLE `tblscheduleconfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblsystemconfiguration`
--

DROP TABLE IF EXISTS `tblsystemconfiguration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsystemconfiguration` (
  `key` varchar(50) CHARACTER SET utf8 NOT NULL,
  `value` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `Description` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblsystemconfiguration`
--

LOCK TABLES `tblsystemconfiguration` WRITE;
/*!40000 ALTER TABLE `tblsystemconfiguration` DISABLE KEYS */;
INSERT INTO `tblsystemconfiguration` VALUES ('CHAIRHIGH','3','Người dùng đánh giá cho ghế \"Không thể sử dụng được\"'),('CHAIRLOW','1','Người dùng đánh giá cho Ghế \"Có thể sử dụng được\"'),('SERIOUSLYDAMGED','50','Tổng bàn hoặc ghế bị hư quá 50% tổng số.'),('TABLEHIGH','5','Người dùng đánh giá cho bàn \"Không thể sử dụng được\"'),('TABLELOW','2','Người dùng đánh giá cho bàn \"Có thể sử dụng được\"'),('USERHIGH_PRIORITYHIGH','50','Người dùng đánh giá \"Không sử dụng được\" và độ ưu tiên là \"Cao\"'),('USERHIGH_PRIORITYLOW','15','Người dùng đánh giá \"Không thể sử dụng được\" và độ ưu tiên là \"Thấp\"'),('USERHIGH_PRIORITYMEDIUM','30','Người dùng đánh giá \"Không thể sử dụng được\" và độ ưu tiên là \"Trung bình\"'),('USERLOW_PRIORITYHIGH','20','Người dùng đánh giá \"Có thể sử dụng được\" và độ ưu tiên là \"Cao\"'),('USERLOW_PRIORITYLOW','10','Người dùng đánh giá \"Có thể sử dụng được\" và độ ưu tiên là \"Thấp\"'),('USERLOW_PRIORITYMEDIUM','10','Người dùng đánh giá \"Có thể sử dụng được\" và độ ưu tiên là \"\"Trung bình\"');
/*!40000 ALTER TABLE `tblsystemconfiguration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbluser`
--

DROP TABLE IF EXISTS `tbluser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbluser` (
  `Username` varchar(30) NOT NULL,
  `RoleId` int(11) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Status` tinyint(1) NOT NULL,
  PRIMARY KEY (`Username`),
  KEY `FKA5A810A9A9DD896A` (`RoleId`),
  CONSTRAINT `FKA5A810A9A9DD896A` FOREIGN KEY (`RoleId`) REFERENCES `tblrole` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbluser`
--

LOCK TABLES `tbluser` WRITE;
/*!40000 ALTER TABLE `tbluser` DISABLE KEYS */;
INSERT INTO `tbluser` VALUES ('admin',1,'123456',1),('AnhNT',3,'123456',1),('AnNDH',3,'123456',1),('BaoNQ',3,'123456',1),('BinhDTH',3,'123456',1),('CamDTH',3,'123456',1),('Cecile',3,'123456',1),('ChauLH',3,'123456',1),('ChungKTT',3,'1234567',1),('Gerardo',3,'123456',1),('HiepNT',3,'123456',1),('HiepTT',3,'1234567',1),('HoangNT',3,'1234567',1),('HungLD',3,'123456',1),('HungNH',3,'1234567',1),('hungtv',3,'123456',1),('HuongPTT',3,'123456',1),('KhanhKT',3,'1234567',1),('KieuTA',3,'1234567',1),('LienTTH',3,'123456',1),('LoiND',3,'123456',1),('NhuLTL',3,'123456',1),('PhuongLHK',3,'123456',1),('staff',2,'123456',1),('SuTV',3,'1234567',1),('TaiNT',3,'123456',1),('teacher',3,'123456',1),('ThanhDT',3,'123456',1),('ThuyetDTM',3,'123456',1),('TriBD',3,'123456',1),('TuDT',3,'123456',1),('VanTTN',3,'123456',1),('VinhDP',3,'123456',1);
/*!40000 ALTER TABLE `tbluser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbluserinfo`
--

DROP TABLE IF EXISTS `tbluserinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbluserinfo` (
  `Username` varchar(30) NOT NULL,
  `FullName` varchar(50) NOT NULL,
  `Phone` varchar(50) NOT NULL,
  `LastLogin` datetime(6) NOT NULL,
  `DeviceId` varchar(255) DEFAULT NULL,
  `DownloadTime` bigint(8) DEFAULT NULL,
  PRIMARY KEY (`Username`),
  CONSTRAINT `FK_tblUserInfo_tblUser` FOREIGN KEY (`Username`) REFERENCES `tbluser` (`Username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbluserinfo`
--

LOCK TABLES `tbluserinfo` WRITE;
/*!40000 ALTER TABLE `tbluserinfo` DISABLE KEYS */;
INSERT INTO `tbluserinfo` VALUES ('admin','admin','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('AnhNT','AnhNT','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('AnNDH','AnNDH','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('BaoNQ','BaoNQ','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('BinhDTH','BinhDTH','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('CamDTH','CamDTH','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('Cecile','Cecile','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('ChauLH','ChauLH','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('ChungKTT','ChungKTT','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('Gerardo','Gerardo','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('HiepNT','HiepNT','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('HiepTT','HiepTT','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('HoangNT','HoangNT','+841688167252','2015-08-17 06:01:39.662000','APA91bGvaSo4RX2cAgOjklL8HKuCyltnGSHHLaj3NeUrkWJLf7GRB8C2bGBquhgGJNpPX2jpAWzEe9yj2HfekxZ0PFt2l1k6-bvtrm7NBiX-mHYt8QXwvM9tjlMjKKrE0xFUIYlgjgf3qeJVStKGChJLom6YX7m1qA',1439766100972),('HungLD','HungLD','+841688167252','2015-08-04 07:46:15.293000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',1438649177110),('HungNH','HungNH','+841688167252','2015-08-11 07:00:17.191000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',1439188099881),('hungtv','hungtv','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('HuongPTT','HuongPTT','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('KhanhKT','KhanhKT','+841688167252','2015-08-14 14:08:23.980000','APA91bGvaSo4RX2cAgOjklL8HKuCyltnGSHHLaj3NeUrkWJLf7GRB8C2bGBquhgGJNpPX2jpAWzEe9yj2HfekxZ0PFt2l1k6-bvtrm7NBiX-mHYt8QXwvM9tjlMjKKrE0xFUIYlgjgf3qeJVStKGChJLom6YX7m1qA',1439539553492),('KieuTA','KieuTA','+841688167252','2015-07-23 08:45:34.706000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('LienTTH','LienTTH','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('LoiND','LoiND','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('NhuLTL','NhuLTL','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('PhuongLHK','PhuongLHK','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('staff','Doan Nguyen','+841688167252','2015-07-21 09:08:26.547000','APA91bHxvLq2fIHYV9hH1WAWCb2IkrLQDeN47HKrAhaNesoLmpsbJVvDMK8g1TWVXeXtSgoZAn12N7wZr0SgXUcDy_wh5b-G-OR1E5tnNRb4vDaCjV7ClamsAowysqpeKd9akg_qhSK_j5Be-SXFXf-MaZr_FQo_gw',1439875260084),('SuTV','SuTV','+841688167252','2015-08-10 07:00:27.478000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',1439164896898),('TaiNT','TaiNT','+841688167252','2015-08-10 00:07:14.842000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',1439140035529),('teacher','Minh Chi','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('ThanhDT','ThanhDT','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('ThuyetDTM','ThuyetDTM','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('TriBD','TriBD','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('TuDT','TuDT','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('VanTTN','VanTTN','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('VinhDP','VinhDP','+841688167252','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL);
/*!40000 ALTER TABLE `tbluserinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblusernotification`
--

DROP TABLE IF EXISTS `tblusernotification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblusernotification` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `NotificationId` int(11) DEFAULT NULL,
  `Status` bit(1) DEFAULT NULL,
  `Username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK3BD538343EAC153F` (`Username`),
  KEY `FK3BD53834CCD6CDCF` (`NotificationId`),
  CONSTRAINT `FK3BD538343EAC153F` FOREIGN KEY (`Username`) REFERENCES `tbluser` (`Username`),
  CONSTRAINT `FK3BD53834CCD6CDCF` FOREIGN KEY (`NotificationId`) REFERENCES `tblnotification` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblusernotification`
--

LOCK TABLES `tblusernotification` WRITE;
/*!40000 ALTER TABLE `tblusernotification` DISABLE KEYS */;
INSERT INTO `tblusernotification` VALUES (9,9,'\0','staff');
/*!40000 ALTER TABLE `tblusernotification` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-08-17 10:44:19
