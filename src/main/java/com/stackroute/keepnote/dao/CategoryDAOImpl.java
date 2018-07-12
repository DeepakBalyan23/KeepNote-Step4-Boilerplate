package com.stackroute.keepnote.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class CategoryDAOImpl implements CategoryDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	@Autowired
	SessionFactory sessionFactory;

	public CategoryDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new category
	 */
	public boolean createCategory(Category category) {
		sessionFactory.getCurrentSession().save(category);
		sessionFactory.getCurrentSession().flush();
		return true;
	}

	/*
	 * Remove an existing category
	 */
	public boolean deleteCategory(int categoryId) {
		try {
			sessionFactory.getCurrentSession().delete(getCategoryById(categoryId));
			sessionFactory.getCurrentSession().flush();
			return true;
		} catch (CategoryNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	/*
	 * Update an existing category
	 */

	public boolean updateCategory(Category category) {
		try {
			if(getCategoryById(category.getCategoryId())==null) {
				return false;
			} else {
				sessionFactory.getCurrentSession().clear();
				sessionFactory.getCurrentSession().update(category);
				sessionFactory.getCurrentSession().flush();
				return true;
			}
		} catch (CategoryNotFoundException e) {
			e.printStackTrace();
			return false;
		}

	}
	/*
	 * Retrieve details of a specific category
	 */

	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
		Category category = (Category)sessionFactory.getCurrentSession().get(Category.class, categoryId);
		if(category==null)
			throw new CategoryNotFoundException("Category not found");
		sessionFactory.getCurrentSession().flush();
		return category;
	}

	/*
	 * Retrieve details of all categories by userId
	 */
	@SuppressWarnings("unchecked")
	public List<Category> getAllCategoryByUserId(String userId) {
		String hql = "FROM Category where categoryCreatedBy= :userId";
		Query query = sessionFactory.openSession().createQuery(hql).setParameter("userId", userId);
		return query.getResultList();
	}

}
