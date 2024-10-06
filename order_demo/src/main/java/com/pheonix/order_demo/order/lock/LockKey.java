package com.pheonix.order_demo.order.lock;

import java.util.Objects;

public class LockKey {

    private static final String PREFIX = "LOCK::";

    private Long eventId;

    private LockKey(Long eventId) {
        if (Objects.isNull(eventId)) {
            throw new IllegalArgumentException("eventId can't be null");
        }
        this.eventId = eventId;
    }

    public static LockKey from(Long eventId) {
        return new LockKey(eventId);
    }

    public static LockKey fromString(String key) {
        String idToken = key.substring(0, PREFIX.length());
        Long eventId = Long.valueOf(idToken);
        return LockKey.from(eventId);
    }

    @Override
    public String toString() {
        return new StringBuilder(PREFIX).append(eventId).toString();
    }
}
