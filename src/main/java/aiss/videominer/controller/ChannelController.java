package aiss.videominer.controller;

import aiss.videominer.exception.ChannelNotFoundException;
import aiss.videominer.model.Channel;
import aiss.videominer.model.Video;
import aiss.videominer.repository.ChannelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Channel", description = "Channel management API")
@RestController
@RequestMapping("/videominer/channels")
public class ChannelController {

    @Autowired
    ChannelRepository repository;


    // GET http://localhost:8080/videominer/channels
    @Operation(
            summary = "List of channels",
            description = "Get all channels",
            tags = {"channels", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Channel.class), mediaType = "application/json")})
    })
    @GetMapping
    public List<Channel> findAll(
            @Parameter(description = "name of the channels to be retrived") @RequestParam(required = false) String name,
            @Parameter(description = "parameter to order channels retrieved") @RequestParam(required = false) String order,
            @Parameter(description = "number of page to be retrived") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "size of page to be retrieved") @RequestParam(defaultValue = "10") int size
    ){

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

        return channels;
    }



    // GET http://localhost:8080/videominer/channels/{id}
    @Operation(
            summary = "Retrive a channel by id",
            description = "Get a channel by specifying its id",
            tags = {"channel", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Channel.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Channel findOne(@Parameter(description = "id of channel to be searched") @PathVariable String id) throws ChannelNotFoundException{
        Optional<Channel> channel = repository.findById(id);
        if(!channel.isPresent()){
            throw new ChannelNotFoundException();
        }
        return channel.get();
    }

    // POST http://localhost:8080/videominer/channels
    @Operation(
            summary = "Create a channel",
            description = "Create a new channel",
            tags = {"channel", "post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Channel.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel create(@Valid @RequestBody Channel channel){
        Channel newChannel = repository.save(channel);

        return newChannel;
    }

    // PUT http://localhost:8080/videominer/channels/{id}
    @Operation(
            summary = "Update a channel",
            description = "Update a channel (or create it if it doesn't exist)",
            tags = {"channel" , "put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema())})
    })
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
    @Operation(
            summary = "Delete a channel",
            description = "Delete a channel by specifying its id",
            tags = {"channel", "delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@Parameter(description = "id of channel to be deleted") @PathVariable String id) throws ChannelNotFoundException{
        if(repository.existsById(id)){
            repository.deleteById(id);
        } else {
            throw new ChannelNotFoundException();
        }
    }


    // Operaciones adicionales

    // GET http://loacalhost:8080/videominer/channels/{id}/videos
    @Operation(
            summary = "List of videos on a channel ",
            description = "Get all videos on a channel by specifying channel id",
            tags = {"channel", "videos", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Channel.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/{id}/videos")
    public List<Video> getAllVideosByChannelId(@Parameter(description = "id of the channel from which we are looking for the videos") @PathVariable String id) throws ChannelNotFoundException{
        Optional<Channel> channel = repository.findById(id);
        if(!channel.isPresent()){
            throw new ChannelNotFoundException();
        }
        return repository.findById(id).get().getVideos();
    }

}
