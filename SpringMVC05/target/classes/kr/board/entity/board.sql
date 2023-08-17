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

create table mem_tbl(
	memIdx number not null,
	memID varchar2(20) not null,
	memPassword varchar2(20) not null,
	memName varchar2(20) not null,
	memAge number,
	memGender varchar2(20),
	memEmail varchar2(50),
	memProfile varchar2(50),
	primary key(memIdx)
);

drop table springboard;

create sequence mem_tbl_idx;

select * from mem_tbl;

delete from MEM_TBL;
