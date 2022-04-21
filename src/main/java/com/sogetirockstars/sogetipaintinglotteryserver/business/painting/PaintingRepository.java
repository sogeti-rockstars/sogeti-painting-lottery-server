package com.sogetirockstars.sogetipaintinglotteryserver.business.painting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ContestantRepository
 */
@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {
}
