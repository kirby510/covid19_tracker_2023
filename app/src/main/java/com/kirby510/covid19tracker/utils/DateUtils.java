package com.kirby510.covid19tracker.utils;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DateUtils {
    private Context mContext;
    private static DateUtils instance = null;

    DateUtils(Context context) {
        this.mContext = context;
    }

    public static DateUtils getInstance(Context context) {
        if (instance == null) {
            instance = new DateUtils(context);
        } else {
            instance.mContext = context;
        }

        return instance;
    }

    public String getDateFromString(String dateString, String fromFormat, String toFormat) {
        try {
            if (fromFormat == null) {
                fromFormat = "yyyy-MM-dd'T'HH:mm:ss.SS'Z'";
            }

            if (toFormat == null) {
                toFormat = "dd MMM yyyy, HH:mm:ss a";
            }

            SimpleDateFormat fromSimpleDateFormat = new SimpleDateFormat(fromFormat);
            SimpleDateFormat toSimpleDateFormat = new SimpleDateFormat(toFormat);

            return toSimpleDateFormat.format(Objects.requireNonNull(fromSimpleDateFormat.parse(dateString)));
        } catch (Exception e) {
            e.printStackTrace();

            return dateString;
        }
    }
}
