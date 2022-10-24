package fr.l3z.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.l3z.models.Event;
import fr.l3z.models.Family;
import fr.l3z.models.Purchase;
import fr.l3z.models.Skill;
import fr.l3z.models.SkillNote;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.SkillVote;
import fr.l3z.models.Task;
import fr.l3z.models.User;
import fr.l3z.models.Vote;

@ApplicationScoped
@Stateless
public class FamilyRepositoryImpl implements FamilyRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public FamilyRepositoryImpl() {
		
	}
	
	@Override
	public Family save(Family t) {
		entityManager.persist(t);
		return t;
	}

	@Override
	public Family find(Long id) {
		return entityManager
				.createQuery("select u from Family u where u.id = :familyIdParam", Family.class)
				.setParameter("familyIdParam", id)
				.getSingleResult();
	}

	@Override
	public Family findByObject(Family t) {
		Family family = entityManager.find(Family.class, t.getId());
		return family;
	}

	@Override
	public List<Family> findAll() {
		return entityManager.createQuery("select u from Family u", Family.class).getResultList();
		
	}

	@Override
	public void delete(Long id) {
		System.out.println("delete action begins");
		List<Event> eventList = entityManager
				.createQuery("select u from Event u where u.family.id = :familyParam", Event.class)
				.setParameter("familyParam", id)
				.getResultList();
		for(Event e:eventList) {
			entityManager.remove(entityManager.find(Event.class, e.getId()));
		}
		System.out.println("remove events done");
		
		
		List<Purchase> purchaseList = entityManager
				.createQuery("select u from Purchase u where u.family.id = :familyParam", Purchase.class)
				.setParameter("familyParam", id)
				.getResultList();
		for(Purchase t:purchaseList) {
			entityManager.remove(entityManager.find(Purchase.class, t.getId()));
		}
		System.out.println("remove purchase done");
		
		List<Task> taskList = entityManager
				.createQuery("select u from Task u where u.family.id = :familyParam", Task.class)
				.setParameter("familyParam", id)
				.getResultList();
		for(Task t:taskList) {
			t.setWhoDidIt(null);
			entityManager.merge(t);
			System.out.println(t);
			for(SkillNote sN:t.getSkillProfileMinimumToCheck().getSkillNoteList()) {
				entityManager.remove(entityManager.find(SkillNote.class, sN.getId()));
			}
			for(SkillNote sN:t.getSkillProfileMinimumToDo().getSkillNoteList()) {
				entityManager.remove(entityManager.find(SkillNote.class, sN.getId()));
			}
			entityManager.remove(entityManager.find(SkillProfile.class, t.getSkillProfileMinimumToCheck().getId()));
			entityManager.remove(entityManager.find(SkillProfile.class, t.getSkillProfileMinimumToDo().getId()));
			entityManager.remove(entityManager.find(Task.class, t.getId()));
		}
		System.out.println("remove tasks done");
		
		List<Vote> voteList = entityManager
				.createQuery("select u from Vote u where u.family.id = :familyParam", Vote.class)
				.setParameter("familyParam", id)
				.getResultList();
		for(Vote t:voteList) {
			entityManager.remove(entityManager.find(Vote.class, t.getId()));
		}
		System.out.println("remove vote done");
		
		List<SkillVote> skillVoteList = 
				entityManager
				.createQuery("select sV from SkillVote sV where sV.family.id= :familyParam", SkillVote.class)
				.setParameter("familyParam", id)
				.getResultList();
		for(SkillVote sV:skillVoteList) {
			entityManager.remove(entityManager.find(SkillVote.class, sV.getId()));
			
		}
		
		System.out.println("remove skillvote done");
		
		Family f = entityManager.find(Family.class,id);
		List<User> userList = entityManager
				.createQuery("select u from User u where u.family =:familyParam", User.class)
				.setParameter("familyParam", f)
				.getResultList();
		
		
		for(User u:userList) {
			for(SkillNote sN:u.getSkillProfile().getSkillNoteList()) {
				entityManager.remove(entityManager.find(SkillNote.class, sN.getId()));
			}
			entityManager.remove(entityManager.find(SkillProfile.class, u.getSkillProfile().getId()));
			entityManager.remove(entityManager.find(User.class, u.getId()));
			}
		
		System.out.println("remove users done");
		
		List<Skill> skillList = entityManager
				.createQuery("select u from Skill u where u.family.id = :familyParam", Skill.class)
				.setParameter("familyParam", id)
				.getResultList();
		for(Skill s:skillList) {
			List<SkillNote> skillNoteList = entityManager
					.createQuery("select u from SkillNote u where u.skill.id = :familyParam", SkillNote.class)
					.setParameter("familyParam", s.getId())
					.getResultList();
			for(SkillNote sN:skillNoteList) {
				
				entityManager.remove(entityManager.find(SkillNote.class, sN.getId()));
			}
			
			entityManager.remove(entityManager.find(Skill.class, s.getId()));
		}
		
		System.out.println("remove skills done");
		
		entityManager.remove(entityManager.find(Family.class, id));
		
	}

	@Override
	public void deleteByObject(Family t) {
		entityManager.remove(entityManager.find(Family.class, t.getId()));
		
	}

	@Override
	public void update(Long idAModifier, Family t) {
		Family familyAModifier=entityManager.find(Family.class,idAModifier);
		familyAModifier.setFamilyName(t.getFamilyName());
		familyAModifier.setPassword(t.getPassword());
		entityManager.merge(familyAModifier);
		
	}

	@Override
	public Family findByName(String name) {
		return entityManager
				.createQuery("select u from Family u where u.familyName = :familynameParam", Family.class)
				.setParameter("familynameParam", name)
				.getSingleResult();
	}

	@Override
	public void setNewUser(Long userId) {
		// TODO Auto-generated method stub
		
	}

}
