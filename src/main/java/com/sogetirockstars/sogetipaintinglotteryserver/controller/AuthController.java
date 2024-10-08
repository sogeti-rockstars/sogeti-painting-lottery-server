package com.sogetirockstars.sogetipaintinglotteryserver.controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("user")
public class AuthController {
    @GetMapping
    public ResponseEntity<?> user(Principal user) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        if (user == null) {
            map.put("name", "USER");
            map.put("roles", new String[] { "ROLE_USER" });
        } else {
            map.put("name", user.getName());
            map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) user).getAuthorities()));
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ResponseEntity<>(Map.of("name", "USER", "roles", new String[] { "ROLE_USER" }), HttpStatus.OK);

    }
}
