create table myboard(
	idx number not null,
	title varchar2(100) not null,
	content varchar2(2000) not null,
	writer varchar2(30) not null,
	indate date default sysdate,
	count number default 0,
	primary key(idx)
);

create sequence myboard_idx;
delete from myboard;

select * from myboard order by idx desc;