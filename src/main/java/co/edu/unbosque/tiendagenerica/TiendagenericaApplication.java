package co.edu.unbosque.tiendagenerica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class TiendagenericaApplication {

	@RequestMapping("/")
    //@ResponseBody
    String home() {
      return "index.html";
    }
	
	public static void main(String[] args) {
		SpringApplication.run(TiendagenericaApplication.class, args);
	}

}
