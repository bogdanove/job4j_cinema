create table if not exists files
(
    id   serial primary key,
    name varchar not null,
    path varchar not null unique
);

create table if not exists genres
(
    id   serial primary key,
    name varchar unique not null
);

create table if not exists films
(
    id                  serial primary key,
    name                varchar                    not null,
    description         varchar                    not null,
    year                int                        not null,
    genre_id            int references genres (id) not null,
    minimal_age         int                        not null,
    duration_in_minutes int                        not null,
    file_id             int references files (id)  not null
);

create table if not exists halls
(
    id          serial primary key,
    name        varchar not null,
    row_count   int     not null,
    place_count int     not null,
    description varchar not null
);

create table if not exists film_sessions
(
    id         serial primary key,
    film_id    int references films (id) not null,
    halls_id   int references halls (id) not null,
    start_time timestamp,
    end_time   timestamp,
    price      int                       not null
);

create table if not exists users
(
    id        serial primary key,
    full_name varchar        not null,
    email     varchar unique not null,
    password  varchar        not null
);

create table if not exists tickets
(
    id           serial primary key,
    session_id   int references film_sessions (id) not null,
    row_number   int                               not null,
    place_number int                               not null,
    user_id      int                               not null,
    unique (session_id, user_id, row_number, place_number)
);

insert into files(name, path) values('green_mile.jpg', 'db/files/green_mile.jpg');
insert into files(name, path) values('shenk.jpg', 'db/files/shenk.jpg');
insert into files(name, path) values('shindlers_list.jpg', 'db/files/shindlers_list.jpg');

insert into genres(name) values ('драма');
insert into genres(name) values ('биография');

insert into films(name, description, year, genre_id, minimal_age, duration_in_minutes, file_id)
values ('Зеленая миля', 'В тюрьме для смертников появляется заключенный с божественным даром.',
       1999, 1, 16, 189, 1);
insert into films(name, description, year, genre_id, minimal_age, duration_in_minutes, file_id)
values ('Список Шиндлера', 'История немецкого промышленника, спасшего тысячи жизней во время Холокоста.',
        1993, 2, 16, 195, 3);
insert into films(name, description, year, genre_id, minimal_age, duration_in_minutes, file_id)
values ('Побег из Шоушенка', 'Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием.',
        1994, 1, 16, 142, 2);

