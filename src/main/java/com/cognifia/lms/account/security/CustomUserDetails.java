package com.cognifia.lms.account.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

import com.cognifia.lms.account.security.token.model.CurrentUser;

@Getter
public class CustomUserDetails implements UserDetails {

    private final int userId;
    private final String username;
    private final String password;
    private final boolean active;
    private final List<GrantedAuthority> authorities;

    //get the db values and set it to user
    public CustomUserDetails(CurrentUser user) {
        userId = user.getUserId();
        username = user.getUsername();
        password = user.getPassword();
        active = user.isEnabled();
        authorities = Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
    }

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
        return active;
    }
}
