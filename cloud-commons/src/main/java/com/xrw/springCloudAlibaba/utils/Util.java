package com.xrw.springCloudAlibaba.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.xrw.springCloudAlibaba.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 静态类不能使用@Autowired。
 * @author xearin
 */
@Component
@Slf4j
public class Util {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }


    /**
     * Unicode转UTF-8
     *
     * @param s
     * @return
     */
    public static String decode(String s) {
        String pattern = "\\\\u([0-9a-zA-Z]{4})";
        Pattern reUnicode = Pattern.compile(pattern);
        Matcher m = reUnicode.matcher(s);
        StringBuffer sb = new StringBuffer(s.length());
        while (m.find()) {
            m.appendReplacement(sb,
                    Character.toString((char) Integer.parseInt(m.group(1), 16)));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static String inputStreamToString(InputStream inputStream) {
        /*StringBuilder content = new StringBuilder();
        byte[] b = new byte[1024];
        int lens = -1;
        try {
            while ((lens = inputStream.read(b)) > 0) {
                content.append(new String(b, 0, lens));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();// 内容*/

        String str = "";
        try {

            String encode = "utf-8";
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encode));
            StringBuffer sb = new StringBuffer();

            while ((str = reader.readLine()) != null) {
                sb.append(str).append("\n");
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;

    }

    /**
     * XML转Bean
     */
    public static <T> T xmlToBean(Element rootElement, Class<T> clazz) {
        T object = null;
        try {
            Constructor<?> constructor = clazz.getConstructor();
            object = (T) constructor.newInstance();
            List<Element> elements = rootElement.elements();
            for (Element element :
                    elements) {
                Method method = clazz.getMethod("set" + element.getName());
                method.invoke(object, element.getText());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 判断某个方法是否具有制定类型的参数
     *
     * @return
     */
    public static boolean ifMethodHasSpecifiedParameterType(Method method, String specifiedClassName) {
        Class[] clzes = method.getParameterTypes();
        for (Class clz :
                clzes) {
            if (clz.getName().contains(specifiedClassName)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static <T> T JsonToModel(Class<T> clazz, JSONObject json) {
        try {
            T t = (T) clazz.getConstructor().newInstance();
            Method[] methods = clazz.getMethods();
            for (Method method :
                    methods) {
                String methodName = method.getName();
                if (!methodName.startsWith("set") || methodName.startsWith("setSys")) {
                    continue;
                }
                String pattern = "set(i|Vc|Ti|Txt|Dt)";
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(methodName);
                String key = m.replaceFirst("");
                key = key.substring(0, 1).toLowerCase() + key.substring(1);
                String val = (String) json.get(key);
                if (val != null) {
                    Class<?> paramClass = method.getParameterTypes()[0];
                    if (paramClass.equals(String.class) || paramClass.equals(int.class)) {
                        method.invoke(t, val);
                    } else if (paramClass.equals(byte.class)) {
                        Byte b = Byte.valueOf(val);
                        method.invoke(t, b);
                    } else if (paramClass.equals(Timestamp.class)) {
                        Date date = null;
                        try {
                            date = new SimpleDateFormat("yyyy-MM-dd").parse(val);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Timestamp ts = new Timestamp(date.getTime());
                        method.invoke(t, ts);
                    } else {
                        Method valueOfMethod = paramClass.getMethod("valueOf", String.class);
                        Object param = valueOfMethod.invoke(null, val);
                        method.invoke(t, param);
                    }
                }
            }
            return t;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * byte数组转字符串
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString();
    }

    /**
     * HMAC-SHA256加密
     *
     * @param content
     * @param secret
     * @return
     */
    public static String hs256Encrypt(String content, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hs256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        hs256.init(secretKeySpec);
        byte[] bytes = hs256.doFinal(content.getBytes());
        return byteToHexString(bytes);
    }


    /**
     * 字符串转对象
     *
     * @param str
     * @param targetClass
     * @return
     */
    public static Object stringToObject(String str, Class targetClass, Type genericParameterType) {
        if (str == null) {
            return null;
        } else {
            try{
                switch (targetClass.getSimpleName()) {
                    case "Integer":
                        return "".equals(str) ? null : Integer.valueOf(str);
                    case "Date":
                        return DateUtil.parseDate(str);
                    case "Timestamp":
                        return DateUtil.parseTimestamp(str);
                    case "JSONObject":
                        return JSONObject.parseObject(str);
                    case "JSONArray":
                        return JSONArray.parseArray(str);
                    case "String[]":
                        List<String> strs = JSONArray.parseArray(str, String.class);
                        return strs.toArray(new String[strs.size()]);
                    case "Integer[]":
                        List<Integer> integers = JSONArray.parseArray(str, Integer.class);
                        return integers.toArray(new Integer[integers.size()]);
                    case "List":
                        if (genericParameterType != null){
                            Type[] types = ((ParameterizedTypeImpl) genericParameterType).getActualTypeArguments();
                            if (types != null && types.length > 0){
                                return JSONArray.parseArray(str).toJavaList((Class<Object>)types[0]);
                            }else{
                                return JSONArray.parseArray(str).toJavaList(Object.class);
                            }
                        }else{
                            return JSONArray.parseArray(str).toJavaList(Object.class);
                        }
                    case "Double":
                        return Double.valueOf(str);
                    case "Boolean":
                        return Boolean.valueOf(str);
                    case "Long":
                        return Long.valueOf(str);
                    case "Byte":
                        return Byte.valueOf(str);
                    default:break;
                }
            }catch (Exception ex){
                log.error(ex.getMessage());
                ex.printStackTrace();
                return null;
            }
        }
        return str;
    }

    /**
     * @param name
     * @return
     * @author xearin
     * 后端字段名转前端字段名
     */
    public static String propertyNameTransfer(String name) {
        String patternString = "(i|vc|dt|ti|txt|dl|b|d)([A-Z][a-z0-9]*)*";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(name);
        if (matcher.matches()) {
            name = name.replaceFirst(matcher.group(1), "");
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        return name;
    }

    /**
     * @param str
     * @return
     * @author xearin
     * 首字母大写
     */
    public static String firstLetterUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * @param str
     * @return
     * @author xearin
     * 首字母小写
     */
    public static String firstLetterLowerCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * @param json             如果为null，返回null
     * @param clazz            实体类class
     * @param ignoreProperties 要屏蔽的实体类属性。即使json包含这些属性，生成的实体类的这些属性也不会有值。
     * @return 实体类实例
     * @author xearin
     * 将json转换为实体类。
     * json键要求：该实体类的属性去除类型前缀，之后按驼峰规则。
     * 例如：实体类属性：vcContactorMobile -> json键：contactorMobile
     */
    public static <T> T jsonToEntity(JSONObject json, Class<T> clazz, String... ignoreProperties) {
        if (json == null) {
            return null;
        }
        List<String> ignoreList = new ArrayList<>();
        for (String property :
                ignoreProperties) {
            ignoreList.add("set" + property.toLowerCase());
        }
        try {
            T t = clazz.getConstructor().newInstance();
            Method[] methods = t.getClass().getMethods();
            for (Method method :
                    methods) {
                String methodName = method.getName();
                if (!methodName.startsWith("set") || methodName.startsWith("setSys")) {
                    continue;
                }
                if (ignoreList.contains(methodName.toLowerCase())) {
                    continue;
                }
                String key = methodName.replaceFirst("set(i|Vc|Dt|Ti|Txt|Dl|b|d)?", "");
                if (!(key.length() > 1 && Character.isUpperCase(key.charAt(1)) &&
                        Character.isUpperCase(key.charAt(0)))) {
                    key = key.substring(0, 1).toLowerCase() + key.substring(1);
                }
                Object value = json.get(key);
                if (value != null) {
                    if ("".equals(value.toString())) {
                        value = null;
                    } else {
                        switch (method.getParameterTypes()[0].getSimpleName()) {
                            case "Integer":
                                value = Integer.valueOf(value.toString());
                                break;
                            case "Long":
                                value = Long.valueOf(value.toString());
                                break;
                            case "Date":
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                value = dateFormat.parse(value.toString());
                                value = new java.sql.Date(((Date) value).getTime());
                                break;
                            case "Timestamp":
                                value = DateUtil.parseTimestamp(value.toString());
                                break;
                            case "Double":
                                value = Double.valueOf(value.toString());
                                break;
                            case "Byte":
                                value = Byte.valueOf(value.toString());
                                break;
                            case "JSONArray":
                                value = JSONArray.parseArray(value.toString());
                                break;
                            default:
                                value = String.valueOf(value);
                        }
                    }
                }
                //System.out.println(key + ": " + value);
                method.invoke(t, value);
            }
            return t;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param json
     * @param <T>
     * @return
     * @author xearin
     */
    public static <T> T jsonToEntity(JSONObject json, Class<T> clazz) {
        return jsonToEntity(json, clazz, new String[]{});
    }

    /**
     * json数组转javaList
     * @param jsonArray
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(JSONArray jsonArray, Class<T> clazz) {
        if (jsonArray == null) {
            return null;
        }
        List<T> list = new ArrayList<>(jsonArray.size());
        jsonArray.forEach(i->{
            list.add(jsonToEntity((JSONObject) i, clazz, new String[]{}));
        });
        return list;
    }

    public static String currentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    /**
     * 把从数据库获取DO的page形式转换成需要VO的page形式
     * DO与VO的字段名需要一样
     * Page<DO> ---> Page<VO>
     *
     * @return
     * @author xearin
     * @date 2019/09/30
     */
    public static <T, V> JSONObject pageToVOpage(Page<T> source, Class<V> target) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("totalElements", source.getTotalElements());
        jsonObject.put("totalPages", source.getTotalPages());
        source.stream().forEach(e -> {
            try {
                V object = target.newInstance();
                BeanUtils.copyProperties(e, object);
                jsonArray.add(object);
            } catch (Exception exception) {
                throw new ApiException(500, "数据转换错误");
            }
        });
        jsonObject.put("data", jsonArray);
        return jsonObject;
    }

//    public static String[] ignoreJsonProp(Class clazz) {
//        SerializeFilter[] p = new SerializeFilter[1];
//        try {
//            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//            HttpServletRequest request = servletRequestAttributes.getRequest();
//            String ignore = request.getHeader("ignoreJsonProp");
//            if (StringVerifyUtil.isEmpty(ignore)) {
//                return new String[1];
//            } else {
//                String ignores[] = ignore.split(",");
//                SimplePropertyPreFilter serializeFilters = new SimplePropertyPreFilter(ignores);
////                MyPropertyFilter serializeFilters = new MyPropertyFilter(clazz.getClass(),ignores);
////            FieldNameFilter[] serializeFilters = new FieldNameFilter[ignores.length];
////            for (int i = 0; i < ignores.length; i++) {
////                serializeFilters[i].process(null, ignores[i], null);
////            }
//                return ignores;
//            }
//
//        } catch (Exception e) {
//            //System.out.println("Error Ignore Prop");
//            return new String[1];
//        }
//
//    }

    public static PropertyFilter ignoreJsonProp(Class clazz) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String ignore = request.getHeader("ignoreJsonProp");
        if (StringVerifyUtil.isEmpty(ignore)) {
            return null;
        }
        String[] ignores = ignore.split(",");
        Set<String> set = new HashSet<>();
        for (int i = 0; i < ignores.length; i++) {
            String s = ignores[i];
            if (StringVerifyUtil.isNotEmpty(s)) {
                s = s.substring(0, 1).toUpperCase() + s.substring(1);
                set.add("vc" + s);
                set.add("i" + s);
                set.add("dt" + s);
                set.add("ti" + s);
                set.add("txt" + s);
                set.add("dl" + s);
                set.add("b" + s);
                set.add("d" + s);
                set.add(s);
                set.add(ignores[i]);
            }

        }
        PropertyFilter propertyFilter = new PropertyFilter() {
            @Override
            public boolean apply(Object object, String name, Object value) {
                return !set.contains(name);
            }
        };
        return propertyFilter;
    }

    public static String underlineToCamel(String underline, boolean firstLetterUpperCase) {
        String[] splits = underline.split("_");
        String camel = "";
        for (String split : splits) {
            camel += firstLetterUpperCase(split);
        }
        if (!firstLetterUpperCase) {
            return firstLetterLowerCase(camel);
        }
        return camel;
    }

    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
