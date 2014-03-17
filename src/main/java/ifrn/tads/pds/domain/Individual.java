package ifrn.tads.pds.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "individual", uniqueConstraints = {
	@UniqueConstraint(columnNames = "id"),
	@UniqueConstraint(columnNames = "cpf"),
	@UniqueConstraint(columnNames = "email")
})
public class Individual {
	private int id;
	protected int skin_id;

	@Column(name = "educationlevel_id", nullable = false)
	protected int educationlevel_id;

	@Column(name = "civilstatus_id", nullable = false)
	protected int civilstatus_id;

	@Column(name = "cpf", unique = true, nullable = false)
	@NotEmpty(message="Informe o CPF")
	@Size(min = 2, max = 11, message="Informe, no mínimo, 2 e, no máximo, 11 caracteres.")
	protected String cpf;

	@Column(name = "first_name", nullable = false)
	@NotEmpty(message="Informe o primeiro nome")
	@Size(min = 2, max = 100, message="Informe, no mínimo, 2 e, no máximo, 100 caracteres.")
	protected String first_name;

	@Column(name = "last_name")
	@Size(min = 2, max = 100, message="Informe, no mínimo, 2 e, no máximo, 100 caracteres.")
	protected String last_name;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Column(name = "birthday")
	protected String birthday;

	@Email
	@Column(name = "email", unique = true, nullable = false)
	@Size(min = 2, max = 100, message="Informe, no mínimo, 2 e, no máximo, 100 caracteres.")
	protected String email;
	
	@OneToOne
	protected User user;
	
	// @OneToMany
	protected Skin skin;
	protected Educationlevel educationlevel;
	protected Civilstatus civilstatus;

	public Individual(){}
	public Individual(int id, int skin_id, int educationlevel_id, int civilstatus_id,
			String cpf, String first_name, String last_name, String birthday,
			String email) {
		super();
		this.id = id;
		this.skin_id = skin_id;
		this.educationlevel_id = educationlevel_id;
		this.civilstatus_id = civilstatus_id;
		this.cpf = cpf;
		this.first_name = first_name;
		this.last_name = last_name;
		this.birthday = birthday;
		this.email = email;
	}
	public Individual(int skin_id, int educationlevel_id, int civilstatus_id,
			String cpf, String first_name, String last_name, String birthday,
			String email) {
		super();
		this.skin_id = skin_id;
		this.educationlevel_id = educationlevel_id;
		this.civilstatus_id = civilstatus_id;
		this.cpf = cpf;
		this.first_name = first_name;
		this.last_name = last_name;
		this.birthday = birthday;
		this.email = email;
	}
	
	//= Getters and Setters
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "skin_id", nullable = false)
	public int getSkin_id() {
		return skin_id;
	}
	public void setSkin_id(int skin_id) {
		this.skin_id = skin_id;
	}
	public int getEducationlevel_id() {
		return educationlevel_id;
	}
	public void setEducationlevel_id(int educationlevel_id) {
		this.educationlevel_id = educationlevel_id;
	}
	public int getCivilstatus_id() {
		return civilstatus_id;
	}
	public void setCivilstatus_id(int civilstatus_id) {
		this.civilstatus_id = civilstatus_id;
	}
	
	@OneToMany  
	@JoinColumn(name ="civilstatus_id")
	public Civilstatus getCivilstatus() {
		return civilstatus;
	}
	public void setCivilstatus(Civilstatus civilstatus) {
		this.civilstatus = civilstatus;
	}
	
	@OneToMany  
	@JoinColumn(name ="educationlevel_id")
	public Educationlevel getEducationlevel() {
		return educationlevel;
	}
	public void setEducationlevel(Educationlevel educationlevel) {
		this.educationlevel = educationlevel;
	}
	
	@OneToMany  
	@JoinColumn(name ="skin_id")
	public Skin getSkin() {
		return skin;
	}
	public void setSkin(Skin skin) {
		this.skin = skin;
	}
	
	@OneToOne
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String toString(){
		return "Individual("+id + ", " + skin_id + ", " + educationlevel_id + ", " + civilstatus_id + ", " + cpf + ", " + first_name + ", " +last_name + ", " + birthday + ", " + email+")";
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
