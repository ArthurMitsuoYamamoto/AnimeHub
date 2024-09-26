package fiap.com.br.otakuhub.domain.usuario.dto;

import fiap.com.br.otakuhub.domain.usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UsuarioFormRequest(
        @NotBlank(message = "{usuario.username.notBlank}")
        String username,

        @NotBlank(message = "{usuario.email.notBlank}")
        @Email(message = "{usuario.email.invalid}")
        String email,

        @NotBlank(message = "{usuario.password.notBlank}")
        @Size(min = 6, message = "{usuario.password.size}")
        String password
) {
    public Usuario toModel() {
        return Usuario.builder()
                .username(username)
                .email(email)
                .password(password)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
