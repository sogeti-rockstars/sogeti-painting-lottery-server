package com.sogetirockstars.sogetipaintinglotteryserver.repository;

import com.sogetirockstars.sogetipaintinglotteryserver.model.AssociationInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociationInfoRepository extends JpaRepository<AssociationInfo, Long> {
    AssociationInfo findByField(String field);
}
