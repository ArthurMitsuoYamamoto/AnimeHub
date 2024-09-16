package fiap.com.br.otakuhub.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //metodos:
    Optional<Usuario> findByUsername(String username);
    Usuario findByEmail(String email);


}
