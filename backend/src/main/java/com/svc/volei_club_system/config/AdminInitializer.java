package com.svc.volei_club_system.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.svc.volei_club_system.enums.Role;
import com.svc.volei_club_system.model.UsuarioModel;
import com.svc.volei_club_system.repository.UsuarioRepository;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner criarAdmin(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {

        return args -> {

            String email = "admin@svc.com";

            // verifica se já existe admin
            if (usuarioRepository.findByEmail(email).isEmpty()) {

                UsuarioModel admin = new UsuarioModel();

                admin.setNome("Administrador");
                admin.setDataNascimento(
        java.time.LocalDate.of(2000, 1, 1)
);
                admin.setTelefone("(74)99999-9999");

                admin.setEmail(email);

                // senha svc2021 criptografada corretamente
                admin.setSenha(
                        passwordEncoder.encode("svc2021")
                );

                admin.setRole(Role.ADMIN);

                admin.setAtivo(true);

                usuarioRepository.save(admin);

                System.out.println("ADMIN criado com sucesso!");

            }

        };

    }

}