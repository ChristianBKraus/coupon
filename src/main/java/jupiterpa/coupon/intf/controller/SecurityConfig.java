package jupiterpa.coupon.intf.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import jupiterpa.infrastructure.config.BaseSecurityConfig;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends BaseSecurityConfig {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET,  CouponController.PATH+"/**" ).hasAnyRole("USER","ADMIN")
			.antMatchers(HttpMethod.POST,  CouponController.PATH+"/withdraw/**" ).hasAnyRole("USER","ADMIN")
			.antMatchers(HttpMethod.POST, CouponController.PATH+"/issue/**" ).hasRole("ADMIN")
			.anyRequest().permitAll();
	}
}