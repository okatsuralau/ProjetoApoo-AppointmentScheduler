package ifrn.tads.pds.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="patient")
public class Patient extends Individual{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "individual_id", nullable = false)
	private int individual_id;
	
	// Models associados
	private Individual individual;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIndividual_id() {
		return individual_id;
	}

	public void setIndividual_id(int individual_id) {
		this.individual_id = individual_id;
	}

	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}
	// para o findList()
	// TODO: tentar setar dinamicamente
	public int getPrimaryKey() {
		return this.getId();
	}
	public String getDisplayField() {
		return this.getFirst_name();
	}
}
