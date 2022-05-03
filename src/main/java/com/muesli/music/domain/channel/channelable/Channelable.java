package com.muesli.music.domain.channel.channelable;

import com.muesli.music.domain.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "channelables")
public class Channelable extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "channel_id")
    private Long channelId;

    @Column(name = "channelable_id")
    private Long channelableId;

    @Column(name = "channelable_type")
    private String channelableType;

    @Column(name = "order")
    private Long order;

}
