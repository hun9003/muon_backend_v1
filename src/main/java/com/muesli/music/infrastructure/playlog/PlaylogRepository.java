package com.muesli.music.infrastructure.playlog;

import com.muesli.music.domain.playlog.Playlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylogRepository extends JpaRepository<Playlog, Long> {
}
