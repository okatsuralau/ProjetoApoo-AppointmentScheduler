package ifrn.tads.pds.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="queue")
public class Queue {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "datebook_id", nullable = false)
	private int datebook_id;

	private Datebook datebook;

	public Queue() {}

	public Queue(int datebook_id) {
		this.datebook_id = datebook_id;
	}

	public Queue(int id, int datebook_id) {
		this.id = id;
		this.datebook_id = datebook_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDatebook_id() {
		return datebook_id;
	}

	public void setDatebook_id(int datebook_id) {
		this.datebook_id = datebook_id;
	}

	public Datebook getDatebook() {
		return datebook;
	}

	public void setDatebook(Datebook datebook) {
		this.datebook = datebook;
	}
	
	// para o findList()
	// TODO: tentar setar dinamicamente
	public int getPrimaryKey() {
		return this.getId();
	}
	public String getDisplayField() {
		return this.getDatebook().getDisplayField();
	}
}
