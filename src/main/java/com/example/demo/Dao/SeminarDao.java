package com.example.demo.Dao;

import com.example.demo.Model.Seminar;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SeminarDao {
    @Select("Select * From seminar;")
    List<Seminar> getAll();

    @Insert("INSERT INTO seminar(name) VALUES (#{name});")
    @Options(useGeneratedKeys = true, keyProperty = "seminarid")
    void insert(Seminar seminar);
}
