package aiss.videominer.controller;

import aiss.videominer.model.Comment;
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
    public List<Comment> findAll(){
        return repository.findAll();
    }

    // GET http://localhost:8080/videominer/videos/{id}
    @GetMapping("/{id}")
    public Comment findOne(@PathVariable String id){
        Optional<Comment> comment = repository.findById(id);
        return comment.get();
    }
    
}
