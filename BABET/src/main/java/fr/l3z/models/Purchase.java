package fr.l3z.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Purchase {
	
	

	@Id
	@GeneratedValue
	private Long id;
	private String description;
	
	@ManyToOne
	private Family family;
		
	@ManyToOne
	private User whoDidIt;
	
	private int status;

	public Purchase() {
		super();
	}

	public Purchase(Family family, String description) {
		super();
		this.family=family;
		this.description = description;
		
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public User getWhoDidIt() {
		return whoDidIt;
	}

	public void setWhoDidIt(User whoDidIt) {
		this.whoDidIt = whoDidIt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Purchase [id=");
		builder.append(id);
		builder.append(", description=");
		builder.append(description);
		builder.append(", whoDidIt=");
		builder.append(whoDidIt);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

	
		
	
}
	