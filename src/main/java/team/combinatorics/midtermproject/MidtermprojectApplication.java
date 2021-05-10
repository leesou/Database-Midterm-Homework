package team.combinatorics.midtermproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "team.combinatorics.midtermproject.dao")
public class MidtermprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MidtermprojectApplication.class, args);
    }

}
