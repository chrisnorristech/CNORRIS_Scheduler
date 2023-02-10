package android.chrisnorris;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {
    public static void switchActivity(Context context, Class newActivity) {
        Intent intent = new Intent(context, newActivity);
        context.startActivity(intent);
    }

    public static String dateAsString(Date date) {
        String formatted_date = "";
        try {
            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            formatted_date = date_format.format(date);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return formatted_date;
    }

    public static Date dateFromString(String stringDate) {
        Date date = null;
        try {
            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            date = date_format.parse(stringDate);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String stringRemoveXCharIf(String str, int index, char check) {
        char[] chars = str.toCharArray();
        String outString = "";
        int count = 0;
        for(char c : chars) {
            if(count != index){
                outString += c;
            } else {
                if(c != check) {
                    outString += c;
                }
            }
            count++;
        }
        return outString;
    }
}
