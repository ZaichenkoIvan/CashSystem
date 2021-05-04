package com.shop.config;

import com.shop.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    private LoginSuccessHandler loginSuccessHandler;

    private BCryptPasswordEncoder encoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index", "/registration", "/signUp", "/login").permitAll()
                .antMatchers("/goods/**", "/goods").hasAnyAuthority("goods_spec")
                .antMatchers("/cancel/**", "/cancel").hasAnyAuthority("senior_cashier")
                .antMatchers("/check/**", "/check").hasAnyAuthority("cashier")

                .anyRequest().authenticated()
                .and()
                .csrf().disable().formLogin().failureUrl("/login?loginError=true").loginPage("/login").successHandler(loginSuccessHandler).permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                .and().exceptionHandling()
                .accessDeniedPage("/error");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(encoder);
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**");
    }
}
