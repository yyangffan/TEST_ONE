package com.example.https.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangfan on 2017/5/9.
 */

public class Databean {
    private String name;
    private int imgvId;
    private ChildMsg mChildMsg = new ChildMsg();

    public ChildMsg getChildMsg() {
        return mChildMsg;
    }

    private List<ChildMsg> mList = new ArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgvId() {
        return imgvId;
    }

    public void setImgvId(int imgvId) {
        this.imgvId = imgvId;
    }

    public List<ChildMsg> getList() {
        return mList;
    }

    public void setList(List<ChildMsg> list) {
        mList = list;
    }


}

