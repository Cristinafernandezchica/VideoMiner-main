package aiss.videominer.repository;

import aiss.videominer.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoMinerRepository extends JpaRepository<Channel, String> {


}
