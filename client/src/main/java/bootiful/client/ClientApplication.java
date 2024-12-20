package bootiful.client;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(CustomerHttpClient cc) {
		return args -> cc.all().subscribe(System.out::println);
	}

	@Bean
	CustomerHttpClient customerHttpClient(WebClient.Builder builder) {
		var wc = builder.baseUrl("http://localhost:8080").build();
		var wca = WebClientAdapter.create(wc);
		return HttpServiceProxyFactory
                .builder().exchangeAdapter(wca)
				.build()
				.createClient(CustomerHttpClient.class);
	}
}

@Controller
class CustomerGraphqlController {
	private final CustomerHttpClient cc;

	CustomerGraphqlController(CustomerHttpClient cc) {
		this.cc = cc;
	}

	@BatchMapping
	Map<Customer, Profile> profile(List<Customer> customerList) {
		var map = new HashMap<Customer, Profile>();
		for(var c : customerList) {
			System.out.println("customer id " + c.id());
			map.put(c, new Profile(c.id()));
		}
		return map;
	}

	/*
	@SchemaMapping(typeName = "Customer")
	Profile profile (Customer customer) {
		System.out.println("customer id " + customer.id());
		return new Profile(customer.id());
	}
	*/

	@QueryMapping
	Flux<Customer> customers() {
		return this.cc.all();
	}
}

interface CustomerHttpClient {
	@GetExchange("/customers")
	Flux<Customer> all();
	@GetExchange("/customers/{name}")
	Flux<Customer> byName(String name);
}

record Profile(Integer id) {}
record Customer(Integer id, String name) {}
