package fiap.com.br.otakuhub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //metodos:
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);


    List<Usuario> findByNameContainingIgnoreCase(String name);
}
