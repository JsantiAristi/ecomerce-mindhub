package com.HijasDelMonte.Ecomerce.Configuraciones;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAutorizacion {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/login","/api/clientes").permitAll()
                .antMatchers("/manager/**").hasAuthority("ADMIN")
                .antMatchers("/web/paginas/perfil.html", "/web/estilos/perfil.css", "/web/js/perfil.js").hasAuthority("CLIENTE")
                .antMatchers("/web/paginas/editarPerfil.html", "/web/estilos/editarPerfil.css", "/web/js/editarPerfil.js").hasAuthority("CLIENTE")
                .antMatchers("/web/paginas/pedidos.html", "/web/estilos/pedidos.css", "/web/js/pedidos.js").hasAuthority("CLIENTE")
                .antMatchers("/index.html","/web/**","/assets/**").permitAll()
                .antMatchers("/api/clientes","/rest/**").hasAuthority("ADMIN")
                .antMatchers("/api/clientes/actual","/api/cliente/orden","/h2-console","/api/cliente/comprobante").hasAnyAuthority("CLIENTE","ADMIN")
                .antMatchers("/api/productos","/api/productos/{id}").permitAll()
                .antMatchers(HttpMethod.POST,"/api/cliente/orden","/api/cliente/carrito","/api/cliente/comprobante", "/api/orden/actualizacion").hasAuthority("CLIENTE")

                .antMatchers(HttpMethod.PUT,"/api/cliente/carrito/suma","/api/cliente/carrito/resta").hasAuthority("CLIENTE")
                .antMatchers(HttpMethod.PUT,"/api/plantas").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/plantas").hasAuthority("ADMIN")
                .anyRequest().denyAll();
        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("contraseña")
                .loginPage("/api/login");
        http.logout().logoutUrl("/api/logout").deleteCookies( "JSESSIONID" );

        // turn off checking for CSRF tokens XCSRF.
        http.csrf().disable();
        // disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();
        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((request , response , exclusion ) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, authenticate) -> clearAuthenticationAttributes(req));
        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exclusion ) -> res.sendError(HttpServletResponse.SC_BAD_REQUEST));
        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();
    }
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
