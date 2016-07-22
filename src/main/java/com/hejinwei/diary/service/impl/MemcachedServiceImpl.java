package com.hejinwei.diary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danga.MemCached.MemCachedClient;
import com.hejinwei.diary.service.MemcachedService;

@Service
@SuppressWarnings("rawtypes")
public class MemcachedServiceImpl implements MemcachedService {

	@Autowired
	private MemCachedClient memCachedClient;

	/*public void setMemCachedClient(MemCachedClient memCachedClient) {
		this.memCachedClient = memCachedClient;

		// 序列化
		if (this.memCachedClient != null) {
			this.memCachedClient.setPrimitiveAsString(true);
		}
	}*/

	public boolean delete(String key) {
		return memCachedClient.delete(key);
	}

	public void set(String key, Object object) {
		if (object != null)
			//memCachedClient.set(key, object, new Date(System.currentTimeMillis() + 1*60*60*1000L));
			memCachedClient.set(key, object);
		else
			memCachedClient.delete(key);
	}

	public Object get(String key) {
		return memCachedClient.get(key);
	}

	public boolean deleteWithType(String key, Class clazz) {
		return memCachedClient.delete(getKeyWithType(key, clazz));
	}
	
	public Object getWithType(String key, Class clazz) {
		return memCachedClient.get(getKeyWithType(key, clazz));
	}

	public void setWithType(String key, Object object) {
		memCachedClient.set(getKeyWithType(key, object.getClass()), object);
	}

	public void setWithType(String key, Object object, Class clazz) {
		if (object != null)
			memCachedClient.set(getKeyWithType(key, clazz), object);
		else
			memCachedClient.delete(getKeyWithType(key, clazz));
	}

	protected String getKeyWithType(String key, Class clazz) {
		return clazz.getSimpleName() + "-" + key;
	}

	@Override
	public Object[] getWithType(String[] keys, Class clazz) {
		for (int i = 0; i < keys.length; i++) {
			keys[i] = getKeyWithType(keys[i], clazz);
		}
		try {
			return memCachedClient.getMultiArray(keys);
		} catch (Exception e) {
		}
		return new Object[0];
	}

	@Override
	public void setWithType(String[] keys, Class clazz, Object[] objects) {
		for (int i = 0; i < keys.length; i++) {
			memCachedClient.set(getKeyWithType(keys[i], clazz), objects[i]);
		}
	}

}
