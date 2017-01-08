package com.cs496.macaron_together_admin;

import java.util.List;

/**
 * Created by q on 2017-01-06.
 */

public class AlarmData {
    private String type;
    private String content;

    public AlarmData() { }

    public AlarmData(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String t) {
        type = t;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String c) {
        content = c;
    }


}
