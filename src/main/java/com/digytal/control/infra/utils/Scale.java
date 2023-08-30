package com.digytal.control.infra.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Scale {
    public static BigDecimal scale2(Double valor){
        return scale(new BigDecimal(valor),2);
    }
    public static BigDecimal scale2(BigDecimal valor){
        return scale(valor,2);
    }
    public static BigDecimal scale4(Double valor){
        return scale(new BigDecimal(valor),4);
    }
    public static BigDecimal scale4(BigDecimal valor){
        return scale(valor,4);
    }
    public static BigDecimal scale(BigDecimal valor,int scale){
        return valor.setScale(scale, RoundingMode.HALF_EVEN);
    }
}
