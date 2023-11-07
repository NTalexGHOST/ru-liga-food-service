create table if not exists courier
(
    id uuid not null,
    phone varchar(32) not null,
    status varchar(64) not null,
    coordinates point,
    constraint courier_pk primary key (id)
);

comment on table courier is 'Сущность курьера';
comment on column courier.id is 'Идентификатор';
comment on column courier.phone is 'Номер телефона';
comment on column courier.status is 'Статус';
comment on column courier.coordinates is 'Координаты';