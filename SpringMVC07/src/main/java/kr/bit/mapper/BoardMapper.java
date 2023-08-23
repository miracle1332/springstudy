package kr.bit.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;

import kr.bit.entity.Board;

public interface BoardMapper { //xml or @annotation
	
	public List<Board> getList();
}
