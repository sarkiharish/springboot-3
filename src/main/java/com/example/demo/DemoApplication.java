package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class DemoApplication {

	private  final  CustomerRepository customerRepository;

	public DemoApplication(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
//	@GetMapping("/greet")
//	public GreetResponse greet(){
//		GreetResponse response = new GreetResponse(
//				"Hello",
//				List.of("Java", "Python","Javascript"),
//				new Person("Hari", 28, 5000)
//				);
//		return response;
//	}
//
//	record Person(String name, int age, double savings ){}


//	record GreetResponse(String greet,
//						 List<String> favProgrammingLanguages,
//						 Person person
//						 ){}


	//FIrst section tried normal w/o json
//	public String greet() {
//		return "Hello Harish";
//	}


	//implementing with spring jpa

	@GetMapping
	public List<Customer> getCustomer() {
		return  customerRepository.findAll();
	}

	record NewCustomerRequest(
			String name,
			String email,
			Integer age
	) {}

	@PostMapping
	public void addCustomer(@RequestBody NewCustomerRequest request) {
		Customer customer = new Customer();
		customer.setName(request.name());
		customer.setEmail(request.email());
		customer.setAge(request.age());
		customerRepository.save(customer);
	}

	@DeleteMapping("{customerId}")
	public  void deleteCustomer(@PathVariable("customerId") Integer id) {
		customerRepository.deleteById(id);
	}

	@PutMapping("{customerId}")
	public  void updateCustomer(@PathVariable("customerId") Integer id,
								@RequestBody NewCustomerRequest request) {
		Customer updateCustomer = customerRepository.getById(id);
		updateCustomer.setName(request.name());
		updateCustomer.setEmail(request.email());
		updateCustomer.setAge(request.age());
		customerRepository.save(updateCustomer);
	}
}
