package com.xss.web.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xss.web.annotation.CacheHandle;
import com.xss.web.annotation.DelCacheHandle;
import com.xss.web.base.cache.CacheFinal;
import com.xss.web.base.page.Pager;
import com.xss.web.cache.base.BaseCache;
import com.xss.web.model.Invite;
import com.xss.web.service.InviteService;
import com.xss.web.util.JUUIDUtil;
import com.xss.web.util.StringUtils;

@Service
public class InviteCache extends BaseCache {
	@Resource
	InviteService inviteService;
	@CacheHandle(key=CacheFinal.INVITE_INFO_KEY ,validTime=10)
	public Invite getInvite(String code) {
		Invite invite = (Invite) baseService.findFirstByField(Invite.class,
				"inviteCode", code);
		return invite;
	}
	@CacheHandle(key=CacheFinal.INVITE_LIST_KEY ,validTime=60)
	public Pager<Invite> getPager(Invite invite, Pager<Invite> pager) {
		Pager<Invite> invites = (Pager<Invite>) baseService.findPagerByObject(invite, pager,
				"id", true);
		return invites;
	}
	@DelCacheHandle(keys={CacheFinal.INVITE_INFO_KEY,CacheFinal.INVITE_LIST_KEY})
	public void importInvite(Integer num) {
		List<Invite> invites = new ArrayList<Invite>();
		Invite invite = null;
		for (int i = 0; i < num; i++) {
			invite = new Invite();
			invite.setInviteCode(JUUIDUtil.createUuid());
			invite.setStatus(1);
			invite.setUpdateTime(new Date());
			invites.add(invite);
		}
		baseService.batchSave(invites);
	}
	@DelCacheHandle(keys={CacheFinal.INVITE_INFO_KEY,CacheFinal.INVITE_LIST_KEY})
	public void delete(Integer... id) {
		inviteService.delete(id);
	}
	@DelCacheHandle(keys={CacheFinal.INVITE_INFO_KEY})
	public void save(Invite invite) {
		baseService.saveOrUpdate(invite);
	}
}
