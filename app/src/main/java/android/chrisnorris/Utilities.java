// Utilities.java - utilities functions used through application
// version 1.0b
// Christopher D. Norris (cnorris@wgu.edu)
// Western Governors University
// Student ID: 000493268
//
// 2/10/2023 - initial development

package android.chrisnorris;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {
    // helper function to switch between activities (no data sent between activities)
    public static void switchActivity(Context context, Class newActivity) {
        Intent intent = new Intent(context, newActivity);
        context.startActivity(intent);
    }

    // helper function to switch between activities sending data elements
    // form_mode : string => often used to send modes like add/update etc
    // value : integer => often used to send term_id, course_id etc
    public static void switchActivityWithValue(Context context, Class newActivity, String form_mode, int value) {
        Intent intent = new Intent(context, newActivity);
        intent.putExtra("form_mode", form_mode);
        intent.putExtra("value", value);
        context.startActivity(intent);
    }

    // helper function to convert date to string format
    public static String dateAsString(Date date) {
        String formatted_date = "";
        try {
            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            formatted_date = date_format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatted_date;
    }

    // helper function to convert string ot date
    public static Date dateFromString(String stringDate) {
        Date date = null;
        try {
            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            date = date_format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // generates header row
    public static LinearLayout build_header_row(Context context, String header_title) {
        // header row
        LinearLayout header_row = new LinearLayout(context);
        header_row.setGravity(Gravity.CENTER);
        header_row.setOrientation(LinearLayout.VERTICAL);
        header_row.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_700));

        // header text
        TextView header_text = new TextView(context);
        header_text.setTextSize(25);
        header_text.setGravity(Gravity.CENTER);
        header_text.setText(header_title);
        header_text.setTextColor(ContextCompat.getColor(context, R.color.white));

        // add header text to header row
        header_row.addView(header_text);

        return header_row;
    }

    // get screen width
    public static int get_screen_width(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
