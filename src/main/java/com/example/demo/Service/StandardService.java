package com.example.demo.Service;

import com.example.demo.Model.Proposal;

import java.util.List;

public interface StandardService {

    Proposal getProposalByPid(int pid);

    List<Proposal> getAll();

    List<Proposal> getProposalsByUid(int uid);

    List<Proposal> getProposalsByName(String name);

    int add(int uid, String name, String content);
}