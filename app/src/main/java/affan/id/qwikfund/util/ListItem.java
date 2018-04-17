package affan.id.qwikfund.util;

import android.graphics.Bitmap;

/**
 * Created by Herfanda on 10/3/2017 AD.
 */

public class ListItem {

    private String name;
    private String url;
    private Bitmap image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
