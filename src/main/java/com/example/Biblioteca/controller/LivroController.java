package com.example.Biblioteca.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Biblioteca.model.Livro;
import com.example.Biblioteca.repository.LivroRepository;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin(origins = "http://localhost:8081")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;


    @PostMapping
    public ResponseEntity<Livro> criarLivro(@RequestBody Livro livro) {
        try {
            Livro novoLivro = livroRepository.save(new Livro(
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getIsbn(),
                    livro.getAnoPublicacao()
            ));
            return new ResponseEntity<>(novoLivro, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        try {
            List<Livro> livros = livroRepository.findAll();

            if (livros.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(livros, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarLivroPorId(@PathVariable("id") Long id) {
        Optional<Livro> livroData = livroRepository.findById(id);

        return livroData.map(livro -> new ResponseEntity<>(livro, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable("id") Long id, @RequestBody Livro livroAtualizado) {
        Optional<Livro> livroData = livroRepository.findById(id);

        if (livroData.isPresent()) {
            Livro livro = livroData.get();
            livro.setTitulo(livroAtualizado.getTitulo());
            livro.setAutor(livroAtualizado.getAutor());
            livro.setIsbn(livroAtualizado.getIsbn());
            livro.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
            return new ResponseEntity<>(livroRepository.save(livro), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletarLivro(@PathVariable("id") Long id) {
        try {
            livroRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}