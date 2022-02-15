package com.muesli.music.domain.like;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.AbstractEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.sql.Timestamp;
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
    @Column(name = "is_like")
    private int isLike;

    @Builder
    public Like(Long id, Long likeableId, Long userId, String likeableType) {
        if (likeableId == null) throw new InvalidParamException("Like.likeableId");
        if (userId == null) throw new InvalidParamException("Like.userId");
        if (StringUtils.isBlank(likeableType)) throw new InvalidParamException("Like.likeableType");

        this.id = id;
        this.likeableId = likeableId;
        this.userId = userId;
        this.likeableType = likeableType;
    }

    public void doLike() {
        this.isLike = 1;
    }

    public void doDisLike() {
        this.isLike = 0;
    }
}
