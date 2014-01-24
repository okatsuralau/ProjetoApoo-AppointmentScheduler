package ifrn.tads.pds.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity  
@Table(name="expertise")
public class Expertise {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "title")
	@NotEmpty(message="Informe o título")
	private String title;


	public Expertise() {}

	public Expertise(String title) {
		this.title = title;
	}

	public Expertise(Integer id, String title) {
		this.id = id;
		this.title = title;
	}

	public Integer getId() {
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
