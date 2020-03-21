package com.study.room.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * TODO: 文件工具类
 * @Date 2019/11/5 上午11:01
 * @Author cjr7399
 */
public class FileUtils {

    private FileUtils(){}
    //生成文件路径
    public static String json_path = "/var/file/";//
//    public static String json_path = "file/";//


    /**
     * @Method saveFile
     * TODO: 保存文件
     * @param filepath 文件名
     * @param content 内容
     * @Return boolean
     * @Exception
     * @Date 2019/11/5 上午11:04
     * @Author cjr7399
     */
    public static boolean saveFile(String filepath, String content){
        Boolean bool = false;

        File file = new File(filepath);

        try {
            if (!file.exists()) {
                file.createNewFile();
                bool = true;
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bufferedWriter.write(content);
            bufferedWriter.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bool;
    }

    /**
     * @Method saveFile
     * TODO: 保存文件
     * @param filepath 文件名
     * @param inputStream 输入流
     * @Return file
     * @Exception
     * @Date 2019/11/5 上午11:06
     * @Author cjr7399
     */
    public static File saveFile(String filepath, InputStream inputStream){
        File file = new File(filepath);

        try {
            if (!file.exists()) {
                file.createNewFile();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = bufferedReader.readLine()) != null){
                    bufferedWriter.write(line);
                    bufferedWriter.flush();
                    bufferedWriter.newLine();
                }
                return file;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Method outputFile
     * TODO: CSV文件导出到输出流中
     * @param response 响应对象
     * @param fileName 文件名称
     * @param content 内容
     * @Return void
     * @Exception
     * @Date 2019/11/5 上午11:07
     * @Author cjr7399
     */
    public static void outputFile(HttpServletResponse response, String fileName, String content) throws Exception{
        saveFile(json_path + fileName, content);
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attchement;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        File file = new File(json_path + fileName);
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @Method uploadFile
     * TODO: 保存上传的文件
     * @param file
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     * @Exception
     * @Date 2019/11/5 上午11:10
     * @Author cjr7399
     */
    public static File uploadFile(MultipartFile file) throws Exception{
        String id = UUID.randomUUID().toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String times = dateFormat.format(new Date());
        String randomStr = ((int) (Math.random() * 9000 + 1000)) + "";
        String serverName = times + "_" + id + "_" + randomStr;
        serverName = serverName.replace("-","_");

        String originalFilename = file.getOriginalFilename();

        String[] split = originalFilename.split("\\.");
        String newName =  serverName + "." + split[1];
        return saveFile(json_path + newName, file.getInputStream());

/*        Map map = new HashMap();
        map.put("oldFileName", file.getName());
        map.put("newFileNam", newName);
        map.put("src", json_path);
        map.put("uploadId", id);*/
//        return  fileDTO;
    }

    /**
     * @Method parseObject
     * TODO: 将CSV文件 办照 指定类型 解析 成实体
     * @param fileName 文件名
     * @param c 实体类型
     * @Return java.lang.Object JSONObject or JSONArrayu or null
     * @Exception
     * @Date 2019/11/5 上午11:11
     * @Author cjr7399
     */
    public static Object parseObject(String fileName, Class c){
        File file = new File(fileName);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine())!= null){
                sb.append(line);
            }
            System.out.println(sb.toString());
            Object parse = null;
            try {
                parse = JSON.parseObject(sb.toString(), c);
                return parse;
            } catch (Exception e) {
                e.printStackTrace();
            }
            parse = JSON.parseArray(sb.toString(), c);
            return parse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * @Method readToString
     * TODO: 读取文本文件内容 已字符串返回
     * @param filename
     * @Return java.lang.String
     * @Exception
     * @Date 2019/11/7 上午10:00
     * @Author cjr7399
     */
    public static String readToString(String filename){
        String result = null;

        File file = new File(filename);
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = bf.readLine()) != null){
                sb.append(line);
            }
            result = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Boolean copyFile(String oldFileName, String newFileName){
        if(StringUtils.isEmpty(oldFileName) || StringUtils.isEmpty(newFileName))return Boolean.FALSE;
        File oldFile = new File(oldFileName);
        File newFile = new File(newFileName);
        if(oldFile.exists()){
            try {
                System.out.println("开始复制文件: " + oldFileName);
                newFile.createNewFile();
                InputStream is = new FileInputStream(oldFile);
                OutputStream os = new FileOutputStream(newFile);
                byte[] bytes = new byte[1024];
                int index = -1;
                while ((index = is.read(bytes)) != -1){
                    os.write(bytes,0 , index);
                    os.flush();
                }
                is.close();
                os.close();
                System.out.println(oldFileName + "=复制完成。");
                return Boolean.TRUE;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Boolean.FALSE;
    }
}
