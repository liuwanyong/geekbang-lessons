package org.geektimes.projects.user.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geektimes.context.ComponentContext;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.repository.DatabaseUserRepository;
import org.geektimes.projects.user.repository.UserRepository;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.web.mvc.controller.PageController;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.sql.SQLException;

@Path("/register")
public class RegisterController implements PageController {

    private static final Log log= LogFactory.getLog(RegisterController.class);

    @GET
    @POST
    @Path("")
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        String method=request.getMethod();
        if("GET".equals(method)){
            return "register-form.jsp";
        }
        User user = getUser(request);
        UserService userService;
        userService = (UserService)ComponentContext.getInstance().getComponent("bean/UserService");
        try {
            userService.register(user);
        }catch (Exception ex){
            request.setAttribute("errors",ex.getMessage());
            request.getRequestDispatcher("register-failed.jsp").forward(request,response);
        }

        log.info(user);

//        try{
//            DBConnectionManager dbConnectionManager = getDbConnectionManager(request);
//            UserRepository repository=new DatabaseUserRepository(dbConnectionManager);
//            UserService userService=new UserServiceImpl(repository);
//            if(userService.register(user)) {
//                return "register-success.jsp";
//            }else{
//                return "register-failed.jsp";
//            }
//        }catch (Exception ex){
//            return "register-failed.jsp";
//        }
        return "register-success.jsp";

    }

    private User getUser(HttpServletRequest request) {
        String username= request.getParameter("username");
        String password= request.getParameter("password");
        String email= request.getParameter("email");
        String phoneNumber= request.getParameter("phoneNumber");

        User user=new User();
        user.setName(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        return user;
    }

}
