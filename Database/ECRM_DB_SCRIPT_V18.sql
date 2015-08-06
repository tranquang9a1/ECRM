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
  `RoomTypeId2` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_tblClassroom_tblRoomType2` (`RoomTypeId2`),
  KEY `FK1F2E435B2E33001` (`RoomTypeId2`),
  CONSTRAINT `FK1F2E435B2E33001` FOREIGN KEY (`RoomTypeId2`) REFERENCES `tblroomtype2` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1081 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblclassroom`
--

LOCK TABLES `tblclassroom` WRITE;
/*!40000 ALTER TABLE `tblclassroom` DISABLE KEYS */;
INSERT INTO `tblclassroom` VALUES (1049,'101','2015-07-01 12:45:34.000000',0,'2015-07-15 11:10:31.314000',1,0,3),(1050,'213','2015-07-01 12:45:50.000000',0,'2015-07-01 12:45:50.000000',1,0,3),(1051,'310','2015-07-01 12:48:22.000000',0,'2015-07-25 17:02:22.706000',1,0,3),(1052,'312','2015-07-01 12:48:43.000000',0,'2015-07-01 12:48:43.000000',1,0,3),(1053,'403','2015-07-01 12:49:00.000000',0,'2015-07-01 12:49:00.000000',1,0,3),(1054,'407','2015-07-01 12:49:19.000000',0,'2015-07-01 12:49:19.000000',1,0,3),(1055,'102','2015-07-13 13:18:50.869000',0,'2015-07-25 17:11:19.886000',1,0,3),(1056,'202','2015-07-06 15:45:43.322000',0,'2015-07-06 15:45:43.322000',1,0,3),(1057,'203','2015-07-07 06:05:33.362000',0,'2015-07-07 06:05:33.362000',1,0,3),(1058,'103','2015-07-23 22:09:21.000000',0,'2015-07-23 22:09:21.000000',1,0,3),(1059,'113','2015-07-23 22:09:49.243000',0,'2015-07-23 22:09:49.243000',1,0,3),(1060,'115','2015-07-23 22:10:10.055000',0,'2015-07-23 22:10:10.055000',1,100,3),(1061,'209','2015-07-23 22:10:20.964000',0,'2015-07-23 22:10:20.964000',1,15,3),(1062,'306','2015-07-23 22:10:53.242000',0,'2015-07-23 22:10:53.242000',1,0,3),(1063,'308','2015-07-23 22:11:05.818000',0,'2015-07-23 22:11:05.818000',1,0,3),(1064,'307','2015-07-23 22:11:19.243000',0,'2015-07-23 22:11:19.243000',1,0,3),(1065,'313','2015-07-23 22:11:46.169000',0,'2015-07-23 22:11:46.169000',1,0,3),(1066,'410','2015-07-23 22:11:57.403000',0,'2015-07-23 22:11:57.403000',1,0,3),(1067,'405','2015-07-23 22:12:17.891000',0,'2015-07-23 22:12:17.891000',1,0,3),(1068,'401','2015-07-23 22:12:34.867000',0,'2015-07-23 22:12:34.867000',1,0,3),(1069,'412','2015-07-23 22:12:47.794000',0,'2015-07-23 22:12:47.794000',1,100,3),(1070,'414','2015-07-23 22:13:00.067000',0,'2015-07-23 22:13:00.067000',1,0,3),(1071,'504','2015-07-23 22:13:13.278000',0,'2015-07-23 22:13:13.278000',1,100,3),(1072,'508','2015-07-23 22:13:25.547000',0,'2015-07-23 22:13:25.547000',1,0,3),(1073,'234234','2015-07-25 16:12:31.376000',1,'2015-07-25 16:12:31.376000',1,0,3),(1074,'1','2015-07-26 13:03:53.521000',1,'2015-07-31 05:58:10.921000',1,0,5),(1076,'512','2015-07-31 23:05:53.496000',0,'2015-08-01 01:14:03.300000',0,0,5),(1077,'513','2015-08-01 01:19:05.383000',0,'2015-08-01 01:19:05.383000',0,0,5),(1078,'556','2015-08-01 01:32:37.129000',0,'2015-08-01 01:33:10.800000',1,0,7),(1079,'600','2015-08-02 13:30:57.648000',0,'2015-08-06 11:01:25.233000',0,0,14),(1080,'601','2015-08-02 13:41:20.238000',0,'2015-08-02 13:41:20.238000',1,0,3);
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
) ENGINE=InnoDB AUTO_INCREMENT=473 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblequipment`
--

LOCK TABLES `tblequipment` WRITE;
/*!40000 ALTER TABLE `tblequipment` DISABLE KEYS */;
INSERT INTO `tblequipment` VALUES (211,1,1049,'[1]',1,3000,'Toshiba','2131231231',0,3000),(212,3,1049,'[3]',1,0,'Hitachi','4564564645',0,0),(213,3,1049,'[3]',1,0,'Hitachi','8798798798',0,0),(214,2,1049,'[2]',1,0,'Samsung','8767678678',0,0),(215,1,1050,'[1]',1,4000,'Toshiba','7857858758',0,3000),(216,3,1050,'[3]',1,0,'Hitachi','7487484784',0,0),(217,3,1050,'[3]',1,0,'Hitachi','5494949959',0,0),(218,1,1051,'[1]',1,2915,'Toshiba','2166122126',0,3000),(219,3,1051,'[3]',1,0,'Hitachi','5435434343',0,0),(220,3,1055,'[3]',1,0,'Hitachi','5616566156',0,0),(221,2,1055,'[2]',1,0,'Samsung','6876876242',0,0),(222,1,1052,'[1]',1,2825,'Toshiba','2192192919',0,3000),(223,3,1052,'[3]',1,0,'Hitachi','2818218287',0,0),(224,3,1052,'[3]',1,0,'Hitachi','1731737137',0,0),(225,1,1053,'[1]',1,2914.5,'Toshiba','9349493943',0,3000),(226,3,1053,'[3]',1,0,'Hitachi','6346437437',0,0),(227,3,1053,'[3]',1,0,'Hitachi','2182181288',0,0),(228,2,1053,'[2]',1,0,'Samsung','3163163637',0,0),(229,1,1054,'[1]',1,2914.5,'Toshiba','3783735731',0,3000),(230,3,1054,'[3]',1,0,'Hitachi','0212012334',0,0),(231,3,1054,'[3]',1,0,'Hitachi','5064506504',0,0),(232,2,1054,'[2]',1,0,'Samsung','3700738078',0,0),(261,1,1055,'[1]',1,3000,'Sony','98392385123',0,3000),(262,3,1080,'[3]',1,0,'DAIKIN','1239382454',0,0),(263,3,1080,'[3]',1,0,'DAIKIN','1231924872',0,0),(264,2,1051,'[2]',1,0,'SAMSUNG','3423423545',0,0),(280,1,1056,'[1]',1,2998.5,'SamSung','97238460123',0,3000),(281,3,1056,'[3]',1,0,'DAIKIN','37412030822',0,0),(282,3,1056,'[3]',1,0,'DAIKIN','34597233474',0,0),(283,2,1056,'[2]',1,0,'SONY','32445701283',0,0),(289,1,1057,'[1]',1,3000,'Sony','30487345123',0,3000),(290,3,1057,'[3]',1,0,'DAIKIN','97980982131',0,0),(291,3,1057,'[3]',1,0,'DAIKIN','12309845666',0,0),(292,2,1057,'[2]',1,0,'LG','97454802938',0,0),(334,1,1058,'[1]',1,3000,'PROJECTOR','23325314234',0,3000),(335,3,1058,'[3]',1,0,'AIR','32545653756',0,0),(336,3,1058,'[3]',1,0,'AIR','23434241231',0,0),(337,2,1058,'[2]',1,0,'TIVI','43524624632',0,0),(338,1,1059,'[1]',1,2997,'PROJECTOR','34546345654',0,3000),(339,3,1059,'[3]',1,0,'AIR','12312334345',0,0),(340,3,1059,'[3]',1,0,'AIR','5464879785',0,0),(341,2,1059,'[2]',1,0,'TIVI','23434645654',0,0),(342,1,1060,'[1]',1,2994,'PROJECTOR','35463Q2343',0,3000),(343,3,1060,'[3]',1,0,'AIR','45635546546',0,0),(344,3,1060,'[3]',1,0,'AIR','1231234556',0,0),(345,2,1060,'[2]',1,0,'TIVI','2341231436',0,0),(346,1,1061,'[1]',1,3000,'PROJECTOR','4575687967',0,3000),(347,3,1061,'[3]',1,0,'AIR','1231232353',0,0),(348,3,1061,'[3]',1,0,'AIR','89676574535',0,0),(349,2,1061,'[2]',1,0,'TIVI','5476583452',0,0),(350,1,1062,'[1]',1,2990,'PROJECTOR','2343546356',0,3000),(351,3,1062,'[3]',1,0,'AIR','234346765722',0,0),(352,3,1062,'[3]',1,0,'AIR','32434673474',0,0),(353,2,1062,'[2]',1,0,'TIVI','76867956754',0,0),(354,1,1063,'[1]',1,2992,'PROJECTOR','3224235457',0,3000),(355,3,1063,'[3]',1,0,'AIR','45675683234',0,0),(356,3,1063,'[3]',1,0,'AIR','3454573452',0,0),(357,2,1063,'[2]',1,0,'TIVI','2354657658',0,0),(358,1,1064,'[1]',1,2996,'PROJECTOR','5685435234',0,3000),(359,3,1064,'[3]',1,0,'AIR','457686557645',0,0),(360,3,1064,'[3]',1,0,'AIR','576584534',0,0),(361,2,1064,'[2]',1,0,'TIVI','464578685456',0,0),(362,1,1065,'[1]',1,2993,'PROJECTOR','23424534646',0,3000),(363,3,1065,'[3]',1,0,'AIR','345457443523',0,0),(364,3,1065,'[3]',1,0,'AIR','234264563345',0,0),(365,2,1065,'[2]',1,0,'TIVI','2343457567324',0,0),(366,1,1066,'[1]',1,2994,'PROJECTOR','2345346435',0,3000),(367,3,1066,'[3]',1,0,'AIR','2353465456',0,0),(368,3,1066,'[3]',1,0,'AIR','678678565',0,0),(369,2,1066,'[2]',1,0,'TIVI','78089068755',0,0),(370,1,1067,'[1]',1,2994,'PROJECTOR','657578679',0,3000),(371,3,1067,'[3]',1,0,'AIR','7890786875',0,0),(372,3,1067,'[3]',1,0,'AIR','566456878',0,0),(373,2,1067,'[2]',1,0,'TIVI','45768978976',0,0),(374,1,1068,'[1]',1,2994,'PROJECTOR','57686979856',0,3000),(375,3,1068,'[3]',1,0,'AIR','53454356744',0,0),(376,3,1068,'[3]',1,0,'AIR','34423567546',0,0),(377,2,1068,'[2]',1,0,'TIVI','23454576433',0,0),(378,1,1069,'[1]',0,2994,'PROJECTOR','7457667867',0,3000),(379,3,1069,'[3]',0,0,'AIR','23421454545',0,0),(380,3,1069,'[3]',1,0,'AIR','1242565687',0,0),(381,2,1069,'[2]',1,0,'TIVI','58564643457',0,0),(382,1,1070,'[1]',1,2992,'PROJECTOR','78796565534',0,3000),(383,3,1070,'[3]',1,0,'AIR','35346567885',0,0),(384,3,1070,'[3]',1,0,'AIR','23434657736',0,0),(385,2,1070,'[2]',1,0,'TIVI','21434575464',0,0),(386,1,1071,'[1]',1,2992,'PROJECTOR','89968575456',0,3000),(387,3,1071,'[3]',1,0,'AIR','4352346577',0,0),(388,3,1071,'[3]',1,0,'AIR','2456457567',0,0),(389,2,1071,'[2]',1,0,'TIVI','79867465645',0,0),(390,1,1072,'[1]',1,2994,'PROJECTOR','457568688676',0,3000),(391,3,1072,'[3]',1,0,'AIR','3454575674',0,0),(392,3,1072,'[3]',1,0,'AIR','2454675568',0,0),(393,2,1072,'[2]',1,0,'TIVI','45688796575',0,0),(403,4,1069,'[4]',1,NULL,NULL,NULL,0,NULL),(404,6,1069,'[6]',1,NULL,NULL,NULL,0,NULL),(405,1,1062,'[1]',1,NULL,NULL,NULL,0,3000),(406,3,1062,'[3]',1,NULL,NULL,NULL,0,NULL),(407,1,1063,'[1]',1,NULL,NULL,NULL,0,3000),(408,4,1063,'[4]',1,NULL,NULL,NULL,0,NULL),(409,6,1063,'[6]',1,NULL,NULL,NULL,0,NULL),(410,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(411,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(412,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(413,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(415,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(416,3,NULL,'[3]',1,0,NULL,NULL,0,0),(417,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(418,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(419,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(420,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(421,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(422,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(423,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(424,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(425,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(426,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(427,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(428,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(429,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(430,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(431,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(432,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(433,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(434,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(435,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(436,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(437,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(438,1,1080,NULL,1,NULL,'Projector','22222222222',0,3000),(439,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(440,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(441,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(442,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(443,1,1059,'[1]',1,NULL,NULL,NULL,0,3000),(444,3,1059,'[3]',1,NULL,NULL,NULL,0,NULL),(445,1,1049,'[1]',1,NULL,NULL,NULL,0,3000),(446,6,1049,'[6]',1,NULL,NULL,NULL,0,NULL),(447,1,1052,'[1]',1,NULL,NULL,NULL,0,3000),(448,4,1052,'[4]',1,NULL,NULL,NULL,0,NULL),(449,1,1053,'[1]',1,NULL,NULL,NULL,0,3000),(450,4,1053,'[4]',1,NULL,NULL,NULL,0,NULL),(451,1,1054,'[1]',1,NULL,NULL,NULL,0,3000),(452,4,1054,'[4]',1,NULL,NULL,NULL,0,NULL),(453,1,1050,'[1]',1,NULL,NULL,NULL,0,3000),(454,4,1050,'[4]',1,NULL,NULL,NULL,0,NULL),(455,1,1055,'[1]',1,NULL,NULL,NULL,0,3000),(456,4,1055,'[4]',1,NULL,NULL,NULL,0,NULL),(457,1,1056,'[1]',1,NULL,NULL,NULL,0,3000),(458,4,1056,'[4]',1,NULL,NULL,NULL,0,NULL),(459,1,1057,'[1]',1,NULL,NULL,NULL,0,3000),(460,4,1057,'[4]',1,NULL,NULL,NULL,0,NULL),(461,7,1051,'[1,1]',1,NULL,NULL,NULL,0,NULL),(462,1,NULL,'[1]',1,0,NULL,NULL,0,0),(463,5,1069,'[5]',0,NULL,NULL,NULL,NULL,NULL),(464,3,1060,'[3]',0,NULL,NULL,NULL,NULL,NULL),(465,1,1060,'[1]',0,NULL,NULL,NULL,NULL,NULL),(466,1,NULL,'[1]',1,3333,'schweinsteiger','1231232131332',0,3333),(467,1,1078,'[1]',1,3000,'rưerqwe','12313435234',0,3000),(468,6,1060,'[6]',0,NULL,NULL,NULL,NULL,NULL),(469,5,1060,'[5]',0,NULL,NULL,NULL,NULL,NULL),(470,5,1061,'[5]',0,NULL,NULL,NULL,NULL,NULL),(471,3,1071,'[3]',0,NULL,NULL,NULL,NULL,NULL),(472,1,1071,'[1]',0,NULL,NULL,NULL,NULL,NULL);
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
  `UsingTime` int(11) DEFAULT NULL,
  `IsManaged` tinyint(1) DEFAULT NULL,
  `ImageUrl` varchar(100) DEFAULT NULL,
  `IsDelete` tinyint(1) DEFAULT NULL,
  `UpdateTime` bigint(8) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblequipmentcategory`
--

LOCK TABLES `tblequipmentcategory` WRITE;
/*!40000 ALTER TABLE `tblequipmentcategory` DISABLE KEYS */;
INSERT INTO `tblequipmentcategory` VALUES (1,'Máy chiếu',3000,1,'projector.png',0,1438291042460),(2,'Tivi',0,1,'widescreentv.png',0,1438291042460),(3,'Máy lạnh',6000,1,'air512.png',0,1438291042460),(4,'Máy quạt',0,0,'fan512.png',0,1438291042460),(5,'Loa',0,0,'003386glossyblackiconmediamusicspeaker.png',0,1438291042460),(6,'Bóng đèn',0,0,'lightbulb7icon270x250.png',0,1438291042460),(7,'Bàn',0,0,'HouseholdTableicon.png',0,1438291042460),(8,'Ghế',0,0,'chairoffice512.png',0,1438291042460),(12,'Empty',0,1,'lollipop.png',0,1438291042460);
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
  CONSTRAINT `FKDE89741B975A2EAF` FOREIGN KEY (`RoomTypeId`) REFERENCES `tblroomtype2` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblequipmentquantity`
--

LOCK TABLES `tblequipmentquantity` WRITE;
/*!40000 ALTER TABLE `tblequipmentquantity` DISABLE KEYS */;
INSERT INTO `tblequipmentquantity` VALUES (1,3,1,1,3,0),(2,3,3,2,3,0),(3,3,5,1,1,0),(4,3,6,1,3,0),(5,7,1,1,1,0),(6,8,1,1,3,1),(7,8,3,2,3,1),(8,8,5,1,1,1),(9,8,6,1,3,1),(10,8,1,1,3,0),(11,8,3,2,3,0),(12,9,1,1,3,1),(13,9,3,2,3,1),(14,9,5,1,1,1),(15,9,6,1,3,1),(16,5,1,1,3,0),(17,9,1,1,3,1),(18,10,1,1,3,0),(19,10,3,2,3,0),(20,10,5,1,1,0),(21,10,6,1,3,0),(22,9,1,1,1,1),(23,9,1,2,1,0);
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
  `Body` varchar(255) DEFAULT NULL,
  `IsRead` bit(1) DEFAULT NULL,
  `Phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblnotification`
--

LOCK TABLES `tblnotification` WRITE;
/*!40000 ALTER TABLE `tblnotification` DISABLE KEYS */;
INSERT INTO `tblnotification` VALUES (2,1059,'HoangNT báo cáo hư hại phòng 113','2015-07-27 08:51:18','/bao-cao/hu-hai?phong=1059',1,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblreport`
--

LOCK TABLES `tblreport` WRITE;
/*!40000 ALTER TABLE `tblreport` DISABLE KEYS */;
INSERT INTO `tblreport` VALUES (11,'KhanhKT',1069,'2015-07-31 09:44:19.410000','Vẫn dạy được',2,'401'),(12,'KhanhKT',1060,'2015-07-31 12:57:33.823000','Vẫn dạy được',2,'101'),(13,'KhanhKT',1060,'2015-08-03 15:14:57.232000','Vẫn dạy được',1,NULL),(14,'SuTV',1061,'2015-08-03 15:16:49.690000','Vẫn dạy được',1,NULL),(15,'SuTV',1071,'2015-08-03 15:20:36.533000','Vẫn dạy được',2,'403');
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
INSERT INTO `tblreportdetail` VALUES (378,11,'1','[1]','Không sử dụng được',NULL,NULL,0),(379,11,'1','[3]','Không sử dụng được',NULL,NULL,0),(463,11,'1','[5]','Không sử dụng được',NULL,NULL,0),(464,12,'1','[3]','Không sử dụng được',NULL,NULL,0),(465,12,'1','[1]','Không sử dụng được',NULL,NULL,0),(468,13,'1','[6]','Không sử dụng được',NULL,NULL,0),(469,13,'1','[5]','Không sử dụng được',NULL,NULL,0),(470,14,'1','[5]','Không sử dụng được',NULL,NULL,0),(471,15,'1','[3]','Không sử dụng được',NULL,NULL,0),(472,15,'1','[1]','Không sử dụng được',NULL,NULL,0);
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
-- Table structure for table `tblroomtype2`
--

DROP TABLE IF EXISTS `tblroomtype2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblroomtype2` (
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblroomtype2`
--

LOCK TABLES `tblroomtype2` WRITE;
/*!40000 ALTER TABLE `tblroomtype2` DISABLE KEYS */;
INSERT INTO `tblroomtype2` VALUES (3,'Loại 25 chỗ',25,2,'5-5','3-2','2015-07-29 07:31:03',0,'2015-07-29 07:31:03'),(4,'Loại 30 chỗ',30,2,'5-5','3-3','2015-07-29 07:35:48',0,'2015-07-29 07:35:48'),(5,'test',2,2,'1-1','1-1','2015-07-30 06:56:27',0,'2015-07-31 06:01:16'),(6,'test2',50,2,'5-5','5-5','2015-07-30 09:12:00',0,'2015-07-30 09:12:00'),(7,'test3',6,2,'5-1','1-1','2015-07-30 09:15:58',0,'2015-07-30 14:02:31'),(8,'test 4',25,2,'5-5','3-2','2015-07-30 17:06:21',0,'2015-07-30 17:38:16'),(9,'Test5',6,2,'5-1','1-1','2015-07-31 02:12:33',0,'2015-07-31 23:05:06'),(10,'Chi',25,2,'5-5','3-2','2015-07-31 10:27:16',0,'2015-07-31 10:27:16'),(11,'test6',2,2,'1-1','1-1','2015-07-31 23:05:24',0,'2015-07-31 23:05:24'),(12,'test67',11,2,'1-1','10-1','2015-08-01 01:35:31',0,'2015-08-02 13:57:18'),(13,'tes67',30,4,'9-1-1-1','3-1-1-1','2015-08-02 13:45:20',0,'2015-08-02 13:45:20'),(14,'aaaa',2,2,'1-1','1-1','2015-08-05 10:08:56',0,'2015-08-05 10:08:56');
/*!40000 ALTER TABLE `tblroomtype2` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=6232 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblschedule`
--

LOCK TABLES `tblschedule` WRITE;
/*!40000 ALTER TABLE `tblschedule` DISABLE KEYS */;
INSERT INTO `tblschedule` VALUES (5151,'PhuongLHK',1049,30,'Đổi sang phòng 103','2015-07-28',0,1),(5152,'PhuongLHK',1049,30,NULL,'2015-07-30',1,1),(5153,'PhuongLHK',1049,30,'Đổi sang phòng 103','2015-07-28',0,2),(5154,'PhuongLHK',1049,30,NULL,'2015-07-30',1,2),(5155,'HungNH',1049,30,'Đổi sang phòng 103','2015-07-28',0,4),(5156,'HungNH',1049,30,NULL,'2015-07-30',1,4),(5157,'HungNH',1049,30,'Đổi sang phòng 115','2015-07-28',0,5),(5158,'HungNH',1049,30,NULL,'2015-07-30',1,5),(5159,'HungLD',1058,30,NULL,'2015-07-28',1,5),(5160,'HungLD',1058,30,NULL,'2015-07-30',1,5),(5161,'HoangNT',1059,30,NULL,'2015-07-27',1,2),(5162,'HoangNT',1059,30,NULL,'2015-07-28',1,2),(5163,'HoangNT',1059,30,NULL,'2015-07-29',1,2),(5164,'HoangNT',1059,30,NULL,'2015-07-30',1,2),(5165,'HoangNT',1059,30,NULL,'2015-07-31',1,2),(5166,'TaiNT',1059,30,NULL,'2015-07-28',1,5),(5167,'TaiNT',1059,30,NULL,'2015-07-29',1,5),(5168,'TaiNT',1059,30,NULL,'2015-07-30',1,5),(5169,'TaiNT',1059,30,NULL,'2015-07-31',1,5),(5170,'HungLD',1060,30,NULL,'2015-07-28',1,1),(5171,'HungLD',1060,30,NULL,'2015-07-30',1,1),(5172,'HungLD',1060,30,NULL,'2015-07-31',1,1),(5173,'HiepNT',1060,30,NULL,'2015-07-27',1,2),(5174,'HiepNT',1060,30,NULL,'2015-07-28',1,2),(5175,'HiepNT',1060,30,NULL,'2015-07-29',1,2),(5176,'HiepNT',1060,30,NULL,'2015-07-30',1,2),(5177,'HiepNT',1060,30,NULL,'2015-07-31',1,2),(5178,'ChungKTT',1060,30,NULL,'2015-07-27',1,3),(5179,'ChungKTT',1060,30,NULL,'2015-07-28',1,3),(5180,'ChungKTT',1060,30,NULL,'2015-07-29',1,3),(5181,'ChungKTT',1060,30,NULL,'2015-07-30',1,3),(5182,'ChungKTT',1060,30,NULL,'2015-07-31',1,3),(5183,'KhanhKT',1060,30,NULL,'2015-07-27',1,4),(5184,'KhanhKT',1060,30,NULL,'2015-07-31',1,4),(5185,'KhanhKT',1060,30,NULL,'2015-07-27',1,5),(5186,'KhanhKT',1060,30,NULL,'2015-07-31',1,5),(5187,'LienTTH',1061,30,NULL,'2015-07-27',1,2),(5188,'HuongPTT',1061,30,NULL,'2015-07-28',1,2),(5189,'LienTTH',1061,30,NULL,'2015-07-29',1,2),(5190,'HuongPTT',1061,30,NULL,'2015-07-30',1,2),(5191,'LienTTH',1061,30,NULL,'2015-07-31',1,2),(5192,'LienTTH',1061,30,NULL,'2015-07-27',1,3),(5193,'HuongPTT',1061,30,NULL,'2015-07-28',1,3),(5194,'LienTTH',1061,30,NULL,'2015-07-29',1,3),(5195,'HuongPTT',1061,30,NULL,'2015-07-30',1,3),(5196,'LienTTH',1061,30,NULL,'2015-07-31',1,3),(5197,'AnNDH',1061,30,NULL,'2015-07-27',1,4),(5198,'AnNDH',1061,30,NULL,'2015-07-29',1,4),(5199,'SuTV',1061,30,NULL,'2015-07-27',1,5),(5200,'SuTV',1061,30,NULL,'2015-07-28',1,5),(5201,'SuTV',1061,30,NULL,'2015-07-29',1,5),(5202,'SuTV',1061,30,NULL,'2015-07-30',1,5),(5203,'SuTV',1061,30,NULL,'2015-07-31',1,5),(5204,'KieuTA',1061,30,NULL,'2015-07-27',1,6),(5205,'KieuTA',1061,30,NULL,'2015-07-28',1,6),(5206,'KieuTA',1061,30,NULL,'2015-07-29',1,6),(5207,'KieuTA',1061,30,NULL,'2015-07-30',1,6),(5208,'KieuTA',1061,30,NULL,'2015-07-31',1,6),(5209,'HiepTT',1050,20,NULL,'2015-07-27',1,1),(5210,'HiepTT',1050,20,NULL,'2015-07-28',1,1),(5211,'HiepTT',1050,20,NULL,'2015-07-29',1,1),(5212,'HiepTT',1050,20,NULL,'2015-07-30',1,1),(5213,'HiepTT',1050,20,NULL,'2015-07-31',1,1),(5214,'HungLD',1050,20,NULL,'2015-07-27',1,2),(5215,'HungLD',1050,20,NULL,'2015-07-28',1,2),(5216,'HungLD',1050,20,NULL,'2015-07-29',1,2),(5217,'HungLD',1050,20,NULL,'2015-07-30',1,2),(5218,'HungLD',1050,20,NULL,'2015-07-31',1,2),(5219,'KieuTA',1050,20,NULL,'2015-07-28',1,3),(5220,'KieuTA',1050,20,NULL,'2015-07-30',1,3),(5221,'KieuTA',1050,20,NULL,'2015-07-31',1,3),(5222,'AnNDH',1062,30,NULL,'2015-07-27',1,1),(5223,'AnNDH',1062,30,NULL,'2015-07-28',1,1),(5224,'AnNDH',1062,30,NULL,'2015-07-29',1,1),(5225,'AnNDH',1062,30,NULL,'2015-07-30',1,1),(5226,'AnNDH',1062,30,NULL,'2015-07-31',1,1),(5227,'KieuTA',1062,30,NULL,'2015-07-27',1,2),(5228,'KieuTA',1062,30,NULL,'2015-07-29',1,2),(5229,'TuDT',1062,30,NULL,'2015-07-27',1,3),(5230,'TuDT',1062,30,NULL,'2015-07-28',1,3),(5231,'TuDT',1062,30,NULL,'2015-07-29',1,3),(5232,'TuDT',1062,30,NULL,'2015-07-30',1,3),(5233,'TuDT',1062,30,NULL,'2015-07-31',1,3),(5234,'HiepNT',1062,30,NULL,'2015-07-28',1,4),(5235,'HiepNT',1062,30,NULL,'2015-07-30',1,4),(5236,'HiepNT',1062,30,NULL,'2015-07-31',1,4),(5237,'BinhDTH',1062,30,NULL,'2015-07-27',1,5),(5238,'BinhDTH',1062,30,NULL,'2015-07-28',1,5),(5239,'BinhDTH',1062,30,NULL,'2015-07-29',1,5),(5240,'BinhDTH',1062,30,NULL,'2015-07-30',1,5),(5241,'BinhDTH',1062,30,NULL,'2015-07-31',1,5),(5242,'KhanhKT',1062,30,NULL,'2015-07-27',1,6),(5243,'KhanhKT',1062,30,NULL,'2015-07-28',1,6),(5244,'KhanhKT',1062,30,NULL,'2015-07-29',1,6),(5245,'KhanhKT',1062,30,NULL,'2015-07-30',1,6),(5246,'KhanhKT',1062,30,NULL,'2015-07-31',1,6),(5247,'TaiNT',1063,25,NULL,'2015-07-27',1,1),(5248,'TaiNT',1063,25,NULL,'2015-07-28',1,1),(5249,'TaiNT',1063,25,NULL,'2015-07-29',1,1),(5250,'TaiNT',1063,25,NULL,'2015-07-30',1,1),(5251,'TaiNT',1063,25,NULL,'2015-07-31',1,1),(5252,'KieuTA',1063,25,NULL,'2015-07-28',1,2),(5253,'KieuTA',1063,25,NULL,'2015-07-30',1,2),(5254,'KieuTA',1063,25,NULL,'2015-07-31',1,2),(5255,'AnNDH',1063,25,NULL,'2015-07-27',1,3),(5256,'AnNDH',1063,25,NULL,'2015-07-28',1,3),(5257,'AnNDH',1063,25,NULL,'2015-07-29',1,3),(5258,'AnNDH',1063,25,NULL,'2015-07-30',1,3),(5259,'AnNDH',1063,25,NULL,'2015-07-31',1,3),(5260,'CamDTH',1063,25,NULL,'2015-07-28',1,4),(5261,'CamDTH',1063,25,NULL,'2015-07-30',1,4),(5262,'CamDTH',1063,25,NULL,'2015-07-31',1,4),(5263,'TuDT',1063,25,NULL,'2015-07-27',1,5),(5264,'TuDT',1063,25,NULL,'2015-07-28',1,5),(5265,'TuDT',1063,25,NULL,'2015-07-29',1,5),(5266,'TuDT',1063,25,NULL,'2015-07-30',1,5),(5267,'TuDT',1063,25,NULL,'2015-07-31',1,5),(5268,'AnNDH',1063,25,NULL,'2015-07-27',1,6),(5269,'AnNDH',1063,25,NULL,'2015-07-28',1,6),(5270,'AnNDH',1063,25,NULL,'2015-07-29',1,6),(5271,'AnNDH',1063,25,NULL,'2015-07-30',1,6),(5272,'AnNDH',1063,25,NULL,'2015-07-31',1,6),(5273,'PhuongLHK',1064,25,NULL,'2015-07-27',1,4),(5274,'PhuongLHK',1064,25,NULL,'2015-07-28',1,4),(5275,'PhuongLHK',1064,25,NULL,'2015-07-29',1,4),(5276,'PhuongLHK',1064,25,NULL,'2015-07-30',1,4),(5277,'PhuongLHK',1064,25,NULL,'2015-07-31',1,4),(5278,'KieuTA',1051,25,NULL,'2015-07-27',1,1),(5279,'KieuTA',1051,25,NULL,'2015-07-29',1,1),(5280,'HungNH',1051,25,NULL,'2015-07-27',1,2),(5281,'HungNH',1051,25,NULL,'2015-07-28',1,2),(5282,'HungNH',1051,25,NULL,'2015-07-29',1,2),(5283,'HungNH',1051,25,NULL,'2015-07-30',1,2),(5284,'HungNH',1051,25,NULL,'2015-07-31',1,2),(5285,'ThuyetDTM',1051,25,NULL,'2015-07-27',1,3),(5286,'ThuyetDTM',1051,25,NULL,'2015-07-28',1,3),(5287,'ThuyetDTM',1051,25,NULL,'2015-07-29',1,3),(5288,'ThuyetDTM',1051,25,NULL,'2015-07-30',1,3),(5289,'ThuyetDTM',1051,25,NULL,'2015-07-31',1,3),(5290,'Cecile',1051,25,NULL,'2015-07-27',1,4),(5291,'Cecile',1051,25,NULL,'2015-07-28',1,4),(5292,'Cecile',1051,25,NULL,'2015-07-29',1,4),(5293,'Cecile',1051,25,NULL,'2015-07-30',1,4),(5294,'Cecile',1051,25,NULL,'2015-07-31',1,4),(5295,'CamDTH',1051,25,NULL,'2015-07-27',1,5),(5296,'CamDTH',1051,25,NULL,'2015-07-29',1,5),(5297,'HungNH',1051,25,NULL,'2015-07-27',1,6),(5298,'HungNH',1051,25,NULL,'2015-07-28',1,6),(5299,'HungNH',1051,25,NULL,'2015-07-29',1,6),(5300,'HungNH',1051,25,NULL,'2015-07-30',1,6),(5301,'HungNH',1051,25,NULL,'2015-07-31',1,6),(5302,'HungLD',1052,20,NULL,'2015-07-27',1,4),(5303,'HungLD',1052,20,NULL,'2015-07-28',1,4),(5304,'HungLD',1052,20,NULL,'2015-07-29',1,4),(5305,'HungLD',1052,20,NULL,'2015-07-30',1,4),(5306,'HungLD',1052,20,NULL,'2015-07-31',1,4),(5307,'CamDTH',1052,20,NULL,'2015-07-28',1,5),(5308,'CamDTH',1052,20,NULL,'2015-07-30',1,5),(5309,'CamDTH',1052,20,NULL,'2015-07-31',1,5),(5310,'TaiNT',1052,20,NULL,'2015-07-27',1,6),(5311,'TaiNT',1052,20,NULL,'2015-07-28',1,6),(5312,'TaiNT',1052,20,NULL,'2015-07-29',1,6),(5313,'TaiNT',1052,20,NULL,'2015-07-30',1,6),(5314,'TaiNT',1052,20,NULL,'2015-07-31',1,6),(5315,'ThanhDT',1065,25,NULL,'2015-07-27',1,4),(5316,'ThanhDT',1065,25,NULL,'2015-07-28',1,4),(5317,'ThanhDT',1065,25,NULL,'2015-07-29',1,4),(5318,'ThanhDT',1065,25,NULL,'2015-07-30',1,4),(5319,'ThanhDT',1065,25,NULL,'2015-07-31',1,4),(5320,'AnhNT',1065,25,NULL,'2015-07-27',1,5),(5321,'AnhNT',1065,25,NULL,'2015-07-28',1,5),(5322,'AnhNT',1065,25,NULL,'2015-07-29',1,5),(5323,'AnhNT',1065,25,NULL,'2015-07-30',1,5),(5324,'AnhNT',1065,25,NULL,'2015-07-31',1,5),(5325,'PhuongLHK',1065,25,NULL,'2015-07-28',1,6),(5326,'PhuongLHK',1065,25,NULL,'2015-07-30',1,6),(5327,'PhuongLHK',1065,25,NULL,'2015-07-31',1,6),(5328,'TriBD',1068,25,NULL,'2015-07-28',1,1),(5329,'TriBD',1068,25,NULL,'2015-07-30',1,1),(5330,'TriBD',1068,25,NULL,'2015-07-28',1,2),(5331,'TriBD',1068,25,NULL,'2015-07-30',1,2),(5332,'LoiND',1068,25,NULL,'2015-07-27',1,4),(5333,'LoiND',1068,25,NULL,'2015-07-28',1,4),(5334,'LoiND',1068,25,NULL,'2015-07-29',1,4),(5335,'LoiND',1068,25,NULL,'2015-07-30',1,4),(5336,'LoiND',1068,25,NULL,'2015-07-31',1,4),(5337,'ChungKTT',1068,25,NULL,'2015-07-27',1,5),(5338,'ChungKTT',1068,25,NULL,'2015-07-28',1,5),(5339,'ChungKTT',1068,25,NULL,'2015-07-29',1,5),(5340,'ChungKTT',1068,25,NULL,'2015-07-30',1,5),(5341,'ChungKTT',1068,25,NULL,'2015-07-31',1,5),(5342,'PhuongLHK',1068,25,NULL,'2015-07-27',1,6),(5343,'PhuongLHK',1068,25,NULL,'2015-07-29',1,6),(5344,'ChauLH',1067,25,NULL,'2015-07-27',1,1),(5345,'ChauLH',1067,25,NULL,'2015-07-28',1,1),(5346,'ChauLH',1067,25,NULL,'2015-07-29',1,1),(5347,'ChauLH',1067,25,NULL,'2015-07-30',1,1),(5348,'ChauLH',1067,25,NULL,'2015-07-31',1,1),(5349,'PhuongLHK',1067,25,NULL,'2015-07-27',1,2),(5350,'PhuongLHK',1067,25,NULL,'2015-07-29',1,2),(5351,'HiepNT',1067,25,NULL,'2015-07-27',1,3),(5352,'HiepNT',1067,25,NULL,'2015-07-28',1,3),(5353,'HiepNT',1067,25,NULL,'2015-07-29',1,3),(5354,'HiepNT',1067,25,NULL,'2015-07-30',1,3),(5355,'HiepNT',1067,25,NULL,'2015-07-31',1,3),(5356,'VinhDP',1054,25,NULL,'2015-07-27',1,1),(5357,'VinhDP',1054,25,NULL,'2015-07-28',1,1),(5358,'VinhDP',1054,25,NULL,'2015-07-29',1,1),(5359,'VinhDP',1054,25,NULL,'2015-07-30',1,1),(5360,'VinhDP',1054,25,NULL,'2015-07-31',1,1),(5361,'AnNDH',1054,25,NULL,'2015-07-27',1,2),(5362,'AnNDH',1054,25,NULL,'2015-07-28',1,2),(5363,'AnNDH',1054,25,NULL,'2015-07-29',1,2),(5364,'AnNDH',1054,25,NULL,'2015-07-30',1,2),(5365,'AnNDH',1054,25,NULL,'2015-07-31',1,2),(5366,'HoangNT',1054,25,NULL,'2015-07-27',1,3),(5367,'HoangNT',1054,25,NULL,'2015-07-29',1,3),(5368,'HiepNT',1054,25,NULL,'2015-07-27',1,4),(5369,'HiepNT',1054,25,NULL,'2015-07-29',1,4),(5370,'HiepTT',1054,25,NULL,'2015-07-27',1,5),(5371,'HiepTT',1054,25,NULL,'2015-07-28',1,5),(5372,'HiepTT',1054,25,NULL,'2015-07-29',1,5),(5373,'HiepTT',1054,25,NULL,'2015-07-30',1,5),(5374,'HiepTT',1054,25,NULL,'2015-07-31',1,5),(5375,'BinhDTH',1054,25,NULL,'2015-07-27',1,6),(5376,'BinhDTH',1054,25,NULL,'2015-07-28',1,6),(5377,'BinhDTH',1054,25,NULL,'2015-07-29',1,6),(5378,'BinhDTH',1054,25,NULL,'2015-07-30',1,6),(5379,'BinhDTH',1054,25,NULL,'2015-07-31',1,6),(5380,'NhuLTL',1066,25,NULL,'2015-07-27',1,3),(5381,'NhuLTL',1066,25,NULL,'2015-07-28',1,3),(5382,'NhuLTL',1066,25,NULL,'2015-07-29',1,3),(5383,'NhuLTL',1066,25,NULL,'2015-07-30',1,3),(5384,'NhuLTL',1066,25,NULL,'2015-07-31',1,3),(5385,'HoangNT',1066,25,NULL,'2015-07-27',1,4),(5386,'HoangNT',1066,25,NULL,'2015-07-28',1,4),(5387,'HoangNT',1066,25,NULL,'2015-07-29',1,4),(5388,'HoangNT',1066,25,NULL,'2015-07-30',1,4),(5389,'HoangNT',1066,25,NULL,'2015-07-31',1,4),(5390,'TuDT',1066,25,NULL,'2015-07-27',1,6),(5391,'TuDT',1066,25,NULL,'2015-07-28',1,6),(5392,'TuDT',1066,25,NULL,'2015-07-29',1,6),(5393,'TuDT',1066,25,NULL,'2015-07-30',1,6),(5394,'TuDT',1066,25,NULL,'2015-07-31',1,6),(5395,'HungLD',1069,25,NULL,'2015-07-27',1,1),(5396,'HungLD',1069,25,NULL,'2015-07-29',1,1),(5397,'KhanhKT',1069,25,NULL,'2015-07-27',1,2),(5398,'KhanhKT',1069,25,NULL,'2015-07-28',1,2),(5399,'KhanhKT',1069,25,NULL,'2015-07-29',1,2),(5400,'KhanhKT',1069,25,NULL,'2015-07-30',1,2),(5401,'KhanhKT',1069,25,NULL,'2015-07-31',1,2),(5402,'TaiNT',1069,25,NULL,'2015-07-27',1,3),(5403,'TaiNT',1069,25,NULL,'2015-07-28',1,3),(5404,'TaiNT',1069,25,NULL,'2015-07-29',1,3),(5405,'TaiNT',1069,25,NULL,'2015-07-30',1,3),(5406,'TaiNT',1069,25,'Đổi sang phòng 401','2015-07-31',0,3),(5407,'VanTTN',1070,25,NULL,'2015-07-27',1,2),(5408,'VanTTN',1070,25,NULL,'2015-07-28',1,2),(5409,'VanTTN',1070,25,NULL,'2015-07-29',1,2),(5410,'VanTTN',1070,25,NULL,'2015-07-30',1,2),(5411,'VanTTN',1070,25,NULL,'2015-07-31',1,2),(5412,'VinhDP',1070,25,NULL,'2015-07-27',1,3),(5413,'VinhDP',1070,25,NULL,'2015-07-28',1,3),(5414,'VinhDP',1070,25,NULL,'2015-07-29',1,3),(5415,'VinhDP',1070,25,NULL,'2015-07-30',1,3),(5416,'VinhDP',1070,25,NULL,'2015-07-31',1,3),(5417,'VanTTN',1070,25,NULL,'2015-07-27',1,4),(5418,'VanTTN',1070,25,NULL,'2015-07-28',1,4),(5419,'VanTTN',1070,25,NULL,'2015-07-29',1,4),(5420,'VanTTN',1070,25,NULL,'2015-07-30',1,4),(5421,'VanTTN',1070,25,NULL,'2015-07-31',1,4),(5422,'VinhDP',1070,25,NULL,'2015-07-27',1,6),(5423,'VinhDP',1070,25,NULL,'2015-07-28',1,6),(5424,'VinhDP',1070,25,NULL,'2015-07-29',1,6),(5425,'VinhDP',1070,25,NULL,'2015-07-30',1,6),(5426,'VinhDP',1070,25,NULL,'2015-07-31',1,6),(5427,'SuTV',1071,25,NULL,'2015-07-27',1,1),(5428,'SuTV',1071,25,NULL,'2015-07-28',1,1),(5429,'SuTV',1071,25,NULL,'2015-07-29',1,1),(5430,'SuTV',1071,25,NULL,'2015-07-30',1,1),(5431,'SuTV',1071,25,NULL,'2015-07-31',1,1),(5432,'TaiNT',1071,25,NULL,'2015-07-27',1,2),(5433,'TaiNT',1071,25,NULL,'2015-07-28',1,2),(5434,'TaiNT',1071,25,NULL,'2015-07-29',1,2),(5435,'TaiNT',1071,25,NULL,'2015-07-30',1,2),(5436,'TaiNT',1071,25,NULL,'2015-07-31',1,2),(5437,'TaiNT',1071,25,NULL,'2015-07-27',1,4),(5438,'TaiNT',1071,25,NULL,'2015-07-28',1,4),(5439,'TaiNT',1071,25,NULL,'2015-07-29',1,4),(5440,'TaiNT',1071,25,NULL,'2015-07-30',1,4),(5441,'TaiNT',1071,25,NULL,'2015-07-31',1,4),(5442,'HoangNT',1071,25,NULL,'2015-07-27',1,5),(5443,'HoangNT',1071,25,NULL,'2015-07-28',1,5),(5444,'HoangNT',1071,25,NULL,'2015-07-29',1,5),(5445,'HoangNT',1071,25,NULL,'2015-07-30',1,5),(5446,'HoangNT',1071,25,NULL,'2015-07-31',1,5),(5447,'HungNH',1072,25,NULL,'2015-07-27',1,4),(5448,'HungNH',1072,25,NULL,'2015-07-29',1,4),(5449,'HungNH',1072,25,NULL,'2015-07-31',1,4),(5450,'VinhDP',1072,25,NULL,'2015-07-27',1,5),(5451,'VinhDP',1072,25,NULL,'2015-07-28',1,5),(5452,'VinhDP',1072,25,NULL,'2015-07-29',1,5),(5453,'VinhDP',1072,25,NULL,'2015-07-30',1,5),(5454,'VinhDP',1072,25,NULL,'2015-07-31',1,5),(5455,'SuTV',1072,25,NULL,'2015-07-27',1,6),(5456,'SuTV',1072,25,NULL,'2015-07-28',1,6),(5457,'SuTV',1072,25,NULL,'2015-07-29',1,6),(5458,'SuTV',1072,25,NULL,'2015-07-30',1,6),(5459,'SuTV',1072,25,NULL,'2015-07-31',1,6),(5460,'HungNH',1060,30,'Thay đổi phòng từ phòng 101 sang phòng 115','2015-07-28',1,5),(5461,'HungNH',1058,30,'Thay đổi phòng từ phòng 101 sang phòng 103','2015-07-28',1,4),(5462,'PhuongLHK',1058,30,'Thay đổi phòng từ phòng 101 sang phòng 103','2015-07-28',1,1),(5463,'PhuongLHK',1058,30,'Thay đổi phòng từ phòng 101 sang phòng 103','2015-07-28',1,2),(5464,'TaiNT',1068,25,'Thay đổi phòng từ phòng 412 sang phòng 401','2015-07-31',1,3),(5465,'PhuongLHK',1049,25,'Đã thay đổi','2015-08-04',0,1),(5466,'PhuongLHK',1049,25,NULL,'2015-08-06',1,1),(5467,'PhuongLHK',1049,25,NULL,'2015-08-04',1,2),(5468,'PhuongLHK',1049,25,NULL,'2015-08-06',1,2),(5469,'HungNH',1049,25,NULL,'2015-08-04',1,4),(5470,'HungNH',1049,25,NULL,'2015-08-06',1,4),(5471,'HungNH',1049,25,NULL,'2015-08-04',1,5),(5472,'HungNH',1049,25,NULL,'2015-08-06',1,5),(5473,'HungLD',1058,25,NULL,'2015-08-04',1,5),(5474,'HungLD',1058,25,NULL,'2015-08-06',1,5),(5475,'HoangNT',1059,25,NULL,'2015-08-03',1,2),(5476,'HoangNT',1059,25,NULL,'2015-08-04',1,2),(5477,'HoangNT',1059,25,NULL,'2015-08-05',1,2),(5478,'HoangNT',1059,25,NULL,'2015-08-06',1,2),(5479,'HoangNT',1059,25,NULL,'2015-08-07',1,2),(5480,'TaiNT',1059,25,NULL,'2015-08-04',1,5),(5481,'TaiNT',1059,25,NULL,'2015-08-05',1,5),(5482,'TaiNT',1059,25,NULL,'2015-08-06',1,5),(5483,'TaiNT',1059,25,NULL,'2015-08-07',1,5),(5484,'HungLD',1060,25,'Đổi sang phòng 102','2015-08-04',0,1),(5485,'HungLD',1060,25,NULL,'2015-08-06',1,1),(5486,'HungLD',1060,25,NULL,'2015-08-07',1,1),(5487,'HiepNT',1060,25,NULL,'2015-08-03',1,2),(5488,'HiepNT',1060,25,'Đổi sang phòng 102','2015-08-04',0,2),(5489,'HiepNT',1060,25,NULL,'2015-08-05',1,2),(5490,'HiepNT',1060,25,NULL,'2015-08-06',1,2),(5491,'HiepNT',1060,25,NULL,'2015-08-07',1,2),(5492,'ChungKTT',1060,25,NULL,'2015-08-03',1,3),(5493,'ChungKTT',1060,25,'Đổi sang phòng 102','2015-08-04',0,3),(5494,'ChungKTT',1060,25,NULL,'2015-08-05',1,3),(5495,'ChungKTT',1060,25,NULL,'2015-08-06',1,3),(5496,'ChungKTT',1060,25,NULL,'2015-08-07',1,3),(5497,'KhanhKT',1060,25,NULL,'2015-08-03',1,4),(5498,'KhanhKT',1060,25,NULL,'2015-08-07',1,4),(5499,'KhanhKT',1060,25,'Đổi sang phòng 101','2015-08-03',0,5),(5500,'KhanhKT',1060,25,NULL,'2015-08-07',1,5),(5501,'LienTTH',1061,25,NULL,'2015-08-03',1,2),(5502,'HuongPTT',1061,25,NULL,'2015-08-04',1,2),(5503,'LienTTH',1061,25,NULL,'2015-08-05',1,2),(5504,'HuongPTT',1061,25,NULL,'2015-08-06',1,2),(5505,'LienTTH',1061,25,NULL,'2015-08-07',1,2),(5506,'LienTTH',1061,25,NULL,'2015-08-03',1,3),(5507,'HuongPTT',1061,25,NULL,'2015-08-04',1,3),(5508,'LienTTH',1061,25,NULL,'2015-08-05',1,3),(5509,'HuongPTT',1061,25,NULL,'2015-08-06',1,3),(5510,'LienTTH',1061,25,NULL,'2015-08-07',1,3),(5511,'AnNDH',1061,25,NULL,'2015-08-03',1,4),(5512,'AnNDH',1061,25,NULL,'2015-08-05',1,4),(5513,'SuTV',1061,25,NULL,'2015-08-03',1,5),(5514,'SuTV',1061,25,NULL,'2015-08-04',1,5),(5515,'SuTV',1061,25,NULL,'2015-08-05',1,5),(5516,'SuTV',1061,25,NULL,'2015-08-06',1,5),(5517,'SuTV',1061,25,NULL,'2015-08-07',1,5),(5518,'KieuTA',1061,25,NULL,'2015-08-03',1,6),(5519,'KieuTA',1061,25,NULL,'2015-08-04',1,6),(5520,'KieuTA',1061,25,NULL,'2015-08-05',1,6),(5521,'KieuTA',1061,25,NULL,'2015-08-06',1,6),(5522,'KieuTA',1061,25,NULL,'2015-08-07',1,6),(5523,'HiepTT',1050,25,NULL,'2015-08-03',1,1),(5524,'HiepTT',1050,25,NULL,'2015-08-04',1,1),(5525,'HiepTT',1050,25,NULL,'2015-08-05',1,1),(5526,'HiepTT',1050,25,NULL,'2015-08-06',1,1),(5527,'HiepTT',1050,25,NULL,'2015-08-07',1,1),(5528,'HungLD',1050,25,NULL,'2015-08-03',1,2),(5529,'HungLD',1050,25,NULL,'2015-08-04',1,2),(5530,'HungLD',1050,25,NULL,'2015-08-05',1,2),(5531,'HungLD',1050,25,NULL,'2015-08-06',1,2),(5532,'HungLD',1050,25,NULL,'2015-08-07',1,2),(5533,'KieuTA',1050,25,NULL,'2015-08-04',1,3),(5534,'KieuTA',1050,25,NULL,'2015-08-06',1,3),(5535,'KieuTA',1050,25,NULL,'2015-08-07',1,3),(5536,'AnNDH',1062,25,NULL,'2015-08-03',1,1),(5537,'AnNDH',1062,25,NULL,'2015-08-04',1,1),(5538,'AnNDH',1062,25,NULL,'2015-08-05',1,1),(5539,'AnNDH',1062,25,NULL,'2015-08-06',1,1),(5540,'AnNDH',1062,25,NULL,'2015-08-07',1,1),(5541,'KieuTA',1062,25,NULL,'2015-08-03',1,2),(5542,'KieuTA',1062,25,NULL,'2015-08-05',1,2),(5543,'TuDT',1062,25,NULL,'2015-08-03',1,3),(5544,'TuDT',1062,25,NULL,'2015-08-04',1,3),(5545,'TuDT',1062,25,NULL,'2015-08-05',1,3),(5546,'TuDT',1062,25,NULL,'2015-08-06',1,3),(5547,'TuDT',1062,25,NULL,'2015-08-07',1,3),(5548,'HiepNT',1062,25,NULL,'2015-08-04',1,4),(5549,'HiepNT',1062,25,NULL,'2015-08-06',1,4),(5550,'HiepNT',1062,25,NULL,'2015-08-07',1,4),(5551,'BinhDTH',1062,25,NULL,'2015-08-03',1,5),(5552,'BinhDTH',1062,25,NULL,'2015-08-04',1,5),(5553,'BinhDTH',1062,25,NULL,'2015-08-05',1,5),(5554,'BinhDTH',1062,25,NULL,'2015-08-06',1,5),(5555,'BinhDTH',1062,25,NULL,'2015-08-07',1,5),(5556,'KhanhKT',1062,25,NULL,'2015-08-03',1,6),(5557,'KhanhKT',1062,25,NULL,'2015-08-04',1,6),(5558,'KhanhKT',1062,25,NULL,'2015-08-05',1,6),(5559,'KhanhKT',1062,25,NULL,'2015-08-06',1,6),(5560,'KhanhKT',1062,25,NULL,'2015-08-07',1,6),(5561,'TaiNT',1063,25,NULL,'2015-08-03',1,1),(5562,'TaiNT',1063,25,NULL,'2015-08-04',1,1),(5563,'TaiNT',1063,25,NULL,'2015-08-05',1,1),(5564,'TaiNT',1063,25,'Đổi sang phòng 202','2015-08-06',0,1),(5565,'TaiNT',1063,25,'Đổi sang phòng 202','2015-08-07',0,1),(5566,'KieuTA',1063,25,NULL,'2015-08-04',1,2),(5567,'KieuTA',1063,25,'Đổi sang phòng 202','2015-08-06',0,2),(5568,'KieuTA',1063,25,'Đổi sang phòng 202','2015-08-07',0,2),(5569,'AnNDH',1063,25,NULL,'2015-08-03',1,3),(5570,'AnNDH',1063,25,NULL,'2015-08-04',1,3),(5571,'AnNDH',1063,25,NULL,'2015-08-05',1,3),(5572,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-06',0,3),(5573,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-07',0,3),(5574,'CamDTH',1063,25,NULL,'2015-08-04',1,4),(5575,'CamDTH',1063,25,'Đổi sang phòng 202','2015-08-06',0,4),(5576,'CamDTH',1063,25,'Đổi sang phòng 202','2015-08-07',0,4),(5577,'TuDT',1063,25,NULL,'2015-08-03',1,5),(5578,'TuDT',1063,25,NULL,'2015-08-04',1,5),(5579,'TuDT',1063,25,NULL,'2015-08-05',1,5),(5580,'TuDT',1063,25,'Đổi sang phòng 202','2015-08-06',0,5),(5581,'TuDT',1063,25,'Đổi sang phòng 202','2015-08-07',0,5),(5582,'AnNDH',1063,25,NULL,'2015-08-03',1,6),(5583,'AnNDH',1063,25,NULL,'2015-08-04',1,6),(5584,'AnNDH',1063,25,NULL,'2015-08-05',1,6),(5585,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-06',0,6),(5586,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-07',0,6),(5587,'PhuongLHK',1064,25,NULL,'2015-08-03',1,4),(5588,'PhuongLHK',1064,25,NULL,'2015-08-04',1,4),(5589,'PhuongLHK',1064,25,NULL,'2015-08-05',1,4),(5590,'PhuongLHK',1064,25,NULL,'2015-08-06',1,4),(5591,'PhuongLHK',1064,25,NULL,'2015-08-07',1,4),(5592,'KieuTA',1051,25,NULL,'2015-08-03',1,1),(5593,'KieuTA',1051,25,NULL,'2015-08-05',1,1),(5594,'HungNH',1051,25,NULL,'2015-08-03',1,2),(5595,'HungNH',1051,25,NULL,'2015-08-04',1,2),(5596,'HungNH',1051,25,NULL,'2015-08-05',1,2),(5597,'HungNH',1051,25,NULL,'2015-08-06',1,2),(5598,'HungNH',1051,25,NULL,'2015-08-07',1,2),(5599,'ThuyetDTM',1051,25,NULL,'2015-08-03',1,3),(5600,'ThuyetDTM',1051,25,NULL,'2015-08-04',1,3),(5601,'ThuyetDTM',1051,25,NULL,'2015-08-05',1,3),(5602,'ThuyetDTM',1051,25,NULL,'2015-08-06',1,3),(5603,'ThuyetDTM',1051,25,NULL,'2015-08-07',1,3),(5604,'Cecile',1051,25,NULL,'2015-08-03',1,4),(5605,'Cecile',1051,25,NULL,'2015-08-04',1,4),(5606,'Cecile',1051,25,NULL,'2015-08-05',1,4),(5607,'Cecile',1051,25,NULL,'2015-08-06',1,4),(5608,'Cecile',1051,25,NULL,'2015-08-07',1,4),(5609,'CamDTH',1051,25,NULL,'2015-08-03',1,5),(5610,'CamDTH',1051,25,NULL,'2015-08-05',1,5),(5611,'HungNH',1051,25,NULL,'2015-08-03',1,6),(5612,'HungNH',1051,25,NULL,'2015-08-04',1,6),(5613,'HungNH',1051,25,NULL,'2015-08-05',1,6),(5614,'HungNH',1051,25,NULL,'2015-08-06',1,6),(5615,'HungNH',1051,25,NULL,'2015-08-07',1,6),(5616,'HungLD',1052,25,NULL,'2015-08-03',1,4),(5617,'HungLD',1052,25,NULL,'2015-08-04',1,4),(5618,'HungLD',1052,25,NULL,'2015-08-05',1,4),(5619,'HungLD',1052,25,NULL,'2015-08-06',1,4),(5620,'HungLD',1052,25,NULL,'2015-08-07',1,4),(5621,'CamDTH',1052,25,NULL,'2015-08-04',1,5),(5622,'CamDTH',1052,25,NULL,'2015-08-06',1,5),(5623,'CamDTH',1052,25,NULL,'2015-08-07',1,5),(5624,'TaiNT',1052,25,NULL,'2015-08-03',1,6),(5625,'TaiNT',1052,25,NULL,'2015-08-04',1,6),(5626,'TaiNT',1052,25,NULL,'2015-08-05',1,6),(5627,'TaiNT',1052,25,NULL,'2015-08-06',1,6),(5628,'TaiNT',1052,25,NULL,'2015-08-07',1,6),(5629,'ThanhDT',1065,25,NULL,'2015-08-03',1,4),(5630,'ThanhDT',1065,25,NULL,'2015-08-04',1,4),(5631,'ThanhDT',1065,25,NULL,'2015-08-05',1,4),(5632,'ThanhDT',1065,25,NULL,'2015-08-06',1,4),(5633,'ThanhDT',1065,25,NULL,'2015-08-07',1,4),(5634,'AnhNT',1065,25,NULL,'2015-08-03',1,5),(5635,'AnhNT',1065,25,NULL,'2015-08-04',1,5),(5636,'AnhNT',1065,25,NULL,'2015-08-05',1,5),(5637,'AnhNT',1065,25,NULL,'2015-08-06',1,5),(5638,'AnhNT',1065,25,NULL,'2015-08-07',1,5),(5639,'PhuongLHK',1065,25,NULL,'2015-08-04',1,6),(5640,'PhuongLHK',1065,25,NULL,'2015-08-06',1,6),(5641,'PhuongLHK',1065,25,NULL,'2015-08-07',1,6),(5642,'TriBD',1068,25,NULL,'2015-08-04',1,1),(5643,'TriBD',1068,25,NULL,'2015-08-06',1,1),(5644,'TriBD',1068,25,NULL,'2015-08-04',1,2),(5645,'TriBD',1068,25,NULL,'2015-08-06',1,2),(5646,'LoiND',1068,25,NULL,'2015-08-03',1,4),(5647,'LoiND',1068,25,NULL,'2015-08-04',1,4),(5648,'LoiND',1068,25,NULL,'2015-08-05',1,4),(5649,'LoiND',1068,25,NULL,'2015-08-06',1,4),(5650,'LoiND',1068,25,NULL,'2015-08-07',1,4),(5651,'ChungKTT',1068,25,NULL,'2015-08-03',1,5),(5652,'ChungKTT',1068,25,NULL,'2015-08-04',1,5),(5653,'ChungKTT',1068,25,NULL,'2015-08-05',1,5),(5654,'ChungKTT',1068,25,NULL,'2015-08-06',1,5),(5655,'ChungKTT',1068,25,NULL,'2015-08-07',1,5),(5656,'PhuongLHK',1068,25,NULL,'2015-08-03',1,6),(5657,'PhuongLHK',1068,25,NULL,'2015-08-05',1,6),(5658,'ChauLH',1067,25,NULL,'2015-08-03',1,1),(5659,'ChauLH',1067,25,NULL,'2015-08-04',1,1),(5660,'ChauLH',1067,25,NULL,'2015-08-05',1,1),(5661,'ChauLH',1067,25,NULL,'2015-08-06',1,1),(5662,'ChauLH',1067,25,NULL,'2015-08-07',1,1),(5663,'PhuongLHK',1067,25,NULL,'2015-08-03',1,2),(5664,'PhuongLHK',1067,25,NULL,'2015-08-05',1,2),(5665,'HiepNT',1067,25,NULL,'2015-08-03',1,3),(5666,'HiepNT',1067,25,NULL,'2015-08-04',1,3),(5667,'HiepNT',1067,25,NULL,'2015-08-05',1,3),(5668,'HiepNT',1067,25,NULL,'2015-08-06',1,3),(5669,'HiepNT',1067,25,NULL,'2015-08-07',1,3),(5670,'VinhDP',1054,25,NULL,'2015-08-03',1,1),(5671,'VinhDP',1054,25,NULL,'2015-08-04',1,1),(5672,'VinhDP',1054,25,NULL,'2015-08-05',1,1),(5673,'VinhDP',1054,25,NULL,'2015-08-06',1,1),(5674,'VinhDP',1054,25,NULL,'2015-08-07',1,1),(5675,'AnNDH',1054,25,NULL,'2015-08-03',1,2),(5676,'AnNDH',1054,25,NULL,'2015-08-04',1,2),(5677,'AnNDH',1054,25,NULL,'2015-08-05',1,2),(5678,'AnNDH',1054,25,NULL,'2015-08-06',1,2),(5679,'AnNDH',1054,25,NULL,'2015-08-07',1,2),(5680,'HoangNT',1054,25,NULL,'2015-08-03',1,3),(5681,'HoangNT',1054,25,NULL,'2015-08-05',1,3),(5682,'HiepNT',1054,25,NULL,'2015-08-03',1,4),(5683,'HiepNT',1054,25,NULL,'2015-08-05',1,4),(5684,'HiepTT',1054,25,NULL,'2015-08-03',1,5),(5685,'HiepTT',1054,25,NULL,'2015-08-04',1,5),(5686,'HiepTT',1054,25,NULL,'2015-08-05',1,5),(5687,'HiepTT',1054,25,NULL,'2015-08-06',1,5),(5688,'HiepTT',1054,25,NULL,'2015-08-07',1,5),(5689,'BinhDTH',1054,25,NULL,'2015-08-03',1,6),(5690,'BinhDTH',1054,25,NULL,'2015-08-04',1,6),(5691,'BinhDTH',1054,25,NULL,'2015-08-05',1,6),(5692,'BinhDTH',1054,25,NULL,'2015-08-06',1,6),(5693,'BinhDTH',1054,25,NULL,'2015-08-07',1,6),(5694,'NhuLTL',1066,25,NULL,'2015-08-03',1,3),(5695,'NhuLTL',1066,25,NULL,'2015-08-04',1,3),(5696,'NhuLTL',1066,25,NULL,'2015-08-05',1,3),(5697,'NhuLTL',1066,25,NULL,'2015-08-06',1,3),(5698,'NhuLTL',1066,25,NULL,'2015-08-07',1,3),(5699,'HoangNT',1066,25,NULL,'2015-08-03',1,4),(5700,'HoangNT',1066,25,NULL,'2015-08-04',1,4),(5701,'HoangNT',1066,25,NULL,'2015-08-05',1,4),(5702,'HoangNT',1066,25,NULL,'2015-08-06',1,4),(5703,'HoangNT',1066,25,NULL,'2015-08-07',1,4),(5704,'TuDT',1066,25,NULL,'2015-08-03',1,6),(5705,'TuDT',1066,25,NULL,'2015-08-04',1,6),(5706,'TuDT',1066,25,NULL,'2015-08-05',1,6),(5707,'TuDT',1066,25,NULL,'2015-08-06',1,6),(5708,'TuDT',1066,25,NULL,'2015-08-07',1,6),(5709,'HungLD',1069,25,NULL,'2015-08-03',1,1),(5710,'HungLD',1069,25,NULL,'2015-08-05',1,1),(5711,'KhanhKT',1069,25,NULL,'2015-08-03',1,2),(5712,'KhanhKT',1069,25,'Đổi sang phòng 403','2015-08-04',0,2),(5713,'KhanhKT',1069,25,NULL,'2015-08-05',1,2),(5714,'KhanhKT',1069,25,NULL,'2015-08-06',1,2),(5715,'KhanhKT',1069,25,NULL,'2015-08-07',1,2),(5716,'TaiNT',1069,25,NULL,'2015-08-03',1,3),(5717,'TaiNT',1069,25,'Đổi sang phòng 403','2015-08-04',0,3),(5718,'TaiNT',1069,25,NULL,'2015-08-05',1,3),(5719,'TaiNT',1069,25,NULL,'2015-08-06',1,3),(5720,'TaiNT',1069,25,NULL,'2015-08-07',1,3),(5721,'VanTTN',1070,25,NULL,'2015-08-03',1,2),(5722,'VanTTN',1070,25,NULL,'2015-08-04',1,2),(5723,'VanTTN',1070,25,NULL,'2015-08-05',1,2),(5724,'VanTTN',1070,25,NULL,'2015-08-06',1,2),(5725,'VanTTN',1070,25,NULL,'2015-08-07',1,2),(5726,'VinhDP',1070,25,NULL,'2015-08-03',1,3),(5727,'VinhDP',1070,25,NULL,'2015-08-04',1,3),(5728,'VinhDP',1070,25,NULL,'2015-08-05',1,3),(5729,'VinhDP',1070,25,NULL,'2015-08-06',1,3),(5730,'VinhDP',1070,25,NULL,'2015-08-07',1,3),(5731,'VanTTN',1070,25,NULL,'2015-08-03',1,4),(5732,'VanTTN',1070,25,NULL,'2015-08-04',1,4),(5733,'VanTTN',1070,25,NULL,'2015-08-05',1,4),(5734,'VanTTN',1070,25,NULL,'2015-08-06',1,4),(5735,'VanTTN',1070,25,NULL,'2015-08-07',1,4),(5736,'VinhDP',1070,25,NULL,'2015-08-03',1,6),(5737,'VinhDP',1070,25,NULL,'2015-08-04',1,6),(5738,'VinhDP',1070,25,NULL,'2015-08-05',1,6),(5739,'VinhDP',1070,25,NULL,'2015-08-06',1,6),(5740,'VinhDP',1070,25,NULL,'2015-08-07',1,6),(5741,'SuTV',1071,25,NULL,'2015-08-03',1,1),(5742,'SuTV',1071,25,'Đổi sang phòng 508','2015-08-04',0,1),(5743,'SuTV',1071,25,NULL,'2015-08-05',1,1),(5744,'SuTV',1071,25,NULL,'2015-08-06',1,1),(5745,'SuTV',1071,25,NULL,'2015-08-07',1,1),(5746,'TaiNT',1071,25,NULL,'2015-08-03',1,2),(5747,'TaiNT',1071,25,'Đổi sang phòng 508','2015-08-04',0,2),(5748,'TaiNT',1071,25,NULL,'2015-08-05',1,2),(5749,'TaiNT',1071,25,NULL,'2015-08-06',1,2),(5750,'TaiNT',1071,25,NULL,'2015-08-07',1,2),(5751,'TaiNT',1071,25,NULL,'2015-08-03',1,4),(5752,'TaiNT',1071,25,NULL,'2015-08-04',1,4),(5753,'TaiNT',1071,25,NULL,'2015-08-05',1,4),(5754,'TaiNT',1071,25,NULL,'2015-08-06',1,4),(5755,'TaiNT',1071,25,NULL,'2015-08-07',1,4),(5756,'HoangNT',1071,25,'Đổi sang phòng 403','2015-08-03',0,5),(5757,'HoangNT',1071,25,NULL,'2015-08-04',1,5),(5758,'HoangNT',1071,25,NULL,'2015-08-05',1,5),(5759,'HoangNT',1071,25,NULL,'2015-08-06',1,5),(5760,'HoangNT',1071,25,NULL,'2015-08-07',1,5),(5761,'HungNH',1072,25,NULL,'2015-08-03',1,4),(5762,'HungNH',1072,25,NULL,'2015-08-05',1,4),(5763,'HungNH',1072,25,'Đổi sang phòng 403','2015-08-07',0,4),(5764,'VinhDP',1072,25,NULL,'2015-08-03',1,5),(5765,'VinhDP',1072,25,NULL,'2015-08-04',1,5),(5766,'VinhDP',1072,25,NULL,'2015-08-05',1,5),(5767,'VinhDP',1072,25,'Đổi sang phòng 403','2015-08-06',0,5),(5768,'VinhDP',1072,25,'Đổi sang phòng 403','2015-08-07',0,5),(5769,'SuTV',1072,25,NULL,'2015-08-03',1,6),(5770,'SuTV',1072,25,NULL,'2015-08-04',1,6),(5771,'SuTV',1072,25,NULL,'2015-08-05',1,6),(5772,'SuTV',1072,25,'Đổi sang phòng 403','2015-08-06',0,6),(5773,'SuTV',1072,25,'Đổi sang phòng 403','2015-08-07',0,6),(5774,'KhanhKT',1049,25,'Thay đổi phòng từ phòng 115 sang phòng 101','2015-08-03',1,5),(5775,'HoangNT',1053,25,'Thay đổi phòng từ phòng 504 sang phòng 403','2015-08-03',1,5),(5776,'HungLD',1055,25,'Thay đổi phòng từ phòng 115 sang phòng 102','2015-08-04',1,1),(5777,'HiepNT',1055,25,'Thay đổi phòng từ phòng 115 sang phòng 102','2015-08-04',1,2),(5778,'ChungKTT',1055,25,'Thay đổi phòng từ phòng 115 sang phòng 102','2015-08-04',1,3),(5779,'KhanhKT',1053,25,'Thay đổi phòng từ phòng 412 sang phòng 403','2015-08-04',1,2),(5780,'TaiNT',1053,25,'Thay đổi phòng từ phòng 412 sang phòng 403','2015-08-04',1,3),(5781,'SuTV',1072,25,'Thay đổi phòng từ phòng 504 sang phòng 508','2015-08-04',1,1),(5782,'TaiNT',1072,25,'Thay đổi phòng từ phòng 504 sang phòng 508','2015-08-04',1,2),(5783,'PhuongLHK',1049,25,NULL,'2015-08-11',1,1),(5784,'PhuongLHK',1049,25,NULL,'2015-08-13',1,1),(5785,'PhuongLHK',1049,25,NULL,'2015-08-11',1,2),(5786,'PhuongLHK',1049,25,NULL,'2015-08-13',1,2),(5787,'HungNH',1049,25,NULL,'2015-08-11',1,4),(5788,'HungNH',1049,25,NULL,'2015-08-13',1,4),(5789,'HungNH',1049,25,NULL,'2015-08-11',1,5),(5790,'HungNH',1049,25,NULL,'2015-08-13',1,5),(5791,'HungLD',1058,25,NULL,'2015-08-11',1,5),(5792,'HungLD',1058,25,NULL,'2015-08-13',1,5),(5793,'HoangNT',1059,25,NULL,'2015-08-10',1,2),(5794,'HoangNT',1059,25,NULL,'2015-08-11',1,2),(5795,'HoangNT',1059,25,NULL,'2015-08-12',1,2),(5796,'HoangNT',1059,25,NULL,'2015-08-13',1,2),(5797,'HoangNT',1059,25,NULL,'2015-08-14',1,2),(5798,'TaiNT',1059,25,NULL,'2015-08-11',1,5),(5799,'TaiNT',1059,25,NULL,'2015-08-12',1,5),(5800,'TaiNT',1059,25,NULL,'2015-08-13',1,5),(5801,'TaiNT',1059,25,NULL,'2015-08-14',1,5),(5802,'HungLD',1060,25,NULL,'2015-08-11',1,1),(5803,'HungLD',1060,25,NULL,'2015-08-13',1,1),(5804,'HungLD',1060,25,NULL,'2015-08-14',1,1),(5805,'HiepNT',1060,25,NULL,'2015-08-10',1,2),(5806,'HiepNT',1060,25,NULL,'2015-08-11',1,2),(5807,'HiepNT',1060,25,NULL,'2015-08-12',1,2),(5808,'HiepNT',1060,25,NULL,'2015-08-13',1,2),(5809,'HiepNT',1060,25,NULL,'2015-08-14',1,2),(5810,'ChungKTT',1060,25,NULL,'2015-08-10',1,3),(5811,'ChungKTT',1060,25,NULL,'2015-08-11',1,3),(5812,'ChungKTT',1060,25,NULL,'2015-08-12',1,3),(5813,'ChungKTT',1060,25,NULL,'2015-08-13',1,3),(5814,'ChungKTT',1060,25,NULL,'2015-08-14',1,3),(5815,'KhanhKT',1060,25,NULL,'2015-08-10',1,4),(5816,'KhanhKT',1060,25,NULL,'2015-08-14',1,4),(5817,'KhanhKT',1060,25,NULL,'2015-08-10',1,5),(5818,'KhanhKT',1060,25,NULL,'2015-08-14',1,5),(5819,'LienTTH',1061,25,NULL,'2015-08-10',1,2),(5820,'HuongPTT',1061,25,NULL,'2015-08-11',1,2),(5821,'LienTTH',1061,25,NULL,'2015-08-12',1,2),(5822,'HuongPTT',1061,25,NULL,'2015-08-13',1,2),(5823,'LienTTH',1061,25,NULL,'2015-08-14',1,2),(5824,'LienTTH',1061,25,NULL,'2015-08-10',1,3),(5825,'HuongPTT',1061,25,NULL,'2015-08-11',1,3),(5826,'LienTTH',1061,25,NULL,'2015-08-12',1,3),(5827,'HuongPTT',1061,25,NULL,'2015-08-13',1,3),(5828,'LienTTH',1061,25,NULL,'2015-08-14',1,3),(5829,'AnNDH',1061,25,NULL,'2015-08-10',1,4),(5830,'AnNDH',1061,25,NULL,'2015-08-12',1,4),(5831,'SuTV',1061,25,NULL,'2015-08-10',1,5),(5832,'SuTV',1061,25,NULL,'2015-08-11',1,5),(5833,'SuTV',1061,25,NULL,'2015-08-12',1,5),(5834,'SuTV',1061,25,NULL,'2015-08-13',1,5),(5835,'SuTV',1061,25,NULL,'2015-08-14',1,5),(5836,'KieuTA',1061,25,NULL,'2015-08-10',1,6),(5837,'KieuTA',1061,25,NULL,'2015-08-11',1,6),(5838,'KieuTA',1061,25,NULL,'2015-08-12',1,6),(5839,'KieuTA',1061,25,NULL,'2015-08-13',1,6),(5840,'KieuTA',1061,25,NULL,'2015-08-14',1,6),(5841,'HiepTT',1050,25,NULL,'2015-08-10',1,1),(5842,'HiepTT',1050,25,NULL,'2015-08-11',1,1),(5843,'HiepTT',1050,25,NULL,'2015-08-12',1,1),(5844,'HiepTT',1050,25,NULL,'2015-08-13',1,1),(5845,'HiepTT',1050,25,NULL,'2015-08-14',1,1),(5846,'HungLD',1050,25,NULL,'2015-08-10',1,2),(5847,'HungLD',1050,25,NULL,'2015-08-11',1,2),(5848,'HungLD',1050,25,NULL,'2015-08-12',1,2),(5849,'HungLD',1050,25,NULL,'2015-08-13',1,2),(5850,'HungLD',1050,25,NULL,'2015-08-14',1,2),(5851,'KieuTA',1050,25,NULL,'2015-08-11',1,3),(5852,'KieuTA',1050,25,NULL,'2015-08-13',1,3),(5853,'KieuTA',1050,25,NULL,'2015-08-14',1,3),(5854,'AnNDH',1062,25,NULL,'2015-08-10',1,1),(5855,'AnNDH',1062,25,NULL,'2015-08-11',1,1),(5856,'AnNDH',1062,25,NULL,'2015-08-12',1,1),(5857,'AnNDH',1062,25,NULL,'2015-08-13',1,1),(5858,'AnNDH',1062,25,NULL,'2015-08-14',1,1),(5859,'KieuTA',1062,25,NULL,'2015-08-10',1,2),(5860,'KieuTA',1062,25,NULL,'2015-08-12',1,2),(5861,'TuDT',1062,25,NULL,'2015-08-10',1,3),(5862,'TuDT',1062,25,NULL,'2015-08-11',1,3),(5863,'TuDT',1062,25,NULL,'2015-08-12',1,3),(5864,'TuDT',1062,25,NULL,'2015-08-13',1,3),(5865,'TuDT',1062,25,NULL,'2015-08-14',1,3),(5866,'HiepNT',1062,25,NULL,'2015-08-11',1,4),(5867,'HiepNT',1062,25,NULL,'2015-08-13',1,4),(5868,'HiepNT',1062,25,NULL,'2015-08-14',1,4),(5869,'BinhDTH',1062,25,NULL,'2015-08-10',1,5),(5870,'BinhDTH',1062,25,NULL,'2015-08-11',1,5),(5871,'BinhDTH',1062,25,NULL,'2015-08-12',1,5),(5872,'BinhDTH',1062,25,NULL,'2015-08-13',1,5),(5873,'BinhDTH',1062,25,NULL,'2015-08-14',1,5),(5874,'KhanhKT',1062,25,NULL,'2015-08-10',1,6),(5875,'KhanhKT',1062,25,NULL,'2015-08-11',1,6),(5876,'KhanhKT',1062,25,NULL,'2015-08-12',1,6),(5877,'KhanhKT',1062,25,NULL,'2015-08-13',1,6),(5878,'KhanhKT',1062,25,NULL,'2015-08-14',1,6),(5879,'TaiNT',1063,25,'Đổi sang phòng 202','2015-08-10',0,1),(5880,'TaiNT',1063,25,'Đổi sang phòng 202','2015-08-11',0,1),(5881,'TaiNT',1063,25,'Đổi sang phòng 202','2015-08-12',0,1),(5882,'TaiNT',1063,25,'Đổi sang phòng 202','2015-08-13',0,1),(5883,'TaiNT',1063,25,'Đổi sang phòng 202','2015-08-14',0,1),(5884,'KieuTA',1063,25,'Đổi sang phòng 202','2015-08-11',0,2),(5885,'KieuTA',1063,25,'Đổi sang phòng 202','2015-08-13',0,2),(5886,'KieuTA',1063,25,'Đổi sang phòng 202','2015-08-14',0,2),(5887,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-10',0,3),(5888,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-11',0,3),(5889,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-12',0,3),(5890,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-13',0,3),(5891,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-14',0,3),(5892,'CamDTH',1063,25,'Đổi sang phòng 202','2015-08-11',0,4),(5893,'CamDTH',1063,25,'Đổi sang phòng 202','2015-08-13',0,4),(5894,'CamDTH',1063,25,'Đổi sang phòng 202','2015-08-14',0,4),(5895,'TuDT',1063,25,'Đổi sang phòng 202','2015-08-10',0,5),(5896,'TuDT',1063,25,'Đổi sang phòng 202','2015-08-11',0,5),(5897,'TuDT',1063,25,'Đổi sang phòng 202','2015-08-12',0,5),(5898,'TuDT',1063,25,'Đổi sang phòng 202','2015-08-13',0,5),(5899,'TuDT',1063,25,'Đổi sang phòng 202','2015-08-14',0,5),(5900,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-10',0,6),(5901,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-11',0,6),(5902,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-12',0,6),(5903,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-13',0,6),(5904,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-14',0,6),(5905,'PhuongLHK',1064,25,NULL,'2015-08-10',1,4),(5906,'PhuongLHK',1064,25,NULL,'2015-08-11',1,4),(5907,'PhuongLHK',1064,25,NULL,'2015-08-12',1,4),(5908,'PhuongLHK',1064,25,NULL,'2015-08-13',1,4),(5909,'PhuongLHK',1064,25,NULL,'2015-08-14',1,4),(5910,'KieuTA',1051,25,NULL,'2015-08-10',1,1),(5911,'KieuTA',1051,25,NULL,'2015-08-12',1,1),(5912,'HungNH',1051,25,NULL,'2015-08-10',1,2),(5913,'HungNH',1051,25,NULL,'2015-08-11',1,2),(5914,'HungNH',1051,25,NULL,'2015-08-12',1,2),(5915,'HungNH',1051,25,NULL,'2015-08-13',1,2),(5916,'HungNH',1051,25,NULL,'2015-08-14',1,2),(5917,'ThuyetDTM',1051,25,NULL,'2015-08-10',1,3),(5918,'ThuyetDTM',1051,25,NULL,'2015-08-11',1,3),(5919,'ThuyetDTM',1051,25,NULL,'2015-08-12',1,3),(5920,'ThuyetDTM',1051,25,NULL,'2015-08-13',1,3),(5921,'ThuyetDTM',1051,25,NULL,'2015-08-14',1,3),(5922,'Cecile',1051,25,NULL,'2015-08-10',1,4),(5923,'Cecile',1051,25,NULL,'2015-08-11',1,4),(5924,'Cecile',1051,25,NULL,'2015-08-12',1,4),(5925,'Cecile',1051,25,NULL,'2015-08-13',1,4),(5926,'Cecile',1051,25,NULL,'2015-08-14',1,4),(5927,'CamDTH',1051,25,NULL,'2015-08-10',1,5),(5928,'CamDTH',1051,25,NULL,'2015-08-12',1,5),(5929,'HungNH',1051,25,NULL,'2015-08-10',1,6),(5930,'HungNH',1051,25,NULL,'2015-08-11',1,6),(5931,'HungNH',1051,25,NULL,'2015-08-12',1,6),(5932,'HungNH',1051,25,NULL,'2015-08-13',1,6),(5933,'HungNH',1051,25,NULL,'2015-08-14',1,6),(5934,'HungLD',1052,25,NULL,'2015-08-10',1,4),(5935,'HungLD',1052,25,NULL,'2015-08-11',1,4),(5936,'HungLD',1052,25,NULL,'2015-08-12',1,4),(5937,'HungLD',1052,25,NULL,'2015-08-13',1,4),(5938,'HungLD',1052,25,NULL,'2015-08-14',1,4),(5939,'CamDTH',1052,25,NULL,'2015-08-11',1,5),(5940,'CamDTH',1052,25,NULL,'2015-08-13',1,5),(5941,'CamDTH',1052,25,NULL,'2015-08-14',1,5),(5942,'TaiNT',1052,25,NULL,'2015-08-10',1,6),(5943,'TaiNT',1052,25,NULL,'2015-08-11',1,6),(5944,'TaiNT',1052,25,NULL,'2015-08-12',1,6),(5945,'TaiNT',1052,25,NULL,'2015-08-13',1,6),(5946,'TaiNT',1052,25,NULL,'2015-08-14',1,6),(5947,'ThanhDT',1065,25,NULL,'2015-08-10',1,4),(5948,'ThanhDT',1065,25,NULL,'2015-08-11',1,4),(5949,'ThanhDT',1065,25,NULL,'2015-08-12',1,4),(5950,'ThanhDT',1065,25,NULL,'2015-08-13',1,4),(5951,'ThanhDT',1065,25,NULL,'2015-08-14',1,4),(5952,'AnhNT',1065,25,NULL,'2015-08-10',1,5),(5953,'AnhNT',1065,25,NULL,'2015-08-11',1,5),(5954,'AnhNT',1065,25,NULL,'2015-08-12',1,5),(5955,'AnhNT',1065,25,NULL,'2015-08-13',1,5),(5956,'AnhNT',1065,25,NULL,'2015-08-14',1,5),(5957,'PhuongLHK',1065,25,NULL,'2015-08-11',1,6),(5958,'PhuongLHK',1065,25,NULL,'2015-08-13',1,6),(5959,'PhuongLHK',1065,25,NULL,'2015-08-14',1,6),(5960,'TriBD',1068,25,NULL,'2015-08-11',1,1),(5961,'TriBD',1068,25,NULL,'2015-08-13',1,1),(5962,'TriBD',1068,25,NULL,'2015-08-11',1,2),(5963,'TriBD',1068,25,NULL,'2015-08-13',1,2),(5964,'LoiND',1068,25,NULL,'2015-08-10',1,4),(5965,'LoiND',1068,25,NULL,'2015-08-11',1,4),(5966,'LoiND',1068,25,NULL,'2015-08-12',1,4),(5967,'LoiND',1068,25,NULL,'2015-08-13',1,4),(5968,'LoiND',1068,25,NULL,'2015-08-14',1,4),(5969,'ChungKTT',1068,25,NULL,'2015-08-10',1,5),(5970,'ChungKTT',1068,25,NULL,'2015-08-11',1,5),(5971,'ChungKTT',1068,25,NULL,'2015-08-12',1,5),(5972,'ChungKTT',1068,25,NULL,'2015-08-13',1,5),(5973,'ChungKTT',1068,25,NULL,'2015-08-14',1,5),(5974,'PhuongLHK',1068,25,NULL,'2015-08-10',1,6),(5975,'PhuongLHK',1068,25,NULL,'2015-08-12',1,6),(5976,'ChauLH',1067,25,NULL,'2015-08-10',1,1),(5977,'ChauLH',1067,25,NULL,'2015-08-11',1,1),(5978,'ChauLH',1067,25,NULL,'2015-08-12',1,1),(5979,'ChauLH',1067,25,NULL,'2015-08-13',1,1),(5980,'ChauLH',1067,25,NULL,'2015-08-14',1,1),(5981,'PhuongLHK',1067,25,NULL,'2015-08-10',1,2),(5982,'PhuongLHK',1067,25,NULL,'2015-08-12',1,2),(5983,'HiepNT',1067,25,NULL,'2015-08-10',1,3),(5984,'HiepNT',1067,25,NULL,'2015-08-11',1,3),(5985,'HiepNT',1067,25,NULL,'2015-08-12',1,3),(5986,'HiepNT',1067,25,NULL,'2015-08-13',1,3),(5987,'HiepNT',1067,25,NULL,'2015-08-14',1,3),(5988,'VinhDP',1054,25,NULL,'2015-08-10',1,1),(5989,'VinhDP',1054,25,NULL,'2015-08-11',1,1),(5990,'VinhDP',1054,25,NULL,'2015-08-12',1,1),(5991,'VinhDP',1054,25,NULL,'2015-08-13',1,1),(5992,'VinhDP',1054,25,NULL,'2015-08-14',1,1),(5993,'AnNDH',1054,25,NULL,'2015-08-10',1,2),(5994,'AnNDH',1054,25,NULL,'2015-08-11',1,2),(5995,'AnNDH',1054,25,NULL,'2015-08-12',1,2),(5996,'AnNDH',1054,25,NULL,'2015-08-13',1,2),(5997,'AnNDH',1054,25,NULL,'2015-08-14',1,2),(5998,'HoangNT',1054,25,NULL,'2015-08-10',1,3),(5999,'HoangNT',1054,25,NULL,'2015-08-12',1,3),(6000,'HiepNT',1054,25,NULL,'2015-08-10',1,4),(6001,'HiepNT',1054,25,NULL,'2015-08-12',1,4),(6002,'HiepTT',1054,25,NULL,'2015-08-10',1,5),(6003,'HiepTT',1054,25,NULL,'2015-08-11',1,5),(6004,'HiepTT',1054,25,NULL,'2015-08-12',1,5),(6005,'HiepTT',1054,25,NULL,'2015-08-13',1,5),(6006,'HiepTT',1054,25,NULL,'2015-08-14',1,5),(6007,'BinhDTH',1054,25,NULL,'2015-08-10',1,6),(6008,'BinhDTH',1054,25,NULL,'2015-08-11',1,6),(6009,'BinhDTH',1054,25,NULL,'2015-08-12',1,6),(6010,'BinhDTH',1054,25,NULL,'2015-08-13',1,6),(6011,'BinhDTH',1054,25,NULL,'2015-08-14',1,6),(6012,'NhuLTL',1066,25,NULL,'2015-08-10',1,3),(6013,'NhuLTL',1066,25,NULL,'2015-08-11',1,3),(6014,'NhuLTL',1066,25,NULL,'2015-08-12',1,3),(6015,'NhuLTL',1066,25,NULL,'2015-08-13',1,3),(6016,'NhuLTL',1066,25,NULL,'2015-08-14',1,3),(6017,'HoangNT',1066,25,NULL,'2015-08-10',1,4),(6018,'HoangNT',1066,25,NULL,'2015-08-11',1,4),(6019,'HoangNT',1066,25,NULL,'2015-08-12',1,4),(6020,'HoangNT',1066,25,NULL,'2015-08-13',1,4),(6021,'HoangNT',1066,25,NULL,'2015-08-14',1,4),(6022,'TuDT',1066,25,NULL,'2015-08-10',1,6),(6023,'TuDT',1066,25,NULL,'2015-08-11',1,6),(6024,'TuDT',1066,25,NULL,'2015-08-12',1,6),(6025,'TuDT',1066,25,NULL,'2015-08-13',1,6),(6026,'TuDT',1066,25,NULL,'2015-08-14',1,6),(6027,'HungLD',1069,25,NULL,'2015-08-10',1,1),(6028,'HungLD',1069,25,NULL,'2015-08-12',1,1),(6029,'KhanhKT',1069,25,NULL,'2015-08-10',1,2),(6030,'KhanhKT',1069,25,NULL,'2015-08-11',1,2),(6031,'KhanhKT',1069,25,NULL,'2015-08-12',1,2),(6032,'KhanhKT',1069,25,NULL,'2015-08-13',1,2),(6033,'KhanhKT',1069,25,NULL,'2015-08-14',1,2),(6034,'TaiNT',1069,25,NULL,'2015-08-10',1,3),(6035,'TaiNT',1069,25,NULL,'2015-08-11',1,3),(6036,'TaiNT',1069,25,NULL,'2015-08-12',1,3),(6037,'TaiNT',1069,25,NULL,'2015-08-13',1,3),(6038,'TaiNT',1069,25,NULL,'2015-08-14',1,3),(6039,'VanTTN',1070,25,NULL,'2015-08-10',1,2),(6040,'VanTTN',1070,25,NULL,'2015-08-11',1,2),(6041,'VanTTN',1070,25,NULL,'2015-08-12',1,2),(6042,'VanTTN',1070,25,NULL,'2015-08-13',1,2),(6043,'VanTTN',1070,25,NULL,'2015-08-14',1,2),(6044,'VinhDP',1070,25,NULL,'2015-08-10',1,3),(6045,'VinhDP',1070,25,NULL,'2015-08-11',1,3),(6046,'VinhDP',1070,25,NULL,'2015-08-12',1,3),(6047,'VinhDP',1070,25,NULL,'2015-08-13',1,3),(6048,'VinhDP',1070,25,NULL,'2015-08-14',1,3),(6049,'VanTTN',1070,25,NULL,'2015-08-10',1,4),(6050,'VanTTN',1070,25,NULL,'2015-08-11',1,4),(6051,'VanTTN',1070,25,NULL,'2015-08-12',1,4),(6052,'VanTTN',1070,25,NULL,'2015-08-13',1,4),(6053,'VanTTN',1070,25,NULL,'2015-08-14',1,4),(6054,'VinhDP',1070,25,NULL,'2015-08-10',1,6),(6055,'VinhDP',1070,25,NULL,'2015-08-11',1,6),(6056,'VinhDP',1070,25,NULL,'2015-08-12',1,6),(6057,'VinhDP',1070,25,NULL,'2015-08-13',1,6),(6058,'VinhDP',1070,25,NULL,'2015-08-14',1,6),(6059,'SuTV',1071,25,NULL,'2015-08-10',1,1),(6060,'SuTV',1071,25,NULL,'2015-08-11',1,1),(6061,'SuTV',1071,25,NULL,'2015-08-12',1,1),(6062,'SuTV',1071,25,NULL,'2015-08-13',1,1),(6063,'SuTV',1071,25,NULL,'2015-08-14',1,1),(6064,'TaiNT',1071,25,NULL,'2015-08-10',1,2),(6065,'TaiNT',1071,25,NULL,'2015-08-11',1,2),(6066,'TaiNT',1071,25,NULL,'2015-08-12',1,2),(6067,'TaiNT',1071,25,NULL,'2015-08-13',1,2),(6068,'TaiNT',1071,25,NULL,'2015-08-14',1,2),(6069,'TaiNT',1071,25,NULL,'2015-08-10',1,4),(6070,'TaiNT',1071,25,NULL,'2015-08-11',1,4),(6071,'TaiNT',1071,25,NULL,'2015-08-12',1,4),(6072,'TaiNT',1071,25,NULL,'2015-08-13',1,4),(6073,'TaiNT',1071,25,NULL,'2015-08-14',1,4),(6074,'HoangNT',1071,25,NULL,'2015-08-10',1,5),(6075,'HoangNT',1071,25,NULL,'2015-08-11',1,5),(6076,'HoangNT',1071,25,NULL,'2015-08-12',1,5),(6077,'HoangNT',1071,25,NULL,'2015-08-13',1,5),(6078,'HoangNT',1071,25,NULL,'2015-08-14',1,5),(6079,'HungNH',1072,25,'Đổi sang phòng 403','2015-08-10',0,4),(6080,'HungNH',1072,25,'Đổi sang phòng 403','2015-08-12',0,4),(6081,'HungNH',1072,25,NULL,'2015-08-14',1,4),(6082,'VinhDP',1072,25,'Đổi sang phòng 403','2015-08-10',0,5),(6083,'VinhDP',1072,25,'Đổi sang phòng 403','2015-08-11',0,5),(6084,'VinhDP',1072,25,'Đổi sang phòng 403','2015-08-12',0,5),(6085,'VinhDP',1072,25,'Đổi sang phòng 403','2015-08-13',0,5),(6086,'VinhDP',1072,25,NULL,'2015-08-14',1,5),(6087,'SuTV',1072,25,'Đổi sang phòng 403','2015-08-10',0,6),(6088,'SuTV',1072,25,'Đổi sang phòng 403','2015-08-11',0,6),(6089,'SuTV',1072,25,'Đổi sang phòng 403','2015-08-12',0,6),(6090,'SuTV',1072,25,'Đổi sang phòng 403','2015-08-13',0,6),(6091,'SuTV',1072,25,NULL,'2015-08-14',1,6),(6092,'PhuongLHK',1049,25,NULL,'2015-08-12',1,1),(6093,'PhuongLHK',1049,25,NULL,'2015-08-14',1,1),(6094,'PhuongLHK',1049,25,NULL,'2015-08-14',1,2),(6095,'HungNH',1049,25,NULL,'2015-08-12',1,5),(6096,'HungNH',1049,25,NULL,'2015-08-14',1,5),(6097,'HungLD',1058,25,NULL,'2015-08-12',1,5),(6098,'HungLD',1058,25,NULL,'2015-08-14',1,5),(6099,'HoangNT',1059,25,NULL,'2015-08-15',1,2),(6100,'TaiNT',1059,25,NULL,'2015-08-15',1,5),(6101,'HungLD',1060,25,NULL,'2015-08-15',1,1),(6102,'HiepNT',1060,25,NULL,'2015-08-15',1,2),(6103,'ChungKTT',1060,25,NULL,'2015-08-15',1,3),(6104,'KhanhKT',1060,25,NULL,'2015-08-11',1,4),(6105,'KhanhKT',1060,25,NULL,'2015-08-15',1,4),(6106,'KhanhKT',1060,25,NULL,'2015-08-11',1,5),(6107,'KhanhKT',1060,25,NULL,'2015-08-15',1,5),(6108,'LienTTH',1061,25,NULL,'2015-08-15',1,2),(6109,'LienTTH',1061,25,NULL,'2015-08-15',1,3),(6110,'AnNDH',1061,25,NULL,'2015-08-11',1,4),(6111,'AnNDH',1061,25,NULL,'2015-08-13',1,4),(6112,'SuTV',1061,25,NULL,'2015-08-15',1,5),(6113,'KieuTA',1061,25,NULL,'2015-08-15',1,6),(6114,'HiepTT',1050,25,NULL,'2015-08-15',1,1),(6115,'HungLD',1050,25,NULL,'2015-08-15',1,2),(6116,'KieuTA',1050,25,NULL,'2015-08-12',1,3),(6117,'KieuTA',1050,25,NULL,'2015-08-15',1,3),(6118,'AnNDH',1062,25,NULL,'2015-08-15',1,1),(6119,'TuDT',1062,25,NULL,'2015-08-15',1,3),(6120,'HiepNT',1062,25,NULL,'2015-08-15',1,4),(6121,'BinhDTH',1062,25,NULL,'2015-08-15',1,5),(6122,'KhanhKT',1062,25,NULL,'2015-08-15',1,6),(6123,'TaiNT',1063,25,'Đổi sang phòng 202','2015-08-15',0,1),(6124,'KieuTA',1063,25,'Đổi sang phòng 202','2015-08-15',0,2),(6125,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-15',0,3),(6126,'CamDTH',1063,25,'Đổi sang phòng 202','2015-08-12',0,4),(6127,'CamDTH',1063,25,'Đổi sang phòng 202','2015-08-15',0,4),(6128,'TuDT',1063,25,'Đổi sang phòng 202','2015-08-15',0,5),(6129,'AnNDH',1063,25,'Đổi sang phòng 202','2015-08-15',0,6),(6130,'PhuongLHK',1064,25,NULL,'2015-08-15',1,4),(6131,'KieuTA',1051,25,NULL,'2015-08-11',1,1),(6132,'KieuTA',1051,25,NULL,'2015-08-13',1,1),(6133,'HungNH',1051,25,NULL,'2015-08-15',1,2),(6134,'ThuyetDTM',1051,25,NULL,'2015-08-15',1,3),(6135,'Cecile',1051,25,NULL,'2015-08-15',1,4),(6136,'HungNH',1051,25,NULL,'2015-08-15',1,6),(6137,'HungLD',1052,25,NULL,'2015-08-15',1,4),(6138,'CamDTH',1052,25,NULL,'2015-08-15',1,5),(6139,'TaiNT',1052,25,NULL,'2015-08-15',1,6),(6140,'ThanhDT',1065,25,NULL,'2015-08-15',1,4),(6141,'AnhNT',1065,25,NULL,'2015-08-15',1,5),(6142,'PhuongLHK',1065,25,NULL,'2015-08-15',1,6),(6143,'TriBD',1068,25,NULL,'2015-08-12',1,1),(6144,'TriBD',1068,25,NULL,'2015-08-14',1,1),(6145,'TriBD',1068,25,NULL,'2015-08-12',1,2),(6146,'TriBD',1068,25,NULL,'2015-08-14',1,2),(6147,'LoiND',1068,25,NULL,'2015-08-15',1,4),(6148,'ChungKTT',1068,25,NULL,'2015-08-15',1,5),(6149,'ChauLH',1067,25,NULL,'2015-08-15',1,1),(6150,'HiepNT',1067,25,NULL,'2015-08-15',1,3),(6151,'VinhDP',1054,25,NULL,'2015-08-15',1,1),(6152,'AnNDH',1054,25,NULL,'2015-08-15',1,2),(6153,'HoangNT',1054,25,NULL,'2015-08-11',1,3),(6154,'HoangNT',1054,25,NULL,'2015-08-13',1,3),(6155,'HiepTT',1054,25,NULL,'2015-08-15',1,5),(6156,'BinhDTH',1054,25,NULL,'2015-08-15',1,6),(6157,'NhuLTL',1066,25,NULL,'2015-08-15',1,3),(6158,'HoangNT',1066,25,NULL,'2015-08-15',1,4),(6159,'TuDT',1066,25,NULL,'2015-08-15',1,6),(6160,'KhanhKT',1069,25,NULL,'2015-08-15',1,2),(6161,'TaiNT',1069,25,NULL,'2015-08-15',1,3),(6162,'VanTTN',1070,25,NULL,'2015-08-15',1,2),(6163,'VinhDP',1070,25,NULL,'2015-08-15',1,3),(6164,'VanTTN',1070,25,NULL,'2015-08-15',1,4),(6165,'VinhDP',1070,25,NULL,'2015-08-15',1,6),(6166,'SuTV',1071,25,NULL,'2015-08-15',1,1),(6167,'TaiNT',1071,25,NULL,'2015-08-15',1,2),(6168,'TaiNT',1071,25,NULL,'2015-08-15',1,4),(6169,'HoangNT',1071,25,NULL,'2015-08-15',1,5),(6170,'HungNH',1072,25,NULL,'2015-08-15',1,4),(6171,'VinhDP',1072,25,NULL,'2015-08-15',1,5),(6172,'SuTV',1072,25,NULL,'2015-08-15',1,6),(6173,'KhanhKT',1049,12,'Nhập tay','2015-08-04',1,1),(6174,'HungNH',1053,25,'Đổi từ phòng 508 sang phòng 403','2015-08-12',1,4),(6175,'VinhDP',1053,25,'Đổi từ phòng 508 sang phòng 403','2015-08-06',1,5),(6176,'VinhDP',1053,25,'Đổi từ phòng 508 sang phòng 403','2015-08-07',1,5),(6177,'VinhDP',1053,25,'Đổi từ phòng 508 sang phòng 403','2015-08-10',1,5),(6178,'VinhDP',1053,25,'Đổi từ phòng 508 sang phòng 403','2015-08-11',1,5),(6179,'VinhDP',1053,25,'Đổi từ phòng 508 sang phòng 403','2015-08-12',1,5),(6180,'VinhDP',1053,25,'Đổi từ phòng 508 sang phòng 403','2015-08-13',1,5),(6181,'SuTV',1053,25,'Đổi từ phòng 508 sang phòng 403','2015-08-06',1,6),(6182,'SuTV',1053,25,'Đổi từ phòng 508 sang phòng 403','2015-08-07',1,6),(6183,'SuTV',1053,25,'Đổi từ phòng 508 sang phòng 403','2015-08-10',1,6),(6184,'SuTV',1053,25,'Đổi từ phòng 508 sang phòng 403','2015-08-11',1,6),(6185,'SuTV',1053,25,'Đổi từ phòng 508 sang phòng 403','2015-08-12',1,6),(6186,'SuTV',1053,25,'Đổi từ phòng 508 sang phòng 403','2015-08-13',1,6),(6187,'TaiNT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-06',1,1),(6188,'TaiNT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-07',1,1),(6189,'TaiNT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-10',1,1),(6190,'TaiNT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-11',1,1),(6191,'TaiNT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-12',1,1),(6192,'TaiNT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-13',1,1),(6193,'TaiNT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-14',1,1),(6194,'TaiNT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-15',1,1),(6195,'KieuTA',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-06',1,2),(6196,'KieuTA',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-07',1,2),(6197,'KieuTA',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-11',1,2),(6198,'KieuTA',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-13',1,2),(6199,'KieuTA',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-14',1,2),(6200,'KieuTA',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-15',1,2),(6201,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-06',1,3),(6202,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-07',1,3),(6203,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-10',1,3),(6204,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-11',1,3),(6205,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-12',1,3),(6206,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-13',1,3),(6207,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-14',1,3),(6208,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-15',1,3),(6209,'CamDTH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-06',1,4),(6210,'CamDTH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-07',1,4),(6211,'CamDTH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-11',1,4),(6212,'CamDTH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-13',1,4),(6213,'CamDTH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-14',1,4),(6214,'CamDTH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-12',1,4),(6215,'CamDTH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-15',1,4),(6216,'TuDT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-06',1,5),(6217,'TuDT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-07',1,5),(6218,'TuDT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-10',1,5),(6219,'TuDT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-11',1,5),(6220,'TuDT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-12',1,5),(6221,'TuDT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-13',1,5),(6222,'TuDT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-14',1,5),(6223,'TuDT',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-15',1,5),(6224,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-06',1,6),(6225,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-07',1,6),(6226,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-10',1,6),(6227,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-11',1,6),(6228,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-12',1,6),(6229,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-13',1,6),(6230,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-14',1,6),(6231,'AnNDH',1056,25,'Đổi từ phòng 308 sang phòng 202','2015-08-15',1,6);
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
INSERT INTO `tblscheduleconfig` VALUES (1,1,'07:00:00','08:30:00'),(2,2,'08:45:00','10:15:00'),(3,3,'10:30:00','12:00:00'),(4,4,'12:30:00','14:00:00'),(5,5,'14:15:00','15:45:00'),(6,6,'16:00:00','17:30:00'),(7,7,'17:45:00','07:30:00');
/*!40000 ALTER TABLE `tblscheduleconfig` ENABLE KEYS */;
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
INSERT INTO `tbluser` VALUES ('admin',1,'123456',1),('AnhNT',3,'123456',1),('AnNDH',3,'123456',1),('BaoNQ',3,'123456',1),('BinhDTH',3,'123456',1),('CamDTH',3,'123456',1),('Cecile',3,'123456',1),('ChauLH',3,'123456',1),('ChungKTT',3,'123456',1),('Gerardo',3,'123456',1),('HiepNT',3,'123456',1),('HiepTT',3,'123456',1),('HoangNT',3,'123456',1),('HungLD',3,'123456',1),('HungNH',3,'123456',1),('hungtv',3,'123456',1),('HuongPTT',3,'123456',1),('KhanhKT',3,'123456',1),('KieuTA',3,'123456',1),('LienTTH',3,'123456',1),('LoiND',3,'123456',1),('NhuLTL',3,'123456',1),('PhuongLHK',3,'123456',1),('staff',2,'123456',1),('SuTV',3,'123456',1),('TaiNT',3,'123456',1),('teacher',3,'123456',1),('ThanhDT',3,'123456',1),('ThuyetDTM',3,'123456',1),('TriBD',3,'123456',1),('TuDT',3,'123456',1),('VanTTN',3,'123456',1),('VinhDP',3,'123456',1);
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
INSERT INTO `tbluserinfo` VALUES ('AnhNT','AnhNT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('AnNDH','AnNDH','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('BaoNQ','BaoNQ','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('BinhDTH','BinhDTH','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('CamDTH','CamDTH','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('Cecile','Cecile','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('ChauLH','ChauLH','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('ChungKTT','ChungKTT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('Gerardo','Gerardo','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('HiepNT','HiepNT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('HiepTT','HiepTT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('HoangNT','HoangNT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('HungLD','HungLD','+84962067505','2015-08-04 07:46:15.293000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',1438649177110),('HungNH','HungNH','+0962067505','2015-07-27 15:08:42.956000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('hungtv','hungtv','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('HuongPTT','HuongPTT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('KhanhKT','KhanhKT','+84962067505','2015-08-03 15:10:34.075000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',1438589555288),('KieuTA','KieuTA','+84962067505','2015-07-23 08:45:34.706000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('LienTTH','LienTTH','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('LoiND','LoiND','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('NhuLTL','NhuLTL','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('PhuongLHK','PhuongLHK','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('staff','Doan Nguyen','+841688167252','2015-07-21 09:08:26.547000','APA91bHxvLq2fIHYV9hH1WAWCb2IkrLQDeN47HKrAhaNesoLmpsbJVvDMK8g1TWVXeXtSgoZAn12N7wZr0SgXUcDy_wh5b-G-OR1E5tnNRb4vDaCjV7ClamsAowysqpeKd9akg_qhSK_j5Be-SXFXf-MaZr_FQo_gw',1438311850503),('SuTV','SuTV','+84962067505','2015-08-03 15:16:10.466000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',1438589773647),('TaiNT','TaiNT','+84962067505','2015-07-23 07:58:57.037000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('teacher','Minh Chi','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('ThanhDT','ThanhDT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('ThuyetDTM','ThuyetDTM','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('TriBD','TriBD','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('TuDT','TuDT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('VanTTN','VanTTN','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL),('VinhDP','VinhDP','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g',NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblusernotification`
--

LOCK TABLES `tblusernotification` WRITE;
/*!40000 ALTER TABLE `tblusernotification` DISABLE KEYS */;
INSERT INTO `tblusernotification` VALUES (2,2,'','staff');
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

-- Dump completed on 2015-08-06 21:26:18
