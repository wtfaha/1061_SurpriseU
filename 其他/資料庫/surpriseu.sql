-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- 主機: 127.0.0.1
-- 產生時間： 2017-12-03 19:19:24
-- 伺服器版本: 10.1.21-MariaDB
-- PHP 版本： 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `surpriseu`
--

-- --------------------------------------------------------

--
-- 資料表結構 `change`
--

CREATE TABLE `change` (
  `changeID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `organiser` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `participant` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `stateID` varchar(10) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `changedetail`
--

CREATE TABLE `changedetail` (
  `changeID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `title` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `typeID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `lowPrice` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `highPrice` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `participated` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `location` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `secondHand` varchar(2) COLLATE utf8_unicode_ci NOT NULL,
  `maxPeople` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `mention` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `picture` longblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `changelist`
--

CREATE TABLE `changelist` (
  `changeID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `sender` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `reciever` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(60) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `exchangeitem`
--

CREATE TABLE `exchangeitem` (
  `changeID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `userID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `itemPic` longblob,
  `itemState` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `itemTalk` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `sendPic` longblob,
  `sendState` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `sendTalk` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `receiveState` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `receiveTalk` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `stateID` varchar(10) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `exchangetype`
--

CREATE TABLE `exchangetype` (
  `typeID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `typeName` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `defaultPicture` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `messageboard`
--

CREATE TABLE `messageboard` (
  `boardID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `changeID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `userID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `content` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `time` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `score`
--

CREATE TABLE `score` (
  `scoreID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `commenter` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `commented` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `star` varchar(2) COLLATE utf8_unicode_ci NOT NULL,
  `comment` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `statetime`
--

CREATE TABLE `statetime` (
  `stateID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `registerTime` datetime NOT NULL,
  `startTime` datetime NOT NULL,
  `sendTime` datetime NOT NULL,
  `receiveTime` datetime NOT NULL,
  `objectTime` datetime NOT NULL,
  `endTime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `user`
--

CREATE TABLE `user` (
  `userID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `account` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `userName` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `birthday` date NOT NULL,
  `sex` varchar(2) COLLATE utf8_unicode_ci NOT NULL,
  `phonenumber` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `participated` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `photo` longblob,
  `defaultAddress` varchar(60) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `whisper`
--

CREATE TABLE `whisper` (
  `whisperID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `sender` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `receiver` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `whisper` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 已匯出資料表的索引
--

--
-- 資料表索引 `change`
--
ALTER TABLE `change`
  ADD PRIMARY KEY (`changeID`),
  ADD KEY `organiser` (`organiser`),
  ADD KEY `stateID` (`stateID`);

--
-- 資料表索引 `changedetail`
--
ALTER TABLE `changedetail`
  ADD PRIMARY KEY (`changeID`);

--
-- 資料表索引 `changelist`
--
ALTER TABLE `changelist`
  ADD PRIMARY KEY (`changeID`);

--
-- 資料表索引 `exchangeitem`
--
ALTER TABLE `exchangeitem`
  ADD PRIMARY KEY (`changeID`);

--
-- 資料表索引 `exchangetype`
--
ALTER TABLE `exchangetype`
  ADD PRIMARY KEY (`typeID`);

--
-- 資料表索引 `messageboard`
--
ALTER TABLE `messageboard`
  ADD PRIMARY KEY (`boardID`);

--
-- 資料表索引 `score`
--
ALTER TABLE `score`
  ADD PRIMARY KEY (`scoreID`);

--
-- 資料表索引 `statetime`
--
ALTER TABLE `statetime`
  ADD PRIMARY KEY (`stateID`);

--
-- 資料表索引 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`);

--
-- 資料表索引 `whisper`
--
ALTER TABLE `whisper`
  ADD PRIMARY KEY (`whisperID`);

--
-- 已匯出資料表的限制(Constraint)
--

--
-- 資料表的 Constraints `change`
--
ALTER TABLE `change`
  ADD CONSTRAINT `change_ibfk_1` FOREIGN KEY (`organiser`) REFERENCES `user` (`userID`),
  ADD CONSTRAINT `change_ibfk_2` FOREIGN KEY (`stateID`) REFERENCES `statetime` (`stateID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
