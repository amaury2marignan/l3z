package fr.l3z.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("babetdb");
	        this.entityManager = emf.createEntityManager();
	}
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		
	}
	
	@Override
	public SkillProfile save(SkillProfile t) {
		entityManager.getTransaction().begin();
		entityManager.persist(t);
		entityManager.getTransaction().commit();
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
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(SkillProfile.class, id));
		entityManager.getTransaction().commit();
	}

	@Override
	public void deleteByObject(SkillProfile t) {
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(SkillProfile.class, t.getId()));
		entityManager.getTransaction().commit();
	}

	@Override
	public void update(Long idAModifier, SkillProfile t) {
		entityManager.getTransaction().begin();
		SkillProfile skillProfileAModifier=entityManager.find(SkillProfile.class,idAModifier);
		skillProfileAModifier.setSkillNoteList(t.getSkillNoteList());
		
		entityManager.merge(skillProfileAModifier);
		entityManager.getTransaction().commit();
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
		System.out.println("getScore - skillID : "+skillId+" - SPtoCheck : "+skillProfileToCheck.getSkillNoteList());
		for (SkillNote sn: skillProfileToCheck.getSkillNoteList()) {
			System.out.println(sn);
			System.out.println(skillId);
			System.out.println(sn.getSkill().getId());
			if (sn.getSkill().getId().equals(skillId)){
				return sn.getScore();
			}
		}
		return 0;
	}

	@Override
	public SkillProfile setSkillScore(Long skillId, SkillProfile skillProfileToUpdate, int score) {
	
		
		if (this.isThisSkillIn(skillId,skillProfileToUpdate)) {
			for (SkillNote sn: skillProfileToUpdate.getSkillNoteList()) {
				if (sn.getSkill().getId()==skillId){
					sn.setScore(score);
				}
			}
		} else {
			skillProfileToUpdate.getSkillNoteList().add(new SkillNote(skillRep.find(skillId),score));
		}
		
		return skillProfileToUpdate;
	}
	

}
