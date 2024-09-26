package fiap.com.br.otakuhub.auth;

import fiap.com.br.otakuhub.domain.usuario.Usuario;
import fiap.com.br.otakuhub.domain.usuario.UsuarioRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final UsuarioRepository usuarioRepository;
    private final Algorithm algorithm;

    public TokenService(UsuarioRepository userRepository, @Value("${jwt.secret}") String secret) {
        this.usuarioRepository = userRepository;
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public Token createToken(Credentials credentials) {
        var expiresAt = LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.ofHours(-3));
        String token = JWT.create()
                .withSubject(credentials.email())
                .withIssuer("otakuhub")
                .withExpiresAt(expiresAt)
                .withClaim("role", "USER")
                .sign(algorithm);

        return new Token(token, credentials.email());
    }

    public Usuario getUserFromToken(String token) {
        var email = JWT.require(algorithm)
                .withIssuer("otakuhub")
                .build()
                .verify(token)
                .getSubject();

        return usuarioRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
