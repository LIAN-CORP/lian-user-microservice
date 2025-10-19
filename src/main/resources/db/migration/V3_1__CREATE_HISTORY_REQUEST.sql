CREATE TABLE IF NOT EXISTS history_request(
    id UUID DEFAULT uuid_generate_v4() NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    status VARCHAR(255),
    created_at DATE DEFAULT CURRENT_DATE NOT NULL,
    reason VARCHAR(255),
    PRIMARY KEY (id)
);