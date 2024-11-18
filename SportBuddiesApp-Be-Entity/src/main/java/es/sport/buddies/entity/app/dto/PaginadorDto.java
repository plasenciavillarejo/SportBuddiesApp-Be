package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PaginadorDto implements Serializable {

	// Número de elementos que se muestran por página
	private Integer tamanioPagina;

	// Página mostrada de entre todas las que tiene la búsqueda
	private Integer paginaActual;

	// Número de paginas que tiene la búsqueda
	private Integer paginas;

	// Número total de elementos que tiene la búsqueda
	private Integer registros;

	// Verdadero si se está posicionado en la primera página
	private boolean inicio;

	// Verdadero si se está posicionado en la última página
	private boolean fin;

	// Inicializamos el paginador con valores por defecto
	public PaginadorDto() {
	   this.paginaActual = 1;
	   this.tamanioPagina = 10;
	 }

	public PaginadorDto(Integer paginaActual) {
	   this.tamanioPagina = 10;
	   this.paginaActual = paginaActual;
	 }

	public PaginadorDto(Integer registros, Integer tamanioPagina) {
	   this.registros = registros;
	   this.tamanioPagina = tamanioPagina;
	   this.paginaActual = 1;
	   this.paginas = this.getPaginas();
	 }

	public Integer getTamanioPagina() {
		return tamanioPagina;
	}

	public void setTamanioPagina(Integer tamanioPagina) {
		this.tamanioPagina = tamanioPagina;
	}

	public Integer getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(Integer paginaActual) {
		this.paginaActual = paginaActual;
	}

	public Integer getRegistros() {
		return registros;
	}

	public void setRegistros(Integer registros) {
		this.registros = registros;
	}

	public boolean isInicio() {
		this.inicio = this.paginaActual == 1;
		return this.inicio;
	}

	public void setInicio(boolean inicio) {
		this.inicio = inicio;
	}

	public boolean isFin() {
		this.fin = this.paginaActual.equals(this.paginas);
		return fin;
	}

	public void setFin(boolean fin) {
		this.fin = fin;
	}
	
	public Integer getPaginas() {
		if (registros != null) {
			this.paginas = (int) Math.ceil((double) registros / (double) tamanioPagina);
		} else {
			this.paginas = 0;
		}
		return paginas;
	}

	public void setPaginas(Integer paginas) {
		this.paginas = paginas;
	}

	private static final long serialVersionUID = 2204195332241839710L;

}