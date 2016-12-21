package com.xss.web.base.page;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.xss.web.model.base.BaseModel;
import com.xss.web.util.WebUtil;

public class Pager<T> extends BaseModel {

	private static final long serialVersionUID = -5319485510025861943L;
	private Integer totalRows;
	private int pageSize = 10;
	private Integer currentPage;
	private Integer totalPages;
	private Integer startRow;
	private int formNumber;
	private Collection<?> pageData;

	public Pager(int pageSize) {
		super();
		this.currentPage = 1;
		this.startRow = 0;
		this.pageSize = pageSize;
	}

	public Pager(HttpServletRequest request, int pageSize) {
		super();
		this.currentPage = Integer.parseInt(WebUtil.getParm(request, "page",
				"0"));
		this.startRow = 0;
		this.pageSize = pageSize;
	}

	public Pager(String page, int pageSize) {
		super();
		// Object page=request.get("page");
		this.currentPage = page == null ? 0 : Integer.parseInt(page);
		this.startRow = 0;
		this.pageSize = pageSize;
	}

	public Pager() {
		this.currentPage = 1;
		this.startRow = 0;
	}

	public Collection<?> getPageData() {
		return pageData;
	}

	public void setPageData(Collection<?> pageData) {
		this.pageData = pageData;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getStartRow() {
		return startRow != 0 ? startRow : (currentPage - 1) * pageSize;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
		try {
			this.totalPages = totalRows / pageSize;
			long mod = totalRows % pageSize;
			if (mod > 0) {
				this.totalPages++;
			}
			if (this.totalPages == 0) {
				this.totalPages = 1;
			}
			if (this.currentPage > totalPages) {
				this.currentPage = totalPages;
			}
			this.startRow = (currentPage - 1) * pageSize;
			if (this.startRow < 0) {
				startRow = 0;
			}
			if (this.currentPage == 0 || this.currentPage < 0) {
				currentPage = 1;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public int getFormNumber() {
		return formNumber;
	}

	public void setFormNumber(int formNumber) {
		this.formNumber = formNumber;
	}
}
