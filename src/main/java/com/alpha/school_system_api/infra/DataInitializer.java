package com.alpha.school_system_api.infra;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alpha.school_system_api.model.user.Role;
import com.alpha.school_system_api.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepo;

    public DataInitializer(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public void run(String... args) {
        if (roleRepo.findByNome("ROLE_ADMIN").isEmpty()) {
            roleRepo.save(new Role(null, "ROLE_ADMIN"));
        }

        if (roleRepo.findByNome("ROLE_USUARIO").isEmpty()) {
            roleRepo.save(new Role(null, "ROLE_USUARIO"));
        }
    }

}
