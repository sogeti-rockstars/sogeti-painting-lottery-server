package com.sogetirockstars.sogetipaintinglotteryserver.repository;

import com.sogetirockstars.sogetipaintinglotteryserver.model.Winner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinnerRepository extends JpaRepository<Winner, Long> {
}
