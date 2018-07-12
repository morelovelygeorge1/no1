package com.example.demo.Dao;

import com.example.demo.Model.Proposal;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProposalDao {
    @Select("Select * From proposal")
    List<Proposal> getAll();

    @Select("Select * From proposal Where status=0")
    List<Proposal> getCollectProposals();

    @Select("Select * From proposal Where status=1")
    List<Proposal> getRecommendProposals();

    @Select("Select * From proposal Where status=2")
    List<Proposal> getRecordProposals();

    @Select("Select * From proposal Where status=3")
    List<Proposal> getRegisterProposals();

    @Select("Select * From proposal Where name like \"%\"#{name}\"%\"")
    List<Proposal> getProposalsByName(@Param("name") String name);

    @Select("Select * From proposal Where author=#{uid}")
    List<Proposal> getProposalsByUid(@Param("uid") int uid);

    @Select("Select * From proposal Where author=#{uid} And status=0;")
    List<Proposal> getCollectProposalsByUid(@Param("uid") int uid);

    @Select("Select * From proposal Where pid=#{pid}")
    Proposal getProposalByPid(@Param("pid") int pid);

    @Insert("INSERT INTO proposal(name,author,content,timeline,deadline) VALUES (#{name},#{author},#{content},#{timeline},#{deadline})")
    @Options(useGeneratedKeys = true, keyProperty = "pid")
    int insert(Proposal proposal);

    @Update("Update proposal Set support=support+1 Where pid=#{pid}")
    void support(@Param("pid") int pid);

    @Update("Update proposal Set reject=reject+1 Where pid=#{pid}")
    void reject(@Param("pid") int pid);

    @Update("Update proposal Set content=#{content} Where pid=#{pid}")
    void update(@Param("pid") int pid, @Param("content") String content);

    @Update("Update proposal Set status=#{status} Where pid=#{pid}")
    void updateStatus(@Param("pid") int pid, @Param("status") int status);
}

