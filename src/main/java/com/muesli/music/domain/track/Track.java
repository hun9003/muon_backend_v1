package com.muesli.music.domain.track;

import com.google.common.collect.Sets;
import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.like.Like;
import com.muesli.music.domain.track.artist.TrackArtist;
import com.muesli.music.domain.track.lyrics.Lyrics;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "tracks")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "original")
    private String original;
    @Column(name = "number")
    private Long number;
    @Column(name = "duration")
    private Long duration;
    @Column(name = "url")
    private String url;
    @Column(name = "description")
    private String description;
    @Column(name = "composer")
    private String composer;
    @Column(name = "lyricser")
    private String lyricser;
    @Column(name = "arranger")
    private String arranger;
    @Column(name = "adult")
    private Long adult;

    @Column(name = "downloadable")
    private Long downloadable;
    @Column(name = "flacable")
    private Long flacable;
    @Column(name = "streaming_free")
    private Long streamingFree;
    @Column(name = "download_free")
    private Long downloadFree;
    @Column(name = "is_title")
    private Long isTitle;


    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @Where(clause = "likeable_type LIKE '%Track'")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "track", cascade = CascadeType.PERSIST)
    private Set<Like> likeList = Sets.newHashSet();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "track", cascade = CascadeType.PERSIST)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Lyrics> lyrics = Sets.newHashSet();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "track", cascade = CascadeType.PERSIST)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<TrackArtist> trackArtists = Sets.newHashSet();


    public Track(Long id, String name, String original, Long number, Long duration, String url, String description, String composer, String lyricser, String arranger, Long adult) {
        if (id == null) throw new InvalidParamException("Tracks.id");

        this.id = id;
        this.name = name;
        this.original = original;
        this.number = number;
        this.duration = duration;
        this.url = url;
        this.description = description;
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
