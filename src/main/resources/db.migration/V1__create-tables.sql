CREATE TABLE work_items (
    id uuid NOT NULL,
    event_date timestamp NOT NULL,
    description varchar(255) NOT NULL,
    created_datetime timestamp NOT NULL,
    duration_amount integer NOT NULL,
    duration_unit varchar(10),
    PRIMARY KEY (id)
);
