CREATE DATABASE  IF NOT EXISTS `DishwasherSchedule`;
USE `DishwasherSchedule`;

CREATE TABLE Students (
id INT AUTO_INCREMENT PRIMARY KEY,
firstName VARCHAR(100) NOT NULL,
lastName VARCHAR(100) NOT NULL,
nickName VARCHAR(100),
birthday DATE NOT NULL,
program VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL,
phoneNumber VARCHAR(100) NOT NULL
);

CREATE TABLE DishwasherScheduler (
id INT AUTO_INCREMENT PRIMARY KEY,
chargedate DATE NOT NULL,
filler INT NOT NULL,
emptier INT NOT NULL
);

INSERT INTO Students (firstName, lastName, nickName, birthday, program, email, phoneNumber) VALUE ('Balint', 'Kliszki', 'Kliba', '1986-02-03', 'software developer course (java) - trainee', 'klibalint@gmail.com', '+36 30 603 8242');
INSERT INTO Students (firstName, lastName, nickName, birthday, program, email, phoneNumber) VALUES ('Jason', 'Yeo', 'Jay', '1984-11-21', 'software developer course (java) - trainee', 'jayyeo21@gmail.com', '+36 70 557 6220');
INSERT INTO Students (firstName, lastName, nickName, birthday, program, email, phoneNumber) VALUES ('Daniel','Kocsis','Dani','1988-06-28','software developer course (java) - trainee','kocsis.daniel4@gmail.com', '+36 30 298 2674');
INSERT INTO Students (firstName, lastName, nickName, birthday, program, email, phoneNumber) VALUES ('Istvan','Bencsik','Benyo','1991-08-09','software developer course (java) - trainee','slayerenkala@gmail.com','+36 30 906 0053');
INSERT INTO Students (firstName, lastName, nickName, birthday, program, email, phoneNumber) VALUES ('Máté','Kiss','Nathazzy','1991-06-15','software developer course (java) - trainee','matek.nat@gmail.com','+36 70 940 8562');
INSERT INTO Students (firstName, lastName, nickName, birthday, program, email, phoneNumber) VALUES ('Sandor', 'Csicsman', 'Sanyi','1986-08-27', 'software developer course (java) - trainee', 'csisanyi@gmail.com', '+36 20 553 3137');
INSERT INTO Students (firstName, lastName, nickName, birthDay, program, email, phoneNumber) VALUES ('Zsolt', 'Putnoki Nagy', 'Zsolt', '1973.04.01', 'software developer course (java) - trainee', 'putnokinagy@gmail.com', '+36 70 623 5706');
INSERT INTO Students (firstName, lastName, nickName, birthday, program, email, phoneNumber) VALUES ('Tamas','Berki','Tamas','1992.06.17','software developer course (java) - trainee','berki.tamas.jozsef@gmail.com', '+36 70 362 3336');
INSERT INTO Students (firstName, lastName, nickName, birthday, program, email, phoneNumber) VALUES ('Daniel', 'Torok', 'Dani', '1979-05-03', 'software developer course (java) - trainee', 'da.torok@gmail.com', '+36 70 357 6355');
INSERT INTO Students (firstName, lastName, nickName, birthday, program, email, phoneNumber) VALUES ('Gergo', 'Mandi', 'Gergo', '1993-10-28','software developer course (java) - trainee','mandi.gergo@gmail.com','+36 20 577 7035');
INSERT INTO Students (firstName, lastName, nickName, birthday, program, email, phoneNumber) VALUES ('Bence', 'Mandi', 'Bence', '1993-10-28','software developer course (java) - trainee','mandi.bence@gmail.com','+36 30 891 7707');
INSERT INTO Students (firstName, lastName, nickName, birthday, program, email, phoneNumber) VALUES ('Greczi', 'Laszlo', 'Laci', '1980-06-18','software developer course (java) - trainer','laszlo.greczi@matritel.eu','+36 70 434 8818');
INSERT INTO Students (firstName, lastName, nickName, birthday, program, email, phoneNumber) VALUES ('Pratser', 'Frigyes', 'Frigo', '1982-08-18','software developer course (java) - trainer','frigyes.pratser@matritel.eu','+36 30 891 7707');


CREATE TABLE Holidays (
id INT AUTO_INCREMENT PRIMARY KEY,
holiday DATE NOT NULL
);

INSERT INTO Holidays (holiday) VALUE ('2018-06-09');
INSERT INTO Holidays (holiday) VALUE ('2018-06-10');
INSERT INTO Holidays (holiday) VALUE ('2018-06-16');
INSERT INTO Holidays (holiday) VALUE ('2018-06-17');
INSERT INTO Holidays (holiday) VALUE ('2018-06-23');
INSERT INTO Holidays (holiday) VALUE ('2018-06-24');
INSERT INTO Holidays (holiday) VALUE ('2018-06-30');
INSERT INTO Holidays (holiday) VALUE ('2018-07-01');
INSERT INTO Holidays (holiday) VALUE ('2018-07-07');
INSERT INTO Holidays (holiday) VALUE ('2018-07-08');
INSERT INTO Holidays (holiday) VALUE ('2018-07-14');
INSERT INTO Holidays (holiday) VALUE ('2018-07-15');
