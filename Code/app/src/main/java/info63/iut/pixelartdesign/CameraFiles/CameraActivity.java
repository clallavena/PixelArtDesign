package info63.iut.pixelartdesign.CameraFiles;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import info63.iut.pixelartdesign.Accessors.FileAccessor;
import info63.iut.pixelartdesign.Accessors.IMediaFiles;
import info63.iut.pixelartdesign.R;

public class CameraActivity extends AppCompatActivity{
    public static final int MY_CAMERA_REQUEST_CODE = 100;
    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 110;
    public static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 120;
    public static final String ALBUM_NAME = "PixelArtsDesign_Photo";
    private Camera mCamera;
    private CameraPreview mPreview;
    private CameraManager cameraManager = new CameraManager();

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            IMediaFiles fi = new FileAccessor();
            File pictureFile = fi.getOutputMediaFile(FileAccessor.MEDIA_TYPE_IMAGE, ALBUM_NAME);


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
    protected void onCreate(final Bundle savedInstanceState) {

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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

            mCamera = cameraManager.getCameraInstance();
            Log.d("devNote", String.valueOf(Camera.getNumberOfCameras()));

            cameraManager.setCameraDisplayOrientation(this, 0, mCamera);

            // Créer notre Preview view et le paramètre comme notre content de notre activity.
            mPreview = new CameraPreview(this, mCamera);
            final FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(mPreview);
        }

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

                    mCamera = cameraManager.getCameraInstance();
                    Log.d("devNote", String.valueOf(Camera.getNumberOfCameras()));

                    cameraManager.setCameraDisplayOrientation(this, 0, mCamera);

                    // Create our Preview view and set it as the content of our activity.
                    mPreview = new CameraPreview(this, mCamera);
                    final FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
                    preview.addView(mPreview);

                } else {
                    Log.d("devNote", "onRequestPermissionsResult: permission refuse");
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

            case READ_EXTERNAL_STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("devNote", "Permission granted for READ");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCamera != null) mCamera.release();
    }
}
