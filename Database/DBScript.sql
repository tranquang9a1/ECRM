-- MySQL dump 10.13  Distrib 5.6.24, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: ecrm
-- ------------------------------------------------------
-- Server version	5.5.39

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
  `CreateTime` datetime NOT NULL,
  `IsDelete` tinyint(1) DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `IsAllInformation` tinyint(1) DEFAULT NULL,
  `DamagedLevel` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_tblClassroom_tblRoomType1` (`RoomTypeId`),
  CONSTRAINT `FK_tblClassroom_tblRoomType1` FOREIGN KEY (`RoomTypeId`) REFERENCES `tblroomtype` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1049 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblclassroom`
--

LOCK TABLES `tblclassroom` WRITE;
/*!40000 ALTER TABLE `tblclassroom` DISABLE KEYS */;
INSERT INTO `tblclassroom` VALUES (1042,1017,'101','2015-06-16 10:09:43',0,NULL,1,0),(1043,1017,'213','2015-06-16 10:09:59',0,NULL,1,0),(1044,1017,'310','2015-06-16 10:10:36',0,NULL,1,0),(1045,1017,'312','2015-06-16 10:10:53',0,NULL,1,65),(1046,1017,'403','2015-06-16 10:11:11',0,NULL,1,100),(1047,1017,'407','2015-06-16 10:11:29',0,NULL,1,0),(1048,1016,'102','2015-06-24 10:37:47',0,NULL,1,0);
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
  PRIMARY KEY (`Id`),
  KEY `FK_tblEquipment_tblEquipmentCategory` (`CategoryId`),
  KEY `FK_tblEquipment_tblClassroom` (`ClassroomId`),
  CONSTRAINT `FK_tblEquipment_tblClassroom` FOREIGN KEY (`ClassroomId`) REFERENCES `tblclassroom` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_tblEquipment_tblEquipmentCategory` FOREIGN KEY (`CategoryId`) REFERENCES `tblequipmentcategory` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblequipment`
--

LOCK TABLES `tblequipment` WRITE;
/*!40000 ALTER TABLE `tblequipment` DISABLE KEYS */;
INSERT INTO `tblequipment` VALUES (161,1,1042,'[1]',0,2994,'Projector2015','11111111111'),(162,1,1043,'[1]',1,2998.5,'Projector2015','22222222222'),(163,1,1044,'[1]',1,2998.5,'Projector2015','33333333333'),(164,1,1045,'[1]',0,3000,'Projector2015','44444444444'),(165,1,1046,'[1]',0,2998.5,'Projector2015','55555555555'),(166,1,1047,'[1]',1,2998.5,'Projector2015','66666666666'),(167,2,1042,'[2]',0,NULL,'SONY','11111111111'),(168,1,NULL,'[1]',1,3000,'SONY','22222222222'),(169,2,1043,'[2]',1,NULL,'SONY','33333333333'),(170,2,1044,'[2]',1,NULL,'SONY','44444444444'),(171,2,1045,'[2]',1,NULL,'SONY','55555555555'),(172,2,1046,'[2]',0,NULL,'SONY','66666666666'),(173,2,1047,'[2]',1,NULL,'SONY','22222222222'),(174,3,1042,'[3]',1,NULL,'DAIKIN','11111111111'),(175,2,NULL,NULL,1,NULL,'DAIKIN','22222222222'),(176,3,1042,'[3]',1,NULL,'DAIKIN','22222222222'),(177,3,1043,'[3]',1,NULL,'DAIKIN','33333333333'),(178,3,1043,'[3]',1,NULL,'DAIKIN','44444444444'),(179,3,1044,'[3]',1,NULL,'DAIKIN','55555555555'),(180,3,1044,'[3]',1,NULL,'DAIKIN','66666666666'),(181,3,1045,'[3]',0,NULL,'HITACHI','11111111111'),(182,3,1045,'[3]',1,NULL,'HITACHI','22222222222'),(183,3,1046,'[3]',0,NULL,'HITACHI','33333333333'),(184,3,1046,'[3]',1,NULL,'HITACHI','44444444444'),(185,3,1047,'[3]',1,NULL,'HITACHI','55555555555'),(186,3,1047,'[3]',1,NULL,'HITACHI','66666666666'),(187,7,1046,NULL,1,0,NULL,NULL),(188,8,1046,NULL,1,0,NULL,NULL),(189,7,1046,'[0,1]',1,0,NULL,NULL),(190,7,1046,'[1,1]',1,0,NULL,NULL),(191,7,1046,'[0,2]',1,0,NULL,NULL),(192,8,1046,'[0,1,0]',1,0,NULL,NULL),(193,8,1046,'[0,1,1]',1,0,NULL,NULL),(194,7,1047,NULL,1,0,NULL,NULL),(195,8,1047,NULL,1,0,NULL,NULL),(196,1,1048,'[1]',1,3000,NULL,NULL),(197,3,1048,'[3]',1,NULL,NULL,NULL),(198,3,1048,'[3]',1,NULL,NULL,NULL),(199,2,1048,'[2]',1,NULL,NULL,NULL),(200,4,1042,'[4]',1,NULL,NULL,NULL),(201,4,1045,'[4]',1,NULL,NULL,NULL),(202,7,1045,NULL,1,NULL,NULL,NULL),(203,4,1046,'[4]',0,NULL,NULL,NULL),(204,8,1045,NULL,1,NULL,NULL,NULL);
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
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblequipmentcategory`
--

LOCK TABLES `tblequipmentcategory` WRITE;
/*!40000 ALTER TABLE `tblequipmentcategory` DISABLE KEYS */;
INSERT INTO `tblequipmentcategory` VALUES (1,'Máy chiếu',3000),(2,'Tivi',0),(3,'Máy lạnh',6000),(4,'Máy quạt',0),(5,'Loa',0),(6,'Bóng đèn',0),(7,'Bàn',0),(8,'Ghế',0);
/*!40000 ALTER TABLE `tblequipmentcategory` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblnotification`
--

LOCK TABLES `tblnotification` WRITE;
/*!40000 ALTER TABLE `tblnotification` DISABLE KEYS */;
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
  `CreateTime` datetime NOT NULL,
  `Evaluate` longtext NOT NULL,
  `Status` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_tblReport_tblUser1` (`Username`),
  KEY `FK_tblReport_tblClassroom` (`ClassRoomId`),
  CONSTRAINT `FK_tblReport_tblClassroom` FOREIGN KEY (`ClassRoomId`) REFERENCES `tblclassroom` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_tblReport_tblUser1` FOREIGN KEY (`Username`) REFERENCES `tbluser` (`Username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblreport`
--

LOCK TABLES `tblreport` WRITE;
/*!40000 ALTER TABLE `tblreport` DISABLE KEYS */;
INSERT INTO `tblreport` VALUES (19,'KhanhKT',1042,'2015-06-17 03:36:10','Doi phong',3),(25,'SuTV',1045,'2015-06-26 10:22:37','3',3),(27,'SuTV',1046,'2015-06-26 11:34:41','3',1),(28,'SuTV',1045,'2015-06-28 01:48:12','3',2),(29,'KhanhKT',1045,'2015-06-28 09:10:58','3',3);
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
  `ResolveTime` datetime DEFAULT NULL,
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
INSERT INTO `tblreportdetail` VALUES (161,19,'1','[1]','be man hinh',NULL,NULL,1),(164,25,'2','[1]','',NULL,NULL,1),(164,28,'4','[1]','',NULL,NULL,0),(164,29,'2','[1]','',NULL,NULL,1),(165,27,'4','[1]','',NULL,NULL,0),(167,19,'1','[2]','abc',NULL,NULL,1),(171,28,'2','[2]','',NULL,NULL,1),(171,29,'4','[2]','',NULL,NULL,1),(172,27,'2','[2]','',NULL,NULL,0),(181,25,'3','[3]','',NULL,NULL,1),(181,28,'2','[3]','',NULL,NULL,0),(181,29,'2','[3]','',NULL,NULL,1),(183,27,'1','[3]','sadsa',NULL,NULL,0),(201,28,'1','[4]','213123131',NULL,NULL,1),(202,29,'4',NULL,'',NULL,NULL,1),(203,27,'1','[4]','asdasds',NULL,NULL,0),(204,29,'4',NULL,'',NULL,NULL,1);
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
  `CreateTime` datetime NOT NULL,
  `IsDelete` tinyint(1) DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1020 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblroomtype`
--

LOCK TABLES `tblroomtype` WRITE;
/*!40000 ALTER TABLE `tblroomtype` DISABLE KEYS */;
INSERT INTO `tblroomtype` VALUES (1015,'Loai 3 day',30,3,'5-5-5','2-2-2',1,0,1,1,1,1,'2015-06-10 00:13:39',0,NULL),(1016,'Loai 30 cho',30,2,'5-5','3-3',1,2,1,0,0,1,'2015-06-10 08:10:18',0,NULL),(1017,'Loai 25 cho',25,2,'5-5','3-2',1,2,1,2,0,1,'2015-06-13 12:14:04',0,NULL),(1018,'Loai 40 cho',40,2,'5-5','4-4',1,2,1,4,1,1,'2015-06-14 20:42:56',0,NULL),(1019,'Loai 20 cho',20,2,'5-5','2-2',1,2,1,0,1,0,'2015-06-15 15:39:25',0,NULL);
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
  `TimeFrom` time NOT NULL,
  `Slots` int(11) NOT NULL,
  `Date` date NOT NULL,
  `IsActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_tblSchedule_tblUser1` (`Username`),
  KEY `FK_tblSchedule_tblClassroom` (`ClassroomId`),
  CONSTRAINT `FK_tblSchedule_tblClassroom` FOREIGN KEY (`ClassroomId`) REFERENCES `tblclassroom` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_tblSchedule_tblUser1` FOREIGN KEY (`Username`) REFERENCES `tbluser` (`Username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1683 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblschedule`
--

LOCK TABLES `tblschedule` WRITE;
/*!40000 ALTER TABLE `tblschedule` DISABLE KEYS */;
INSERT INTO `tblschedule` VALUES (1509,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-06-15',1),(1510,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-06-16',1),(1511,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-06-17',1),(1512,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-06-18',1),(1513,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-06-19',1),(1514,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-06-15',1),(1515,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-06-16',1),(1516,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-06-17',1),(1517,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-06-18',1),(1518,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-06-19',1),(1519,'SuTV',1044,25,NULL,'16:00:00',1,'2015-06-15',1),(1520,'SuTV',1044,25,NULL,'16:00:00',1,'2015-06-16',1),(1521,'SuTV',1044,25,NULL,'16:00:00',1,'2015-06-17',1),(1522,'SuTV',1044,25,NULL,'16:00:00',1,'2015-06-18',1),(1523,'SuTV',1044,25,NULL,'16:00:00',1,'2015-06-19',1),(1524,'SuTV',1045,25,NULL,'08:45:00',1,'2015-06-15',1),(1525,'SuTV',1045,25,NULL,'08:45:00',1,'2015-06-16',0),(1526,'SuTV',1045,25,NULL,'08:45:00',1,'2015-06-17',1),(1527,'SuTV',1045,25,NULL,'08:45:00',1,'2015-06-18',1),(1528,'SuTV',1045,25,NULL,'08:45:00',1,'2015-06-19',1),(1529,'KhanhKT',1045,25,NULL,'12:30:00',1,'2015-06-15',1),(1530,'KhanhKT',1045,25,NULL,'12:30:00',1,'2015-06-16',1),(1531,'KhanhKT',1045,25,NULL,'12:30:00',1,'2015-06-17',1),(1532,'KhanhKT',1045,25,NULL,'12:30:00',1,'2015-06-18',1),(1533,'KhanhKT',1045,25,NULL,'12:30:00',1,'2015-06-19',1),(1534,'SuTV',1045,25,NULL,'14:15:00',1,'2015-06-15',1),(1535,'SuTV',1045,25,NULL,'14:15:00',1,'2015-06-16',1),(1536,'SuTV',1045,25,NULL,'14:15:00',1,'2015-06-17',1),(1537,'SuTV',1045,25,NULL,'14:15:00',1,'2015-06-18',1),(1538,'SuTV',1045,25,NULL,'14:15:00',1,'2015-06-19',1),(1539,'SuTV',1046,25,NULL,'10:30:00',1,'2015-06-15',1),(1540,'SuTV',1046,25,NULL,'10:30:00',1,'2015-06-16',1),(1541,'SuTV',1046,25,NULL,'10:30:00',1,'2015-06-17',1),(1542,'SuTV',1046,25,NULL,'10:30:00',1,'2015-06-18',1),(1543,'SuTV',1046,25,NULL,'10:30:00',1,'2015-06-19',1),(1544,'KhanhKT',1047,25,NULL,'14:15:00',1,'2015-06-15',1),(1545,'KhanhKT',1047,25,NULL,'14:15:00',1,'2015-06-16',1),(1546,'KhanhKT',1047,25,NULL,'14:15:00',1,'2015-06-17',1),(1547,'KhanhKT',1047,25,NULL,'14:15:00',1,'2015-06-18',1),(1548,'KhanhKT',1047,25,NULL,'14:15:00',1,'2015-06-19',1),(1549,'KhanhKT',1047,25,NULL,'16:00:00',1,'2015-06-15',1),(1550,'KhanhKT',1047,25,NULL,'16:00:00',1,'2015-06-16',1),(1551,'KhanhKT',1047,25,NULL,'16:00:00',1,'2015-06-17',1),(1552,'KhanhKT',1047,25,NULL,'16:00:00',1,'2015-06-18',1),(1553,'KhanhKT',1047,25,NULL,'16:00:00',1,'2015-06-19',1),(1557,'SuTV',1042,25,'Thay doi phong','08:45:00',1,'2015-06-16',1),(1558,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-06-22',1),(1559,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-06-23',1),(1560,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-06-24',1),(1561,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-06-25',1),(1562,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-06-26',1),(1563,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-06-22',1),(1564,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-06-23',1),(1565,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-06-24',1),(1566,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-06-25',1),(1567,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-06-26',1),(1568,'SuTV',1044,25,NULL,'16:00:00',1,'2015-06-22',1),(1569,'SuTV',1044,25,NULL,'16:00:00',1,'2015-06-23',1),(1570,'SuTV',1044,25,NULL,'16:00:00',1,'2015-06-24',1),(1571,'SuTV',1044,25,NULL,'16:00:00',1,'2015-06-25',1),(1572,'SuTV',1044,25,NULL,'16:00:00',1,'2015-06-26',1),(1573,'SuTV',1045,25,NULL,'08:45:00',1,'2015-06-22',0),(1574,'SuTV',1045,25,NULL,'08:45:00',1,'2015-06-23',0),(1575,'SuTV',1045,25,NULL,'08:45:00',1,'2015-06-24',0),(1576,'SuTV',1045,25,NULL,'08:45:00',1,'2015-06-25',0),(1577,'SuTV',1045,25,NULL,'08:45:00',1,'2015-06-28',1),(1578,'KhanhKT',1045,25,NULL,'12:30:00',1,'2015-06-22',0),(1579,'KhanhKT',1045,25,NULL,'12:30:00',1,'2015-06-23',0),(1580,'KhanhKT',1045,25,NULL,'12:30:00',1,'2015-06-24',0),(1581,'KhanhKT',1045,25,NULL,'12:30:00',1,'2015-06-25',0),(1582,'KhanhKT',1045,25,NULL,'12:30:00',1,'2015-06-28',1),(1583,'SuTV',1045,25,NULL,'14:15:00',1,'2015-06-22',0),(1584,'SuTV',1045,25,NULL,'14:15:00',1,'2015-06-23',0),(1585,'SuTV',1045,25,NULL,'14:15:00',1,'2015-06-24',0),(1586,'SuTV',1045,25,NULL,'14:15:00',1,'2015-06-25',0),(1587,'SuTV',1045,25,NULL,'14:15:00',1,'2015-06-28',1),(1588,'SuTV',1046,25,NULL,'10:30:00',1,'2015-06-22',1),(1589,'SuTV',1046,25,NULL,'10:30:00',1,'2015-06-23',1),(1590,'SuTV',1046,25,NULL,'10:30:00',1,'2015-06-24',1),(1591,'SuTV',1046,25,NULL,'10:30:00',1,'2015-06-25',1),(1592,'SuTV',1046,25,NULL,'12:30:00',1,'2015-06-28',1),(1593,'KhanhKT',1047,25,NULL,'14:15:00',1,'2015-06-22',1),(1594,'KhanhKT',1047,25,NULL,'14:15:00',1,'2015-06-23',1),(1595,'KhanhKT',1047,25,NULL,'14:15:00',1,'2015-06-24',1),(1596,'KhanhKT',1047,25,NULL,'14:15:00',1,'2015-06-25',1),(1597,'KhanhKT',1047,25,NULL,'14:15:00',1,'2015-06-26',1),(1599,'SuTV',1042,25,'Thay đổi phòng','08:45:00',1,'2015-06-25',1),(1600,'KhanhKT',1042,25,'Thay đổi phòng','12:30:00',1,'2015-06-25',1),(1601,'SuTV',1042,25,'Thay đổi phòng','14:15:00',1,'2015-06-25',1),(1602,'SuTV',1042,25,'Thay đổi phòng','08:45:00',1,'2015-06-22',1),(1603,'KhanhKT',1042,25,'Thay đổi phòng','12:30:00',1,'2015-06-22',1),(1604,'SuTV',1042,25,'Thay đổi phòng','14:15:00',1,'2015-06-22',1),(1605,'SuTV',1042,25,'Thay đổi phòng','08:45:00',1,'2015-06-23',1),(1606,'KhanhKT',1042,25,'Thay đổi phòng','12:30:00',1,'2015-06-23',1),(1607,'SuTV',1042,25,'Thay đổi phòng','14:15:00',1,'2015-06-23',1),(1608,'SuTV',1042,25,'Thay đổi phòng','08:45:00',1,'2015-06-24',1),(1609,'KhanhKT',1042,25,'Thay đổi phòng','12:30:00',1,'2015-06-24',1),(1610,'SuTV',1042,25,'Thay đổi phòng','14:15:00',1,'2015-06-24',1),(1611,'KhanhKT',1047,25,NULL,'07:00:00',1,'2015-07-01',1),(1612,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-06-29',1),(1613,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-06-30',1),(1614,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-07-01',1),(1615,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-07-02',1),(1616,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-07-03',1),(1617,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-06-29',1),(1618,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-06-30',1),(1619,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-07-01',1),(1620,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-07-02',1),(1621,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-07-03',1),(1622,'SuTV',1044,25,NULL,'16:00:00',1,'2015-06-29',1),(1623,'SuTV',1044,25,NULL,'16:00:00',1,'2015-06-30',1),(1624,'SuTV',1044,25,NULL,'16:00:00',1,'2015-07-01',1),(1625,'SuTV',1044,25,NULL,'16:00:00',1,'2015-07-02',1),(1626,'SuTV',1044,25,NULL,'16:00:00',1,'2015-07-03',1),(1627,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-06-29',1),(1628,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-06-30',1),(1629,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-07-01',1),(1630,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-07-02',1),(1631,'KhanhKT',1042,25,NULL,'07:00:00',1,'2015-07-03',1),(1632,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-06-29',1),(1633,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-06-30',1),(1634,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-07-01',1),(1635,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-07-02',1),(1636,'KhanhKT',1043,25,NULL,'08:45:00',1,'2015-07-03',1),(1637,'SuTV',1044,25,NULL,'16:00:00',1,'2015-06-29',1),(1638,'SuTV',1044,25,NULL,'16:00:00',1,'2015-06-30',1),(1639,'SuTV',1044,25,NULL,'16:00:00',1,'2015-07-01',1),(1640,'SuTV',1044,25,NULL,'16:00:00',1,'2015-07-02',1),(1641,'SuTV',1044,25,NULL,'16:00:00',1,'2015-07-03',1),(1642,'SuTV',1045,25,NULL,'08:45:00',1,'2015-06-29',1),(1643,'SuTV',1045,25,NULL,'08:45:00',1,'2015-06-30',1),(1644,'SuTV',1045,25,NULL,'08:45:00',1,'2015-07-01',1),(1645,'SuTV',1045,25,NULL,'08:45:00',1,'2015-07-02',1),(1646,'SuTV',1045,25,NULL,'08:45:00',1,'2015-07-03',1),(1647,'KhanhKT',1045,25,NULL,'12:30:00',1,'2015-06-29',1),(1648,'KhanhKT',1045,25,NULL,'12:30:00',1,'2015-06-30',1),(1649,'KhanhKT',1045,25,NULL,'12:30:00',1,'2015-07-01',1),(1650,'KhanhKT',1045,25,NULL,'12:30:00',1,'2015-07-02',1),(1651,'KhanhKT',1045,25,NULL,'12:30:00',1,'2015-07-03',1),(1652,'SuTV',1045,25,NULL,'14:15:00',1,'2015-06-29',1),(1653,'SuTV',1045,25,NULL,'14:15:00',1,'2015-06-30',1),(1654,'SuTV',1045,25,NULL,'14:15:00',1,'2015-07-01',1),(1655,'SuTV',1045,25,NULL,'14:15:00',1,'2015-07-02',1),(1656,'SuTV',1045,25,NULL,'14:15:00',1,'2015-07-03',1),(1657,'SuTV',1046,25,NULL,'10:30:00',1,'2015-06-29',1),(1658,'SuTV',1046,25,NULL,'10:30:00',1,'2015-06-30',1),(1659,'SuTV',1046,25,NULL,'10:30:00',1,'2015-07-01',1),(1660,'SuTV',1046,25,NULL,'10:30:00',1,'2015-07-02',1),(1661,'SuTV',1046,25,NULL,'10:30:00',1,'2015-07-03',1),(1662,'KhanhKT',1047,25,NULL,'14:15:00',1,'2015-06-29',1),(1663,'KhanhKT',1047,25,NULL,'14:15:00',1,'2015-06-30',1),(1664,'KhanhKT',1047,25,NULL,'14:15:00',1,'2015-07-01',1),(1665,'KhanhKT',1047,25,NULL,'14:15:00',1,'2015-07-02',1),(1666,'KhanhKT',1047,25,NULL,'14:15:00',1,'2015-07-03',1);
/*!40000 ALTER TABLE `tblschedule` ENABLE KEYS */;
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
INSERT INTO `tbluser` VALUES ('admin',1,'123456',1),('BaoNQ',3,'123456',1),('Gerardo',3,'123456',1),('hungtv',3,'123456',1),('KhanhKT',3,'123456',1),('LienTTH',3,'123456',1),('PhuongLHK',3,'123456',1),('staff',2,'123456',1),('SuTV',3,'123456',1),('teacher',3,'123456',1),('VinhDP',3,'123456',1);
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
  `LastLogin` datetime NOT NULL,
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
INSERT INTO `tbluserinfo` VALUES ('BaoNQ','BaoNQ','123','2015-05-15 00:00:00',NULL),('Gerardo','Gerardo','123','2015-05-15 00:00:00',NULL),('hungtv','hungtv','123','2015-05-15 00:00:00',NULL),('KhanhKT','KhanhKT','+841666815718','2015-05-15 00:00:00',NULL),('LienTTH','LienTTH','123','2015-05-15 00:00:00',NULL),('PhuongLHK','PhuongLHK','123','2015-05-15 00:00:00',NULL),('staff','Doan Nguyen','01666815718','2015-06-17 03:36:37',NULL),('SuTV','SuTV','+841666815718','2015-05-15 00:00:00',NULL),('teacher','Minh Chi','1666812243','2015-05-15 00:00:00',NULL),('VinhDP','VinhDP','123','2015-05-15 00:00:00',NULL);
/*!40000 ALTER TABLE `tbluserinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-28 13:16:47
