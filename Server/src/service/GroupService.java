package service;

import dao.GroupDao;
import model.Group;

import java.util.List;

public class GroupService {
    public List<Group> getGroupsByAccount(long account){
        GroupDao groupDao = new GroupDao();
        return groupDao.getGroupsByAccount(account);
    }
}
