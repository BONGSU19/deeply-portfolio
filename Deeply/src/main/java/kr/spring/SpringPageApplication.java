package kr.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories(basePackages = {"kr.spring.repository"})
@EntityScan(basePackages = {"domain"})
@SpringBootApplication
@EnableScheduling
public class SpringPageApplication {

public static void main(String[] args) {
SpringApplication.run(SpringPageApplication.class, args);
}

}