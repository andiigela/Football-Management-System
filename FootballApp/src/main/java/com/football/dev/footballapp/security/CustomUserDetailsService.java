package com.football.dev.footballapp.security;

import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.models.principal.UserPrincipal;
import com.football.dev.footballapp.repository.jparepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username).get();
        if (user ==null){
            throw new UsernameNotFoundException("This user does not exists");
        }
        UserPrincipal userPrincipal=new UserPrincipal(user);
        return userPrincipal;
    }

}
