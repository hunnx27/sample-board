package com.twodollar.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


//    @Autowired
//    private UserService userService;
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable() //csrf 토큰 비활성화 (테스트 시 걸어두는 게 좋음)
                .authorizeRequests()
                .antMatchers("/").authenticated() // 해당 주소로 들어오면 인증이 필요함
                .antMatchers("/index.html").authenticated() // 해당 주소로 들어오면 인증이 필요함
                // .antMatchers("/**").access("hasRole('ROLE_ADMIN') or hasROLE('Role_MANAGER')")
                // .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll() //이 3개 주소가 아니라면 권한이 허용된다.
        ;
        // .and()
        // .formLogin()
        // .login
        // .loginPage("/login");
        http.formLogin();

//        http.formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/")
//                .permitAll();
//
//        http.logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login")
//                .invalidateHttpSession(true);
//
//        http.exceptionHandling()
//                .accessDeniedPage("/denied");
//
//        http.headers().frameOptions().disable();
    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//    }

    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().withUser("admin").password("{noop}skcc!@3456")
                .roles("ADMIN");

                auth.inMemoryAuthentication().withUser("parkhw").password("{noop}1111")
                .roles("USER");

    }
}
