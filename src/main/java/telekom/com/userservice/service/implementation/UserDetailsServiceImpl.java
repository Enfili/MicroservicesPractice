package telekom.com.userservice.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import telekom.com.userservice.database.UserDatabase;
import telekom.com.userservice.model.User;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDatabase userDatabase;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDatabase.findByName(username);

        return user.map(value -> org.springframework.security.core.userdetails.User.builder()
                .username(value.getName())
                .password(new BCryptPasswordEncoder().encode(value.getPassword()))
                .roles("USER")
                .build()).orElse(null);
    }
}
