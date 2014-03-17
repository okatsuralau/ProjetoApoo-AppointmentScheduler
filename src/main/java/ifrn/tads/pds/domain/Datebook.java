package ifrn.tads.pds.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="datebook")
public class Datebook {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "availability_id", nullable = false)
	private int availability_id;

	@Column(name = "patient_id", nullable = false)
	private int patient_id;

	@Column(name = "exam_id", nullable = false)
	private int exam_id;

	@Column(name = "health_plan_id", nullable = false)
	private int health_plan_id;

	@Column(name = "note")
	private String note;

	@Column(name = "checkin")
	private boolean checkin;

	@Column(name = "datacheckin")
	private Date datacheckin;

	@Column(name = "displayed")
	private boolean displayed;
	
	// Associates
	private Availability availability; // belongsTo
	private Patient patient; // belongsTo
	private Exam exam; // belongsTo
	private HealthPlan healthPlan; // belongsTo
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getAvailability_id() {
		return availability_id;
	}
	public void setAvailability_id(int availability_id) {
		this.availability_id = availability_id;
	}
	public int getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}
	public int getExam_id() {
		return exam_id;
	}
	public void setExam_id(int exam_id) {
		this.exam_id = exam_id;
	}
	public int getHealth_plan_id() {
		return health_plan_id;
	}
	public void setHealth_plan_id(int health_plan_id) {
		this.health_plan_id = health_plan_id;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public boolean isCheckin() {
		return checkin;
	}
	public void setCheckin(boolean checkin) {
		this.checkin = checkin;
	}
	public Date getDatacheckin() {
		return datacheckin;
	}
	public void setDatacheckin(Date datacheckin) {
		this.datacheckin = datacheckin;
	}
	public boolean isDisplayed() {
		return displayed;
	}
	public void setDisplayed(boolean displayed) {
		this.displayed = displayed;
	}
	
	// Associates
	public Availability getAvailability() {
		return availability;
	}
	public void setAvailability(Availability availability) {
		this.availability = availability;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Exam getExam() {
		return exam;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	public HealthPlan getHealthPlan() {
		return healthPlan;
	}
	public void setHealthPlan(HealthPlan healthPlan) {
		this.healthPlan = healthPlan;
	}
	
	// para o findList()
	// TODO: tentar setar dinamicamente
	public int getPrimaryKey() {
		return this.getId();
	}
	public String getDisplayField() {
		return this.getPatient().getFirst_name();
	}
}
