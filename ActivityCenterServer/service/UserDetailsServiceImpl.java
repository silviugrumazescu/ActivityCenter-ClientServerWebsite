package com.ActivityCenterServer.service;

import com.ActivityCenterServer.database.repository.UserRepository;
import com.ActivityCenterServer.model.User;
import com.ActivityCenterServer.model.UserDetailsPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(username);

        if(!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }

        return new UserDetailsPrincipal(user.get());
    }
}
