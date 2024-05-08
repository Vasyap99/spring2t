package org.example;


//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.example.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.models.User;

import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Component
public class AuthProvider implements AuthenticationProvider {
    @Autowired
    UserRepository ur;

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String user=a.getName();
        String pasw=a.getCredentials().toString();

        Optional<User>o=ur.findByName(user);

        System.out.println(">>>"+o.orElse(new User("notFound","notFound")).getName());

        System.out.println(">>LOGINING:"+user+":"+pasw);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, pasw, List.of(new SimpleGrantedAuthority(o.get().getRole())));
        if( o.orElseThrow(()->new BadCredentialsException("NO SUCH USER")) .getName() .equals(user) ) {
            System.out.println("T");
            return usernamePasswordAuthenticationToken;
        }else {
            System.out.println("F");
            throw new BadCredentialsException("err");
        }
    }

    @Override
    public boolean supports(Class<?> a){
        return true;
    }
}
