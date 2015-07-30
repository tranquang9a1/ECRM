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
  `RoomTypeId` int(11) DEFAULT NULL,
  `Name` varchar(50) NOT NULL,
  `CreateTime` datetime(6) NOT NULL,
  `IsDelete` tinyint(1) DEFAULT NULL,
  `UpdateTime` datetime(6) DEFAULT NULL,
  `IsAllInformation` tinyint(1) DEFAULT NULL,
  `DamagedLevel` int(11) DEFAULT NULL,
  `RoomTypeId2` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_tblClassroom_tblRoomType1` (`RoomTypeId`),
  KEY `FK_tblClassroom_tblRoomType2` (`RoomTypeId2`),
  KEY `FK1F2E435B2E33001` (`RoomTypeId2`),
  CONSTRAINT `FK1F2E435B2E33001` FOREIGN KEY (`RoomTypeId2`) REFERENCES `tblroomtype2` (`Id`),
  CONSTRAINT `FK_tblClassroom_tblRoomType1` FOREIGN KEY (`RoomTypeId`) REFERENCES `tblroomtype` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1075 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblclassroom`
--

LOCK TABLES `tblclassroom` WRITE;
/*!40000 ALTER TABLE `tblclassroom` DISABLE KEYS */;
INSERT INTO `tblclassroom` VALUES (1049,1016,'101','2015-07-01 12:45:34.000000',0,'2015-07-15 11:10:31.314000',1,0,3),(1050,1019,'213','2015-07-01 12:45:50.000000',0,NULL,1,0,3),(1051,1017,'310','2015-07-01 12:48:22.000000',0,'2015-07-25 17:02:22.706000',1,0,3),(1052,1019,'312','2015-07-01 12:48:43.000000',0,NULL,1,0,3),(1053,1016,'403','2015-07-01 12:49:00.000000',0,NULL,1,0,3),(1054,1017,'407','2015-07-01 12:49:19.000000',0,NULL,1,0,3),(1055,1017,'102','2015-07-13 13:18:50.869000',0,'2015-07-25 17:11:19.886000',1,0,3),(1056,1016,'202','2015-07-06 15:45:43.322000',0,NULL,1,0,3),(1057,1016,'203','2015-07-07 06:05:33.362000',0,NULL,1,0,3),(1058,1016,'103','2015-07-23 22:09:21.000000',0,NULL,1,0,3),(1059,1016,'113','2015-07-23 22:09:49.243000',0,NULL,1,0,3),(1060,1016,'115','2015-07-23 22:10:10.055000',0,NULL,1,0,3),(1061,1016,'209','2015-07-23 22:10:20.964000',0,NULL,1,0,3),(1062,1016,'306','2015-07-23 22:10:53.242000',0,NULL,1,0,3),(1063,1017,'308','2015-07-23 22:11:05.818000',0,NULL,1,0,3),(1064,1017,'307','2015-07-23 22:11:19.243000',0,NULL,1,0,3),(1065,1017,'313','2015-07-23 22:11:46.169000',0,NULL,1,0,3),(1066,1017,'410','2015-07-23 22:11:57.403000',0,NULL,1,0,3),(1067,1017,'405','2015-07-23 22:12:17.891000',0,NULL,1,0,3),(1068,1017,'401','2015-07-23 22:12:34.867000',0,NULL,1,0,3),(1069,1017,'412','2015-07-23 22:12:47.794000',0,NULL,1,0,3),(1070,1017,'414','2015-07-23 22:13:00.067000',0,NULL,1,0,3),(1071,1017,'504','2015-07-23 22:13:13.278000',0,NULL,1,0,3),(1072,1017,'508','2015-07-23 22:13:25.547000',0,NULL,1,0,3),(1073,1017,'234234','2015-07-25 16:12:31.376000',1,NULL,1,0,3),(1074,1018,'1','2015-07-26 13:03:53.521000',0,NULL,1,0,3);
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
) ENGINE=InnoDB AUTO_INCREMENT=462 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblequipment`
--

LOCK TABLES `tblequipment` WRITE;
/*!40000 ALTER TABLE `tblequipment` DISABLE KEYS */;
INSERT INTO `tblequipment` VALUES (211,1,1049,'[1]',1,0,'Toshiba','2131231231',0,3000),(212,3,1049,'[3]',1,0,'Hitachi','4564564645',0,0),(213,3,1049,'[3]',1,0,'Hitachi','8798798798',0,0),(214,2,1049,'[2]',1,0,'Samsung','8767678678',0,0),(215,1,1050,'[1]',1,2936,'Toshiba','7857858758',0,3000),(216,3,1050,'[3]',1,0,'Hitachi','7487484784',0,0),(217,3,1050,'[3]',1,0,'Hitachi','5494949959',0,0),(218,1,1051,'[1]',1,2915,'Toshiba','2166122126',0,3000),(219,3,1051,'[3]',1,0,'Hitachi','5435434343',0,0),(220,3,1055,'[3]',1,0,'Hitachi','5616566156',0,0),(221,2,1055,'[2]',1,0,'Samsung','6876876242',0,0),(222,1,1052,'[1]',1,2825,'Toshiba','2192192919',0,3000),(223,3,1052,'[3]',1,0,'Hitachi','2818218287',0,0),(224,3,1052,'[3]',1,0,'Hitachi','1731737137',0,0),(225,1,1053,'[1]',1,2914.5,'Toshiba','9349493943',0,3000),(226,3,1053,'[3]',1,0,'Hitachi','6346437437',0,0),(227,3,1053,'[3]',1,0,'Hitachi','2182181288',0,0),(228,2,1053,'[2]',1,0,'Samsung','3163163637',0,0),(229,1,1054,'[1]',1,2914.5,'Toshiba','3783735731',0,3000),(230,3,1054,'[3]',1,0,'Hitachi','0212012334',0,0),(231,3,1054,'[3]',1,0,'Hitachi','5064506504',0,0),(232,2,1054,'[2]',1,0,'Samsung','3700738078',0,0),(261,1,1055,'[1]',1,3000,'Sony','98392385123',0,3000),(262,3,NULL,'[3]',1,0,'DAIKIN','1239382454',0,0),(263,3,NULL,'[3]',1,0,'DAIKIN','1231924872',0,0),(264,2,1051,'[2]',1,0,'SAMSUNG','3423423545',0,0),(280,1,1056,'[1]',1,2998.5,'SamSung','97238460123',0,3000),(281,3,1056,'[3]',1,0,'DAIKIN','37412030822',0,0),(282,3,1056,'[3]',1,0,'DAIKIN','34597233474',0,0),(283,2,1056,'[2]',1,0,'SONY','32445701283',0,0),(289,1,1057,'[1]',1,3000,'Sony','30487345123',0,3000),(290,3,1057,'[3]',1,0,'DAIKIN','97980982131',0,0),(291,3,1057,'[3]',1,0,'DAIKIN','12309845666',0,0),(292,2,1057,'[2]',1,0,'LG','97454802938',0,0),(334,1,1058,'[1]',1,3000,'PROJECTOR','23325314234',0,3000),(335,3,1058,'[3]',1,0,'AIR','32545653756',0,0),(336,3,1058,'[3]',1,0,'AIR','23434241231',0,0),(337,2,1058,'[2]',1,0,'TIVI','43524624632',0,0),(338,1,1059,'[1]',1,2997,'PROJECTOR','34546345654',0,3000),(339,3,1059,'[3]',1,0,'AIR','12312334345',0,0),(340,3,1059,'[3]',1,0,'AIR','5464879785',0,0),(341,2,1059,'[2]',1,0,'TIVI','23434645654',0,0),(342,1,1060,'[1]',1,2994,'PROJECTOR','35463Q2343',0,3000),(343,3,1060,'[3]',1,0,'AIR','45635546546',0,0),(344,3,1060,'[3]',1,0,'AIR','1231234556',0,0),(345,2,1060,'[2]',1,0,'TIVI','2341231436',0,0),(346,1,1061,'[1]',1,2990,'PROJECTOR','4575687967',0,3000),(347,3,1061,'[3]',1,0,'AIR','1231232353',0,0),(348,3,1061,'[3]',1,0,'AIR','89676574535',0,0),(349,2,1061,'[2]',1,0,'TIVI','5476583452',0,0),(350,1,1062,'[1]',1,2990,'PROJECTOR','2343546356',0,3000),(351,3,1062,'[3]',1,0,'AIR','234346765722',0,0),(352,3,1062,'[3]',1,0,'AIR','32434673474',0,0),(353,2,1062,'[2]',1,0,'TIVI','76867956754',0,0),(354,1,1063,'[1]',1,2992,'PROJECTOR','3224235457',0,3000),(355,3,1063,'[3]',1,0,'AIR','45675683234',0,0),(356,3,1063,'[3]',1,0,'AIR','3454573452',0,0),(357,2,1063,'[2]',1,0,'TIVI','2354657658',0,0),(358,1,1064,'[1]',1,2996,'PROJECTOR','5685435234',0,3000),(359,3,1064,'[3]',1,0,'AIR','457686557645',0,0),(360,3,1064,'[3]',1,0,'AIR','576584534',0,0),(361,2,1064,'[2]',1,0,'TIVI','464578685456',0,0),(362,1,1065,'[1]',1,2993,'PROJECTOR','23424534646',0,3000),(363,3,1065,'[3]',1,0,'AIR','345457443523',0,0),(364,3,1065,'[3]',1,0,'AIR','234264563345',0,0),(365,2,1065,'[2]',1,0,'TIVI','2343457567324',0,0),(366,1,1066,'[1]',1,2994,'PROJECTOR','2345346435',0,3000),(367,3,1066,'[3]',1,0,'AIR','2353465456',0,0),(368,3,1066,'[3]',1,0,'AIR','678678565',0,0),(369,2,1066,'[2]',1,0,'TIVI','78089068755',0,0),(370,1,1067,'[1]',1,2994,'PROJECTOR','657578679',0,3000),(371,3,1067,'[3]',1,0,'AIR','7890786875',0,0),(372,3,1067,'[3]',1,0,'AIR','566456878',0,0),(373,2,1067,'[2]',1,0,'TIVI','45768978976',0,0),(374,1,1068,'[1]',1,2994,'PROJECTOR','57686979856',0,3000),(375,3,1068,'[3]',1,0,'AIR','53454356744',0,0),(376,3,1068,'[3]',1,0,'AIR','34423567546',0,0),(377,2,1068,'[2]',1,0,'TIVI','23454576433',0,0),(378,1,1069,'[1]',1,2994,'PROJECTOR','7457667867',0,3000),(379,3,1069,'[3]',1,0,'AIR','23421454545',0,0),(380,3,1069,'[3]',1,0,'AIR','1242565687',0,0),(381,2,1069,'[2]',1,0,'TIVI','58564643457',0,0),(382,1,1070,'[1]',1,2992,'PROJECTOR','78796565534',0,3000),(383,3,1070,'[3]',1,0,'AIR','35346567885',0,0),(384,3,1070,'[3]',1,0,'AIR','23434657736',0,0),(385,2,1070,'[2]',1,0,'TIVI','21434575464',0,0),(386,1,1071,'[1]',1,2992,'PROJECTOR','89968575456',0,3000),(387,3,1071,'[3]',1,0,'AIR','4352346577',0,0),(388,3,1071,'[3]',1,0,'AIR','2456457567',0,0),(389,2,1071,'[2]',1,0,'TIVI','79867465645',0,0),(390,1,1072,'[1]',1,2994,'PROJECTOR','457568688676',0,3000),(391,3,1072,'[3]',1,0,'AIR','3454575674',0,0),(392,3,1072,'[3]',1,0,'AIR','2454675568',0,0),(393,2,1072,'[2]',1,0,'TIVI','45688796575',0,0),(403,4,1069,'[4]',1,NULL,NULL,NULL,0,NULL),(404,6,1069,'[6]',1,NULL,NULL,NULL,0,NULL),(405,1,1062,'[1]',1,NULL,NULL,NULL,0,3000),(406,3,1062,'[3]',1,NULL,NULL,NULL,0,NULL),(407,1,1063,'[1]',1,NULL,NULL,NULL,0,3000),(408,4,1063,'[4]',1,NULL,NULL,NULL,0,NULL),(409,6,1063,'[6]',1,NULL,NULL,NULL,0,NULL),(410,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(411,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(412,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(413,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(415,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(416,3,NULL,'[3]',1,0,NULL,NULL,0,0),(417,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(418,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(419,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(420,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(421,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(422,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(423,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(424,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(425,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(426,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(427,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(428,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(429,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(430,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(431,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(432,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(433,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(434,1,NULL,'[1]',1,3000,NULL,NULL,0,3000),(435,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(436,3,NULL,'[3]',1,NULL,NULL,NULL,0,NULL),(437,2,NULL,'[2]',1,NULL,NULL,NULL,0,NULL),(438,1,NULL,NULL,1,NULL,'Projector','22222222222',0,3000),(439,1,1074,'[1]',1,3000,NULL,NULL,0,3000),(440,3,1074,'[3]',1,NULL,NULL,NULL,0,NULL),(441,3,1074,'[3]',1,NULL,NULL,NULL,0,NULL),(442,2,1074,'[2]',1,NULL,NULL,NULL,0,NULL),(443,1,1059,'[1]',1,NULL,NULL,NULL,0,3000),(444,3,1059,'[3]',1,NULL,NULL,NULL,0,NULL),(445,1,1049,'[1]',1,NULL,NULL,NULL,0,3000),(446,6,1049,'[6]',1,NULL,NULL,NULL,0,NULL),(447,1,1052,'[1]',1,NULL,NULL,NULL,0,3000),(448,4,1052,'[4]',1,NULL,NULL,NULL,0,NULL),(449,1,1053,'[1]',1,NULL,NULL,NULL,0,3000),(450,4,1053,'[4]',1,NULL,NULL,NULL,0,NULL),(451,1,1054,'[1]',1,NULL,NULL,NULL,0,3000),(452,4,1054,'[4]',1,NULL,NULL,NULL,0,NULL),(453,1,1050,'[1]',1,NULL,NULL,NULL,0,3000),(454,4,1050,'[4]',1,NULL,NULL,NULL,0,NULL),(455,1,1055,'[1]',1,NULL,NULL,NULL,0,3000),(456,4,1055,'[4]',1,NULL,NULL,NULL,0,NULL),(457,1,1056,'[1]',1,NULL,NULL,NULL,0,3000),(458,4,1056,'[4]',1,NULL,NULL,NULL,0,NULL),(459,1,1057,'[1]',1,NULL,NULL,NULL,0,3000),(460,4,1057,'[4]',1,NULL,NULL,NULL,0,NULL),(461,7,1051,'[1,1]',1,NULL,NULL,NULL,0,NULL);
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
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblequipmentcategory`
--

LOCK TABLES `tblequipmentcategory` WRITE;
/*!40000 ALTER TABLE `tblequipmentcategory` DISABLE KEYS */;
INSERT INTO `tblequipmentcategory` VALUES (1,'Máy chiếu',3000,1,'projector.png',0),(2,'Tivi',0,1,'widescreentv.png',0),(3,'Máy lạnh',6000,1,'air512.png',0),(4,'Máy quạt',0,0,'fan512.png',0),(5,'Loa',0,0,'003386glossyblackiconmediamusicspeaker.png',0),(6,'Bóng đèn',0,0,'lightbulb7icon270x250.png',0),(7,'Bàn',0,0,'HouseholdTableicon.png',0),(8,'Ghế',0,0,'chairoffice512.png',0),(12,'Empty',0,1,'empty_set_u2205_icon_256x256.png',0);
/*!40000 ALTER TABLE `tblequipmentcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblequipmentquantity`
--

DROP TABLE IF EXISTS `tblequipmentquantity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblequipmentquantity` (
  `RoomTypeId` int(11) NOT NULL DEFAULT '0',
  `EquipmentCategoryId` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`RoomTypeId`,`EquipmentCategoryId`),
  KEY `FK_tblequipmentquantity_tblequipmentcategory` (`EquipmentCategoryId`),
  KEY `FK_tblequipmentquantity_tblroomtype2` (`RoomTypeId`),
  KEY `FKEA4EA83B67FE6D5B` (`EquipmentCategoryId`),
  KEY `FKEA4EA83B975A2EAF` (`RoomTypeId`),
  CONSTRAINT `FKEA4EA83B67FE6D5B` FOREIGN KEY (`EquipmentCategoryId`) REFERENCES `tblequipmentcategory` (`Id`),
  CONSTRAINT `FKEA4EA83B975A2EAF` FOREIGN KEY (`RoomTypeId`) REFERENCES `tblroomtype2` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblequipmentquantity`
--

LOCK TABLES `tblequipmentquantity` WRITE;
/*!40000 ALTER TABLE `tblequipmentquantity` DISABLE KEYS */;
INSERT INTO `tblequipmentquantity` VALUES (3,1,1,3),(3,2,1,1),(3,3,2,3),(3,4,1,1),(3,5,1,1),(3,6,1,3),(4,1,1,3),(4,2,0,1),(4,3,2,3),(4,4,1,1),(4,5,1,1),(4,6,1,3);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblreport`
--

LOCK TABLES `tblreport` WRITE;
/*!40000 ALTER TABLE `tblreport` DISABLE KEYS */;
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
  `Name` varchar(100) NOT NULL,
  `Slots` int(11) NOT NULL,
  `VerticalRows` int(11) NOT NULL,
  `HorizontalRows` varchar(50) NOT NULL,
  `NumberOfSlotsEachHRows` varchar(50) NOT NULL,
  `Projector` int(11) NOT NULL,
  `AirConditioning` int(11) NOT NULL,
  `Bulb` int(11) NOT NULL,
  `Fan` int(11) NOT NULL,
  `Speaker` int(11) NOT NULL,
  `Television` int(11) NOT NULL,
  `CreateTime` datetime(6) NOT NULL,
  `IsDelete` tinyint(1) DEFAULT NULL,
  `UpdateTime` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1024 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblroomtype`
--

LOCK TABLES `tblroomtype` WRITE;
/*!40000 ALTER TABLE `tblroomtype` DISABLE KEYS */;
INSERT INTO `tblroomtype` VALUES (1015,'Loại 3 dãy',30,3,'5-5-5','2-2-2',1,0,1,1,1,1,'2015-06-10 00:13:39.000000',0,NULL),(1016,'Loại 30 chỗ',30,2,'5-5','3-3',1,2,1,0,0,1,'2015-06-10 08:10:18.000000',0,NULL),(1017,'Loại 25 chỗ',25,2,'5-5','3-2',1,2,1,2,0,1,'2015-06-13 12:14:04.000000',0,NULL),(1018,'Loại 40 chỗ',40,2,'5-5','4-4',1,2,1,4,1,1,'2015-06-14 20:42:56.000000',0,NULL),(1019,'Loại 20 chỗ',20,2,'5-5','2-2',1,2,1,0,1,0,'2015-06-15 15:39:25.000000',0,NULL),(1020,'Logo',30,2,'5-5','3-3',1,2,1,2,1,1,'2015-07-13 13:18:22.686000',0,NULL),(1021,'Loại 25 chỗ',1,1,'1','1',0,0,1,0,0,0,'2015-07-15 11:32:52.139000',0,NULL),(1022,'test',1,1,'1','1',0,1,1,1,0,0,'2015-07-23 22:43:48.087000',0,NULL),(1023,'ads',1,1,'1','1',0,0,1,0,0,0,'2015-07-25 15:52:27.997000',1,NULL);
/*!40000 ALTER TABLE `tblroomtype` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblroomtype2`
--

LOCK TABLES `tblroomtype2` WRITE;
/*!40000 ALTER TABLE `tblroomtype2` DISABLE KEYS */;
INSERT INTO `tblroomtype2` VALUES (3,'Loại 25 chỗ',25,2,'5-5','3-2','2015-07-29 07:31:03',0,NULL),(4,'Loại 30 chỗ',30,2,'5-5','3-3','2015-07-29 07:35:48',0,NULL);
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
  `TimeFrom` time(6) NOT NULL,
  `Slots` int(11) NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=5464 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblschedule`
--

LOCK TABLES `tblschedule` WRITE;
/*!40000 ALTER TABLE `tblschedule` DISABLE KEYS */;
INSERT INTO `tblschedule` VALUES (5151,'PhuongLHK',1049,30,'Đổi sang phòng 103','07:00:00.000000',1,'2015-07-28',0,1),(5152,'PhuongLHK',1049,30,NULL,'07:00:00.000000',1,'2015-07-30',1,1),(5153,'PhuongLHK',1049,30,'Đổi sang phòng 103','08:45:00.000000',1,'2015-07-28',0,2),(5154,'PhuongLHK',1049,30,NULL,'08:45:00.000000',1,'2015-07-30',1,2),(5155,'HungNH',1049,30,'Đổi sang phòng 103','12:30:00.000000',1,'2015-07-28',0,4),(5156,'HungNH',1049,30,NULL,'12:30:00.000000',1,'2015-07-30',1,4),(5157,'HungNH',1049,30,'Đổi sang phòng 115','14:15:00.000000',1,'2015-07-28',0,5),(5158,'HungNH',1049,30,NULL,'14:15:00.000000',1,'2015-07-30',1,5),(5159,'HungLD',1058,30,NULL,'14:15:00.000000',1,'2015-07-28',1,5),(5160,'HungLD',1058,30,NULL,'14:15:00.000000',1,'2015-07-30',1,5),(5161,'HoangNT',1059,30,NULL,'08:45:00.000000',1,'2015-07-27',1,2),(5162,'HoangNT',1059,30,NULL,'08:45:00.000000',1,'2015-07-28',1,2),(5163,'HoangNT',1059,30,NULL,'08:45:00.000000',1,'2015-07-29',1,2),(5164,'HoangNT',1059,30,NULL,'08:45:00.000000',1,'2015-07-30',1,2),(5165,'HoangNT',1059,30,NULL,'08:45:00.000000',1,'2015-07-31',1,2),(5166,'TaiNT',1059,30,NULL,'14:15:00.000000',1,'2015-07-28',1,5),(5167,'TaiNT',1059,30,NULL,'14:15:00.000000',1,'2015-07-29',1,5),(5168,'TaiNT',1059,30,NULL,'14:15:00.000000',1,'2015-07-30',1,5),(5169,'TaiNT',1059,30,NULL,'14:15:00.000000',1,'2015-07-31',1,5),(5170,'HungLD',1060,30,NULL,'07:00:00.000000',1,'2015-07-28',1,1),(5171,'HungLD',1060,30,NULL,'07:00:00.000000',1,'2015-07-30',1,1),(5172,'HungLD',1060,30,NULL,'07:00:00.000000',1,'2015-07-31',1,1),(5173,'HiepNT',1060,30,NULL,'08:45:00.000000',1,'2015-07-27',1,2),(5174,'HiepNT',1060,30,NULL,'08:45:00.000000',1,'2015-07-28',1,2),(5175,'HiepNT',1060,30,NULL,'08:45:00.000000',1,'2015-07-29',1,2),(5176,'HiepNT',1060,30,NULL,'08:45:00.000000',1,'2015-07-30',1,2),(5177,'HiepNT',1060,30,NULL,'08:45:00.000000',1,'2015-07-31',1,2),(5178,'ChungKTT',1060,30,NULL,'10:30:00.000000',1,'2015-07-27',1,3),(5179,'ChungKTT',1060,30,NULL,'10:30:00.000000',1,'2015-07-28',1,3),(5180,'ChungKTT',1060,30,NULL,'10:30:00.000000',1,'2015-07-29',1,3),(5181,'ChungKTT',1060,30,NULL,'10:30:00.000000',1,'2015-07-30',1,3),(5182,'ChungKTT',1060,30,NULL,'10:30:00.000000',1,'2015-07-31',1,3),(5183,'KhanhKT',1060,30,NULL,'12:30:00.000000',1,'2015-07-27',1,4),(5184,'KhanhKT',1060,30,NULL,'12:30:00.000000',1,'2015-07-31',1,4),(5185,'KhanhKT',1060,30,NULL,'14:15:00.000000',1,'2015-07-27',1,5),(5186,'KhanhKT',1060,30,NULL,'14:15:00.000000',1,'2015-07-31',1,5),(5187,'LienTTH',1061,30,NULL,'08:45:00.000000',1,'2015-07-27',1,2),(5188,'HuongPTT',1061,30,NULL,'08:45:00.000000',1,'2015-07-28',1,2),(5189,'LienTTH',1061,30,NULL,'08:45:00.000000',1,'2015-07-29',1,2),(5190,'HuongPTT',1061,30,NULL,'08:45:00.000000',1,'2015-07-30',1,2),(5191,'LienTTH',1061,30,NULL,'08:45:00.000000',1,'2015-07-31',1,2),(5192,'LienTTH',1061,30,NULL,'10:30:00.000000',1,'2015-07-27',1,3),(5193,'HuongPTT',1061,30,NULL,'10:30:00.000000',1,'2015-07-28',1,3),(5194,'LienTTH',1061,30,NULL,'10:30:00.000000',1,'2015-07-29',1,3),(5195,'HuongPTT',1061,30,NULL,'10:30:00.000000',1,'2015-07-30',1,3),(5196,'LienTTH',1061,30,NULL,'10:30:00.000000',1,'2015-07-31',1,3),(5197,'AnNDH',1061,30,NULL,'12:30:00.000000',1,'2015-07-27',1,4),(5198,'AnNDH',1061,30,NULL,'12:30:00.000000',1,'2015-07-29',1,4),(5199,'SuTV',1061,30,NULL,'14:15:00.000000',1,'2015-07-27',1,5),(5200,'SuTV',1061,30,NULL,'14:15:00.000000',1,'2015-07-28',1,5),(5201,'SuTV',1061,30,NULL,'14:15:00.000000',1,'2015-07-29',1,5),(5202,'SuTV',1061,30,NULL,'14:15:00.000000',1,'2015-07-30',1,5),(5203,'SuTV',1061,30,NULL,'14:15:00.000000',1,'2015-07-31',1,5),(5204,'KieuTA',1061,30,NULL,'16:00:00.000000',1,'2015-07-27',1,6),(5205,'KieuTA',1061,30,NULL,'16:00:00.000000',1,'2015-07-28',1,6),(5206,'KieuTA',1061,30,NULL,'16:00:00.000000',1,'2015-07-29',1,6),(5207,'KieuTA',1061,30,NULL,'16:00:00.000000',1,'2015-07-30',1,6),(5208,'KieuTA',1061,30,NULL,'16:00:00.000000',1,'2015-07-31',1,6),(5209,'HiepTT',1050,20,NULL,'07:00:00.000000',1,'2015-07-27',1,1),(5210,'HiepTT',1050,20,NULL,'07:00:00.000000',1,'2015-07-28',1,1),(5211,'HiepTT',1050,20,NULL,'07:00:00.000000',1,'2015-07-29',1,1),(5212,'HiepTT',1050,20,NULL,'07:00:00.000000',1,'2015-07-30',1,1),(5213,'HiepTT',1050,20,NULL,'07:00:00.000000',1,'2015-07-31',1,1),(5214,'HungLD',1050,20,NULL,'08:45:00.000000',1,'2015-07-27',1,2),(5215,'HungLD',1050,20,NULL,'08:45:00.000000',1,'2015-07-28',1,2),(5216,'HungLD',1050,20,NULL,'08:45:00.000000',1,'2015-07-29',1,2),(5217,'HungLD',1050,20,NULL,'08:45:00.000000',1,'2015-07-30',1,2),(5218,'HungLD',1050,20,NULL,'08:45:00.000000',1,'2015-07-31',1,2),(5219,'KieuTA',1050,20,NULL,'10:30:00.000000',1,'2015-07-28',1,3),(5220,'KieuTA',1050,20,NULL,'10:30:00.000000',1,'2015-07-30',1,3),(5221,'KieuTA',1050,20,NULL,'10:30:00.000000',1,'2015-07-31',1,3),(5222,'AnNDH',1062,30,NULL,'07:00:00.000000',1,'2015-07-27',1,1),(5223,'AnNDH',1062,30,NULL,'07:00:00.000000',1,'2015-07-28',1,1),(5224,'AnNDH',1062,30,NULL,'07:00:00.000000',1,'2015-07-29',1,1),(5225,'AnNDH',1062,30,NULL,'07:00:00.000000',1,'2015-07-30',1,1),(5226,'AnNDH',1062,30,NULL,'07:00:00.000000',1,'2015-07-31',1,1),(5227,'KieuTA',1062,30,NULL,'08:45:00.000000',1,'2015-07-27',1,2),(5228,'KieuTA',1062,30,NULL,'08:45:00.000000',1,'2015-07-29',1,2),(5229,'TuDT',1062,30,NULL,'10:30:00.000000',1,'2015-07-27',1,3),(5230,'TuDT',1062,30,NULL,'10:30:00.000000',1,'2015-07-28',1,3),(5231,'TuDT',1062,30,NULL,'10:30:00.000000',1,'2015-07-29',1,3),(5232,'TuDT',1062,30,NULL,'10:30:00.000000',1,'2015-07-30',1,3),(5233,'TuDT',1062,30,NULL,'10:30:00.000000',1,'2015-07-31',1,3),(5234,'HiepNT',1062,30,NULL,'12:30:00.000000',1,'2015-07-28',1,4),(5235,'HiepNT',1062,30,NULL,'12:30:00.000000',1,'2015-07-30',1,4),(5236,'HiepNT',1062,30,NULL,'12:30:00.000000',1,'2015-07-31',1,4),(5237,'BinhDTH',1062,30,NULL,'14:15:00.000000',1,'2015-07-27',1,5),(5238,'BinhDTH',1062,30,NULL,'14:15:00.000000',1,'2015-07-28',1,5),(5239,'BinhDTH',1062,30,NULL,'14:15:00.000000',1,'2015-07-29',1,5),(5240,'BinhDTH',1062,30,NULL,'14:15:00.000000',1,'2015-07-30',1,5),(5241,'BinhDTH',1062,30,NULL,'14:15:00.000000',1,'2015-07-31',1,5),(5242,'KhanhKT',1062,30,NULL,'16:00:00.000000',1,'2015-07-27',1,6),(5243,'KhanhKT',1062,30,NULL,'16:00:00.000000',1,'2015-07-28',1,6),(5244,'KhanhKT',1062,30,NULL,'16:00:00.000000',1,'2015-07-29',1,6),(5245,'KhanhKT',1062,30,NULL,'16:00:00.000000',1,'2015-07-30',1,6),(5246,'KhanhKT',1062,30,NULL,'16:00:00.000000',1,'2015-07-31',1,6),(5247,'TaiNT',1063,25,NULL,'07:00:00.000000',1,'2015-07-27',1,1),(5248,'TaiNT',1063,25,NULL,'07:00:00.000000',1,'2015-07-28',1,1),(5249,'TaiNT',1063,25,NULL,'07:00:00.000000',1,'2015-07-29',1,1),(5250,'TaiNT',1063,25,NULL,'07:00:00.000000',1,'2015-07-30',1,1),(5251,'TaiNT',1063,25,NULL,'07:00:00.000000',1,'2015-07-31',1,1),(5252,'KieuTA',1063,25,NULL,'08:45:00.000000',1,'2015-07-28',1,2),(5253,'KieuTA',1063,25,NULL,'08:45:00.000000',1,'2015-07-30',1,2),(5254,'KieuTA',1063,25,NULL,'08:45:00.000000',1,'2015-07-31',1,2),(5255,'AnNDH',1063,25,NULL,'10:30:00.000000',1,'2015-07-27',1,3),(5256,'AnNDH',1063,25,NULL,'10:30:00.000000',1,'2015-07-28',1,3),(5257,'AnNDH',1063,25,NULL,'10:30:00.000000',1,'2015-07-29',1,3),(5258,'AnNDH',1063,25,NULL,'10:30:00.000000',1,'2015-07-30',1,3),(5259,'AnNDH',1063,25,NULL,'10:30:00.000000',1,'2015-07-31',1,3),(5260,'CamDTH',1063,25,NULL,'12:30:00.000000',1,'2015-07-28',1,4),(5261,'CamDTH',1063,25,NULL,'12:30:00.000000',1,'2015-07-30',1,4),(5262,'CamDTH',1063,25,NULL,'12:30:00.000000',1,'2015-07-31',1,4),(5263,'TuDT',1063,25,NULL,'14:15:00.000000',1,'2015-07-27',1,5),(5264,'TuDT',1063,25,NULL,'14:15:00.000000',1,'2015-07-28',1,5),(5265,'TuDT',1063,25,NULL,'14:15:00.000000',1,'2015-07-29',1,5),(5266,'TuDT',1063,25,NULL,'14:15:00.000000',1,'2015-07-30',1,5),(5267,'TuDT',1063,25,NULL,'14:15:00.000000',1,'2015-07-31',1,5),(5268,'AnNDH',1063,25,NULL,'16:00:00.000000',1,'2015-07-27',1,6),(5269,'AnNDH',1063,25,NULL,'16:00:00.000000',1,'2015-07-28',1,6),(5270,'AnNDH',1063,25,NULL,'16:00:00.000000',1,'2015-07-29',1,6),(5271,'AnNDH',1063,25,NULL,'16:00:00.000000',1,'2015-07-30',1,6),(5272,'AnNDH',1063,25,NULL,'16:00:00.000000',1,'2015-07-31',1,6),(5273,'PhuongLHK',1064,25,NULL,'12:30:00.000000',1,'2015-07-27',1,4),(5274,'PhuongLHK',1064,25,NULL,'12:30:00.000000',1,'2015-07-28',1,4),(5275,'PhuongLHK',1064,25,NULL,'12:30:00.000000',1,'2015-07-29',1,4),(5276,'PhuongLHK',1064,25,NULL,'12:30:00.000000',1,'2015-07-30',1,4),(5277,'PhuongLHK',1064,25,NULL,'12:30:00.000000',1,'2015-07-31',1,4),(5278,'KieuTA',1051,25,NULL,'07:00:00.000000',1,'2015-07-27',1,1),(5279,'KieuTA',1051,25,NULL,'07:00:00.000000',1,'2015-07-29',1,1),(5280,'HungNH',1051,25,NULL,'08:45:00.000000',1,'2015-07-27',1,2),(5281,'HungNH',1051,25,NULL,'08:45:00.000000',1,'2015-07-28',1,2),(5282,'HungNH',1051,25,NULL,'08:45:00.000000',1,'2015-07-29',1,2),(5283,'HungNH',1051,25,NULL,'08:45:00.000000',1,'2015-07-30',1,2),(5284,'HungNH',1051,25,NULL,'08:45:00.000000',1,'2015-07-31',1,2),(5285,'ThuyetDTM',1051,25,NULL,'10:30:00.000000',1,'2015-07-27',1,3),(5286,'ThuyetDTM',1051,25,NULL,'10:30:00.000000',1,'2015-07-28',1,3),(5287,'ThuyetDTM',1051,25,NULL,'10:30:00.000000',1,'2015-07-29',1,3),(5288,'ThuyetDTM',1051,25,NULL,'10:30:00.000000',1,'2015-07-30',1,3),(5289,'ThuyetDTM',1051,25,NULL,'10:30:00.000000',1,'2015-07-31',1,3),(5290,'Cecile',1051,25,NULL,'12:30:00.000000',1,'2015-07-27',1,4),(5291,'Cecile',1051,25,NULL,'12:30:00.000000',1,'2015-07-28',1,4),(5292,'Cecile',1051,25,NULL,'12:30:00.000000',1,'2015-07-29',1,4),(5293,'Cecile',1051,25,NULL,'12:30:00.000000',1,'2015-07-30',1,4),(5294,'Cecile',1051,25,NULL,'12:30:00.000000',1,'2015-07-31',1,4),(5295,'CamDTH',1051,25,NULL,'14:15:00.000000',1,'2015-07-27',1,5),(5296,'CamDTH',1051,25,NULL,'14:15:00.000000',1,'2015-07-29',1,5),(5297,'HungNH',1051,25,NULL,'16:00:00.000000',1,'2015-07-27',1,6),(5298,'HungNH',1051,25,NULL,'16:00:00.000000',1,'2015-07-28',1,6),(5299,'HungNH',1051,25,NULL,'16:00:00.000000',1,'2015-07-29',1,6),(5300,'HungNH',1051,25,NULL,'16:00:00.000000',1,'2015-07-30',1,6),(5301,'HungNH',1051,25,NULL,'16:00:00.000000',1,'2015-07-31',1,6),(5302,'HungLD',1052,20,NULL,'12:30:00.000000',1,'2015-07-27',1,4),(5303,'HungLD',1052,20,NULL,'12:30:00.000000',1,'2015-07-28',1,4),(5304,'HungLD',1052,20,NULL,'12:30:00.000000',1,'2015-07-29',1,4),(5305,'HungLD',1052,20,NULL,'12:30:00.000000',1,'2015-07-30',1,4),(5306,'HungLD',1052,20,NULL,'12:30:00.000000',1,'2015-07-31',1,4),(5307,'CamDTH',1052,20,NULL,'14:15:00.000000',1,'2015-07-28',1,5),(5308,'CamDTH',1052,20,NULL,'14:15:00.000000',1,'2015-07-30',1,5),(5309,'CamDTH',1052,20,NULL,'14:15:00.000000',1,'2015-07-31',1,5),(5310,'TaiNT',1052,20,NULL,'16:00:00.000000',1,'2015-07-27',1,6),(5311,'TaiNT',1052,20,NULL,'16:00:00.000000',1,'2015-07-28',1,6),(5312,'TaiNT',1052,20,NULL,'16:00:00.000000',1,'2015-07-29',1,6),(5313,'TaiNT',1052,20,NULL,'16:00:00.000000',1,'2015-07-30',1,6),(5314,'TaiNT',1052,20,NULL,'16:00:00.000000',1,'2015-07-31',1,6),(5315,'ThanhDT',1065,25,NULL,'12:30:00.000000',1,'2015-07-27',1,4),(5316,'ThanhDT',1065,25,NULL,'12:30:00.000000',1,'2015-07-28',1,4),(5317,'ThanhDT',1065,25,NULL,'12:30:00.000000',1,'2015-07-29',1,4),(5318,'ThanhDT',1065,25,NULL,'12:30:00.000000',1,'2015-07-30',1,4),(5319,'ThanhDT',1065,25,NULL,'12:30:00.000000',1,'2015-07-31',1,4),(5320,'AnhNT',1065,25,NULL,'14:15:00.000000',1,'2015-07-27',1,5),(5321,'AnhNT',1065,25,NULL,'14:15:00.000000',1,'2015-07-28',1,5),(5322,'AnhNT',1065,25,NULL,'14:15:00.000000',1,'2015-07-29',1,5),(5323,'AnhNT',1065,25,NULL,'14:15:00.000000',1,'2015-07-30',1,5),(5324,'AnhNT',1065,25,NULL,'14:15:00.000000',1,'2015-07-31',1,5),(5325,'PhuongLHK',1065,25,NULL,'16:00:00.000000',1,'2015-07-28',1,6),(5326,'PhuongLHK',1065,25,NULL,'16:00:00.000000',1,'2015-07-30',1,6),(5327,'PhuongLHK',1065,25,NULL,'16:00:00.000000',1,'2015-07-31',1,6),(5328,'TriBD',1068,25,NULL,'07:00:00.000000',1,'2015-07-28',1,1),(5329,'TriBD',1068,25,NULL,'07:00:00.000000',1,'2015-07-30',1,1),(5330,'TriBD',1068,25,NULL,'08:45:00.000000',1,'2015-07-28',1,2),(5331,'TriBD',1068,25,NULL,'08:45:00.000000',1,'2015-07-30',1,2),(5332,'LoiND',1068,25,NULL,'12:30:00.000000',1,'2015-07-27',1,4),(5333,'LoiND',1068,25,NULL,'12:30:00.000000',1,'2015-07-28',1,4),(5334,'LoiND',1068,25,NULL,'12:30:00.000000',1,'2015-07-29',1,4),(5335,'LoiND',1068,25,NULL,'12:30:00.000000',1,'2015-07-30',1,4),(5336,'LoiND',1068,25,NULL,'12:30:00.000000',1,'2015-07-31',1,4),(5337,'ChungKTT',1068,25,NULL,'14:15:00.000000',1,'2015-07-27',1,5),(5338,'ChungKTT',1068,25,NULL,'14:15:00.000000',1,'2015-07-28',1,5),(5339,'ChungKTT',1068,25,NULL,'14:15:00.000000',1,'2015-07-29',1,5),(5340,'ChungKTT',1068,25,NULL,'14:15:00.000000',1,'2015-07-30',1,5),(5341,'ChungKTT',1068,25,NULL,'14:15:00.000000',1,'2015-07-31',1,5),(5342,'PhuongLHK',1068,25,NULL,'16:00:00.000000',1,'2015-07-27',1,6),(5343,'PhuongLHK',1068,25,NULL,'16:00:00.000000',1,'2015-07-29',1,6),(5344,'ChauLH',1067,25,NULL,'07:00:00.000000',1,'2015-07-27',1,1),(5345,'ChauLH',1067,25,NULL,'07:00:00.000000',1,'2015-07-28',1,1),(5346,'ChauLH',1067,25,NULL,'07:00:00.000000',1,'2015-07-29',1,1),(5347,'ChauLH',1067,25,NULL,'07:00:00.000000',1,'2015-07-30',1,1),(5348,'ChauLH',1067,25,NULL,'07:00:00.000000',1,'2015-07-31',1,1),(5349,'PhuongLHK',1067,25,NULL,'08:45:00.000000',1,'2015-07-27',1,2),(5350,'PhuongLHK',1067,25,NULL,'08:45:00.000000',1,'2015-07-29',1,2),(5351,'HiepNT',1067,25,NULL,'10:30:00.000000',1,'2015-07-27',1,3),(5352,'HiepNT',1067,25,NULL,'10:30:00.000000',1,'2015-07-28',1,3),(5353,'HiepNT',1067,25,NULL,'10:30:00.000000',1,'2015-07-29',1,3),(5354,'HiepNT',1067,25,NULL,'10:30:00.000000',1,'2015-07-30',1,3),(5355,'HiepNT',1067,25,NULL,'10:30:00.000000',1,'2015-07-31',1,3),(5356,'VinhDP',1054,25,NULL,'07:00:00.000000',1,'2015-07-27',1,1),(5357,'VinhDP',1054,25,NULL,'07:00:00.000000',1,'2015-07-28',1,1),(5358,'VinhDP',1054,25,NULL,'07:00:00.000000',1,'2015-07-29',1,1),(5359,'VinhDP',1054,25,NULL,'07:00:00.000000',1,'2015-07-30',1,1),(5360,'VinhDP',1054,25,NULL,'07:00:00.000000',1,'2015-07-31',1,1),(5361,'AnNDH',1054,25,NULL,'08:45:00.000000',1,'2015-07-27',1,2),(5362,'AnNDH',1054,25,NULL,'08:45:00.000000',1,'2015-07-28',1,2),(5363,'AnNDH',1054,25,NULL,'08:45:00.000000',1,'2015-07-29',1,2),(5364,'AnNDH',1054,25,NULL,'08:45:00.000000',1,'2015-07-30',1,2),(5365,'AnNDH',1054,25,NULL,'08:45:00.000000',1,'2015-07-31',1,2),(5366,'HoangNT',1054,25,NULL,'10:30:00.000000',1,'2015-07-27',1,3),(5367,'HoangNT',1054,25,NULL,'10:30:00.000000',1,'2015-07-29',1,3),(5368,'HiepNT',1054,25,NULL,'12:30:00.000000',1,'2015-07-27',1,4),(5369,'HiepNT',1054,25,NULL,'12:30:00.000000',1,'2015-07-29',1,4),(5370,'HiepTT',1054,25,NULL,'14:15:00.000000',1,'2015-07-27',1,5),(5371,'HiepTT',1054,25,NULL,'14:15:00.000000',1,'2015-07-28',1,5),(5372,'HiepTT',1054,25,NULL,'14:15:00.000000',1,'2015-07-29',1,5),(5373,'HiepTT',1054,25,NULL,'14:15:00.000000',1,'2015-07-30',1,5),(5374,'HiepTT',1054,25,NULL,'14:15:00.000000',1,'2015-07-31',1,5),(5375,'BinhDTH',1054,25,NULL,'16:00:00.000000',1,'2015-07-27',1,6),(5376,'BinhDTH',1054,25,NULL,'16:00:00.000000',1,'2015-07-28',1,6),(5377,'BinhDTH',1054,25,NULL,'16:00:00.000000',1,'2015-07-29',1,6),(5378,'BinhDTH',1054,25,NULL,'16:00:00.000000',1,'2015-07-30',1,6),(5379,'BinhDTH',1054,25,NULL,'16:00:00.000000',1,'2015-07-31',1,6),(5380,'NhuLTL',1066,25,NULL,'10:30:00.000000',1,'2015-07-27',1,3),(5381,'NhuLTL',1066,25,NULL,'10:30:00.000000',1,'2015-07-28',1,3),(5382,'NhuLTL',1066,25,NULL,'10:30:00.000000',1,'2015-07-29',1,3),(5383,'NhuLTL',1066,25,NULL,'10:30:00.000000',1,'2015-07-30',1,3),(5384,'NhuLTL',1066,25,NULL,'10:30:00.000000',1,'2015-07-31',1,3),(5385,'HoangNT',1066,25,NULL,'12:30:00.000000',1,'2015-07-27',1,4),(5386,'HoangNT',1066,25,NULL,'12:30:00.000000',1,'2015-07-28',1,4),(5387,'HoangNT',1066,25,NULL,'12:30:00.000000',1,'2015-07-29',1,4),(5388,'HoangNT',1066,25,NULL,'12:30:00.000000',1,'2015-07-30',1,4),(5389,'HoangNT',1066,25,NULL,'12:30:00.000000',1,'2015-07-31',1,4),(5390,'TuDT',1066,25,NULL,'16:00:00.000000',1,'2015-07-27',1,6),(5391,'TuDT',1066,25,NULL,'16:00:00.000000',1,'2015-07-28',1,6),(5392,'TuDT',1066,25,NULL,'16:00:00.000000',1,'2015-07-29',1,6),(5393,'TuDT',1066,25,NULL,'16:00:00.000000',1,'2015-07-30',1,6),(5394,'TuDT',1066,25,NULL,'16:00:00.000000',1,'2015-07-31',1,6),(5395,'HungLD',1069,25,NULL,'07:00:00.000000',1,'2015-07-27',1,1),(5396,'HungLD',1069,25,NULL,'07:00:00.000000',1,'2015-07-29',1,1),(5397,'KhanhKT',1069,25,NULL,'08:45:00.000000',1,'2015-07-27',1,2),(5398,'KhanhKT',1069,25,NULL,'08:45:00.000000',1,'2015-07-28',1,2),(5399,'KhanhKT',1069,25,NULL,'08:45:00.000000',1,'2015-07-29',1,2),(5400,'KhanhKT',1069,25,NULL,'08:45:00.000000',1,'2015-07-30',1,2),(5401,'KhanhKT',1069,25,NULL,'08:45:00.000000',1,'2015-07-31',1,2),(5402,'TaiNT',1069,25,NULL,'10:30:00.000000',1,'2015-07-27',1,3),(5403,'TaiNT',1069,25,NULL,'10:30:00.000000',1,'2015-07-28',1,3),(5404,'TaiNT',1069,25,NULL,'10:30:00.000000',1,'2015-07-29',1,3),(5405,'TaiNT',1069,25,NULL,'10:30:00.000000',1,'2015-07-30',1,3),(5406,'TaiNT',1069,25,NULL,'10:30:00.000000',1,'2015-07-31',1,3),(5407,'VanTTN',1070,25,NULL,'08:45:00.000000',1,'2015-07-27',1,2),(5408,'VanTTN',1070,25,NULL,'08:45:00.000000',1,'2015-07-28',1,2),(5409,'VanTTN',1070,25,NULL,'08:45:00.000000',1,'2015-07-29',1,2),(5410,'VanTTN',1070,25,NULL,'08:45:00.000000',1,'2015-07-30',1,2),(5411,'VanTTN',1070,25,NULL,'08:45:00.000000',1,'2015-07-31',1,2),(5412,'VinhDP',1070,25,NULL,'10:30:00.000000',1,'2015-07-27',1,3),(5413,'VinhDP',1070,25,NULL,'10:30:00.000000',1,'2015-07-28',1,3),(5414,'VinhDP',1070,25,NULL,'10:30:00.000000',1,'2015-07-29',1,3),(5415,'VinhDP',1070,25,NULL,'10:30:00.000000',1,'2015-07-30',1,3),(5416,'VinhDP',1070,25,NULL,'10:30:00.000000',1,'2015-07-31',1,3),(5417,'VanTTN',1070,25,NULL,'12:30:00.000000',1,'2015-07-27',1,4),(5418,'VanTTN',1070,25,NULL,'12:30:00.000000',1,'2015-07-28',1,4),(5419,'VanTTN',1070,25,NULL,'12:30:00.000000',1,'2015-07-29',1,4),(5420,'VanTTN',1070,25,NULL,'12:30:00.000000',1,'2015-07-30',1,4),(5421,'VanTTN',1070,25,NULL,'12:30:00.000000',1,'2015-07-31',1,4),(5422,'VinhDP',1070,25,NULL,'16:00:00.000000',1,'2015-07-27',1,6),(5423,'VinhDP',1070,25,NULL,'16:00:00.000000',1,'2015-07-28',1,6),(5424,'VinhDP',1070,25,NULL,'16:00:00.000000',1,'2015-07-29',1,6),(5425,'VinhDP',1070,25,NULL,'16:00:00.000000',1,'2015-07-30',1,6),(5426,'VinhDP',1070,25,NULL,'16:00:00.000000',1,'2015-07-31',1,6),(5427,'SuTV',1071,25,NULL,'07:00:00.000000',1,'2015-07-27',1,1),(5428,'SuTV',1071,25,NULL,'07:00:00.000000',1,'2015-07-28',1,1),(5429,'SuTV',1071,25,NULL,'07:00:00.000000',1,'2015-07-29',1,1),(5430,'SuTV',1071,25,NULL,'07:00:00.000000',1,'2015-07-30',1,1),(5431,'SuTV',1071,25,NULL,'07:00:00.000000',1,'2015-07-31',1,1),(5432,'TaiNT',1071,25,NULL,'08:45:00.000000',1,'2015-07-27',1,2),(5433,'TaiNT',1071,25,NULL,'08:45:00.000000',1,'2015-07-28',1,2),(5434,'TaiNT',1071,25,NULL,'08:45:00.000000',1,'2015-07-29',1,2),(5435,'TaiNT',1071,25,NULL,'08:45:00.000000',1,'2015-07-30',1,2),(5436,'TaiNT',1071,25,NULL,'08:45:00.000000',1,'2015-07-31',1,2),(5437,'TaiNT',1071,25,NULL,'12:30:00.000000',1,'2015-07-27',1,4),(5438,'TaiNT',1071,25,NULL,'12:30:00.000000',1,'2015-07-28',1,4),(5439,'TaiNT',1071,25,NULL,'12:30:00.000000',1,'2015-07-29',1,4),(5440,'TaiNT',1071,25,NULL,'12:30:00.000000',1,'2015-07-30',1,4),(5441,'TaiNT',1071,25,NULL,'12:30:00.000000',1,'2015-07-31',1,4),(5442,'HoangNT',1071,25,NULL,'14:15:00.000000',1,'2015-07-27',1,5),(5443,'HoangNT',1071,25,NULL,'14:15:00.000000',1,'2015-07-28',1,5),(5444,'HoangNT',1071,25,NULL,'14:15:00.000000',1,'2015-07-29',1,5),(5445,'HoangNT',1071,25,NULL,'14:15:00.000000',1,'2015-07-30',1,5),(5446,'HoangNT',1071,25,NULL,'14:15:00.000000',1,'2015-07-31',1,5),(5447,'HungNH',1072,25,NULL,'12:30:00.000000',1,'2015-07-27',1,4),(5448,'HungNH',1072,25,NULL,'12:30:00.000000',1,'2015-07-29',1,4),(5449,'HungNH',1072,25,NULL,'12:30:00.000000',1,'2015-07-31',1,4),(5450,'VinhDP',1072,25,NULL,'14:15:00.000000',1,'2015-07-27',1,5),(5451,'VinhDP',1072,25,NULL,'14:15:00.000000',1,'2015-07-28',1,5),(5452,'VinhDP',1072,25,NULL,'14:15:00.000000',1,'2015-07-29',1,5),(5453,'VinhDP',1072,25,NULL,'14:15:00.000000',1,'2015-07-30',1,5),(5454,'VinhDP',1072,25,NULL,'14:15:00.000000',1,'2015-07-31',1,5),(5455,'SuTV',1072,25,NULL,'16:00:00.000000',1,'2015-07-27',1,6),(5456,'SuTV',1072,25,NULL,'16:00:00.000000',1,'2015-07-28',1,6),(5457,'SuTV',1072,25,NULL,'16:00:00.000000',1,'2015-07-29',1,6),(5458,'SuTV',1072,25,NULL,'16:00:00.000000',1,'2015-07-30',1,6),(5459,'SuTV',1072,25,NULL,'16:00:00.000000',1,'2015-07-31',1,6),(5460,'HungNH',1060,30,'Thay đổi phòng từ phòng 101 sang phòng 115','14:15:00.000000',1,'2015-07-28',1,5),(5461,'HungNH',1058,30,'Thay đổi phòng từ phòng 101 sang phòng 103','12:30:00.000000',1,'2015-07-28',1,4),(5462,'PhuongLHK',1058,30,'Thay đổi phòng từ phòng 101 sang phòng 103','07:00:00.000000',1,'2015-07-28',1,1),(5463,'PhuongLHK',1058,30,'Thay đổi phòng từ phòng 101 sang phòng 103','08:45:00.000000',1,'2015-07-28',1,2);
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
  PRIMARY KEY (`Username`),
  CONSTRAINT `FK_tblUserInfo_tblUser` FOREIGN KEY (`Username`) REFERENCES `tbluser` (`Username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbluserinfo`
--

LOCK TABLES `tbluserinfo` WRITE;
/*!40000 ALTER TABLE `tbluserinfo` DISABLE KEYS */;
INSERT INTO `tbluserinfo` VALUES ('AnhNT','AnhNT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('AnNDH','AnNDH','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('BaoNQ','BaoNQ','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('BinhDTH','BinhDTH','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('CamDTH','CamDTH','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('Cecile','Cecile','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('ChauLH','ChauLH','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('ChungKTT','ChungKTT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('Gerardo','Gerardo','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('HiepNT','HiepNT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('HiepTT','HiepTT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('HoangNT','HoangNT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('HungLD','HungLD','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('HungNH','HungNH','+0962067505','2015-07-27 15:08:42.956000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('hungtv','hungtv','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('HuongPTT','HuongPTT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('KhanhKT','KhanhKT','+84962067505','2015-07-21 09:47:34.381000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('KieuTA','KieuTA','+84962067505','2015-07-23 08:45:34.706000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('LienTTH','LienTTH','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('LoiND','LoiND','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('NhuLTL','NhuLTL','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('PhuongLHK','PhuongLHK','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('staff','Doan Nguyen','+841688167252','2015-07-21 09:08:26.547000','APA91bHxvLq2fIHYV9hH1WAWCb2IkrLQDeN47HKrAhaNesoLmpsbJVvDMK8g1TWVXeXtSgoZAn12N7wZr0SgXUcDy_wh5b-G-OR1E5tnNRb4vDaCjV7ClamsAowysqpeKd9akg_qhSK_j5Be-SXFXf-MaZr_FQo_gw'),('SuTV','SuTV','+84962067505','2015-07-17 15:30:51.233000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('TaiNT','TaiNT','+84962067505','2015-07-23 07:58:57.037000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('teacher','Minh Chi','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('ThanhDT','ThanhDT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('ThuyetDTM','ThuyetDTM','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('TriBD','TriBD','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('TuDT','TuDT','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('VanTTN','VanTTN','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g'),('VinhDP','VinhDP','+84962067505','2015-05-15 00:00:00.000000','APA91bEFTFhCFWbyuqGireJV1oKA2QLUwp4I80fMKowpt8pJLpg1asWFhjkD89gVZacmUO4Enanp3V4TZFJ2gASe7cgQQO5xgJhCP0luAw-eJr0S-rL3kdXrgYY5R1js31ewtdJZ4_n4iTjo-AvcSYCorDUBYRLZ_g');
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

-- Dump completed on 2015-07-30  5:38:50
