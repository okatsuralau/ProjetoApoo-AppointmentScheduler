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
@Table(name="civilstatus")
public class Civilstatus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	@NotEmpty(message="Informe o título")
	@Size(min = 2, max = 45, message="Informe, no mínimo, 2 e, no máximo, 45 caracteres.")
	private String title;


	public Civilstatus() {}

	public Civilstatus(String title) {
		this.title = title;
	}

	public Civilstatus(int id, String title) {
		this.id = id;
		this.title = title;
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

	public String _toString() {
		return this.title;
	}
}
