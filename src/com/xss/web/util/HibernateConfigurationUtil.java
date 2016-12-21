package com.xss.web.util;


import java.util.Iterator;  

import org.hibernate.cfg.Configuration;  
import org.hibernate.mapping.Column;  
import org.hibernate.mapping.PersistentClass;  
import org.hibernate.mapping.Property;  
import org.springframework.beans.BeansException;  
import org.springframework.context.ApplicationContext;  
import org.springframework.context.ApplicationContextAware;  
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;  

/** 
* 根据实体类得到对应的表名、主键名、字段名（与Spring集成） 
* 这里使用xml文件配置的映射，需要保证实体类名与对应映射文件名一致，即User.java与User.hbm.xml  
* </p> 
* 这里使用继承ApplicationContextAware的方式来获得ApplicationContext, 
* 因此需要在Spring配置文件中配置一下该类，才能自动注入ApplicationContext对象 
*  
* <bean class="util.HibernateConfigurationUtil"/> 
*/  
public class HibernateConfigurationUtil {  


  private static Configuration configuration;  

  public static Configuration getConfiguration() {  

      if (configuration == null) {   
          // 取sessionFactory的时候要加上&  
          LocalSessionFactoryBean factory = (LocalSessionFactoryBean) SpringContextHelper  
                  .getBean("&sessionFactory");  
          configuration = factory.getConfiguration();  
      }  

      return configuration;  
  }  

  private static <T> PersistentClass getPersistentClass(Class<T> clazz) {  
      synchronized (HibernateConfigurationUtil.class) {  
          PersistentClass pc = getConfiguration().getClassMapping(  
                  clazz.getSimpleName());  
          if (pc == null) {  
              configuration = configuration.addClass(clazz);  
              pc = configuration.getClassMapping(clazz.getName());  
          }  
          return pc;  
      }  
  }  

  /** 
   * 获得实体类对应的表名 
   *  
   * @param clazz 
   *            实体类的Class对象 
   * @return 表名 
   */  
  public static <T> String getTableName(Class<T> clazz) {  
      return getPersistentClass(clazz).getTable().getName();  
  }  

  /** 
   * 获得实体类对应表的主键字段名称 
   *  
   * @param clazz 
   *            实体类的Class对象 
   * @return 主键字段名称 
   */  
  public static <T> String getPKColumnName(Class<T> clazz) {  
      return getPersistentClass(clazz).getTable().getPrimaryKey()  
              .getColumn(0).getName();  
  }  

  /** 
   * 获得类属性对应的字段名 
   *  
   * @param clazz 
   *            实体类的Class对象 
   * @param propertyName 
   *            实体类的属性名 
   * @return 属性对应的字段名 
   */  
  public static <T> String getColumnName(Class<T> clazz, String propertyName) {  
      String columnName = "";  
      PersistentClass persistentClass = getPersistentClass(clazz);  
      Property property = persistentClass.getProperty(propertyName);  
      Iterator<?> iterator = property.getColumnIterator();  
      if (iterator.hasNext()) {  
          Column column = (Column) iterator.next();  
          columnName += column.getName();  
      }  
      return columnName;  
  }  

}  