package com.sogetirockstars.sogetipaintinglotteryserver.service;

import com.sogetirockstars.sogetipaintinglotteryserver.model.UserAccount;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.AuthenticationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

/**
 * AuthService
 */
@Service
public class AuthService {
    private final AuthenticationRepository authRepo;
    private UserDetailsManager manager;
    // private final int minPassLength = 4;

    @Autowired
    public AuthService(AuthenticationRepository authRepo) {
        this.authRepo = authRepo;
    }

    public UserDetailsManager getManager() {
        return manager;
    }

    // Set by AuthConfig
    public void setManager(UserDetailsManager manager) {
        this.manager = manager;
    }

    public String getCurrentSavedPass() {
        String currPass = authRepo.existsById("admin") ? authRepo.findById("admin").get().getPass() : null;
        return ensureEncryption(currPass);
    }

    // Todo: Password validation for length?
    public String setSavedPassword(String newPass) {
        String encPass = ensureEncryption(newPass);
        if (encPass == null)
            return null;
        authRepo.saveAndFlush(new UserAccount("admin", encPass));
        return encPass;
    }

    public String setPassForCurrentUser(String newPass) {
        String encPass = setSavedPassword(newPass);
        if (encPass == null)
            return null;
        String oldPass = this.manager.loadUserByUsername("admin").getPassword();
        this.manager.changePassword(oldPass, encPass);
        return encPass;
    }

    private String ensureEncryption(String origPass) {
        if (origPass != null && (origPass.length() < 8 || !origPass.substring(0, 8).equals("{bcrypt}")))
            return "{bcrypt}" + BCrypt.hashpw(origPass, BCrypt.gensalt());
        return origPass;
    }
}
