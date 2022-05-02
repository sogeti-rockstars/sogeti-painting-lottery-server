package com.sogetirockstars.sogetipaintinglotteryserver.controller;

import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.service.ContestantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ContestantController
 */
@Component

@RestController
@RequestMapping(path = "api/v1/contestants")
public class ContestantController {
    private final ContestantService service;

    @Autowired
    public ContestantController(ContestantService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<Contestant> getAllContestants() {
        return service.getAllContestants();
    }

    @GetMapping("/get/{contestantId}")
    @ResponseBody
    public Contestant getPainting(@PathVariable("contestantId") Long contestantId) {
        return service.getContestant(contestantId);
    }
}
