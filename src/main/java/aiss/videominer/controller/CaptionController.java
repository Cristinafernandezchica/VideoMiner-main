package aiss.videominer.controller;

import aiss.videominer.exception.CaptionNotFoundException;
import aiss.videominer.model.Caption;
import aiss.videominer.repository.CaptionRepository;
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

@Tag(name = "Caption", description = "Caption management API" )
@RestController
@RequestMapping("/videominer/captions")
public class CaptionController {

    @Autowired
    CaptionRepository repository;

    // GET http://localhost:8080/videominer/captions
    @Operation(
            summary = "List of captions",
            description = "Get all the captions",
            tags = {"captions", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Caption.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping
    public List<Caption> findAll() throws CaptionNotFoundException {
        List<Caption> captions = repository.findAll();
        if(!captions.isEmpty()){
            throw new CaptionNotFoundException();
        }
        return captions;
    }

    // GET http://localhost:8080/videominer/captions/{id}
    @Operation(
            summary = "Retrive a caption by id",
            description = "Get a caption by specifying its id",
            tags = {"caption", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Caption.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Caption findOne(@Parameter(description = "id of caption to be searched") @PathVariable(value = "id") String id) throws CaptionNotFoundException {
        Optional<Caption> caption = repository.findById(id);
        if(!caption.isPresent()){
            throw new CaptionNotFoundException();
        }
        return caption.get();
    }

}
