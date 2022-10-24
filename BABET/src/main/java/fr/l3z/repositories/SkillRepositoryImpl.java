package fr.l3z.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.l3z.models.Family;
import fr.l3z.models.Skill;
import fr.l3z.models.SkillNote;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.User;

@ApplicationScoped
@Stateless
public class SkillRepositoryImpl implements SkillRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public SkillRepositoryImpl() {
		
	}
	
	@Override
	public Skill save(Skill t) {
		entityManager.persist(t);
		return t;
	}

	@Override
	public Skill find(Long id) {
		return entityManager
				.createQuery("select u from Skill u where u.id = :skillIdParam", Skill.class)
				.setParameter("skillIdParam", id)
				.getSingleResult();
	}

	@Override
	public Skill findByObject(Skill t) {
		Skill skill = entityManager.find(Skill.class, t.getId());
		return skill;
	}

	@Override
	public List<Skill> findAll() {
		return entityManager.createQuery("select u from Skill u", Skill.class).getResultList();
		
	}

	@Override
	public void delete(Long id) {
		entityManager.remove(entityManager.find(Skill.class, id));
		
	}

	@Override
	public void deleteByObject(Skill t) {
		entityManager.remove(entityManager.find(Skill.class, t.getId()));
		
	}

	@Override
	public void update(Long idAModifier, Skill t) {
		Skill skillAModifier=entityManager.find(Skill.class,idAModifier);
		skillAModifier.setName(t.getName());
		skillAModifier.setDescription(t.getDescription());
		skillAModifier.setFamily(t.getFamily());
		skillAModifier.setDescriptionLevel1(t.getDescriptionLevel1());
		skillAModifier.setDescriptionLevel2(t.getDescriptionLevel2());
		skillAModifier.setDescriptionLevel3(t.getDescriptionLevel3());
		skillAModifier.setDescriptionLevel4(t.getDescriptionLevel4());
		skillAModifier.setDescriptionLevel5(t.getDescriptionLevel5());
		
		entityManager.merge(skillAModifier);
		
	}

	@Override
	public int getNbPoints(SkillProfile sp) {
		int nbPoints = 0;
		for(SkillNote s : sp.getSkillNoteList()) {
			nbPoints = s.getScore()+nbPoints;
		}
		return nbPoints;
	}

	@Override
	public List<Skill> findWithFamily(Long id) {
		
		return entityManager
				.createQuery("select u from Skill u where u.family.id = :familyIdParam", Skill.class)
				.setParameter("familyIdParam", id)
				.getResultList();
		
	}

	@Override
	public Skill findByName(String string) {
		return entityManager
				.createQuery("select u from Skill u where u.name = :skillNameParam", Skill.class)
				.setParameter("skillNameParam", string)
				.getSingleResult();
		
	}

	@Override
	public Skill findByNameAndFamily(Long id, String string) {
		return entityManager
				.createQuery("select u from Skill u where u.name = :skillNameParam AND u.family.id= :familyIdParam", Skill.class)
				.setParameter("skillNameParam", string)
				.setParameter("familyIdParam", id)
				.getSingleResult();
	}
 
	

}
