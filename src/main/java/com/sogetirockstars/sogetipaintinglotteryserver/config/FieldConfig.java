package com.sogetirockstars.sogetipaintinglotteryserver.config;

import java.util.Map;

import com.sogetirockstars.sogetipaintinglotteryserver.model.AssociationInfo;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.AssociationInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * FieldConfig
 */

@Configuration
public class FieldConfig {
    private final Map<String, String> defaultFields = Map.of(
            "aboutAssociationTitle", "Styrelsen",
            "aboutAssociationBody",  "Vi som sim sitter i styrelsen är...",
            "aboutFlowTitle",        "Nyheter",
            "aboutFlowBody",         "Från och med imorgon!",
            "aboutUsTitle",          "Om oss",
            "aboutUsBody",           "Vi är den bästa föreningen för konst och inom konstbaserade saker, utmärkt CSR och non-profit förening för alla människors lika värde och nytta. ");

    @Autowired
    public void defaultFields(AssociationInfoRepository repo) {
        defaultFields.entrySet().stream().forEach(entry -> {
            if (repo.findByField(entry.getKey()) == null)
                repo.save(new AssociationInfo(entry.getKey(), entry.getValue()));
        });
    }
}
