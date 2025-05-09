DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS (
                       userId          varchar(12)		NOT NULL,
                       password		varchar(12)		NOT NULL,
                       name			varchar(20)		NOT NULL,
                       email			varchar(50),

                       PRIMARY KEY               (userId)
);

INSERT INTO USERS VALUES('kuit', 'kuit', 'kuit', 'kuit@kuit.kr');

DROP TABLE IF EXISTS QUESTIONS;

CREATE TABLE QUESTIONS (
                           questionId 			bigint				auto_increment,
                           writer				varchar(30)			NOT NULL,
                           title				varchar(50)			NOT NULL,
                           contents			varchar(5000)		NOT NULL,
                           createdDate			timestamp			NOT NULL,
                           countOfAnswer int,
                           PRIMARY KEY               (questionId)
);

DROP TABLE IF EXISTS ANSWERS;

CREATE TABLE ANSWERS (
                         answerId 			bigint				auto_increment,
                         writer				varchar(30)			NOT NULL,
                         contents			varchar(5000)		NOT NULL,
                         createdDate			timestamp			NOT NULL,
                         questionId			bigint				NOT NULL,
                         PRIMARY KEY         (answerId)
);

INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES
    ('박지원',
     '졸업하고 싶은 사람',
     '졸업하고 싶다',
     CURRENT_TIMESTAMP(), 0);

INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES
    ('함형주',
     '저는 회식이 너무 좋아요',
     '회식을 하면 저를 꼭 불러주세요',
     CURRENT_TIMESTAMP(), 0);

INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES
    ('장현준',
     '객체 지향이 어쩌구',
     '객체 지향을 좋아하는 사람입니다',
     CURRENT_TIMESTAMP(), 0);

INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES
    ('임제형',
     '회원식당 돌이',
     '화원식당 mvp 입니다',
     CURRENT_TIMESTAMP(), 0);

INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES
    ('이윤정',
     '저는 객체지향 마스터에요!',
     '오브젝트란 책이 저의 최애 책이랍니다~^^ \n 여러분의 최애 책은 무엇인가요?',
     CURRENT_TIMESTAMP(), 0);

INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES
    ('김지환',
     '저는 깃 마스터랍니다~ 하핫',
     CURRENT_TIMESTAMP(), 2);

INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES
    ('김민우',
     '바쁘다 바빠 현대사회',
     CURRENT_TIMESTAMP(), 3);

INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES
    ('지호준',
     '나는야 풀스택 개발자 *_*',
     CURRENT_TIMESTAMP(), 1);