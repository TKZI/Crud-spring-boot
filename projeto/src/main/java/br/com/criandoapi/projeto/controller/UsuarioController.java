package br.com.criandoapi.projeto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.criandoapi.projeto.dto.UsuarioDto;
import br.com.criandoapi.projeto.model.Usuario;
import br.com.criandoapi.projeto.security.Token;
import br.com.criandoapi.projeto.service.CadastroUsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController{
	
	private CadastroUsuarioService usuarioService;
	
	
	
	public UsuarioController(CadastroUsuarioService cadastroService) {
		this.usuarioService = cadastroService;
	}
	
	@GetMapping
	public List<Usuario> listar(){
		
		return  usuarioService.listar();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Usuario> salvar(@Valid @RequestBody Usuario usuario) {
		Usuario novoUsuario = usuarioService.salvar(usuario);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> alterar(@PathVariable Integer id, @Valid @RequestBody Usuario usuario) {
		Usuario novo = usuarioService.atualizar(usuario, id);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novo);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		usuarioService.deletar(id);
		
	}
	
	@PostMapping("/senha")
	public ResponseEntity<Token> logar(@Valid @RequestBody UsuarioDto usuarioDto){
		Token token = usuarioService.gerarToken(usuarioDto);
		
		if(token != null) {
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(403).build();
	}
	

	

}
