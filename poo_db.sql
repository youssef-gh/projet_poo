-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 11, 2021 at 12:08 PM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `poo_db`
--
CREATE DATABASE IF NOT EXISTS `poo_db` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `poo_db`;

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `numeroclient` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(30) NOT NULL,
  `prenom` varchar(30) NOT NULL,
  `adresse` varchar(80) NOT NULL,
  `telephone` varchar(12) NOT NULL,
  PRIMARY KEY (`numeroclient`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`numeroclient`, `nom`, `prenom`, `adresse`, `telephone`) VALUES
(66, 'Hrouch', 'Aymane', 'Khenifra', '0637478085'),
(67, 'Ghoundal', 'Youssef', 'Beni-Mellal', '0654357649');

-- --------------------------------------------------------

--
-- Table structure for table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE IF NOT EXISTS `commande` (
  `numerocommande` int(11) NOT NULL AUTO_INCREMENT,
  `datecommande` date DEFAULT NULL,
  `numeroclient` int(11) NOT NULL,
  `numeroproduit` int(11) DEFAULT NULL,
  PRIMARY KEY (`numerocommande`),
  KEY `numeroclient` (`numeroclient`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `commande`
--

INSERT INTO `commande` (`numerocommande`, `datecommande`, `numeroclient`, `numeroproduit`) VALUES
(10, '2021-06-11', 66, 6);

-- --------------------------------------------------------

--
-- Table structure for table `facture`
--

DROP TABLE IF EXISTS `facture`;
CREATE TABLE IF NOT EXISTS `facture` (
  `numerofacture` int(11) NOT NULL AUTO_INCREMENT,
  `datefacture` varchar(12) DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `numerocommande` int(11) DEFAULT NULL,
  PRIMARY KEY (`numerofacture`),
  KEY `numerocommande` (`numerocommande`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `facture`
--

INSERT INTO `facture` (`numerofacture`, `datefacture`, `montant`, `numerocommande`) VALUES
(3, '2021-06-12', 52004, 10);

-- --------------------------------------------------------

--
-- Table structure for table `livraison`
--

DROP TABLE IF EXISTS `livraison`;
CREATE TABLE IF NOT EXISTS `livraison` (
  `numerolivraison` int(11) NOT NULL AUTO_INCREMENT,
  `dateLivraison` date DEFAULT NULL,
  `numerocommande` int(11) NOT NULL,
  PRIMARY KEY (`numerolivraison`),
  KEY `numerocommande` (`numerocommande`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `livraison`
--

INSERT INTO `livraison` (`numerolivraison`, `dateLivraison`, `numerocommande`) VALUES
(16, '2021-06-12', 10);

-- --------------------------------------------------------

--
-- Table structure for table `produit`
--

DROP TABLE IF EXISTS `produit`;
CREATE TABLE IF NOT EXISTS `produit` (
  `numeroproduit` int(11) NOT NULL AUTO_INCREMENT,
  `nomproduit` varchar(30) NOT NULL,
  `quantite` int(11) NOT NULL,
  `prix` double NOT NULL,
  PRIMARY KEY (`numeroproduit`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `produit`
--

INSERT INTO `produit` (`numeroproduit`, `nomproduit`, `quantite`, `prix`) VALUES
(6, 'Laptop HP', 8, 6500.5);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(30) NOT NULL,
  `pwd` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `login`, `pwd`) VALUES
(1, 'root', 'toor');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `commande_ibfk_1` FOREIGN KEY (`numeroclient`) REFERENCES `client` (`numeroclient`);

--
-- Constraints for table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `facture_ibfk_1` FOREIGN KEY (`numerocommande`) REFERENCES `commande` (`numerocommande`);

--
-- Constraints for table `livraison`
--
ALTER TABLE `livraison`
  ADD CONSTRAINT `livraison_ibfk_1` FOREIGN KEY (`numerocommande`) REFERENCES `commande` (`numerocommande`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
