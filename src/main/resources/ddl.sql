DROP DATABASE IF EXISTS moviedb;
CREATE DATABASE moviedb;
USE moviedb;

DROP TABLE IF EXISTS USER;

CREATE TABLE USER (
  ID        BIGINT       NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(255) NOT NULL,
  PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS MOVIE;

CREATE TABLE MOVIE (
  ID           BIGINT       NOT NULL AUTO_INCREMENT,
  movie_name   VARCHAR(255) NOT NULL,
  director     VARCHAR(255),
  release_date DATE,
  trailer_url  VARCHAR(100),
  rating       DOUBLE,
  description  TEXT,
  PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS REVIEW;

CREATE TABLE REVIEW (
  ID           BIGINT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (ID),
  user_ID      BIGINT NOT NULL,
  FOREIGN KEY (user_ID)
  REFERENCES USER (ID)
    ON DELETE CASCADE,
  movie_ID     BIGINT NOT NULL,
  FOREIGN KEY (movie_ID)
  REFERENCES MOVIE (ID)
    ON DELETE CASCADE,
  post_date    DATE,
  review_title VARCHAR(100),
  rating       INT,
  review_text  TEXT
);

INSERT INTO USER (user_name) VALUES ('Lothar'), ('Medivh'), ('Neltarion');

INSERT INTO MOVIE (movie_name, director, release_date, trailer_url, rating, description)
VALUES ('Warcraft', 'Duncan Jones', '2016-06-10', 'https://www.youtube.com/embed/RhFMIRuHAL4', 7.4,
        'As an Orc horde invades the planet Azeroth using a magic portal, a few human heroes and dissenting Orcs must attempt to stop the true evil behind this war.'),
  ('Tempo', 'Random Dude', '2015-03-12', 'https://www.youtube.com/embed/RhFMIRuHAL4', 6.6, 'Nothing to say here'),
  ('Dragon', 'Another Random Dude', '2014-09-22', 'https://www.youtube.com/embed/RhFMIRuHAL4', 5.4, 'Nice movie');

INSERT INTO REVIEW (user_ID, movie_ID, post_date, review_title, rating, review_text)
VALUES (1, 1, '2016-06-11', 'Best shit ever', 10, 'Still best shit I have ever seen'),
  (1, 3, '2016-06-11', 'Best shit ever', 10, 'Still best shit I have ever seen'),
  (2, 2, '2015-06-05', 'Nice', 7, 'Nice movie'),
  (3, 1, '2015-04-20', 'So so', 6, 'Not so nice'),
  (3, 2, '2015-07-01', 'Bad', 3, 'Garbage');



