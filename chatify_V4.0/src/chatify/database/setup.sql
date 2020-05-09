CREATE SEQUENCE seq_user AS BIGINT START WITH 1;
CREATE SEQUENCE seq_message AS BIGINT START WITH 1;
CREATE SEQUENCE seq_chatroom AS BIGINT START WITH 1;

CREATE TABLE benutzer (
    userid      Decimal(8) NOT NULL,
    username    VARCHAR(50) NOT NULL,
    email       VARCHAR(50),
    password    VARCHAR(50) NOT NULL,
    createdat   DATE NOT NULL
);

ALTER TABLE benutzer ADD CONSTRAINT user_pk PRIMARY KEY ( userid );

CREATE TABLE chatparticipants (
    createdat             DATE NOT NULL,
    chatroom_chatroomid   Decimal(8) NOT NULL,
    user_userid           Decimal(8)
);

CREATE TABLE chatroom (
    chatroomid   Decimal(8) NOT NULL,
    createdat    DATE NOT NULL
);

ALTER TABLE chatroom ADD CONSTRAINT chatroom_pk PRIMARY KEY ( chatroomid );

CREATE TABLE imagemessage (
    imagechatid           Decimal(8) NOT NULL,
    imagemessage          VARCHAR(2000) NOT NULL,
    createdat             DATE NOT NULL,
    chatroom_chatroomid   Decimal(8),
    user_userid           Decimal(8),
    images_imageid        Decimal(8)
);

ALTER TABLE imagemessage ADD CONSTRAINT imagemessage_pk PRIMARY KEY ( imagechatid );

CREATE TABLE images (
    imageid   Decimal(8) NOT NULL,
    url       VARCHAR(200) NOT NULL
);

ALTER TABLE images ADD CONSTRAINT images_pk PRIMARY KEY ( imageid );

CREATE TABLE message (
    messageid             Decimal(8) NOT NULL,
    messagecontent        VARCHAR(2000) NOT NULL,
    createdat             DATE NOT NULL,
    user_userid           Decimal(8),
    chatroom_chatroomid   Decimal(8)
);

ALTER TABLE message ADD CONSTRAINT message_pk PRIMARY KEY ( messageid );

ALTER TABLE chatparticipants
    ADD CONSTRAINT chatparticipants_chatroom_fk FOREIGN KEY ( chatroom_chatroomid )
        REFERENCES chatroom ( chatroomid );

ALTER TABLE chatparticipants
    ADD CONSTRAINT chatparticipants_user_fk FOREIGN KEY ( user_userid )
        REFERENCES benutzer ( userid );

ALTER TABLE imagemessage
    ADD CONSTRAINT imagemessage_chatroom_fk FOREIGN KEY ( chatroom_chatroomid )
        REFERENCES chatroom ( chatroomid );

ALTER TABLE imagemessage
    ADD CONSTRAINT imagemessage_images_fk FOREIGN KEY ( images_imageid )
        REFERENCES images ( imageid );

ALTER TABLE imagemessage
    ADD CONSTRAINT imagemessage_user_fk FOREIGN KEY ( user_userid )
        REFERENCES benutzer ( userid );

ALTER TABLE message
    ADD CONSTRAINT message_chatroom_fk FOREIGN KEY ( chatroom_chatroomid )
        REFERENCES chatroom ( chatroomid );

ALTER TABLE message
    ADD CONSTRAINT message_user_fk FOREIGN KEY ( user_userid )
        REFERENCES benutzer ( userid );




--Tabellen ausgeben
select * from benutzer;
select * from chatroom;
select * from message;

-- Benutzer erstellen
insert into benutzer (userid, username, email, password, createdat) values(next value for seq_user, 'Florian', 'f.beckerle@abc123.co.at', 'abcdefg1A', CURRENT_DATE);

-- Chatroom erstellen
insert into CHATROOM(CHATROOMID, CREATEDAT) values(next value for seq_chatroom, CURRENT_DATE);

-- Message erstellen
insert into MESSAGE (MESSAGEID, MESSAGECONTENT, CREATEDAT, USER_USERID, CHATROOM_CHATROOMID) values(next value for seq_message, 'Willkommen in Chatify! Das hier ist eine Testnachricht.', CURRENT_DATE, 2, 1);
