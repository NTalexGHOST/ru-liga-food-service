create table if not exists restaurant
(
    id uuid not null,
    name varchar(64) not null,
    address point not null,
    status varchar(64) not null,
    constraint restaurant_pk primary key (id)
);

comment on table restaurant is 'Сущность ресторана';
comment on column restaurant.id is 'Идентификатор';
comment on column restaurant.address is 'Физический адрес';
comment on column restaurant.status is 'Статус ресторана';