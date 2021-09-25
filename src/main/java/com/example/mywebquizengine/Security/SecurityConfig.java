package com.example.mywebquizengine.Security;
import com.example.mywebquizengine.Service.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


import javax.sql.DataSource;
import java.util.*;


@Configuration
@EnableWebSecurity(debug=true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Qualifier("userService")
    @Autowired
    protected UserDetailsService userDetailsService;


    // Биним в конфигурации
    /*@Bean
    public TokenBasedRememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices
                = new CustomRememberMeService(stringKey(), userDetailsService);
        return rememberMeServices;
    }*/



    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider()  {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    @Configuration
    @Order(1)
    public static class ApiConfigurationAdapter extends
            WebSecurityConfigurerAdapter {

        @Autowired
        private JWTFilter jwtFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.csrf().disable();

            http.antMatcher("/api/**")

                    .authorizeRequests()
                    .antMatchers("/api/register", "/api/jwt", "/activate/*", "/img/**",
                            "/api/quizzes", "/reg", "/geo", "/androidSign", "/api/signin", "/api/googleauth","/api/signup",
                            "/", "/signin", "/checkyandex", "/h2-console/**", "/.well-known/pki-validation/**",
                            "/static/forgotPassword.js", "/static/changePassword.js", "/update/userinfo/pswrdwithoutauth",
                            "/updatepass/**", "/pass/**", "/updatepassword/{activationCOde}", "/yandex_135f209071de02b1.html").permitAll()
                    .anyRequest().authenticated()

                    .and()
                    .formLogin()

                    /*.loginPage("/api/jwt")*//*.successForwardUrl("/api/jwt")*/.and().logout().logoutUrl("/api/logout").permitAll()

                    //.and().oauth2Login().defaultSuccessUrl("/loginSuccess")
                    .permitAll()
                    .and()
                    .logout()
                    .permitAll()
                    .and()
                    //.rememberMe().and()
                    // for h2-console correct view
                    .headers()
                    .frameOptions()
                    .sameOrigin().and()
                    .requiresChannel()
                    .anyRequest()
                    .requiresSecure().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        }
    }



    @Order(2)
    @Configuration
    public class FormConfigurationAdapter extends
            WebSecurityConfigurerAdapter {

        @Autowired
        DataSource dataSource;

    /*    @Autowired
        private PersistentTokenBasedRememberMeServices2 persistentTokenBasedRememberMeServices2;*/

        


        /*public void setPersistentTokenBasedRememberMeServices2(PersistentTokenBasedRememberMeServices2 persistentTokenBasedRememberMeServices2) {
            this.persistentTokenBasedRememberMeServices2 = persistentTokenBasedRememberMeServices2;
        }*/

        @Bean
        public String getKey() {
            return "secretkey";
        }

        /*@Bean
        public TokenBasedRememberMeServices2 tokenBasedRememberMeServices() {
            return new TokenBasedRememberMeServices2("secretkey", userDetailsService);
        }*/

        /*@Bean
        public PersistentTokenBasedRememberMeServices2 persistentTokenBasedRememberMeServices2() {
            return new PersistentTokenBasedRememberMeServices2("secretkey", userDetailsService, persistentTokenRepository());
        }*/

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
            tokenRepository.setDataSource(dataSource);
            return tokenRepository;
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.csrf().disable();

            http

                    .authorizeRequests()
                    .antMatchers("/register", "/activate/*", "/img/**",
                            "/quizzes", "/reg",  "/androidSign",
                            "/", "/signin", "/checkyandex", "/h2-console/**", "/.well-known/pki-validation/**",
                            "/static/forgotPassword.js", "/static/changePassword.js", "/update/userinfo/pswrdwithoutauth",
                            "/updatepass/**", "/pass/**", "/updatepassword/{activationCOde}", "/yandex_135f209071de02b1.html").permitAll()
                    .anyRequest().authenticated()

                    .and()
                    .formLogin()

                    .loginPage("/signin").defaultSuccessUrl("/profile").failureUrl("/signin?error")
                    .and().oauth2Login().userInfoEndpoint()
                    .oidcUserService(this.oidcUserService()).userService(this.oAuth2UserService())
                    .userAuthoritiesMapper(this.userAuthoritiesMapper()).and()
                    .defaultSuccessUrl("/loginSuccess")
                    .permitAll()/*.and().rememberMe().rememberMeParameter("remember-me")
                    //.and().key("secretkey").alwaysRemember(true).rememberMeServices(rememberMeServices())*/


                    .and().rememberMe()
                    .key("secretkey").alwaysRemember(true).userDetailsService(userDetailsService)
                    .tokenRepository(persistentTokenRepository())
                    /*.rememberMeServices(new PersistentTokenBasedRememberMeServices2(getKey(), userDetailsService))*/

                    /*.rememberMeServices(rememberMeServices())
                    .rememberMeParameter("remember-me")*/

                    .and()
                    .logout()
                    .permitAll()
                    .and()
                    // for h2-console correct view
                    .headers()
                    .frameOptions()
                    .sameOrigin().and()
                    .requiresChannel()
                    .anyRequest()
                    .requiresSecure();
    }


        private OAuth2UserService<OAuth2UserRequest, OAuth2User>  oAuth2UserService() {

            /*final OAuth2UserService delegate = new OAuth2UserService() {
                @Override
                public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
                    return null;
                }
            };*/

            final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

            return userRequest -> {

                OAuth2User defaultOAuth2User = delegate.loadUser(userRequest);

                return new DefaultOAuth2User(defaultOAuth2User.getAuthorities(), defaultOAuth2User.getAttributes(), "login");


            };
        }


        private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
            final OidcUserService delegate = new OidcUserService();

            return (userRequest) -> {
                // Delegate to the default implementation for loading a user

               // userRequest = null;
                OidcUser oidcUser = delegate.loadUser(userRequest);


                Set<GrantedAuthority> mappedAuthorities = new HashSet<>(oidcUser.getAuthorities());

                /*OidcUser oidcUser = delegate.loadUser(userRequest);

                //List<GrantedAuthority> authorities2 = AuthorityUtils.commaSeparatedStringToAuthorityList(commaSeparatedListOfAuthorities);


                OAuth2AccessToken accessToken = userRequest.getAccessToken();
                Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
                mappedAuthorities.add((GrantedAuthority) oidcUser.getAuthorities().toArray()[0]);

                // TODO
                // 1) Fetch the authority information from the protected resource using accessToken
                // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities

                // 3) Create a copy of oidcUser but use the mappedAuthorities instead



                oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());*/

                Map<String, Object> map = new HashMap<>(oidcUser.getIdToken().getClaims());
                map.put("myLogin", oidcUser.getIdToken().getEmail().replace("@gmail.com", ""));

                OidcIdToken oidcIdToken = new OidcIdToken(oidcUser.getIdToken().getTokenValue(), oidcUser.getIssuedAt(),
                        oidcUser.getExpiresAt(), map);

                oidcUser = new DefaultOidcUser(mappedAuthorities, oidcIdToken, oidcUser.getUserInfo(), "myLogin");




                return oidcUser;

            };
        }


        private GrantedAuthoritiesMapper userAuthoritiesMapper() {
            return (authorities) -> {
                Set<GrantedAuthority> mappedAuthorities = new HashSet<>();





                authorities.forEach(authority -> {
                    if (authority instanceof OidcUserAuthority) {
                        OidcUserAuthority oidcUserAuthority = (OidcUserAuthority)authority;

                        OidcIdToken idToken = oidcUserAuthority.getIdToken();
                        OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();

                        // Map the claims found in idToken and/or userInfo
                        // to one or more GrantedAuthority's and add it to mappedAuthorities

                    } else if (authority instanceof OAuth2UserAuthority) {
                        OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)authority;

                        Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

                        // Map the attributes found in userAttributes
                        // to one or more GrantedAuthority's and add it to mappedAuthorities

                    }
                });

                return mappedAuthorities;
            };
        }


    }
}
