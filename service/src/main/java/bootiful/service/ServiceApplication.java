package bootiful.service;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

}

@ControllerAdvice
class ErrorHandlingControllerAdvice {
	@ExceptionHandler
	ProblemDetail handle(IllegalStateException ise) {
		var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value());
		pd.setDetail(ise.getMessage());
		return pd;
	}
}

@Controller
@ResponseBody
class CustomerHttpController {
	private final CustomerRepository customerRepository;
	private final ObservationRegistry registry;

	CustomerHttpController(CustomerRepository customerRepository, ObservationRegistry registry) {
		this.customerRepository = customerRepository;
		this.registry = registry;
	}

	@GetMapping("/customers")
	Iterable<Customer> customers() {
		return this.customerRepository.findAll();
	}

	@GetMapping("/customers/{name}")
	Iterable<Customer> customersByName(@PathVariable String name) {
		Assert.state(Character.isUpperCase(name.charAt(0)), "the name must start with a capital letter");
		return Observation
				.createNotStarted("by-name", this.registry)
				.observe(() -> this.customerRepository.findByName(name));
	}
}
interface CustomerRepository extends CrudRepository<Customer, Integer> {
	Iterable<Customer> findByName(String name);
}

record Customer(@Id Integer id, String name) {}
