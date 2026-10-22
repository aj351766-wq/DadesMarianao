package cat.institutmarianao.mediamanager.persistence.xml;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cat.institutmarianao.mediamanager.model.Movie;
import cat.institutmarianao.mediamanager.persistence.ExerciseNotImplementedException;

/**
 * Student exercise for importing movies_dom.xml using DOM.
 * File access must use traditional Java I/O. This class does not manage a Catalog.
 */
public class DomMovieImporter {
    /**
     * Creates a stateless importer without opening any files.
     */
	private List<Movie>movieList=new ArrayList <>();
	
    public DomMovieImporter() {
    }

    /**
     * Imports the source using DOM, as required by Task 1.
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
    public List<Movie> importMovies(File input)  {
        // TODO Task 1: Implement the DomMovieImporter operation.
    	final String ETIQUETA_MOVIE="movie";
		final String ETIQUETA_SOURCE_ID = "sourceId";
		final String ETIQUETA_NAME = "name";
		final String ETIQUETA_DIRECTOR = "director";
		final String ETIQUETA_GENERE= "genre";
		final String ETIQUETA_REALEASEDATE="releaseDate";
		final String ETIQUETA_PLATFORM="platform";
		final String ETIQUETA_RUNTIME="runtime";
		
		
		
        try {
			// Definir el patrón exacto que tiene tu XML/HTML
			DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			
			
			// Configuració i creació del parser
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			
			// Protecció contra XML externs (XXE)
			f.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

			// Si l'XML té namespaces
			f.setNamespaceAware(true);
			
			// Ignorar espais en blanc i comentaris
			f.setIgnoringElementContentWhitespace(true);
			
			f.setIgnoringComments(true);
			// Crear el parser
			DocumentBuilder b = f.newDocumentBuilder();
			
			Document doc = b.parse(new File("data/input/movies_dom.xml"));
			
			// 3) Normalitzar (opcional)
			doc.getDocumentElement().normalize();
			
			
			//Listar la informacióde Movies
			
			NodeList movies= doc.getElementsByTagName(ETIQUETA_MOVIE);
			
			for(int i=0; i < movies.getLength(); i++) {
				Element e = (Element) movies.item(i);
				
				int sourceId = Integer.parseInt(
					    e.getElementsByTagName(ETIQUETA_SOURCE_ID)
					     .item(0)
					     .getTextContent()
					     .trim()
					);
				
				String title= e.getElementsByTagName(ETIQUETA_NAME).toString().trim();
				
				Element director =(Element) e.getElementsByTagName(ETIQUETA_DIRECTOR).item(0);
				
				String directorName= director.getElementsByTagName(ETIQUETA_NAME).toString().trim();
				
				String genere= e.getElementsByTagName(ETIQUETA_GENERE).toString().trim();
				
				LocalDate releaseDate = LocalDate.parse(
					    e.getElementsByTagName(ETIQUETA_REALEASEDATE)
					     .item(0)
					     .getTextContent()
					     .trim(),
					    formateador
					);
			
				String platform= e.getElementsByTagName(ETIQUETA_PLATFORM).toString().trim();
				
				int runTime = Integer.parseInt(e.getElementsByTagName(ETIQUETA_RUNTIME).toString().trim());
			
				/*String title, String director, String genre,
			    LocalDate releaseDate, String platform, int runtime) {
			super(title, director, genre, releaseDate, platform);*/
				movieList.add(new Movie(title, directorName, genere, releaseDate, platform, runTime));
			}
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return movieList;
		
		//throw new ExerciseNotImplementedException("Task 1: DomMovieImporter");
		
    }
}
