package aiss.videominer.controller;

import aiss.videominer.exception.ChannelNotFoundException;
import aiss.videominer.exception.VideoNotFoundException;
import aiss.videominer.model.Caption;
import aiss.videominer.model.Comment;
import aiss.videominer.model.Video;
import aiss.videominer.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer/videos")
public class VideoController {

    @Autowired
    VideoRepository repository;
    
    // GET http://localhost:8080/videominer/videos
    @GetMapping
    public List<Video> findAll(){
        return repository.findAll();
    }

    // GET http://localhost:8080/videominer/videos/{id}
    @GetMapping("/{id}")
    public Video findOne(@PathVariable String id) throws VideoNotFoundException {
        Optional<Video> video = repository.findById(id);
        if(!video.isPresent()){
            throw new VideoNotFoundException();
        }
        return video.get();
    }

    // Añadir prueba en postman
    // GET http://loacalhost:8080/videominer/videos/{id}/captions
    @GetMapping("/{id}/captions")
    public List<Caption> getAllCaptionsByVideoId(@PathVariable(value="id") String id) throws VideoNotFoundException {
        Optional<Video> video = repository.findById(id);
        if(!video.isPresent()){
            throw new VideoNotFoundException();
        }
        List<Caption> captions = new ArrayList<>(video.get().getCaptions());
        return captions;
    }

    // Añadir prueba en postman
    // GET http://loacalhost:8080/videominer/videos/{id}/comments
    @GetMapping("/{id}/comments")
    public List<Comment> getAllCommentsByVideoId(@PathVariable(value="id") String id) throws VideoNotFoundException {
        Optional<Video> video = repository.findById(id);
        if(!video.isPresent()){
            throw new VideoNotFoundException();
        }
        List<Comment> comments = new ArrayList<>(video.get().getComments());
        return comments;
    }

}
