package cz.addai.service;

import com.ibm.watson.assistant.v2.model.MessageResponse;
import cz.addai.components.session.UserSession;
import cz.addai.persistence.dao.AnswerLogDao;
import cz.addai.watson.RequestResponseToAnswerLogConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.ZonedDateTime;

/**
 * Service to log answer data to database
 */
@Service
public class WatsonAnswerLogService {

    @Resource
    private AnswerLogDao answerLogDao;

    @Resource
    private UserSession userSession;

    @Transactional
    public void logAnswer(ZonedDateTime askedAt, String request, MessageResponse messageResponse) {
        try {
            var answerLog = RequestResponseToAnswerLogConverter.convert(askedAt, userSession.getClientId(), request, messageResponse);
            answerLogDao.create(answerLog);
        } catch(Throwable thr) {
            // Quick and dirty log error in case that something is wrong with transaction
            thr.printStackTrace();
        }
    }
}
