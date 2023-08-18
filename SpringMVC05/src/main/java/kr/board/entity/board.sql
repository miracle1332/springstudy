create table springboard(
	idx number not null,
	memID varchar2(20) not null,
	title varchar2(100) not null,
	content varchar2(2000) not null,
	writer varchar2(30) not null,
	indate date default sysdate,
	count number default 0,
	primary key(idx)
);
--스프링 시큐리티(회원테이블)--
create table mem_stbl(
	memIdx number not null,
	memID varchar2(20) not null,
	memPassword varchar2(68) not null,
	memName varchar2(20) not null,
	memAge number,
	memGender varchar2(20),
	memEmail varchar2(50),
	memProfile varchar2(50),
	primary key(memID)
);

create table mem_auth (
	no number primary key,
	memID varchar2(50) not null,
	auth varchar2(50) not null,
	constraint fk_member_auth foreign key(memID) references mem_stbl(memID)
);

select * from mem_auth;
