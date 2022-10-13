package fr.l3z.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class SkillProfile {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<SkillNote> skillNoteList = new ArrayList<SkillNote>();

	public SkillProfile(List<SkillNote> skillNoteList) {
		super();
		this.skillNoteList = skillNoteList;
	}

	public SkillProfile() {
		super();
	}

	public List<SkillNote> getSkillNoteList() {
		return skillNoteList;
	}

	public void setSkillNoteList(List<SkillNote> skillNoteList) {
		this.skillNoteList = skillNoteList;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SkillProfile [id=");
		builder.append(id);
		builder.append(", skillNoteList=");
		builder.append(skillNoteList);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
