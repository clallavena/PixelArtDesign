package info63.iut.pixelartdesign.Accessors;

import java.util.List;

/**
 * Interface contenant des méthodes de modification d'un fichier
 */
public interface IModifier {
    List<String> chargementPathImages();
    void deleteFile(int position);
}
