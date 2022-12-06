package com.zzh.common;

import com.zzh.entity.OnlineUser;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author zzh
 * @description 存储在线对象的集合
 * @date 2022/2/5 20:41
 */
@Component
public class OnlineUserMap extends HashMap<String, OnlineUser> {

}
