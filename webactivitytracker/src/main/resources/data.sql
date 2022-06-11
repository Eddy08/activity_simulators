DROP TABLE IF EXISTS ACTIVITY;
CREATE TABLE ACTIVITY(ID INT PRIMARY KEY  AUTO_INCREMENT,
   UNIQUE_ID BIGINT NOT NULL,
   NAME VARCHAR(255) NOT NULL,
   TIME TIMESTAMP NOT NULL,
   DURATION INT NOT NULL

);
select * from ACTIVITY;