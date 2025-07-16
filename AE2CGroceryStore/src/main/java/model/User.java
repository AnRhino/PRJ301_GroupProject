/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Dinh Cong Phuc - CE190770
 */
public class User {

    private int id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private int roleId;

    public User() {
    }

    public User(String username, String password, int roleId) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }
    
    public User(String username, String password, int roleId, String fullName, String email) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.fullName = fullName;
        this.email = email;
    }

    public User(int id, String username, String password, String fullName, String email, int roleId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.roleId = roleId;
    }

    public User(int id, String userName) {
        this.id = id;
        this.username = userName;
    }

    public User(int id, String userName, String fullName, String email, int roleId) {
        this.id = id;
        this.username = userName;
        this.fullName = fullName;
        this.email = email;
        this.roleId = roleId;
    }
    
     public User(int id, String userName, String fullName, String email, int roleId, String password) {
        this.id = id;
        this.username = userName;
        this.fullName = fullName;
        this.email = email;
        this.roleId = roleId;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}
