package kr.bit.mapper;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") 
//root-context.xml을 실행해주는 어노테이션
public class DataSourceTest {

		@Autowired
		private DataSource dataSource;//javax.sql.
		
		//테스트 코드 짜기
		@Test
		public void testConnection() {
			//javax.sql.
			try(Connection conn = dataSource.getConnection()) {
				//커넥션정보를 받으면
				log.info(conn);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
}
