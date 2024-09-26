package fiap.com.br.otakuhub.domain.avaliacao.dto;

import fiap.com.br.otakuhub.domain.avaliacao.Avaliacao;

public record AvaliacaoResponse(
        Long id,
        int rating,
        String comment,
        Long usuarioId,
        Long animeId
) {
    public AvaliacaoResponse(Avaliacao avaliacao) {
        this(
                avaliacao.getId(),
                avaliacao.getRating(),
                avaliacao.getComentario(),
                avaliacao.getUsuario().getId(),
                avaliacao.getAnime().getId()
        );
    }
}
