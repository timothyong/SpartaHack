package com.chrisjang.snapchatkiller;

import android.hardware.Camera;
import android.nfc.Tag;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
import android.util.Log;
import android.util.Size;
import java.util.List;

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
            Log.d("Testing", "Error setting camera preview" + e.getMessage());
        }
    }

    // Surface destroyer
    public void surfaceDestroyed(SurfaceHolder holder) {
        /**
         * We will leave this empty as we handle destroying surfaces elsewhere
         */
    }

    /**
     * This helper function will help determine the best resolution dimensions for our CameraPreview
     * @param sizes - The list of sizes to query to determine the optimal size
     * @param w     - the width to compare to
     * @param h     - the height to compare to
     * @return      - the Camera.Size object that is optimal for the given parameters
     */
    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.05;
        double targetRatio = (double) w/h;

        if (sizes==null) return null;

        Camera.Size optimalSize = null;

        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Find size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    // Surface changer
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        /**
         * TODO: Rotate; currently only landscape orientation available
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

        // Get the camera's parameters so we can determine the optimal width/height
        Camera.Parameters parameters = mainCamera.getParameters();

        // Get a list of the approved sizes, NEVER USE ARBITRARY VALUES
        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();

        // Call our helper function to determine the right size for us (fullscreen)
        Camera.Size optimalSize = getOptimalPreviewSize(sizes, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);

        // set the optimal size dimensions to our CameraPreview
        parameters.setPreviewSize(optimalSize.width, optimalSize.height);
        mainCamera.setParameters(parameters);

        // Start up the preview with the new settings
        try {
            mainCamera.setPreviewDisplay(mainHolder);
            mainCamera.startPreview();
        }
        catch (Exception e) {
            //print the error
            Log.d("SurfaceChanged", "Error starting camera preview" + e.getMessage());
        }
    }

}
