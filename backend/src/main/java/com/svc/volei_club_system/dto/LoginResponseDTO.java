package com.svc.volei_club_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private String token;

    private String role;

    private Long atletaId;

    private Boolean perfilCompleto;

}