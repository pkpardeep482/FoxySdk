package com.pardeep.foxy_native_module.foxynativemodules.utilities;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utility {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isDatePast(final String dateStr) {
        OffsetDateTime date = OffsetDateTime.parse(dateStr);
        LocalDate dateInLocalZone = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());

        return dateInLocalZone.isBefore(currentDate);
    }
}
