package fiap.com.br.otakuhub.exception;

public class AnimeNotFoundException extends RuntimeException {
    public AnimeNotFoundException(Long id) {
        super("Anime not found with id: " + id);
    }
}