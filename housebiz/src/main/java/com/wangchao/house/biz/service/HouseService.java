package com.wangchao.house.biz.service;

import com.wangchao.house.common.model.House;
import com.wangchao.house.common.page.PageData;
import com.wangchao.house.common.page.PageParams;

public interface HouseService {

    PageData<House> queryHouse(House query, PageParams build);

}
