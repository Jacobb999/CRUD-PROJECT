package com.java.fx;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DBService{

    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> getNewEmployeesByDate() {
        String sql = "SELECT DATE(created_at) AS date, COUNT(id) AS employee_count " +
                "FROM model GROUP BY DATE(created_at) ORDER BY DATE(created_at) ASC LIMIT 7";
        return entityManager.createNativeQuery(sql).getResultList();
    }
}
