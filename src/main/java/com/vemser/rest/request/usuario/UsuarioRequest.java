package com.vemser.rest.request.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

    private String nome;
    private String email;
    private String password;
    private String administrador;
}
