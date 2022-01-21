package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

        
public static void main(String[] args) {
    //exercice1();
    String sh ="())fdsfdf";
    System.out.println(isValid(sh));

  
}

static boolean isValid(String sh){

    char[] ch = sh.toCharArray();
    List<Character> que = new ArrayList<Character>();
    char c;
    for (int i=0; i<ch.length;i++) {
        c = ch[i];
        if(c == '{' || c == '[' || c == '(' ){
        que.add(c);
        }else{
            int leng = que.size();
            if(leng == 0) return false;
            if((c == '}' && que.get(leng -1) == '{')
            || (c == ')' && que.get(leng -1) == '(')
            || (c == ']' && que.get(leng -1) == '[')){
                que.remove(leng-1);
            }else {
                return false;
            } 
        }
             
    }
    return true;
    
}
private static char opposite(char c){
    if(c == '{')
     return '}';
     if(c == '[')
      return ']';
      if(c == '(')
      return ')';
      throw new RuntimeException("Val impossible");
}
private static boolean findNextClose(String sh, char c) {
    char[] ch = sh.toCharArray();
    for (char d : ch) {
        if(d ==  opposite(c))
            return true;
        if(d == '{' || d == '[' || d == '(')
            continue;
        else return false;

    }
    return false;

}

private static void exercice1() {
    List<String> ops = List.of("5","2","C","D","+");
    List<Integer> re = new ArrayList<Integer>();

    for (String s : ops) {
        if(s.equals("C"))
            re.remove(re.size() -1);
        else if(s.equals("D"))
            re.add(2*re.get(re.size() -1));
        else if(s.equals("+"))
            re.add(re.get(re.size() -1)+re.get(re.size() -2));
        else 
            re.add(Integer.valueOf(s));
    
    }
    System.out.println(re);
    System.out.println(re.stream().map(p-> p.toString()).collect(Collectors.toList()));
    re.stream().reduce((a,b)-> a+b).ifPresent(System.out::println);
}
    
}
