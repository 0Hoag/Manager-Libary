USE [master]
GO
/****** Object:  Database [QL_ThuVien]    Script Date: 7/30/2024 9:15:49 PM ******/
CREATE DATABASE [QL_ThuVien]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'QL_ThuVien', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.DAFPL\MSSQL\DATA\QL_ThuVien.mdf' , SIZE = 73728KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'QL_ThuVien_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.DAFPL\MSSQL\DATA\QL_ThuVien_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [QL_ThuVien] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [QL_ThuVien].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [QL_ThuVien] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [QL_ThuVien] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [QL_ThuVien] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [QL_ThuVien] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [QL_ThuVien] SET ARITHABORT OFF 
GO
ALTER DATABASE [QL_ThuVien] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [QL_ThuVien] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [QL_ThuVien] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [QL_ThuVien] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [QL_ThuVien] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [QL_ThuVien] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [QL_ThuVien] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [QL_ThuVien] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [QL_ThuVien] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [QL_ThuVien] SET  DISABLE_BROKER 
GO
ALTER DATABASE [QL_ThuVien] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [QL_ThuVien] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [QL_ThuVien] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [QL_ThuVien] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [QL_ThuVien] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [QL_ThuVien] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [QL_ThuVien] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [QL_ThuVien] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [QL_ThuVien] SET  MULTI_USER 
GO
ALTER DATABASE [QL_ThuVien] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [QL_ThuVien] SET DB_CHAINING OFF 
GO
ALTER DATABASE [QL_ThuVien] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [QL_ThuVien] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [QL_ThuVien] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [QL_ThuVien] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [QL_ThuVien] SET QUERY_STORE = ON
GO
ALTER DATABASE [QL_ThuVien] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [QL_ThuVien]
GO
/****** Object:  Table [dbo].[DOCGIA]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DOCGIA](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[HOTEN] [nvarchar](50) NULL,
	[SDT] [varchar](10) NULL,
	[EMAIL] [varchar](50) NULL,
	[NGAYTAO] [date] NULL,
 CONSTRAINT [PK_DOCGIA] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PHIEUMUON]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PHIEUMUON](
	[MAPHIEU] [int] IDENTITY(1,1) NOT NULL,
	[MADOCGIA] [int] NOT NULL,
	[SONGAYMUON] [int] NULL,
	[SOLUONGMUON] [int] NULL,
	[NGAYMUON] [date] NULL,
	[NGAYTRADUTINH] [date] NULL,
	[TRANGTHAITRA] [nvarchar](50) NULL,
 CONSTRAINT [PK_PHIEUMUON_1] PRIMARY KEY CLUSTERED 
(
	[MAPHIEU] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SACHMUON]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SACHMUON](
	[maPhieuMuon] [int] NOT NULL,
	[maSach] [int] NOT NULL,
	[soLuongBiMuon] [int] NOT NULL,
 CONSTRAINT [PK_SACHMUON] PRIMARY KEY CLUSTERED 
(
	[maSach] ASC,
	[maPhieuMuon] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PHIEUTRA]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PHIEUTRA](
	[MAPHIEUTRA] [int] IDENTITY(1,1) NOT NULL,
	[MAPHIEUMUON] [int] NOT NULL,
	[SOLUONGTRA] [int] NULL,
	[NGAYTAO] [date] NULL,
 CONSTRAINT [PK_PHIEUTRA] PRIMARY KEY CLUSTERED 
(
	[MAPHIEUTRA] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[v_DocGia]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE view [dbo].[v_DocGia]
as

SELECT 
    dg.ID, 
    dg.HOTEN, 
    dg.SDT, 
    dg.EMAIL, 
	dg.NGAYTAO,
	SUM(CASE WHEN pm.TRANGTHAITRA = N'Đang mượn' THEN sm.soLuongBiMuon ELSE 0 END) AS SoSachDangMuon,
    SUM(CASE WHEN pm.TRANGTHAITRA = N'Đang mượn' AND GETDATE() > pm.NGAYTRADUTINH THEN sm.soLuongBiMuon ELSE 0 END) AS SoSachQuaHan

    
FROM 
    DOCGIA dg
LEFT JOIN 
    PHIEUMUON pm ON dg.ID = pm.MADOCGIA
LEFT JOIN 
    PHIEUTRA pt ON pm.MAPHIEU = pt.MAPHIEUMUON
LEFT JOIN 
    SACHMUON sm ON pm.MAPHIEU = sm.MAPHIEUMUON
GROUP BY 
    dg.ID, dg.HOTEN, dg.SDT, dg.EMAIL,dg.NGAYTAO;
GO
/****** Object:  Table [dbo].[ACCOUNT]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ACCOUNT](
	[username] [varchar](10) NOT NULL,
	[pass] [varchar](50) NULL,
	[role] [varchar](10) NULL,
	[id] [int] NULL,
 CONSTRAINT [PK_ACCOUNT] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NXB]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NXB](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](50) NULL,
 CONSTRAINT [PK_NXB] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PHIEUPHAT]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PHIEUPHAT](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[MADOCGIA] [int] NOT NULL,
	[NOIDUNGPHAT] [nvarchar](max) NULL,
	[PHIPHAT] [int] NULL,
	[NGAYTAO] [date] NULL,
 CONSTRAINT [PK_PHIEUPHAT] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SACH]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SACH](
	[MaSach] [int] IDENTITY(1,1) NOT NULL,
	[tenSach] [nvarchar](100) NULL,
	[tacGia] [int] NOT NULL,
	[nxb] [int] NOT NULL,
	[theLoai] [int] NOT NULL,
	[giaSach] [int] NULL,
	[tongBanSao] [int] NULL,
	[soBanSaoHienCo] [int] NULL,
	[hinh] [nchar](250) NULL,
 CONSTRAINT [PK_SACH] PRIMARY KEY CLUSTERED 
(
	[MaSach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TACGIA]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TACGIA](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[TEN] [nvarchar](50) NULL,
 CONSTRAINT [PK_TACGIA] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[THELOAI]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[THELOAI](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[TEN] [nvarchar](max) NULL,
 CONSTRAINT [PK_THELOAI] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'a         ', N'123', N'kh', 11)
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'admin     ', N'adm', N'admin', 1)
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'b         ', N'b                                                 ', N'kh', 13)
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'c         ', N'c                                                 ', N'kh', 14)
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'hoag      ', N'123                                               ', N'kh', 18)
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'hoag123   ', N'123', N'kh', 19)
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'tien123   ', N'tien123                                           ', N'kh', 20)
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'user0002  ', N'123', N'kh        ', 2)
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'user0003  ', N'password3                                         ', N'kh        ', 3)
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'user0004  ', N'password4                                         ', N'kh        ', 4)
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'user0005  ', N'password5                                         ', N'kh        ', 5)
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'user0006  ', N'password6                                         ', N'kh        ', 6)
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'user0007  ', N'password7                                         ', N'kh        ', 7)
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'user0008  ', N'password8                                         ', N'kh        ', 8)
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'user0009  ', N'password9                                         ', N'kh        ', 9)
INSERT [dbo].[ACCOUNT] ([username], [pass], [role], [id]) VALUES (N'user0010  ', N'password10                                        ', N'kh        ', 10)
GO
SET IDENTITY_INSERT [dbo].[DOCGIA] ON 

INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (1, N'Trần Văn A', N'0123456789', N'vtt2005.hcm@gmail.com                             ', CAST(N'2024-07-13' AS Date))
INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (2, N'Nguyễn Thị B', N'0987654321', N'nguyenthb@example.com                             ', CAST(N'2024-07-13' AS Date))
INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (3, N'Lê Văn C', N'0912345678', N'levanc@example.com                                ', CAST(N'2024-07-13' AS Date))
INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (4, N'Phạm Thị D', N'0901234567', N'phamthid@example.com                              ', CAST(N'2024-07-13' AS Date))
INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (5, N'Hoàng Văn E', N'0934567890', N'hoangvane@example.com                             ', CAST(N'2024-07-13' AS Date))
INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (6, N'Võ Thị F', N'0923456789', N'vothif@example.com                                ', CAST(N'2024-07-13' AS Date))
INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (7, N'Đặng Văn G', N'0956789123', N'dangvang@example.com                              ', CAST(N'2024-07-13' AS Date))
INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (8, N'Bùi Thị H', N'0967891234', N'buithih@example.com                               ', CAST(N'2024-07-13' AS Date))
INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (9, N'Vũ Văn I', N'0978912345', N'vuvani@example.com                                ', CAST(N'2024-07-13' AS Date))
INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (10, N'Trịnh Thị K', N'0989123456', N'trinhthik@example.com                             ', CAST(N'2024-07-13' AS Date))
INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (11, N'a', N's         ', N'a                                                 ', CAST(N'2024-07-18' AS Date))
INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (12, N'a', N'a         ', N'a                                                 ', CAST(N'2024-07-18' AS Date))
INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (17, N'afdsf', N'sfsdf', N'afsdaf', CAST(N'2024-07-26' AS Date))
INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (18, N'Hoag', N'218937123 ', N'hoag@gmail.com                                    ', CAST(N'2024-07-27' AS Date))
INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (19, N'hoag123', N'2316237   ', N'zzeross2005@gmail.com                             ', CAST(N'2024-07-27' AS Date))
INSERT [dbo].[DOCGIA] ([ID], [HOTEN], [SDT], [EMAIL], [NGAYTAO]) VALUES (20, N'tien', N'0123456   ', N'tien@gmail.com                                    ', CAST(N'2024-07-27' AS Date))
SET IDENTITY_INSERT [dbo].[DOCGIA] OFF
GO
SET IDENTITY_INSERT [dbo].[NXB] ON 

INSERT [dbo].[NXB] ([id], [ten]) VALUES (1, N'Nhà xuất bản Kim Đồng')
INSERT [dbo].[NXB] ([id], [ten]) VALUES (2, N'Nhà xuất bản Trẻ')
INSERT [dbo].[NXB] ([id], [ten]) VALUES (3, N'Nhà xuất bản Văn Học')
INSERT [dbo].[NXB] ([id], [ten]) VALUES (4, N'Nhà xuất bản Giáo Dục')
INSERT [dbo].[NXB] ([id], [ten]) VALUES (5, N'Nhà xuất bản Phụ Nữ')
INSERT [dbo].[NXB] ([id], [ten]) VALUES (7, N'Nhà xuât bản Nụ Cười Mới')
SET IDENTITY_INSERT [dbo].[NXB] OFF
GO
SET IDENTITY_INSERT [dbo].[PHIEUMUON] ON 

INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (1, 2, 30, 3, CAST(N'2024-07-13' AS Date), CAST(N'2024-08-12' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (2, 3, 30, 4, CAST(N'2024-07-13' AS Date), CAST(N'2024-08-12' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (3, 4, 30, 3, CAST(N'2024-07-13' AS Date), CAST(N'2024-08-12' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (4, 5, 30, 3, CAST(N'2024-07-13' AS Date), CAST(N'2024-08-12' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (5, 6, 30, 4, CAST(N'2024-07-13' AS Date), CAST(N'2024-08-12' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (6, 7, 30, 3, CAST(N'2024-07-13' AS Date), CAST(N'2024-08-12' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (7, 8, 30, 4, CAST(N'2024-07-13' AS Date), CAST(N'2024-08-12' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (8, 9, 30, 5, CAST(N'2024-07-13' AS Date), CAST(N'2024-08-12' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (9, 10, 30, 13, CAST(N'2024-07-13' AS Date), CAST(N'2024-08-12' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (10, 9, 30, 6, CAST(N'2024-07-13' AS Date), CAST(N'2024-08-12' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (15, 1, 30, 2, CAST(N'2024-07-22' AS Date), CAST(N'2024-08-21' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (16, 1, 30, 2, CAST(N'2024-07-22' AS Date), CAST(N'2024-08-21' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (17, 1, 30, 2, CAST(N'2024-07-22' AS Date), CAST(N'2024-08-21' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (20, 1, 30, 2, CAST(N'2024-07-22' AS Date), CAST(N'2024-08-21' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (21, 1, 30, 2, CAST(N'2024-07-22' AS Date), CAST(N'2024-08-21' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (22, 1, 1, 5, CAST(N'2024-07-24' AS Date), CAST(N'2024-07-25' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (23, 1, 1, 3, CAST(N'2024-07-24' AS Date), CAST(N'2024-07-25' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (29, 1, 1, 3, CAST(N'2024-07-25' AS Date), CAST(N'2024-07-26' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (30, 2, 2, 1, CAST(N'2024-07-25' AS Date), CAST(N'2024-07-27' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (31, 3, 3, 2, CAST(N'2024-07-25' AS Date), CAST(N'2024-07-28' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (32, 4, 30, 2, CAST(N'2024-07-25' AS Date), CAST(N'2024-08-24' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (33, 1, 30, 3, CAST(N'2024-07-25' AS Date), CAST(N'2024-08-24' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (34, 1, 30, 2, CAST(N'2024-07-25' AS Date), CAST(N'2024-08-24' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (35, 3, 30, 3, CAST(N'2024-07-26' AS Date), CAST(N'2024-08-25' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (37, 17, 2, 1, CAST(N'2024-07-26' AS Date), CAST(N'2024-07-28' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (38, 12, 3, 2, CAST(N'2024-07-26' AS Date), CAST(N'2024-07-29' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (40, 1, 1, 3, CAST(N'2024-07-26' AS Date), CAST(N'2024-07-27' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (41, 1, 1, 5, CAST(N'2024-07-26' AS Date), CAST(N'2024-07-27' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (46, 1, 1, 3, CAST(N'2024-07-27' AS Date), CAST(N'2024-07-28' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (47, 1, 30, 3, CAST(N'2024-07-27' AS Date), CAST(N'2024-07-28' AS Date), N'Đang mượn')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (48, 11, 30, 2, CAST(N'2024-07-27' AS Date), CAST(N'2024-08-26' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (49, 11, 30, 2, CAST(N'2024-07-27' AS Date), CAST(N'2024-08-26' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (50, 3, 3, 0, CAST(N'2024-07-27' AS Date), CAST(N'2024-07-30' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (51, 11, 12, 2, CAST(N'2024-07-27' AS Date), CAST(N'2024-08-08' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (52, 20, 123, 4, CAST(N'2024-07-27' AS Date), CAST(N'2024-11-27' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (53, 2, 30, 1, CAST(N'2024-07-29' AS Date), CAST(N'2024-08-28' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (54, 1, 30, 0, CAST(N'2024-07-30' AS Date), CAST(N'2024-08-29' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (55, 5, 30, 0, CAST(N'2024-07-30' AS Date), CAST(N'2024-08-29' AS Date), N'Đã trả')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (56, 3, 30, 1, CAST(N'2024-07-30' AS Date), CAST(N'2024-08-29' AS Date), N'Đang mượn')
INSERT [dbo].[PHIEUMUON] ([MAPHIEU], [MADOCGIA], [SONGAYMUON], [SOLUONGMUON], [NGAYMUON], [NGAYTRADUTINH], [TRANGTHAITRA]) VALUES (57, 2, 30, 0, CAST(N'2024-07-30' AS Date), CAST(N'2024-08-29' AS Date), N'Đã trả')
SET IDENTITY_INSERT [dbo].[PHIEUMUON] OFF
GO
SET IDENTITY_INSERT [dbo].[PHIEUTRA] ON 

INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (1, 1, 3, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (2, 2, 4, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (3, 3, 3, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (4, 4, 3, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (5, 5, 4, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (6, 6, 3, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (7, 7, 4, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (8, 8, 4, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (9, 9, 4, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (10, 9, 4, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (11, 9, 3, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (12, 9, 1, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (13, 8, 1, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (14, 10, 1, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (15, 10, 1, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (16, 10, 1, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (17, 10, 1, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (18, 10, 2, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (19, 9, 1, CAST(N'2024-07-13' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (20, 15, 1, CAST(N'2024-07-22' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (21, 16, 1, CAST(N'2024-07-22' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (22, 15, 1, CAST(N'2024-07-22' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (23, 16, 1, CAST(N'2024-07-22' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (24, 17, 1, CAST(N'2024-07-22' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (25, 17, 1, CAST(N'2024-07-22' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (26, 21, 2, CAST(N'2024-07-22' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (27, 20, 2, CAST(N'2024-07-22' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (30, 22, 1, CAST(N'2024-07-25' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (31, 23, 1, CAST(N'2024-07-25' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (32, 22, 1, CAST(N'2024-07-25' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (33, 23, 1, CAST(N'2024-07-25' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (34, 22, 1, CAST(N'2024-07-25' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (35, 23, 1, CAST(N'2024-07-25' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (36, 22, 1, CAST(N'2024-07-25' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (37, 22, 1, CAST(N'2024-07-25' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (38, 29, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (39, 30, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (40, 31, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (41, 32, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (42, 34, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (43, 35, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (44, 33, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (45, 34, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (46, 35, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (47, 37, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (48, 31, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (49, 35, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (50, 38, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (51, 29, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (52, 32, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (53, 33, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (54, 33, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (55, 38, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (56, 29, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (57, 41, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (58, 40, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (59, 41, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (60, 40, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (61, 41, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (62, 41, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (63, 40, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (64, 41, 1, CAST(N'2024-07-26' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (65, 52, 1, CAST(N'2024-07-29' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (66, 52, 0, CAST(N'2024-07-29' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (69, 48, 0, CAST(N'2024-07-29' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (70, 47, 0, CAST(N'2024-07-29' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (71, 49, 2, CAST(N'2024-07-29' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (72, 53, 1, CAST(N'2024-07-30' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (73, 53, 1, CAST(N'2024-07-30' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (74, 53, 1, CAST(N'2024-07-30' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (75, 55, 1, CAST(N'2024-07-30' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (76, 54, 1, CAST(N'2024-07-30' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (77, 50, 1, CAST(N'2024-07-30' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (78, 56, 1, CAST(N'2024-07-30' AS Date))
INSERT [dbo].[PHIEUTRA] ([MAPHIEUTRA], [MAPHIEUMUON], [SOLUONGTRA], [NGAYTAO]) VALUES (79, 57, 2, CAST(N'2024-07-30' AS Date))
SET IDENTITY_INSERT [dbo].[PHIEUTRA] OFF
GO
SET IDENTITY_INSERT [dbo].[SACH] ON 

INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (1, N'Nác Bướm', 1, 1, 1, 50000, 6, 3, N'hinh1.jpg                                                                                                                                                                                                                                                 ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (2, N'Harry Potter và Hòn Đá Phù Thủy', 2, 2, 4, 150000, 5, 2, N'hinh2.jpg                                                                                                                                                                                                                                                 ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (3, N'Nguồn gốc của các loài', 3, 3, 3, 80000, 7, 4, N'hinh3.jpg                                                                                                                                                                                                                                                 ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (4, N'The Shining', 4, 4, 2, 120000, 5, 5, N'hinh4.jpg                                                                                                                                                                                                                                                 ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (5, N'Stalingrad', 5, 5, 3, 90000, 7, 5, N'hinh5.jpg                                                                                                                                                                                                                                                 ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (6, N'Les Misérables', 6, 1, 3, 130000, 6, 4, N'hinh6.jpg                                                                                                                                                                                                                                                 ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (7, N'Kafka on the Shore', 7, 2, 4, 140000, 4, 2, N'hinh7.jpg                                                                                                                                                                                                                                                 ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (8, N'One Hundred Years of Solitude', 8, 3, 3, 125000, 5, 4, N'hinh8.jpg                                                                                                                                                                                                                                                 ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (9, N'1984', 9, 4, 4, 110000, 5, 4, N'hinh9.jpg                                                                                                                                                                                                                                                 ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (10, N'Crime and Punishment', 10, 5, 3, 115000, 6, 6, N'hinh10.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (11, N'Totto-chan: The Little Girl at the Window', 1, 2, 1, 95000, 6, 6, N'hinh11.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (12, N'Carrie', 4, 3, 2, 100000, 6, 6, N'hinh12.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (13, N'Emma', 5, 4, 3, 105000, 6, 5, N'hinh13.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (14, N'Notre-Dame de Paris', 6, 5, 3, 135000, 5, 5, N'hinh14.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (15, N'Norwegian Wood', 7, 1, 4, 145000, 5, 5, N'hinh15.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (16, N'Love in the Time of Cholera', 8, 2, 5, 125000, 5, 4, N'hinh16.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (17, N'Animal Farm', 9, 3, 4, 95000, 6, 6, N'hinh17.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (18, N'The Brothers Karamazov', 10, 4, 3, 115000, 5, 5, N'hinh18.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (19, N'Dế Mèn Phiêu Lưu Ký', 1, 5, 1, 85000, 6, 6, N'hinh19.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (20, N'Misery', 4, 1, 2, 120000, 6, 6, N'hinh20.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (21, N'Sense and Sensibility', 5, 2, 3, 105000, 5, 4, N'hinh21.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (22, N'The Hunchback of Notre-Dame', 6, 3, 3, 135000, 6, 4, N'hinh22.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (23, N'1Q84', 7, 4, 4, 150000, 6, 6, N'hinh23.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (24, N'Chronicle of a Death Foretold', 8, 5, 5, 125000, 5, 5, N'hinh24.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (25, N'Homage to Catalonia', 9, 1, 4, 110000, 5, 5, N'hinh25.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (26, N'The Idiot', 10, 2, 3, 115000, 5, 5, N'hinh26.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (27, N'Áo Trắng', 1, 3, 1, 60000, 6, 6, N'hinh27.jpg                                                                                                                                                                                                                                                ')
INSERT [dbo].[SACH] ([MaSach], [tenSach], [tacGia], [nxb], [theLoai], [giaSach], [tongBanSao], [soBanSaoHienCo], [hinh]) VALUES (28, N'Doctor Sleep', 4, 4, 2, 130000, 5, 5, N'hinh28.jpg                                                                                                                                                                                                                                                ')
SET IDENTITY_INSERT [dbo].[SACH] OFF
GO
INSERT [dbo].[SACHMUON] ([maPhieuMuon], [maSach], [soLuongBiMuon]) VALUES (47, 2, 1)
INSERT [dbo].[SACHMUON] ([maPhieuMuon], [maSach], [soLuongBiMuon]) VALUES (47, 7, 1)
INSERT [dbo].[SACHMUON] ([maPhieuMuon], [maSach], [soLuongBiMuon]) VALUES (56, 22, 1)
GO
SET IDENTITY_INSERT [dbo].[TACGIA] ON 

INSERT [dbo].[TACGIA] ([ID], [TEN]) VALUES (1, N'Nguyễn Nhật Ánh')
INSERT [dbo].[TACGIA] ([ID], [TEN]) VALUES (2, N'J.K. Rowling')
INSERT [dbo].[TACGIA] ([ID], [TEN]) VALUES (3, N'Nguyễn Du')
INSERT [dbo].[TACGIA] ([ID], [TEN]) VALUES (4, N'Stephen King')
INSERT [dbo].[TACGIA] ([ID], [TEN]) VALUES (5, N'Jane Austen')
INSERT [dbo].[TACGIA] ([ID], [TEN]) VALUES (6, N'Victor Hugo')
INSERT [dbo].[TACGIA] ([ID], [TEN]) VALUES (7, N'Haruki Murakami')
INSERT [dbo].[TACGIA] ([ID], [TEN]) VALUES (8, N'Gabriel Garcia Marquez')
INSERT [dbo].[TACGIA] ([ID], [TEN]) VALUES (9, N'George Orwell')
INSERT [dbo].[TACGIA] ([ID], [TEN]) VALUES (10, N'Fyodor Dostoevsky')
INSERT [dbo].[TACGIA] ([ID], [TEN]) VALUES (12, N'Tố Hữu')
INSERT [dbo].[TACGIA] ([ID], [TEN]) VALUES (13, N'Hồ Chí Minh')
SET IDENTITY_INSERT [dbo].[TACGIA] OFF
GO
SET IDENTITY_INSERT [dbo].[THELOAI] ON 

INSERT [dbo].[THELOAI] ([ID], [TEN]) VALUES (1, N'Thiếu nhi')
INSERT [dbo].[THELOAI] ([ID], [TEN]) VALUES (2, N'Kinh dị')
INSERT [dbo].[THELOAI] ([ID], [TEN]) VALUES (3, N'Văn học cổ điển')
INSERT [dbo].[THELOAI] ([ID], [TEN]) VALUES (4, N'Khoa học viễn tưởng')
INSERT [dbo].[THELOAI] ([ID], [TEN]) VALUES (5, N'Tiểu thuyết lãng mạn')
SET IDENTITY_INSERT [dbo].[THELOAI] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_ACCOUNT]    Script Date: 7/30/2024 9:15:50 PM ******/
CREATE NONCLUSTERED INDEX [IX_ACCOUNT] ON [dbo].[ACCOUNT]
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[PHIEUMUON] ADD  CONSTRAINT [DF_PHIEUMUON_SOLUONGMUON]  DEFAULT ((30)) FOR [SOLUONGMUON]
GO
ALTER TABLE [dbo].[PHIEUMUON] ADD  CONSTRAINT [DF_PHIEUMUON_TRANGTHAITRA]  DEFAULT (N'Đang mượn') FOR [TRANGTHAITRA]
GO
ALTER TABLE [dbo].[PHIEUMUON]  WITH NOCHECK ADD  CONSTRAINT [FK_PHIEUMUON_DOCGIA] FOREIGN KEY([MADOCGIA])
REFERENCES [dbo].[DOCGIA] ([ID])
GO
ALTER TABLE [dbo].[PHIEUMUON] CHECK CONSTRAINT [FK_PHIEUMUON_DOCGIA]
GO
ALTER TABLE [dbo].[PHIEUPHAT]  WITH CHECK ADD  CONSTRAINT [FK_PHIEUPHAT_DOCGIA] FOREIGN KEY([MADOCGIA])
REFERENCES [dbo].[DOCGIA] ([ID])
GO
ALTER TABLE [dbo].[PHIEUPHAT] CHECK CONSTRAINT [FK_PHIEUPHAT_DOCGIA]
GO
ALTER TABLE [dbo].[PHIEUTRA]  WITH NOCHECK ADD  CONSTRAINT [FK_PHIEUTRA_PHIEUMUON] FOREIGN KEY([MAPHIEUMUON])
REFERENCES [dbo].[PHIEUMUON] ([MAPHIEU])
GO
ALTER TABLE [dbo].[PHIEUTRA] CHECK CONSTRAINT [FK_PHIEUTRA_PHIEUMUON]
GO
ALTER TABLE [dbo].[SACH]  WITH NOCHECK ADD  CONSTRAINT [FK_SACH_NXB] FOREIGN KEY([nxb])
REFERENCES [dbo].[NXB] ([id])
GO
ALTER TABLE [dbo].[SACH] CHECK CONSTRAINT [FK_SACH_NXB]
GO
ALTER TABLE [dbo].[SACH]  WITH NOCHECK ADD  CONSTRAINT [FK_SACH_TACGIA] FOREIGN KEY([tacGia])
REFERENCES [dbo].[TACGIA] ([ID])
GO
ALTER TABLE [dbo].[SACH] CHECK CONSTRAINT [FK_SACH_TACGIA]
GO
ALTER TABLE [dbo].[SACH]  WITH NOCHECK ADD  CONSTRAINT [FK_SACH_THELOAI] FOREIGN KEY([theLoai])
REFERENCES [dbo].[THELOAI] ([ID])
GO
ALTER TABLE [dbo].[SACH] CHECK CONSTRAINT [FK_SACH_THELOAI]
GO
ALTER TABLE [dbo].[SACHMUON]  WITH NOCHECK ADD  CONSTRAINT [FK_SACHMUON_PHIEUMUON] FOREIGN KEY([maPhieuMuon])
REFERENCES [dbo].[PHIEUMUON] ([MAPHIEU])
GO
ALTER TABLE [dbo].[SACHMUON] CHECK CONSTRAINT [FK_SACHMUON_PHIEUMUON]
GO
ALTER TABLE [dbo].[SACHMUON]  WITH NOCHECK ADD  CONSTRAINT [FK_SACHMUON_SACH] FOREIGN KEY([maSach])
REFERENCES [dbo].[SACH] ([MaSach])
GO
ALTER TABLE [dbo].[SACHMUON] CHECK CONSTRAINT [FK_SACHMUON_SACH]
GO
/****** Object:  StoredProcedure [dbo].[sp_insertAccount]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[sp_insertAccount](
	@userName nchar(10),
	@pass nchar(200),
	@hoten nvarchar(50),
	@sdt nchar(10),
	@email nchar(50)
)
as
begin
	declare @id int
	insert into DOCGIA
	values (@hoten, @sdt, @email,getdate())

	 set @id = SCOPE_IDENTITY()

	 insert into ACCOUNT
	 values (@userName, @pass, 'kh', @id)
	
end
GO
/****** Object:  StoredProcedure [dbo].[sp_insertDocGia]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[sp_insertDocGia]
@hoten nvarchar(50),
@sdt varchar(10),
@email varchar(50)
as
begin
insert into DOCGIA
values (@hoten, @sdt, @email, GETDATE())
end
GO
/****** Object:  StoredProcedure [dbo].[sp_insertPhieuTra]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_insertPhieuTra](
    @maPhieuMuon int,
    @soLuongTra int
)
AS
BEGIN
    -- Tạo phiếu trả
    INSERT INTO PHIEUTRA (MAPHIEUMUON, SOLUONGTRA, NGAYTAO)
    VALUES (@maPhieuMuon, @soLuongTra, getDate());

    -- Kiểm tra xem có cần tạo phiếu phạt hay không
    --IF @phieuPhat IS NOT NULL
    --BEGIN
    --    -- Tạo phiếu phạt
    --    EXEC TaoPhieuPhat @phieuPhat);
    --END
END
GO
/****** Object:  StoredProcedure [dbo].[sp_insertSachMuon]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[sp_insertSachMuon] (
  @maPhieuMuon int,
  @maSach int,
  @soLuongBiMuon int
)
AS
BEGIN

if not exists (select @maSach from SACHMUON where maPhieuMuon = @maPhieuMuon and maSach = @maSach)
begin
	INSERT INTO SACHMUON (
    maPhieuMuon,
    MASACH,
    SOLUONGBIMUON
  )
  VALUES (
    @maPhieuMuon,
    @maSach,
    @soLuongBiMuon
  );

	UPDATE SACH
	 SET soBanSaoHienCo = soBanSaoHienCo - @soLuongBiMuon
	 WHERE MaSach = @maSach
end
else
	begin
		update SACHMUON
		set soLuongBiMuon = @soLuongBiMuon
		where maSach = @maSach and maPhieuMuon = @maPhieuMuon

		UPDATE SACH
	 SET soBanSaoHienCo = soBanSaoHienCo + @soLuongBiMuon
	 WHERE MaSach = @maSach

	end
  
  
END
GO
/****** Object:  StoredProcedure [dbo].[sp_muonSach]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_muonSach] (
  @madocgia int,
  @songaymuon int,
  @trangthaitra nvarchar(50) ,
  @muonSach  varchar(1000)
)
AS
BEGIN
  
  BEGIN TRAN
  BEGIN TRY
	---Tạo phiếu mượn
	DECLARE @maPhieuMuon int;
	  INSERT INTO PHIEUMUON (
		MADOCGIA,
		SONGAYMUON,
		SOLUONGMUON,
		NGAYMUON,
		NGAYTRADUTINH,
		TRANGTHAITRA
	  )
	  VALUES (
		@madocgia,
		@songaymuon,
		0,
		getdate(),
		DATEADD(DAY,@songaymuon,getdate()),
		@trangthaitra
	  );

	  ---Lưu mã phiếu mượn vừa tạo
	 SET @maPhieuMuon = @@IDENTITY

	 --Tạo bảng tạm chứa mượn sách
	 SELECT * into #MuonSachTam
	FROM string_split(@muonSach, ';')

	--Duyệt qua từng dòng trong bảng tạm để bóc tách maSach, soLuongBiMuon
	DECLARE @value varchar(1000)
	DECLARE @maSach int, @soLuongBiMuon int

	DECLARE MuonSachCursor CURSOR FOR
	SELECT * FROM #MuonSachTam

	OPEN MuonSachCursor
	--Duyệt qua từng record trong bảng tạm
	FETCH NEXT FROM MuonSachCursor INTO @value

	--Kiểm tra xem còn dòng để duyệt nữa không
	WHILE @@FETCH_STATUS = 0
	BEGIN
		SELECT @maSach = CAST(SUBSTRING(@value, 0, CHARINDEX(',',@value)) AS int),
				@soLuongBiMuon = CAST(SUBSTRING(@value, CHARINDEX(',',@value) + 1, LEN(@value) - CHARINDEX(',', @value) ) AS int)

		---Chèn vào bảng sách mượn


				INSERT INTO SACHMUON (
				maPhieuMuon,
				MASACH,
				SOLUONGBIMUON
			  )
			  VALUES (
				@maPhieuMuon,
				@maSach,
				@soLuongBiMuon
			  );

				UPDATE SACH
				 SET soBanSaoHienCo = soBanSaoHienCo - @soLuongBiMuon
				 WHERE MaSach = @maSach
			
			


		--Lấy dòng kế
		FETCH NEXT FROM MuonSachCursor INTO @value
	END



	CLOSE MuonSachCursor
	DEALLOCATE MuonSachCursor

	COMMIT TRAN
  END TRY
  BEGIN CATCH
	ROLLBACK TRAN
  END CATCH

END
GO
/****** Object:  StoredProcedure [dbo].[sp_traSach]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[sp_traSach] (
	@maPhieuMuon int,
	@sachTra varchar(1000)
)
as
begin
	BEGIN TRAN
	BEGIN TRY
		DECLARE @maPhieuTra int
	--Tạo phiếu trả
	INSERT INTO PHIEUTRA
	VALUES (@maPhieuMuon, 0, GETDATE())

	--Lưu mã phiếu trả vừa tạo
	--set @maPhieuTra = @@IDENTITY

	--Tạo bảng sách trả tạm
		SELECT * INTO #TRASACHTAM
		FROM string_split(@sachTra, ';')

	--Duyệt qua từng dòng trong bảng tạm để bóc tách maSach, soLuongBiMuon
	DECLARE @value varchar(1000)
	DECLARE @maSach int, @soLuongBiMuon int

	DECLARE TraSachhCursor CURSOR FOR
	SELECT * FROM #TRASACHTAM

	OPEN TraSachhCursor
	--Duyệt qua từng record trong bảng tạm
	FETCH NEXT FROM TraSachhCursor INTO @value

	--Kiểm tra xem còn dòng để duyệt nữa không
	WHILE @@FETCH_STATUS = 0
	BEGIN
		SELECT @maSach = CAST(SUBSTRING(@value, 0, CHARINDEX(',',@value)) AS int),
				@soLuongBiMuon = CAST(SUBSTRING(@value, CHARINDEX(',',@value) + 1, LEN(@value) - CHARINDEX(',', @value) ) AS int)

		---Xóa vào bảng sách mượn

		IF (SELECT soLuongBiMuon FROM SACHMUON WHERE maSach = @maSach AND maPhieuMuon = @maPhieuMuon ) = @soLuongBiMuon
			BEGIN
				DELETE SACHMUON
				WHERE maSach = @maSach AND maPhieuMuon = @maPhieuMuon
			END
		ELSE
			BEGIN
				UPDATE SACHMUON
				SET soLuongBiMuon = soLuongBiMuon - @soLuongBiMuon
				WHERE maSach = @maSach AND maPhieuMuon = @maPhieuMuon

				UPDATE SACH
				SET soBanSaoHienCo = soBanSaoHienCo + @soLuongBiMuon
				WHERE MaSach = @maSach
			END
				

		--Lấy dòng kế
		FETCH NEXT FROM TraSachhCursor INTO @value
	END

	CLOSE TraSachhCursor
	DEALLOCATE TraSachhCursor


		COMMIT TRAN
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
	END CATCH



end
GO
/****** Object:  StoredProcedure [dbo].[sp_updateMuonSach]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[sp_updateMuonSach] (
	@maPhieuMuon int,
  @madocgia int,
  @songaymuon int,
  @trangthaitra nvarchar(50) ,
  @muonSach  varchar(1000)
)
AS
BEGIN
  
  BEGIN TRAN
  BEGIN TRY

	 --Tạo bảng tạm chứa mượn sách
	 SELECT * into #MuonSachTam
	FROM string_split(@muonSach, ';')

	--Duyệt qua từng dòng trong bảng tạm để bóc tách maSach, soLuongBiMuon
	DECLARE @value varchar(1000)
	DECLARE @maSach int, @soLuongBiMuon int

	DECLARE MuonSachCursor CURSOR FOR
	SELECT * FROM #MuonSachTam

	OPEN MuonSachCursor
	--Duyệt qua từng record trong bảng tạm
	FETCH NEXT FROM MuonSachCursor INTO @value

	--Kiểm tra xem còn dòng để duyệt nữa không
	WHILE @@FETCH_STATUS = 0
	BEGIN
		SELECT @maSach = CAST(SUBSTRING(@value, 0, CHARINDEX(',',@value)) AS int),
				@soLuongBiMuon = CAST(SUBSTRING(@value, CHARINDEX(',',@value) + 1, LEN(@value) - CHARINDEX(',', @value) ) AS int)

		---Chèn vào bảng sách mượn
		if not exists (select 1 from SACHMUON where maPhieuMuon = @maPhieuMuon and maSach = @maSach)
			begin
				INSERT INTO SACHMUON (
				maPhieuMuon,
				MASACH,
				SOLUONGBIMUON
			  )
			  VALUES (
				@maPhieuMuon,
				@maSach,
				@soLuongBiMuon
			  );

				UPDATE SACH
				 SET soBanSaoHienCo = soBanSaoHienCo - @soLuongBiMuon
				 WHERE MaSach = @maSach
			end
			else
				begin
					update SACHMUON
					set soLuongBiMuon = @soLuongBiMuon
					where maSach = @maSach and maPhieuMuon = @maPhieuMuon

					UPDATE SACH
				 SET soBanSaoHienCo = soBanSaoHienCo + @soLuongBiMuon
				 WHERE MaSach = @maSach

		end


		--Lấy dòng kế
		FETCH NEXT FROM MuonSachCursor INTO @value
	END



	CLOSE MuonSachCursor
	DEALLOCATE MuonSachCursor

	COMMIT TRAN
  END TRY
  BEGIN CATCH
	ROLLBACK TRAN
  END CATCH

END
GO
/****** Object:  StoredProcedure [dbo].[TaoPhieuPhat]    Script Date: 7/30/2024 9:15:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[TaoPhieuPhat](
	@docGiaID int,
	@noiDungPhat NVARCHAR(MAX),
	@PHIPHAT INT
)
AS
BEGIN

    -- Tạo phiếu phạt
    INSERT INTO PHIEUPHAT (MADOCGIA ,NOIDUNGPHAT, PHIPHAT, NGAYTAO)
    VALUES (@docGiaID, @noiDungPhat, @phiPhat, GETDATE());
END
GO
USE [master]
GO
ALTER DATABASE [QL_ThuVien] SET  READ_WRITE 
GO
