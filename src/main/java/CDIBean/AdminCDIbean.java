/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBean;

import EJB.AdminBeanLocal;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

/**
 *
 * @author Admin
 */
@Named(value = "adminCDIbean")
@RequestScoped
public class AdminCDIbean {

     @EJB
    AdminBeanLocal abl;
      private String username;
    private String email;
    private String password;
    private String phone;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public AdminCDIbean() {
    }

    // Email validation
private static boolean isValidEmail(String email) {
     if (email == null || email.trim().isEmpty())
            return false;
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    return email.matches(emailRegex);
}
     // Register user with DEFAULT group_id = 2
    public String registerUser() {

        if (!isValidEmail(email)) {
            System.out.println("Invalid email format");
            return null;
        }

        try {
            // group_id always = 2 (user)
            abl.addUser(2, username, email, password, phone);

            return "Login.jsf?faces-redirect=true";

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
