package com.business.common.other.cache;

import com.business.common.other.map.ManagedConcurrentWeakHashMap;
import com.business.pojo.dto.user.UserDTO;
import org.apache.commons.lang3.StringUtils;
import java.util.Map;

/**
 * NettyCache
 * Created by yuTong on 2018/03/16.
 */
public class CachePool {
    private volatile static ManagedConcurrentWeakHashMap<String, UserDTO> map = new ManagedConcurrentWeakHashMap<>();
    private CachePool() {
    }

    public static CachePool getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    public void put(String userName, UserDTO userDTO) {
        if (null != userDTO && StringUtils.isNotEmpty(userName)) {
            map.put(userName, userDTO);
        }
    }
    public UserDTO get(String clientId) {
        return map.get(clientId);
    }

    public Map<String, UserDTO> getAllMap() {
        return map;
    }

    public void remove(UserDTO userDTO) {
        String clientId = null;
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getValue().equals(userDTO)) {
                clientId = (String) entry.getKey();
                break;
            }
        }
        if (null != clientId) {
            remove(clientId);
        }
    }

    private void remove(String clientId) {
        map.remove(clientId);
        map.maintain();
    }

    private enum Singleton {
        INSTANCE;
        private CachePool cachePool;

        Singleton() {
            cachePool = new CachePool();
        }

        public CachePool getInstance() {
            return cachePool;
        }
    }
}
