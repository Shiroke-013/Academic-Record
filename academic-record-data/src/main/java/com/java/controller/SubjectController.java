package com.java.controller;

import com.java.dto.SubjectDto;
import com.java.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RequestMapping("/subject/")
@RestController
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public void save(@Valid @RequestBody SubjectDto subjectDto) throws Exception {
        try {
            subjectService.save(subjectDto);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping("{id}")
    public Object findById(@PathVariable Integer id) throws Exception {
        try {
            return subjectService.findById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping
    public Collection<SubjectDto> findAll() throws Exception {
        try {
            return subjectService.findAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable Integer id) throws Exception {
        try {
            subjectService.deleteById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @DeleteMapping
    void deleteAll() throws Exception {
        try {
            subjectService.deleteAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PatchMapping("{id}")
    void update(@Valid @RequestBody SubjectDto subjectDto, @PathVariable Integer id) throws Exception {
        try {
            subjectService.update(subjectDto, id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
