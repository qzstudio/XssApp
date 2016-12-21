package com.xss.web.util;

import java.util.Collection;


public interface IPager<T> {
    public Collection<T> getPageData();

    public void setPageData(Collection<T> pageData);

    public long getCurrentPage();

    public void setCurrentPage(long currentPage);

    public int getPageSize();

    public void setPageSize(int pageSize);

    public long getStartRow();

    public void setStartRow(long startRow);

    public long getTotalPages();

    public void setTotalPages(long totalPages);

    public long getTotalRows();

    public void setTotalRows(long totalRows);

    public int getFormNumber();

    public void setFormNumber(int formNumber);
}
