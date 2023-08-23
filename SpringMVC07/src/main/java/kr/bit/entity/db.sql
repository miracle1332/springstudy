-- tblBoard --
create table tblBoard (
	idx number primary key,
	memID varchar2(20) not null,
	title varchar2(100) not null,
	content varchar2(2000) not null,
	writer varchar2(30) not null,
	indate date default sysdate,
	count number default 0,
	boardGroup number, --부모글과 자식글--
	boardSequence number, --같은 그룹에서 순서--
	boardLevel number, --들여쓰기--
	boardAvailable number --부모글삭제 여부 표시--
);
select max(idx) from tblBoard; --> null이면 1부터 시작, null이아니면 +1을 해서 n+1을 만들어야함.
select nvl(max(idx)+1, 1) from tblBoard; --> 그렇게 만듦.
select nvl(max(boardGroup)+1, 0) from tblBoard; --> 그렇게 만듦.

insert into tblBoard 
select nvl(max(idx)+1, 1), 'bit01', '게시판연습', '께시판연습', '관리자', sysdate,
0,nvl(max(boardGroup)+1, 0),0,0,1 from tblBoard; 

insert into tblBoard 
select nvl(max(idx)+1, 1), 'bit02', '연습중', '께시판연습', '오혜린', sysdate,
0,nvl(max(boardGroup)+1, 0),0,0,1 from tblBoard; 

insert into tblBoard 
select nvl(max(idx)+1, 1), 'bit03', '연습중', '께시판연습', '뭉이', sysdate,
0,nvl(max(boardGroup)+1, 0),0,0,1 from tblBoard; 

select * from TBLBOARD;
--회원관련테이블--
create table tblMember(
	memID varchar2(50) primary key,
	memPwd varchar2(50) not null,
	memName varchar2(50) not null,
	memPhone varchar2(50) not null,
	memAddr varchar2(100),
	latitude decimal(13,10), --현재위치위도
	longitude decimal(13,10) --현재위치 경도
);
drop table tblMember;
insert into tblMember(memID, memPwd, memName, memPhone)
values('bit01', 'bit01', '관리자', '010-1111-1111');
insert into tblMember(memID, memPwd, memName, memPhone)
values('bit02', 'bit02', '오혜린', '010-2222-2222');
insert into tblMember(memID, memPwd, memName, memPhone)
values('bit03', 'bit03', '뭉이', '010-3333-3333');


