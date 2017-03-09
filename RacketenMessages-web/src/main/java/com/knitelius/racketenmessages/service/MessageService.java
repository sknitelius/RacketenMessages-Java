package com.knitelius.racketenmessages.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.knitelius.racketenmessages.model.Message;

@Stateless
public class MessageService {

    @PersistenceContext(unitName = "message-pu")
    private EntityManager em;
    
    public List<Message> loadAll() {
    	TypedQuery<Message> findAll = em.createQuery("SELECT e FROM Message e", Message.class);
    	return findAll.getResultList();
    }
    
    public Message find(Long id) {
    	return em.find(Message.class, id);
    }
    
    public Message insert(Message message) {
    	em.persist(message);
    	return message;
    }
    
    public Message update(Message message) {
    	return em.merge(message);
    }
}
