package co.edu.unbosque.tiendagenerica.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class BasicConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http

				.authorizeRequests().antMatchers("/", "index", "/css/*", "/js/*", "/img/*", "registrar", "login").permitAll()
				.anyRequest().authenticated().and().formLogin()// .loginPage("/login.html").permitAll()
				.defaultSuccessUrl("/home.html", true).and().csrf().disable();
	}
}