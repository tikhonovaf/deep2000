package ru.atikhonov.deep2000.backend.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.atikhonov.deep2000.backend.model.Demo2000User;
import ru.atikhonov.deep2000.backend.repository.Demo2000UserRepository;

import javax.inject.Inject;

@Component
public class DaoUserDetailsService implements UserDetailsService {
    @Inject
    private Demo2000UserRepository migrUserRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//        demo2000User user = migrUserRepository.findByLogin(login)
//                .orElseThrow(() -> new UsernameNotFoundException("Username " + login + " not found!"));

        Demo2000User user = new Demo2000User();
        user.setLogin("ADMIN");
        user.setPassword("8257a3811b9f6bb9d59dfb3931e220fa5574cee38fff551066caca1a50b1691ebdffa87f2d7213910e8bdbcf4d669c2756e57196667dd8f5e8af66971b2");

        //                .orElseThrow(() -> new UsernameNotFoundException("Username " + login + " not found!"));

        return User.withUsername(login)
                .password(user.getPassword())
                .roles(user.getRole() == null ? "ADMIN" : user.getRole().getName())
                .build();
    }
}
