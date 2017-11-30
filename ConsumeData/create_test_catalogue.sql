CREATE DATABASE `test_catalogue` /*!40100 DEFAULT CHARACTER SET utf8 */
CREATE TABLE `members` (
  `member_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_name` text,
  `l_name` text,
  `landline` text,
  `mobile` text,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8
CREATE TABLE `birthdays` (
  `birthday` date NOT NULL,
  `member_id` int(11) NOT NULL,
  PRIMARY KEY (`birthday`,`member_id`),
  KEY `birthdays_fk_1` (`member_id`),
  CONSTRAINT `birthdays_fk_1` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
INSERT INTO members (f_name, l_name, landline, mobile) VALUES ('Alex', 'Alexiadis', 2100002000, 6979320382),('Mike', 'Michailidis', 2100000201, 6979320383),('Antonis', 'Antoniadis', 2100000201, 6979320383);
