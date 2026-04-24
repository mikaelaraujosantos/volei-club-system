package com.svc.volei_club_system.dto;

public class LoginResponseDTO {
    
    private String token;
    private String role;
    
    // Construtor vazio (necessário para alguns frameworks)
    public LoginResponseDTO() {
    }
    
    // Construtor com parâmetros
    public LoginResponseDTO(String token, String role) {
        this.token = token;
        this.role = role;
    }
    
    // Getters e Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
}