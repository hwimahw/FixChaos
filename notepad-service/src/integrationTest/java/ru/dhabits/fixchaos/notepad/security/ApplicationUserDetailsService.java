package ru.dhabits.fixchaos.notepad.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dhabits.fixchaos.notepad.db.repository.UserRepository;


@Service
public class ApplicationUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User is not found");
        });
    }
}
