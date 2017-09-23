package cat.uab.crossword.exception;

import java.io.File;
import java.io.IOException;

public class DictionaryFileException extends IOException {

    public DictionaryFileException(File file) {
        super("Error al cargar el fichero "+file.getName());
    }
}
