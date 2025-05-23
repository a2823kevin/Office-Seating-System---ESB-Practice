CREATE TABLE IF NOT EXISTS SeatingChart (
  FLOOR_SEAT_SEQ INT AUTO_INCREMENT PRIMARY KEY,
  FLOOR_NO INT NOT NULL,
  SEAT_NO INT NOT NULL
)$$

CREATE TABLE IF NOT EXISTS Employee (
    EMP_ID CHAR(5) PRIMARY KEY,
    NAME VARCHAR(100) NOT NULL, 
    EMAIL VARCHAR(100) NOT NULL,
    FLOOR_SEAT_SEQ INT,
    FOREIGN KEY(FLOOR_SEAT_SEQ) REFERENCES SeatingChart(FLOOR_SEAT_SEQ)
)$$