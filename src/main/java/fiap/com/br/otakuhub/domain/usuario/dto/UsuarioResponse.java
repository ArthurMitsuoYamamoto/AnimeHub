package fiap.com.br.otakuhub.domain.usuario.dto;

import fiap.com.br.otakuhub.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String username,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public UsuarioResponse(Usuario usuario) {
        this(usuario.getId(), usuario.getUsername(), usuario.getEmail(), usuario.getCreatedAt(), usuario.getUpdatedAt());
    }
}
