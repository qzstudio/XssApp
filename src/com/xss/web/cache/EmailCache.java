package com.xss.web.cache;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xss.web.annotation.CacheHandle;
import com.xss.web.annotation.DelCacheHandle;
import com.xss.web.base.cache.CacheFinal;
import com.xss.web.cache.base.BaseCache;
import com.xss.web.model.Email;

@Service
public class EmailCache extends BaseCache {
	@CacheHandle(key=CacheFinal.LETTER_LIST_KEY ,validTime=60)
	public List<Email> loadEmails() {
		List<Email> emails = (List<Email>) baseService.load(Email.class);
		return emails;
	}
	@CacheHandle(key=CacheFinal.EMAIL_INFO_KEY ,validTime=60)
	public Email getEmail(Integer id) {
		Email email = (Email) baseService.get(Email.class, id);
		return email;
	}
	@DelCacheHandle(keys={CacheFinal.EMAIL_INFO_KEY,CacheFinal.LETTER_LIST_KEY})
	public void save(Email email) {
		baseService.saveOrUpdate(email);
	}
	@DelCacheHandle(keys={CacheFinal.EMAIL_INFO_KEY,CacheFinal.LETTER_LIST_KEY})
	public void delete(Integer id) {
		baseService.delete(Email.class, id);
	}
}
