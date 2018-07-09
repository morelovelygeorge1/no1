package com.example.demo.Dao;

import com.example.demo.Model.Proposal;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProposalDao {
    @Select("Select * From proposal")
    List<Proposal> getAll();

    @Select("Select * From proposal Where name like \"%\"#{name}\"%\"")
    List<Proposal> getProposalsByName(@Param("name") String name);

    @Select("Select * From proposal Where author=#{uid}")
    List<Proposal> getProposalsByUid(@Param("uid") int uid);

    @Select("Select * From proposal Where pid=#{pid}")
    Proposal geyProposalByPid(@Param("pid") int pid);

    @Insert("INSERT INTO proposal(name,author,content,timeline,deadline) VALUES (#{name},#{author},#{content},#{timeline},#{deadline})")
    @Options(useGeneratedKeys = true, keyProperty = "pid")
    int insert(Proposal proposal);

    @Update("Update proposal Set support=support+1 Where pid=#{pid}")
    void support(@Param("pid") int pid);

    @Update("Update proposal Set reject=reject+1 Where pid=#{pid}")
    void reject(@Param("pid") int pid);
}

