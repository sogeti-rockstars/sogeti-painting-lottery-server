package com.sogetirockstars.sogetipaintinglotteryserver.repository;

import com.sogetirockstars.sogetipaintinglotteryserver.model.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotteryRepository extends JpaRepository<Lottery, Long> {
}
