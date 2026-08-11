package com.david.almacen.dto;

public record CustomErrorResponse(
        int codigo,
        String mensaje
) {
}
