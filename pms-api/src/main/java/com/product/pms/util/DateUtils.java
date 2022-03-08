package com.product.pms.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class DateUtils {

    public static OffsetDateTime toOffsetDateTime(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }

    private DateUtils() {

    }

}
