package in.kuari.trackall.bean;

/**
 * Created by sultan mirza on 3/7/16.
 */
public class UserProfile {
    private static String displayName;
    private static String imageURL;

    public static String getDisplayName() {
        return displayName;
    }

    public static void setDisplayName(String displayName) {
        UserProfile.displayName = displayName;
    }

    public static String getImageURL() {
        return imageURL;
    }

    public static void setImageURL(String imageURL) {
        UserProfile.imageURL = imageURL;
    }
}