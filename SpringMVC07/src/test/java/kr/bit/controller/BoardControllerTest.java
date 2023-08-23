package kr.bit.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import kr.bit.mapper.BoardMapperTest;
import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration/*스프링컨테이너라는 곳에 객체들이 만들어지고 autowired되는데, 
						스프링컨테이너를 메모리에서 가상으로 만들고 거기에톰캣개발환경을 만들어주기.*/
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
					   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"}) 
public class BoardControllerTest {
		@Autowired
		private WebApplicationContext ctx; //스프링 컨데이너 메모리 공간을 ctx가 받는거쥬.
		
		private MockMvc mocMvc; //가상의 mvc환경을 만들어준다.
		
		@Before //junit
		public void setup() {
			//톰캣서버를 실행하지 않고 만들어야 하기 때문...//가상의 컨테이너 환경을 만들어주는 메서드
			this.mocMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
		}
		
		@Test
		public void testList() throws Exception{
			log.info(
					 //perform-요청날려주는 메서드 , MockMvcRequestBuilders-요청을 만들어주는 메서드
					mocMvc.perform(MockMvcRequestBuilders.get("/board/list"))
					.andReturn()
					.getModelAndView());//모델과 뷰를 가져오겠다. 그리고 info로 출력..
					//.getModelMap(); //더 이어서 하면 모델 값만 출력하는 메서드.
					//.getViewName(); -> 뷰 네임만 출력 메서드

		}
}
