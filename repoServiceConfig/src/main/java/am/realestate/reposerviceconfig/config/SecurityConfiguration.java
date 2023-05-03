package am.realestate.reposerviceconfig.config;


import am.realestate.reposerviceconfig.secur.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.GET, "/")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/save")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/myAccount")
                .hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/activate")
                .hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/activateCode")
                .hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/admin111")
                .hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/user/delete")
                .hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/myAccount/editProfile")
                .hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/updateProfile")
                .hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/addHome")
                .hasAnyAuthority("USER")
                .antMatchers(HttpMethod.POST, "/addHome")
                .hasAnyAuthority("USER")
                .antMatchers(HttpMethod.POST, "/sendMessage")
                .hasAnyAuthority("USER")
                .antMatchers(HttpMethod.GET, "/messageUserToUser")
                .hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/delete/messageUserToUser")
                .hasAnyAuthority("USER", "ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .failureForwardUrl("/fail_login")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
        return http.build();
    }
}

