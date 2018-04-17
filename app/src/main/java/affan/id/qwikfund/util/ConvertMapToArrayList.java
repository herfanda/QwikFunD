package affan.id.qwikfund.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Herfanda on 10/6/2017 AD.
 */

public class ConvertMapToArrayList {
        public List<StringWithTag> arrayList;

        public List<StringWithTag> ConvertMapToArrayList(TreeMap<String,String> map){

            arrayList = new ArrayList<>();

            for (Map.Entry<String,String> entry : map.entrySet()){

                String key = entry.getKey();
                String value = entry.getValue();

                arrayList.add(new StringWithTag(value, key));

            }

            return arrayList;

        }

    }




