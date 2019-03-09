package info63.iut.pixelartdesign.CameraFiles;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import info63.iut.pixelartdesign.R;

import static android.view.OrientationEventListener.ORIENTATION_UNKNOWN;

public class CameraActivity extends AppCompatActivity {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 110;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private final int ORIENTATION_DEGREE = 90;
    private Camera mCamera;
    private CameraPreview mPreview;

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "PixelArtsDesign_Photo");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("devNote", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }


    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                Log.d("devNote", "check storage permission, error creating media file");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                mCamera.startPreview();
            } catch (FileNotFoundException e) {
                Log.d("devNote", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("devNote", "Error accessing file: " + e.getMessage());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }

        mCamera = getCameraInstance();
        Log.d("devNote", String.valueOf(Camera.getNumberOfCameras()));

        setCameraDisplayOrientation(this, 0, mCamera);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        final FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        // Listener button_capture
        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        mCamera.takePicture(null, null, mPicture);
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.toast_picture), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, Camera camera) {
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        Camera.Parameters parameters = camera.getParameters();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        Log.d("devNoteCamera", String.valueOf(result));
        //onOrientationChanged(result, parameters);
        parameters.setRotation(result);
        camera.setDisplayOrientation(result);
        camera.setParameters(parameters);
    }

    public void onOrientationChanged(int orientation, Camera.Parameters parameters) {
        if (orientation == ORIENTATION_UNKNOWN) return;
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(0, info);
        orientation = (orientation + 45) / 90 * 90;
        int rotation = 0;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            rotation = (info.orientation - orientation + 360) % 360;
        } else {  // back-facing camera
            rotation = (info.orientation + orientation) % 360;
        }
        Log.d("devNoteCamera", "rotation onOrientationChanged: " + String.valueOf(rotation-90));
        parameters.setRotation(rotation);
    }

    /**
     * Gère les résultas de permissions, cache la vue camera si les permissions ne sont pas acceptés, fait fonctionné l'application normalement sinon
     * @param requestCode corresspond au code de la permission demandé
     * @param permissions
     * @param grantResults contient les resultats des permissions sous formes de tableaux
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case MY_CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("devNote", "Permission granted for camera");
                    findViewById(R.id.camera_preview).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView_permissionError).setVisibility(View.GONE);
                    findViewById(R.id.button_capture).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.camera_preview).setVisibility(View.GONE);
                    findViewById(R.id.textView_permissionError).setVisibility(View.VISIBLE);
                    findViewById(R.id.button_capture).setVisibility(View.GONE);
                }
                return;
            }

            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("devNote", "Permission granted for Storage");
                    findViewById(R.id.camera_preview).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView_permissionError).setVisibility(View.GONE);
                    findViewById(R.id.button_capture).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.camera_preview).setVisibility(View.GONE);
                    findViewById(R.id.textView_permissionError).setVisibility(View.VISIBLE);
                    findViewById(R.id.button_capture).setVisibility(View.GONE);
                }
                return;
            }
        }
    }

    // TODO: Faire une action pour la caméra frontale, id=1, faire une vérif du nombre de caméra disponible sur l'appareil.
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e){
            Log.d("devNote", "Camera unreachable");
        }
        return c; // returns null if camera is unavailable
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
