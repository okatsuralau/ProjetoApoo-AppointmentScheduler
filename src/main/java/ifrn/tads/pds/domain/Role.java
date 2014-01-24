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
@Table(name="role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	// TODO: validar por registros �nicos
	
	@Column(name = "title", unique = true)
	@NotEmpty(message="Informe o t�tulo")
	@Size(min = 2, max = 45, message="Informe, no m�nimo, 2 e, no m�ximo, 45 caracteres.")
	private String title;

	@Column(name = "slug")
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
		// TODO: criar, de fato, o slug do t�tulo
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
}