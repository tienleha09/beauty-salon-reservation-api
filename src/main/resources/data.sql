INSERT INTO service (id, name, description, price) VALUES
  (11, 'Haircut', 'A basic haircut for any hair length.', 25.00),
  (22, 'Hair Color', 'Change your hair color to any shade you desire.', 80.00),
  (33, 'Facial', 'A customized facial to cleanse and rejuvenate your skin.', 50.00),
  (44, 'Manicure', 'A basic manicure to shape and polish your nails.', 20.00),
  (55, 'Pedicure', 'A basic pedicure to shape and polish your toenails.', 25.00),
  (66, 'Waxing', 'Hair removal for any body part using warm wax.', 40.00);
  
  -- Insert employees
INSERT INTO employee (id, first_Name, last_Name, phone, email) VALUES
  (11, 'Jane', 'Smith', '555-1234', 'janesmith@example.com'),
  (12, 'John', 'Doe', '555-5678', 'johndoe@example.com'),
  (13, 'Sarah', 'Johnson', '555-9876', 'sarahjohnson@example.com');

-- Insert employee-service relationships
INSERT INTO employee_service (employee_id, service_id) VALUES
  (11, 11), -- Jane Smith provides Haircut
  (11, 22), -- Jane Smith provides Coloring
  (12, 11), -- John Doe provides Haircut
  (12, 22), -- John Doe provides Hair Color
  (13, 33), -- Sarah Johnson provides Facial
  (13, 66); -- Sarah Johnson provides Waxing
  

--   INSERT INTO employee_availability (id,date, start_time, end_time, is_available, employee_id, service_id) VALUES
--   (11,'2023-04-22', '09:00:00', '17:00:00', 1, 11, 11),
--   (12,'2023-04-22', '09:00:00', '17:00:00', 1, 11, 22),
--   (13,'2023-04-22', '12:00:00', '20:00:00', 1, 12, 11),
--   (14,'2023-04-22', '12:00:00', '20:00:00', 1, 12, 33),
--   (15,'2023-04-22', '09:00:00', '17:00:00', 0, 13, 44),
--   (16,'2023-04-23', '09:00:00', '17:00:00', 1, 11, 11),
--   (17,'2023-04-23', '09:00:00', '17:00:00', 0, 11, 22),
--   (18,'2023-04-23', '12:00:00', '20:00:00', 1, 12, 11),
--   (19,'2023-04-23', '12:00:00', '20:00:00', 1, 12, 33),
--   (20,'2023-04-23', '09:00:00', '17:00:00', 1, 13, 44),
--   (21,'2023-04-24', '09:00:00', '17:00:00', 0, 11, 11);


INSERT INTO customer (id, first_name, last_name, email, phone)
VALUES (1, 'Johny', 'Din', 'johny.din@example.com', '555-3439');

INSERT INTO customer (id, first_name, last_name, email, phone)
VALUES (2, 'Alice', 'Smith', 'alice.smith@example.com', '555-8742');

INSERT INTO customer (id, first_name, last_name, email, phone)
VALUES (3, 'Bob', 'Johnson', 'bob.johnson@example.com', '555-9012');


INSERT INTO reservation (id, date, end_time, is_cancelled, start_time, customer_id, employee_id, service_id)
VALUES (1000, '2023-04-25', '14:00:00', false, '12:00:00', 1, 11, 11);

INSERT INTO reservation (id, date, end_time, is_cancelled, start_time, customer_id, employee_id, service_id)
VALUES (1002, '2023-04-26', '17:00:00', false, '15:00:00', 2, 13, 22);
INSERT INTO reservation (id, date, end_time, is_cancelled, start_time, customer_id, employee_id, service_id)
VALUES (1003, '2023-04-27', '16:00:00', false, '14:00:00', 3, 12, 33);

INSERT INTO reservation (id, date, end_time, is_cancelled, start_time, customer_id, employee_id, service_id)
VALUES (1004, '2023-04-28', '12:00:00', true, '10:00:00', 1, 11, 22);

INSERT INTO reservation (id, date, end_time, is_cancelled, start_time, customer_id, employee_id, service_id)
VALUES (1005, '2023-03-29', '15:00:00', false, '13:00:00', 2, 13, 11);


INSERT INTO employee_schedule (id, employee_id, date, start_time, end_time)
VALUES (1,11, '2023-04-25', '08:00:00', '16:00:00'),
       (2,11, '2023-04-26', '09:00:00', '17:00:00'),
       (3,12, '2023-04-25', '10:00:00', '18:00:00');


