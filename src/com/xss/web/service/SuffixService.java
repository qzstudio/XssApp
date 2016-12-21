package com.xss.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xss.web.model.SuffixStatic;
import com.xss.web.model.Suffix;
import com.xss.web.service.base.BaseService;
@Service("SuffixService")
public class SuffixService extends BaseService{
	public List<Suffix> loadSuffix(){
		String hql="from Suffix s order by s.status desc,s.suffix";
		List<Suffix> suffixList=(List<Suffix>) baseDao.findByHql(hql);
		return suffixList;
	}
	public List<String> loadAvaSuffix(){
		String hql="from Suffix s where s.status>0";
		List<Suffix> suffixList=(List<Suffix>) baseDao.findByHql(hql);
		List<String> suffix=new ArrayList<String>();
		for(Suffix tmp:suffixList){
			suffix.add(tmp.getSuffix());
		}
		return suffix;
	}
	public Suffix getSuffix(Integer id){
		return (Suffix) baseDao.get(Suffix.class, id);
	}
	public void updateSuffix(Suffix suffix){
		String hql="update Suffix s set s.status=1 where s.status=2";
		baseDao.cudByHql(hql, null);
		baseDao.update(suffix);
	}
	public String loadDefSuffix(){
		String hql="from Suffix s where s.status=2";
		List<Suffix> suffixList=(List<Suffix>) baseDao.findByHql(hql);
		if(suffixList!=null&&!suffixList.isEmpty()){
			return suffixList.get(0).getSuffix();
		}
		return null;
	}
	
	public List<String> loadStaSuffix(){
		String hql="from SuffixStatic s";
		List<SuffixStatic> suffixList=(List<SuffixStatic>) baseDao.findByHql(hql);
		List<String> list=new ArrayList<String>();
		for(SuffixStatic tmp:suffixList){
			list.add(tmp.getSuffix());
		}
		return list;
	}
	public void updateSuffix(Integer[]suffix){
		String hql="update Suffix s set s.status=0 where s.status<>2 ";
		baseDao.cudByHql(hql);
		hql="update Suffix s set s.status=1 where s.status<>2 and s.id in(:suffix)";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("suffix", suffix);
		baseDao.cudByHql(hql,map);
	}
}
