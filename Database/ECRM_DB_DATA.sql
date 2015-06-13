USE [ecrm]
GO
SET IDENTITY_INSERT [dbo].[tblEquipmentCategory] ON 

INSERT [dbo].[tblEquipmentCategory] ([Id], [Name], [UsingTime]) VALUES (1, N'Máy chiếu', 3000)
INSERT [dbo].[tblEquipmentCategory] ([Id], [Name], [UsingTime]) VALUES (2, N'Tivi', 0)
INSERT [dbo].[tblEquipmentCategory] ([Id], [Name], [UsingTime]) VALUES (3, N'Máy lạnh', 6000)
INSERT [dbo].[tblEquipmentCategory] ([Id], [Name], [UsingTime]) VALUES (4, N'Máy quạt', 0)
INSERT [dbo].[tblEquipmentCategory] ([Id], [Name], [UsingTime]) VALUES (5, N'Loa', 0)
INSERT [dbo].[tblEquipmentCategory] ([Id], [Name], [UsingTime]) VALUES (6, N'Bóng đèn', 0)
INSERT [dbo].[tblEquipmentCategory] ([Id], [Name], [UsingTime]) VALUES (7, N'Bàn', 0)
INSERT [dbo].[tblEquipmentCategory] ([Id], [Name], [UsingTime]) VALUES (8, N'Ghế', 0)
SET IDENTITY_INSERT [dbo].[tblEquipmentCategory] OFF
SET IDENTITY_INSERT [dbo].[tblRoomType] ON 

INSERT [dbo].[tblRoomType] ([Id], [Name], [Slots], [VerticalRows], [HorizontalRows], [NumberOfSlotsEachHRows], [Projector], [AirConditioning], [Bulb], [Fan], [Speaker], [Television], [CreateTime], [IsDelete], [UpdateTime]) VALUES (1015, NULL, 30, 3, N'5-5-5', N'2-2-2', 1, 0, 1, 1, 1, 1, CAST(N'2015-06-10 00:13:39.373' AS DateTime), 0, NULL)
INSERT [dbo].[tblRoomType] ([Id], [Name], [Slots], [VerticalRows], [HorizontalRows], [NumberOfSlotsEachHRows], [Projector], [AirConditioning], [Bulb], [Fan], [Speaker], [Television], [CreateTime], [IsDelete], [UpdateTime]) VALUES (1016, NULL, 34, 2, N'5-5', N'3-3', 1, 2, 1, 0, 0, 1, CAST(N'2015-06-10 08:10:18.327' AS DateTime), 0, NULL)
INSERT [dbo].[tblRoomType] ([Id], [Name], [Slots], [VerticalRows], [HorizontalRows], [NumberOfSlotsEachHRows], [Projector], [AirConditioning], [Bulb], [Fan], [Speaker], [Television], [CreateTime], [IsDelete], [UpdateTime]) VALUES (1017, NULL, 45, 2, N'5-5', N'3-2', 1, 2, 1, 2, 0, 1, CAST(N'2015-06-13 12:14:04.220' AS DateTime), 0, NULL)
SET IDENTITY_INSERT [dbo].[tblRoomType] OFF
SET IDENTITY_INSERT [dbo].[tblClassroom] ON 

INSERT [dbo].[tblClassroom] ([Id], [RoomTypeId], [Name], [CreateTime], [IsDelete], [UpdateTime], [IsAllInformation]) VALUES (1034, 1015, N'101', CAST(N'2015-06-10 03:56:53.043' AS DateTime), 0, NULL, NULL)
INSERT [dbo].[tblClassroom] ([Id], [RoomTypeId], [Name], [CreateTime], [IsDelete], [UpdateTime], [IsAllInformation]) VALUES (1035, 1016, N'102', CAST(N'2015-06-13 12:15:34.627' AS DateTime), 0, NULL, NULL)
INSERT [dbo].[tblClassroom] ([Id], [RoomTypeId], [Name], [CreateTime], [IsDelete], [UpdateTime], [IsAllInformation]) VALUES (1036, 1017, N'103', CAST(N'2015-06-13 12:16:14.033' AS DateTime), 0, NULL, NULL)
SET IDENTITY_INSERT [dbo].[tblClassroom] OFF
SET IDENTITY_INSERT [dbo].[tblEquipment] ON 

INSERT [dbo].[tblEquipment] ([Id], [CategoryId], [ClassroomId], [Position], [Status], [TimeRemain], [Name], [SerialNumber]) VALUES (93, 1, 1034, N'[1]       ', 0, 0, NULL, NULL)
INSERT [dbo].[tblEquipment] ([Id], [CategoryId], [ClassroomId], [Position], [Status], [TimeRemain], [Name], [SerialNumber]) VALUES (94, 1, 1035, N'[1]       ', 1, 0, NULL, NULL)
INSERT [dbo].[tblEquipment] ([Id], [CategoryId], [ClassroomId], [Position], [Status], [TimeRemain], [Name], [SerialNumber]) VALUES (95, 3, 1035, N'[3]       ', 1, 0, NULL, NULL)
INSERT [dbo].[tblEquipment] ([Id], [CategoryId], [ClassroomId], [Position], [Status], [TimeRemain], [Name], [SerialNumber]) VALUES (96, 3, 1035, N'[3]       ', 1, 0, NULL, NULL)
INSERT [dbo].[tblEquipment] ([Id], [CategoryId], [ClassroomId], [Position], [Status], [TimeRemain], [Name], [SerialNumber]) VALUES (97, 2, 1035, N'[2]       ', 1, 0, NULL, NULL)
INSERT [dbo].[tblEquipment] ([Id], [CategoryId], [ClassroomId], [Position], [Status], [TimeRemain], [Name], [SerialNumber]) VALUES (98, 1, 1036, N'[1]       ', 1, 0, NULL, NULL)
INSERT [dbo].[tblEquipment] ([Id], [CategoryId], [ClassroomId], [Position], [Status], [TimeRemain], [Name], [SerialNumber]) VALUES (99, 3, 1036, N'[3]       ', 1, 0, NULL, NULL)
INSERT [dbo].[tblEquipment] ([Id], [CategoryId], [ClassroomId], [Position], [Status], [TimeRemain], [Name], [SerialNumber]) VALUES (100, 3, 1036, N'[3]       ', 1, 0, NULL, NULL)
INSERT [dbo].[tblEquipment] ([Id], [CategoryId], [ClassroomId], [Position], [Status], [TimeRemain], [Name], [SerialNumber]) VALUES (101, 2, 1036, N'[2]       ', 1, 0, NULL, NULL)
SET IDENTITY_INSERT [dbo].[tblEquipment] OFF
SET IDENTITY_INSERT [dbo].[tblRole] ON 

INSERT [dbo].[tblRole] ([Id], [Name]) VALUES (1, N'Admin')
INSERT [dbo].[tblRole] ([Id], [Name]) VALUES (2, N'Staff')
INSERT [dbo].[tblRole] ([Id], [Name]) VALUES (3, N'Teacher')
SET IDENTITY_INSERT [dbo].[tblRole] OFF
INSERT [dbo].[tblUser] ([Username], [RoleId], [Password], [Status]) VALUES (N'admin', 1, N'123456', 1)
INSERT [dbo].[tblUser] ([Username], [RoleId], [Password], [Status]) VALUES (N'BaoNQ', 3, N'123456', 1)
INSERT [dbo].[tblUser] ([Username], [RoleId], [Password], [Status]) VALUES (N'Gerardo', 3, N'123456', 1)
INSERT [dbo].[tblUser] ([Username], [RoleId], [Password], [Status]) VALUES (N'hungtv', 3, N'123456', 1)
INSERT [dbo].[tblUser] ([Username], [RoleId], [Password], [Status]) VALUES (N'LienTTH', 3, N'123456', 1)
INSERT [dbo].[tblUser] ([Username], [RoleId], [Password], [Status]) VALUES (N'PhuongLHK', 3, N'123456', 1)
INSERT [dbo].[tblUser] ([Username], [RoleId], [Password], [Status]) VALUES (N'staff', 2, N'123456', 1)
INSERT [dbo].[tblUser] ([Username], [RoleId], [Password], [Status]) VALUES (N'SuTV', 3, N'123456', 1)
INSERT [dbo].[tblUser] ([Username], [RoleId], [Password], [Status]) VALUES (N'teacher', 3, N'123456', 1)
INSERT [dbo].[tblUser] ([Username], [RoleId], [Password], [Status]) VALUES (N'VinhDP', 3, N'123456', 1)
INSERT [dbo].[tblUserInfo] ([Username], [FullName], [Phone], [LastLogin]) VALUES (N'staff', N'Doan Nguyen', N'01666815718', CAST(N'2015-01-06 00:00:00.000' AS DateTime))
INSERT [dbo].[tblUserInfo] ([Username], [FullName], [Phone], [LastLogin]) VALUES (N'teacher', N'Minh Chi', N'1666812243', CAST(N'2015-05-15 00:00:00.000' AS DateTime))
SET IDENTITY_INSERT [dbo].[tblSchedule] ON 

INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (6, N'teacher', 1034, 25, NULL, CAST(N'06:00:00' AS Time), 5, CAST(N'2015-05-05' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (8, N'PhuongLHK', 1034, 30, NULL, CAST(N'07:00:00' AS Time), 2, CAST(N'2015-05-12' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (9, N'SuTV', 1034, 30, NULL, CAST(N'07:00:00' AS Time), 1, CAST(N'2015-05-13' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (10, N'PhuongLHK', 1034, 30, NULL, CAST(N'07:00:00' AS Time), 2, CAST(N'2015-05-14' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (11, N'SuTV', 1034, 30, NULL, CAST(N'07:00:00' AS Time), 1, CAST(N'2015-05-15' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (12, N'PhuongLHK', 1034, 30, NULL, CAST(N'08:45:00' AS Time), 1, CAST(N'2015-05-13' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (13, N'PhuongLHK', 1034, 30, NULL, CAST(N'08:45:00' AS Time), 1, CAST(N'2015-05-15' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (14, N'SuTV', 1034, 30, NULL, CAST(N'10:30:00' AS Time), 1, CAST(N'2015-05-11' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (15, N'LienTTH', 1034, 30, NULL, CAST(N'10:30:00' AS Time), 1, CAST(N'2015-05-12' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (16, N'LienTTH', 1034, 30, NULL, CAST(N'10:30:00' AS Time), 1, CAST(N'2015-05-13' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (17, N'LienTTH', 1034, 30, NULL, CAST(N'10:30:00' AS Time), 1, CAST(N'2015-05-14' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (18, N'LienTTH', 1034, 30, NULL, CAST(N'10:30:00' AS Time), 1, CAST(N'2015-05-15' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (19, N'SuTV', 1034, 30, NULL, CAST(N'12:30:00' AS Time), 1, CAST(N'2015-05-11' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (20, N'PhuongLHK', 1034, 30, NULL, CAST(N'12:30:00' AS Time), 1, CAST(N'2015-05-12' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (21, N'Gerardo', 1034, 30, NULL, CAST(N'12:30:00' AS Time), 2, CAST(N'2015-05-13' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (22, N'PhuongLHK', 1034, 30, NULL, CAST(N'12:30:00' AS Time), 1, CAST(N'2015-05-14' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (23, N'Gerardo', 1034, 30, NULL, CAST(N'12:30:00' AS Time), 1, CAST(N'2015-05-15' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (24, N'VinhDP', 1034, 30, NULL, CAST(N'14:15:00' AS Time), 1, CAST(N'2015-05-11' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (25, N'VinhDP', 1034, 30, NULL, CAST(N'14:15:00' AS Time), 1, CAST(N'2015-05-12' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (26, N'VinhDP', 1034, 30, NULL, CAST(N'14:15:00' AS Time), 1, CAST(N'2015-05-14' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (27, N'VinhDP', 1034, 30, NULL, CAST(N'14:15:00' AS Time), 2, CAST(N'2015-05-15' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (28, N'BaoNQ', 1034, 30, NULL, CAST(N'16:00:00' AS Time), 1, CAST(N'2015-05-11' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (29, N'BaoNQ', 1034, 30, NULL, CAST(N'16:00:00' AS Time), 1, CAST(N'2015-05-12' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (30, N'BaoNQ', 1034, 30, NULL, CAST(N'16:00:00' AS Time), 1, CAST(N'2015-05-13' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (31, N'BaoNQ', 1034, 30, NULL, CAST(N'16:00:00' AS Time), 1, CAST(N'2015-05-14' AS Date))
INSERT [dbo].[tblSchedule] ([Id], [Username], [ClassroomId], [NumberOfStudents], [Note], [TimeFrom], [Slots], [Date]) VALUES (33, N'hungtv', 1034, 25, NULL, CAST(N'07:00:00' AS Time), 2, CAST(N'2015-05-11' AS Date))
SET IDENTITY_INSERT [dbo].[tblSchedule] OFF
