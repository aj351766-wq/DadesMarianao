package cat.institutmarianao.mediamanager.persistence.text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cat.institutmarianao.mediamanager.model.ContenidoOrdenable;
import cat.institutmarianao.mediamanager.model.MediaContent;

/**
 * Student exercise for generating list.txt using sequential text output.
 * The class is independent of Main and must use traditional Java I/O.
 */
public class TextCatalogExporter {
    /**
     * Creates a stateless exporter without opening any files.
     */
    public TextCatalogExporter() {
    }

    public void exportCatalog(File output, Collection<? extends MediaContent> contents)
            throws IOException {
    	
    	
            List<ContenidoOrdenable> contentsSorted = new ArrayList<>();

            
            for (MediaContent contenido : contents) {

                int codigo = contenido.getCode(); 

                contentsSorted.add(new ContenidoOrdenable(codigo, contenido));
            }
            
            Collections.sort(contentsSorted);
    	
    		try (BufferedWriter out = new BufferedWriter(
    		        new FileWriter(output, StandardCharsets.UTF_8))) {

    			
    			for (ContenidoOrdenable contenidoOrdenable : contentsSorted) {

                    MediaContent contenido = contenidoOrdenable.getContenido();

                    String linea = contenidoOrdenable.getCodigo() + ";"
                            + contenido.getTitle() + ";"
                            + contenido.getDirector();

                    out.write(linea);
                    out.newLine();
                }
            }
        }
    			
    		
}	
    		
    		
        
    	
    	
    
