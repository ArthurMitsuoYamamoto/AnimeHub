package fiap.com.br.otakuhub.domain.usuario;

import fiap.com.br.otakuhub.mail.MailService;
import fiap.com.br.otakuhub.domain.usuario.dto.UsuarioProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Criar um novo usuário
    public Usuario createUsuario(Usuario usuario) {
        // Codifica a senha antes de salvar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario = usuarioRepository.save(usuario);
        mailService.sendWelcomeEmail(usuario); // Envia e-mail de boas-vindas
        return usuario;
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
            throw new RuntimeException("Usuário com ID " + id + " não encontrado.");
        }
    }

    // Atualizar um usuário existente
    public Usuario updateUsuario(Long id, Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            usuario.setId(id); // Define o ID para garantir a atualização correta
            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuário com ID " + id + " não encontrado.");
        }
    }

    // Obter perfil do usuário
    public UsuarioProfileResponse getProfile(String email) {
        return usuarioRepository.findByEmail(email)
                .map(UsuarioProfileResponse::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    // Buscar usuários por nome
    public List<Usuario> findByName(String name) {
        return usuarioRepository.findByNameContainingIgnoreCase(name);
    }
}
