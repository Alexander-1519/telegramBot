CREATE TABLE Users(
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT NOT NULL
);

CREATE TABLE Facts(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(2500) NOT NULL
);