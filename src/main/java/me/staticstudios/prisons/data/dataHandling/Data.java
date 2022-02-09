package me.staticstudios.prisons.data.dataHandling;

import me.staticstudios.prisons.data.PlayerBackpack;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

//Data types can be added here, they will get serialized without a problem as long as that type is serializable.
public class Data implements Serializable {
    public BigInteger bigInteger;
    public List list;
    public Map map;
    public byte[] byteArr;
    public boolean _boolean = false;
    public int _int = 0;
    public long _long = 0;
    public float _float = 0;
    public double _double = 0;
    public char _char = ' ';
    public String _string = "";
}
