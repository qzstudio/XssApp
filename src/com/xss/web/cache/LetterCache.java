package com.xss.web.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xss.web.annotation.CacheHandle;
import com.xss.web.annotation.DelCacheHandle;
import com.xss.web.base.cache.CacheFinal;
import com.xss.web.base.page.Pager;
import com.xss.web.cache.base.BaseCache;
import com.xss.web.entity.Where;
import com.xss.web.model.Letter;
import com.xss.web.model.LetterParas;
import com.xss.web.model.Project;
import com.xss.web.service.LetterService;
import com.xss.web.util.StringUtils;

@Service
public class LetterCache extends BaseCache {
	@Resource
	LetterService letterService;
	@DelCacheHandle(keys={CacheFinal.LETTER_PAGER_KEY,CacheFinal.LETTER_PARAS_KEY,CacheFinal.LETTER_INFO_KEY,CacheFinal.LETTER_CONTEXT_KEY,CacheFinal.LETTER_COUNT_KEY,CacheFinal.USER_PROJECT_LETER_COUNT_KEY})
	public void save(Letter letter) {
		letterService.saveOrUpdate(letter);
	}
	@CacheHandle(key=CacheFinal.LETTER_PARAS_KEY ,validTime=60)
	public List<LetterParas> getLetterParas(Integer letterId) {
		List<LetterParas> paras = (List<LetterParas>) letterService.findByField(
				LetterParas.class, "paraName", false, "letter.id", letterId);
		return paras;

	}
	@CacheHandle(key=CacheFinal.LETTER_CONTEXT_KEY ,validTime=60)
	public Letter findLetter(String context) {
		Letter letter = (Letter) letterService.findFirstByField(Letter.class,
				"context", context);
		return letter;
	}
	@CacheHandle(key=CacheFinal.LETTER_INFO_KEY ,validTime=60)
	public Letter getLetter(Integer id) {
		Letter letter = (Letter) letterService.get(Letter.class, id);
		return letter;
	}
	@CacheHandle(key=CacheFinal.LETTER_COUNT_KEY ,validTime=30)
	public Integer getLetterCount() {
		// 查DB
		Integer count = letterService.getLetterCount();
		return count;

	}
	@CacheHandle(key=CacheFinal.LETTER_INFO_KEY ,validTime=60)
	public List<Letter> getLetter(Integer userId, Integer... id) {
		Where where = new Where();
		where.set("project.user.id", userId);
		where.set("id", "in", id);
		List<Letter> letters = (List<Letter>) letterService
				.findByObject(Letter.class, where);
		return letters;
	}
	@DelCacheHandle(keys={CacheFinal.LETTER_PAGER_KEY,CacheFinal.LETTER_PARAS_KEY,CacheFinal.LETTER_INFO_KEY,CacheFinal.LETTER_CONTEXT_KEY,CacheFinal.LETTER_COUNT_KEY,CacheFinal.USER_PROJECT_LETER_COUNT_KEY})
	public void delLetter(Integer... letteryId) {
		letterService.delLetterById(letteryId);
		String key = CacheFinal.LETTER_LIST_KEY.toString();
		removeCacheFuzzy(key);
		key = CacheFinal.LETTER_PARAS_KEY.toString();
		removeCacheFuzzy(key);
		key = CacheFinal.LETTER_INFO_KEY.toString();
		removeCacheFuzzy(key);
		key = CacheFinal.LETTER_CONTEXT_KEY.toString();
		removeCacheFuzzy(key);
		key = CacheFinal.LETTER_COUNT_KEY.toString();
		removeCacheFuzzy(key);
		key = CacheFinal.USER_PROJECT_LETER_COUNT_KEY.toString();
		removeCacheFuzzy(key);

	}
	@CacheHandle(key=CacheFinal.LETTER_INFO_KEY ,validTime=60)
	public Letter getLetter(String uuid) {
		Letter letter = (Letter) letterService.findFirstByField(Letter.class, "uuid",
				uuid);
		return letter;
	}
	@CacheHandle(key=CacheFinal.LETTER_LIST_KEY ,validTime=60)
	public Pager<Letter> getLetters(Letter letter, Pager<Letter> pager) {
		Pager<Letter> letters = (Pager<Letter>) letterService.findPagerByObject(letter,
				pager, "id", true);
		return letters;
	}
	@DelCacheHandle(keys={CacheFinal.USER_PROJECT_LETER_COUNT_KEY})
	public void batchSaveLeter(List<LetterParas> leters, Project project) {
		letterService.batchSave(leters);
	}
}
