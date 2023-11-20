CREATE DATABASE servlet_db;


CREATE TABLE characters (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    status VARCHAR(255),
    species VARCHAR(255),
    gender VARCHAR(255),
    image VARCHAR(255),
    episodes TEXT,
    created timestamp,
    updated_at timestamp
);