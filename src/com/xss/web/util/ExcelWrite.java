package com.xss.web.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelWrite {
	public static void writeExcel(String fileName, List<String> datas) {
		try {
			File fileWrite = new File(fileName);
			if (!fileWrite.exists()) {
				fileWrite.createNewFile();
			}
			WritableWorkbook wwb = Workbook
					.createWorkbook(new FileOutputStream(fileWrite));
			// 创建Excel工作表 指定名称和位置
			WritableSheet ws = wwb.createSheet("Test Sheet 1", 0);

			// **************往工作表中添加数据*****************

			// 1.添加Label对象
			Label label = null;
			for (int i=0;i<datas.size();i++) {
				label = new Label(0,  i, datas.get(i));
				ws.addCell(label);
			}
			// 写入工作表
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			String all = FileUtils.readFile("f:/1.txt", "utf-8");
			String[] tabs = all.split("\r\n");
			all = null;
			String tmp = "";
			List<String> list=new ArrayList<String>();
			for (int i = 0; i < tabs.length; i++) {
				tmp = StringUtils.textCutCenter(tabs[i], "mobile\":\"", "\"");
				if (StringUtils.isNullOrEmpty(tmp)) {
					continue;
				}
				list.add(tmp);
				if(list.size()>=3000||i>=tabs.length-1){
					if(tabs.length-i-1<4000){
						continue;
					}
					writeExcel("f://mobile/"+i/3000+".xls", list);
					list=new ArrayList<String>();
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
