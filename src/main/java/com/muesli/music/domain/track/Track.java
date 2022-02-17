package com.muesli.music.domain.track;

import com.google.common.collect.Lists;
import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.like.Like;
import com.muesli.music.domain.track.lyrics.Lyrics;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "tracks")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
    @Column(name = "name")
    private String name;
    @Column(name = "original")
    private String original;
    @Column(name = "number")
    private Long number;
    @Column(name = "duration")
    private Long duration;
    @Column(name = "artists_legacy")
    private String artistsLegacy;
    @Column(name = "url")
    private String url;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "composer")
    private String composer;
    @Column(name = "lyricser")
    private String lyricser;
    @Column(name = "arranger")
    private String arranger;
    @Column(name = "adult")
    private Long adult;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "track", cascade = CascadeType.PERSIST)
    private List<Like> likeList = Lists.newArrayList();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "track", cascade = CascadeType.PERSIST)
    private Lyrics lyrics;

    public Track(Long id, String name, String original, Long number, Long duration, String artistsLegacy, String url, String description, String image, String composer, String lyricser, String arranger, Long adult) {
        if (id == null) throw new InvalidParamException("Tracks.id");

        this.id = id;
        this.name = name;
        this.original = original;
        this.number = number;
        this.duration = duration;
        this.artistsLegacy = artistsLegacy;
        this.url = url;
        this.description = description;
        this.image = image;
        this.composer = composer;
        this.lyricser = lyricser;
        this.arranger = arranger;
        this.adult = adult;
    }

    public Track(Long id) {
        if (id == null) throw new InvalidParamException("Tracks.id");

        this.id = id;
    }
}
