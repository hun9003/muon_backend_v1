package com.muesli.music.domain.like;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.AbstractEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "likes")
public class Like extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "likeable_id")
    private Long likeableId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "likeable_type")
    private String likeableType;
    private ZonedDateTime created_at;

    @Builder
    public Like(Long id, Long likeableId, Long userId, String likeableType) {
        if (id == null) throw new InvalidParamException("Like.id");
        if (likeableId == null) throw new InvalidParamException("Like.likeable_id");
        if (userId == null) throw new InvalidParamException("Like.user_id");
        if (StringUtils.isBlank(likeableType)) throw new InvalidParamException("Like.likeable_type");

        this.id = id;
        this.likeableId = likeableId;
        this.userId = userId;
        this.likeableType = likeableType;
    }
}
