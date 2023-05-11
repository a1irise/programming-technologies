package ru.kotiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories
public class KotikiApplication {

    public static void main(String[] args) {
        //"$2a$10$w4Wz4HXxBXy/QkE50ZATMOR8Vas4jxoRfEMskTGY.mMcl5vJGGhza"
        SpringApplication.run(KotikiApplication.class, args);
    }
}
