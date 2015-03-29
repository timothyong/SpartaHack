package com.chrisjang.snapchatkiller;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.content.Context;
import android.hardware.Camera;
import android.content.Intent;
import android.view.Window;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

// This will be the splash screen
public class TitleScreen extends ActionBarActivity {

    // the camera
    private Camera cam;

    /**
     * Check the hardware for a camera
     * We will use this function to detect if there is a usable camera among the available
     * hardware.
     * Returns true if yes, false otherwise
     */
    private boolean cameraDetected(Context context) {
        // use the package manager class to check
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        else {
            return false;
        }
    }



    /**
     * Access the camera (Assuming there is one to access)
     * We will assume that cameraDetected has already been called and returned true when
     * this function is called. So we will then use the camera2 API to access the camera
     * @return the Camera object
     */
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the actionBar for the Title Screen
        getSupportActionBar().hide();

        setContentView(R.layout.activity_title_screen);

        // get the 'button_Camera' button by id
        final ImageButton camera_button = (ImageButton)findViewById(R.id.button_Camera);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        camera_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (cameraDetected(getApplicationContext())) {
                            //camera_button.setText("CAMERA, YAY");
                            //cam = Camera.open();
                           cameraOnClick();


                        }
                        else {
                            //camera_button.setText("WTF NO CAMERA??");
                        }
                    }
                }
        );



        // release the camera
        if (cam != null) {
            cam.release();
            cam = null;
        }
    }

    //blah
    public void cameraOnClick() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_title_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
