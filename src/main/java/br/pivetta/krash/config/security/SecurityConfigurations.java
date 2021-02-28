package br.pivetta.krash.config.security;

import br.pivetta.krash.repository.ClientRepository;
import br.pivetta.krash.service.AuthService;
import br.pivetta.krash.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
    private final AuthService authService;
    private final TokenService tokenService;
    private final ClientRepository clientRepository;

    public SecurityConfigurations(AuthService authService, TokenService tokenService, ClientRepository clientRepository) {
        this.authService = authService;
        this.tokenService = tokenService;
        this.clientRepository = clientRepository;
    }

    // Configurações de autenticação.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // Configurações de autorização.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .authorizeRequests()
                .antMatchers("/permission", "/permission/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/client").permitAll()
                .antMatchers(HttpMethod.PUT, "/client/*").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/auth/is-token-valid").permitAll()
                .antMatchers(HttpMethod.GET, "/file/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenService, clientRepository), UsernamePasswordAuthenticationFilter.class);
    }

    // Configurações de recursos estáticos.
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    // Torna a classe de autenticação manual injetável.
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
