package jo.edu.htu.noaa.web.initializers;

import jo.edu.htu.DBGlobalSummaryOfDayRepository;
import jo.edu.htu.DBStationRepository;
import jo.edu.htu.DBUsersRepository;
import jo.edu.htu.noaa.*;
import jo.edu.htu.noaa.gsod.ImportGSODHandler;
import jo.edu.htu.noaa.gsod.ListGSODHandler;
import jo.edu.htu.noaa.stations.ImportStationsHandler;
import jo.edu.htu.noaa.stations.ListStationsHandler;
import jo.edu.htu.noaa.users.*;
import jo.edu.htu.noaa.web.filters.AuthenticationFilter;
import jo.edu.htu.noaa.web.servlets.*;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.*;
import java.util.EnumSet;
import java.util.Set;

public class NoaaInitializersSystem implements ServletContainerInitializer {

    private BasicDataSource dataSource;

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext ctx) throws ServletException {
        getBasicDataSource();
        RegistrationLoginServlets(ctx);
        RegistrationAddUserServlets(ctx);
        RegistrationChangePasswordServlets(ctx);
        RegistrationImportGsodServlets(ctx);
        RegistrationImportStationsServlets(ctx);
        RegistrationListGsodServlets(ctx);
        RegistrationListStationsServlets(ctx);
        RegistrationUserManagementServlets(ctx);
        registerAuthenticationFilter(ctx);


    }

    private void RegistrationUserManagementServlets(ServletContext ctx) {
        ListAllUsers listAllUsers = new ListAllUsers(dbUsersRepository());
        EnableUserHandler enableUserHandler = new EnableUser(dbUsersRepository());
        DisableUserHandler disableUserHandler = new DisableUser(dbUsersRepository());
        UserManagementServlets userManagementServlets = new UserManagementServlets(listAllUsers, enableUserHandler, disableUserHandler);
        ServletRegistration.Dynamic userManagement = ctx.addServlet("userManagementServlets", userManagementServlets);
        userManagement.addMapping("/user-management");

    }


    private void RegistrationImportStationsServlets(ServletContext ctx) {
        ImportStationsHandler importStationsHandler = new ImportStation(dbStationRepository());
        ImportStationsServlets importStationsServlets = new ImportStationsServlets(importStationsHandler);
        ServletRegistration.Dynamic importStations = ctx.addServlet("importStationsServlets", importStationsServlets);
        importStations.addMapping("/import-stations");

    }

    private void RegistrationImportGsodServlets(ServletContext ctx) {
        ImportGSODHandler importGSODHandler = new ImportGlobalSummaryOfDay(dbGsdoRepository());
        ImportGsodServlets importGsodServlets = new ImportGsodServlets(importGSODHandler);
        ServletRegistration.Dynamic importGsod = ctx.addServlet("importGsodServlets", importGsodServlets);
        importGsod.addMapping("/import-gsod");
    }

    private void registerAuthenticationFilter(ServletContext ctx) {
        FilterRegistration.Dynamic filterRegistration = ctx.addFilter("authenticationFilter", new AuthenticationFilter());
        filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false,
                "/");
    }

    private void RegistrationListStationsServlets(ServletContext ctx) {
        ListStationsHandler listStationsHandler = new ListStations(dbStationRepository());
        ListStationsServlets listStationsServlets = new ListStationsServlets(listStationsHandler);
        ServletRegistration.Dynamic listStations = ctx.addServlet("listStationsServlets", listStationsServlets);
        listStations.addMapping("/list-stations");


    }

    private void RegistrationListGsodServlets(ServletContext ctx) {
        ListGSODHandler listGSODHandler = new ListGSOD(dbGsdoRepository());
        ListGsodServlets listGsodServlets = new ListGsodServlets(listGSODHandler);
        ServletRegistration.Dynamic listGsod = ctx.addServlet("ListGsodServlets", listGsodServlets);
        listGsod.addMapping("/list-gsod");
    }

    private void RegistrationChangePasswordServlets(ServletContext ctx) {
        ChangePasswordHandler changePasswordHandler = new ChangePassword(dbUsersRepository());
        ChangePasswordServlets changePasswordServlets = new ChangePasswordServlets(changePasswordHandler);
        ServletRegistration.Dynamic changePassword = ctx.addServlet("changePasswordServlets", changePasswordServlets);
        changePassword.addMapping("/change-password");
    }

    private void RegistrationAddUserServlets(ServletContext ctx) {
        AddUserHandler addUserHandler = new AddUser(dbUsersRepository());
        AddUserServlets addUserServlets = new AddUserServlets(addUserHandler);
        ServletRegistration.Dynamic addUser = ctx.addServlet("addUserServlets", addUserServlets);
        addUser.addMapping("/add-user");
    }

    private void RegistrationLoginServlets(ServletContext ctx) {
        AuthenticationHandler authenticationHandle = new Authentication(dbUsersRepository());
        AuthenticationServlets authenticationServlets = new AuthenticationServlets(authenticationHandle);
        ServletRegistration.Dynamic registration = ctx.addServlet("authenticationServlets", authenticationServlets);
        registration.addMapping("/login");
    }

    private DBUsersRepository dbUsersRepository() {
        // TODO this should be only one datasource for all repositories
        BasicDataSource dataSource = getBasicDataSource();
        return new DBUsersRepository(dataSource);
    }

    private DBGlobalSummaryOfDayRepository dbGsdoRepository() {
        BasicDataSource dataSource = getBasicDataSource();
        return new DBGlobalSummaryOfDayRepository(dataSource);
    }

    private DBStationRepository dbStationRepository() {
        BasicDataSource dataSource = getBasicDataSource();
        return new DBStationRepository(dataSource);
    }

    private BasicDataSource getBasicDataSource() {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setUrl("jdbc:mysql://localhost:3306/countries?serverTimezone=UTC");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        }
        return dataSource;
    }
}

