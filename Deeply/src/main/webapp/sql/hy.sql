CREATE TABLE video (
    video_id NUMBER PRIMARY KEY, -- 시퀀스를 통해 값이 자동으로 할당됨
    artist_id NUMBER NOT NULL, -- 아티스트 ID (FK)
    title VARCHAR2(100 CHAR) NOT NULL, -- 영상 제목
    description CLOB, -- 영상 설명
    is_exclusive NUMBER(1) DEFAULT 0 NOT NULL, -- 0: 무료, 1: 유료
    media_url VARCHAR2(500 CHAR), -- 영상 파일 경로 또는 URL
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 업로드 일시
    views NUMBER DEFAULT 0, -- 조회수
    likes NUMBER DEFAULT 0, -- 좋아요 수
    comments_count NUMBER DEFAULT 0, -- 댓글 수
    category_id NUMBER NOT NULL -- 카테고리 ID (FK)
);

CREATE SEQUENCE VIDEO_SEQ
START WITH 1 -- 초기값
INCREMENT BY 1 -- 증가값
NOCACHE -- 메모리 캐시 사용 안 함 (옵션)
NOCYCLE; -- 최대값 이후 다시 1로 돌아가지 않음

CREATE TABLE VIDEO_CATEGORIES (
    CATEGORY_ID NUMBER PRIMARY KEY,        -- 카테고리 ID (PK)
    VIDEO_ID NUMBER,                       -- 영상 ID (FK)
    USER_NUM NUMBER NOT NULL,              -- 아티스트 번호 (FK)
    CATEGORY_NAME VARCHAR2(100) NOT NULL,  -- 카테고리 이름
    DESCRIPTION VARCHAR2(255),             -- 카테고리 설명
    CONSTRAINT FK_VIDEO_CATEGORIES_USER FOREIGN KEY (USER_NUM) REFERENCES AUSER(USER_NUM)
);

-- CATEGORY_ID 시퀀스 생성
CREATE SEQUENCE CATEGORY_SEQ
    START WITH 1       -- 초기 값
    INCREMENT BY 1     -- 증가 값
    NOCACHE;           -- 캐시 사용 안 함
    
CREATE TABLE video_comments (
    comment_id NUMBER PRIMARY KEY, -- 댓글을 구별하는 고유 ID
    user_num NUMBER NOT NULL, -- 유저 ID (DUSER_DETAIL 테이블의 user_num 참조)
    video_id NUMBER NOT NULL, -- 댓글이 달린 영상 ID (video 테이블 참조)
    parent_comment_id NUMBER NULL, -- 상위 댓글 ID (NULL이면 일반 댓글, 값이 있으면 대댓글)
    comment_content VARCHAR2(500) NOT NULL, -- 댓글 내용
    likes NUMBER DEFAULT 0 NOT NULL, -- 댓글의 좋아요 수
    dislikes NUMBER DEFAULT 0 NOT NULL, -- 댓글의 싫어요 수
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 댓글 작성 시점
    CONSTRAINT fk_user_num FOREIGN KEY (user_num) REFERENCES DUSER_DETAIL(user_num), -- 유저 테이블 참조
    CONSTRAINT fk_video_id FOREIGN KEY (video_id) REFERENCES video(video_id), -- 영상 테이블 참조
    CONSTRAINT fk_parent_comment_id FOREIGN KEY (parent_comment_id) REFERENCES video_comments(comment_id) -- 대댓글 참조
);

-- 시퀀스 생성
CREATE SEQUENCE INQUIRIES_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- INQUIRIES 테이블 생성
CREATE TABLE INQUIRIES (
    INQUIRY_ID NUMBER PRIMARY KEY,
    user_num NUMBER NOT NULL,
    TITLE VARCHAR2(100) NOT NULL,
    CONTENT VARCHAR2(1000) NOT NULL,
    STATUS NUMBER(1) NOT NULL, -- 0: 대기중, 1: 답변완료, 2: 처리중
    CREATED_AT TIMESTAMP(5) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATED_AT TIMESTAMP(5) DEFAULT CURRENT_TIMESTAMP,
    ANSWER_CONTENT VARCHAR2(1000),
    ANSWERED_AT TIMESTAMP(5),
    file_name VARCHAR2(255) NOT NULL
);