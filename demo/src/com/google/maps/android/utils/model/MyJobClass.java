package com.google.maps.android.utils.model;

/**
 * Created by varada.vamsi on 6/1/2017.
 */

public class MyJobClass {
    private final String Date;
    private final String Area;
    private final String Task;
    private final String Status;
    public MyJobClass(final String Date, final String Area, final String Task, final String Status) {
        this.Date = Date;
        this.Area = Area;
        this.Task = Task;
        this.Status = Status;
    }

    public String getArea() {
        return Area;
    }

    public String getTask() {
        return Task;
    }

    public String getStatus() {
        return Status;
    }

    public String getDate() {

        return Date;
    }
}
