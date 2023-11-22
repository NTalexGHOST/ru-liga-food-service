create table if not exists customer_order
(
    id uuid not null,
    customer_id uuid not null,
    restaurant_id uuid not null,
    status varchar(64) not null,
    courier_id uuid,
    timestamp timestamp not null,
    constraint order_pk primary key (id),
    constraint customer_fk foreign key(customer_id) references customer(id),
    constraint restaurant_fk foreign key(restaurant_id) references restaurant(id),
    constraint courier_fk foreign key(courier_id) references courier(id)
);

comment on table customer_order is 'Сущность заказа';
comment on column customer_order.id is 'Идентификатор';
comment on column customer_order.customer_id is 'Идентификатор покупателя';
comment on column customer_order.restaurant_id is 'Идентификатор ресторана';
comment on column customer_order.status is 'Статус';
comment on column customer_order.courier_id is 'Идентификатор курьера';
comment on column customer_order.timestamp is 'Время заказа';