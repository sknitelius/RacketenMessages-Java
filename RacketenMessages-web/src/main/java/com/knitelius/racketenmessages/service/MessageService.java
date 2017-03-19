/*
 * The MIT License
 *
 * Copyright 2017 Stephan Knitelius.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
