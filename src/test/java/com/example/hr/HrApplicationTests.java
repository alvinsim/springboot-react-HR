package com.example.hr;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HrApplication.class)
class HrApplicationTests {

	@Test
	public void whenSpringContextIsBootstrapped_thenNoExceptions() {
	}
}
