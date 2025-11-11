package com.sena.springecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.springecommerce.model.Usuario;
import com.sena.springecommerce.service.IUsuarioService;

@RestController
@RequestMapping("/apiusuario")
public class APIUsuarioController {

	@Autowired
	private IUsuarioService usuarioService;

	// Endpoint GET para obtener todos los usuarios
	@GetMapping("/list")
	public List<Usuario> getAllUsers() {
		return usuarioService.findAll();
	}

	// Endpoint GET para obtener un usuario por id
	@GetMapping("/user/{id}")
	public ResponseEntity<Usuario> getUsersById(@PathVariable Integer id) {
		Optional<Usuario> usuario = usuarioService.get(id);
		return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// Endpoint POST para crear un nuevo usuario
	@PostMapping("/create")
	public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario) {
		Usuario savedUsuario = usuarioService.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
	}

	// Endpoint PUT para actualizar un usuario
	@PutMapping("/update/{id}")
	public ResponseEntity<Usuario> updateUser(@PathVariable Integer id, @RequestBody Usuario userDetails) {
		Optional<Usuario> usuario = usuarioService.get(id);
		if (!usuario.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Usuario existingUser = usuario.get();
		existingUser.setDireccion(userDetails.getDireccion());
		existingUser.setEmail(userDetails.getEmail());
		existingUser.setNombre(userDetails.getNombre());
		existingUser.setPassword(userDetails.getPassword());
		existingUser.setRol(userDetails.getRol());
		existingUser.setTelefono(userDetails.getTelefono());
		usuarioService.update(existingUser);
		return ResponseEntity.ok(existingUser);
	}

	// Endpoint DELETE para eliminar un producto
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		Optional<Usuario> usuario = usuarioService.get(id);
		if (!usuario.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		usuarioService.delete(id);
		return ResponseEntity.ok().build();
	}

}
