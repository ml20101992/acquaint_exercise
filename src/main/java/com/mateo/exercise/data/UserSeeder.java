package com.mateo.exercise.data;

import com.mateo.exercise.data.models.UserModel;
import com.mateo.exercise.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Autowired
    public UserSeeder(UserRepository userRepository) {

        this.encoder = passwordEncoder();
        this.userRepository = userRepository;

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @EventListener
    public void seed(ContextRefreshedEvent event) {
        UserModel model = new UserModel("baseuser", encoder.encode("password321"), 1, true );
        UserModel userAdvanced = new UserModel("advanceduser", encoder.encode("password123"), 2, true);

        userRepository.save(model);
        userRepository.save(userAdvanced);
    }

}
