package cat.institutmarianao.mediamanager.persistence.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import cat.institutmarianao.mediamanager.model.Movie;
import cat.institutmarianao.mediamanager.persistence.ExerciseNotImplementedException;

/**
 * Student exercise for importing films_sax.xml using SAX.
 * File access must use traditional Java I/O. This class does not manage a Catalog.
 */
public class SaxMovieImporter {
    /**
     * Creates a stateless importer without opening any files.
     */

    public SaxMovieImporter() {
    }

    /**
     * Imports the source using SAX, as required by Task 1.
     *
     * <p>Return a non-null list of newly constructed domain objects in document
     * order, with internal codes left at zero. Preserve all required descriptive
     * information and any complete series, season and episode hierarchy.
     * A valid empty source produces an empty list. Malformed or invalid data
     * must be reported, not silently discarded.
     *
     * <p>Use traditional Java I/O, respect the source encoding (UTF-8 for the
     * supplied files), close opened resources and do not print to the console.
     * Do not depend on Catalog or assign internal codes. Binding classes, where
     * required, are part of the student exercise and must remain outside the model.
     *
     * @param input source file; never null
     * @return newly constructed, uncoded contents in source order; never null
     * @throws NullPointerException if input is null after implementation
     * @throws IOException if reading, parsing, binding, configuration or data
     *         validation fails; preserve the cause and useful file context
     * @throws ParserConfigurationException 
     * @throws SAXException 
     * @throws ExerciseNotImplementedException while this exercise is unfinished
     */
    public List<Movie> importMovies(File input) throws IOException {

        if (input == null) {
            throw new NullPointerException("El archivo de entrada no puede ser null");
        }

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();

            factory.setNamespaceAware(true);
            factory.setValidating(false);

            // Seguridad: evitar XXE y entidades externas
            factory.setFeature(
                "http://xml.org/sax/features/external-general-entities", false
            );
            factory.setFeature(
                "http://xml.org/sax/features/external-parameter-entities", false
            );
            factory.setFeature(
                "http://apache.org/xml/features/nonvalidating/load-external-dtd", false
            );

            SAXParser parser = factory.newSAXParser();

            SaxMovieHandler handler = new SaxMovieHandler();

            // Utilizamos el archivo recibido por parámetro
            parser.parse(input, handler);

            return handler.getllistatMovies();

        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException(
                "Error al importar el archivo XML: " + input.getAbsolutePath(),
                e
            );
        }
    }
}
