package com.sogetirockstars.sogetipaintinglotteryserver.repository;

import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ContestantRepository
 */
@Repository
public interface ContestantRepository extends JpaRepository<Contestant, Long> {
}
