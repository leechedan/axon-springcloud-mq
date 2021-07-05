package org.github.axon.tag.user;

//import org.github.axon.dispose.starter.annotation.EnableGlobalDispose;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@SpringBootApplication
//@EnableGlobalDispose
@EntityScan("org.github.axon")
@EnableJpaRepositories(basePackages = {
		"org.github.axon.tag.user",
		"org.github.axon.tag.user.repository"
})
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
}
