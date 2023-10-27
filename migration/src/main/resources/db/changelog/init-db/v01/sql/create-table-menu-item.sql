create sequence if not exists menu_item_seq;

create table if not exists menu_item
(
    id bigint not null default nextval('menu_item_seq'),
    restaurant_id bigint not null,
    name varchar(64) not null,
    price decimal not null,
    image text,
    description text,
    constraint menu_item_pk primary key (id),
    constraint restaurant_fk foreign key(restaurant_id) references restaurant(id)
);

comment on table menu_item is 'Сущность позиции в ресторане';
comment on column menu_item.id is 'Идентификатор';
comment on column menu_item.restaurant_id is 'Идентификатор ресторана';
comment on column menu_item.name is 'Название';
comment on column menu_item.price is 'Цена';
comment on column menu_item.image is 'Фотография';
comment on column menu_item.description is 'Описание';