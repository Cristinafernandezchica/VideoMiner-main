package aiss.videominer.controller;

import aiss.videominer.exception.VideoNotFoundException;
import aiss.videominer.model.Caption;
import aiss.videominer.model.Comment;
import aiss.videominer.model.Video;
import aiss.videominer.repository.VideoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name= "Video", description="Video management API" )
@RestController
@RequestMapping("/videominer/videos")
public class VideoController {

    @Autowired
    VideoRepository repository;
    
    // GET http://localhost:8080/videominer/videos
    @Operation(
            summary="List of videos",
            description = "Get all videos",
            tags = {"videos", "get"})

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Video.class), mediaType = "application/json")}),
    })
    @GetMapping
    public List<Video> findAll() {
        List<Video> videos = repository.findAll();

        return videos;
    }

    // GET http://localhost:8080/videominer/videos/{id}
    @Operation(
            summary="Retrieve a video by Id",
            description = "Get a video by specifying its id",
            tags = {"videos", "get"})

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Video.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema)})
    })
    @GetMapping("/{id}")
    public Video findOne(@Parameter(description = "id of video to be searched") @PathVariable String id) throws VideoNotFoundException {
        Optional<Video> video = repository.findById(id);
        if(!video.isPresent()){
            throw new VideoNotFoundException();
        }
        return video.get();
    }


    // Operaciones adicionales

    // GET http://loacalhost:8080/videominer/videos/{id}/captions
    @Operation(
            summary = "List of captions on a video",
            description = "Get all captions on a video by specifying video id",
            tags = {"video", "captions", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Video.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/{id}/captions")
    public List<Caption> getAllCaptionsByVideoId(@Parameter(description = "id of video to get the captions") @PathVariable String id) throws VideoNotFoundException {
        Optional<Video> video = repository.findById(id);
        if(!video.isPresent()){
            throw new VideoNotFoundException();
        }
        List<Caption> captions = new ArrayList<>(video.get().getCaptions());
        return captions;
    }

    // GET http://loacalhost:8080/videominer/videos/{id}/comments
    @Operation(
            summary = "List of comments on a video",
            description = "Get all comments on a video by specifying video id",
            tags = {"video", "comments", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Video.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/{id}/comments")
    public List<Comment> getAllCommentsByVideoId(@Parameter(description = "id of video to get the comments")@PathVariable String id) throws VideoNotFoundException {
        Optional<Video> video = repository.findById(id);
        if(!video.isPresent()){
            throw new VideoNotFoundException();
        }
        List<Comment> comments = new ArrayList<>(video.get().getComments());
        return comments;
    }

}
