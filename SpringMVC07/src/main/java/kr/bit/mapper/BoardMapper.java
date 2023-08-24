package kr.bit.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;

import kr.bit.entity.Board;
import kr.bit.entity.Member;

public interface BoardMapper { //xml or @annotation
	
	public List<Board> getList();
	public void insert(Board vo);
    public void insertSelectKey(Board vo);
    public Member login(Member vo); // SQL
    public Board read(int idx);
    public void update(Board vo);
    public void delete(int idx);
    public void replySeqUpdate(Board parent);
    public void replyInsert(Board vo);
   // public int totalCount();*/
}
