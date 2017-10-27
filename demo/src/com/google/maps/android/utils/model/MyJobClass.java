package com.google.maps.android.utils.model;

/**
 * Created by varada.vamsi on 10/27/2017.
 */

public class MyJobClass {
    private final String Date;
    private final String Area;
    private final String Task;
    private final String Status;
    private final String EquipmentType;
    public MyJobClass(final String Date, final String Area, final String Task, final String Status,final String EquipmentType) {
        this.Date = Date;
        this.Area = Area;
        this.Task = Task;
        this.Status = Status;
        this.EquipmentType = EquipmentType;
    }

    public String getArea() {
        return Area;
    }

    public String getEquipmentType() {
        return EquipmentType;
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
