package br.com.criandoapi.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.criandoapi.projeto.model.Usuario;

@Repository
public interface IUsuario extends JpaRepository<Usuario, Integer> {

	public Usuario findByNomeOrEmail(String nome, String email);

	
}