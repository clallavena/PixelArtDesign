package info63.iut.pixelartdesign.Accessors;

import android.util.Log;

import java.io.File;
import java.util.List;

import info63.iut.pixelartdesign.CameraFiles.CameraActivity;

public class FileModifier implements IModifier {
    private FileAccessor fi = new FileAccessor();
    private List<String> imageButtonList;

    @Override
    /**
     * Charge les chemins des images du fichier CameraActivity.ALBUM_NAME dans la liste de string imageButtonList;
     */
    public List<String> chargementPathImages() {
        File directoryImage = fi.getPublicAlbumStorageDir(CameraActivity.ALBUM_NAME);
        imageButtonList = fi.getImageButtonList();

        for (String s :
                directoryImage.list()) {
            if (imageButtonList.contains(directoryImage.getPath() + "/" + s)) continue;
            imageButtonList.add(directoryImage.getPath() + "/" + s);
        }
        Log.d("delete", "chargementPathImages: " + directoryImage.list());

        return imageButtonList;
    }

    @Override
    public void deleteFile(int position) {
        File directoryImage = fi.getPublicAlbumStorageDir(CameraActivity.ALBUM_NAME);

        String[] children = directoryImage.list();
        File imageToDelete = new File(directoryImage, children[position]);
        if (imageToDelete.exists()) imageToDelete.delete();
    }
}
