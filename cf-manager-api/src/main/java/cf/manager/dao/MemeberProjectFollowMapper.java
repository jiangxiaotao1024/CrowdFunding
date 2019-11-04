package cf.manager.dao;

import cf.bean.MemeberProjectFollow;
import cf.bean.MemeberProjectFollowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemeberProjectFollowMapper {
    long countByExample(MemeberProjectFollowExample example);

    int deleteByExample(MemeberProjectFollowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemeberProjectFollow record);

    int insertSelective(MemeberProjectFollow record);

    List<MemeberProjectFollow> selectByExample(MemeberProjectFollowExample example);

    MemeberProjectFollow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemeberProjectFollow record, @Param("example") MemeberProjectFollowExample example);

    int updateByExample(@Param("record") MemeberProjectFollow record, @Param("example") MemeberProjectFollowExample example);

    int updateByPrimaryKeySelective(MemeberProjectFollow record);

    int updateByPrimaryKey(MemeberProjectFollow record);
}