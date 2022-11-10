package fr.l3z.models;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
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
public class User {

	@Id
	@GeneratedValue
	private Long id;
	
	private String userName;
	private String password;
	private int score;
	
	
	@ManyToOne
	private Family family;
 
	
	@OneToOne
	private SkillProfile skillProfile;
	
	private String color;
	
	private int idPicNumber;
	

	public User(String userName, String password, String color) {
		
		this.userName = userName;
		this.password = password;
		this.color = color;
		this.idPicNumber = 1;
		this.score = 0;
		
	}
	
	public User() {
		this.score = 0;
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public SkillProfile getSkillProfile() {
		return skillProfile;
	}

	public void setSkillProfile(SkillProfile skillProfile) {
		this.skillProfile = skillProfile;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", userName=");
		builder.append(userName);
		builder.append("]");
		return builder.toString();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getIdPicNumber() {
		return idPicNumber;
	}

	public void setIdPicNumber(int idPicNumber) {
		this.idPicNumber = idPicNumber;
	}

	
}