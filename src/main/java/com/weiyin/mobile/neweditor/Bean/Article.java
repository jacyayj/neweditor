package com.weiyin.mobile.neweditor.Bean;

/**
 * Created by jacyayj on 2016/1/28.
 */
public class Article extends SuperT {

    /**
     * articleId : 文章标识
     * title : 文章标题
     * thumb : 文章缩略图
     * description : 文章描述
     * content : 文章内容
     * reads : 阅读数
     * source : 文章来源
     * sourceLink : 文章链接
     * createTime : 发布时间
     */
    private int articleId;
    private String title = null;
    private String thumb = null;
    private String description = null;
    private String content = null;
    private String reads = null;
    private String source = null;
    private String sourceLink = null;
    private String createTime = null;

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReads() {
        return reads;
    }

    public void setReads(String reads) {
        this.reads = reads;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
