DROP DATABASE IF EXISTS `ourhome`;
CREATE DATABASE `ourhome`;
USE ourhome;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
	`id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `user_id` varchar(50) NOT NULL UNIQUE,
    `password` varchar(128) NOT NULL,
    `name` varchar(30) NOT NULL,
    `birth` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `gender` char(1) CHECK (`gender` in ('M', 'F')),
    `img` blob
);

INSERT INTO `user` (`user_id`, `password`, `name`, `gender`) 
VALUES ('test', 'D404559F602EAB6FD602AC7680DACBFAADD13630335E951F097AF3900E9DE176B6DB28512F2E000B9D04FBA5133E8B1C6E8DF59DB3A8AB9D60BE4B97CC9E81DB', 'name', 'M');

DROP TABLE IF EXISTS `user_token`;
CREATE TABLE `user_token` (
	`user_id` VARCHAR(50),
    `hashed_token` VARCHAR(128),
    `expiration` TIMESTAMP,
	`valid` TINYINT(1) CHECK (`valid` in (1, 0)),
    CONSTRAINT `token_pk` PRIMARY KEY(`user_id`, `hashed_token`)
);

DROP TABLE IF EXISTS `home`;
CREATE TABLE `home`(
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `address` VARCHAR(50) NOT NULL,
    `build_year` INT,
    `type` VARCHAR(20),
    `jeonsae` VARCHAR(20) DEFAULT 0,
    `monthly_deposit` VARCHAR(20) DEFAULT 0,
    `monthly_pay` VARCHAR(20) DEFAULT 0,
    `area` DOUBLE NOT NULL,
    `phone` VARCHAR(20) NOT NULL,
    `lng` VARCHAR(30) NOT NULL,
    `lat` VARCHAR(30) NOT NULL,
    `registered_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT chk_home_type CHECK (`type` IN ("아파트", "원룸"))
);

DROP TABLE IF EXISTS `favorite_home`;
CREATE TABLE `favorite_home`(
	`user_id` BIGINT,
	`home_id` BIGINT,
    `registered_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_favorite_home_home FOREIGN KEY (`home_id`) REFERENCES `home`(`id`),
    CONSTRAINT fk_favorite_home_user FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    CONSTRAINT pk_favorite_home PRIMARY KEY (`home_id`, `user_id`)
);

-- INSERT INTO `favorite_home` (`user_id`, `home_id`)
-- VALUES
-- (1, 2),
-- (1, 4),
-- (1, 5),
-- (1, 6),
-- (1, 9),
-- (1, 10);

DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
	`id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT,
    `home_id` BIGINT,
    `title` VARCHAR(100) NOT NULL,
    `content` TEXT NOT NULL,
    `registered_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_post_user FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    CONSTRAINT fk_post_home FOREIGN KEY (`home_id`) REFERENCES `home`(`id`)
);
INSERT INTO `post` (`user_id`, `title`, `content`) VALUES (1, 'testTitle', 'testContent');

SELECT * FROM `post`;

DROP TABLE IF EXISTS `chat_room`;
CREATE TABLE `chat_room` (
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `home_id` BIGINT NOT NULL,
    `name` VARCHAR(30) NOT NULL,
    `created_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_chat_room_home FOREIGN KEY (`home_id`) REFERENCES `home`(`id`)
);

-- INSERT INTO `chat_room` (`home_id`, `name`)
-- VALUES
-- (1, '서울시 강남구 테헤란로 123'),
-- (2, '서울시 마포구 상암동 456'),
-- (3, '부산시 해운대구 센텀로 789'),
-- (4, '대구시 수성구 수성로 101'),
-- (5, '서울시 종로구 종로3가 111'),
-- (6, '인천시 남동구 구월로 222'),
-- (7, '서울시 서초구 서초대로 333'),
-- (8, '광주시 서구 상무대로 444'),
-- (9, '대전시 유성구 대학로 555'),
-- (10, '서울시 은평구 연서로 666');

DROP TABLE IF EXISTS `entered_chat_room`;
CREATE TABLE `entered_chat_room` (
	`user_id` BIGINT,
    `chat_room_id` BIGINT,
    `registered_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_entered_chat_room_user FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    CONSTRAINT fk_entered_chat_room_room FOREIGN KEY (`chat_room_id`) REFERENCES `chat_room`(`id`),
    CONSTRAINT pk_entered_chat_room PRIMARY KEY (`user_id`, `chat_room_id`)
);

-- INSERT INTO `entered_chat_room` (`user_id`, `chat_room_id`)
-- VALUES
-- (1, 2),
-- (1, 5),
-- (1, 6),
-- (1, 7),
-- (1, 8),
-- (1, 9);

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `chat_room_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    `registered_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `content` TEXT,
    
    CONSTRAINT fk_message_user FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    CONSTRAINT fk_message_chat_room FOREIGN KEY (`chat_room_id`) REFERENCES `chat_room`(`id`)
);

-- INSERT INTO `message` (`chat_room_id`, `user_id`, `content`)
-- VALUES
-- (1, 1, '안녕하세요! 이 방에 처음 왔습니다.'),
-- (2, 1, '모두들 안녕하세요!'),
-- (3, 1, '부산 방에 오신 것을 환영합니다.'),
-- (4, 1, '대구 방에 오신 것을 환영합니다.'),
-- (5, 1, '서울 종로 방에 오신 것을 환영합니다.'),
-- (6, 1, '인천 방에 오신 것을 환영합니다.'),
-- (7, 1, '서울 서초 방에 오신 것을 환영합니다.'),
-- (8, 1, '광주 방에 오신 것을 환영합니다.'),
-- (9, 1, '대전 방에 오신 것을 환영합니다.'),
-- (10, 1, '서울 은평 방에 오신 것을 환영합니다.'),
-- (1, 1, '오늘 날씨가 정말 좋네요!'),
-- (2, 1, '다들 주말 잘 보내세요!'),
-- (3, 1, '부산에서 맛집 추천 좀 해주세요.'),
-- (4, 1, '대구에서 가볼 만한 곳이 어디 있을까요?'),
-- (5, 1, '종로에 새로운 카페가 생겼어요.'),
-- (6, 1, '인천에서 만나요!'),
-- (7, 1, '서초동에서 회의가 있습니다.'),
-- (8, 1, '광주에 새로운 박물관이 열렸습니다.'),
-- (9, 1, '대전에서 좋은 곳 추천해 주세요.'),
-- (10, 1, '은평구에서 좋은 산책로 아시는 분?');

DROP TABLE IF EXISTS `message_status`;
CREATE TABLE `message_status` (   
	`user_id` BIGINT,
    `message_id` BIGINT,
    `is_readed` BOOLEAN,
    
    CONSTRAINT fk_message_status_user FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    CONSTRAINT fk_message_status_message FOREIGN KEY (`message_id`) REFERENCES `message`(`id`)
);