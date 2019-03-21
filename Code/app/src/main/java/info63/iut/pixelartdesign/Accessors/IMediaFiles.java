package info63.iut.pixelartdesign.Accessors;

import java.io.File;
import java.util.List;

/**
 * Interface contenant des méthodes d'accées à des fichiers de types Média.
 */
public interface IMediaFiles {
    File getOutputMediaFile(int type, String albumName);
    File getPublicAlbumStorageDir(String albumName);
    // A enlever
    List<String> chargementPathImages();
    void deleteFile(int position);
}
