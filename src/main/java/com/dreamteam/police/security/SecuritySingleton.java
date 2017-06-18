package com.dreamteam.police.security;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by loci on 11-6-17.
 */
@Component
public class SecuritySingleton {

    private Map<String, LocalTime> sessions;

    @PostConstruct
    void init() {
        sessions = new HashMap<>();
    }

    public boolean login(String username, String password, String sessionId) {
        if (username.equals("police") && password.equals("police")) {
            sessions.put(sessionId, LocalTime.now());
            return true;
        }
        return false;
    }

    public boolean isLoggedIn(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    @Scheduled(cron = "0 0 */1 * * *")
    private void clearSessions() {
        for (String key : sessions.keySet()) {
            LocalTime time = sessions.get(key);
            long minutes = ChronoUnit.MINUTES.between(LocalTime.now(), time);
            if (minutes > 60) {
                sessions.remove(key);
            }
        }
    }
}
