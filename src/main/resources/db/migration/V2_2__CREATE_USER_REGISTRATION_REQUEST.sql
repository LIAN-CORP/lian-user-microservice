CREATE TABLE IF NOT EXISTS user_registration_request(
    id UUID DEFAULT uuid_generate_v4() NOT NULL,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    email varchar(320) NOT NULL,
    "password" varchar(255) NOT NULL,
    birthday date NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at DATE DEFAULT CURRENT_DATE NOT NULL,
    reviewed_by UUID NOT NULL,
    CONSTRAINT user_email_check_registration CHECK (((email)::text ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'::text)),
    CONSTRAINT user_email_key_registration UNIQUE (email),
    PRIMARY KEY (id),
    CONSTRAINT reviewed_by_user_fk FOREIGN KEY (reviewed_by) REFERENCES _user(id) ON DELETE CASCADE ON UPDATE CASCADE
);