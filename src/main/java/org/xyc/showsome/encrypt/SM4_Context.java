package org.xyc.showsome.encrypt;

/**
 * Created by IntelliJ IDEA.
 * Author: wks
 * Date: 2016/12/23
 */
public class SM4_Context
{
    public int mode;

    public long[] sk;

    public boolean isPadding;

    public SM4_Context()
    {
        this.mode = 1;
        this.isPadding = true;
        this.sk = new long[32];
    }
}
