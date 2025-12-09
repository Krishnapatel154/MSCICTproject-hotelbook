/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

/**
 *
 * @author Admin
 */
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "hotel/jndi",
        callerQuery = "SELECT password FROM users WHERE username = ?",
        groupsQuery = "SELECT g.group_name FROM group_master g JOIN users u ON g.group_id = u.group_id WHERE u.username = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class,
        priority = 30
)
public class Project {

}
