package com.wangchao.house.biz.mapper;

import com.wangchao.house.common.model.Community;
import com.wangchao.house.common.model.House;
import com.wangchao.house.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HouseMapper {

    List<House> selectPageHouses(@Param("house") House house,@Param("pageParams") PageParams pageParams);

    Long selectPageCount(@Param("house") House house);

    int insert(House account);

    List<Community> selectCommunity(Community community);
}
