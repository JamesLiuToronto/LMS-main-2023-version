CREATE TABLE account_UserAccount
(
    userId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid varchar(200) not null,
    emailAddress varchar(100) NOT NULL unique,
    firstName varchar(100) NOT NULL,
    lastName varchar(100) NOT NULL,
    birthDate Date DEFAULT NULL,
    sex char(1) DEFAULT NULL,
    statusCode varchar(20) NOT NULL,
    createDate datetime DEFAULT current_timestamp(),
    updateDate datetime DEFAULT current_timestamp(),
    createUserId int not null ,
    updateUserId int not null,
    note varchar(500) null
    );

CREATE TABLE account_UserLogin
(
    userId INT NOT NULL PRIMARY KEY,
    password varchar(500) null,
    authenticationSource varchar(20) not null,
    locked boolean NULL,
    lastSuccessLogin datetime null,
    lastFaliedLogin datetime null,
    faliedLoginAttemp int NULL,
    createDate datetime DEFAULT current_timestamp(),
    updateDate datetime DEFAULT current_timestamp(),
    createUserId int not null ,
    updateUserId int not null

);

CREATE TABLE account_UserGroup
(
    userGroupId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid varchar(200) not null,
    groupCode varchar(10) NOT NULL,
    userId int NOT NULL ,
    active bit not null,
    createDate datetime DEFAULT current_timestamp(),
    updateDate datetime DEFAULT current_timestamp(),
    createUserId int not null ,
    updateUserId int not null ,

    CONSTRAINT UserGroup_user FOREIGN KEY (userId)
        REFERENCES account_UserAccount(userId)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
);

CREATE TABLE account_Family
(
    familyId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    familyName varchar(200) NULL,
    note varchar(500) default null,
    createDate datetime DEFAULT current_timestamp(),
    updateDate datetime DEFAULT current_timestamp(),
    createUserId int not null ,
    updateUserId int not null

);

CREATE TABLE account_FamilyMember
(
    userId INT NOT NULL PRIMARY KEY,
    familyId INT NOT NULL,
    active boolean default null,
    createDate datetime DEFAULT current_timestamp(),
    updateDate datetime DEFAULT current_timestamp(),
    createUserId int not null ,
    updateUserId int not null

);

insert into account_userAccount values (-1, '01010101', 'admin@yahoo.com', 'admin', 'admin', '2000-01-01', 'M', 'ACTIVE',
now(), now(), -1, -1, null);

insert into account_userAccount values (1, '01010102', 'jamesliu_8@yahoo.com', 'james', 'admin', '2000-01-01', 'M', 'ACTIVE',
                                now(), now(), -1, -1,null);

insert into account_userlogin values (-1, 'admin', 'NULL', false, null, null, 0,  now(), now(), -1, -1);
insert into account_userlogin values (1, '$2a$10$1KqopvqntQlXKb7/w8gdJ.mChC.2JWEeYIEGWNKtixzxaq5hYUodi', 'NULL', false, null, null, 0, now(), now(), -1, -1);



insert into account_UserGroup values (1, UUID(), 'ADMIN', 1, 1, now(), now(), -1, -1);
insert into account_UserGroup values (2, UUID(), 'USER', 1, 1, now(), now(), -1, -1);
insert into account_UserGroup values (3, UUID(), 'STUDENT', 1, 1, now(), now(), -1, -1);
insert into account_UserGroup values (4, UUID(), 'TEACHER', 1, 1, now(), now(), -1, -1);
insert into account_UserGroup values (5, UUID(), 'ASST', 1, 1, now(), now(), -1, -1);


CREATE TABLE logging_TransactionLog
(
    transactionLogId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid varchar(200) not null,
    transactionTypeCode varchar(20) DEFAULT NULL,
    message text DEFAULT NULL,
    statusCode varchar(20) Default NULL,
    userId int not null ,
    utimestamp datetime DEFAULT current_timestamp()
)

CREATE TABLE account_session_blacklist
(
    blacklistId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    token varchar(200) not null,
    createDate datetime DEFAULT current_timestamp(),
    note varchar(100) null
);

CREATE TABLE account_group_method_map
(
    groupMethodId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    GroupCode varchar(20) NOT NULL,
    methodName varchar(100) not null,
    createDate datetime DEFAULT current_timestamp(),
    note varchar(100) null
);

insert into account_group_method_map values( 1, 'ALL', 'getLoginUserInfo', now(), null) ;
insert into account_group_method_map values(2, 'ADMIN', 'registerNewAccountByAdmin', now(), null) ;
insert into account_group_method_map values(3, 'ADMIN', 'activateUserAccount', now(), null) ;
insert into account_group_method_map values(4,  'ADMIN', 'deactivateUserAccount', now(), null) ;
insert into account_group_method_map values(5,  'ADMIN', 'updateUserInfo', now(), null) ;
insert into account_group_method_map values(6,  'FAMILY', 'updateUserInfo', now(), null) ;

insert into account_group_method_map values(7,  'ADMIN', 'updateEmailAddress', now(), null) ;
insert into account_group_method_map values(8,  'FAMILY', 'updateEmailAddress', now(), null) ;
insert into account_group_method_map values(9,  'ADMIN', 'assignUserGroup', now(), null) ;

insert into account_group_method_map values(10 , 'ADMIN', 'getActivateToken', now(), null) ;
