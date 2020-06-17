-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 17, 2020 at 02:24 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `banque`
--

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

CREATE TABLE `clients` (
  `codecli` bigint(20) NOT NULL,
  `adresse_client` varchar(255) DEFAULT NULL,
  `nom_client` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf16;

--
-- Dumping data for table `clients`
--

INSERT INTO `clients` (`codecli`, `adresse_client`, `nom_client`) VALUES
(1, '6 Boulevard Anatole France 90000 Belfort', 'NOUASSI Zechirine'),
(2, '6 A Boulevard Anatole ', 'KOIMI AGOTSI Gedeon'),
(3, '12 Avenue General Leclerc', 'KOSSI Roger'),
(4, '2 rue victor hugo 75000 Paris', 'WOUANTCHUANG Fride Priscilia'),
(5, '12 Rue de la Zorn Schiltigheim', 'PWEGNE Daniel'),
(6, '12 Rue Faubourg Belfort', 'GBEGNON'),
(7, '25 Rue des Pepinières Belfort', 'GARNIER NICOLAS'),
(8, 'Rue des Bastiers Strasbourg', 'LECOUBERT Leo'),
(9, '20 Rue des Solistes Belfort', 'DUPONT Leo');

-- --------------------------------------------------------

--
-- Table structure for table `compte`
--

CREATE TABLE `compte` (
  `type_cpte` varchar(2) NOT NULL,
  `code_compte` varchar(255) NOT NULL,
  `date_creation` datetime DEFAULT NULL,
  `solde` double NOT NULL,
  `decouvert` double DEFAULT NULL,
  `taux` double DEFAULT NULL,
  `codecli` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

--
-- Dumping data for table `compte`
--

INSERT INTO `compte` (`type_cpte`, `code_compte`, `date_creation`, `solde`, `decouvert`, `taux`, `codecli`) VALUES
('CC', 'CC00012020', '2020-06-16 23:13:07', 15000, 0, NULL, 1),
('CE', 'CE10012020', '2020-06-16 23:14:52', 5000, NULL, 0, 1),
('CC', 'CC20012020', '2020-06-16 23:15:16', 800, 0, NULL, 3),
('CE', 'CE30012020', '2020-06-16 23:17:44', 1000, NULL, 0, 3),
('CC', 'CC40012020', '2020-06-16 23:45:12', 50000, 0, NULL, 5),
('CC', 'CC50012020', '2020-06-16 23:45:38', 510000, 0, NULL, 6),
('CE', 'CE60012020', '2020-06-16 23:50:44', 40000, NULL, 0, 1),
('CC', 'CC70012020', '2020-06-17 00:03:53', 6000000, 0, NULL, 9),
('CE', 'CE80012020', '2020-06-17 00:06:29', 40000, NULL, 0, 9);

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf16;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(10);

-- --------------------------------------------------------

--
-- Table structure for table `operation`
--

CREATE TABLE `operation` (
  `type_op` varchar(1) NOT NULL,
  `numero_operation` bigint(20) NOT NULL,
  `date_operation` datetime DEFAULT NULL,
  `montant` double NOT NULL,
  `code_compte` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf16;

--
-- Dumping data for table `operation`
--

INSERT INTO `operation` (`type_op`, `numero_operation`, `date_operation`, `montant`, `code_compte`) VALUES
('V', 1, '2020-06-16 23:15:51', 100, 'CC00012020'),
('R', 2, '2020-06-16 23:16:38', 100, 'CC00012020'),
('V', 3, '2020-06-16 23:18:13', 150, 'CC20012020'),
('R', 4, '2020-06-16 23:18:57', 150, 'CC20012020'),
('V', 5, '2020-06-16 23:51:51', 10000, 'CC50012020'),
('V', 6, '2020-06-17 00:07:28', 1000000, 'CC70012020');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`codecli`);

--
-- Indexes for table `operation`
--
ALTER TABLE `operation`
  ADD PRIMARY KEY (`numero_operation`),
  ADD KEY `FK6x81chpvk8psd9e86steac7jf` (`code_compte`(250));

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `operation`
--
ALTER TABLE `operation`
  MODIFY `numero_operation` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
