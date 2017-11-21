package com.tony.admindashboard.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.tony.admindashboard.models.Subscription;

import com.tony.admindashboard.models.User;
import com.tony.admindashboard.models.Role;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    Subscription findByName(String subscribe);

    public List<Subscription> findAll();
    
 

}
