package ifrn.tads.pds.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="doctor")
public class Doctor extends Individual{

	private int id;
	private int individual_id;
	private int expertise_id;

	// Armazena os dados relacionados na entidade Individual  e Expertise
	private Individual individual; //belongsTo
	private Expertise expertise; // hasMany or hasOne

	//private String primaryKey = "id", displayField = "individual.first_name";

	public Doctor(){}
	public Doctor(int skin_id, int educationlevel_id, int civilstatus_id, String cpf, String first_name, String last_name, String birthday, String email) {
		super(skin_id, educationlevel_id, civilstatus_id, cpf, first_name, last_name,
				birthday, email);
	}
	/*public Doctor(int individual_id, int skin_id, int educationlevel_id, int civilstatus_id, String cpf, String first_name, String last_name, String birthday, String email) {
		super(individual_id, skin_id, educationlevel_id, civilstatus_id, cpf, first_name, last_name,
				birthday, email);
	}*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "individual_id")
	public int getIndividual_id() {
		return individual_id;
	}
	public void setIndividual_id(int individual_id) {
		this.individual_id = individual_id;
	}
	
	@Column(name = "expertise_id")
	public int getExpertise_id() {
		return expertise_id;
	}
	public void setExpertise_id(int expertise_id) {
		this.expertise_id = expertise_id;
	}
	public Individual getIndividual() {
		return individual;
	}
	public void setIndividual(Individual individual) {
		this.individual = individual;
	}
	public Expertise getExpertise() {
		return expertise;
	}
	public void setExpertise(Expertise expertise) {
		this.expertise = expertise;
	}
	
	public String toString(){
		return "Doctor("+id + ", " + expertise.getDisplayField() + ", " + individual.getDisplayField() +")";
	}
	
	// para o findList()
	// TODO: tentar setar dinamicamente
	public int getPrimaryKey() {
		return this.getId();
	}
	public String getDisplayField() {
		return this.individual.getFirst_name();
	}
}
