package com.shihu.my.api.util;

import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 文件工具类
 * @author shihu
 * @date 2018/05/26
 */
public class FileUtil {



    public static void writeToFileAppend(String filePath,String str) throws IOException {
        writeToFile(filePath, str, true);
    }

    public static void writeToFileOverride(String filePath, String str) throws IOException {
        writeToFile(filePath, str, false);
    }

    public static void writeToFile(String filePath,String str,boolean isAppend) throws IOException {
        OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(filePath,isAppend),"UTF-8");
        BufferedWriter bw=new BufferedWriter(osw);
        bw.write(str+"\n");
        bw.close();
    }

    public static void writeToFileAppend(String filePath,Collection<String> collection) throws IOException {
        writeToFile(filePath,collection,true);
    }

    public static void writeToFileOverride(String filePath,Collection<String> collection) throws IOException {
        writeToFile(filePath,collection,false);
    }

    private static void writeToFile(String filePath, Collection<String> collection, boolean isAppend) throws IOException {
        OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(filePath,isAppend),"UTF-8");
        BufferedWriter bw=new BufferedWriter(osw);
        for(String str:collection){
            bw.write(str+"\n");
        }
        bw.close();
    }

    public static List<String> readFromFile(String filePath) throws IOException {
        InputStreamReader isr=new InputStreamReader(new FileInputStream(filePath),"UTF-8");
        BufferedReader br=new BufferedReader(isr);
        List<String> list=new LinkedList<String>();
        String str=br.readLine();
        while(null!=str){
            if(!"".equals(str.trim())){
                list.add(str);
            }
            str=br.readLine();
        }
        br.close();
        return list;
    }
}
