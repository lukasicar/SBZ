package application;
/*
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.inMemoryAuthentication().withUser("user1").password("user1Pass").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
        .loginPage("/#/login").permitAll()
        .loginProcessingUrl("/api/login").permitAll()
        .usernameParameter("username")
        .successHandler((request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
        })
        .failureHandler((request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        })
        .and()
        .httpBasic()
        .and()
        .logout()
        .logoutUrl("/api/logout")
        .deleteCookies("JSESSIONID")
        .and()
        .authorizeRequests()
        .antMatchers("/home/**").authenticated()
        .and()
        .rememberMe();
	}
	
}*/