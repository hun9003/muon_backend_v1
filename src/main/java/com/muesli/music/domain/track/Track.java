package com.muesli.music.domain.track;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.like.Like;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @OneToOne
    @JoinColumn(name = "id")
    private Like like;

    private String name;
    private String original;
    private Long number;
    private Long duration;
    private String artists_legacy;
    private String url;
    private String description;
    private String image;
    private String composer;
    private String lyricser;
    private String arranger;
    private Long adult;

    public Track(Long id, String name, String original, Long number, Long duration, String artists_legacy, String url, String description, String image, String composer, String lyricser, String arranger, Long adult) {
        if (id == null) throw new InvalidParamException("Tracks.id");

        this.id = id;
        this.name = name;
        this.original = original;
        this.number = number;
        this.duration = duration;
        this.artists_legacy = artists_legacy;
        this.url = url;
        this.description = description;
        this.image = image;
        this.composer = composer;
        this.lyricser = lyricser;
        this.arranger = arranger;
        this.adult = adult;
    }
}
