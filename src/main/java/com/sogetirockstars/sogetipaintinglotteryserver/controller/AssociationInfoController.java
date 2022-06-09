package com.sogetirockstars.sogetipaintinglotteryserver.controller;

import java.util.Map;
import java.util.stream.Collectors;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.AssociationInfo;
import com.sogetirockstars.sogetipaintinglotteryserver.service.AssociationInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/info")
public class AssociationInfoController {

    private final AssociationInfoService service;

    @Autowired
    public AssociationInfoController(AssociationInfoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> get() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            AssociationInfo infoItem = service.get(id);
            return new ResponseEntity<>(infoItem, HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<AssociationInfo> addNew(@RequestBody AssociationInfo infoItem) {
        infoItem.setId(null);
        return ResponseEntity.ok().body(service.add(infoItem));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AssociationInfo infoItem) {
        try {
            infoItem.setId(id);
            return new ResponseEntity<>(service.update(infoItem), HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "field")
    public Map<String, String> getAllMap() {
        return service.getAll().stream().collect(Collectors.toMap(AssociationInfo::getField, AssociationInfo::getData));
    }

    @GetMapping(value = "field/{fieldName}")
    public ResponseEntity<?> get(@PathVariable String fieldName) {
        try {
            return new ResponseEntity<>(service.findByField(fieldName).getData(), HttpStatus.OK);
        } catch (NullPointerException | IdException e) {
            return new ResponseEntity<>("No matching field with field name " + fieldName + ".", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "field/{fieldName}")
    public ResponseEntity<?> deletePost(@PathVariable String fieldName) {
        try {
            return deletePost(service.findByField(fieldName).getId());
        } catch (NullPointerException | IdException e) {
            return new ResponseEntity<>("No matching field with field name " + fieldName + ".", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "field/{fieldName}")
    public ResponseEntity<?> update(@PathVariable String fieldName, @RequestBody String data) {
        try {
            var oldInfoItem = service.findByField(fieldName);
            oldInfoItem.setData(data);
            return update(oldInfoItem.getId(), oldInfoItem);
        } catch (NullPointerException | IdException e) {
            return new ResponseEntity<>("No matching field with field name " + fieldName + ".", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "field/{fieldName}")
    public ResponseEntity<?> addNew(@PathVariable String fieldName, @RequestBody String data) {
        try {
            if (service.findByField(fieldName) != null)
                throw new IdException();
            return addNew(new AssociationInfo(fieldName, data));
        } catch (NullPointerException | IdException e) {
            return new ResponseEntity<>("Field " + fieldName + " is used. Use PUT to change value.",
                    HttpStatus.NOT_FOUND);
        }
    }
}
