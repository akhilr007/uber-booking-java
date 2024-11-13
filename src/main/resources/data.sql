INSERT INTO app_user (id, email, name, password)
VALUES
  (1, 'john.doe@example.com', 'John Doe', 'password1'),
  (2, 'jane.smith@example.com', 'Jane Smith', 'password2'),
  (3, 'michael.jones@example.com', 'Michael Jones', 'password3'),
  (4, 'emily.davis@example.com', 'Emily Davis', 'password4'),
  (5, 'daniel.brown@example.com', 'Daniel Brown', 'password5'),
  (6, 'sarah.wilson@example.com', 'Sarah Wilson', 'password6'),
  (7, 'chris.moore@example.com', 'Chris Moore', 'password7'),
  (8, 'laura.taylor@example.com', 'Laura Taylor', 'password8'),
  (9, 'joshua.anderson@example.com', 'Joshua Anderson', 'password9'),
  (10, 'olivia.thomas@example.com', 'Olivia Thomas', 'password10'),
  (11, 'david.jackson@example.com', 'David Jackson', 'password11'),
  (12, 'lisa.white@example.com', 'Lisa White', 'password12'),
  (13, 'kevin.harris@example.com', 'Kevin Harris', 'password13'),
  (14, 'ashley.martin@example.com', 'Ashley Martin', 'password14'),
  (15, 'ryan.lee@example.com', 'Ryan Lee', 'password15'),
  (16, 'megan.walker@example.com', 'Megan Walker', 'password16'),
  (17, 'brian.hall@example.com', 'Brian Hall', 'password17'),
  (18, 'emma.young@example.com', 'Emma Young', 'password18'),
  (19, 'justin.king@example.com', 'Justin King', 'password19'),
  (20, 'sophia.scott@example.com', 'Sophia Scott', 'password20');

INSERT INTO user_roles (user_id, roles) VALUES
  (1, 'RIDER'),
  (1, 'DRIVER'),
  (2, 'RIDER'),
  (2, 'DRIVER'),
  (3, 'RIDER'),
  (3, 'DRIVER'),
  (4, 'RIDER'),
  (4, 'DRIVER'),
  (5, 'RIDER'),
  (5, 'DRIVER'),
  (6, 'RIDER'),
  (6, 'DRIVER'),
  (7, 'RIDER'),
  (7, 'DRIVER'),
  (8, 'RIDER'),
  (8, 'DRIVER'),
  (9, 'RIDER'),
  (9, 'DRIVER'),
  (10, 'RIDER'),
  (10, 'DRIVER'),
  (11, 'RIDER'),
  (12, 'RIDER'),
  (13, 'RIDER'),
  (14, 'RIDER'),
  (15, 'RIDER'),
  (16, 'RIDER'),
  (17, 'RIDER'),
  (18, 'RIDER'),
  (19, 'RIDER'),
  (20, 'RIDER');

INSERT INTO vehicle (id, name, vehicle_type)
VALUES
  (1, 'Toyota Camry', 'SEDAN'),
  (2, 'Honda Civic', 'SEDAN'),
  (3, 'Ford Focus', 'HATCHBACK'),
  (4, 'Hyundai Elantra', 'SEDAN'),
  (5, 'Chevrolet Equinox', 'SUV'),
  (6, 'Nissan Rogue', 'SUV'),
  (7, 'Volkswagen Golf', 'HATCHBACK'),
  (8, 'Kia Soul', 'HATCHBACK'),
  (9, 'Toyota RAV4', 'SUV'),
  (10, 'Mazda CX-5', 'SUV');

INSERT INTO driver (id, user_id, vehicle_id, available, rating, current_location)
VALUES
  (1, 1, 1, TRUE, 4.5, ST_GeomFromText('POINT(-118.2437 34.0522)', 4326)),   -- Los Angeles, CA
  (2, 2, 2, FALSE, 3.8, ST_GeomFromText('POINT(-121.8863 37.3382)', 4326)),  -- San Jose, CA
  (3, 3, 3, TRUE, 4.2, ST_GeomFromText('POINT(-119.4179 36.7783)', 4326)),   -- Fresno, CA
  (4, 4, 4, TRUE, 4.9, ST_GeomFromText('POINT(-118.1503 33.8303)', 4326)),   -- Long Beach, CA
  (5, 5, 5, FALSE, 3.5, ST_GeomFromText('POINT(-118.2201 34.0736)', 4326)),  -- Burbank, CA
  (6, 6, 6, TRUE, 4.7, ST_GeomFromText('POINT(-117.1611 32.7157)', 4326)),   -- San Diego, CA
  (7, 7, 7, FALSE, 4.0, ST_GeomFromText('POINT(-120.6552 35.2828)', 4326)),  -- Santa Barbara, CA
  (8, 8, 8, TRUE, 3.9, ST_GeomFromText('POINT(-122.4194 37.7749)', 4326)),   -- San Francisco, CA
  (9, 9, 9, TRUE, 4.6, ST_GeomFromText('POINT(-118.2505 34.0736)', 4326)),   -- Pasadena, CA
  (10, 10, 10, FALSE, 4.3, ST_GeomFromText('POINT(-119.8310 36.7468)', 4326)); -- Visalia, CA



INSERT INTO rider (id, user_id, rating)
VALUES
  (1, 11, 4.2),
  (2, 12, 3.9),
  (3, 13, 4.5),
  (4, 14, 4.1),
  (5, 15, 3.7),
  (6, 16, 4.8),
  (7, 17, 3.6),
  (8, 18, 4.3),
  (9, 19, 3.9),
  (10, 20, 4.0);

INSERT INTO wallet (id, user_id, balance)
VALUES
  (1, 11, 1000.0),
  (2, 12, 6000.0),
  (3, 1, 120.0);

