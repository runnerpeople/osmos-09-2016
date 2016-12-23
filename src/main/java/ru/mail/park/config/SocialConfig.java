//package ru.mail.park.config;
//
//import javax.inject.Inject;
//import javax.sql.DataSource;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//import org.springframework.context.annotation.ScopedProxyMode;
//import org.springframework.core.env.Environment;
//import org.springframework.security.crypto.encrypt.Encryptors;
//import org.springframework.social.UserIdSource;
//import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
//import org.springframework.social.config.annotation.EnableSocial;
//import org.springframework.social.config.annotation.SocialConfigurer;
//import org.springframework.social.connect.Connection;
//import org.springframework.social.connect.ConnectionFactoryLocator;
//import org.springframework.social.connect.ConnectionRepository;
//import org.springframework.social.connect.UsersConnectionRepository;
//import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
//import org.springframework.social.connect.web.ProviderSignInController;
//import org.springframework.social.vkontakte.api.*;
//import org.springframework.social.vkontakte.connect.VKontakteConnectionFactory;
//import ru.mail.park.user.SecurityContext;
//import ru.mail.park.user.SimpleConnectionSignUp;
//import ru.mail.park.user.SimpleSignInAdapter;
//
//
//@Configuration
//@EnableSocial
//public class SocialConfig implements SocialConfigurer {
//
//    @Inject
//    private DataSource dataSource;
//
//
//    @Override
//    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
//        cfConfig.addConnectionFactory(new VKontakteConnectionFactory(env.getProperty("vkontakte.appKey"), env.getProperty("vkontakte.appSecret")));
//    }
//
//
//    @Override
//    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
//        repository.setConnectionSignUp(new SimpleConnectionSignUp());
//        return repository;
//    }
//
//    public UserIdSource getUserIdSource() {
//        return new UserIdSource() {
//            @Override
//            public String getUserId() {
//                return SecurityContext.getCurrentUser().getId();
//            }
//        };
//    }
//
//    @Bean
//    @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
//    public VKontakte vkontakte(ConnectionRepository repository) {
//        Connection<VKontakte> connection = repository.findPrimaryConnection(VKontakte.class);
//        return connection != null ? connection.getApi() : null;
//    }
//
//    @Bean
//    public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) {
//        return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, new SimpleSignInAdapter());
//    }
//
//}
