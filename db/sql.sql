create database workdb;
use workdb;
CREATE TABLE Works (
    dt DATE NOT NULL,
    task VARCHAR(255) NOT NULL,
    categry_id INTEGER,
    working_hour FLOAT,
    progress INTEGER,
    PRIMARY KEY (dt, task)
);
CREATE TABLE Categry (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    categry CHAR(8) NOT NULL
);