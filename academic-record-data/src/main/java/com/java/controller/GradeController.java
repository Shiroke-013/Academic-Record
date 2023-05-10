package com.java.controller;

import com.java.dto.GradeDto;
import com.java.dto.SubjectDto;
import com.java.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RequestMapping("/grade/")
@RestController
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @PostMapping
    public void save(@Valid @RequestBody GradeDto gradeDto) throws Exception {
        try {
            gradeService.save(gradeDto);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping("{id}")
    public Object findById(@PathVariable Integer id) throws Exception {
        try {
            return gradeService.findById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping
    public Collection<GradeDto> findAll() throws Exception {
        try {
            return gradeService.findAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable Integer id) throws Exception {
        try {
            gradeService.deleteById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @DeleteMapping
    void deleteAll() throws Exception {
        try {
            gradeService.deleteAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PatchMapping("{id}")
    void update(@Valid @RequestBody GradeDto gradeDto, @PathVariable Integer id) throws Exception {
        try {
            gradeService.update(gradeDto, id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
