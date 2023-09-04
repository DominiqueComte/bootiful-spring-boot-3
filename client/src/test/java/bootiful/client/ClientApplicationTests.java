package bootiful.client;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ClientApplicationTests {

	@Autowired
	ApplicationContext context;

	@Test
	@Disabled("Client needs service running, this needs refining")
	void contextLoads() {
		assertNotNull(context);
		assertNotNull(context.getEnvironment());
	}

}
