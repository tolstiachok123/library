package com.academia.andruhovich.library.util;

import org.mapstruct.Named;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateHelper {

    public static final String CURRENT_LOCATION = "Europe/Minsk";

    @Named("getCurrentDate")
    public static ZonedDateTime currentDate() {
        return ZonedDateTime.now(ZoneId.of(CURRENT_LOCATION));
    }
}
