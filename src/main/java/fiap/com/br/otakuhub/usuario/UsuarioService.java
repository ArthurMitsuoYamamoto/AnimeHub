package fiap.com.br.otakuhub.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Criar ou atualizar um usuário
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Encontrar um usuário por ID
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    // Encontrar um usuário por nome de usuário
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    // Listar todos os usuários
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    // Deletar um usuário por ID
    public void deleteUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            // Lançar uma exceção ou retornar um erro, se necessário
            throw new RuntimeException("Usuário com ID " + id + " não encontrado.");
        }
    }

    // Atualizar um usuário
    public Usuario updateUsuario(Long id, Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            usuario.setId(id);
            return usuarioRepository.save(usuario);
        } else {
            // Lançar uma exceção ou retornar um erro, se necessário
            throw new RuntimeException("Usuário com ID " + id + " não encontrado.");
        }
    }
}
