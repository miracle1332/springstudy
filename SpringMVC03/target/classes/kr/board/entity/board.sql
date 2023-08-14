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
create sequence mem_tbl_idx;

select * from mem_tbl;

delete from MEM_TBL;
