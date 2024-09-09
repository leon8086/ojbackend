package xmut.cs.ojbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@MapperScan("xmut.cs.ojbackend.mapper")
public class OjbackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OjbackendApplication.class, args);
    }

}
