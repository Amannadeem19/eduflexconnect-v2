package com.fyp.eduflexconnect.Util;

public class COMMONFuncs {
    public final static String separateBearerFromToken(String jwt){
        return jwt.substring(7);
    }
}
