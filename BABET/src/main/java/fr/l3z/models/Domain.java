package fr.l3z.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Domain {

	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private String description;
	
	@ManyToOne
	private Family family;
	
	@OneToOne
	private SkillProfile minimumSkillProfileToUpdate;

	public Domain(String name, String description, Family family, SkillProfile minimumSkillProfileToUpdate) {
		super();
		this.name = name;
		this.description = description;
		this.family = family;
		this.minimumSkillProfileToUpdate = minimumSkillProfileToUpdate;
	}

	public Domain() {
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

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public SkillProfile getMinimumSkillProfileToUpdate() {
		return minimumSkillProfileToUpdate;
	}

	public void setMinimumSkillProfileToUpdate(SkillProfile minimumSkillProfileToUpdate) {
		this.minimumSkillProfileToUpdate = minimumSkillProfileToUpdate;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Domain [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", family=");
		builder.append(family);
		builder.append(", minimumSkillProfileToUpdate=");
		builder.append(minimumSkillProfileToUpdate);
		builder.append("]");
		return builder.toString();
	}

	
}
