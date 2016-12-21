package com.xss.web.entity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.xss.web.model.base.BaseModel;

public class BeanFieldEntity extends BaseModel{

	private String fieldName;
	private Object fieldValue;
	private Class fieldType;
	private Annotation[] fieldAnnotations;
	private Field sourceField;
	public Field getSourceField() {
		return sourceField;
	}
	public void setSourceField(Field sourceField) {
		this.sourceField = sourceField;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	public Class getFieldType() {
		return fieldType;
	}
	public void setFieldType(Class fieldType) {
		this.fieldType = fieldType;
	}
	public Annotation[] getFieldAnnotations() {
		return fieldAnnotations;
	}
	public void setFieldAnnotations(Annotation[] fieldAnnotations) {
		this.fieldAnnotations = fieldAnnotations;
	}
	
	
}
