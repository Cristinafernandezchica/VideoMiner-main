package aiss.videominer.controller;

import aiss.videominer.exception.CaptionNotFoundException;
import aiss.videominer.model.Caption;
import aiss.videominer.repository.CaptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer/captions")
public class CaptionController {

    @Autowired
    CaptionRepository repository;


    // GET http://localhost:8080/videominer/captions
    @GetMapping
    public List<Caption> findAll() throws CaptionNotFoundException {
        List<Caption> captions = repository.findAll();
        if(!captions.isEmpty()){
            throw new CaptionNotFoundException();
        }
        return captions;
    }

    // GET http://localhost:8080/videominer/captions/{id}
    @GetMapping("/{id}")
    public Caption findOne(@PathVariable String id) throws CaptionNotFoundException {
        Optional<Caption> caption = repository.findById(id);
        if(!caption.isPresent()){
            throw new CaptionNotFoundException();
        }
        return caption.get();
    }

}
