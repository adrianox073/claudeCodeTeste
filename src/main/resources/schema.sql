CREATE TABLE IF NOT EXISTS mensaje (
    id        IDENTITY PRIMARY KEY,
    texto     VARCHAR(1000),
    resultado VARCHAR(1000) NOT NULL,
    fecha     TIMESTAMP     NOT NULL
);
