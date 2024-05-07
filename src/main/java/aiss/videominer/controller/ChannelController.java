package aiss.videominer.controller;

import aiss.videominer.model.Channel;
import aiss.videominer.model.Comment;
import aiss.videominer.model.Video;
import aiss.videominer.repository.ChannelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer/channels")
public class ChannelController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ChannelRepository repository;


    // POST http://localhost:8080/videominer/channels
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel create(@Valid @RequestBody Channel channel){

        Channel newChannel = repository.save(channel);

        return newChannel;

    }

    // GET http://localhost:8080/videominer/channels
    @GetMapping
    public List<Channel> findAll(){
        return repository.findAll();
    }

    // GET http://localhost:8080/videominer/channels/{id}
    @GetMapping("/{id}")
    public Channel findOne(@PathVariable String id){
        Optional<Channel> channel = repository.findById(id);
        return channel.get();
    }

    /*
    // Preguntar si en el constructor hay que incluir la lista de videos
    // POST http://localhost:8080/videominer/channels
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel createChannel(@Valid @RequestBody Channel channel){
        Channel channel1 = repository
                .save(new Channel(channel.getName(), channel.getDescription(), channel.getCreatedTime(), channel.getVideos()));
        return channel1;
    }
    */

    // Preguntar si el id hay que ponerlo a long!!!
    // PUT http://localhost:8080/videominer/channels/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody Channel updatedChannel, @PathVariable String id){
        Optional<Channel> channelData = repository.findById(id);

        Channel channel = channelData.get();
        channel.setName(updatedChannel.getName());
        channel.setDescription(updatedChannel.getDescription());
        channel.setCreatedTime(updatedChannel.getCreatedTime());
        channel.setVideos(updatedChannel.getVideos());

    }

    // DELETE http://localhost:8080/videominer/channels/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
    }


    // Operaciones adicionale

    // Obtener todos los videos de un canal dado su id
    // Añadir prueba en postman
    // GET http://loacalhost:8080/videominer/channels/{id}/videos
    @GetMapping("/channels/{id}/videos")
    public List<Video> findCommentsByVideoId(@PathVariable String id){
        return repository.findById(id).get().getVideos();
    }




}
