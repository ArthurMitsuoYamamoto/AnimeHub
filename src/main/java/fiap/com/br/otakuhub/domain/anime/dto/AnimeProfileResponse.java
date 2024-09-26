package fiap.com.br.otakuhub.domain.anime.dto;

import fiap.com.br.otakuhub.domain.anime.Anime;

public record AnimeProfileResponse(
        Long id,
        String title,
        String genre,
        int year,
        String imageUrl
) {
    public AnimeProfileResponse(Anime anime) {
        this(
                anime.getId(),
                anime.getTitle(),
                anime.getGenre(),
                anime.getYear(),
                anime.getImageUrl()
        );
    }
}
