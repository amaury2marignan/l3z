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
public class Task {
	
	

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String description;
	private LocalDate nextDate;
	private int repeatAfter;
	
	@ManyToOne
	private SkillProfile skillProfileMinimumToDo;
	
	@ManyToOne
	private SkillProfile skillProfileMinimumToCheck;
	
	@ManyToOne
	private User whoDidIt;
	
	@ManyToOne
	private Family family;
	
	private int status;
	private int nbPoints;
	private int difficulty;
	
	@ManyToOne
	private Task nextTask;

	public Task(Family family, String name, String description, LocalDate nextDate, int repeatAfter,
			SkillProfile skillProfileMinimumToDo, SkillProfile skillProfileMinimumToCheck,int difficulty) {
		super();
		this.family=family;
		this.name = name;
		this.description = description;
		this.nextDate = nextDate;
		this.repeatAfter = repeatAfter;
		this.skillProfileMinimumToDo = skillProfileMinimumToDo;
		this.skillProfileMinimumToCheck = skillProfileMinimumToCheck;
		this.status = 0;
		this.nextTask = null;
		this.nbPoints = 0;
		this.difficulty=difficulty;
	}
	
	
	public Task() {
		super();
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getNextDate() {
		return nextDate;
	}

	public void setNextDate(LocalDate nextDate) {
		this.nextDate = nextDate;
	}

	public int getRepeatAfter() {
		return repeatAfter;
	}

	public void setRepeatAfter(int repeatAfter) {
		this.repeatAfter = repeatAfter;
	}

	public SkillProfile getSkillProfileMinimumToDo() {
		return skillProfileMinimumToDo;
	}

	public void setSkillProfileMinimumToDo(SkillProfile skillProfileMinimumToDo) {
		this.skillProfileMinimumToDo = skillProfileMinimumToDo;
	}

	public SkillProfile getSkillProfileMinimumToCheck() {
		return skillProfileMinimumToCheck;
	}

	public void setSkillProfileMinimumToCheck(SkillProfile skillProfileMinimumToCheck) {
		this.skillProfileMinimumToCheck = skillProfileMinimumToCheck;
	}


	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Task [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", nbpoints=");
		builder.append(nbPoints);
		builder.append(", nextDate=");
		builder.append(nextDate);
		builder.append(", repeatAfter=");
		builder.append(repeatAfter);
		builder.append(", skillProfileMinimumToDo=");
		builder.append(skillProfileMinimumToDo);
		builder.append(", skillProfileMinimumToCheck=");
		builder.append(skillProfileMinimumToCheck);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}

	public User getWhoDidIt() {
		return whoDidIt;
	}


	public void setWhoDidIt(User whoDidIt) {
		this.whoDidIt = whoDidIt;
	}


	public Task getNextTask() {
		return nextTask;
	}


	public void setNextTask(Task nextTask) {
		this.nextTask = nextTask;
	}


	public int getNbPoints() {
		return nbPoints;
	}


	public void setNbPoints(int nbPoints) {
		this.nbPoints = nbPoints;
	}


	public Family getFamily() {
		return family;
	}


	public void setFamily(Family family) {
		this.family = family;
	}


	public int getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	

}
