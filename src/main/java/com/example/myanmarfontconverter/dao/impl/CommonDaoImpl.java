package com.example.myanmarfontconverter.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.myanmarfontconverter.config.JsonPayloadApi;
import com.example.myanmarfontconverter.dao.CommonDao;

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CommonDaoImpl implements CommonDao {

	@Autowired
	private EntityManager entityManager;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	@Override
	public Map<String, Object> delete(Object instance) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Session session = getSession();
		session.delete(instance);
		map.put(JsonPayloadApi.RESULT_KEY, JsonPayloadApi.DELETE_SUCCESS_RESPONSE);
		return map;
	}

	@Override
	public Object get(Class<?> clz, Serializable id) throws Exception {
		Session session = getSession();
		return session.get(clz, id);
	}

	@Override
	public Map<String, Object> save(Object instance) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Session session = getSession();
		session.save(instance);
		map.put(JsonPayloadApi.RESULT_KEY, instance);
		return map;
	}

	@Override
	public Map<String, Object> merge(Object detachedInstance) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		Session session = getSession();
		session.merge(detachedInstance);
		map.put(JsonPayloadApi.RESULT_KEY, JsonPayloadApi.MERGE_SUCCESS_RESPONSE);
		return map;
	}

	@Override
	public Map<String, Object> update(Object instance) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Session session = getSession();
		session.update(instance);
		map.put(JsonPayloadApi.RESULT_KEY, instance);
		return map;
	}

	@Override
	public List<?> executeSqlQuery(String sql, Map<String, Object> params) throws Exception {
		Session session = getSession();
		Query<?> query = session.createSQLQuery(sql);
		if (params != null && params.keySet().size() > 0) {
			java.util.Iterator<String> newIterator = params.keySet().iterator();
			while (newIterator.hasNext()) {
				String key = newIterator.next();
				if (params.get(key) instanceof java.util.List) {
					query.setParameterList(key, (List<?>) params.get(key));
				} else if (params.get(key) instanceof java.util.Set) {
					query.setParameterList(key, (Set<?>) params.get(key));
				} else {
					query.setParameter(key, params.get(key));
				}

			}
		}
		return query.list();
	}

	@Override
	public Object findFristResult(String hql, Map<String, Object> params) throws Exception {
		Session session = getSession();
		Query<?> query = session.createQuery(hql).setCacheable(true);
		if (params != null) {
			java.util.Iterator<String> newIterator = params.keySet().iterator();
			while (newIterator.hasNext()) {
				String key = newIterator.next();
				if (params.get(key) instanceof java.util.List) {
					query.setParameterList(key, (List<?>) params.get(key));
				} else {
					query.setParameter(key, params.get(key));
				}
			}
		}

		query.setFirstResult(0);
		query.setMaxResults(1);
		List<?> data = query.list();
		if (data != null && data.size() > 0) {
			return data.get(0);
		}
		return null;
	}

	@Override
	public List<?> findAll(String sql, Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean executeSqlUpdate(String sql, Map<String, Object> params) throws Exception {
		Session session = getSession();
		Query<?> query = session.createSQLQuery(sql);
		java.util.Iterator<String> newIterator = params.keySet().iterator();
		while (newIterator.hasNext()) {
			String key = newIterator.next();
			if(params.get(key) instanceof java.util.List){
				query.setParameterList(key, (List<?>) params.get(key));
			}else{
			    query.setParameter(key, params.get(key));
			}
		}

		int flag = query.executeUpdate();
		if (flag == 1) {
			// sessionFactory.getCurrentSession().flush();
			return true;
		} else {
			return false;
		}

	}
}
