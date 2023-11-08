package ru.liga.deliveryservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.liga.common.entities.ServiceUser;
import ru.liga.common.repos.ServiceUserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = "ru.liga.common")
public class CustomUserDetailsService implements UserDetailsService {

    private final ServiceUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        ServiceUser user = userRepo.findFirstById(UUID.fromString(userName)).get();
        if (user == null) throw new UsernameNotFoundException("Неизвестный пользователь - " + userName);
        return org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                .username(user.getId().toString())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }
}
