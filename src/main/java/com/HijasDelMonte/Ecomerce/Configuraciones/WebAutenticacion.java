package com.HijasDelMonte.Ecomerce.Configuraciones;

import com.HijasDelMonte.Ecomerce.Models.Clientes;
import com.HijasDelMonte.Ecomerce.Repositorios.ClientesRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAutenticacion extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    private ClientesRepositorio clientesRepositorio;
    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( inputName -> {
            Clientes cliente = clientesRepositorio.findByEmail(inputName);
            if (cliente != null) {
                if (cliente.getEmail().equals("juan@gmail.com")){
                    return new User(cliente.getEmail(), cliente.getContraseña(),
                            AuthorityUtils.createAuthorityList("ADMIN"));
                }
                return new User(cliente.getEmail(), cliente.getContraseña(),
                        AuthorityUtils.createAuthorityList("CLIENTE"));
            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputName);
            }
        });
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();}
}
