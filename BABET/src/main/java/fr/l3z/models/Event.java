package fr.l3z.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity 
@Table
public class Event {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private User user;
	
	private LocalDateTime date;
	
	@ManyToOne
	private Rule rule;
	
	@ManyToOne
	private Task task;
	
	@ManyToOne
	private Project project;
	
	@ManyToOne
	private Domain domain;
	
	private String action;

	public Event(User user, LocalDateTime date, Rule rule, Task task, Project project, Domain domain, String action) {
		super();
		this.user = user;
		this.date = date;
		this.rule = rule;
		this.task = task;
		this.project = project;
		this.domain = domain;
		this.action = action;
	}

	public Event() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Event [id=");
		builder.append(id);
		builder.append(", user=");
		builder.append(user);
		builder.append(", date=");
		builder.append(date);
		builder.append(", action=");
		builder.append(action);
		builder.append("]");
		return builder.toString();
	}


	
}
