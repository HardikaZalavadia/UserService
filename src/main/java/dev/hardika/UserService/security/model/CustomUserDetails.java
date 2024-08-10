package dev.hardika.UserService.security.model;

import dev.hardika.UserService.Entity.Role;
import dev.hardika.UserService.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private User user;
    public CustomUserDetails(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<CustomGrantedAuthority> authorities = new ArrayList<>();

        for(Role role : user.getRole()){
            authorities.add(new CustomGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmailId();
    }

    @Override
    public boolean isAccountNonExpired() {
        //not implement this logic so for me it is always account is not expired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //not implement this logic so for me it is always account is not locked
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // if(Date.now() - lastPasswordUpdate() > 90 ) return false else return true
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
