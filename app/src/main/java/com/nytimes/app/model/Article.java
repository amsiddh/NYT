package com.nytimes.app.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sbingi on 3/31/2019.
 */
public class Article implements Parcelable {

    private String url;
    private String section;
    private String byline;
    private String type;
    private String title;
    @SerializedName("abstract")
    private String synopsis;
    @SerializedName("published_date")
    private String publishedDate;
    private String source;
    private Integer views;
    private List<Media> media;
    private String uri;

    public String getUrl() {
        return url;
    }

    public String getSection() {
        return section;
    }

    public String getByline() {
        return byline;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getSource() {
        return source;
    }

    public Integer getViews() {
        return views;
    }

    public List<Media> getMedia() {
        return media;
    }

    public String getUri() {
        return uri;
    }

    public static class Media implements Parcelable {
        private String caption;
        private String copyright;
        @SerializedName("media-metadata")
        private List<MediaMetadata> mediaMetadata;

        public String getCaption() {
            return caption;
        }

        public String getCopyright() {
            return copyright;
        }

        public List<MediaMetadata> getMediaMetadata() {
            return mediaMetadata;
        }

        protected Media(Parcel in) {
            caption = in.readString();
            copyright = in.readString();
            mediaMetadata = in.createTypedArrayList(MediaMetadata.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(caption);
            dest.writeString(copyright);
            dest.writeTypedList(mediaMetadata);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Media> CREATOR = new Creator<Media>() {
            @Override
            public Media createFromParcel(Parcel in) {
                return new Media(in);
            }

            @Override
            public Media[] newArray(int size) {
                return new Media[size];
            }
        };
    }

    public static class MediaMetadata implements Parcelable {
        private String url;
        private String format;
        private Integer height;
        private Integer width;

        public String getUrl() {
            return url;
        }

        public String getFormat() {
            return format;
        }

        public Integer getHeight() {
            return height;
        }

        public Integer getWidth() {
            return width;
        }

        protected MediaMetadata(Parcel in) {
            url = in.readString();
            format = in.readString();
            if (in.readByte() == 0) {
                height = null;
            } else {
                height = in.readInt();
            }
            if (in.readByte() == 0) {
                width = null;
            } else {
                width = in.readInt();
            }
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(url);
            dest.writeString(format);
            if (height == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(height);
            }
            if (width == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(width);
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<MediaMetadata> CREATOR = new Creator<MediaMetadata>() {
            @Override
            public MediaMetadata createFromParcel(Parcel in) {
                return new MediaMetadata(in);
            }

            @Override
            public MediaMetadata[] newArray(int size) {
                return new MediaMetadata[size];
            }
        };
    }

    protected Article(Parcel in) {
        url = in.readString();
        section = in.readString();
        byline = in.readString();
        type = in.readString();
        title = in.readString();
        synopsis = in.readString();
        publishedDate = in.readString();
        source = in.readString();
        if (in.readByte() == 0) {
            views = null;
        } else {
            views = in.readInt();
        }
        media = in.createTypedArrayList(Media.CREATOR);
        uri = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(section);
        dest.writeString(byline);
        dest.writeString(type);
        dest.writeString(title);
        dest.writeString(synopsis);
        dest.writeString(publishedDate);
        dest.writeString(source);
        if (views == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(views);
        }
        dest.writeTypedList(media);
        dest.writeString(uri);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public boolean isEmptyPublishedDate() {
        return TextUtils.isEmpty(publishedDate);
    }


    public String getSmallImageUrl() {
        if (media != null && !media.isEmpty()
                && media.get(0).mediaMetadata != null
                && !media.get(0).mediaMetadata.isEmpty()
                && !TextUtils.isEmpty(media.get(0).mediaMetadata.get(0).url)) {
            return media.get(0).mediaMetadata.get(0).url;
        }
        return "";
    }

    public String getLargeImageUrl() {
        if (media != null && !media.isEmpty()
                && media.get(0).mediaMetadata != null
                && media.get(0).mediaMetadata.size() >= 3
                && !TextUtils.isEmpty(media.get(0).mediaMetadata.get(2).url)) {
            return media.get(0).mediaMetadata.get(2).url;
        }
        return "";
    }
}
