package affan.id.qwikfund.util;

/**
 * Created by Herfanda on 10/6/2017 AD.
 */

public class StringWithTag {

    public String string;
    public Object tag;

    public StringWithTag(String string, Object tag) {
        this.string = string;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return string;
    }
}
