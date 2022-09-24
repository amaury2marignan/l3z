package fr.l3z.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity 
@Table
public class SkillNote {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne
	private Skill skill;
	private int score;
	
		
	public SkillNote(Skill skill, int score) {
		super();
		this.skill = skill;
		this.score = score;
		
	}


	public SkillNote() {
		super();
	}


	public Skill getSkill() {
		return skill;
	}


	public void setSkill(Skill skill) {
		this.skill = skill;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public Long getId() {
		return id;
	}
	



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SkillNote [id=");
		builder.append(id);
		builder.append(", skill=");
		builder.append(skill);
		builder.append(", score=");
		builder.append(score);

		builder.append("]");
		
		return builder.toString();
	}

	
	
}
