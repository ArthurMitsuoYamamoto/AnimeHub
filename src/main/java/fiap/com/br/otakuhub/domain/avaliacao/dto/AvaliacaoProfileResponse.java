package fiap.com.br.otakuhub.domain.avaliacao.dto;

import fiap.com.br.otakuhub.domain.avaliacao.Avaliacao;

public record AvaliacaoProfileResponse(
        Long id,
        int rating,
        String comment,
        Long usuarioId,
        Long animeId
) {
    public AvaliacaoProfileResponse(Avaliacao avaliacao) {
        this(
                avaliacao.getId(),
                avaliacao.getRating(),
                avaliacao.getComentario(),
                avaliacao.getUsuario().getId(),
                avaliacao.getAnime().getId()
        );
    }
}
