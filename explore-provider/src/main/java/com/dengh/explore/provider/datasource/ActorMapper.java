package com.dengh.explore.provider.datasource;

import com.dengh.explore.provider.datasource.Actor;
import com.dengh.explore.provider.datasource.ActorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActorMapper {
    int countByExample(ActorExample example);

    int deleteByExample(ActorExample example);

    int insert(Actor record);

    int insertSelective(Actor record);

    List<Actor> selectByExample(ActorExample example);

    int updateByExampleSelective(@Param("record") Actor record, @Param("example") ActorExample example);

    int updateByExample(@Param("record") Actor record, @Param("example") ActorExample example);
}