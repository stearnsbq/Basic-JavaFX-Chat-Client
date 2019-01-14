package server;

import java.io.Serializable;

public class message implements Serializable {

    private String name;
    private String msg;


    public String getMsg() {
        return msg;
    }


    public String getName() {
        return name;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }


    public void setName(String name) {
        this.name = name;
    }



}
