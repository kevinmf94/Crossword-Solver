package cat.uab.crossword.exception;

import java.io.File;
import java.io.IOException;

public class CrosswordFileException extends IOException {

    public CrosswordFileException(File file) {
        super("Error al cargar el fichero "+file.getName());
    }
}
