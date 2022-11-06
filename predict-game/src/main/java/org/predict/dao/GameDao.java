package org.predict.dao;

import lombok.extern.log4j.Log4j2;
import org.data.dao.RedisDao;
import org.data.model.predict.entity.PredictGame;
import org.data.model.request.PredictGameRequest;
import org.data.model.user.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class GameDao {

    @Autowired
    private RedisDao<PredictGame> gameCacheDao;

    @Autowired
    private RedisDao<User> userRedisDao;

    @Autowired
    private Session session;

    public void cacheSave(PredictGame predictGame) {
        /*if(!predictGame.getGameAttempts().isEmpty())
            predictGame.getGameAttempts().forEach(gameAttempt -> gameAttempt.setPredictGame(null));*/
        gameCacheDao.save(PredictGame.KEY + predictGame.getGameId(), "$", predictGame);
    }

    public User user(PredictGameRequest predictGameRequest) {
        User user = userRedisDao.get(User.KEY + predictGameRequest.getUserId(), User.class);
        if(user != null)
            return user;
        return session.find(User.class, predictGameRequest.getUserSerialId());
    }
}
