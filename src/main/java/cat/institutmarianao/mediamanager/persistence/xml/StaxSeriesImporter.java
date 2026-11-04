package cat.institutmarianao.mediamanager.persistence.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import cat.institutmarianao.mediamanager.model.Episode;
import cat.institutmarianao.mediamanager.model.Season;
import cat.institutmarianao.mediamanager.model.Series;
import cat.institutmarianao.mediamanager.persistence.ExerciseNotImplementedException;

/**
 * Student exercise for importing series_stax.xml using StAX.
 * File access must use traditional Java I/O. This class does not manage a Catalog.
 */
public class StaxSeriesImporter {
	
	
	private List<Series> seriesList;

	private List<Season> seasons;
	private List<Episode> episodes;

	private String showTitle;
	private String director;
	private String genre;
	private String platform;
	private LocalDate premiere;

	private int sourceId;
	private int seasonNumber;
	private int episodeNumber;
	private int runtime;

	private String episodeTitle;
	private boolean dentroEpisodio=false;
	
    /**
     * Creates a stateless importer without opening any files.
     */
    public StaxSeriesImporter() {
    }

    /**
     * Imports the source using StAX, as required by Task 1.
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
     * @throws ExerciseNotImplementedException while this exercise is unfinished
     */
    public List<Series> importSeries(File input) throws IOException {
        

		seriesList = new ArrayList<>();

		XMLInputFactory factory = XMLInputFactory.newInstance();
		
		factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
		factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
		
		try (FileInputStream fis = new FileInputStream(input)){
			
			XMLStreamReader reader = factory.createXMLStreamReader(fis);
			
			String elementActual= null;
			StringBuilder text= new StringBuilder();
			
			while(reader.hasNext()) {
				int event= reader.next();
				
				switch (event) {
					case XMLStreamConstants.START_ELEMENT:
						elementActual= reader.getLocalName();
						text.setLength(0);
						
						if("show".equals(elementActual)) {
							String premiereText = reader.getAttributeValue(null, "premiere");
							String idText = reader.getAttributeValue(null, "id");
							
							premiere= LocalDate.parse(premiereText);
							sourceId = Integer.parseInt(idText);
							
							seasons = new ArrayList <>();
						}
						
						if("details".equals(elementActual)) {
							genre= reader.getAttributeValue(null, "genre");
							platform= reader.getAttributeValue(null, "platform");
						}
						
						if("season".equals(elementActual)) {
							seasonNumber= Integer.parseInt(reader.getAttributeValue(null, "number"));
						
							episodes= new ArrayList <>();
						
						}
						
						if("episode".equals(elementActual)) {
							episodeNumber = Integer.parseInt(reader.getAttributeValue(null, "number"));
						
							runtime = Integer.parseInt(reader.getAttributeValue(null, "minutes"));
							dentroEpisodio=true;
						}
						break;
					
					case XMLStreamConstants.CHARACTERS:
					    text.append(reader.getText());
					    break;
					    
					case XMLStreamConstants.END_ELEMENT:
						
						String endName= reader.getLocalName();
						String value= text.toString().trim();
						
						switch (endName) {
					
						case "title":

							if(dentroEpisodio) {
								episodeTitle=value;
							}
							
							else{
								showTitle=value;
							}
							break;
							
				        case "director":
				            director = value;
				            break;
				            
						case "episode":
						    Episode episode = new Episode(
						    		episodeNumber,
						    		episodeTitle,
						            runtime
						        );
							episodes.add(episode);
							dentroEpisodio=false;
							break;
							
						case "season":
						    Season season = new Season(seasonNumber);

						    for (Episode episodio : episodes) {
						        season.addEpisode(episodio);
						    }

						    seasons.add(season);
						    break;		
						    
						case "show":
						    Series series = new Series(
						            showTitle,
						            director,
						            genre,
						            premiere,
						            platform
						        );
						    
						    for (Season temporada : seasons) {
						        series.addSeason(temporada);
						    }

						    seriesList.add(series);
						    break;
					
						}
						
				
				}
				
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return seriesList;
		
		
		
    	// TODO Task 1: Implement the StaxSeriesImporter operation.
        //throw new ExerciseNotImplementedException("Task 1: StaxSeriesImporter");
    }
}
