package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

// BEGIN


// END

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN



    @Bean
    @Scope("prototype")
    public Daytime getDaytime(){
        int time = LocalDateTime.now().getHour();
        if (time >= 6 & time < 22) {
            return new Day();
        }
        else
        {
        return new Night();
        }

    }

    
    // END

//    Используйте необходимые аннотации и сконфигурируйте приложение так,
//    чтобы в контекст добавлялся только определенный бин в зависимости от времени суток:
//
//    С 6 часов включительно до 22 часов — бин класса Day
//    С 22 часов включительно до 6 часов — бин класса Night
//    Подумайте, какой должна быть область видимости бина.
}
