create table shop_item(
 item_num number not null,
 user_num number not null,
 item_name varchar(100) not null,
 item_price number not null,
 item_description clob not null,
 item_regdate date default sysdate not null,
 item_modifydate date,
 item_stock number not null,
 constraint shop_item_pk primary key (item_num),
 constraint shop_item_fk foreign key (user_num) references auser_detail (user_num)
 constraint shop_item_fk_group foreign key (group_num) references agroup (group_num)
);
create sequence shop_item_seq;

--결제 관련 테이블 생성하면 다시 오라클 등록해야함.
create table shop_order(
 order_num number not null,
 item_num number not null,
 user_num number not null,
 item_quantity number not null,
 total_price number(9) not null,
 order_date date default sysdate not null,
 pay_num number not null,
 item_status number(1) default 1 not null, --1:결제 및 주문완료 2:배송 준비중 3: 배송 중 4: 배송완료 5: 주문 취소
 order_notice varchar(4000),
 constraint shop_order_pk primary key (order_num),
 constraint shop_order_fk_item foreign key (item_num) references shop_item (item_num),
 constraint shop_order_fk_user foreign key (user_num) references auser_detail (user_num),
 constraint shop_order_fk_pay foreign key (pay_num) references payment (pay_num) 
);
create sequence order_num_seq;

create table shop_cart(
 cart_num number not null,
 item_num number not null,
 user_num number not null,
 reg_date date default sysdate not null,
 order_quantity number not null,
 constraint shop_cart_pk primary key(cart_num),
 constraint shop_cart_fk_item foreign key (item_num) references shop_item (item_num),
 constraint shop_cart_fk_user foreign key (user_num) references auser_detail (user_num)
);
create sequence cart_num_seq;

create table shop_like(
 item_num number not null,
 like_date date default sysdate not null,
 user_num number not null,
 constraint shop_like_fk_item foreign key (item_num) references shop_item (item_num),
 constraint shop_like_fk_user foreign key (user_num) references auser_detail (user_num)
);