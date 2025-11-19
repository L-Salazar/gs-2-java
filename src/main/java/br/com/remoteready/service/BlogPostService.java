package br.com.remoteready.service;

import br.com.remoteready.model.BlogPost;
import br.com.remoteready.repository.BlogPostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Transactional
    public BlogPost salvar(BlogPost blogPost) {
        return blogPostRepository.save(blogPost);
    }

    @Transactional
    public BlogPost atualizar(Long id, BlogPost dados) {
        BlogPost existente = buscarPorId(id);
        existente.setTitulo(dados.getTitulo());
        existente.setDescricao(dados.getDescricao());
        existente.setImageUrl(dados.getImageUrl());
        existente.setTag(dados.getTag());
        existente.setDataCriacao(dados.getDataCriacao());
        return blogPostRepository.save(existente);
    }

    public List<BlogPost> listar() {
        return blogPostRepository.findAll();
    }

    public BlogPost buscarPorId(Long id) {
        return blogPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BlogPost não encontrado: " + id));
    }

    @Transactional
    public void excluir(Long id) {
        if (!blogPostRepository.existsById(id)) {
            throw new IllegalArgumentException("BlogPost não encontrado: " + id);
        }
        blogPostRepository.deleteById(id);
    }
}