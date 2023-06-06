package org.ymall.model.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.ymall.model.product.SpuInfo;
import org.ymall.model.product.SpuInfoModel;

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
