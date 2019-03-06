package info63.iut.pixelartdesign.CameraFiles;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import info63.iut.pixelartdesign.R;

import static info63.iut.pixelartdesign.Fragments.AddFragment.getCameraInstance;

public class CameraActivity extends AppCompatActivity {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private final int orientationDegree = 90;
    private Camera mCamera;
    private CameraPreview mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
         }
        mCamera = getCameraInstance();
        Log.d("devNote", String.valueOf(Camera.getNumberOfCameras()));

        mCamera.setDisplayOrientation(orientationDegree);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("devNote", "onPause camera is called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCamera.release();
        Log.d("devNote", "onDestroy camera called!");
    }

}
