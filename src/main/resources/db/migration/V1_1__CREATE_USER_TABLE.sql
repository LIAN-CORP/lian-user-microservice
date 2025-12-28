CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS public._user (
                              id uuid DEFAULT uuid_generate_v4() NOT NULL,
                              first_name varchar(255) NOT NULL,
                              last_name varchar(255) NOT NULL,
                              email varchar(320) NOT NULL,
                              "password" varchar(255) NOT NULL,
                              birthday date NOT NULL,
                              CONSTRAINT user_email_check CHECK (((email)::text ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'::text)),
	CONSTRAINT user_email_key UNIQUE (email),
	CONSTRAINT user_pkey PRIMARY KEY (id)
);