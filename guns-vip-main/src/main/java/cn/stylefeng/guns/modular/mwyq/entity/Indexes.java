package cn.stylefeng.guns.modular.mwyq.entity;

/**
 * 这个类用来保存querybykeyword返回结果时所需要的翻页的页码跟关键字
 * @author Jack
 *
 */
public class Indexes {
    private long index;
    private String parameter="";
    private String text="";
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public long getIndex() {
        return this.index;
    }
    public void setIndex(long index) {
        this.index = index;
    }
    public String getParameter() {
        return this.parameter;
    }
    public void setParameter(String paramter) {
        this.parameter = paramter;
    }
    public Indexes(long index,String parameter,String text){
        this.index=index;
        this.parameter=parameter;
        this.text=text;
    }
}
