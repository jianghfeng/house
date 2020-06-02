package com.babyjuan.house.service;

import com.babyjuan.house.service.dto.BaseResponse;
import com.babyjuan.house.service.dto.DistrictSecondHandHouseSummaryDTO;
import com.babyjuan.house.service.dto.PageDTO;
import com.babyjuan.house.service.dto.SecondHandHouseDTO;
import com.babyjuan.house.service.dto.SecondHandHouseSummaryDTO;
import java.util.Date;
import java.util.List;

/**
 * @author anxi
 * @version 2020/5/25 19:34
 */
public interface SecondHandHouseService {

    BaseResponse<List<String>> getAllDistricts();

    BaseResponse<PageDTO<SecondHandHouseDTO>> getSecondHouseList(int page, int pageSize);

    BaseResponse<PageDTO<SecondHandHouseSummaryDTO>> getSecondHouseSummaryList(int page, int pageSize);

    BaseResponse<DistrictSecondHandHouseSummaryDTO> getSecondHouseSummaryRange(Date from, Date to);

    BaseResponse<DistrictSecondHandHouseSummaryDTO> getSecondHouseSummarySpecific(Date from, Date to,
            List<String> districts);
}
