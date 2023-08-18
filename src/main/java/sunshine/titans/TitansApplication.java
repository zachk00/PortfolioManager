package sunshine.titans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Import;

@SpringBootApplication
//@Import(SwaggerConfig.class)
@ComponentScan
public class TitansApplication {

	public static void main(String[] args) {
		SpringApplication.run(TitansApplication.class, args);
	}

}
