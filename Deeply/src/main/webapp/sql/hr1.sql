--채팅방 테이블
create table chatroom(
auser_num number not null, 
chat_num number not null, --채팅 방 번호 pk
chat_name varchar2(10) not null, --채팅방 이름
chat_status number(1) default 0 not null, --1이면 방이 활성화
constraint chatroom_pk primary key (chat_num) 
);

create sequence chatroom_seq;

--메세지 테이블
create table chatmsg(
chatmsg_num number not null, --pk 메세지 번호
chat_num number not null,  --fk, 채팅방 번호
chat_user_num number not null, --fk, 유저 번호
chat_msg varchar2(200) not null, --메세지
reply_status number(10) default 0 not null,
constraint chatmsg_pk primary key (chatmsg_num),
constraint chatmsg_chat_fk foreign key (chat_num) references chatroom (chat_num),
constraint chatmsg_user_fk foreign key (chat_user_num) references chat (chat_user_num)
);

create sequence chatmsg_seq;


--채팅에 참여하는 artist
create table achat(
auser_num number not null, --아티스트 번호, fk
auser_id varchar2(10) not null, --아티스트 아이디 ,fk
constraint achat_fk_num foreign key (auser_num) references auser (user_num),
constraint achat_fk_id foreign key (auser_id) references auser (id)
);

--채팅에 참여하는 유저

create table dchat(
duser_num number not null, --아티스트 번호, fk
duser_id varchar2(10) not null, --아티스트 아이디 ,fk
constraint dchat_fk_num foreign key (duser_num) references duser (user_num),
constraint dchat_fk_id foreign key (duser_id) references duser (id)
);



--중간 테이블
create table chat(
chat_user_num number not null, --채팅방에 들어온 user 번호, 두 테이블에서 들어오는 거라서 fk 될 수 없음
chat_num number not null, --채팅방 번호, fk
chat_id varchar2(10) not null, --두 테이블에서 넘어오는 거라서 fk가 될 수 없음
chat_kind number(1) default 0 not null, --0이면 유료 유저 1이면 artist
constraint chat_fk foreign key (chat_num) references chatroom (chat_num),

);
--unique 제약조건 추가
ALTER TABLE chat 
ADD CONSTRAINT chat_user_uniq UNIQUE (chat_user_num);
create sequence chatmsg_seq;

ALTER TABLE chatroom
ADD CONSTRAINT fk_auser_num FOREIGN KEY (auser_num)references
 auser (user_num);
 
 
 create table alarm(
 
 al_num number not null,
 user_num number not null, 
 al_title varchar2(100) not null,
 al_kind number(5) not null,
 al_status number(1) default 0 not null,
 constraint alarm_pk primary key (al_num),
 constraint alarm_fk foreign key (user_num) references duser (user_num)

 );
 
 create sequence alarm_seq;
