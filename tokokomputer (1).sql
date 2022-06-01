-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 01, 2022 at 06:35 AM
-- Server version: 5.7.21
-- PHP Version: 5.6.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tokokomputer`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

DROP TABLE IF EXISTS `barang`;
CREATE TABLE IF NOT EXISTS `barang` (
  `id_barang` int(11) NOT NULL,
  `nama_barang` varchar(50) NOT NULL,
  `jenis_produk` varchar(50) NOT NULL,
  `garansi` date DEFAULT NULL,
  `stock` int(11) NOT NULL,
  `harga_produk` varchar(15) NOT NULL,
  `id_supplier` int(11) NOT NULL,
  PRIMARY KEY (`id_barang`),
  KEY `IdSupplier` (`id_supplier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`id_barang`, `nama_barang`, `jenis_produk`, `garansi`, `stock`, `harga_produk`, `id_supplier`) VALUES
(1, 'ASUS', 'Laptop', '2023-05-30', 0, '', 1),
(2, 'Lenovo', 'Laptop', '2023-05-30', 0, '', 1),
(3, 'ACER', 'Notebook', '2023-05-30', 0, '', 2),
(4, 'HP', 'PC', '2023-05-30', 0, '', 2),
(5, 'Samsung', 'CPU', '2023-05-30', 0, '', 3),
(6, 'Mouse RGB 312', 'Mouse', NULL, 20, '30000', 1);

-- --------------------------------------------------------

--
-- Table structure for table `detailpembelian`
--

DROP TABLE IF EXISTS `detailpembelian`;
CREATE TABLE IF NOT EXISTS `detailpembelian` (
  `id_detailpembelian` int(11) NOT NULL AUTO_INCREMENT,
  `id_pembelian` int(11) NOT NULL,
  `harga_beli` varchar(20) NOT NULL,
  `jumlah_barang` varchar(50) NOT NULL,
  `total_pembelian` varchar(50) NOT NULL,
  PRIMARY KEY (`id_detailpembelian`),
  KEY `id_pembelian` (`id_pembelian`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `detailpenjualan`
--

DROP TABLE IF EXISTS `detailpenjualan`;
CREATE TABLE IF NOT EXISTS `detailpenjualan` (
  `id_detailpenjualan` int(11) NOT NULL AUTO_INCREMENT,
  `id_penjualan` int(11) NOT NULL,
  `harga_jual` varchar(20) NOT NULL,
  `total_penjualan` varchar(50) NOT NULL,
  PRIMARY KEY (`id_detailpenjualan`),
  KEY `id_penjualan` (`id_penjualan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hutang`
--

DROP TABLE IF EXISTS `hutang`;
CREATE TABLE IF NOT EXISTS `hutang` (
  `id_hutang` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(20) NOT NULL,
  `cicilan` varchar(20) NOT NULL,
  `jatuh_tempo` date NOT NULL,
  PRIMARY KEY (`id_hutang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `jadwal`
--

DROP TABLE IF EXISTS `jadwal`;
CREATE TABLE IF NOT EXISTS `jadwal` (
  `id_jadwal` int(11) NOT NULL AUTO_INCREMENT,
  `id_karyawan` int(11) NOT NULL,
  `terjadwal` date DEFAULT NULL,
  PRIMARY KEY (`id_jadwal`),
  KEY `FOREIGN KEY` (`id_karyawan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

DROP TABLE IF EXISTS `karyawan`;
CREATE TABLE IF NOT EXISTS `karyawan` (
  `id_karyawan` int(11) NOT NULL AUTO_INCREMENT,
  `nama_karyawan` varchar(50) NOT NULL,
  `password` int(15) NOT NULL,
  `jabatan` enum('owner','kasir','staff gudang','customer service','') NOT NULL,
  PRIMARY KEY (`id_karyawan`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `nama_karyawan`, `password`, `jabatan`) VALUES
(1, 'vcxvxvcxvcx', 0, 'kasir'),
(2, 'Rizqi', 190901, 'kasir'),
(3, 'Steven', 3, 'kasir'),
(4, 'Lutfa', 4, 'staff gudang'),
(5, 'Nizar', 5, 'customer service');

-- --------------------------------------------------------

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
CREATE TABLE IF NOT EXISTS `manager` (
  `Id_Manager` int(11) NOT NULL,
  `id_karyawan` int(11) NOT NULL,
  `NamaManager` varchar(255) NOT NULL,
  `Alamat` varchar(255) NOT NULL,
  `NoTelp` varchar(12) NOT NULL,
  PRIMARY KEY (`Id_Manager`),
  KEY `id_karyawan` (`id_karyawan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `pelanggan`
--

DROP TABLE IF EXISTS `pelanggan`;
CREATE TABLE IF NOT EXISTS `pelanggan` (
  `Id_Pelanggan` int(11) NOT NULL,
  `NamaPelanggan` varchar(255) NOT NULL,
  `NoTelp` varchar(255) NOT NULL,
  PRIMARY KEY (`Id_Pelanggan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pelanggan`
--

INSERT INTO `pelanggan` (`Id_Pelanggan`, `NamaPelanggan`, `NoTelp`) VALUES
(1, 'Sudarjo Jamiono', '081212123434'),
(2, 'Joni Alfaruq', '081122223333'),
(3, 'Subai Dorkunx', '082233334444'),
(4, 'Albert Suyanto', '081123234545'),
(5, 'Sunoko Albert', '087711113333');

-- --------------------------------------------------------

--
-- Table structure for table `pembayaran`
--

DROP TABLE IF EXISTS `pembayaran`;
CREATE TABLE IF NOT EXISTS `pembayaran` (
  `id_pembayaran` int(11) NOT NULL AUTO_INCREMENT,
  `id_hutang` int(11) NOT NULL,
  `id_barang` int(11) NOT NULL,
  `id_supplier` int(11) NOT NULL,
  PRIMARY KEY (`id_pembayaran`),
  KEY `id_hutang` (`id_hutang`),
  KEY `id_barang` (`id_barang`),
  KEY `id_supplier` (`id_supplier`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pembelian`
--

DROP TABLE IF EXISTS `pembelian`;
CREATE TABLE IF NOT EXISTS `pembelian` (
  `id_pembelian` int(11) NOT NULL AUTO_INCREMENT,
  `id_pembayaran` int(11) NOT NULL,
  `id_barang` int(11) NOT NULL,
  `id_supplier` int(11) NOT NULL,
  `tgl_pembelian` date NOT NULL,
  PRIMARY KEY (`id_pembelian`),
  KEY `id_barang` (`id_barang`),
  KEY `id_supplier` (`id_supplier`),
  KEY `id_pembayaran` (`id_pembayaran`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `penjualan`
--

DROP TABLE IF EXISTS `penjualan`;
CREATE TABLE IF NOT EXISTS `penjualan` (
  `id_penjualan` int(11) NOT NULL AUTO_INCREMENT,
  `id_pelanggan` int(11) NOT NULL,
  `id_barang` int(11) NOT NULL,
  `id_karyawan` int(11) NOT NULL,
  `tanggal_transaksi` date NOT NULL,
  PRIMARY KEY (`id_penjualan`),
  KEY `id_barang` (`id_barang`),
  KEY `id_karyawan` (`id_karyawan`),
  KEY `id_pelanggan` (`id_pelanggan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
CREATE TABLE IF NOT EXISTS `supplier` (
  `IdSupplier` int(11) NOT NULL,
  `NamaSupplier` varchar(50) NOT NULL,
  `Alamat` varchar(100) NOT NULL,
  `NoTelp` varchar(12) NOT NULL,
  PRIMARY KEY (`IdSupplier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`IdSupplier`, `NamaSupplier`, `Alamat`, `NoTelp`) VALUES
(1, 'Sujatmiko Alamsyah', 'Jalan Kambing', '08553333444'),
(2, 'Subroto', 'Jalan Kalimantan', '08998844667'),
(3, 'Rosiyah', 'Jalan Senja', '08776666555');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `barang`
--
ALTER TABLE `barang`
  ADD CONSTRAINT `barang_ibfk_1` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`IdSupplier`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `detailpembelian`
--
ALTER TABLE `detailpembelian`
  ADD CONSTRAINT `detailpembelian_ibfk_1` FOREIGN KEY (`id_pembelian`) REFERENCES `pembelian` (`id_pembelian`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `detailpenjualan`
--
ALTER TABLE `detailpenjualan`
  ADD CONSTRAINT `detailpenjualan_ibfk_1` FOREIGN KEY (`id_penjualan`) REFERENCES `penjualan` (`id_penjualan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `manager`
--
ALTER TABLE `manager`
  ADD CONSTRAINT `manager_ibfk_1` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD CONSTRAINT `pembayaran_ibfk_1` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`) ON UPDATE CASCADE,
  ADD CONSTRAINT `pembayaran_ibfk_2` FOREIGN KEY (`id_hutang`) REFERENCES `hutang` (`id_hutang`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pembayaran_ibfk_3` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`IdSupplier`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pembelian`
--
ALTER TABLE `pembelian`
  ADD CONSTRAINT `pembelian_ibfk_1` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pembelian_ibfk_2` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`IdSupplier`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pembelian_ibfk_3` FOREIGN KEY (`id_pembayaran`) REFERENCES `pembayaran` (`id_pembayaran`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `penjualan`
--
ALTER TABLE `penjualan`
  ADD CONSTRAINT `penjualan_ibfk_1` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `penjualan_ibfk_2` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `penjualan_ibfk_3` FOREIGN KEY (`id_pelanggan`) REFERENCES `pelanggan` (`Id_Pelanggan`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
