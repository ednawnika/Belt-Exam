package com.tony.admindashboard.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tony.admindashboard.models.User;
import com.tony.admindashboard.models.Subscription;
import com.tony.admindashboard.repositories.SubscriptionRepository;
import com.tony.admindashboard.controllers.UserController;

@Service
public class SubscriptionService {
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> all() {
        return subscriptionRepository.findAll();
    }

    public Subscription findByName(String name) {
        return subscriptionRepository.findByName(name);
    }

    public Subscription create(Subscription subscription) {
       return subscriptionRepository.save(subscription);
    }

    public void update(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public void destroy(long id) {
        subscriptionRepository.delete(id);
    }

    public Subscription getById(long id) {
        return (Subscription) subscriptionRepository.findOne(id);
    }

}
