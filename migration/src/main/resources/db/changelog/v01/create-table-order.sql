create sequence if not exists order_seq;

create table if not exists order
(
    id bigint not null default nextval('order_seq'),
    customer_id bigint not null,
    restaurant_id bigint not null,
    status varchar(64) not null,
    courier_id bigint,
    timestamp timestamp not null,
    constraint order_pk primary key (id),
    constraint customer_fk foreign key(customer_id) references customer(id),
    constraint restaurant_fk foreign key(restaurant_id) references restaurant(id),
    constraint courier_fk foreign key(courier_id) references courier(id)
);

comment on table order is 'Сущность заказа';
comment on column order.id is 'Идентификатор';
comment on column order.customer_id is 'Идентификатор покупателя';
comment on column order.restaurant_id is 'Идентификатор ресторана';
comment on column order.status is 'Статус';
comment on column order.courier_id is 'Идентификатор курьера';
comment on column order.timestamp is 'Время заказа';