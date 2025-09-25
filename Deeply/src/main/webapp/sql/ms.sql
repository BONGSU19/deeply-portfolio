CREATE TABLE duser (
    user_num NUMBER PRIMARY KEY, -- 유저 번호 (고유 키)
    id VARCHAR2(10) NOT NULL UNIQUE, -- 유저 ID (고유, 로그인 시 사용)
    nick_name VARCHAR2(30) NOT NULL UNIQUE, -- 유저 닉네임 (고유)
    auth NUMBER(1) default 0 NOT NULL -- 유저 등급
);

CREATE TABLE duser_detail (
    user_num NUMBER PRIMARY KEY, -- 유저 번호 (고유 키, 외래 키)
    name VARCHAR2(30) NOT NULL, -- 유저 이름 (본명)
    passwd_hash VARCHAR2(60) NOT NULL, -- 비밀번호 (해싱된 값)
    email VARCHAR2(50) NOT NULL, -- 이메일
    verification_code VARCHAR2(10) NOT NULL, -- 인증 번호
    is_verified CHAR(1) DEFAULT 'N', -- 인증 여부 (기본값 'N')
    code_expiration DATE, -- 인증 유효 기간
    zipcode VARCHAR2(5) NOT NULL, -- 우편번호
    address1 VARCHAR2(90) NOT NULL, -- 집 주소
    address2 VARCHAR2(90), -- 상세 주소
    photo VARCHAR2(255), -- 프로필 정보 저장 (필요 시 NOT NULL 추가)
    photo_name VARCHAR2(255), -- 프로필 이름 (필요 시 NOT NULL 추가)
    phone VARCHAR2(15) NOT NULL, -- 전화번호
    social_name VARCHAR2(10) UNIQUE, -- 소셜 아이디 (고유)
    two_factor_enabled CHAR(1) DEFAULT 'N', -- 2단계 인증 여부 (기본값 'N')
    automatic_login CHAR(1) DEFAULT 'N', -- 자동 로그인 여부 (기본값 'N')
    user_bal NUMBER(10, 2) DEFAULT 0 NOT NULL, -- 사용자 보유 금액 (기본값 0)
    CONSTRAINT fk_user_detail_user FOREIGN KEY (user_num) REFERENCES duser (user_num)
);

CREATE TABLE duser_detail (
    user_num NUMBER PRIMARY KEY, -- 유저 번호 (고유 키, 외래 키)
    name VARCHAR2(30) NOT NULL, -- 유저 이름 (본명)
    passwd_hash VARCHAR2(60) NOT NULL, -- 비밀번호 (해싱된 값)
    email VARCHAR2(50) NOT NULL, -- 이메일
    verification_code VARCHAR2(10) NOT NULL, -- 인증 번호
    is_verified CHAR(1) DEFAULT 'N', -- 인증 여부 (기본값 'N')
    code_expiration DATE, -- 인증 유효 기간
    zipcode VARCHAR2(5) NOT NULL, -- 우편번호
    address1 VARCHAR2(90) NOT NULL, -- 집 주소
    address2 VARCHAR2(90), -- 상세 주소
    photo VARCHAR2(255), -- 프로필 정보 저장 (필요 시 NOT NULL 추가)
    photo_name VARCHAR2(255), -- 프로필 이름 (필요 시 NOT NULL 추가)
    phone VARCHAR2(15) NOT NULL, -- 전화번호
    social_name VARCHAR2(10) UNIQUE, -- 소셜 아이디 (고유)
    two_factor_enabled CHAR(1) DEFAULT 'N', -- 2단계 인증 여부 (기본값 'N')
    automatic_login CHAR(1) DEFAULT 'N', -- 자동 로그인 여부 (기본값 'N')
    user_bal NUMBER(10, 2) DEFAULT 0 NOT NULL, -- 사용자 보유 금액 (기본값 0)
    CONSTRAINT fk_user_detail_user FOREIGN KEY (user_num) REFERENCES duser (user_num)
);

CREATE TABLE user_two_factor_auth (
    user_num NUMBER NOT NULL,                     -- 유저 번호
    auth_method VARCHAR2(20 BYTE),                -- 인증 방법
    verification_code VARCHAR2(10 BYTE),          -- 2단계 인증 코드
    code_expiration DATE,                         -- 인증 코드 만료 시간
    is_verified CHAR(1) DEFAULT 'N',              -- 인증 여부 (기본값 'N')
    CONSTRAINT pk_user_two_factor_auth PRIMARY KEY (user_num),  -- 유저 번호를 기본 키로 설정
    CONSTRAINT fk_user_num FOREIGN KEY (user_num) REFERENCES dusers(user_num)  -- 외래 키 제약 조건 (users 테이블을 참조)
);
--자동로그인테이블
create table persistent_logins(
 series varchar2(64) primary key,
 username varchar2(64) not null,
 token varchar2(64) not null,
 last_used timestamp not null
);

--duser 시퀀스
CREATE SEQUENCE SEQ_USER_NUM
START WITH 1          
INCREMENT BY 1        
NOCACHE               
NOCYCLE; 