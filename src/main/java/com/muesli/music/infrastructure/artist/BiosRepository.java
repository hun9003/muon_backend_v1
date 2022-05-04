package com.muesli.music.infrastructure.artist;

import com.muesli.music.domain.artist.bios.Bios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BiosRepository extends JpaRepository<Bios, Long> {

    Optional<Bios> findBiosByArtistId(Long artistId);
}
