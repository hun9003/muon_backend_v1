package com.muesli.music.domain.track.lyrics;

import com.muesli.music.domain.track.Track;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "lyrics")
public class Lyrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timeline")
    private String timaline;

    @Column(name = "text")
    private String text;

    @Column(name = "text_original")
    private String textOriginal;

    @Column(name = "text_pron")
    private String textPron;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id")
    private Track track;

    public Lyrics(Long id, String timaline, String text, String textOriginal, String textPron, Track track) {
        this.id = id;
        this.timaline = timaline;
        this.text = text;
        this.textOriginal = textOriginal;
        this.textPron = textPron;
        this.track = track;
    }
}
