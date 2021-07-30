USE [master]
GO
/****** Object:  Database [SE140633_SE010D_P0016]    Script Date: 30/07/2021 12:11:12 CH ******/
CREATE DATABASE [SE140633_SE010D_P0016]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'SE140633_SE010D_P0016', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\SE140633_SE010D_P0016.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'SE140633_SE010D_P0016_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\SE140633_SE010D_P0016_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [SE140633_SE010D_P0016].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET ARITHABORT OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET  DISABLE_BROKER 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET RECOVERY FULL 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET  MULTI_USER 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET DB_CHAINING OFF 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'SE140633_SE010D_P0016', N'ON'
GO
USE [SE140633_SE010D_P0016]
GO
/****** Object:  Table [dbo].[tblAccountVerifications]    Script Date: 30/07/2021 12:11:12 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblAccountVerifications](
	[VerificationCode] [varchar](4) NOT NULL,
	[userID] [varchar](60) NULL,
	[VerificationDate] [datetime] NOT NULL CONSTRAINT [DF_tblAccountVerifications_VerificationDate]  DEFAULT (getdate()),
 CONSTRAINT [PK_tblAccountVerifications_1] PRIMARY KEY CLUSTERED 
(
	[VerificationDate] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblRequests]    Script Date: 30/07/2021 12:11:12 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblRequests](
	[requestID] [smallint] IDENTITY(1,1) NOT NULL,
	[requestUserID] [varchar](60) NULL,
	[requestResource] [varchar](15) NULL,
	[requestDate] [datetime] NULL CONSTRAINT [DF_tblRequests_requestDate]  DEFAULT (getdate()),
	[status] [varchar](10) NULL CONSTRAINT [DF_tblRequests_status]  DEFAULT ('New'),
	[requestContent] [varchar](255) NULL,
	[isProcess] [bit] NOT NULL CONSTRAINT [DF_tblRequests_isProcess]  DEFAULT ((0)),
 CONSTRAINT [PK_tblRequests] PRIMARY KEY CLUSTERED 
(
	[requestID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblResourceCategory]    Script Date: 30/07/2021 12:11:12 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblResourceCategory](
	[categoryID] [varchar](10) NOT NULL,
	[categoryName] [varchar](30) NULL,
 CONSTRAINT [PK_tblResourceCategory] PRIMARY KEY CLUSTERED 
(
	[categoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblResources]    Script Date: 30/07/2021 12:11:12 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblResources](
	[resourceID] [varchar](15) NOT NULL,
	[resourceName] [varchar](30) NULL,
	[resourceDirectory] [text] NULL,
	[resourceCataegory] [varchar](10) NULL,
 CONSTRAINT [PK_tblResources] PRIMARY KEY CLUSTERED 
(
	[resourceID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblRoles]    Script Date: 30/07/2021 12:11:12 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblRoles](
	[roleID] [varchar](10) NOT NULL,
	[roleName] [varchar](20) NULL,
	[isManager] [bit] NULL,
 CONSTRAINT [PK_tblRole] PRIMARY KEY CLUSTERED 
(
	[roleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblUserResource]    Script Date: 30/07/2021 12:11:12 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblUserResource](
	[usingDate] [date] NULL DEFAULT (getdate()),
	[requestUserID] [varchar](60) NOT NULL,
	[resourceID] [varchar](15) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[requestUserID] ASC,
	[resourceID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblUsers]    Script Date: 30/07/2021 12:11:12 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblUsers](
	[userID] [varchar](60) NOT NULL,
	[password] [varchar](30) NULL,
	[fullName] [varchar](40) NULL,
	[phone] [varchar](20) NULL,
	[createDate] [date] NULL CONSTRAINT [DF_tblUsers_createDate]  DEFAULT (getdate()),
	[address] [text] NULL,
	[status] [bit] NULL CONSTRAINT [DF_tblUsers_status]  DEFAULT ((0)),
	[roleID] [varchar](10) NULL CONSTRAINT [DF_tblUsers_roleID]  DEFAULT ('role03'),
 CONSTRAINT [PK_tblUsers] PRIMARY KEY CLUSTERED 
(
	[userID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[tblAccountVerifications]  WITH CHECK ADD  CONSTRAINT [FK_tblAccountVerifications_tblUsers] FOREIGN KEY([userID])
REFERENCES [dbo].[tblUsers] ([userID])
GO
ALTER TABLE [dbo].[tblAccountVerifications] CHECK CONSTRAINT [FK_tblAccountVerifications_tblUsers]
GO
ALTER TABLE [dbo].[tblRequests]  WITH CHECK ADD  CONSTRAINT [FK_tblRequests_tblResources] FOREIGN KEY([requestResource])
REFERENCES [dbo].[tblResources] ([resourceID])
GO
ALTER TABLE [dbo].[tblRequests] CHECK CONSTRAINT [FK_tblRequests_tblResources]
GO
ALTER TABLE [dbo].[tblRequests]  WITH CHECK ADD  CONSTRAINT [FK_tblRequests_tblUsers] FOREIGN KEY([requestUserID])
REFERENCES [dbo].[tblUsers] ([userID])
GO
ALTER TABLE [dbo].[tblRequests] CHECK CONSTRAINT [FK_tblRequests_tblUsers]
GO
ALTER TABLE [dbo].[tblResources]  WITH CHECK ADD  CONSTRAINT [FK_tblResources_tblResourceCategory] FOREIGN KEY([resourceCataegory])
REFERENCES [dbo].[tblResourceCategory] ([categoryID])
GO
ALTER TABLE [dbo].[tblResources] CHECK CONSTRAINT [FK_tblResources_tblResourceCategory]
GO
ALTER TABLE [dbo].[tblUserResource]  WITH CHECK ADD FOREIGN KEY([requestUserID])
REFERENCES [dbo].[tblUsers] ([userID])
GO
ALTER TABLE [dbo].[tblUserResource]  WITH CHECK ADD FOREIGN KEY([resourceID])
REFERENCES [dbo].[tblResources] ([resourceID])
GO
ALTER TABLE [dbo].[tblUsers]  WITH CHECK ADD  CONSTRAINT [FK_tblUsers_tblRoles] FOREIGN KEY([roleID])
REFERENCES [dbo].[tblRoles] ([roleID])
GO
ALTER TABLE [dbo].[tblUsers] CHECK CONSTRAINT [FK_tblUsers_tblRoles]
GO
USE [master]
GO
ALTER DATABASE [SE140633_SE010D_P0016] SET  READ_WRITE 
GO
