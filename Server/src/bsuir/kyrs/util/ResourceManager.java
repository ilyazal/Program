package bsuir.kyrs.util;

import java.util.ResourceBundle;


public class ResourceManager {
    private ResourceBundle bundle;

    public ResourceManager(String filePath){
        bundle = ResourceBundle.getBundle(filePath);
    }

    public String getValue(String key){
        return bundle.getString(key);
    }
}
