package fr.l3z.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Rule {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private String description;
	
	@OneToMany
	private List<Task> tasksList = new ArrayList<Task>();
	
	@OneToOne
	private SkillProfile skillProfileMinimumToUpdate;

	@ManyToOne
	private Domain domain;

	public Rule(String name, String description, List<Task> tasksList, SkillProfile skillProfileMinimumToUpdate,
			Domain domain) {
		super();
		this.name = name;
		this.description = description;
		this.tasksList = tasksList;
		this.skillProfileMinimumToUpdate = skillProfileMinimumToUpdate;
		this.domain = domain;
	}

	public Rule() {
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

	public List<Task> getTasksList() {
		return tasksList;
	}

	public void setTasksList(List<Task> tasksList) {
		this.tasksList = tasksList;
	}

	public SkillProfile getSkillProfileMinimumToUpdate() {
		return skillProfileMinimumToUpdate;
	}

	public void setSkillProfileMinimumToUpdate(SkillProfile skillProfileMinimumToUpdate) {
		this.skillProfileMinimumToUpdate = skillProfileMinimumToUpdate;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Rule [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", tasksList=");
		builder.append(tasksList);
		builder.append(", skillProfileMinimumToUpdate=");
		builder.append(skillProfileMinimumToUpdate);
		builder.append(", domain=");
		builder.append(domain);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
