package com.ActivityCenterServer.model;

import io.jsonwebtoken.lang.Collections;
import net.bytebuddy.build.Plugin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.awt.image.SinglePixelPackedSampleModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsPrincipal implements UserDetails {

    private User user;

    public UserDetailsPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> autorities = new ArrayList<SimpleGrantedAuthority>();
        autorities.add(new SimpleGrantedAuthority("USER"));
        if (user.isAdmin == true) {
            autorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        return autorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getEmail() {return user.getEmail(); }

    public String getFirstname() { return user.firstname; }

    public String getLastname() { return user.lastname; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
