package DAVID.escuela.dto.maestros;

public record MestroaRequest(
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String email,
        String Telefono
) {
}
