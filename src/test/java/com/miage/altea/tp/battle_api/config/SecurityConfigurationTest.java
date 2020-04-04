package com.miage.altea.tp.battle_api.config;

import com.miage.altea.tp.battle_api.bo.Trainer;
import com.miage.altea.tp.battle_api.trainers.TrainerService;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SecurityConfigurationTest {
    @Test
    void securityConfig_shouldExtendWebSecurityConfigurerAdapter() {
        assertTrue(WebSecurityConfigurerAdapter.class.isAssignableFrom(SecurityConfig.class));
    }

    @Test
    void passwordEncoder_shouldBeBCryptPasswordEncoder() {
        var securityConfig = new SecurityConfig();
        var passwordEncoder = securityConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
        assertEquals(BCryptPasswordEncoder.class, passwordEncoder.getClass());
    }

    @Test
    void userDetailsService_shouldUseTrainerService() {
        var securityConfig = new SecurityConfig();

        var trainerService = mock(TrainerService.class);
        var trainer = new Trainer();
        trainer.setName("Alexis");
        trainer.setPassword("test");
        when(trainerService.getTrainerByName("Alexis")).thenReturn(trainer);

        securityConfig.setTrainerService(trainerService);

        var userDetailsService = securityConfig.userDetailsService();

        var alexis = userDetailsService.loadUserByUsername("Alexis");
        verify(trainerService).getTrainerByName("Alexis");

        assertNotNull(alexis);
        assertEquals("Alexis", alexis.getUsername());
        assertEquals("test", alexis.getPassword());
        assertTrue(alexis.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void userDetailsService_shouldThrowABadCredentialsException_whenUserDoesntExists() {
        var securityConfig = new SecurityConfig();
        var trainerService = mock(TrainerService.class);
        securityConfig.setTrainerService(trainerService);

        var userDetailsService = securityConfig.userDetailsService();

        var exception = assertThrows(BadCredentialsException.class, () -> userDetailsService.loadUserByUsername("Alexis"));
        assertEquals("No such user", exception.getMessage());

        verify(trainerService).getTrainerByName("Alexis");
    }

}
