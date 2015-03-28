package com.chrisjang.snapchatkiller;

import android.hardware.Camera;
import android.nfc.Tag;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Christopher Jang on 3/28/15.
 * This class will be used to create the custom camera interface for our app
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    // the SurfaceHolder which holds the actual layout
    private SurfaceHolder mainHolder;

    // the actual Camera we will be using to take pictures
    private Camera mainCamera;

    // the Camera Preview constructor
    public CameraPreview(Context context, Camera camera) {
        // use the parent's constructor for 'context'
        super(context);

        // get the camera
        mainCamera = camera;

        // install the new Callback function so we know when the Surface is created/destroyed
        mainHolder = getHolder();
        mainHolder.addCallback(this);

        //deprecated -- for android before 3.0
        //mainHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    // Create the new surfaceHolder
    public void surfaceCreated(SurfaceHolder holder) {
        // Surface has been created, tell Camera where to draw picture
        try {
            mainCamera.setPreviewDisplay(holder);
            mainCamera.startPreview();
        }
        catch (Exception e) {
            Log.d("Testing", "Error setting camera preview");
        }
    }

    // Surface destroyer
    public void surfaceDestroyed(SurfaceHolder holder) {
        /**
         * We will leave this empty as we handle destroying surfaces elsewhere
         */
    }

    // Surface changer
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        /**
         * TODO: Rotate or resize camera (See below)
         */

        // if there is no preview surface
        if (holder.getSurface() == null) {
            return;
        }

        // Stop the preview before making changes
        try {
            mainCamera.stopPreview();
        }
        catch (Exception e) {
            // Won't do anything here because it means there was no preview to stop
        }

        //TODO: This is where we make changes (rotate, resize, etc)
        //Make sure sizes are approved -- DO NOT USE ARBITRARY VALUES

        // Start up the preview with the new settings
        try {
            mainCamera.setPreviewDisplay(mainHolder);
            mainCamera.startPreview();
        }
        catch (Exception e) {
            //print the error
            Log.d("Testing", "Error starting camera preview" + e.getMessage());
        }
    }



}
