create table if not exists customer
(
    id uuid not null,
    phone varchar(32) not null,
    email varchar(64) not null,
    address point,
    constraint customer_pk primary key (id)
);

comment on table customer is 'Сущность покупателя';
comment on column customer.id is 'Идентификатор';
comment on column customer.phone is 'Номер телефона';
comment on column customer.email is 'Электронная почта';
comment on column customer.address is 'Физический адрес';