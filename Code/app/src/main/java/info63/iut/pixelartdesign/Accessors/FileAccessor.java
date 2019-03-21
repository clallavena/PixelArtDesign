package info63.iut.pixelartdesign.Accessors;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info63.iut.pixelartdesign.CameraFiles.CameraActivity;

public class FileAccessor implements IMediaFiles {
    public static final int MEDIA_TYPE_IMAGE = 1;
    private List<String> imageButtonList = new ArrayList<>();

    /**
     * Créer le directory albumName s'il n'existe pas et vérifie que le type envoyé est bien du type Media
     * @param type Type envoyé
     * @param albumName Nom du fichier voulant être crée
     * @return
     */
    @Override
    public File getOutputMediaFile(int type, String albumName) {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
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

    @Override
    /**
     * Récupère le chemin absolu du Directory Pictures passé en paramètre
     * @param albumName Nom du fichier dont on cherche le chemin
     * @return Le chemin du directory passé en paramètre
     */
    public File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("devNote", "Directory not created");
        }
        return file;
    }

    @Override
    /**
     * Charge les chemins des images du fichier CameraActivity.ALBUM_NAME dans la liste de string imageButtonList;
     */
    public List<String> chargementPathImages(){
        File directoryImage = getPublicAlbumStorageDir(CameraActivity.ALBUM_NAME);

        for (String s :
                directoryImage.list()) {
            if (imageButtonList.contains(directoryImage.getPath() + "/" + s)) continue;
            imageButtonList.add(directoryImage.getPath() + "/" + s);
        }
        Log.d("delete", "chargementPathImages: " + directoryImage.list());

        return imageButtonList;
    }

    public void deleteFile(int pos){
        File directoryImage = getPublicAlbumStorageDir(CameraActivity.ALBUM_NAME);

        String[] children = directoryImage.list();
        File imageToDelete = new File(directoryImage, children[pos]);
        if (imageToDelete.exists()) imageToDelete.delete();
    }
}
