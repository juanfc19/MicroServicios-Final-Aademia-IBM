package com.proyecto.sucursales.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.proyecto.sucursales.modelo.Sucursal;

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
    private List<Sucursal> data;
}
