package com.muesli.music.infrastructure.channel;

import com.muesli.music.domain.channel.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    @Query(value = "SELECT c FROM Channel c WHERE c.isPublic = 1")
    Optional<List<Channel>> findChannelBy();
}
