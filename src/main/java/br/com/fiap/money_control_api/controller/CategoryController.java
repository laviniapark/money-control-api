package br.com.fiap.money_control_api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.money_control_api.model.Category;

@RestController
public class CategoryController {

	private List<Category> repository = new ArrayList<>();

	@GetMapping("/categories") // o padrao 200 - OK será utilizado no GET
	public List<Category> index() {
		return repository;
	}

	// @PostMapping("/categories")
	// @ResponseStatus(code = HttpStatus.CREATED) // irá retornar o codigo 201 - CREATED
	// public Category create(@RequestBody Category category){
	// 	System.out.println("Cadastrando categoria " + category.getName());
	// 	repository.add(category);
	// 	return category;
	// }

	@PostMapping("/categories")
	public ResponseEntity<Category> create(@RequestBody Category category){
		System.out.println("Cadastrando categoria " + category.getName());
		repository.add(category);
		return ResponseEntity.status(201).body(category);
	}

	@GetMapping("/categories/{id}") // o nome do path e da variavel devem ser o mesmo
	public ResponseEntity<Category> get(@PathVariable Long id){ // precisamos receber o valor atraves do parametro
		System.out.println("buscando categoria " + id);
		// programacao funcional do Java para buscar categoria
		var category = repository.stream()
			.filter(c -> c.getId().equals(id)) // o filtro foi colocado para nos trazer apenas a info desejada (a regra ficará entre parenteses)
			.findFirst();
		
		//verifica se existe algo 
		if(category.isEmpty()){
			return ResponseEntity.notFound().build();
		}

			return ResponseEntity.ok().body(category.get()); // jamais chame o get sem garantir que o valor esteja presente
	}
}
