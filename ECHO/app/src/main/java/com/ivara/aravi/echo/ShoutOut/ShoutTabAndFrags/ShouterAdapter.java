package com.ivara.aravi.echo.ShoutOut.ShoutTabAndFrags;

/**
 * Created by Aravi on 28-08-2017.
 */

public class ShouterAdapter {

    private String EVENT_MESSAGE;
    private String EVENT_SENDER;
    private String EVENT_TITLE;
    private String PHOTO_URL;

    public ShouterAdapter() {

    }

    public ShouterAdapter(String EVENT_MESSAGE, String EVENT_SENDER, String EVENT_TITLE, String PHOTO_URL) {
        this.EVENT_MESSAGE = EVENT_MESSAGE;
        this.EVENT_SENDER = EVENT_SENDER;
        this.EVENT_TITLE = EVENT_TITLE;
        this.PHOTO_URL = PHOTO_URL;
    }

    public String getEVENT_MESSAGE() {
        return EVENT_MESSAGE;
    }

    public void setEVENT_MESSAGE(String EVENT_MESSAGE) {
        this.EVENT_MESSAGE = EVENT_MESSAGE;
    }

    public String getEVENT_SENDER() {
        return EVENT_SENDER;
    }

    public void setEVENT_SENDER(String EVENT_SENDER) {
        this.EVENT_SENDER = EVENT_SENDER;
    }

    public String getEVENT_TITLE() {
        return EVENT_TITLE;
    }

    public void setEVENT_TITLE(String EVENT_TITLE) {
        this.EVENT_TITLE = EVENT_TITLE;
    }

    public String getPHOTO_URL() {
        return PHOTO_URL;
    }

    public void setPHOTO_URL(String PHOTO_URL) {
        this.PHOTO_URL = PHOTO_URL;
    }
}
