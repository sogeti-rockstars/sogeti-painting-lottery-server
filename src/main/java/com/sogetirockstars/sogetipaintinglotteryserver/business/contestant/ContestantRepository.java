package com.sogetirockstars.sogetipaintinglotteryserver.business.contestant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ContestantRepository
 */
@Repository
public interface ContestantRepository extends JpaRepository<Contestant, Long> {
}
