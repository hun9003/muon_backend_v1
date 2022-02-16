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
    @Column(name = "text")
    private String text;

    @OneToOne
    @JoinColumn(name = "track_id")
    private Track track;

    public Lyrics(Long id, String text, Track track) {
        this.id = id;
        this.text = text;
        this.track = track;
    }
}
