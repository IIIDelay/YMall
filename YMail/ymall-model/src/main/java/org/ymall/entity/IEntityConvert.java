package org.ymall.entity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * IEntityConvert
 *
 * @Author IIIDelay
 * @Date 2023/6/2 22:47
 **/
@Mapper
public interface IEntityConvert {
    IEntityConvert INSTANT = Mappers.getMapper(IEntityConvert.class);

    SpuInfoModel spuInfoModelToSpuInfo(SpuInfo spuInfo);
}
