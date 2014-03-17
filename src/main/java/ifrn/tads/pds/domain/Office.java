package ifrn.tads.pds.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "office", uniqueConstraints = {
	@UniqueConstraint(columnNames = "id"),
	@UniqueConstraint(columnNames = "title")
})
public class Office {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "title", unique = true, nullable = false)
	@NotEmpty(message="Informe o título")
	@Size(min = 2, max = 10, message="Informe, no mínimo, 2 e, no máximo, 10 caracteres.")
	private String title;

	public Office() {}

	public Office(String title) {
		this.title = title;
	}

	public Office(int id, String title) {
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
	// para o findList()
	// TODO: tentar setar dinamicamente
	public int getPrimaryKey() {
		return this.getId();
	}
	public String getDisplayField() {
		return this.getTitle();
	}
}
