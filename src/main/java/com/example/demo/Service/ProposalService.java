package com.example.demo.Service;

import com.example.demo.Model.Proposal;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProposalService {

    Proposal getProposalByPid(int pid);

    List<Proposal> getAll();

    List<Proposal> getCollectProposals();

    List<Proposal> getRecommendProposals();

    List<Proposal> getRecordProposals();

    List<Proposal> getRegisterProposals();

    List<Proposal> getProposalsByUid(int uid);

    List<Proposal> getProposalsByName(String name);

    int add(int uid, String name, String content);

    int update(int pid, String content);

    int updateStatus(int pid, int status);
}

