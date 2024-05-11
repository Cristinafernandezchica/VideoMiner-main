package aiss.videominer.repository;

import aiss.videominer.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, String> {

    Page<Channel> findByName(String name, Pageable pageable);

}
