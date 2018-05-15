package com.example.common;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.Endpoint;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomizeEndPoint extends AbstractEndpoint implements Endpoint {

    private EntityManager em;

    public CustomizeEndPoint(EntityManager em) {
        super("myentitys",false);
        this.em = em;
    }

    public Object invoke() {
        HashMap<String, Map<String,Object>> stringStringHashMap = new HashMap<>();
        Set<EntityType<?>> entities = em.getMetamodel().getEntities();
        for (EntityType<?> e : entities){
            System.out.println(e.getName());
            Field[] declaredFields = e.getJavaType().getDeclaredFields();
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            for (Field field: declaredFields){
                stringObjectHashMap.put( field.getName(), field.getType());
//                // 读取@Column注解内容
//                Column annotation = field.getAnnotation(Column.class);
//                if(annotation != null){
//                    stringObjectHashMap.put(annotation.name(), annotation.columnDefinition());
//                }
            }
            stringStringHashMap.put(e.getName(), stringObjectHashMap);
        }
        return stringStringHashMap;
    }
}
