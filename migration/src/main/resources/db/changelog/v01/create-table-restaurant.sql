create sequence if not exists restaurant_seq;

create table if not exists restaurant
(
    id bigint not null default nextval('restaurant_seq'),
    name varchar(64) not null,
    address point not null,
    status varchar(64) not null,
    constraint restaurant_pk primary key (id)
);

comment on table restaurant is 'Сущность ресторана';
comment on column restaurant.id is 'Идентификатор';
comment on column restaurant.address is 'Физический адрес';
comment on column restaurant.status is 'Статус ресторана';