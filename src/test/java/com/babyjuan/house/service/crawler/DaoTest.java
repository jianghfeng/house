package com.babyjuan.house.service.crawler;

import com.babyjuan.house.HouseApplicationTests;
import com.babyjuan.house.common.enums.RecordStatus;
import com.babyjuan.house.dao.entity.Community;
import com.babyjuan.house.dao.entity.CommunityExample;
import com.babyjuan.house.dao.entity.CommunityExample.Criteria;
import com.babyjuan.house.dao.entity.RentingHouse;
import com.babyjuan.house.dao.CommunityMapper;
import com.babyjuan.house.dao.RentingHouseMapper;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: hjg
 * @Date: Create in 2018/6/18 13:21
 * @Description:
 */
public class DaoTest extends HouseApplicationTests {

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private RentingHouseMapper houseMapper;

    @Test
    public void testExample() {

        CommunityExample example = new CommunityExample();
        Criteria criteria = example.createCriteria();
        criteria.andSourceIdEqualTo(1);
        criteria.andCommunityCodeEqualTo("1");
        criteria.andStatusEqualTo(RecordStatus.LATEST.getStatus());

        List<Community> OldCommunityList = communityMapper.selectByExample(example);
        System.out.println(OldCommunityList);
    }

    @Test
    public void testNull() {
        RentingHouse house = new RentingHouse();
        house.setSourceId(1);
        houseMapper.insert(house);
    }

}
