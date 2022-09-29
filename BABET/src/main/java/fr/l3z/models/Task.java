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
	@ManyToOne
	private Rule rule;
	private String name;
	private String description;
	private LocalDateTime nextDate;
	private int repeatAfter;
	
	@OneToOne
	private SkillProfile skillProfileMinimumToDo;
	
	@OneToOne
	private SkillProfile skillProfileMinimumToCheck;
	
	@ManyToOne
	private User whoDidIt;
	
	private int status;
	private int nbPoints;
	
	@ManyToOne
	private Task nextTask;

	public Task(String name, String description, LocalDateTime nextDate, int repeatAfter,
			SkillProfile skillProfileMinimumToDo, SkillProfile skillProfileMinimumToCheck) {
		super();
		this.name = name;
		this.description = description;
		this.nextDate = nextDate;
		this.repeatAfter = repeatAfter;
		this.skillProfileMinimumToDo = skillProfileMinimumToDo;
		this.skillProfileMinimumToCheck = skillProfileMinimumToCheck;
		this.status = 0;
		this.nextTask = null;
		this.nbPoints = 0;
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

	public LocalDateTime getNextDate() {
		return nextDate;
	}

	public void setNextDate(LocalDateTime nextDate) {
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


	public Rule getRule() {
		return rule;
	}


	public void setRule(Rule rule) {
		this.rule = rule;
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

}
