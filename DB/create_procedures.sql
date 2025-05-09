DROP PROCEDURE IF EXISTS getSeats$$
DROP PROCEDURE IF EXISTS getEmployees$$
DROP PROCEDURE IF EXISTS getSeatByFloorSeatSeq$$
DROP PROCEDURE IF EXISTS getEmployeeById$$
DROP PROCEDURE IF EXISTS getEmployeeByFloorSeatSeq$$
DROP PROCEDURE IF EXISTS assignSeat$$

CREATE PROCEDURE getSeats()
BEGIN
  SELECT *
  FROM SeatingChart s
  ORDER BY s.FLOOR_NO, s.SEAT_NO;
END$$

CREATE PROCEDURE getEmployees()
BEGIN
  SELECT 
    e.EMP_ID,
    e.NAME,
    e.EMAIL,
    s.FLOOR_NO,
    s.SEAT_NO
  FROM Employee e
  LEFT JOIN SeatingChart s ON e.FLOOR_SEAT_SEQ = s.FLOOR_SEAT_SEQ
  ORDER BY e.EMP_ID;
END$$

CREATE PROCEDURE getSeatByFloorSeatSeq(IN seq INT)
BEGIN
  SELECT *
  FROM SeatingChart
  WHERE FLOOR_SEAT_SEQ = seq;
END$$

CREATE PROCEDURE getEmployeeById(IN p_emp_id CHAR(5))
BEGIN
  SELECT 
    e.EMP_ID,
    e.NAME,
    e.EMAIL,
    s.FLOOR_NO,
    s.SEAT_NO
  FROM Employee e
  LEFT JOIN SeatingChart s ON e.FLOOR_SEAT_SEQ = s.FLOOR_SEAT_SEQ
  WHERE e.EMP_ID = p_emp_id;
END$$

CREATE PROCEDURE getEmployeeByFloorSeatSeq (IN seq INT)
BEGIN
  SELECT 
    e.EMP_ID,
    e.NAME,
    e.EMAIL,
    s.FLOOR_NO,
    s.SEAT_NO
  FROM Employee e
  LEFT JOIN SeatingChart s ON e.FLOOR_SEAT_SEQ = s.FLOOR_SEAT_SEQ
  WHERE e.FLOOR_SEAT_SEQ = seq;
END$$

CREATE PROCEDURE assignSeat(IN p_emp_id CHAR(5), IN seq INT)
BEGIN
  START TRANSACTION;

  -- clear current seat
  UPDATE Employee
  SET FLOOR_SEAT_SEQ = NULL
  WHERE EMP_ID = p_emp_id;

  -- assign new seat
  UPDATE Employee
  SET FLOOR_SEAT_SEQ = seq
  WHERE EMP_ID = p_emp_id;

  COMMIT;
END$$