package com.bit.httpd.common;

//服务器支持的文本类型
public enum SupportMimeType {

    HTML("html", "text/html"),
    HTM("htm", "text/htm"),
    CSS("css", "text/css"),
    JS("js", "text/javascript"),
    GIF("gif", "image/gif"),
    JPEG("jpeg", "image/jpeg"),
    JPG("jpg", "image/jpeg"),
    PNG("png", "image/png"),
    MP3("mp3", "audio/mpeg"),
    ICO("ico", "image/ico"),
    MP4("mp4", "video/mp4");

    /**
     * 扩展名
     */
    private String extend;

    /**
     * 内容类型
     */
    private String mimeType;

    SupportMimeType(String extend, String mimeType) {
        this.extend = extend;
        this.mimeType = mimeType;
    }

    public static SupportMimeType lookup(String extend) {
        for (SupportMimeType supportedMimeType : SupportMimeType.values()) {
            if (supportedMimeType.getExtend().equalsIgnoreCase(extend)) {
                return supportedMimeType;
            }
        }
        return null;
    }

    public String getExtend() {
        return extend;
    }

    public String getMimeType() {
        return mimeType;
    }
}

