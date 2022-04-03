package me.staticstudios.prisons.data.dataHandling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Data types can be added here, they will get serialized without a problem as long as that type is serializable.
public class Data implements Serializable {
    public List list = new ArrayList();
    public Map map = new HashMap();
    public byte[] byteArr;
    public boolean _boolean = false;
    public int _int = 0;
    public long _long = 0;
    public float _float = 0;
    public double _double = 0;
    public char _char = ' ';
    public String _string = "";
}