package org.ymall.commons.helper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import common.MongodbConstant;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import utils.CollectionHelper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MongoUtil {

    public static Query buildIdQuery(Object obj) {
        if (isPrimitive(obj)) {
            Criteria criteria = Criteria.where(MongodbConstant.ID).is(obj);
            return Query.query(criteria);
        } else {
            return Query.query(Criteria.where(MongodbConstant.ID).is(getDocumentId(obj)));
        }
    }

    public static Update buildUpdate(Object obj) {
        Update update = new Update();

        try {
            Class rClass = obj.getClass();// 得到类对象
            Field[] fs = rClass.getDeclaredFields();// 得到属性集合
            for (Field f : fs) {// 遍历属性
                f.setAccessible(true); // 设置属性是可以访问的
                String name = f.getName();// 得到此属性的name
                Object val = f.get(obj);// 得到此属性的value

                // id不作为更新字段
                Id id = f.getAnnotation(Id.class);
                if (id != null || StrUtil.equals(name, "_id")) {
                    continue;
                }

                // 字段值不为null的情况下进行更新
                // if (val != null) {
                org.springframework.data.mongodb.core.mapping.Field field = f.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class);
                if (field != null) {
                    update.set(field.value(), val);
                } else {
                    update.set(name, val);
                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return update;
    }

    public static Query buildQuery(Object obj) {
        Query query = new Query();

        try {
            Class rClass = obj.getClass();// 得到类对象
            Field[] fs = rClass.getDeclaredFields();// 得到属性集合

            for (Field f : fs) {// 遍历属性
                f.setAccessible(true); // 设置属性是可以访问的
                String name = f.getName();// 得到此属性的name
                Object val = f.get(obj);// 得到此属性的value

                // val不为null且是基本数据类型构组装成条件
                if (val != null && isPrimitive(val)) {
                    org.springframework.data.mongodb.core.mapping.Field field = f.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class);
                    // 是否指定字段
                    query.addCriteria(Criteria.where(field != null ? field.value() : name).is(val));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return query;
    }

    /**
     * 判断一个对象是否是: 基本类型/基本类型的封装类型/字符串/其他
     */
    private static boolean isPrimitive(Object obj) {

        if (obj == null) return false;

        // 自定义放行对象类型 TODO 待完善 继续添加新的类型
        if (obj instanceof String) return true;
        if (obj instanceof BigInteger) return true;
        if (obj instanceof BigDecimal) return true;

        // 基本数据类型或封装类型放行
        try {
            return ((Class<?>) obj.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }

    }

    @SneakyThrows
    public static <T> List<Pair<Query, Update>> getPairList(Collection<T> list) {
        List<Pair<Query, Update>> pairList = new LinkedList<>();
        for (T t : list) {
            // 循环获取Pair对象
            Pair<Query, Update> pair = getPair(t);
            pairList.add(pair);
        }

        return pairList.stream().filter(e -> e != null).collect(Collectors.toList());
    }

    @SneakyThrows
    private static <T> Pair<Query, Update> getPair(T t) {
        Query query = buildIdQuery(t);

        Update update = buildUpdate(t);

        // TODO 没有任何一个字段做更新不做处理, 否则会将除_id外的字段都置null
        if (update.isIsolated()) {
            return null;
        }

        // 返回Pair对象
        return Pair.of(query, buildUpdate(t));
    }

    /**
     * 判断@Id是否存在, 并且value不为null
     *
     * @param obj
     * @return
     */
    public static boolean existDocumentId(Object obj) {
        return getDocumentId(obj) == null ? false : true;// 空值返回false 非空返回true
    }

    /**
     * 获取@Id下的字段value
     *
     * @param obj
     * @return
     */
    @SneakyThrows
    public static Object getDocumentId(Object obj) {
        Class rClass = obj.getClass();// 得到类对象
        Field[] fs = rClass.getDeclaredFields();// 得到属性集合
        for (Field f : fs) {// 遍历属性
            Id id = f.getAnnotation(Id.class);
            if (id != null) {
                f.setAccessible(true); // 设置属性是可以访问的
                return f.get(obj);// 返回@Id注解下的字段值
            }
        }
        return null;
    }

    private static final int FIRST_PAGE_NUM = 1;

    private static final String ID = "_id";

    private MongoTemplate MONGO_TEMPLATE;

    /**
     * 分页查询，直接返回集合类型的结果
     */
    public <T> IPage<T> pageQuery(Query query, Class<T> entityClass, Integer pageSize, Integer pageNum, String collectionName) {
        return pageQuery(query, entityClass, pageSize, pageNum, Function.identity(), null, collectionName);
    }

    public MongoUtil injectMongoTemplate(MongoTemplate mongoTemplate) {
        this.MONGO_TEMPLATE = mongoTemplate;
        return this;
    }

    /**
     * 分页查询
     *
     * @param query       mongo Query 对象，构造你自己的查询条件
     * @param entityClass mongo collection 定义的entity class ,确定集合
     * @param pageSize    每页大小
     * @param pageNum     当前页
     * @param mapper      映射器 collection 定义的entityClass,如要转化为另一个对象
     * @param lastId      上一页的主键id
     * @param <T>         mongo映射实体类
     * @param <R>         响应实体类
     * @return 分页参数
     */
    public <T, R> IPage<R> pageQuery(Query query, Class<T> entityClass, Integer pageSize,
                                     Integer pageNum, Function<T, R> mapper, String lastId, String collectionName) {
        // 分页逻辑
        long total = MONGO_TEMPLATE.count(query, entityClass, collectionName);
        // 多少页
        final Integer pages = (int) Math.ceil(total / (double) pageSize);
        if (pageNum <= 0) {
            pageNum = FIRST_PAGE_NUM;
        }

        if (!StringUtils.isEmpty(lastId)) {
            Criteria criteria = new Criteria();
            if (pageNum != FIRST_PAGE_NUM) {
                criteria.and(ID).gt(new ObjectId(lastId));
            }
            query.addCriteria(criteria);
            query.limit(pageSize);
        } else {
            int skip = pageSize * (pageNum - 1);
            query.skip(skip).limit(pageSize);
        }
        List<T> entityList = MONGO_TEMPLATE.find(query, entityClass, collectionName);

        IPage<R> page = new Page<>();

        page.setTotal(total);
        page.setPages(pages);
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page.setRecords(CollectionHelper.toList(entityList, null, mapper));
        return page;
    }
}