package org.example.config;

import org.example.AuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@SpringBootConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthProvider ap;

    @Override
    public void configure(AuthenticationManagerBuilder a) throws Exception{
        System.out.println(">>>SC-c()");
        a.authenticationProvider(ap);
    }


    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/login/**").permitAll()
                .antMatchers("/book/**").authenticated()//hasAnyRole()
                .antMatchers("/**").authenticated()//hasAnyRole()
                .and().formLogin()
        ;
        //http.logout();
        return http.build();
    }*/  ///ERR: Found WebSecurityConfigurerAdapter as well as SecurityFilterChain  Please select just one.

    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()

                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/ovo/**").hasRole("OVO")
                .antMatchers("/login/**").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/book/**").authenticated()//hasAnyRole()
                .antMatchers( "/favicon.ico").permitAll()
                .antMatchers("/**").authenticated()//hasAnyRole()
                .and()
                .formLogin().successHandler(new myAuthenticationSuccessHandler())
        ;
        ///return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }
}

//обаботчик успешной аутентификации
class myAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {

        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    protected void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            /*logger.debug(
                    "Response has already been committed. Unable to redirect to "
                            + targetUrl);*/
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }


    protected String determineTargetUrl(final Authentication authentication) {

        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_USER", "/");
        roleTargetUrlMap.put("ROLE_ADMIN", "/");
        roleTargetUrlMap.put("ROLE_OVO", "/ovo");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if(roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }
}