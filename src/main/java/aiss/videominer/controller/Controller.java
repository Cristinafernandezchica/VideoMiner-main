package aiss.videominer.controller;

import aiss.videominer.model.Channel;
import aiss.videominer.repository.VideoMinerRepository;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/videominer/channels")
public class Controller {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    VideoMinerRepository repository;


    List<Channel> lChannel = new ArrayList<>();
    @PostMapping
    public Channel create(@Valid @RequestBody Channel channel){
        lChannel.clear();
        lChannel.add(channel);

        Channel newChannel = repository.save(channel);



        return newChannel;

    }

}
