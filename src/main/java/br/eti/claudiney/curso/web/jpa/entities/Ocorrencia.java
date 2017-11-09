package br.eti.claudiney.curso.web.jpa.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TAB_OCR")
@SuppressWarnings("serial")
public class Ocorrencia implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="NM_OCR")
	private String nomeOcorrencia;
	
	public Integer getId() {
		return id;
	}
	
	public String getNomeOcorrencia() {
		return nomeOcorrencia;
	}
	
	public void setNomeOcorrencia(String nomeOcorrencia) {
		this.nomeOcorrencia = nomeOcorrencia;
	}

}
