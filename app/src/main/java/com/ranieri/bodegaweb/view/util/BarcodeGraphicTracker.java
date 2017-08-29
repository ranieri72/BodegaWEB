package com.ranieri.bodegaweb.view.util;

import android.content.Context;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by ranie on 28 de ago.
 */

public class BarcodeGraphicTracker extends Tracker<Barcode> {

    private Context context;

    public BarcodeGraphicTracker(Context context) {
        this.context = context;
    }

    /**
     * Start tracking the detected item instance within the item overlay.
     */
    @Override
    public void onNewItem(int id, Barcode item) {
        if (context instanceof NewBarcodeListener) {
            NewBarcodeListener listener = (NewBarcodeListener) context;
            listener.newBarcode(item);
        }
    }

    /**
     * Update the position/characteristics of the item within the overlay.
     */
    @Override
    public void onUpdate(Detector.Detections<Barcode> detectionResults, Barcode item) {
    }

    /**
     * Hide the graphic when the corresponding object was not detected.  This can happen for
     * intermediate frames temporarily, for example if the object was momentarily blocked from
     * view.
     */
    @Override
    public void onMissing(Detector.Detections<Barcode> detectionResults) {
    }

    /**
     * Called when the item is assumed to be gone for good. Remove the graphic annotation from
     * the overlay.
     */
    @Override
    public void onDone() {
    }

    public interface NewBarcodeListener {
        void newBarcode(Barcode item);
    }
}