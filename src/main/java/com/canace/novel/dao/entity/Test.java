package com.canace.novel.dao.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @date 2024/01/15
 */
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Byte test;

    private Byte test2;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getTest() {
        return test;
    }

    public void setTest(Byte test) {
        this.test = test;
    }

    public Byte getTest2() {
        return test2;
    }

    public void setTest2(Byte test2) {
        this.test2 = test2;
    }

    @Override
    public String toString() {
        return "Test{" +
        "id=" + id +
        ", test=" + test +
        ", test2=" + test2 +
        "}";
    }
}
