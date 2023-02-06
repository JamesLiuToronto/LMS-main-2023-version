SELECT * FROM peaktraining.AuditLog;

select * from TransactionLog

select * from UserAccount;
select * from userlogin;
select * from usergroup;
select * from accesscontrol

delete from usergroup where usergroupid in (8,9, 10)
delete from UserAccount where userid in (4,5)
ALTER TABLE userLogin add auditId int null;

update accesscontrol
set methodCode = 'ACCOUNT.ALL'
where accesscontrolid = 2

update userlogin 
set password='james'
where userid = 1

select * from userlogin
drop table userlogin


insert into userAccount values (-1, '01010101', 'admin@yahoo.com', 'admin', 'admin', now(), 'M', 1, now(), null)
insert into userlogin values (1, 'james', 'NULL', now(), null, 0, null, null, 0)

insert into userAccount values (1, 'james', 'NULL', now(), null, 0, null, null, 0)
insert into userlogin values (1, 'james', 'NULL', now(), null, 0, null, null, 0)
insert into userlogin values (2, 'test1', 'GOOGLE', now(), null )
insert into userlogin values (3, 'test2', 'FACEBOOK', now(), null )

drop table user2
CREATE TABLE UserLogin
(
    userId INT NOT NULL PRIMARY KEY,
    password varchar(50) null,
    authenticationSource varchar(20) not null,
    utimestamp datetime DEFAULT current_timestamp(),
    auditId INT NULL,
    locked boolean NULL, 
    lastSuccessLogin datetime null,
    lastFaliedLogin datetime null,
    faliedLoginAttemp int NULL
)

insert into user2 values (1, 'james', 'liu', 'jamesliu_8@yahoo.com', '$2a$11$QCbr.Qtgb9wRKGcj5uoF..jFBHNqKERmx0ejNPElukt0F1MoFfIBG', 'admin', 1 )



select * from user2
delete from user2 where id =1



