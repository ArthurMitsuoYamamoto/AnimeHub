package fiap.com.br.otakuhub.exception;

public class AnimeNotFoundException extends RuntimeException {
    public AnimeNotFoundException(Long id) {
        super("Apartment not found with id: " + id);
    }
}