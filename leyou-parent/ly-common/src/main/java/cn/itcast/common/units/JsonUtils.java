package cn.itcast.common.units;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: HuYi.Zhang
 * @create: 2018-04-24 17:20
 **/
public class JsonUtils {

    public static final ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 吧对象序列化为字符串
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    public static <T> T toBean(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    public static <E> List<E> toList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

/*
    public static void main(String[] args) {
        // 序列化
//        List<User> list = Arrays.asList(new User("jack", 21), new User("Rose", 18));
//        String json = toString(list);
//        System.out.println("json = " + json);

        // 反序列化
        // String json = "{\"name\":\"jack\",\"age\":21}";
//        User user = toBean(json, User.class);
//        System.out.println("user = " + user);

        // 反序列化为List
//        String json = "[{\"name\":\"jack\",\"age\":21},{\"name\":\"Rose\",\"age\":18}]";
//        List<User> users = toList(json, User.class);
//        System.out.println("users = " + users);

        // 反序列化变态版
        String json = "{\"userList1\": [{\"name\":\"jack\",\"age\":21},{\"name\":\"Rose\",\"age\":18}], \"userList2\":[{\"name\":\"jack\",\"age\":21},{\"name\":\"Rose\",\"age\":18}]}";
        Map<String, List<User>> map = nativeRead(json, new TypeReference<Map<String, List<User>>>() {});
        for (Map.Entry<String, List<User>> entry : map.entrySet()) {
            System.out.println("entry.getKey() = " + entry.getKey());
            for (User user : entry.getValue()) {
                System.out.println("\t user = " + user);
            }
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class User{
        String name;
        int age;
    }*/
}
