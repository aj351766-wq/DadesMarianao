package cat.institutmarianao.mediamanager.persistence.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cat.institutmarianao.mediamanager.model.Episode;
import cat.institutmarianao.mediamanager.model.MediaContent;
import cat.institutmarianao.mediamanager.model.Movie;
import cat.institutmarianao.mediamanager.model.Season;
import cat.institutmarianao.mediamanager.model.Series;


public class DomCatalogExporter {

    public DomCatalogExporter() {
    }

    
    private void createMovie(Document doc, Element movies, Movie movie) {

  
        Element movieElement = doc.createElement("movie");

        movieElement.setAttribute("code", String.valueOf(movie.getCode()));

        movies.appendChild(movieElement);

        appendText(doc, movieElement, "title", movie.getTitle());

        appendText(doc, movieElement, "director", movie.getDirector());

        appendText(doc, movieElement, "genre", movie.getGenre());

        appendText(doc, movieElement, "releaseDate",
                movie.getReleaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        appendText(doc, movieElement, "platform", movie.getPlatform());

        appendText(doc, movieElement, "runtime",
                String.valueOf(movie.getRuntime()));
    }
    
    
    private void createSeries(Document doc, Element series, Series serie) {

 
        Element seriesElement = doc.createElement("seriesItem");

        seriesElement.setAttribute("code", String.valueOf(serie.getCode()));

        series.appendChild(seriesElement);

        appendText(doc, seriesElement, "title", serie.getTitle());

        appendText(doc, seriesElement, "director", serie.getDirector());

        appendText(doc, seriesElement, "genre", serie.getGenre());

        appendText(doc, seriesElement, "releaseDate",
                serie.getReleaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        appendText(doc, seriesElement, "platform", serie.getPlatform());


        Element seasons = doc.createElement("seasons");
        seriesElement.appendChild(seasons);

        List<Season> seasonList = new ArrayList<>(serie.getSeasons());

        seasonList.sort(
                Comparator.comparingInt(Season::getNumber)
        );

        for (Season season : seasonList) {

            Element seasonElement = doc.createElement("season");

            seasonElement.setAttribute("number",
                    String.valueOf(season.getNumber()));

            seasons.appendChild(seasonElement);

            Element episodes = doc.createElement("episodes");
            seasonElement.appendChild(episodes);

            List<Episode> episodeList = new ArrayList<>(season.getEpisodes());

            episodeList.sort(
                    Comparator.comparingInt(Episode::getNumber)
            );

            for (Episode episode : episodeList) {

                Element episodeElement = doc.createElement("episode");

                episodeElement.setAttribute("number",
                        String.valueOf(episode.getNumber()));

                episodes.appendChild(episodeElement);

                appendText(doc, episodeElement, "title", episode.getTitle());

                appendText(doc, episodeElement, "runtime",
                        String.valueOf(episode.getRuntime()));
            }
        }
    }

    private void appendText(Document doc, Element parent, String name, String value) {

        Element element = doc.createElement(name);

        element.setTextContent(value);

        parent.appendChild(element);
    }

    
    public void exportCatalog(File output, Collection<? extends MediaContent> contents) throws IOException
            {
		
	        Objects.requireNonNull(output, "El archivo de salida no puede ser null");
	        Objects.requireNonNull(contents, "La colección no puede ser null");

	     
	        List<Movie> movieList = new ArrayList<>();
	        List<Series> seriesList = new ArrayList<>();

		
			for (MediaContent content : contents) {
				Objects.requireNonNull(content, "La coleccion contiene un elemento null");
				
				Integer code= content.getCode();
				
				if(code==null) {
					throw new IllegalArgumentException("El contenido no tiene código asignado");
				}
				
				if(content instanceof Movie) {
					movieList.add((Movie) content);
				
				} else if (content instanceof Series) {
					seriesList.add((Series) content);
				} 
				
				else {
					throw new IllegalArgumentException("Tipo de contenido no soportado");
				}
			}
			
			
			movieList.sort(
					Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER).thenComparing(Movie::getCode)
					);
			
			seriesList.sort(
					Comparator.comparing(Series::getTitle, String.CASE_INSENSITIVE_ORDER).thenComparing(Series::getCode)
					);
			
			
           
				try {
					DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();

					f.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

					DocumentBuilder b = f.newDocumentBuilder();

					
		
					Document doc = b.newDocument();

				
					Element catalog = doc.createElement("catalog");
					doc.appendChild(catalog);

					Element movies = doc.createElement("movies");
					catalog.appendChild(movies);


					Element series = doc.createElement("series");
					catalog.appendChild(series);
					
					
					for (Movie movie : movieList) {
						createMovie(doc,movies,movie); 
					}
					
					for (Series serie : seriesList) {
					    createSeries(doc, series, serie);
					}
					
					TransformerFactory tf = TransformerFactory.newInstance();

					Transformer tr = tf.newTransformer();

					tr.setOutputProperty(OutputKeys.INDENT, "yes");
					tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
					tr.setOutputProperty(
					        "{http://xml.apache.org/xslt}indent-amount", "2");
					
					
					
					try(FileOutputStream fos = new FileOutputStream(output, false)){
						
					    tr.transform(
					            new DOMSource(doc),
					            new StreamResult(fos)
					    );
					
					
					}
				
            } catch (ParserConfigurationException | TransformerException | DOMException e) {

                throw new IOException("Error al generar el catálogo XML", e);

            } catch (TransformerFactoryConfigurationError e) {

                throw new IOException("Error al configurar el Transformer", e);

            } catch (IOException e) {

                throw new IOException("Error al escribir el archivo: " + output.getAbsolutePath(), e);
            }
            
            
		

    }
}
