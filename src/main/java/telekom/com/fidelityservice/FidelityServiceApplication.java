package telekom.com.fidelityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FidelityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FidelityServiceApplication.class, args);
    }

}
