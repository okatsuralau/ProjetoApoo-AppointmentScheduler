package ifrn.tads.pds.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="log")
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "user_id", nullable = false)
	private int user_id;

	@Column(name = "record_id", nullable = false)
	private int record_id;

	@Column(name = "model", nullable = false)
	@Size(min = 2, max = 30, message="Informe, no mínimo, 2 e, no máximo, 30 caracteres.")
	private String model;

	@Column(name = "activity", nullable = false)
	@Size(min = 2, max = 20, message="Informe, no mínimo, 2 e, no máximo, 20 caracteres.")
	private String activity;

	@Column(name = "description", nullable = true)
	@Size(min = 2, max = 255, message="Informe, no mínimo, 2 e, no máximo, 255 caracteres.")
	private String description;

	@Column(name = "ip", nullable = false)
	@Size(min = 2, max = 15, message="Informe, no mínimo, 2 e, no máximo, 15 caracteres.")
	private String ip;

	@Column(name = "hostname", nullable = true)
	@Size(min = 2, max = 255, message="Informe, no mínimo, 2 e, no máximo, 255 caracteres.")
	private String hostname;

	@Column(name = "timestamp_date", nullable = false)
	private Timestamp timestamp_date;

	// Models associados
	private User user;
	private Record record;

	public Log(){}

	public Log(int id, int user_id, int record_id, String model, String activity, String description, String ip, String hostname, Timestamp timestamp_date, User user, Record record) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.record_id = record_id;
		this.model = model;
		this.activity = activity;
		this.description = description;
		this.ip = ip;
		this.hostname = hostname;
		this.timestamp_date = timestamp_date;
		this.user = user;
		this.record = record;
	}

	// Usado para criar o log
	public Log(int user_id, int record_id, String model, String activity, String description) {
		super();
		this.user_id = user_id;
		this.record_id = record_id;
		this.model = model;
		this.activity = activity;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getRecord_id() {
		return record_id;
	}
	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public Timestamp getTimestamp_date() {
		return timestamp_date;
	}
	public void setTimestamp_date(Timestamp timestamp_date) {
		this.timestamp_date = timestamp_date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Record getRecord() {
		return record;
	}
	public void setRecord(Record record) {
		this.record = record;
	}
	// para o findList()
	// TODO: tentar setar dinamicamente
	public int getPrimaryKey() {
		return this.getId();
	}
	public String getDisplayField() {
		return this.getActivity();
	}
}
