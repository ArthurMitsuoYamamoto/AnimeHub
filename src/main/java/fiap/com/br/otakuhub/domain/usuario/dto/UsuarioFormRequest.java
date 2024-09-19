package fiap.com.br.otakuhub.domain.usuario.dto;

import fiap.com.br.otakuhub.domain.usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


import java.time.LocalDateTime;

public record UsuarioFormRequest(
        @NotBlank(message = "Nome do usuário é obrigatório")
        String username,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ser válido")
        String email,

        @NotBlank(message = "Password é obrigatório")
        @Size(min = 6, message = "Password deve ter pelo menos 6 caracteres")
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
