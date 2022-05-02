package com.sogetirockstars.sogetipaintinglotteryserver.repository;

import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ContestantRepository
 */
@Repository
public interface LotteryItemRepository extends JpaRepository<LotteryItem, Long> {
}
