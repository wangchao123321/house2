package com.wangchao.house.biz.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.wangchao.house.biz.mapper.HouseMapper;
import com.wangchao.house.common.model.Community;
import com.wangchao.house.common.model.House;
import com.wangchao.house.common.page.PageData;
import com.wangchao.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseServiceImpl implements HouseService {

    @Value("${file.path}")
    private String imgPrefix;

    @Autowired
    private HouseMapper houseMapper;

    @Override
    public PageData<House> queryHouse(House query, PageParams pageParams) {
        List<House> houses= Lists.newArrayList();
        if(!Strings.isNullOrEmpty(query.getName())){
            Community community=new Community();
            community.setName(query.getName());
            List<Community> communities=houseMapper.selectCommunity(community);
            if(!communities.isEmpty()){
                query.setCommunityId(communities.get(0).getId());
            }
        }
        houses= queryAndSetImg(query,pageParams);
        Long count=houseMapper.selectPageCount(query);
        return PageData.buildPage(houses,count,pageParams.getPageSize(),pageParams.getPageNum());
    }

    private List<House> queryAndSetImg(House query, PageParams pageParams) {
        List<House> houses = houseMapper.selectPageHouses(query, pageParams);
        houses.forEach(h -> {
            h.setFirstImg(imgPrefix+h.getFirstImg());
            h.setImageList(h.getImageList().stream().map(img -> imgPrefix+ img).collect(Collectors.toList()));
            h.setFloorPlanList(h.getFloorPlanList().stream().map(pic -> imgPrefix + pic).collect(Collectors.toList()));
        });
        return houses;
    }
}
