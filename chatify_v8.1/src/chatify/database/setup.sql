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


--Chatroom Name (länge 20 Zeichen, not null) hinzufügen
alter table chatroom add column name varchar(20) not null default 'New Chatroom';

-- UserId bei Chatparticipants auf Not NULL setzen
alter table chatparticipants update column user_userid not null;

--Tabellen ausgeben
select * from benutzer;
select * from chatroom;
select * from message;


-- Benutzer erstellen
insert into benutzer (userid, username, email, password, createdat) values(next value for seq_user, 'Florian', 'f.beckerle@abc123.co.at', 'abcdefg1A', CURRENT_DATE);

-- Chatroom erstellen
insert into CHATROOM(CHATROOMID, CREATEDAT) values(next value for seq_chatroom, CURRENT_DATE);
insert into CHATROOM(CHATROOMID, CREATEDAT, NAME) values(next value for seq_chatroom, CURRENT_DATE, '2ter Chatroom');

-- Message erstellen
insert into MESSAGE (MESSAGEID, MESSAGECONTENT, CREATEDAT, USER_USERID, CHATROOM_CHATROOMID) values(next value for seq_message, 'Willkommen in Chatify! Das hier ist eine Testnachricht.', CURRENT_DATE, 2, 1);

-- Username und Password aus der Datenbank hohlen mit Variablen als Suchkriterien
select password from benutzer where username = 'Xodeen';

--Nachschauen ob Username bereits Existiert
select count(*) from benutzer where username ='Xodeen';


select * from benutzer order by username desc;

select * from chatroom;

-- Wenn das Password NULL oder "" ist könnte der Server Öffentlich sein oder?       ""= Leerstring
-- Neue Spalte(n) für Chatroom (Password)
alter table chatroom add column password varchar(50);

--Chatroom Erstellen V7
insert into CHATROOM(CHATROOMID, CREATEDAT, NAME, PASSWORD) values(next value for seq_chatroom, CURRENT_DATE, 'Implodium', 'implodium01');

UPDATE benutzer set username='Xodeen' where username='X0DEEN';
