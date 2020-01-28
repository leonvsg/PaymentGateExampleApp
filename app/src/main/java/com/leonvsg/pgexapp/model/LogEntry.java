package com.leonvsg.pgexapp.model;

import android.icu.text.SimpleDateFormat;

import java.util.Date;

public class LogEntry {

    private Date date;
    private String header;
    private String text;

    public LogEntry(Date date, String header, String text) {
        this.date = date;
        this.header = header;
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public String getHeader() {
        return header;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "date=" + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S Z").format(date) +
                ", header='" + header + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
