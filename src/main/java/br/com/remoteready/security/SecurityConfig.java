package br.com.remoteready.security;

import br.com.remoteready.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso público às páginas de login, logout e recursos estáticos
                        .requestMatchers("/login","/register", "/logout", "/error", "/css/**", "/js/**", "/images/**").permitAll()

                        // Permite acesso público à página home
                        .requestMatchers("/home").permitAll()


                        .requestMatchers("/api/**").permitAll()

                        // Restrições específicas para POSTS - apenas ADMIN pode acessar
                        .requestMatchers("/posts/**").hasRole("ADMIN")

                        // ADMIN e OPERADOR podem acessar companies e users
                        .requestMatchers("/companies/**", "/users/**").hasAnyRole("ADMIN", "OPERADOR")

                        // Qualquer outra rota requer autenticação
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Desabilita CSRF para facilitar testes (em produção, habilite!)

        return http.build();
    }
}