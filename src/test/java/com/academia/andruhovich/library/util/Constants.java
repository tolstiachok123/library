package com.academia.andruhovich.library.util;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final Long ID = 1L;

    public static final String FIRST_NAME = "Jules";
    public static final String LAST_NAME = "Verne";

    public static final String TAG_NAME = "Horror";

    public static final String DEFAULT_ROLE_NAME = "USER";

    public static final String TITLE = "Dagon";
    public static final BigDecimal PRICE = new BigDecimal("45.00");
    public static final String IMAGE_URL = "www.google.by";

    public static final String EMAIL = "admin_mock";
    public static final String UNREGISTERED_EMAIL = "new_user";
    public static final String PASSWORD = "12356";
    public static final String ENCRYPTED_PASSWORD = "$2y$12$Ol2wWiJjCsY.u3C7H8u3I.xB9FWRZdRF/qppePPnT75rWKQGSIRbq";

    public static final String HISTORY = "[{\"book\": {\"id\": 1, \"tags\": [{\"id\": 1, \"name\": \"Horror\"}], \"price\": 48.0, \"title\": \"Dagon\", \"author\": {\"id\": 1, \"lastName\": \"Lovecraft\", \"firstName\": \"Hovard Philips\"}, \"imageUrl\": \"url\", \"createdAt\": \"2021-12-15 16:19:52\", \"updatedAt\": \"2021-12-15 16:19:52\"}, \"quantity\": 1}]";

    public static final Map<Long, Integer> ORDER_CONTENT = Collections.singletonMap(1L, 1);
}
