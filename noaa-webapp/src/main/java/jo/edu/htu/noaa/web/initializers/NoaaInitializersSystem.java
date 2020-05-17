package jo.edu.htu.noaa.web.initializers;

import jo.edu.htu.DBUsersRepository;
import jo.edu.htu.noaa.Authentication;
import jo.edu.htu.noaa.users.AuthenticationHandler;
import jo.edu.htu.noaa.web.servlets.AuthenticationServlets;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Set;

public class NoaaInitializersSystem implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext ctx) throws ServletException {
        RegisterationLoginSrevlets(ctx);


    }

    private void RegisterationLoginSrevlets(ServletContext ctx) {
        AuthenticationHandler authenticationHandle = new Authentication(dbUsersRepository());
        AuthenticationServlets servlets = new AuthenticationServlets(authenticationHandle);
        ServletRegistration.Dynamic registration = ctx.addServlet("authenticationHandler", new AuthenticationServlets(authenticationHandle));
        registration.addMapping("/login");
    }

    private DBUsersRepository dbUsersRepository() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/countries?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return new DBUsersRepository(dataSource);
    }
}

