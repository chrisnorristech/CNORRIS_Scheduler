package android.chrisnorris;

import android.content.Context;
import android.content.Intent;

public class Utilities {
    public static void switchActivity(Context context, Class newActivity) {
        Intent intent = new Intent(context, newActivity);
        context.startActivity(intent);
    }
}
