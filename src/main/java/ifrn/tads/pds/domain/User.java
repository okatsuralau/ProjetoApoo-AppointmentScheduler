package ifrn.tads.pds.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.UniqueConstraint;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "user", uniqueConstraints = {
	@UniqueConstraint(columnNames = "id"),
	@UniqueConstraint(columnNames = "username")
})
public class User extends Individual{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	private int individual_id;
	private int role_id;
	private String username;
	private String passcode;
	private boolean activated;
	private boolean deleted;
	private Date created;
	private Date modified;

	// Associates
	private Individual individual; // belongsTo
	private Role role; // hasOne

	public User() {}
	
	// Individual + User (saber se é necessario)
	/*public User(int individual_id, int skin_id, int educationlevel_id, int civilstatus_id, String cpf, String first_name, String last_name, String birthday, String email, int user_id, int role_id, String username, String passcode, boolean activated, boolean deleted, Date created, Date modified) {
		super(individual_id, skin_id, educationlevel_id, civilstatus_id, cpf, first_name, last_name, birthday, email);
		this.id = user_id;
		this.individual_id = individual_id;
		this.role_id = role_id;
		this.username = username;
		this.passcode = passcode;
		this.activated = activated;
		this.deleted = deleted;
		this.created = created;
		this.modified = modified;
	}*/
	
	// Somente user
	public User(int id, int individual_id, int role_id, String username, String passcode, boolean activated, boolean deleted, Date created, Date modified) {
		super();
		this.id = id;
		this.individual_id = individual_id;
		this.role_id = role_id;
		this.username = username;
		this.passcode = passcode;
		this.activated = activated;
		this.deleted = deleted;
		this.created = created;
		this.modified = modified;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "individual_id", nullable = false)
	public int getIndividual_id() {
		return individual_id;
	}

	public void setIndividual_id(int individual_id) {
		this.individual_id = individual_id;
	}
	
	@Column(name = "role_id", nullable = false)
	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	
	@Column(name = "username", unique = true, nullable = false)
	@NotEmpty(message="Informe o título")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	// TODO trocar sistema de validação para customizar quando um objeto será validado
	// Exemplo: quando for editar um usuário, não preciso informar a senha
	// @NotEmpty(message="Informe uma senha")
	@Column(name = "passcode", nullable = false)
	@Size(min = 6, max = 32, message="Informe, no mínimo, 6 e, no máximo, 32 caracteres.")
	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}
	
	@Column(name = "activated")
	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	@Column(name = "deleted")
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	@Column(name = "created")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	@Column(name = "modified")
	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Individual getIndividual() {
		return this.individual;
	}
	
	public void setRole(Role role){
		this.role = role;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Role getRole() {
		return this.role;
	}
	
	public void setIndividual(Individual individual){
		this.individual = individual;
	}
	
	public String toString(){
		return "User("+id + ", " + individual.getDisplayField() + ", " + role.getDisplayField() + ", " + username + ", " + passcode + ", " + activated + ", " + deleted + ", " + created + ", " + modified +")";
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
