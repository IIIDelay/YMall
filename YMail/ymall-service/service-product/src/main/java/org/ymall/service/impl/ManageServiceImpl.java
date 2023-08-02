/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.iiidev.ymall.common.RedisConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ymall.commons.annotation.YMallCache;
import org.ymall.commons.helper.AttrCheckUtil;
import org.ymall.mapper.BaseAttrInfoMapper;
import org.ymall.mapper.BaseAttrValueMapper;
import org.ymall.mapper.BaseCategory1Mapper;
import org.ymall.mapper.BaseCategory2Mapper;
import org.ymall.mapper.BaseCategory3Mapper;
import org.ymall.mapper.BaseCategoryViewMapper;
import org.ymall.mapper.BaseSaleAttrMapper;
import org.ymall.mapper.BaseTrademarkMapper;
import org.ymall.mapper.SkuAttrValueMapper;
import org.ymall.mapper.SkuImageMapper;
import org.ymall.mapper.SkuInfoMapper;
import org.ymall.mapper.SkuSaleAttrValueMapper;
import org.ymall.mapper.SpuImageMapper;
import org.ymall.mapper.SpuInfoMapper;
import org.ymall.mapper.SpuPosterMapper;
import org.ymall.mapper.SpuSaleAttrMapper;
import org.ymall.mapper.SpuSaleAttrValueMapper;
import org.ymall.model.product.BaseAttrInfo;
import org.ymall.model.product.BaseAttrValue;
import org.ymall.model.product.BaseCategory1;
import org.ymall.model.product.BaseCategory2;
import org.ymall.model.product.BaseCategory3;
import org.ymall.model.product.BaseCategoryView;
import org.ymall.model.product.BaseSaleAttr;
import org.ymall.model.product.BaseTrademark;
import org.ymall.model.product.SkuAttrValue;
import org.ymall.model.product.SkuImage;
import org.ymall.model.product.SkuInfo;
import org.ymall.model.product.SkuSaleAttrValue;
import org.ymall.model.product.SpuImage;
import org.ymall.model.product.SpuInfo;
import org.ymall.model.product.SpuPoster;
import org.ymall.model.product.SpuSaleAttr;
import org.ymall.model.product.SpuSaleAttrValue;
import org.ymall.service.IManageService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ManageServiceImpl implements IManageService {
    @Autowired
    private BaseCategory1Mapper baseCategory1Mapper;

    @Autowired
    private BaseCategory2Mapper baseCategory2Mapper;

    @Autowired
    private BaseCategory3Mapper baseCategory3Mapper;

    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;

    @Autowired
    private SpuPosterMapper spuPosterMapper;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    private BaseCategoryViewMapper baseCategoryViewMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private BaseTrademarkMapper baseTrademarkMapper;

    @Override
    public List<BaseCategory1> getCategory1() {
        return baseCategory1Mapper.selectList(null);
    }

    @Override
    public List<BaseCategory2> getCategory2(Long category1Id) {
        // select * from baseCategory2 where Category1Id = ?
        QueryWrapper queryWrapper = new QueryWrapper<BaseCategory2>();
        queryWrapper.eq("category1_id", category1Id);
        List<BaseCategory2> baseCategory2List = baseCategory2Mapper.selectList(queryWrapper);
        return baseCategory2List;
    }

    @Override
    public List<BaseCategory3> getCategory3(Long category2Id) {
        // select * from baseCategory3 where Category2Id = ?
        QueryWrapper<BaseCategory3> queryWrapper = new QueryWrapper<BaseCategory3>();
        queryWrapper.eq("category2_id", category2Id);
        return baseCategory3Mapper.selectList(queryWrapper);
    }

    @Override
    public List<BaseAttrInfo> getAttrInfoList(Long category1Id, Long category2Id, Long category3Id) {
        // 调用mapper：
        return baseAttrInfoMapper.selectBaseAttrInfoList(category1Id, category2Id, category3Id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {

        List<BaseAttrInfo> baseAttrInfos = AttrCheckUtil.updateDuplicateMPVerify(() -> baseAttrInfoMapper, Pair.of(BaseAttrInfo::getId, baseAttrInfo.getId()),
            Pair.of(BaseAttrInfo::getAttrName, baseAttrInfo.getAttrName()));

        // 什么情况下 是添加，什么情况下是更新，修改 根据baseAttrInfo 的Id
        // baseAttrInfo
        if (baseAttrInfo.getId() != null) {
            // 修改数据
            baseAttrInfoMapper.updateById(baseAttrInfo);
        } else {
            // 新增
            // baseAttrInfo 插入数据
            baseAttrInfoMapper.insert(baseAttrInfo);
        }

        // baseAttrValue 平台属性值
        // 修改：通过先删除{baseAttrValue}，在新增的方式！
        // 删除条件：baseAttrValue.attrId = baseAttrInfo.id
        QueryWrapper queryWrapper = new QueryWrapper<BaseAttrValue>();
        queryWrapper.eq("attr_id", baseAttrInfo.getId());
        baseAttrValueMapper.delete(queryWrapper);

        // 获取页面传递过来的所有平台属性值数据
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if (attrValueList != null && attrValueList.size() > 0) {
            // 循环遍历
            for (BaseAttrValue baseAttrValue : attrValueList) {
                // 获取平台属性Id 给attrId
                baseAttrValue.setAttrId(baseAttrInfo.getId()); // ?
                baseAttrValueMapper.insert(baseAttrValue);
            }
        }
    }

    @Override
    public BaseAttrInfo getAttrInfo(Long attrId) {
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectById(attrId);
        // 查询到最新的平台属性值集合数据放入平台属性中！
        baseAttrInfo.setAttrValueList(getAttrValueList(attrId));
        return baseAttrInfo;
    }

    /**
     * 根据属性id获取属性值
     *
     * @param attrId
     * @return
     */
    private List<BaseAttrValue> getAttrValueList(Long attrId) {
        // select * from baseAttrValue where attrId = ?
        QueryWrapper<BaseAttrValue> queryWrapper = new QueryWrapper<BaseAttrValue>();
        queryWrapper.eq("attr_id", attrId);
        return baseAttrValueMapper.selectList(queryWrapper);
    }


    @Override
    public IPage<SpuInfo> getSpuInfoPage(Page<SpuInfo> pageParam, SpuInfo spuInfo) {
        QueryWrapper<SpuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category3_id", spuInfo.getCategory3Id());
        queryWrapper.orderByDesc("id");
        return spuInfoMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return baseSaleAttrMapper.selectList(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSpuInfo(SpuInfo spuInfo) {
    /*
        spuInfo;
        spuImage;
        spuSaleAttr;
        spuSaleAttrValue;
        spuPoster
     */
        spuInfoMapper.insert(spuInfo);

        //  获取到spuImage 集合数据
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        //  判断不为空
        if (CollectionUtils.isNotEmpty(spuImageList)) {
            //  循环遍历
            for (SpuImage spuImage : spuImageList) {
                //  需要将spuId 赋值
                spuImage.setSpuId(spuInfo.getId());
                //  保存spuImge
                spuImageMapper.insert(spuImage);
            }
        }
        //  获取销售属性集合
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        //  判断
        if (!CollectionUtils.isEmpty(spuSaleAttrList)) {
            //  循环遍历
            for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
                //  需要将spuId 赋值
                spuSaleAttr.setSpuId(spuInfo.getId());
                spuSaleAttrMapper.insert(spuSaleAttr);

                //  再此获取销售属性值集合
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                //  判断
                if (!CollectionUtils.isEmpty(spuSaleAttrValueList)) {
                    //  循环遍历
                    for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                        //   需要将spuId， sale_attr_name 赋值
                        spuSaleAttrValue.setSpuId(spuInfo.getId());
                        spuSaleAttrValue.setSaleAttrName(spuSaleAttr.getSaleAttrName());
                        spuSaleAttrValueMapper.insert(spuSaleAttrValue);
                    }
                }
            }
        }

        //  获取到posterList 集合数据
        List<SpuPoster> spuPosterList = spuInfo.getSpuPosterList();
        //  判断不为空
        if (!CollectionUtils.isEmpty(spuPosterList)) {
            for (SpuPoster spuPoster : spuPosterList) {
                //  需要将spuId 赋值
                spuPoster.setSpuId(spuInfo.getId());
                //  保存spuPoster
                spuPosterMapper.insert(spuPoster);
            }
        }
    }

    @Override
    public List<SpuImage> getSpuImageList(Long spuId) {
        QueryWrapper<SpuImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("spu_id", spuId);
        return spuImageMapper.selectList(queryWrapper);
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrList(Long spuId) {
        return spuSaleAttrMapper.selectSpuSaleAttrList(spuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSkuInfo(SkuInfo skuInfo) {
    /*
        skuInfo 库存单元表 --- spuInfo！
        skuImage 库存单元图片表 --- spuImage!
        skuSaleAttrValue sku销售属性值表{sku与销售属性值的中间表} --- skuInfo ，spuSaleAttrValue
        skuAttrValue sku与平台属性值的中间表 --- skuInfo ，baseAttrValue
     */
        skuInfoMapper.insert(skuInfo);
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        if (skuImageList != null && skuImageList.size() > 0) {
            // 循环遍历
            for (SkuImage skuImage : skuImageList) {
                skuImage.setSkuId(skuInfo.getId());
                skuImageMapper.insert(skuImage);
            }
        }

        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        // 调用判断集合方法
        if (!CollectionUtils.isEmpty(skuSaleAttrValueList)) {
            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                skuSaleAttrValue.setSkuId(skuInfo.getId());
                skuSaleAttrValue.setSpuId(skuInfo.getSpuId());
                skuSaleAttrValueMapper.insert(skuSaleAttrValue);
            }
        }

        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
                skuAttrValueMapper.insert(skuAttrValue);
            }
        }
    }

    @Override
    public IPage<SkuInfo> getPage(Page<SkuInfo> pageParam) {
        QueryWrapper<SkuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return skuInfoMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    @Transactional
    public void onSale(Long skuId) {
        // 更改销售状态
        SkuInfo skuInfoUp = new SkuInfo();
        skuInfoUp.setId(skuId);
        skuInfoUp.setIsSale(1);
        skuInfoMapper.updateById(skuInfoUp);
    }

    @Override
    @Transactional
    public void cancelSale(Long skuId) {
        // 更改销售状态
        SkuInfo skuInfoUp = new SkuInfo();
        skuInfoUp.setId(skuId);
        skuInfoUp.setIsSale(0);
        skuInfoMapper.updateById(skuInfoUp);
    }

    @Override
    @YMallCache(prefix = RedisConstants.SKUKEY_PREFIX)
    public SkuInfo getSkuInfo(Long skuId) {
        // 使用框架redisson解决分布式锁！
        // return getSkuInfoRedisson(skuId);

        // SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        // // 根据skuId 查询图片列表集合
        // QueryWrapper<SkuImage> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("sku_id", skuId);
        // List<SkuImage> skuImageList = skuImageMapper.selectList(queryWrapper);
        //
        // skuInfo.setSkuImageList(skuImageList);
        return getSkuInfoRedis(skuId);
    }

    @Override
    @YMallCache(prefix = "categoryViewByCategory3Id:")
    public BaseCategoryView getCategoryViewByCategory3Id(Long category3Id) {
        return baseCategoryViewMapper.selectById(category3Id);
    }

    /**
     * 获取sku价格
     *
     * @param skuId
     * @return
     */
    @Override
    public BigDecimal getSkuPrice(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        if (null != skuInfo) {
            return skuInfo.getPrice();
        }
        return new BigDecimal("0");
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Long skuId, Long spuId) {
        return spuSaleAttrMapper.selectSpuSaleAttrListCheckBySku(skuId, spuId);
    }

    @Override
    public Map getSkuValueIdsMap(Long spuId) {
        Map<Object, Object> map = new HashMap<>();
        // key = 125|123 ,value = 37
        List<Map> mapList = skuSaleAttrValueMapper.selectSaleAttrValuesBySpu(spuId);
        if (mapList != null && mapList.size() > 0) {
            // 循环遍历
            for (Map skuMap : mapList) {
                // key = 125|123 ,value = 37
                map.put(skuMap.get("value_ids"), skuMap.get("sku_id"));
            }
        }
        return map;
    }

    @Override
    public List<SpuPoster> findSpuPosterBySpuId(Long spuId) {
        QueryWrapper<SpuPoster> spuInfoQueryWrapper = new QueryWrapper<>();
        spuInfoQueryWrapper.eq("spu_id", spuId);
        List<SpuPoster> spuPosterList = spuPosterMapper.selectList(spuInfoQueryWrapper);
        return spuPosterList;
    }

    @Override
    public List<BaseAttrInfo> getAttrList(Long skuId) {

        return baseAttrInfoMapper.selectBaseAttrInfoListBySkuId(skuId);
    }


    @Autowired
    private RedisTemplate redisTemplate;

    // 使用redis' 做分布式锁
    private SkuInfo getSkuInfoRedis(Long skuId) {
        SkuInfo skuInfo = null;
        try {
            // 缓存存储数据：key-value
            // 定义key sku:skuId:info
            String skuKey = RedisConstants.SKUKEY_PREFIX + skuId + RedisConstants.SKUKEY_SUFFIX;
            // 获取里面的数据？ redis 有五种数据类型 那么我们存储商品详情 使用哪种数据类型？
            // 获取缓存数据
            skuInfo = (SkuInfo) redisTemplate.opsForValue().get(skuKey);
            // 如果从缓存中获取的数据是空
            if (skuInfo == null) {
                // 直接获取数据库中的数据，可能会造成缓存击穿。所以在这个位置，应该添加锁。
                // 第一种：redis ，第二种：redisson
                // 定义锁的key sku:skuId:lock  set k1 v1 px 10000 nx
                String lockKey = RedisConstants.SKUKEY_PREFIX + skuId + RedisConstants.SKULOCK_SUFFIX;
                // 定义锁的值
                String uuid = UUID.randomUUID().toString().replace("-", "");
                // 上锁
                Boolean isExist = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, RedisConstants.SKULOCK_EXPIRE_PX2, TimeUnit.SECONDS);
                if (isExist) {
                    // 执行成功的话，则上锁。
                    System.out.println("获取到分布式锁！");
                    // 真正获取数据库中的数据 {数据库中到底有没有这个数据 = 防止缓存穿透}
                    skuInfo = getSkuInfoDB(skuId);
                    // 从数据库中获取的数据就是空
                    if (skuInfo == null) {
                        // 为了避免缓存穿透 应该给空的对象放入缓存
                        SkuInfo skuInfo1 = new SkuInfo(); // 对象的地址
                        redisTemplate.opsForValue().set(skuKey, skuInfo1, RedisConstants.SKUKEY_TEMPORARY_TIMEOUT, TimeUnit.SECONDS);
                        return skuInfo1;
                    }
                    // 查询数据库的时候，有值
                    redisTemplate.opsForValue().set(skuKey, skuInfo, RedisConstants.SKUKEY_TIMEOUT, TimeUnit.SECONDS);
                    // 解锁：使用lua 脚本解锁
                    String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                    // 设置lua脚本返回的数据类型
                    DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
                    // 设置lua脚本返回类型为Long
                    redisScript.setResultType(Long.class);
                    redisScript.setScriptText(script);
                    // 删除key 所对应的 value
                    redisTemplate.execute(redisScript, Arrays.asList(lockKey), uuid);

                    return skuInfo;
                } else {
                    // 其他线程等待
                    Thread.sleep(1000);
                    return getSkuInfo(skuId);
                }
            } else {

                return skuInfo;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 为了防止缓存宕机：从数据库中获取数据
        return getSkuInfoDB(skuId);
    }

    private SkuInfo getSkuInfoRedisson(Long skuId) {
        SkuInfo skuInfo = null;
        try {
            // 缓存存储数据：key-value
            // 定义key sku:skuId:info
            String skuKey = RedisConstants.SKUKEY_PREFIX + skuId + RedisConstants.SKUKEY_SUFFIX;
            // 获取里面的数据？ redis 有五种数据类型 那么我们存储商品详情 使用哪种数据类型？
            // 获取缓存数据
            skuInfo = (SkuInfo) redisTemplate.opsForValue().get(skuKey);
            // 如果从缓存中获取的数据是空
            if (skuInfo == null) {
                // 直接获取数据库中的数据，可能会造成缓存击穿。所以在这个位置，应该添加锁。
                // 第二种：redisson
                // 定义锁的key sku:skuId:lock  set k1 v1 px 10000 nx
                String lockKey = RedisConstants.SKUKEY_PREFIX + skuId + RedisConstants.SKULOCK_SUFFIX;
                RLock lock = redissonClient.getLock(lockKey);
            /*
            第一种： lock.lock();
            第二种:  lock.lock(10,TimeUnit.SECONDS);
            第三种： lock.tryLock(100,10,TimeUnit.SECONDS);
             */
                // 尝试加锁
                boolean res = lock.tryLock(RedisConstants.SKULOCK_EXPIRE_PX1, RedisConstants.SKULOCK_EXPIRE_PX2, TimeUnit.SECONDS);
                if (res) {
                    try {
                        // 处理业务逻辑 获取数据库中的数据
                        // 真正获取数据库中的数据 {数据库中到底有没有这个数据 = 防止缓存穿透}
                        skuInfo = getSkuInfoDB(skuId);
                        // 从数据库中获取的数据就是空
                        if (skuInfo == null) {
                            // 为了避免缓存穿透 应该给空的对象放入缓存
                            SkuInfo skuInfo1 = new SkuInfo(); // 对象的地址
                            redisTemplate.opsForValue().set(skuKey, skuInfo1, RedisConstants.SKUKEY_TEMPORARY_TIMEOUT, TimeUnit.SECONDS);
                            return skuInfo1;
                        }
                        // 查询数据库的时候，有值
                        redisTemplate.opsForValue().set(skuKey, skuInfo, RedisConstants.SKUKEY_TIMEOUT, TimeUnit.SECONDS);

                        // 使用redis 用的是lua 脚本删除 ，但是现在用么？ lock.unlock
                        return skuInfo;

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        // 解锁：
                        lock.unlock();
                    }
                } else {
                    // 其他线程等待
                    Thread.sleep(1000);
                    return getSkuInfo(skuId);
                }
            } else {

                return skuInfo;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 为了防止缓存宕机：从数据库中获取数据
        return getSkuInfoDB(skuId);
    }


    @Override
    public SkuInfo getSkuInfoDB(Long skuId) {

        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        if (skuInfo != null) {
            QueryWrapper<SkuImage> skuImageQueryWrapper = new QueryWrapper<>();
            skuImageQueryWrapper.eq("sku_id", skuId);
            List<SkuImage> skuImageList = skuImageMapper.selectList(skuImageQueryWrapper);
            skuInfo.setSkuImageList(skuImageList);
        }

        return skuInfo;
    }

    @Override
    public BigDecimal getPrice(Long skuId) {
        //  select price from sku_info where id = skuId;
        //  select * from sku_info where id = skuId;
        //  SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        //  不需要将数据放入缓存！
        RLock lock = redissonClient.getLock(skuId + ":lock");
        //  上锁
        lock.lock();
        SkuInfo skuInfo = null;
        BigDecimal price = new BigDecimal(0);
        try {
            QueryWrapper<SkuInfo> skuInfoQueryWrapper = new QueryWrapper<>();
            skuInfoQueryWrapper.eq("id", skuId);
            skuInfoQueryWrapper.select("price");
            skuInfo = skuInfoMapper.selectOne(skuInfoQueryWrapper);
            if (skuInfo != null) {
                price = skuInfo.getPrice();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //  解锁！
            lock.unlock();
        }
        //  返回价格
        return price;

    }

    @Override
    @YMallCache(prefix = "category")
    public List<JSONObject> getBaseCategoryList() {
// 声明几个json 集合
        ArrayList<JSONObject> list = new ArrayList<>();
        // 声明获取所有分类数据集合
        List<BaseCategoryView> baseCategoryViewList = baseCategoryViewMapper.selectList(null);
        // 循环上面的集合并安一级分类Id 进行分组
        Map<Long, List<BaseCategoryView>> category1Map  = baseCategoryViewList.stream().collect(Collectors.groupingBy(BaseCategoryView::getCategory1Id));
        int index = 1;
        // 获取一级分类下所有数据
        for (Map.Entry<Long, List<BaseCategoryView>> entry1  : category1Map.entrySet()) {
            // 获取一级分类Id
            Long category1Id  = entry1.getKey();
            // 获取一级分类下面的所有集合
            List<BaseCategoryView> category2List1  = entry1.getValue();
            //
            JSONObject category1 = new JSONObject();
            category1.put("index", index);
            category1.put("categoryId",category1Id);
            // 一级分类名称
            category1.put("categoryName",category2List1.get(0).getCategory1Name());
            // 变量迭代
            index++;
            // 循环获取二级分类数据
            Map<Long, List<BaseCategoryView>> category2Map  = category2List1.stream().collect(Collectors.groupingBy(BaseCategoryView::getCategory2Id));
            // 声明二级分类对象集合
            List<JSONObject> category2Child = new ArrayList<>();
            // 循环遍历
            for (Map.Entry<Long, List<BaseCategoryView>> entry2  : category2Map.entrySet()) {
                // 获取二级分类Id
                Long category2Id  = entry2.getKey();
                // 获取二级分类下的所有集合
                List<BaseCategoryView> category3List  = entry2.getValue();
                // 声明二级分类对象
                JSONObject category2 = new JSONObject();

                category2.put("categoryId",category2Id);
                category2.put("categoryName",category3List.get(0).getCategory2Name());
                // 添加到二级分类集合
                category2Child.add(category2);

                List<JSONObject> category3Child = new ArrayList<>();

                // 循环三级分类数据
                category3List.stream().forEach(category3View -> {
                    JSONObject category3 = new JSONObject();
                    category3.put("categoryId",category3View.getCategory3Id());
                    category3.put("categoryName",category3View.getCategory3Name());

                    category3Child.add(category3);
                });

                // 将三级数据放入二级里面
                category2.put("categoryChild",category3Child);

            }
            // 将二级数据放入一级里面
            category1.put("categoryChild",category2Child);
            list.add(category1);
        }
        return list;

    }

    @Override
    public BaseTrademark getTrademarkByTmId(Long tmId) {
        return baseTrademarkMapper.selectById(tmId);
    }
}
