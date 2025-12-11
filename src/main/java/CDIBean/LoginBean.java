/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CDIBean;

import Entity.Users;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import record.KeepRecord;

/**
 *
 * @author root
 */
@Named(value = "loginBean")
@RequestScoped
public class LoginBean {

    @Inject
    KeepRecord keepRecord;
@Inject
UserCDIbean userCDIbean;
    
    private String errorstatus;

    public String getErrorStatus() {
        return keepRecord.getErrorStatus();
    }

    public void setErrorStatus(String status) {

        this.errorstatus = status;
    }

    public LoginBean() {

        // errorstatus="";
    }
    
    public String doLogin() {

    // username and password KeepRecord me store hua hai
    String uname = keepRecord.getUsername();
    String pass = keepRecord.getPassword();

    // DB se user nikaalna
    Users u = userCDIbean.ubl.findUserByUsernamePassword(uname, pass);

    if (u == null) {
        keepRecord.setErrorStatus("Invalid Username or Password");
        return "Login.jsf";
    }
    // Set user ID in session
    FacesContext fc = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
    session.setAttribute("userId", u.getUserId());
    System.out.println("USER ID = " + u.getUserId());
    // ****** MOST IMPORTANT LINE ******
    userCDIbean.setLoginUserId(u.getUserId());

    return "Home.jsf?faces-redirect=true";
}

    
    
}
