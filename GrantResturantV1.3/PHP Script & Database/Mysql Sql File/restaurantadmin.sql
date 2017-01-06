-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 11, 2016 at 12:26 PM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `restaurantadmin`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_adminlogin`
--

CREATE TABLE IF NOT EXISTS `tbl_adminlogin` (
`id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `notify` tinyint(1) NOT NULL,
  `language` varchar(100) NOT NULL,
  `google_api` text NOT NULL,
  `certificate` varchar(200) NOT NULL,
  `passphrace` varchar(100) NOT NULL,
  `admin_right` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_adminlogin`
--

INSERT INTO `tbl_adminlogin` (`id`, `username`, `password`, `notify`, `language`, `google_api`, `certificate`, `passphrace`, `admin_right`) VALUES
(1, 'admin@gmail.com', '123', 0, 'fr_FR', 'AIzaSyAu19qPvaNCDuhFFCN8qqMU_i7rt8vwO5g', 'Certificates_new_check.pem', '123', 1),
(2, 'demo@gmail.com', '123', 0, 'fr_FR', 'AIzaSyAu19qPvaNCDuhFFCN8qqMU_i7rt8vwO5g', 'pushcert.pem', '123', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_devicedata`
--

CREATE TABLE IF NOT EXISTS `tbl_devicedata` (
`id` int(11) NOT NULL,
  `device_id` text NOT NULL,
  `device` varchar(30) NOT NULL,
  `mobile_device` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_foodcategory`
--

CREATE TABLE IF NOT EXISTS `tbl_foodcategory` (
`id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_multiimages`
--

CREATE TABLE IF NOT EXISTS `tbl_multiimages` (
`id` int(11) NOT NULL,
  `image` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_offerdata`
--

CREATE TABLE IF NOT EXISTS `tbl_offerdata` (
`id` int(11) NOT NULL,
  `title` varchar(250) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_order`
--

CREATE TABLE IF NOT EXISTS `tbl_order` (
`id` int(11) NOT NULL,
  `res_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `no_people` varchar(10) NOT NULL,
  `datetime` varchar(100) NOT NULL,
  `phone` varchar(40) NOT NULL,
  `orderdis` text NOT NULL,
  `orderdis2` text NOT NULL,
  `comment` text NOT NULL,
  `notify` tinyint(1) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `confirm_order` int(11) NOT NULL,
  `reg_id` text NOT NULL,
  `date` text NOT NULL,
  `device` varchar(20) NOT NULL,
  `unik` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_order_homedelivery`
--

CREATE TABLE IF NOT EXISTS `tbl_order_homedelivery` (
`id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `number` varchar(10) NOT NULL,
  `reference` varchar(100) NOT NULL,
  `neighborhood` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `zipcode` varchar(9) NOT NULL,
  `state` varchar(2) NOT NULL,
  `details` varchar(1000) NOT NULL,
  `reg_id` text,
  `user_type` varchar(30) DEFAULT NULL,
  `order_status` int(11) NOT NULL,
  `timestamp` int(11) NOT NULL,
  `orderdesc` text NOT NULL,
  `orderdesc1` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_setcurrency`
--

CREATE TABLE IF NOT EXISTS `tbl_setcurrency` (
`id` int(11) NOT NULL,
  `symbol` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_setcurrency`
--

INSERT INTO `tbl_setcurrency` (`id`, `symbol`) VALUES
(1, '$');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_singleresdetail`
--

CREATE TABLE IF NOT EXISTS `tbl_singleresdetail` (
`id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  `address` text NOT NULL,
  `country` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `street` varchar(50) NOT NULL,
  `zipcode` varchar(10) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(200) NOT NULL,
  `website` text NOT NULL,
  `lat` varchar(200) NOT NULL,
  `long` varchar(200) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_singleresdetail`
--

INSERT INTO `tbl_singleresdetail` (`id`, `name`, `address`, `country`, `state`, `street`, `zipcode`, `phone`, `email`, `website`, `lat`, `long`) VALUES
(1, 'Grant Restaurant', '1001 Queensway', 'London', 'London', 'D32 PA', '10100000', '1034567890', 'grant@gmail.com', 'https://www.grant.com', '23,33', '72,33');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_subcategory`
--

CREATE TABLE IF NOT EXISTS `tbl_subcategory` (
`id` int(11) NOT NULL,
  `menu_id` varchar(200) NOT NULL,
  `cat_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` varchar(10) NOT NULL,
  `image` varchar(200) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_webservice`
--

CREATE TABLE IF NOT EXISTS `tbl_webservice` (
`id` int(11) NOT NULL,
  `desc` text NOT NULL,
  `url` varchar(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_webservice`
--

INSERT INTO `tbl_webservice` (`id`, `desc`, `url`) VALUES
(1, 'Display All Food Category With Tree Format', 'webservice/foodcategory.php'),
(2, 'Display Food Category With Search Text', 'webservice/foodcategory.php?search=""'),
(3, 'Book Order with specify input  ', 'webservice/bookorder.php?name=""&&people=""&&phone=""&&datetime=""&&comment=""&&orderdis=""'),
(4, 'Display Restaurant Offers ', 'webservice/offers.php');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_adminlogin`
--
ALTER TABLE `tbl_adminlogin`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_devicedata`
--
ALTER TABLE `tbl_devicedata`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_foodcategory`
--
ALTER TABLE `tbl_foodcategory`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_multiimages`
--
ALTER TABLE `tbl_multiimages`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_offerdata`
--
ALTER TABLE `tbl_offerdata`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_order`
--
ALTER TABLE `tbl_order`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_order_homedelivery`
--
ALTER TABLE `tbl_order_homedelivery`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_setcurrency`
--
ALTER TABLE `tbl_setcurrency`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_singleresdetail`
--
ALTER TABLE `tbl_singleresdetail`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_subcategory`
--
ALTER TABLE `tbl_subcategory`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_webservice`
--
ALTER TABLE `tbl_webservice`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_adminlogin`
--
ALTER TABLE `tbl_adminlogin`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `tbl_devicedata`
--
ALTER TABLE `tbl_devicedata`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_foodcategory`
--
ALTER TABLE `tbl_foodcategory`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_multiimages`
--
ALTER TABLE `tbl_multiimages`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_offerdata`
--
ALTER TABLE `tbl_offerdata`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_order`
--
ALTER TABLE `tbl_order`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_order_homedelivery`
--
ALTER TABLE `tbl_order_homedelivery`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_setcurrency`
--
ALTER TABLE `tbl_setcurrency`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `tbl_singleresdetail`
--
ALTER TABLE `tbl_singleresdetail`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `tbl_subcategory`
--
ALTER TABLE `tbl_subcategory`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_webservice`
--
ALTER TABLE `tbl_webservice`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
