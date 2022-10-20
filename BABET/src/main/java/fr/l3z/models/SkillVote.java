package fr.l3z.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity 
@Table
public class SkillVote {
	
	@Id
	@GeneratedValue
	private Long id;

	
	@ManyToOne
	private User whoAsked;
	
	@ManyToOne
	private User whoValidate;
	
	@ManyToOne
	private Skill skill;
	
	private int status=0;

	public SkillVote(User whoAsked, Skill skill) {
		super();
		this.whoAsked = whoAsked;
		this.skill = skill;
	}

	public SkillVote() {
		super();
	}

	public User getWhoAsked() {
		return whoAsked;
	}

	public void setWhoAsked(User whoAsked) {
		this.whoAsked = whoAsked;
	}

	public User getWhoValidate() {
		return whoValidate;
	}

	public void setWhoValidate(User whoValidate) {
		this.whoValidate = whoValidate;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Long getId() {
		return id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	
	
	
}