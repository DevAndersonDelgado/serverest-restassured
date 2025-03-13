package com.vemser.rest.data.factory;

import com.vemser.rest.request.usuario.UsuarioRequest;
import org.apache.commons.lang3.StringUtils;

public class UsuariosDataFactory extends BaseDataFactory {


    public static UsuarioRequest usuarioValido() {
        return novoUsuario();
    }

    public static UsuarioRequest usuarioAdministrador() {
        UsuarioRequest usuarioRequest = novoUsuario();
        usuarioRequest.setAdministrador("true");
        return usuarioRequest;
    }

    public static UsuarioRequest usuarioComNomeEmBranco() {
        UsuarioRequest usuario = novoUsuario();
        usuario.setNome(StringUtils.EMPTY);

        return usuario;
    }

    public static UsuarioRequest usuarioComEmailEmBranco() {
        UsuarioRequest usuario = novoUsuario();
        usuario.setEmail(StringUtils.EMPTY);

        return usuario;
    }

    public static UsuarioRequest usuarioComPasswordEmBranco() {
        UsuarioRequest usuario = novoUsuario();
        usuario.setPassword(StringUtils.EMPTY);

        return usuario;
    }

    public static UsuarioRequest usuarioComIsAdminEmBranco() {
        UsuarioRequest usuario = novoUsuario();
        usuario.setAdministrador(StringUtils.EMPTY);

        return usuario;
    }

    public static UsuarioRequest usuarioComDadosEmBranco() {

        UsuarioRequest usuario = novoUsuario();
        usuario.setNome(StringUtils.EMPTY);
        usuario.setEmail(StringUtils.EMPTY);
        usuario.setPassword(StringUtils.EMPTY);
        usuario.setAdministrador(StringUtils.EMPTY);

        return usuario;
    }

    private static UsuarioRequest novoUsuario() {

        UsuarioRequest usuario = new UsuarioRequest();
        usuario.setNome(faker.name().firstName() + " " + faker.name().lastName());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setPassword(faker.internet().password());
        usuario.setAdministrador(String.valueOf(gerarBoleanoAleatorio()));

        return usuario;
    }


}
