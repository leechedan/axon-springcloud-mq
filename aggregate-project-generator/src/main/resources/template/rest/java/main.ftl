package ${packageName}.${(moduleName)!}${(subPkgName)!};

import org.axonframework.springboot.autoconfig.JdbcAutoConfiguration;
import org.axonframework.springboot.autoconfig.JpaEventStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {JpaEventStoreAutoConfiguration.class, JdbcAutoConfiguration.class})
@EntityScan("${packageName}.${(moduleName)!}${(subPkgName)!}")
@EnableJpaRepositories(basePackages = {"${packageName}.${(moduleName)!}${(subPkgName)!}"})
public class ${aggregate}Application {

    public static void main(String[] args) {
        SpringApplication.run(${aggregate}Application.class, args);
    }
}

