-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 12, 2025 at 07:18 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_barang`
--

-- --------------------------------------------------------

--
-- Table structure for table `produk`
--

CREATE TABLE `produk` (
  `id` int(11) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `ukuran` varchar(255) NOT NULL,
  `kategori` varchar(255) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `harga` decimal(10,1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `produk`
--

INSERT INTO `produk` (`id`, `nama`, `ukuran`, `kategori`, `jumlah`, `harga`) VALUES
(1, 'Baju Nike', 'XL', 'T-Shirt', 1, 680000.0),
(2, 'Oakley', 'Other', 'Other', 1, 90000.0),
(3, 'Kappa', 'L', 'Hoodie', 1, 1000000.0),
(4, 'Baju Hijau', 'XL', 'Shirt', 1, 120000.0),
(5, 'Baju Orange', 'M', 'Shirt', 1, 80000.0),
(6, 'Jeans Pendek', 'S', 'Shorts, Jeans', 1, 50000.0),
(7, 'Jeans Panjang', 'S', 'Jeans', 1, 150000.0),
(8, 'Vintage Levi\'s', 'M', 'Jeans', 1, 175000.0),
(9, 'Adidas Tee', 'L', 'T-Shirt', 1, 120000.0),
(10, 'Denim Shorts', 'S', 'Shorts', 1, 85000.0),
(11, 'Oversized Hoodie', 'XL', 'Hoodie', 1, 160000.0),
(12, 'Striped Shirt', 'M', 'Shirt', 1, 110000.0),
(13, 'Basic White Tee', 'L', 'T-Shirt', 1, 75000.0),
(14, 'Cargo Shorts', 'L', 'Shorts', 1, 90000.0),
(15, 'Graphic Hoodie', 'M', 'Hoodie', 1, 150000.0),
(16, 'Slim Fit Jeans', 'M', 'Jeans', 1, 130000.0),
(17, 'Unbranded Tote Bag', 'Free Size', 'Other', 1, 50000.0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `produk`
--
ALTER TABLE `produk`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
