package uz.pdp.apporder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages =
                {
                        "uz.pdp.appproduct",
                        "uz.pdp.apporder",
                }
)

@EnableEurekaClient
@EnableFeignClients(basePackages = {
        "uz.pdp.appproduct",
        "uz.pdp.apporder",
})
@EntityScan(basePackages = {
        "uz.pdp.appproduct.entity",
        "uz.pdp.apporder.entity",
        "uz.pdp.apporder.telegrambot.entity",
})
@EnableJpaRepositories(basePackages = {
        "uz.pdp.apporder.repository",
        "uz.pdp.appproduct.repository",
        "uz.pdp.apporder.telegrambot.repository",
})
public class AppOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppOrderApplication.class, args);
    }

}
