CREATE TABLE workshop (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    date TIMESTAMP NOT NULL,
    max_capacity INT NOT NULL,
    current_attendees INT NOT NULL DEFAULT 0,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE ticket (
    id BIGSERIAL PRIMARY KEY,
    workshop_id BIGINT NOT NULL,
    attendee_email VARCHAR(255) NOT NULL,
    purchase_date TIMESTAMP NOT NULL,
    price DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_ticket_workshop FOREIGN KEY (workshop_id) REFERENCES workshop(id)
);