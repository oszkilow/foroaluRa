package com.foro.alura.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.foro.alura.dto.usuario.UsuarioResponse;
import com.foro.alura.modelo.Usuario;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Service
@Slf4j
public class ValidaServiceImpl implements ValidaService {

    @Autowired
    private Gson gson;
    @Override
    public String generaToken(Usuario usuario) {
        return Jwts
                .builder()
                .claim("id", usuario.getId())
                .claim("nombre", usuario.getNombre())
                .claim("email", usuario.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (50000*1000)))
                .signWith(SignatureAlgorithm.HS512, "#AbcCP2023#!#".getBytes()).compact();
    }

    @Override
    public UsuarioResponse decodeJWTToken(String token) {
            UsuarioResponse userGenericResponse = new UsuarioResponse();
            DecodedJWT jwt;
            String[] chunks = token.split("\\.");
            try {
                jwt = JWT.decode(token);
                String tokenWithoutSignature = chunks[0] + "." + chunks[1];
                String signature = chunks[2];
                SignatureAlgorithm sa = SignatureAlgorithm.HS512;
                SecretKeySpec secretKeySpec = new SecretKeySpec("#AbcCP2023#!#".getBytes(), sa.getJcaName());
                DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec);
                if (!validator.isValid(tokenWithoutSignature, signature)) {
                    log.info("El token a sido modificado");
                    return null;
                }
                if (jwt.getExpiresAt().before(Calendar.getInstance().getTime())) {
                    log.info("Token vencido");
                    return null;
                }
            } catch (Exception e){
                log.error("Error al validar token {}", e.getMessage());
                return null;
            }
            return userGenericResponse;
    }

}
