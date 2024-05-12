package aiss.videominer.controller;

import aiss.videominer.exception.CommentNotFoundException;
import aiss.videominer.model.Comment;
import aiss.videominer.model.User;
import aiss.videominer.model.Video;
import aiss.videominer.repository.CommentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Comment", description = "Comment management API")
@RestController
@RequestMapping("/videominer/comments")
public class CommentController {
    
    @Autowired
    CommentRepository repository;

    // GET http://localhost:8080/videominer/comments
    @Operation(
            summary = "List of comments",
            description = "Get all comments",
            tags = {"comments", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")})
    })
    @GetMapping
    public List<Comment> findAll() {
        List<Comment> comments = repository.findAll();

        return comments;
    }

    // GET http://localhost:8080/videominer/comments/{id}
    @Operation(
            summary = "Retrive a comments by id",
            description = "Get a comments by specifying its id",
            tags = {"comment", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Comment findOne(@Parameter(description = "id of comment to be searched") @PathVariable String id) throws CommentNotFoundException {
        Optional<Comment> comment = repository.findById(id);
        if(!comment.isPresent()){
            throw new CommentNotFoundException();
        }
        return comment.get();
    }

    // Operaciones adicionales

    // GET http://localhost:8080/videominer/comments/{id}/user
    @Operation(
            summary = "Retrive a user by comment id",
            description = "Get a user by specifying comment id",
            tags = {"comment", "user", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/{id}/user")
    public User findUserByComment(@Parameter(description = "id of comment to get the user") @PathVariable(value = "id") String id) throws CommentNotFoundException {
        Optional<Comment> comment = repository.findById(id);
        if(!comment.isPresent()){
            throw new CommentNotFoundException();
        }
        User userComment= comment.get().getAuthor();
        return userComment;
    }

}
