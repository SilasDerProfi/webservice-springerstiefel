package hska.iwi.eShopMaster.model.api;

import java.util.List;

import hska.iwi.eShopMaster.model.database.dataobjects.Product;

public interface ProductApiConnector {

    List<Product> getObjectList();

    List<Product> getProductListByCriteria(String searchDescription, Double searchMinPrice, Double searchMaxPrice);

    Product getObjectById(String name);

    void saveObject(Product product);

    Product getObjectById(int id);

    void deleteById(int id);

    boolean deleteProductsByCategoryId(int categoryId);
    
}
