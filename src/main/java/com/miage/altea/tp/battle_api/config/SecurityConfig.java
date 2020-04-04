package com.miage.altea.tp.battle_api.config;

import com.miage.altea.tp.battle_api.trainers.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

    @Configuration
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private TrainerService trainerService;

        public TrainerService getTrainerService() {
            return trainerService;
        }

        @Autowired
        public void setTrainerService(TrainerService trainerService) {
            this.trainerService = trainerService;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return userName -> Optional.ofNullable(trainerService.getTrainerByName(userName))
                    .map((trainer) ->
                            User.withUsername(trainer.getName())
                                    .password(trainer.getPassword())
                                    .roles("USER")
                                    .build()
                    ).orElseThrow(() -> new BadCredentialsException("No such user"));
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            super.configure(http);
            http.csrf().disable();
        }
    }