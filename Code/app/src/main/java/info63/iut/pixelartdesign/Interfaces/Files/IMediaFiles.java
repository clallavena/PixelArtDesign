package info63.iut.pixelartdesign.Interfaces.Files;

import java.io.File;

/**
 * Interface contenant des méthodes d'accées à des fichiers de types Média.
 */
public interface IMediaFiles {
    File getOutputMediaFile(int type, String albumName);
    File getPublicAlbumStorageDir(String albumName);
}
