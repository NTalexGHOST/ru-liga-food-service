create sequence if not exists customer_seq;

create table if not exists customer
(
    id bigint not null default nextval('customer_seq'),
    phone varchar(15) not null,
    email varchar(64) not null,
    address text,
    constraint customer_pk primary key (id)
);

comment on table customer is 'Сущность покупателя';
comment on column customer.id is 'Идентификатор';
comment on column customer.phone is 'Номер телефона';
comment on column customer.email is 'Электронная почта';
comment on column customer.address is 'Физический адрес';