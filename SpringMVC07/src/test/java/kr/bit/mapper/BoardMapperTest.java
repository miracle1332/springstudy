package kr.bit.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.bit.entity.Board;
import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") 
//root-context.xml을 실행해주는 어노테이션
public class BoardMapperTest {

		@Autowired
		BoardMapper boardMapper;
		
		 /*@Test
		 public void testGetList() {
			 List<Board> list=boardMapper.getList();
			 for(Board vo : list) {
				 //System.out.println(vo);
				 log.info(vo);
			 }
		 }	*/
		
		@Test
		public void testInsert() {
			Board vo = new Board();
			vo.setMemID("bit02");
			vo.setTitle("b제목");
			vo.setContent("컨텐츠내용임bb");
			vo.setWriter("관리자");
			//boardMapper.insert(vo);
			boardMapper.insertSelectKey(vo);
			log.info(vo);
		}
	
}
