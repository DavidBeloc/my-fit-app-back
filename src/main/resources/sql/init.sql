CREATE TABLE IF NOT EXISTS users
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(20)        NOT NULL,
    gender        VARCHAR(10),
    date_of_birth DATE,
    weight        DOUBLE PRECISION,
    height        DOUBLE PRECISION,
    password      VARCHAR(128),
    role          VARCHAR(10),
    phone_number  VARCHAR(20),
    username      VARCHAR(64) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS dates
(
    id      BIGSERIAL PRIMARY KEY,
    date    DATE   NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users (id),
    CONSTRAINT unique_date_per_user UNIQUE (date, user_id)
);

CREATE TABLE IF NOT EXISTS nutritions
(
    id            BIGSERIAL PRIMARY KEY,
    proteins      SMALLINT,
    carbohydrates SMALLINT,
    fats          SMALLINT,
    date_id       BIGINT UNIQUE NOT NULL REFERENCES dates (id)
);

CREATE TABLE IF NOT EXISTS workouts
(
    id      BIGSERIAL PRIMARY KEY,
    date_id BIGINT NOT NULL REFERENCES dates (id),
    user_id BIGINT NOT NULL REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS exercises
(
    id            BIGSERIAL PRIMARY KEY,
    workout_id    BIGINT NOT NULL REFERENCES workouts (id),
    exercise_type VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS cardio
(
    id          BIGSERIAL PRIMARY KEY,
    exercise_id BIGINT NOT NULL UNIQUE REFERENCES exercises (id),
    name        VARCHAR(50),
    distance    SMALLINT,
    report      VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS calisthenics
(
    id          BIGSERIAL PRIMARY KEY,
    exercise_id BIGINT NOT NULL UNIQUE REFERENCES exercises (id),
    name        VARCHAR(50),
    repeat      SMALLINT,
    report      VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS strength
(
    id          BIGSERIAL PRIMARY KEY,
    exercise_id BIGINT NOT NULL UNIQUE REFERENCES exercises (id),
    name        VARCHAR(50),
    weight      DOUBLE PRECISION,
    repeat      SMALLINT,
    report      VARCHAR(128)
);