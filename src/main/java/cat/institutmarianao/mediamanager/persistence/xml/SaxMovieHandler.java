package cat.institutmarianao.mediamanager.persistence.xml;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cat.institutmarianao.mediamanager.model.Movie;

public class SaxMovieHandler extends DefaultHandler{
	
	private List<Movie> llistatMovies;

	private String title;
	private String director;
	private String genre;
	private String platform;

	private LocalDate releaseDate;
	private int runtime;

	private StringBuilder contingut;
	private String etiquetaActual;
	
	public List getllistatMovies() {
		return llistatMovies;
	}
	
	
	@Override
	public void startDocument() throws SAXException {
		llistatMovies = new ArrayList <> ();
	
		contingut= new StringBuilder();
		
		etiquetaActual="";
		
		System.out.println("Iniciant el processament del document XML...");
		
	
	}
	
	@Override
	public void endDocument() throws SAXException{
		System.out.println("S'ha acabat el processament del document XML.");
	}
	
	@Override
	 public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        contingut.setLength(0);
        
        if("film".equalsIgnoreCase(qName)) {
            title = attributes.getValue("name");
            director = null;
            genre = null;
            platform = null;
            releaseDate = null;
            runtime = 0;
            
            String released = attributes.getValue("released");

            if (released != null) {
                releaseDate = LocalDate.parse(released);
            }
        	
        }

        if("creators".equalsIgnoreCase(qName)) {
        	etiquetaActual="creators";
        } else if ("classification".equalsIgnoreCase(qName)) {
        	etiquetaActual="classification";
        } else if ("availability".equalsIgnoreCase(qName)){
        	etiquetaActual="availability";
        }
        
        
        if("footage".equalsIgnoreCase(qName)) {
        	
        	String minutes = attributes.getValue("minutes");
        	
            if (minutes != null) {
                runtime = Integer.parseInt(minutes);
            }
        	
        }
        
    }
	
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
    	// acumulem el contingut dels elements ja que poden venir en diversos fragments
        contingut.append(ch, start, length); 
    }
    
        
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    	String dades= contingut.toString().trim();
    	

    		switch(qName.toLowerCase()) {
    		case "name":
    		    if ("creators".equals(etiquetaActual)) {
    		    	director = dades;

    		    } else if ("classification".equals(etiquetaActual)) {
    		    	genre = dades;

    		    } else if ("availability".equals(etiquetaActual)) {
    		        platform = dades;
    		    }
    			break;
    		
    		
        case "creators":
        case "classification":
        case "availability":
            etiquetaActual = "";
            break;

        
        case "film":
            llistatMovies.add(new Movie(
                    title,
                    director,
                    genre,
                    releaseDate,
                    platform,
                    runtime
                ));
            
            break;
    	
    }
    	
    	contingut.setLength(0);
    	
    }
}
