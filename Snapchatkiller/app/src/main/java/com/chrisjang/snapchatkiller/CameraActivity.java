package com.chrisjang.snapchatkiller;

/**
 * Created by Christopher Jang [c] on 3/28/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.ImageButton;
import android.os.Environment;
import android.net.Uri;
import android.widget.Button;
import android.widget.FrameLayout;

public class CameraActivity extends Activity {

    private Camera mainCamera;
    private CameraPreview mainPreview;

    //temp
    private boolean postCapture = false;
    //FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);

    public static Camera getCamera() {
        // the Camera object we will return
        Camera c = null;

        // attempt to open the camera using try-catch
        try {
            c = Camera.open(0);
        }
        catch (Exception e) {
            // Camera is not available for whatever reason
        }

        // return 'null' if camera is unavailable
        return c;
    }

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    // global byte array representation of image that is taken
    private byte[] imageBytes;

    /**
     * Our callback function that will not immediately handle the Byte[] data but will clone
     * the data into our global array so we can determine if we need to actually save the data
     * to our phone's storage
     */
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            Log.d("PicCallback", "PICTURE TAKEN!");

            //save the image data to the global array 'imageBytes'
            imageBytes = data.clone();
        }
    };

    /**
     * This helper function will take in binary data in the form of 'byte's and save it to the
     * phone's storage (should be DCIM)
     * @param data - The data we will be translating into an image and saving to storage
     */
    public void saveImage(byte[] data) {
        File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE); //only images for now

        // Check if pictureFile was successfully created
        if (pictureFile == null) {
            Log.d("saveImage", "Error creating media file, check storage permissions");
            return;
        }

        // try to save the image to storage
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
        }
        catch (FileNotFoundException e) {
            Log.d("saveImage", "File not found: " + e.getMessage());
        }
        catch (Exception e) {
            Log.d("saveImage", "Error accessing file: " + e.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // Create an instance of Camera
        mainCamera = getCamera();

        // Create our Preview view and set it as the content of our activity.
        mainPreview = new CameraPreview(this, mainCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mainPreview);

        // capture button
        final ImageButton captureButton = (ImageButton) findViewById(R.id.button_capture);

        // save button
        final ImageButton saveButton = (ImageButton)findViewById(R.id.button_save);

        // cancel button
        final ImageButton cancelButton = (ImageButton)findViewById(R.id.button_cancel);

        //event listener for capture button
        captureButton.setOnClickListener(
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get an image from the camera
                    if (!postCapture) {
                        //captureButton.setText("Save");
                        mainCamera.takePicture(null, null, mPicture);

                        postCapture = true;


                        // make save and cancel buttons visible
                        saveButton.setVisibility(View.VISIBLE);
                        cancelButton.setVisibility(View.VISIBLE);
                    }
                    else {

                        finish();
                    }
                }
            }
        );

        // event listener for save button
        saveButton.setOnClickListener(
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //just hide capture button for now
                    //captureButton.setVisibility(View.INVISIBLE); OK THIS WORKS!

                    // save the image
                    saveImage(imageBytes);

                    // TEMP return to splash
                    finish();
                }
            }
        );

        // event listener for cancel button
        cancelButton.setOnClickListener(
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TEMP Go back to splash for now
                    finish();
                }
            }
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera() {
        if(mainCamera != null){
            mainCamera.release();
            mainCamera = null;
        }
    }



}
