package me.andpay.timobileframework.util.calculate;

public class Calculate {
	   // 判断是否为操作符号    
    public static boolean isOperator(String operator) {    
        if (operator.equals("+") || operator.equals("-")    
                || operator.equals("*") || operator.equals("/")    
                || operator.equals("(") || operator.equals(")"))    
            return true;    
        else   
            return false;    
    }    
   
    // 设置操作符号的优先级别    
    public static int priority(String operator) {    
        if (operator.equals("+") || operator.equals("-"))    
            return 1;  
        else if (operator.equals("*") || operator.equals("/"))    
            return 2;    
        else   
            return 0;    
    }    
   
    // 做2值之间的计算    
    public static String twoResult(String operator, String a, String b) {    
        try {    
            String op = operator;    
            String rs = new String();    
            double x = Double.parseDouble(b);    
            double y = Double.parseDouble(a);    
            double z = 0;    
            if (op.equals("+"))    
                z = x + y;    
            else if (op.equals("-"))    
                z = x - y;    
            else if (op.equals("*"))    
                z = x * y;    
            else if (op.equals("/"))    
                z = x / y;    
            else   
                z = 0;    
            return rs + z;    
        } catch (NumberFormatException e) {    
            System.out.println("input has something wrong!");    
            return "Error";    
        }    
    }    
}    


