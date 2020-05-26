package com.babyjuan.house.task.analyze;

import com.babyjuan.house.common.utils.SleepUtils;
import com.babyjuan.house.repository.entity.ShHouseDistrictSummary;
import com.babyjuan.house.repository.entity.ShHouseDistrictSummaryExample;
import com.babyjuan.house.repository.mapper.SecondHandHouseMapper;
import com.babyjuan.house.repository.mapper.ShHouseDistrictSummaryMapper;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author anxi
 * @version 2020/2/3 18:25
 */
@Component
public class SecondHandHouseAnalyzeJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondHandHouseAnalyzeJob.class);

    @Autowired
    private SecondHandHouseMapper secondHandHouseMapper;

    @Autowired
    private ShHouseDistrictSummaryMapper shHouseDistrictSummaryMapper;

    @Scheduled(fixedRate = 24 * 3600 * 1000)
    public void start() {
        generateAvaragePrice(monthsBefore(), daysBefore());
    }

    public void generateAvaragePrice(Date from, Date to) {
        LOGGER.info("analyze second handhouse, from: {}, to: {}", from, to);
        Calendar start = dayStart(from);
        while (start.getTime().compareTo(to) <= 0) {
            generateAvaragePrice(start.getTime());
            start.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    private Calendar dayStart(Date date) {
        Calendar.getInstance().clear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    private Date monthsBefore() {
        Calendar.getInstance().clear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -4);
        return calendar.getTime();
    }

    private Date daysBefore() {
        Calendar.getInstance().clear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -3);
        return calendar.getTime();
    }

    private void generateAvaragePrice(Date date) {
        LOGGER.info("generateAvaragePrice: {}", date);
        List<ShHouseDistrictSummary> toInsertList = secondHandHouseMapper.queryDistrictSummary(date);
        for (ShHouseDistrictSummary shHouseDistrictSummary : toInsertList) {
            SleepUtils.sleep(100);
            ShHouseDistrictSummaryExample example = new ShHouseDistrictSummaryExample();
            example.createCriteria().andInfoTimeEqualTo(date).andDistrictEqualTo(shHouseDistrictSummary.getDistrict());
            List<ShHouseDistrictSummary> records = shHouseDistrictSummaryMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(records)) {
                continue;
            }
            shHouseDistrictSummary.setInfoTime(date);
            shHouseDistrictSummaryMapper.insert(shHouseDistrictSummary);
        }
    }
}
