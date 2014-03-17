package ifrn.tads.pds.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="record")
public class Record {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "patient_id")
	private int patient_id;

	@Column(name = "datebook_id")
	private int datebook_id;

	@Column(name = "description")
	private String description;
	
	// Models associados
	private Patient patient;
	private Datebook datebook;

	public Record() {}
	public Record(int id, int datebook_id, String description){
		this.id = id;
		this.datebook_id = datebook_id;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}
	public int getDatebook_id() {
		return datebook_id;
	}
	public void setDatebook_id(int datebook_id) {
		this.datebook_id = datebook_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Datebook getDatebook() {
		return datebook;
	}
	public void setDatebook(Datebook datebook) {
		this.datebook = datebook;
	}
	// para o findList()
	// TODO: tentar setar dinamicamente
	public int getPrimaryKey() {
		return this.getId();
	}
	public String getDisplayField() {
		return this.getPatient().getDisplayField();
	}
}
