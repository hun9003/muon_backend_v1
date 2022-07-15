package com.muesli.music.domain.playlog;

import com.muesli.music.domain.AbstractEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "play_log")
public class Playlog extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "track_id")
    private Long trackId;

    @Column(name = "album_id")
    private Long albumId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "useragent")
    private String useragent;

    @Column(name = "ip")
    private String ip;

    @Builder
    public Playlog(Long trackId, Long albumId, Long userId, String useragent, String ip) {
        this.trackId = trackId;
        this.albumId = albumId;
        this.userId = userId;
        this.useragent = useragent;
        this.ip = ip;
    }
}
