package com.cognifia.lms.account.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cognifia.lms.account.security.token.AuthorizeException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import com.cognifia.lms.account.infrastructure.adapter.UserAccountAdapter;
import com.cognifia.lms.account.infrastructure.adapter.UserLoginAdapter;
import com.cognifia.lms.account.model.Account;
import com.cognifia.lms.account.model.UserLogin;
import com.cognifia.lms.account.model.UserStatus;
import com.cognifia.lms.account.security.token.model.CurrentUser;
import com.cognifia.lms.common.exception.AppMessageException;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private UserAccountAdapter userAccountAdapter;
    private UserLoginAdapter loginAdapter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = userAccountAdapter.getAccountByEmailAddress(username);
        UserLogin login = loginAdapter.getUserLoginById(account.getUserId());
        validate(account, login);
        return getCurrentUser(username, account, login);
    }

    private CurrentUser getCurrentUser(String username, Account account, UserLogin login) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        List<GrantedAuthority> authorities = Arrays.stream(("USER").split(",")).map(SimpleGrantedAuthority::new)
                                                   .collect(Collectors.toList());
        CurrentUser user = new CurrentUser(account.getUserId(), username, login.getPassword(), enabled,
                                           accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        user.setFirstName(account.getPersonInfo().getFirstName());
        user.setLastName(account.getPersonInfo().getLastName());
        user.setRoles(account.getRoleList());
        return user;
    }

    private void validate(Account account, UserLogin login) {
        if ((account == null) || (account.getUserId() < 1)) {
            throw new UsernameNotFoundException("User Name is not found");
        }
        if ((account.getUserStatus() == null) || UserStatus.DISABLE.equals(account.getUserStatus())) {
            throw new AppMessageException("login.validation.disable.user");
        }

        if (UserStatus.PENDING.equals(account.getUserStatus())) {
            throw new AuthorizeException("login.validation.pending.user");
        }
        if (login.isLocked()) {
            throw new AuthorizeException("login.validation.locked");
        }
    }
}
