package com.hejinwei.diary.service;

@SuppressWarnings("rawtypes")
public interface MemcachedService {
	boolean delete(String key);

	void set(String key, Object object);

	Object get(String key);

	boolean deleteWithType(String key, Class clazz);

	void setWithType(String key, Object object);

	void setWithType(String key, Object object, Class clazz);

	Object getWithType(String key, Class clazz);

	Object[] getWithType(String keys[], Class clazz);

	void setWithType(String keys[], Class clazz, Object objects[]);
}
