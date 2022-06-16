package hska.iwi.eShopMaster.model.api;

import java.util.List;

import hska.iwi.eShopMaster.model.database.dataobjects.Category;


public interface CategoryApiConnector {

    List<Category> getObjectList();

    Category getObjectById(int id);

    Category getObjectByName(String name);

    void saveObject(Category cat);

    void deleteById(int id);
    
}
