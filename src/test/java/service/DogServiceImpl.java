package service;

import domain.Dog;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DogServiceImpl {

    @PersistenceContext
    EntityManager entityManager;

    public Dog groom(Dog dog) {
        return entityManager.merge(dog);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
