package cat.institutmarianao.mediamanager.persistence.xml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cat.institutmarianao.mediamanager.model.Movie;

public class SaxMovieHandler extends DefaultHandler{
	
	private List llistatMovies;
	private Movie movieActual;
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
        
        if("fil,".equalsIgnoreCase(qName)) {
        	movieActual= new Movie();
        }
        
        if("creators".equalsIgnoreCase(qName)) {
        	etiquetaActual="creators";
        } else if ("classification".equalsIgnoreCase(qName)) {
        	etiquetaActual="classification";
        } else if ("availability".equalsIgnoreCase(qName)){
        	etiquetaActual="availability";
        }
        
        
        if("footage".equalsIgnoreCase(qName) && movieActual != null) {
        	
        	movieActual.setRuntime(Integer.parseInt (attributes.getValue("minutes")));
        	
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
    	
    	if(movieActual!=null) {
    		switch(qName.toLowerCase()) {
    		case "name":
    			movieActual.setTitle(dades);
    			break;
    		
    		
        case "creators":
        	movieActual.setDirector(dades);
        	break;
        
        case "classification":
        	movieActual.setGenre(dades);
        	break;
        	
        case "availability":
            etiquetaActual = "";
            break;

        
        case "film":
            llistatMovies.add(movieActual);
            movieActual = null;
            break;
    	
    }
}
    	
    	contingut.setLength(0);
    	
    }
}
