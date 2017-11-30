DROP SCHEMA test_catalogue;

CREATE SCHEMA test_catalogue;

USE test_catalogue;

CREATE TABLE members (
	member_id int AUTO_INCREMENT,
    f_name text,
    l_name text,
    landline text,
    mobile text,
    PRIMARY KEY (member_id)
);

INSERT INTO members (f_name, l_name, landline, mobile) 
VALUES ('Alex', 'Alexiadis', 2100002000, 6979320382),
		('Mike', 'Michailidis', 2100000201, 6979320383),
        ('Antonis', 'Antoniadis', 2100000201, 6979320383);
        

GRANT ALL PRIVILEGES ON `test_catalogue`.* TO 'test_catalogue_user'@'%' IDENTIFIED BY 'test';

