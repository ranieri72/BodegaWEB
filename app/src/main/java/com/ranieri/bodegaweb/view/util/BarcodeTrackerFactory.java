package com.ranieri.bodegaweb.view.util;

import android.content.Context;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by ranie on 28 de ago.
 */

public class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {

    private Context context;

    public BarcodeTrackerFactory(Context context) {
        this.context = context;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        return new BarcodeGraphicTracker(context);
    }
}
