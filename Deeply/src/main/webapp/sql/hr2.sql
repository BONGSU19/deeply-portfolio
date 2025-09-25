--결제
Create table payment(
pay_num number not null,
pay_status number not null,
pay_product number not null,
pay_price number not null,
pay_bank varchar2(30),
pay_type number not null,
pay_money number,
user_num number not null,
Constraint payment_pk primary key (pay_num),
Constraint payment_fk foreign key (user_num) references duser (user_num)
);
Create sequence payment_seq;

--팔로우(무료)
Create table follow(
follower_num number not null,
follow_num number not null,
follow_date date default sysdate not null,
constraint follow_fk1 foreign key (follow_num) references auser (user_num),
constraint follow_fk2 foreign key (follower_num) references duser (user_num)
);

--공지
Create table notice(
notice_num number not null,
notice_title varchar2(90) not null,
notice_content clob not null,
notice_file varchar2(400) not null,
user_num number not null,
notice_date date default sysdate not null,
notice_update date default sysdate,
Constraint notice_pk primary key (notice_num),
Constraint notice_fk foreign key (user_num) references duser (user_num)
);
Create sequence notice_seq;

--커뮤니티(글)
Create table community(
c_num number not null,
c_category number not null,
c_title varchar2(90) not null,
c_content clob not null,
c_date date default sysdate not null,
c_update date default sysdate,
c_hit number not null,
c_file varchar2(400) not null,
user_num number not null,
Constraint community_pk primary key (c_num),
Constraint community_fk foreign key (user_num) references duser (user_num)
);
Create sequence community_seq;

--댓글
Create table community_reply(
cre_num number not null,
cre_content varchar2(450) not null,
cre_date date default sysdate not null,
cre_update date default sysdate,
cre_file varchar2(400),
user_num number not null,
c_num number not null,
Constraint community_reply_pk primary key (cre_num),
Constraint community_reply_fk1 foreign key (user_num) references duser (user_num),
Constraint community_reply_fk2 foreign key (c_num) references community (c_num)
);
Create sequence community_reply_seq;

--답글(대댓글)
create table community_response(
 pe_num number not null,
 pe_content varchar2(900) not null,
 pe_date date default sysdate not null,
 pe_update date,
 pe_parent_num number not null, --부모글의 번호가 들어감,자식글이 아니라 부모글일 경우 0
 pe_depth number not null, --자식글의 깊이. 부모글의 자식글A 1, 자식글A의 자식글B 2, 부모글일 경우 0
 cre_num number not null,
 user_num number not null,
 constraint community_res_pk primary key (pe_num),
 constraint community_res_fk1 foreign key (cre_num) references community_reply (cre_num),
 constraint community_res_fk2 foreign key (user_num) references duser (user_num) 
);
create sequence response_seq;

--커뮤니티 신고
Create table community_report(
report_num number not null,
report_type varchar2(10) not null,
reported_num number not null,
report_reason varchar2(90) not null,
report_date date default sysdate not null,
report_status number not null,
user_num number not null,
Constraint community_report_pk primary key (report_num),
Constraint community_report_fk foreign key (user_num) references duser (user_num)
);
Create sequence community_report_seq;

--홍보 커뮤니티
Create table community_promotion(
cp_num number not null,
cp_title varchar2(90) not null,
cp_content clob not null,
cp_date date default sysdate not null,
cp_file varchar2(400) not null,
user_num number not null,
Constraint community_promotion_pk primary key (cp_num),
Constraint community_promotion_fk foreign key (user_num) references duser (user_num)
);
Create sequence community_promotion_seq;

--팬(유료)
CREATE TABLE FAN (
    fan_num NUMBER NOT NULL,
    fan_start DATE DEFAULT SYSDATE NOT NULL,
    fan_end DATE,
    fan_artist NUMBER NOT NULL,
    user_num NUMBER NOT NULL,
    fan_status NUMBER DEFAULT 1 NOT NULL,
    CONSTRAINT fan_pk PRIMARY KEY (fan_num),
    CONSTRAINT fan_fk1 FOREIGN KEY (fan_artist) REFERENCES auser (user_num),
    CONSTRAINT fan_fk2 FOREIGN KEY (user_num) REFERENCES duser (user_num)
);
Create sequence fan_seq;