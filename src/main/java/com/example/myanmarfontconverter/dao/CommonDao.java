package com.example.myanmarfontconverter.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface CommonDao {

	Map<String, Object> delete(Object instance) throws Exception;

	Object get(Class<?> clz, Serializable id) throws Exception;

	Map<String, Object> save(Object instance) throws Exception;

	Map<String, Object> merge(Object detachedInstance) throws Exception;

	Map<String, Object> update(Object instance) throws Exception;

	List<?> executeSqlQuery(String hql, Map<String, Object> params) throws Exception;

	Object findFristResult(String hql, Map<String, Object> params) throws Exception;

	List<?> findAll(String sql, Map<String, Object> params) throws Exception;

	boolean executeSqlUpdate(String sql, Map<String, Object> params) throws Exception;
}
