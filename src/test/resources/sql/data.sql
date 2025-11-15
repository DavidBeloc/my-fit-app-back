INSERT INTO users (id, name, gender, date_of_birth, weight, height, password, role, phone_number, username)
VALUES (1, 'David', 'MALE', '1999-10-01', 76.5, 180, '1234', 'USER', '+79154488964', 'test1@mail.ru'),
       (2, 'Alina', 'FEMALE', '1997-06-23', 60.5, 167, '12345', 'USER', '+79111115544', 'test2@mail.ru');
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO dates (id, date, user_id)
VALUES (1, '2025-01-01', 1),
       (2, '2025-01-02', 1),
       (3, '2025-02-01', 1),
       (4, '2025-02-02', 1),
       (5, '2025-01-01', 2),
       (6, '2025-01-02', 2),
       (7, '2025-02-01', 2),
       (8, '2025-02-02', 2);
SELECT SETVAL('dates_id_seq', (SELECT MAX(id) FROM dates));

INSERT INTO nutritions (id, proteins, carbohydrates, fats, date_id)
VALUES (1, 110, 400, 20, 1),
       (2, 100, 420, 21, 2),
       (3, 115, 500, 30, 3),
       (4, 70, 410, 40, 4),
       (5, 135, 720, 35, 5),
       (6, 80, 410, 50, 6);
SELECT SETVAL('nutritions_id_seq', (SELECT MAX(id) FROM nutritions));

INSERT INTO workouts (id, date_id, user_id)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 3, 1),
       (4, 4, 1),
       (5, 5, 2),
       (6, 6, 2),
       (7, 7, 2),
       (8, 8, 2),
       (9, 1, 1);
SELECT SETVAL('workouts_id_seq', (SELECT MAX(id) FROM workouts));

INSERT INTO exercises (id, workout_id, exercise_type)
VALUES (1, 1, 'CARDIO'),
       (2, 1, 'CALISTHENICS'),
       (3, 1, 'STRENGTH'),
       (4, 1, 'STRENGTH'),
       (5, 2, 'STRENGTH'),
       (6, 2, 'CARDIO'),
       (7, 3, 'CARDIO'),
       (8, 4, 'CALISTHENICS'),
       (9, 5, 'CALISTHENICS'),
       (10, 5, 'STRENGTH');
SELECT SETVAL('exercises_id_seq', (SELECT MAX(id) FROM exercises));

INSERT INTO cardio (id, exercise_id, name, distance, report)
VALUES (1, 1, 'Бег с уклоном', 2500, 'Отчет по пробежке');
SELECT SETVAL('cardio_id_seq', (SELECT MAX(id) FROM cardio));

INSERT INTO calisthenics (id, exercise_id, name, repeat, report)
VALUES (1, 2, 'Отжимания', 25, 'Отчет по упражнению');
SELECT SETVAL('calisthenics_id_seq', (SELECT MAX(id) FROM calisthenics));

INSERT INTO strength (id, exercise_id, name, weight, repeat, report)
VALUES (1, 3, 'Жим лежа', 37.5, 10, 'Отчет по упражнению');
SELECT SETVAL('strength_id_seq', (SELECT MAX(id) FROM strength));