package com.xss.web.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.xss.web.model.base.BaseModel;

public class Where extends BaseModel {
	private List<ThisWhere> wheres = new ArrayList<ThisWhere>();

	/**
	 * 设置参数
	 * 
	 * @param fieldName
	 *            字段名
	 * @param symbol
	 *            操作符
	 * @param fieldValues
	 *            字段值
	 */
	public Where set(String fieldName, String symbol, Object... fieldValues) {
		ThisWhere thisWhere = new ThisWhere();
		thisWhere.setFieldName(fieldName);
		thisWhere.setSymbol(symbol);
		if (fieldValues != null) {
			thisWhere.setFieldValues(Arrays.asList(fieldValues));
		}
		wheres.add(thisWhere);
		return this;
	}

	/**
	 * 设置参数，默认条件为等于
	 * 
	 * @param fieldName
	 *            字段名
	 * @param fieldValue
	 *            字段值
	 */
	public Where set(String fieldName, Object fieldValue) {
		return set(fieldName, "=", fieldValue);
	}

	public List<ThisWhere> getWheres() {
		return wheres;
	}

	public void setWheres(List<ThisWhere> wheres) {
		this.wheres = wheres;
	}

	public static void main(String[] args) {
		Where where = new Where().set("id", "in", 1, 2, 3, 4, 5, 6);
	}
	
	
	
	public static class ThisWhere {
		private String fieldName;
		private String symbol;
		private List<Object> fieldValues;

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getSymbol() {
			return symbol;
		}

		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}

		public List<Object> getFieldValues() {
			return fieldValues;
		}

		public void setFieldValues(List<Object> fieldValues) {
			this.fieldValues = fieldValues;
		}
	}

}

