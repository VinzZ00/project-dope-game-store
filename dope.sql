-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 17, 2022 at 07:51 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dope`
--

-- --------------------------------------------------------

--
-- Table structure for table `carts`
--

CREATE TABLE `carts` (
  `UserID` char(5) NOT NULL,
  `BeverageID` char(5) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `detailtransactions`
--

CREATE TABLE `detailtransactions` (
  `TransactionID` char(5) NOT NULL,
  `GameID` char(5) NOT NULL,
  `Quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detailtransactions`
--

INSERT INTO `detailtransactions` (`TransactionID`, `GameID`, `Quantity`) VALUES
('TR001', 'GA001', 71),
('TR001', 'GA002', 230),
('TR002', 'GA001', 140),
('TR002', 'GA002', 44),
('TR003', 'GA003', 1),
('TR003', 'GA004', 1),
('TR004', 'GA002', 1),
('TR004', 'GA003', 1),
('TR005', 'GA001', 2),
('TR005', 'GA003', 1),
('TR005', 'GA004', 1),
('TR006', 'GA001', 2),
('TR006', 'GA002', 3);

-- --------------------------------------------------------

--
-- Table structure for table `games`
--

CREATE TABLE `games` (
  `GameID` char(5) NOT NULL,
  `GameName` varchar(30) NOT NULL,
  `GameType` varchar(30) NOT NULL,
  `GamePrice` int(11) NOT NULL,
  `GameStock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `games`
--

INSERT INTO `games` (`GameID`, `GameName`, `GameType`, `GamePrice`, `GameStock`) VALUES
('GA001', 'Minecraft', 'RPG', 650000, 80),
('GA002', 'Crossfire', 'FPS', 4500000, 175),
('GA003', 'warcraft', 'MOBA', 1000, 5),
('GA004', 'Dota 2', 'MOBA', 10000, 2);

-- --------------------------------------------------------

--
-- Table structure for table `headertransactions`
--

CREATE TABLE `headertransactions` (
  `TransactionID` char(5) NOT NULL,
  `UserID` char(5) DEFAULT NULL,
  `TransactionDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `headertransactions`
--

INSERT INTO `headertransactions` (`TransactionID`, `UserID`, `TransactionDate`) VALUES
('TR001', 'US002', '2021-06-08'),
('TR002', 'US002', '2021-06-08'),
('TR003', 'US002', '2022-01-07'),
('TR004', 'US002', '2022-01-07'),
('TR005', 'US002', '2022-01-07'),
('TR006', 'US002', '2022-01-07');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `UserID` char(5) DEFAULT NULL,
  `UserName` varchar(30) DEFAULT NULL,
  `UserEmail` varchar(50) DEFAULT NULL,
  `UserPassword` varchar(30) DEFAULT NULL,
  `UserDOB` date DEFAULT NULL,
  `UserGender` varchar(10) DEFAULT NULL,
  `UserAddress` varchar(255) DEFAULT NULL,
  `UserPhone` varchar(30) DEFAULT NULL,
  `UserRole` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `UserName`, `UserEmail`, `UserPassword`, `UserDOB`, `UserGender`, `UserAddress`, `UserPhone`, `UserRole`) VALUES
('US001', 'Doddie Prawarjito', 'admin@gmail.com', 'admin1', NULL, 'Male', 'asdasdasdasd Street', '0920398193812319', 'Admin'),
('US002', 'daniel fujiono', 'customer', 'customer', NULL, 'Male', 'binus Street', '012345678911', 'Customer'),
('US003', 'budi', 'budi@gmail.com', 'budi123', '2022-01-12', 'Male', 'dot Street', '082356478541', 'Admin'),
('US004', 'budi123', 'budi@gmail.com', 'budi123', NULL, 'Male', 'asdfsf Street', '085655231452', 'Admin'),
('US005', 'dodi13', 'dodi@gmail.com', 'dodi123', NULL, 'Male', 'adsf Street', '085655487545', 'Admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `carts`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`UserID`,`BeverageID`);

--
-- Indexes for table `detailtransactions`
--
ALTER TABLE `detailtransactions`
  ADD PRIMARY KEY (`TransactionID`,`GameID`);

--
-- Indexes for table `headertransactions`
--
ALTER TABLE `headertransactions`
  ADD PRIMARY KEY (`TransactionID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
