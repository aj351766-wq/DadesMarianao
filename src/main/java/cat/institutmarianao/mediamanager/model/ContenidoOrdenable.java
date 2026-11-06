package cat.institutmarianao.mediamanager.model;

public class ContenidoOrdenable implements Comparable<ContenidoOrdenable>{
	
	private int codigo;
	private MediaContent contenido;
	
	public ContenidoOrdenable(int codigo, MediaContent contenido) {
		this.codigo = codigo;
		this.contenido = contenido;
	}
	
	
	
	@Override
	
	public int compareTo(ContenidoOrdenable otro) {
		int resultado = this.contenido.getTitle().compareToIgnoreCase(otro.contenido.getTitle());
	
		if(resultado != 0) {
			return resultado;
		}
		
		return Integer.compare(this.codigo, otro.codigo);
	
	}



	public int getCodigo() {
		return codigo;
	}



	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}



	public MediaContent getContenido() {
		return contenido;
	}



	public void setContenido(MediaContent contenido) {
		this.contenido = contenido;
	}
	
	
}
