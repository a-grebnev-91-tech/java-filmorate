CREATE TABLE IF NOT EXISTS mpa_rating
(
    mpa_rating_id INTEGER PRIMARY KEY,
    name          VARCHAR(10) UNIQUE
);

CREATE INDEX IF NOT EXISTS mpa_rating_id_index ON mpa_rating (mpa_rating_id);
CREATE INDEX IF NOT EXISTS mpa_rating_name ON mpa_rating (name);

CREATE TABLE IF NOT EXISTS films
(
    film_id       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    description   VARCHAR(200),
    duration      INTEGER      NOT NULL,
    likes_count   INTEGER,
    name          VARCHAR(100) NOT NULL,
    release_date  DATE         NOT NULL,
    mpa_rating_id INTEGER REFERENCES mpa_rating (mpa_rating_id),
    CONSTRAINT duration_non_negative CHECK (duration >= 0),
    CONSTRAINT like_count_non_negative CHECK (likes_count >= 0),
    CONSTRAINT film_name_not_empty CHECK (name <> ''),
    CONSTRAINT release_date_is_after CHECK (release_date > '1895-12-28')
);

CREATE INDEX IF NOT EXISTS film_id_index ON films (film_id);
CREATE INDEX IF NOT EXISTS film_likes_count_index ON films (likes_count);

CREATE TABLE IF NOT EXISTS users
(
    user_id  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    birthday DATE,
    email    VARCHAR(100) NOT NULL UNIQUE,
    login    varchar(20)  NOT NULL UNIQUE,
    name     varchar(20)
        CONSTRAINT login_not_empty CHECK (login <> ''),
    CONSTRAINT email_not_empty CHECK (email LIKE '%@%')
);

CREATE INDEX IF NOT EXISTS user_id_index ON users (user_id);

CREATE TABLE IF NOT EXISTS film_likes
(
    film_id BIGINT REFERENCES films (film_id),
    user_id BIGINT REFERENCES users (user_id),
    PRIMARY KEY (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS genre
(
    genre_id INTEGER PRIMARY KEY,
    name     VARCHAR(30) UNIQUE
);

CREATE INDEX IF NOT EXISTS genre_id_index ON genre (genre_id);
CREATE INDEX IF NOT EXISTS genre_name ON genre (name);

CREATE TABLE IF NOT EXISTS film_genre
(
    film_id  BIGINT REFERENCES films (film_id),
    genre_id INTEGER REFERENCES genre (genre_id),
    PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS friends
(
    user_id   BIGINT REFERENCES users (user_id),
    friend_id BIGINT REFERENCES users (user_id),
    confirmed BOOL NOT NULL,
    PRIMARY KEY (user_id, friend_id)
);

CREATE INDEX IF NOT EXISTS friends_confirmed ON friends (confirmed);