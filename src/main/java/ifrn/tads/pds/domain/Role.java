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
@Table(name = "role", uniqueConstraints = {
	@UniqueConstraint(columnNames = "id"),
	@UniqueConstraint(columnNames = "title")
})
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	// TODO: validar por registros únicos

	@Column(name = "title", unique = true, nullable = false)
	@NotEmpty(message="Informe o título")
	@Size(min = 2, max = 45, message="Informe, no mínimo, 2 e, no máximo, 45 caracteres.")
	private String title;

	@Column(name = "slug", unique = true, nullable = false)
	private String slug;

	public Role() {}

	public Role(String title) {
		this.title = title;
		this.slug = title;
	}

	public Role(String title, String slug) {
		this.title = title;
		this.slug = slug;
	}

	public Role(int id, String title, String slug) {
		this.id = id;
		this.title = title;
		this.slug = slug;
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

	public String getSlug() {
		// TODO: criar, de fato, o slug do título
		this.slug = this.title;
		if(this.slug.equalsIgnoreCase("") || this.slug == null){
			this.slug = "";
		}

		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String toString() {
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
