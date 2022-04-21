package com.sogetirockstars.sogetipaintinglotteryserver.business.contestant;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Contestant> getAllContestants(){
        return service.getAllContestants();
    }

    @GetMapping("/get")
    @ResponseBody
    public Contestant getPainting(@RequestParam Long id){
        return service.getContestant(id);
    }
}
