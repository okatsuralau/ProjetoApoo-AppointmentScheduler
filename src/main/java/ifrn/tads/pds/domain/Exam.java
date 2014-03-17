package ifrn.tads.pds.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="exam")

public class Exam {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "title")
	@NotEmpty(message="Informe o título")
	@Size(min = 2, max = 45, message="Informe, no mínimo, 2 e, no máximo, 45 caracteres.")
	private String title;
	
	@Column(name = "expertise_id")
	private int expertise_id;
	
	// Associates
	private Expertise expertise;
	
	public Exam() {}

	public Exam(int id, String title, int expertise_id) {
		this.id = id;
		this.title = title;
		this.expertise_id = expertise_id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getExpertise_id() {
		return expertise_id;
	}
	
	public void setExpertise_id(int expertise_id) {
		this.expertise_id = expertise_id;
	}
	
	public Expertise getExpertise() {
		return expertise;
	}
	
	public void setExpertise(Expertise expertise) {
		this.expertise = expertise;
	}
	
	// para o findList()
	// TODO: tentar setar dinamicamente
	public int getPrimaryKey() {
		return this.getId();
	}
	public String getDisplayField() {
		return this.getTitle();
	}	
}