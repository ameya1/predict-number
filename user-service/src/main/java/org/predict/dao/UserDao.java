package org.predict.dao;

import lombok.extern.log4j.Log4j2;
import org.data.model.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Log4j2
@EnableTransactionManagement
public class UserDao {

    @Autowired
    private Session session;

    @Transactional
    public void save(User user) {
        session.save(user);
    }
}
