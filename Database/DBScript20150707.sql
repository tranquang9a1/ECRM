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
  PRIMARY KEY (`Id`),
  KEY `FK_tblClassroom_tblRoomType1` (`RoomTypeId`),
  CONSTRAINT `FK_tblClassroom_tblRoomType1` FOREIGN KEY (`RoomTypeId`) REFERENCES `tblroomtype` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1058 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblclassroom`
--

LOCK TABLES `tblclassroom` WRITE;
/*!40000 ALTER TABLE `tblclassroom` DISABLE KEYS */;
INSERT INTO `tblclassroom` VALUES (1049,1016,'101','2015-07-01 12:45:34.000000',0,NULL,1,0),(1050,1019,'213','2015-07-01 12:45:50.000000',0,NULL,1,0),(1051,1017,'310','2015-07-01 12:48:22.000000',0,NULL,1,0),(1052,1019,'312','2015-07-01 12:48:43.000000',0,NULL,1,0),(1053,1016,'403','2015-07-01 12:49:00.000000',0,NULL,1,0),(1054,1017,'407','2015-07-01 12:49:19.000000',0,NULL,1,0),(1055,1020,'102','2015-07-13 13:18:50.869000',0,NULL,1,0),(1056,1016,'202','2015-07-06 15:45:43.322000',0,NULL,1,0),(1057,1016,'203','2015-07-07 06:05:33.362000',0,NULL,1,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblequipment`
--

LOCK TABLES `tblequipment` WRITE;
/*!40000 ALTER TABLE `tblequipment` DISABLE KEYS */;
INSERT INTO `tblequipment` VALUES (211,1,1049,'[1]',1,2889,'Toshiba','2131231231'),(212,3,1049,'[3]',1,NULL,'Hitachi','4564564645'),(213,3,1049,'[3]',1,NULL,'Hitachi','8798798798'),(214,2,1049,'[2]',1,NULL,'Samsung','8767678678'),(215,1,1050,'[1]',1,2941.5,'Toshiba','7857858758'),(216,3,1050,'[3]',1,NULL,'Hitachi','7487484784'),(217,3,1050,'[3]',1,NULL,'Hitachi','5494949959'),(218,1,1051,'[1]',1,2923.5,'Toshiba','2166122126'),(219,3,1051,'[3]',1,NULL,'Hitachi','5435434343'),(220,3,1051,'[3]',1,NULL,'Hitachi','5616566156'),(221,2,1051,'[2]',1,NULL,'Samsung','6876876242'),(222,1,1052,'[1]',1,2833.5,'Toshiba','2192192919'),(223,3,1052,'[3]',1,NULL,'Hitachi','2818218287'),(224,3,1052,'[3]',1,NULL,'Hitachi','1731737137'),(225,1,1053,'[1]',1,2917.5,'Toshiba','9349493943'),(226,3,1053,'[3]',1,NULL,'Hitachi','6346437437'),(227,3,1053,'[3]',1,NULL,'Hitachi','2182181288'),(228,2,1053,'[2]',1,NULL,'Samsung','3163163637'),(229,1,1054,'[1]',1,2928,'Toshiba','3783735731'),(230,3,1054,'[3]',1,NULL,'Hitachi','0212012334'),(231,3,1054,'[3]',1,NULL,'Hitachi','5064506504'),(232,2,1054,'[2]',1,NULL,'Samsung','3700738078'),(261,1,1055,'[1]',1,3000,'Sony','98392385123'),(262,3,1055,'[3]',1,NULL,'DAIKIN','1239382454'),(263,3,1055,'[3]',1,NULL,'DAIKIN','1231924872'),(264,2,1055,'[2]',1,NULL,'SAMSUNG','3423423545'),(277,2,1049,'[2]',1,NULL,NULL,NULL),(278,1,1049,'[1]',1,NULL,NULL,NULL),(279,3,1049,'[3]',1,NULL,NULL,NULL),(280,1,1056,'[1]',1,2998.5,'SamSung','97238460123'),(281,3,1056,'[3]',1,NULL,'DAIKIN','37412030822'),(282,3,1056,'[3]',1,NULL,'DAIKIN','34597233474'),(283,2,1056,'[2]',1,NULL,'SONY','32445701283'),(284,1,1054,'[1]',1,NULL,NULL,NULL),(285,4,1054,'[4]',1,NULL,NULL,NULL),(286,6,1054,'[6]',1,NULL,NULL,NULL),(287,1,1052,'[1]',1,NULL,NULL,NULL),(288,6,1052,'[6]',1,NULL,NULL,NULL),(289,1,1057,'[1]',1,3000,'Sony','30487345123'),(290,3,1057,'[3]',1,NULL,'DAIKIN','97980982131'),(291,3,1057,'[3]',1,NULL,'DAIKIN','12309845666'),(292,2,1057,'[2]',1,NULL,'LG','97454802938'),(293,4,1051,'[4]',1,NULL,NULL,NULL),(294,6,1051,'[6]',1,NULL,NULL,NULL);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
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
  `CreateTime` datetime(6) NOT NULL,
  `Evaluate` longtext NOT NULL,
  `Status` int(11) NOT NULL,
  `ChangedRoom` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_tblReport_tblUser1` (`Username`),
  KEY `FK_tblReport_tblClassroom` (`ClassRoomId`),
  CONSTRAINT `FK_tblReport_tblClassroom` FOREIGN KEY (`ClassRoomId`) REFERENCES `tblclassroom` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_tblReport_tblUser1` FOREIGN KEY (`Username`) REFERENCES `tbluser` (`Username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=1021 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblroomtype`
--

LOCK TABLES `tblroomtype` WRITE;
/*!40000 ALTER TABLE `tblroomtype` DISABLE KEYS */;
INSERT INTO `tblroomtype` VALUES (1015,'Loai 3 day',30,3,'5-5-5','2-2-2',1,0,1,1,1,1,'2015-06-10 00:13:39.000000',0,NULL),(1016,'Loai 30 cho',30,2,'5-5','3-3',1,2,1,0,0,1,'2015-06-10 08:10:18.000000',0,NULL),(1017,'Loai 25 cho',25,2,'5-5','3-2',1,2,1,2,0,1,'2015-06-13 12:14:04.000000',0,NULL),(1018,'Loai 40 cho',40,2,'5-5','4-4',1,2,1,4,1,1,'2015-06-14 20:42:56.000000',0,NULL),(1019,'Loai 20 cho',20,2,'5-5','2-2',1,2,1,0,1,0,'2015-06-15 15:39:25.000000',0,NULL),(1020,'Logo',30,2,'5-5','3-3',1,2,1,2,1,1,'2015-07-13 13:18:22.686000',0,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=2411 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblschedule`
--

LOCK TABLES `tblschedule` WRITE;
/*!40000 ALTER TABLE `tblschedule` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
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
INSERT INTO `tbluserinfo` VALUES ('BaoNQ','BaoNQ','123','2015-05-15 00:00:00.000000',NULL),('Gerardo','Gerardo','123','2015-05-15 00:00:00.000000',NULL),('hungtv','hungtv','123','2015-05-15 00:00:00.000000',NULL),('KhanhKT','KhanhKT','+84962067505','2015-07-06 15:46:50.403000','APA91bHtQwK-j4I1Gl8BP3pYVwTqQfGwlqhU2JH_Nh-gDWK2wOHlgFksgIN5WVfYDwRmazskqOHHfG0AV7bDh1EaXNEIBvi6NGV6XWnyEiaxxWfiZVmOhtL-xEzHRq6q2PKCQgddMg5y'),('LienTTH','LienTTH','123','2015-05-15 00:00:00.000000',NULL),('PhuongLHK','PhuongLHK','123','2015-05-15 00:00:00.000000',NULL),('staff','Doan Nguyen','+841688167252','2015-07-06 16:11:21.913000','APA91bF-67gleZ1jHMqCHBgD8DejKVkkb7XqPlJHs7B3XQNIQ10pljYuJEzYep5J3td_BdWVLxrIXwsfFkJ-5aJ1mAPiYM7ryfOloB_fsn4_O--e8fS3qDx9ziA2y3pdx6bunkFhxeZ3'),('SuTV','SuTV','+84962067505','2015-07-06 16:31:15.487000','APA91bHtQwK-j4I1Gl8BP3pYVwTqQfGwlqhU2JH_Nh-gDWK2wOHlgFksgIN5WVfYDwRmazskqOHHfG0AV7bDh1EaXNEIBvi6NGV6XWnyEiaxxWfiZVmOhtL-xEzHRq6q2PKCQgddMg5y'),('teacher','Minh Chi','+84962067505','2015-05-15 00:00:00.000000',NULL),('VinhDP','VinhDP','123','2015-05-15 00:00:00.000000',NULL);
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

-- Dump completed on 2015-07-07 22:04:34
