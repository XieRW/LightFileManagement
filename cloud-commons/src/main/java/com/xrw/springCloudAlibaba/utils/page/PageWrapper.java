package com.xrw.springCloudAlibaba.utils.page;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author xearin
 */
public class PageWrapper<T> {

    private Page<T> page;

    public PageWrapper(Page<T> page) {
        this.page = page;
    }
    public int getTotalPages() {
        return page.getTotalPages();
    }

    public long getTotalElements() {
        return page.getTotalElements();
    }

    public List<T> getData() {
        return page.getContent();
    }

}
