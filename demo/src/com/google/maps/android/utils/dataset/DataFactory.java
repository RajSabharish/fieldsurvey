package com.google.maps.android.utils.dataset;

import com.google.maps.android.utils.model.MyJobClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by varada.vamsi on 6/1/2017.
 */

public final class DataFactory {
    private static List<MyJobClass> None;
    public static List<MyJobClass> createPresentJobList() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String currdate=dateFormat.format(date);
        final MyJobClass audiA1 = new MyJobClass(currdate, "000000015020084510", "3KGP-02","Inactive","PIT");
        final MyJobClass audiA3 = new MyJobClass(currdate, "000000000216799133", "3SAP-08","Active","PIT");
        final MyJobClass audiA4 = new MyJobClass(currdate, "000000000216799133", "3KGP-09","Inactive","PIT");
        final MyJobClass audiA5 = new MyJobClass(currdate, "000000000216799133", "3KGP-04","Inactive","PIT");
        final MyJobClass audiA6 = new MyJobClass(currdate, "000000000201755256", "3KGP-06","Inactive","PIT");
        final MyJobClass audiA7 = new MyJobClass(currdate, "000000000201755256", "3KGP-05","Inactive","PIT");
        final MyJobClass audiA8 = new MyJobClass(currdate, "000000000201755256", "3SAP-07","Inactive","PIT");
        final MyJobClass audiA11 = new MyJobClass("2017/05/01", "000000015020057361", "3KGP-02","Inactive","DUCT");
        final MyJobClass audiA13 = new MyJobClass("2017/04/01", "000000000201755256", "3SAP-08","Active","DUCT");
        final MyJobClass audiA14 = new MyJobClass("2017/03/01", "000000000201755256", "3KGP-09","Inactive","DUCT");
        final MyJobClass audiA15 = new MyJobClass("2017/02/01", "000000000201755256", "3KGP-04","Inactive","DUCT");
        final MyJobClass audiA16 = new MyJobClass("2017/01/01", "000000000201755256", "3KGP-06","Inactive","DUCT");
        final MyJobClass audiA17 = new MyJobClass("2016/12/01", "000000000201755256", "3KGP-05","Inactive","DUCT");
        final MyJobClass audiA18 = new MyJobClass("2016/11/01", "000000000201755256", "3SAP-07","Inactive","DUCT");
        final List<MyJobClass> PresentJobs = new ArrayList<>();
        PresentJobs.add(audiA3);
        PresentJobs.add(audiA1);
        PresentJobs.add(audiA8);
        PresentJobs.add(audiA4);
        PresentJobs.add(audiA6);
        PresentJobs.add(audiA7);
        PresentJobs.add(audiA5);
        PresentJobs.add(audiA13);
        PresentJobs.add(audiA11);
        PresentJobs.add(audiA18);
        PresentJobs.add(audiA14);
        PresentJobs.add(audiA16);
        PresentJobs.add(audiA17);
        PresentJobs.add(audiA15);
        return PresentJobs;

    }

}
