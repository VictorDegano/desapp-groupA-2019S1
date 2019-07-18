package ar.edu.unq.desapp.grupoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@PropertySources({
  @PropertySource("classpath:auth0.properties")
})
public class DesappGroupABackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesappGroupABackendApplication.class, args);
    }

}
