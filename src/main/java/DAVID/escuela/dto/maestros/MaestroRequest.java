package DAVID.escuela.dto.maestros;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MaestroRequest(

        @NotBlank(message = "El nombre es requerido ")
        @Size(min = 1, max = 50, message = "el nombre debe tener entre 1 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El apelldio paterno es requerido ")
        @Size(min = 1, max = 50, message = "el apellido paterno debe tener entre 1 y 50 caracteres")
        String apellidoPaterno,

        @NotBlank(message = "El apellido materno es requerido ")
        @Size(min = 1, max = 50, message = "el apellido materno debe tener entre 1 y 50 caracteres")
        String apellidoMaterno,

        @NotBlank(message = "El email es requerido ")
        @Size(min = 8, max = 100, message = "el email debe tener entre 1 y 50 caracteres")
        @Email(message = "el email debe tener un formato valido (ejemplo@gmail.com")
        String email,

        @NotBlank(message = "El telefono es requerido ")
        @Pattern(regexp = "^[0-9]{10}", message = "eltelefono debe contener solo 18 digitos")
        String telefono
) {
}
