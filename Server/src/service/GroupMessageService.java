package service;

import dao.GroupMessageDao;
import model.GroupMessage;

import java.util.Date;
import java.util.List;

public class GroupMessageService {
    public List<GroupMessage> getGroupMessagesByGroupId(long groupId){
        GroupMessageDao groupMessageDao = new GroupMessageDao();
        return groupMessageDao.getGroupMessagesByGroupId(groupId);
    }

    public List<GroupMessage> getHasNoReadMessages(long groupId, long last_read_messageId){
        GroupMessageDao groupMessageDao = new GroupMessageDao();
        return groupMessageDao.getGroupHasNotReadMessages(groupId, last_read_messageId);
    }

    public void updateLastReadMessageId(long groupId, long account){
        GroupMessageDao groupMessageDao = new GroupMessageDao();
        groupMessageDao.updateUserHasReadMessageId(groupId, account, groupMessageDao.getMaxGroupMessageId(groupId));
    }

    public List<GroupMessage> getHasNoReadMessagesByAccount(long groupId, long account){
        GroupMessageDao groupMessageDao = new GroupMessageDao();
        long last_read_messageId = groupMessageDao.getLastReadMessageId(groupId, account);
        groupMessageDao.updateUserHasReadMessageId(groupId, account, groupMessageDao.getMaxGroupMessageId(groupId));
        return groupMessageDao.getGroupHasNotReadMessages(groupId, last_read_messageId);
    }

    public void addGroupMessage(long groupId, long sendAccount, String content, Date sendDate){
        GroupMessageDao groupMessageDao = new GroupMessageDao();
        groupMessageDao.addGroupMessage(groupId, sendAccount, content, sendDate);
    }
}
