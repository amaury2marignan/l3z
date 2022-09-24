package fr.l3z.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity 
@Table
public class Vote {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne
	private SkillProfile skillProfile;
	
	private Boolean openToVote;

	public Vote() {
		super();
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


	public Boolean getOpenToVote() {
		return openToVote;
	}

	public void setOpenToVote(Boolean openToVote) {
		this.openToVote = openToVote;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vote [id=");
		builder.append(id);
		builder.append(", skillProfile=");
		builder.append(skillProfile);
		builder.append(", openToVote=");
		builder.append(openToVote);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	

}
