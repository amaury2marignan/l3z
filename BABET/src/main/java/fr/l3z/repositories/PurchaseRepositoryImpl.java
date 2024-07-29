package fr.l3z.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import fr.l3z.models.SkillNote;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.Purchase;

@ApplicationScoped
@Stateless
public class PurchaseRepositoryImpl implements PurchaseRepository {

	
	@PersistenceContext
	private EntityManager entityManager;

	public PurchaseRepositoryImpl() {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("babetdb");
	        this.entityManager = emf.createEntityManager();
	}
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		
	}

	@Override
	public Purchase save(Purchase t) {
		 entityManager.getTransaction().begin();
		entityManager.persist(t);
		entityManager.getTransaction().commit();
		return t;
	}

	@Override
	public Purchase find(Long id) {
		return entityManager.createQuery("select u from Purchase u where u.id = :purchaseIdParam", Purchase.class)
				.setParameter("purchaseIdParam", id).getSingleResult();
	}

	@Override
	public Purchase findByObject(Purchase t) {
		Purchase purchase = entityManager.find(Purchase.class, t.getId());
		return purchase;
	}

	@Override
	public List<Purchase> findAll() {
		return entityManager.createQuery("select u from Purchase u", Purchase.class).getResultList();

	}

	@Override
	public void delete(Long id) {
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(Purchase.class, id));
		entityManager.getTransaction().commit();
	}
	@Override
	public List<Purchase> findByStatus(int status){
		return entityManager.createQuery("select u from Purchase u where u.status = :purchaseIdParam", Purchase.class)
				.setParameter("purchaseIdParam", status).getResultList();
	}
	
	@Override
	public List<Purchase> findPurchasesToDo(Long familyId){
		return entityManager.createQuery("select u from Purchase u where u.status = 1 AND u.family.id = :familyIdParam", Purchase.class)
				.setParameter("familyIdParam",familyId).getResultList();
	}

	@Override
	public void deleteByObject(Purchase t) {
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(Purchase.class, t.getId()));
		entityManager.getTransaction().commit();
	}

	@Override
	public void update(Long idAModifier, Purchase t) {
		entityManager.getTransaction().begin();
		Purchase purchaseAModifier = entityManager.find(Purchase.class, idAModifier);
		purchaseAModifier.setDescription(t.getDescription());
		purchaseAModifier.setStatus(t.getStatus());
		purchaseAModifier.setWhoDidIt(t.getWhoDidIt());


		entityManager.merge(purchaseAModifier);
		entityManager.getTransaction().commit();
	}



	@Override
	public List<Purchase> findModels() {
		
		return entityManager.createQuery("select u from Purchase u where u.status = 0", Purchase.class)
				.getResultList();
	}


}
