package aiss.videominer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VideoMinerService {
    @Autowired
    RestTemplate restTemplate;
}
