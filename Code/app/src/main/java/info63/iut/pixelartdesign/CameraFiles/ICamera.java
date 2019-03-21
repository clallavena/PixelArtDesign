package info63.iut.pixelartdesign.CameraFiles;

import android.app.Activity;
import android.hardware.Camera;

/**
 * Interface contenant des méthodes de manipulation de type Caméra.
 */
public interface ICamera {
    void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera);
    Camera getCameraInstance();
}
