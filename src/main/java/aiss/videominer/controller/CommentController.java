package aiss.videominer.controller;

import aiss.videominer.exception.CommentNotFoundException;
import aiss.videominer.model.Comment;
import aiss.videominer.model.User;
import aiss.videominer.model.Video;
import aiss.videominer.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer/comments")
public class CommentController {
    
    @Autowired
    CommentRepository repository;

    // GET http://localhost:8080/videominer/comments
    @GetMapping
    public List<Comment> findAll() throws CommentNotFoundException {
        List<Comment> comments = repository.findAll();
        if(comments.isEmpty()){
            throw new CommentNotFoundException();
        }
        return comments;
    }

    // GET http://localhost:8080/videominer/comments/{id}
    @GetMapping("/{id}")
    public Comment findOne(@PathVariable String id) throws CommentNotFoundException {
        Optional<Comment> comment = repository.findById(id);
        if(!comment.isPresent()){
            throw new CommentNotFoundException();
        }
        return comment.get();
    }

    // Operaciones adicionales

    // Obtener el usuario de un comentario dado su id
    // GET http://localhost:8080/videominer/comments/{id}/user
    @GetMapping("/{id}/user")
    public User findUserByComment(@PathVariable String id) throws CommentNotFoundException {
        Optional<Comment> comment = repository.findById(id);
        if(!comment.isPresent()){
            throw new CommentNotFoundException();
        }
        User userComment= comment.get().getAuthor();
        return userComment;
    }

}
