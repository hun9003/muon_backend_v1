package com.muesli.music.domain.artist.bios;

import com.muesli.music.domain.artist.Artist;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "artist_bios")
public class Bios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public Bios(Long id, String content, Artist artist) {
        this.id = id;
        this.content = content;
        this.artist = artist;
    }
}
