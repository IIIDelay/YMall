package org.ymall.list.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.ymall.list.model.Goods;

public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {

}
