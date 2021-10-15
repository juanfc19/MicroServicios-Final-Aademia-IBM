package com.proyecto.perfiles.tarjetas.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.proyecto.perfiles.tarjetas.models.Perfil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Response {
    private HttpStatus status;
    private String message;
    private LocalDateTime dateTime;
    private Perfil data;
}
