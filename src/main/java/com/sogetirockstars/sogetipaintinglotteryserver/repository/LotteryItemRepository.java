package com.sogetirockstars.sogetipaintinglotteryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ContestantRepository
 */
@Repository
public interface LotteryItemRepository extends JpaRepository<com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem, Long> {
}
