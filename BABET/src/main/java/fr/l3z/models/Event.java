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
	private Family family;
	
	@ManyToOne
	private Task task;
	
	@ManyToOne
	private Vote vote;
	
	@ManyToOne
	private Purchase purchase;
	
	@ManyToOne
	private Skill skill;
	
	private String action;



	public Event(Family family, User user, LocalDateTime date, Task task, Vote vote, Purchase purchase, Skill skill, String action) {
		super();
		this.family=family;
		this.user = user;
		this.date = date;
		this.task = task;
		this.vote = vote;
		this.purchase = purchase;
		this.skill = skill;
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



	public Task getTask() {
		return task;
	}



	public void setTask(Task task) {
		this.task = task;
	}



	public Vote getVote() {
		return vote;
	}



	public void setVote(Vote vote) {
		this.vote = vote;
	}



	public Purchase getPurchase() {
		return purchase;
	}



	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
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



	public Family getFamily() {
		return family;
	}



	public void setFamily(Family family) {
		this.family = family;
	}



	public Skill getSkill() {
		return skill;
	}



	public void setSkill(Skill skill) {
		this.skill = skill;
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
		builder.append(", family=");
		builder.append(family);
		builder.append(", task=");
		builder.append(task);
		builder.append(", vote=");
		builder.append(vote);
		builder.append(", purchase=");
		builder.append(purchase);
		builder.append(", skill=");
		builder.append(skill);
		builder.append(", action=");
		builder.append(action);
		builder.append("]");
		return builder.toString();
	}



	

	
	
}