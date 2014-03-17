package ifrn.tads.pds.domain;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="availability")
public class Availability {
	@Id	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "doctor_id", nullable = false)
	private int doctor_id;

	@Column(name = "expertise_id", nullable = false)
	private int expertise_id;

	@Column(name = "office_id", nullable = false)
	private int office_id;

	@Column(name = "availability_date")
	private Date availability_date;

	@Column(name = "availability_time")
	private String availability_time;

	@Column(name = "available_amount")
	private int available_amount;

	@Column(name = "scheduled_amount")
	private int scheduled_amount;
	
	private Expertise expertise;
	private Office office;
	private Doctor doctor;

	public Availability() {}

	public Availability(int id, int doctor_id, int expertise_id, int office_id,
			Date availability_date, String availability_time, int available_amount, int scheduled_amount) {
		super();
		this.id = id;
		this.doctor_id = doctor_id;
		this.expertise_id = expertise_id;
		this.office_id = office_id;
		this.availability_date = availability_date;
		this.availability_time = availability_time;
		this.available_amount = available_amount;
		this.scheduled_amount = scheduled_amount;
	}

	public Expertise getExpertise() {
		return expertise;
	}

	public void setExpertise(Expertise expertise) {
		this.expertise = expertise;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	public int getExpertise_id() {
		return expertise_id;
	}

	public void setExpertise_id(int expertise_id) {
		this.expertise_id = expertise_id;
	}

	public int getOffice_id() {
		return office_id;
	}

	public void setOffice_id(int office_id) {
		this.office_id = office_id;
	}

	public Date getAvailability_date() {
		return availability_date;
	}

	public void setAvailability_date(Date availability_date) {
		this.availability_date = availability_date;
	}

//	public Time getAvailability_time() {
	public String getAvailability_time() {
		return availability_time;
	}

	public void setAvailability_time(String availability_time) {
		this.availability_time = availability_time;
	}

	public int getAvailable_amount() {
		return available_amount;
	}

	public void setAvailable_amount(int available_amount) {
		this.available_amount = available_amount;
	}

	public int getScheduled_amount() {
		return scheduled_amount;
	}

	public void setScheduled_amount(int scheduled_amount) {
		this.scheduled_amount = scheduled_amount;
	}
	
	// para o findList()
	// TODO: tentar setar dinamicamente
	public int getPrimaryKey() {
		return this.getId();
	}
	public String getDisplayField() {
		return this.getDoctor().getFirst_name();
	}
}
