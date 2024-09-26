package fiap.com.br.otakuhub.domain.avaliacao.dto;

import fiap.com.br.otakuhub.domain.avaliacao.Avaliacao;
import fiap.com.br.otakuhub.domain.anime.Anime;
import fiap.com.br.otakuhub.domain.usuario.Usuario;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoFormRequest(

        @NotNull(message = "{avaliacao.rating.notNull}")
        @Min(value = 1, message = "{avaliacao.rating.min}")
        int rating,

        @NotBlank(message = "{avaliacao.comment.notBlank}")
        String comment,

        @NotNull(message = "{avaliacao.usuarioId.notNull}")
        Long usuarioId,

        @NotNull(message = "{avaliacao.animeId.notNull}")
        Long animeId
) {
    public Avaliacao toModel(Usuario usuario, Anime anime) {
        return Avaliacao.builder()
                .rating(rating)
                .comentario(comment)
                .usuario(usuario) // Associar o usuário
                .anime(anime) // Associar o anime
                .build();
    }

}
