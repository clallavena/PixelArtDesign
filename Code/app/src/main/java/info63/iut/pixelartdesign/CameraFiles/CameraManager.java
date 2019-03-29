package info63.iut.pixelartdesign.CameraFiles;

import android.app.Activity;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;

public class CameraManager implements ICamera {
    @Override
    public void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
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
        parameters.setRotation(result);
        camera.setDisplayOrientation(result);
        camera.setParameters(parameters);
    }

    @Override
    public Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e){
            Log.d("devNote", "Camera unreachable");
        }
        return c; // returns null if camera is unavailable
    }
}
