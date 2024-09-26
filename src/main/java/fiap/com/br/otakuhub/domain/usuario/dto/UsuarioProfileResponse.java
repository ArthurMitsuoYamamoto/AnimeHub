package fiap.com.br.otakuhub.domain.usuario.dto;

import fiap.com.br.otakuhub.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record UsuarioProfileResponse(
        String username,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public UsuarioProfileResponse(Usuario usuario) {
        this(usuario.getUsername(), usuario.getEmail(), usuario.getCreatedAt(), usuario.getUpdatedAt());
    }
}
