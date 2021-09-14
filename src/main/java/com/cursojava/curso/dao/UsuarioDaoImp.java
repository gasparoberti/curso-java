package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> getUsuarios() {
        String query = "FROM Usuario";

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void deleteUsuario(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }


    @Override
    public void postUsuario(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public boolean verificarEmailPassword(Usuario usuario) {
        String query = "FROM Usuario where email = :email";

        List<Usuario> usuarioList = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if (usuarioList.isEmpty())
            return false;

        String passwordHashed = usuarioList.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        return argon2.verify(passwordHashed, usuario.getPassword());
    }

}
