package fr.l3z.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.l3z.models.Family;
import fr.l3z.models.SkillNote;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.User;

@ApplicationScoped
@Stateless
public class SkillProfileRepositoryImpl implements SkillProfileRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Inject
	private SkillRepository skillRep;
	
	public SkillProfileRepositoryImpl() {
		
	}
	
	@Override
	public SkillProfile save(SkillProfile t) {
		entityManager.persist(t);
		return t;
	}

	@Override
	public SkillProfile find(Long id) {
		return entityManager
				.createQuery("select u from SkillProfile u where u.id = :skillProfileIdParam", SkillProfile.class)
				.setParameter("skillProfileIdParam", id)
				.getSingleResult();
	}

	@Override
	public SkillProfile findByObject(SkillProfile t) {
		SkillProfile skillProfile = entityManager.find(SkillProfile.class, t.getId());
		return skillProfile;
	}

	@Override
	public List<SkillProfile> findAll() {
		return entityManager.createQuery("select u from SkillProfile u", SkillProfile.class).getResultList();
		
	}

	@Override
	public void delete(Long id) {
		entityManager.remove(entityManager.find(SkillProfile.class, id));
		
	}

	@Override
	public void deleteByObject(SkillProfile t) {
		entityManager.remove(entityManager.find(SkillProfile.class, t.getId()));
		
	}

	@Override
	public void update(Long idAModifier, SkillProfile t) {
		SkillProfile skillProfileAModifier=entityManager.find(SkillProfile.class,idAModifier);
		skillProfileAModifier.setSkillNoteList(t.getSkillNoteList());
		
		entityManager.merge(skillProfileAModifier);
		
	}
	@Override
	public boolean isThisSkillIn(Long skillId,SkillProfile skillProfileToCheck) {
		for (SkillNote sn : skillProfileToCheck.getSkillNoteList()) {
			if (sn.getSkill().getId()==skillId){
				return true;
			}
			
		}
		
		return false;
	}

	@Override
	public int getScore(Long skillId, SkillProfile skillProfileToCheck) {
		for (SkillNote sn: skillProfileToCheck.getSkillNoteList()) {
			if (sn.getSkill().getId()==skillId){
				return sn.getScore();
			}
		}
		return 0;
	}

	@Override
	public void setSkillScore(Long skillId, SkillProfile skillProfileToUpdate, int score) {
	
		
		if (this.isThisSkillIn(skillId,skillProfileToUpdate)) {
			for (SkillNote sn: skillProfileToUpdate.getSkillNoteList()) {
				if (sn.getSkill().getId()==skillId){
					sn.setScore(score);
				}
			}
		} else {
			skillProfileToUpdate.getSkillNoteList().add(new SkillNote(skillRep.find(skillId),score));
		}
		
	}
	

}
