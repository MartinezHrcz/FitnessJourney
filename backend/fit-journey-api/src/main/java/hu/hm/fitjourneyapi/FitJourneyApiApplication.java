package hu.hm.fitjourneyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FitJourneyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitJourneyApiApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hi";
    }
}
