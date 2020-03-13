package cz.addai.service;

import cz.addai.components.session.UserSession;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractWatsonService {

    @Autowired
    protected UserSession userSession;
}
