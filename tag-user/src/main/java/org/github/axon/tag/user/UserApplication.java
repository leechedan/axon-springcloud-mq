package org.github.axon.tag.user;

//import org.github.axon.tag.common.autoconfig.KafkaAutoConfiguration;

import org.axonframework.springboot.autoconfig.JdbcAutoConfiguration;
import org.axonframework.springboot.autoconfig.JpaEventStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@EnableJpaAuditing
//        (auditorAwareRef = "auditorAware")
@SpringBootApplication(exclude = {JpaEventStoreAutoConfiguration.class, JdbcAutoConfiguration.class})
@EntityScan("org.github.axon")
@EnableJpaRepositories(basePackages = {
        "org.github.axon.tag.user"
})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
