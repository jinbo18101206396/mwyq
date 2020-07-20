package cn.stylefeng.guns.generator.generator.base;

import cn.stylefeng.guns.generator.generator.base.model.ContextParam;

/**
 * 代码生成器
 *
 * @author jinbo
 * @date 2018-12-13-11:08 AM
 */
public abstract class Generator {

    protected ContextParam contextParam;

    /**
     * 初始化配置
     *
     * @author jinbo
     * @Date 2018/12/12 3:13 PM
     */
    public void initContext(ContextParam paramContext) {
        this.contextParam = paramContext;
    }

    /**
     * 代码生成之前，自由发挥
     *
     * @author jinbo
     * @Date 2018/12/13 2:30 PM
     */
    protected void beforeGeneration() {

    }

    /**
     * 执行代码生成
     *
     * @author jinbo
     * @Date 2018/12/12 3:13 PM
     */
    public abstract void doGeneration();

    /**
     * 代码生成之后，自由发挥
     *
     * @author jinbo
     * @Date 2018/12/13 2:30 PM
     */
    protected void afterGeneration() {

    }

}
