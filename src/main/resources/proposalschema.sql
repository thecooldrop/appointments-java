-- Integrity constraints implemented up to now:
-- - A provider can not be at multiple locations at same time
-- - An appointment can only be booked at locations where provider is
-- - A provider can not be booked for multiple appointments at same time
-- Integrity constraints missing:
-- - Right now an appointment can be provided by multiple providers
-- - An appointment can span multiple locations, due to being split across multiple providers
-- -


-- Integrity constraints to check:
-- - A provider can only be at single location at single time
-- - A provider can only have single appointment at single time
-- - A single appointment is only related to single provider
-- - An appointment can only be related to single location
-- - An appointment for given provider and given location can only be made if the provider is at given location at given time

-- Naive:
-- Idea: maybe we can add an nullable foreign key from provider_location_time to appointment
----------------------------------------------------------------------------------------------------------------
CREATE EXTENSION citext;

create table location (
    id serial primary key,
    name varchar(255) not null check(name = lower(name)),
    unique(name)
);
create table provider (
    id serial primary key,
    first_name varchar(128) not null check(first_name = lower(first_name)),
    last_name varchar(128) not null check(last_name = lower(last_name))
);
create table service (
    id serial primary key,
    service_price_id integer not null,
    name varchar(255) not null check(name = lower(name)),
    description text not null,
    duration_minutes integer not null check(duration_minutes > 0),
    unique(name),
    unique(service_price_id),
    foreign key (service_price_id) references service_price(id) on delete restrict on update cascade
);
create table service_price (
    id serial primary key,
    price_unit integer not null check(price_km >= 0),
    price_hundredth integer not null check(price_fening >= 0)
);
create table appointment (
    id serial primary key,
    service_id integer not null,
    customer_id integer not null,
    foreign key (customer_id) references customer_data(id) on delete restrict on update cascade
);
create table customer_data (
    id serial primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    customer_phone text check (customer_phone like '+3876[1-6][0-9][0-9][0-9][0-9][0-9][0-9][0-9]?'),
    customer_mail citext
);
create table provider_location_time (
    id serial primary key,
    provider_id integer not null,
    location_id integer not null,
    time_block timestamp not null check (cast(extract(minute from time_block) as integer) % 5 = 0 and cast(extract(second from time_block) as integer) = 0),
    appointment_id integer,
    foreign key (provider_id) references provider(id) on delete restrict on update cascade,
    foreign key (location_id) references location(id) on delete restrict on update cascade,
    foreign key (appointment_id) references appointment(id) on delete restrict on update cascade,
    constraint PROVIDER_AT_SINGLE_LOCATION_AT_A_TIME unique(provider_id, time_block), -- Provider can be at single location at given time
    constraint APPOINTMENT_BOOKS_TIME_ONCE unique(appointment_id, time_block), -- appointment books a single time block once
    constraint APPOINTMENT_PROVIDED_BY_SINGLE_PROVIDER exclude using gist (provider_id WITH <>, appointment_id WITH =),
    constraint APPOINTMENT_PROVIDED_AT_SINGLE_LOCATION exclude using gist (appointment_id with =, location_id with <>)
);