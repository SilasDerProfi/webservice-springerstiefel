package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import hska.iwi.eShopMaster.model.api.CategoryApiConnector;
import hska.iwi.eShopMaster.model.api.impl.CategoryApiConnectorImpl;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryManagerImpl implements CategoryManager{
	
	CategoryApiConnector helper;

	public CategoryManagerImpl() {
		helper = new CategoryApiConnectorImpl();
		
	}

	public List<Category> getCategories() {
		return helper.getObjectList();
	}

	public Category getCategory(int id) {
		return helper.getObjectById(id);
	}

	public Category getCategoryByName(String name) {
		return helper.getObjectByName(name);
	}

	public void addCategory(String name) {
		Category cat = new Category();
		cat.setName(name);
		helper.saveObject(cat);

	}

	public void delCategory(Category cat) {
	
// 		Products are also deleted because of relation in Category.java 
		helper.deleteById(cat.getId());
	}

	public void delCategoryById(int id) {
		
		helper.deleteById(id);
	}
}
