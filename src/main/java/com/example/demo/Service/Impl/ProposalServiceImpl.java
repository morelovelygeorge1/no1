package com.example.demo.Service.Impl;

import com.example.demo.Dao.ProposalDao;
import com.example.demo.Model.Proposal;
import com.example.demo.Service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProposalServiceImpl implements ProposalService {

    @Autowired
    ProposalDao proposalDao;

    @Override
    public Proposal getProposalByPid(int pid) {
        return proposalDao.getProposalByPid(pid);
    }

    @Override
    public List<Proposal> getAll() {
        return proposalDao.getAll();
    }

    @Override
    public List<Proposal> getCollectProposals() {
        return proposalDao.getCollectProposals();
    }

    @Override
    public List<Proposal> getRecommendProposals() {
        return proposalDao.getRecommendProposals();
    }

    @Override
    public List<Proposal> getRecordProposals() {
        return proposalDao.getRecordProposals();
    }

    @Override
    public List<Proposal> getRegisterProposals() {
        return proposalDao.getRegisterProposals();
    }

    @Override
    public List<Proposal> getProposalsByUid(int uid) {
        return proposalDao.getProposalsByUid(uid);
    }

    @Override
    public List<Proposal> getProposalsByName(String name) {
        return proposalDao.getProposalsByName(name);
    }

    @Override
    public int add(int uid, String name, String content) {
        List<Proposal> proposals = proposalDao.getCollectProposalsByUid(uid);
        if (proposals != null && proposals.size() < 3) {
            Proposal proposal = new Proposal(uid, name, content);
            if (proposalDao.insert(proposal) > 0) {
                return proposal.getPid();
            } else {
                return -1;
            }
        } else {
            return -2;
        }
    }

    @Override
    public int update(int pid, String content) {
        Proposal proposal = proposalDao.getProposalByPid(pid);
        if (proposal != null) {
            proposalDao.update(pid, content);
            return pid;
        } else {
            return 0;
        }
    }

    @Override
    public int updateStatus(int pid, int status) {
        Proposal proposal = proposalDao.getProposalByPid(pid);
        if (proposal != null) {
            if ((proposal.getStatus() == 0 && (status == 1 || status == -1)) ||
                    (proposal.getStatus() == 1 && status == 2) ||
                    (proposal.getStatus() == 2 && status == 3)) {
                proposalDao.updateStatus(pid, status);
                return pid;
            } else {
                return -1;
            }
        } else {
            return 0;
        }
    }
}