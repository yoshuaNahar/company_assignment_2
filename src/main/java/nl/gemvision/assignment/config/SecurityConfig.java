package nl.gemvision.assignment.config;

import nl.gemvision.assignment.security.JwtTokenFilterConfigurer;
import nl.gemvision.assignment.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private JwtTokenProvider jwtTokenProvider;

  private UserDetailsService userDetailsService;

  @Autowired
  public SecurityConfig(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.userDetailsService = userDetailsService;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
        .apply(new JwtTokenFilterConfigurer(jwtTokenProvider))
        .and()
        .cors()
        .and()
        .authorizeRequests()
            .antMatchers("/", "/*","/index.html","/signup", "/login", "/chat-app", "/chat-app/**","/h2-console/**").permitAll()
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .antMatchers("/api/**").hasAuthority("USER")
            .anyRequest().authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .httpBasic().disable()
        .formLogin().disable()
        .logout().disable()
        .csrf().disable()
        .headers().frameOptions().disable();
    // @formatter:on
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

}
