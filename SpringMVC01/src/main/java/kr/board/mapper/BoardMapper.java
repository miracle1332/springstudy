package kr.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import kr.board.entity.Board;

@Mapper //myBatis API
public interface BoardMapper {
	public List<Board> getLists(); //전체리스트 
	public void boardInsert(Board vo);
	public Board boardContent(int idx);
	public void boardDelete(int idx);
	public void boardUpdate(Board vo);
	@Update("update myboard set count=count+1 where idx=#{idx}") //upadte어노테이션을 사용할 수 있다. 마이바티스에서 제공해주는 api
	//어노테이션을 이용해서도 쿼리를 쓸 수 있다는것..그런데 주의할 사항은 어노테이션으로 update를 적고 또 <update>이런식으로 같은 쿼리를 중복하면 안된다.
	public void boardCount(int idx);
	
}