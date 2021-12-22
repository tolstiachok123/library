package com.academia.andruhovich.library.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateHelper {

    public static final String CURRENT_LOCATION = "Europe/Minsk";

    public static ZonedDateTime currentDate() {
        return ZonedDateTime.now(ZoneId.of(CURRENT_LOCATION));
    }
}
