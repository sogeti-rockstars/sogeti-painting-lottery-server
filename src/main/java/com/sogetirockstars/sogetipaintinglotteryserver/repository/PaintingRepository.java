package com.sogetirockstars.sogetipaintinglotteryserver.repository;

import com.sogetirockstars.sogetipaintinglotteryserver.model.Painting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ContestantRepository
 */
@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {
}
