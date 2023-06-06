package org.ymall.ware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.ymall.ware.bean.WareInfo;

import java.util.List;

/**
 * @param
 * @return
 */
@Repository
public interface WareInfoMapper extends BaseMapper<WareInfo> {
    List<WareInfo> selectWareInfoBySkuid(String skuid);
}
