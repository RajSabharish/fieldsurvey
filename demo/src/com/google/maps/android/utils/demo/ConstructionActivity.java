package com.google.maps.android.utils.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonLineStringStyle;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class ConstructionActivity extends BaseDemoActivity {

    protected int getLayoutId() {
        return R.layout.activity_construction;
    }


    @Override
    protected void startDemo() {
        loadmap(true,true,true);
    }

    public void loadmap(boolean trenchlayer, boolean pitlayer, boolean eqplayer)
    {
        final CharSequence[] items = {"Trench","Pit","Equipment"};
        final ArrayList selectedItems=new ArrayList();

        LatLng initial_coordinate = new LatLng(-37.74499146, 144.79864884);
        CameraUpdate initial_location = CameraUpdateFactory.newLatLngZoom(initial_coordinate, 25);
        getMap().animateCamera(initial_location);
        try
        {
            final GeoJsonLayer layer_temp = new GeoJsonLayer(getMap(), R.raw.temp, this);
            GeoJsonLayer layer_eqp_all = new GeoJsonLayer(getMap(), R.raw.equipment_all, this);
            GeoJsonLayer layer_tls_pit = new GeoJsonLayer(getMap(), R.raw.pit, this);
            GeoJsonLayer layer_tls_trench = new GeoJsonLayer(getMap(), R.raw.tls_trench, this);

            if(eqplayer)
            {
                for (GeoJsonFeature feature : layer_eqp_all.getFeatures())
                {
                    layer_temp.addFeature(feature);
                }
            }
            if(pitlayer)
            {
                for (GeoJsonFeature feature : layer_tls_pit.getFeatures())
                {
                    layer_temp.addFeature(feature);
                }
            }
            if(trenchlayer)
            {
                for (GeoJsonFeature feature : layer_tls_trench.getFeatures())
                {
                    layer_temp.addFeature(feature);
                }
            }

            addGeoJsonLayerToMap(layer_temp);
            ImageButton layer_button = (ImageButton) findViewById(R.id.layer_button);

            layer_button.setOnClickListener(new View.OnClickListener()
            {
                AlertDialog dialog;

                @Override
                public void onClick(View view)
                {
                    selectedItems.clear();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ConstructionActivity.this);
                    builder.setTitle("Choose Layers");
                    builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked)
                        {
                            if (isChecked)
                            {
                                selectedItems.add(indexSelected);
                            }
                            else if (selectedItems.contains(indexSelected))
                            {
                                selectedItems.remove(Integer.valueOf(indexSelected));
                            }
                        }
                    })
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                boolean trench=false,pit=false,eqp=false;
                                @Override
                                public void onClick(DialogInterface dialog, int id)
                                {

                                    for(int i=0;i<selectedItems.size();i++)
                                    {
                                        if(selectedItems.get(i).equals(0))
                                        {
                                            trench=true;
                                        }
                                        else if(selectedItems.get(i).equals(1))
                                        {
                                            pit=true;
                                        }
                                        else if(selectedItems.get(i).equals(2))
                                        {
                                            eqp=true;
                                        }

                                    }
                                    layer_temp.removeLayerFromMap();
                                    loadmap(trench,pit,eqp);
                                }

                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int id){ }
                            });
                    dialog = builder.create();
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                    dialog.show();
                }
            });

        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


    private void addColorsToMarkers(GeoJsonLayer layer)
    {

        for (GeoJsonFeature feature : layer.getFeatures())
        {
            Bitmap pit_im = BitmapFactory.decodeResource(getResources(), R.drawable.pit);
            Bitmap ebr_im = BitmapFactory.decodeResource(getResources(), R.drawable.ebr);
            Bitmap fno_im = BitmapFactory.decodeResource(getResources(), R.drawable.fno);
            Bitmap mpt_im = BitmapFactory.decodeResource(getResources(), R.drawable.mpt);
            Bitmap cjl_im = BitmapFactory.decodeResource(getResources(), R.drawable.cjl);
            Bitmap djl_im = BitmapFactory.decodeResource(getResources(), R.drawable.djl);
            Bitmap odf_im = BitmapFactory.decodeResource(getResources(), R.drawable.fan);
            Bitmap fus_im = BitmapFactory.decodeResource(getResources(), R.drawable.fuse);
            GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
            if(feature.hasProperty("EQUIPMENT_ID"))
            {
                if(feature.getProperty("EQUIPMENT_ID").contains("FNO"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(fno_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("EBR"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(ebr_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("MPT"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(mpt_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("CJL"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(cjl_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("DJL"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(djl_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("ODF"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(odf_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }
                else if(feature.getProperty("EQUIPMENT_ID").contains("FUS"))
                {
                    pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(fus_im));
                    pointStyle.setTitle(feature.getProperty("TYPE"));
                    pointStyle.setSnippet(feature.getProperty("EQUIPMENT_ID"));
                }

            }
            else
                {
                pointStyle.setIcon(BitmapDescriptorFactory.fromBitmap(pit_im));
                pointStyle.setTitle("Pit");
                pointStyle.setSnippet(feature.getProperty("ID"));
                }

            feature.setPointStyle(pointStyle);
        }
    }

    private void addColorsToTrenches(GeoJsonLayer layer)
    {

        for (GeoJsonFeature feature : layer.getFeatures())
        {

            GeoJsonLineStringStyle lineStringStyle = new GeoJsonLineStringStyle();
            lineStringStyle.setColor(Color.GREEN);
            lineStringStyle.setClickable(true);
            feature.setLineStringStyle(lineStringStyle);

        }
    }

    private void addGeoJsonLayerToMap(final GeoJsonLayer layer)
    {
        addColorsToMarkers(layer);
        addColorsToTrenches(layer);
        layer.addLayerToMap();
    }
}
