package br.com.fiap.money_control_api.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.money_control_api.model.Category;

@RestController
@RequestMapping("/categories") // faz com que todo o controller aponte para o path categories
public class CategoryController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private List<Category> repository = new ArrayList<>();

	@GetMapping
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

	@PostMapping
	public ResponseEntity<Category> create(@RequestBody Category category){
		log.info("Cadastrando categoria " + category.getName());
		repository.add(category);
		return ResponseEntity.status(201).body(category);
	}

	@GetMapping("{id}") // o nome do path e da variavel devem ser o mesmo
	public Category get(@PathVariable Long id){ // precisamos receber o valor atraves do parametro
		log.info("buscando categoria " + id);

		return getCategory(id);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void destroy(@PathVariable Long id){
		log.info("Apagando categoria " + id);

		repository.remove(getCategory(id));
	}

	@PutMapping("{id}")
	public Category update(@PathVariable Long id, @RequestBody Category category){ //esse metodo ira receber o id e a categoria com dados atualizados que estarão no body
		log.info("Atualizando categoria " + id + " para " + category); //sysouts utilizados para saber se o metodo está sendo inicializado corretamente

		//tirar a categoria antiga e colocar a nova
		repository.remove(getCategory(id)); //agora que temos o metodo getCategory, por ele não ser opcional não é mais necessario o metogo get()
		category.setId(id);
		repository.add(category);

		return category; // nao é mais necessario o retorno do 200 pois não há mais a opção de tambem mostrar o retorno 404
	}

	private Category getCategory(Long id) {
		return repository.stream()
			.filter(c -> c.getId().equals(id))
			.findFirst()
			.orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND)
			);
			//exception - forma do metodo avisar que houve um erro
	}
}
