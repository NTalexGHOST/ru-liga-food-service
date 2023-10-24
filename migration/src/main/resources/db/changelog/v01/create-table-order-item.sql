create sequence if not exists order_item_seq;

create table if not exists order_item
(
    id bigint not null default nextval('order_item_seq'),
    order_id bigint not null,
    menu_item_id bigint not null,
    price decimal not null,
    quantity smallint not null,
    constraint order_item_pk primary key (id),
    constraint order_fk foreign key(order_id) references order(id)
);

comment on table order_item is 'Сущность позиции в заказе';
comment on column order_item.id is 'Идентификатор';
comment on column order_item.order_id is 'Идентификатор заказа';
comment on column order_item.menu_item_id is 'Идентификатор позиции в ресторане';
comment on column order_item.price is 'Цена в момент заказа';
comment on column order_item.quantity is 'Количество';