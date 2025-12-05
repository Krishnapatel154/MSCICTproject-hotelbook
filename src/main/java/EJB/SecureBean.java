/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package EJB;

import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;

/**
 *
 * @author Admin
 */
@DeclareRoles({"Admin", "User"})
@Stateless
@LocalBean
public class SecureBean {
    
@RolesAllowed("Admin") 
public String saySecureHello()
{
    return "Secure Hello from Secure Bean";
}
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
