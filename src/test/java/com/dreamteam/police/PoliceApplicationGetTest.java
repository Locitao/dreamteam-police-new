package com.dreamteam.police;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PoliceApplicationGetTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void defaultGetTest() {
		String body = this.testRestTemplate.getForObject("/", String.class);
		assertThat(body.length() > 20);
	}
}
