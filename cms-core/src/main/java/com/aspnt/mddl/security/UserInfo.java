package com.aspnt.mddl.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public record UserInfo(String email, String name, List<? extends GrantedAuthority> actions) {

}
