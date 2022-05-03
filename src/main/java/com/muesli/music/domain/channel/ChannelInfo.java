package com.muesli.music.domain.channel;

import lombok.Getter;
import lombok.ToString;

public class ChannelInfo {

    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final String name;
        private final String slug;
        private final String contentType;
        private final String autoUpdate;
        private final String layout;
        private final String hideTitle;
        private final Long userId;
        private final String seoTitle;
        private final String seoDescription;
        private final String displayName;

        public Main(Channel channel) {
            this.id = channel.getId();
            this.name = channel.getName();
            this.slug = channel.getSlug();
            this.contentType = channel.getContentType();
            this.autoUpdate = channel.getAutoUpdate();
            this.layout = channel.getLayout();
            this.hideTitle = channel.getHideTitle();
            this.userId = channel.getUserId();
            this.seoTitle = channel.getSeoTitle();
            this.seoDescription = channel.getSeoDescription();
            this.displayName = channel.getDisplayName();
        }

    }
}
