package ifrn.tads.pds.domain;

//import java.util.Date;
//import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="schedule")
public class Schedule {
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private int doctor_id;
		
	@Column(name = "expertise_id")
	private int expertise_id;

	@Column(name = "office_id")
	private int office_id;

	@Column(name = "sunday")
	private boolean sunday;

	@Column(name = "monday")
	private boolean monday;

	@Column(name = "tuesday")
	private boolean tuesday;

	@Column(name = "wednesday")
	private boolean wednesday;

	@Column(name = "thursday")
	private boolean thursday;

	@Column(name = "friday")
	private boolean friday;

	@Column(name = "saturday")
	private boolean saturday;

	@Column(name = "schedule_time")
	private String schedule_time;

	@Column(name = "amount")
	private int amount;
	
	// Associates
	private Expertise expertise;
	private Doctor doctor;
	private Office office;

	public Schedule() {}

	public Schedule(int id, int doctor_id, int expertise_id, int office_id,
			boolean sunday, boolean monday, boolean tuesday, boolean wednesday,
			boolean thursday, boolean friday, boolean saturday,
			String schedule_time, int amount) {
		super();
		this.id = id;
		this.doctor_id = doctor_id;
		this.expertise_id = expertise_id;
		this.office_id = office_id;
		this.sunday = sunday;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.schedule_time = schedule_time;
		this.amount = amount;
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

	public boolean isSunday() {
		return sunday;
	}

	public void setSunday(boolean sunday) {
		this.sunday = sunday;
	}

	public boolean isMonday() {
		return monday;
	}

	public void setMonday(boolean monday) {
		this.monday = monday;
	}

	public boolean isTuesday() {
		return tuesday;
	}

	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}

	public boolean isWednesday() {
		return wednesday;
	}

	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}

	public boolean isThursday() {
		return thursday;
	}

	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}

	public boolean isFriday() {
		return friday;
	}

	public void setFriday(boolean friday) {
		this.friday = friday;
	}

	public boolean isSaturday() {
		return saturday;
	}

	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}

	public String getSchedule_time() {
		return schedule_time;
	}

	public void setSchedule_time(String schedule_time) {
		this.schedule_time = schedule_time;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Expertise getExpertise() {
		return expertise;
	}

	public void setExpertise(Expertise expertise) {
		this.expertise = expertise;
	}
	
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	// para o findList()
	// TODO: tentar setar dinamicamente
	public int getPrimaryKey() {
		return this.getId();
	}
	public String getDisplayField() {
		return this.getDoctor().getDisplayField();
	}
}