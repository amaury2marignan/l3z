package fr.l3z.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity 
@Table
public class Project {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne
	private Rule rule;
	
	@OneToOne
	private Vote vote;

	private Boolean open;
	
	public Project(Rule rule) {
		super();
		this.rule = rule;
		this.open = true;
	}
	
	public Project() {
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public Vote getVote() {
		return vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Project [id=");
		builder.append(id);
		builder.append(", rule=");
		builder.append(rule);
		builder.append(", vote=");
		builder.append(vote);
		builder.append(", open=");
		builder.append(open);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	
	
}
