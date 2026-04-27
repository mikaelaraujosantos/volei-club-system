package com.svc.volei_club_system.dto;

public class LoginResponseDTO {
    private String token;
    private String role;
    private Long atletaId;

    // Construtor padrão (obrigatório para o Spring)
    public LoginResponseDTO() {
    }

    // Construtor com 2 parâmetros (para compatibilidade)
    public LoginResponseDTO(String token, String role) {
        this.token = token;
        this.role = role;
        this.atletaId = null;
    }

    // Construtor com 3 parâmetros (o que você está usando)
    public LoginResponseDTO(String token, String role, Long atletaId) {
        this.token = token;
        this.role = role;
        this.atletaId = atletaId;
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

    public Long getAtletaId() {
        return atletaId;
    }

    public void setAtletaId(Long atletaId) {
        this.atletaId = atletaId;
    }
}