package fiap.com.br.otakuhub.exception;

public class CommentNotFoundException extends RuntimeException {
  public CommentNotFoundException(Long id) {
    super("Comment not found with id: " + id);
  }
}