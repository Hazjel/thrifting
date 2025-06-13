SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;



CREATE DATABASE IF NOT EXISTS `db_barang`;
USE `db_barang`;

CREATE TABLE `manual_payments` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `price` int NOT NULL,
  `address` text NOT NULL,
  `proof_image` varchar(255) DEFAULT NULL,
  `status` varchar(50) DEFAULT 'pending',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `description` text,
  `photo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `price`, `category`, `description`, `photo`) VALUES
(1, 'Baju Nike', 680000.0, 'T-Shirt', 'lorem ipsum', 'images/Baju Nike.png'),
(2, 'Oakley', 90000.0, 'Other', 'lorem ipsum', 'images/Oakley.png'),
(3, 'Kappa', 1000000.0, 'Hoodie', 'lorem ipsum', 'images/Kappa.png'),
(4, 'Baju Hijau', 120000.0, 'Shirt', 'lorem ipsum', 'images/Baju Hijau.png'),
(5, 'Baju Orange', 80000.0, 'Shirt', 'lorem ipsum', 'images/Baju Orange.png'),
(6, 'Jeans Pendek', 50000.0, 'Shorts, Jeans', 'lorem ipsum', 'images/Jeans Pendek.png'),
(7, 'Jeans Panjang', 150000.0, 'Jeans', 'lorem ipsum', 'images/Jeans Panjang.png'),
(8, 'Vintage Levis', 175000.0, 'Jeans', 'lorem ipsum', 'images/Vintage Levis 501 Jeans - W35 L34.jpg'),
(9, 'Adidas Tee', 120000.0, 'T-Shirt', 'lorem ipsum', 'images/Adidas Originals T-Shirt Adicolor 3-Streifen.jpg'),
(10, 'Denim Shorts', 85000.0, 'Shorts', 'lorem ipsum', 'images/Korean High Waist Denim Shorts - White _ XXXL.jpg'),
(11, 'Oversized Hoodie', 160000.0, 'Hoodie', 'lorem ipsum', 'images/Oversize Heavyweight Hoodie WN6606.jpg'),
(12, 'Striped Shirt', 110000.0, 'Shirt', 'lorem ipsum', 'images/stripped shirt.jpg'),
(13, 'Basic White Tee', 75000.0, 'T-Shirt', 'lorem ipsum', 'images/Your Search Is Over for the Perfect White Tee.png'),
(14, 'Cargo Shorts', 90000.0, 'Shorts', 'lorem ipsum', 'images/cargo shorts.jpg'),
(15, 'Graphic Hoodie', 150000.0, 'Hoodie', 'lorem ipsum', 'images/graphic hoodie.jpg'),
(16, 'Slim Fit Jeans', 130000.0, 'Jeans', 'lorem ipsum', 'images/slim fit jeans.jpg'),
(17, 'Unbranded Tote Bag', 50000.0, 'Other', 'lorem ipsum', 'images/totebag.png');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `passwords` varchar(255) NOT NULL,
  `role_type` enum('seller','buyer') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `username`, `passwords`, `role_type`) VALUES
(1, 'Rusdi', 'rusdiganteng@email.com', 'dirusdi', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'seller'),
(2, 'Mamat', 'mamatresing@email.com', 'matmamat', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'buyer'),
(3, 'Imut', 'imutimoetz@email.com', 'mutimut', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'seller');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `manual_payments`
--
ALTER TABLE `manual_payments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `manual_payments`
--
ALTER TABLE `manual_payments`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

