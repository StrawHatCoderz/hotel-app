package org.taj.hotel.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.taj.hotel.domain.User;
import org.taj.hotel.repositry.UserRepo;
import org.taj.hotel.view.UserRegistrationRequest;

public class AppUserDetailService implements UserDetailsService {
    private final UserRepo users;
    private final PasswordEncoder passwordEncoder;
    private IdGenerator idGenerator;

    public AppUserDetailService(UserRepo users, PasswordEncoder passwordEncoder, IdGenerator idGenerator) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.idGenerator = idGenerator;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = users.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException("Username %s not found".formatted(username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

    public void registerUser(UserRegistrationRequest request) {

        String id = this.idGenerator.generate();

        User user = new User(id, request.username(), passwordEncoder.encode(request.password()));

        this.users.save(user);
    }
}