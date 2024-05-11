package aiss.videominer.controller;

import aiss.videominer.exception.ChannelNotFoundException;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    public List<Channel> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws ChannelNotFoundException{

        Pageable paging;

        if(order != null){
            if(order.startsWith("-"))
                paging = PageRequest.of(page, size, Sort.by(order.substring(1)).descending());
            else
                paging = PageRequest.of(page, size, Sort.by(order).ascending());
        }
        else paging = PageRequest.of(page,size);


        Page<Channel> pageChannels;

        if(name == null)
            pageChannels = repository.findAll(paging);
        else
            pageChannels = repository.findByName(name, paging);

        List<Channel> channels = pageChannels.getContent();
        if(channels.isEmpty()){
            throw new ChannelNotFoundException();
        }
        return channels;
    }

    // GET http://localhost:8080/videominer/channels/{id}
    @GetMapping("/{id}")
    public Channel findOne(@PathVariable String id) throws ChannelNotFoundException{
        Optional<Channel> channel = repository.findById(id);
        if(!channel.isPresent()){
            throw new ChannelNotFoundException();
        }
        return channel.get();
    }

    // PUT http://localhost:8080/videominer/channels/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody Channel updatedChannel, @PathVariable String id){
        Optional<Channel> channelData = repository.findById(id);

        if(channelData.isPresent()) {
            Channel channel = channelData.get();
            channel.setName(updatedChannel.getName());
            channel.setDescription(updatedChannel.getDescription());
            channel.setCreatedTime(updatedChannel.getCreatedTime());
            channel.setVideos(updatedChannel.getVideos());

            repository.save(channel);
        } else{
            repository.save(updatedChannel);
        }
    }

    // DELETE http://localhost:8080/videominer/channels/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) throws ChannelNotFoundException{
        if(repository.existsById(id)){
            repository.deleteById(id);
        } else {
            throw new ChannelNotFoundException();
        }
    }


    // Operaciones adicionales

    // Obtener todos los videos de un canal dado su id
    // GET http://loacalhost:8080/videominer/channels/{id}/videos
    @GetMapping("/{id}/videos")
    public List<Video> getAllVideosByChannelId(@PathVariable String id) throws ChannelNotFoundException{
        Optional<Channel> channel = repository.findById(id);
        if(!channel.isPresent()){
            throw new ChannelNotFoundException();
        }
        return repository.findById(id).get().getVideos();
    }

}
