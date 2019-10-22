package org.o7planning.sbsecurity.service;

import java.util.ArrayList;
import java.util.List;

import org.o7planning.sbsecurity.dao.AppUserRepository;
import org.o7planning.sbsecurity.entity.AppUser;
import org.o7planning.sbsecurity.dao.AppRoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;

	public UserDetailsServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository) {
		this.appUserRepository = appUserRepository;
		this.appRoleRepository = appRoleRepository;
	}

	@Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser = this.appUserRepository.findUserAccount(userName);

        if (appUser == null) {
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = this.appRoleRepository.getRoleNames(appUser.getUserId());

        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        return new User(appUser.getUserName(), appUser.getEncryptedPassword(), grantList);
    }

}