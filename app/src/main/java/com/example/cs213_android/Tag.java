package com.example.cs213_android;

import java.io.Serializable;

public class Tag implements Serializable{
    private static final long serialVersionUID = 4L;
    private String type, value;

    public Tag(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return type + ": " + value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Tag)) {
            return false;
        }

        Tag tag = (Tag) obj;

        if (tag.getValue().compareToIgnoreCase(value) == 0 && tag.getType().compareToIgnoreCase(type) == 0) {
            return true;
        }
        return false;
    }
}
