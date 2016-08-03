DROP DATABASE IF EXISTS moviedb;
CREATE DATABASE moviedb;
USE moviedb;

DROP TABLE IF EXISTS USER;

CREATE TABLE USER (
  ID       BIGINT       NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL,
  login    VARCHAR(60)  NOT NULL,
  password VARCHAR(255) NOT NULL,
  isadmin  TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS MOVIE;

CREATE TABLE MOVIE (
  ID           BIGINT       NOT NULL AUTO_INCREMENT,
  moviename   VARCHAR(255) NOT NULL,
  director     VARCHAR(255),
  releasedate DATE,
  trailerurl  VARCHAR(100),
  rating       DOUBLE,
  description  TEXT,
  PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS REVIEW;

CREATE TABLE REVIEW (
  ID           BIGINT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (ID),
  userID      BIGINT NOT NULL,
  FOREIGN KEY (userID)
  REFERENCES USER (ID)
    ON DELETE CASCADE,
  movieID     BIGINT NOT NULL,
  FOREIGN KEY (movieID)
  REFERENCES MOVIE (ID)
    ON DELETE CASCADE,
  postdate    DATE,
  reviewtitle VARCHAR(100),
  rating       INT,
  reviewtext  TEXT
);

INSERT INTO USER (username, login, password, isadmin)
VALUES ('Lothar', '123@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0),
  ('Medivh', 'qwe@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0),
  ('Neltarion', 'asd@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 1);

INSERT INTO MOVIE (moviename, director, releasedate, trailerurl, rating, description)
VALUES ('Warcraft', 'Duncan Jones', '2016-06-10', 'https://www.youtube.com/embed/RhFMIRuHAL4', 7.4,
        'As an Orc horde invades the planet Azeroth using a magic portal, a few human heroes and dissenting Orcs must attempt to stop the true evil behind this war.'),
  ('Tempo', 'Random Dude', '2015-03-12', 'https://www.youtube.com/embed/RhFMIRuHAL4', 6.6, 'Nothing to say here'),
  ('Dragon', 'Another Random Dude', '2014-09-22', 'https://www.youtube.com/embed/RhFMIRuHAL4', 5.4, 'Nice movie');

INSERT INTO REVIEW (userID, movieID, postdate, reviewtitle, rating, reviewtext)
VALUES (1, 1, '2016-06-11', 'Best shit ever', 10, 'Still best shit I have ever seen'),
  (1, 3, '2016-06-11', 'Best shit ever', 10, 'Still best shit I have ever seen'),
  (2, 2, '2015-06-05', 'Nice', 7, 'Nice movie'),
  (3, 1, '2015-04-20', 'So so', 6, 'Not so nice'),
  (3, 2, '2015-07-01', 'Bad', 3, 'Garbage');






