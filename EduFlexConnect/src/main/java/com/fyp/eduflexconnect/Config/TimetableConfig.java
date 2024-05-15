package com.fyp.eduflexconnect.Config;


import com.fyp.eduflexconnect.Models.Timetable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TimetableConfig
{
    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();

        assert contentType != null;
        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }
    }
    public static List<Timetable> convertExcelToListStudent(InputStream is, String sec){
        List<Timetable> list = new ArrayList<>();
        System.out.println("section id in fun"+sec);
        try
        {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            int day =1 ;

            while(day<6)
            {

                XSSFSheet sheet = workbook.getSheet(String.valueOf(day));
                day++;
                int rownumber = 0;
                Iterator<Row> iterator = sheet.iterator();
                while (iterator.hasNext()) {
                    Row row = iterator.next();
                    if (rownumber == 0) {
                        rownumber++;
                        continue;
                    }
                    Iterator<Cell> cells = row.iterator();
                    int cid = 0;
                    Timetable T = new Timetable();

                    while (cells.hasNext())
                    {
                        Cell cell = cells.next();

                        String subval;
                        switch (cid) {
                            case 0:
//                                int value = cell.getNumericCellValue();
//                                T.setPlace(value);
                                break;

                            case 1:
                                String val1 = cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts = val1.split(" ");

                                if(parts.length >=2)
                                {
                                    System.out.println(parts[1]);
                                    if(parts[1].equals(sec))
                                    {
                                        T.setTime1(val1);
                                    }
                                    else
                                    {
                                        T.setTime1("-");
                                    }
                                }
                                else
                                {
                                    T.setTime1("-");
                                }

                                break;
                            case 2:
                                String val2 = cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts2 = val2.split(" ");

                                if(parts2.length >=2)
                                {
                                    System.out.println(parts2[1]);
                                    if(parts2[1].equals(sec))
                                    {
                                        T.setTime2(val2);
                                    }
                                    else
                                    {
                                        T.setTime2("-");
                                    }
                                }
                                else
                                {
                                    T.setTime2("-");
                                }

                                break;
                            case 3:
                                String val3 = cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts3 = val3.split(" ");

                                if(parts3.length >=2)
                                {
                                    System.out.println(parts3[1]);
                                    if(parts3[1].equals(sec))
                                    {
                                        T.setTime3(val3);
                                    }
                                    else
                                    {
                                        T.setTime3("-");
                                    }
                                }
                                else
                                {
                                    T.setTime3("-");
                                }

                                break;
                            case 4:
                                String val4 = cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts4 = val4.split(" ");

                                if(parts4.length >=2)
                                {
                                    System.out.println(parts4[1]);
                                    if(parts4[1].equals(sec))
                                    {
                                        T.setTime4(val4);
                                    }
                                    else
                                    {
                                        T.setTime4("-");
                                    }
                                }
                                else
                                {
                                    T.setTime4("-");
                                }

                                break;
                            case 5:
                                String val5 = cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts5 = val5.split(" ");

                                if(parts5.length >=2)
                                {
                                    System.out.println(parts5[1]);
                                    if(parts5[1].equals(sec))
                                    {
                                        T.setTime5(val5);
                                    }
                                    else
                                    {
                                        T.setTime5("-");
                                    }
                                }
                                else
                                {
                                    T.setTime5("-");
                                }

                                break;
                            case 6:
                                String val6 = cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts6 = val6.split(" ");

                                if(parts6.length >=2)
                                {
                                    System.out.println(parts6[1]);
                                    if(parts6[1].equals(sec))
                                    {
                                        T.setTime6(val6);
                                    }
                                    else
                                    {
                                        T.setTime6("-");
                                    }
                                }
                                else
                                {
                                    T.setTime6("-");
                                }

                                break;
                            case 7:
                                String val7 = cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts7 = val7.split(" ");

                                if(parts7.length >=2)
                                {
                                    System.out.println(parts7[1]);
                                    if(parts7[1].equals(sec))
                                    {
                                        T.setTime7(val7);
                                    }
                                    else
                                    {
                                        T.setTime7("-");
                                    }
                                }
                                else
                                {
                                    T.setTime7("-");
                                }

                                break;
                            case 8:
                                String val8= cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts8 = val8.split(" ");

                                if(parts8.length >=2)
                                {
                                    System.out.println(parts8[1]);
                                    if(parts8[1].equals(sec))
                                    {
                                        T.setTime8(val8);
                                    }
                                    else
                                    {
                                        T.setTime8("-");
                                    }
                                }
                                else
                                {
                                    T.setTime8("-");
                                }

                                break;


                            default:
                                break;
                        }
                        cid++;

                    }
                    list.add(T);
                }
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }



    public static List<Timetable> convertExcelToListTeacher(InputStream is, String teacherName){
        List<Timetable> list = new ArrayList<>();
        System.out.println("section id in fun"+teacherName);
        try
        {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            int day =1 ;

            while(day<6)
            {

                XSSFSheet sheet = workbook.getSheet(String.valueOf(day));
                day++;
                int rownumber = 0;
                Iterator<Row> iterator = sheet.iterator();
                while (iterator.hasNext()) {
                    Row row = iterator.next();
                    if (rownumber == 0) {
                        rownumber++;
                        continue;
                    }
                    Iterator<Cell> cells = row.iterator();
                    int cid = 0;
                    Timetable T = new Timetable();

                    while (cells.hasNext())
                    {
                        Cell cell = cells.next();

                        String subval;
                        switch (cid) {
                            case 0:
                                String value = cell.getStringCellValue();
                                T.setPlace(value);
                                break;

                            case 1:
                                String val1 = cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts = val1.split(" ");

                                if(parts.length >=2)
                                {
                                    System.out.println(parts[2]);
                                    if(parts[2].equals(teacherName))
                                    {
                                        T.setTime1(val1);
                                    }
                                    else
                                    {
                                        T.setTime1("-");
                                    }
                                }
                                else
                                {
                                    T.setTime1("-");
                                }

                                break;
                            case 2:
                                String val2 = cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts2 = val2.split(" ");

                                if(parts2.length >=2)
                                {
                                    System.out.println(parts2[2]);
                                    if(parts2[2].equals(teacherName))
                                    {
                                        T.setTime2(val2);
                                    }
                                    else
                                    {
                                        T.setTime2("-");
                                    }
                                }
                                else
                                {
                                    T.setTime2("-");
                                }

                                break;
                            case 3:
                                String val3 = cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts3 = val3.split(" ");

                                if(parts3.length >=2)
                                {
                                    System.out.println(parts3[2]);
                                    if(parts3[2].equals(teacherName))
                                    {
                                        T.setTime3(val3);
                                    }
                                    else
                                    {
                                        T.setTime3("-");
                                    }
                                }
                                else
                                {
                                    T.setTime3("-");
                                }

                                break;
                            case 4:
                                String val4 = cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts4 = val4.split(" ");

                                if(parts4.length >=2)
                                {
                                    System.out.println(parts4[2]);
                                    if(parts4[2].equals(teacherName))
                                    {
                                        T.setTime4(val4);
                                    }
                                    else
                                    {
                                        T.setTime4("-");
                                    }
                                }
                                else
                                {
                                    T.setTime4("-");
                                }

                                break;
                            case 5:
                                String val5 = cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts5 = val5.split(" ");

                                if(parts5.length >=2)
                                {
                                    System.out.println(parts5[1]);
                                    if(parts5[2].equals(teacherName))
                                    {
                                        T.setTime5(val5);
                                    }
                                    else
                                    {
                                        T.setTime5("-");
                                    }
                                }
                                else
                                {
                                    T.setTime5("-");
                                }

                                break;
                            case 6:
                                String val6 = cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts6 = val6.split(" ");

                                if(parts6.length >=2)
                                {
                                    System.out.println(parts6[1]);
                                    if(parts6[2].equals(teacherName))
                                    {
                                        T.setTime6(val6);
                                    }
                                    else
                                    {
                                        T.setTime6("-");
                                    }
                                }
                                else
                                {
                                    T.setTime6("-");
                                }

                                break;
                            case 7:
                                String val7 = cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts7 = val7.split(" ");

                                if(parts7.length >=2)
                                {
                                    System.out.println(parts7[1]);
                                    if(parts7[2].equals(teacherName))
                                    {
                                        T.setTime7(val7);
                                    }
                                    else
                                    {
                                        T.setTime7("-");
                                    }
                                }
                                else
                                {
                                    T.setTime7("-");
                                }

                                break;
                            case 8:
                                String val8= cell.getStringCellValue();
//                                System.out.println(val1);
                                String[] parts8 = val8.split(" ");

                                if(parts8.length >=2)
                                {
                                    System.out.println(parts8[1]);
                                    if(parts8[2].equals(teacherName))
                                    {
                                        T.setTime8(val8);
                                    }
                                    else
                                    {
                                        T.setTime8("-");
                                    }
                                }
                                else
                                {
                                    T.setTime8("-");
                                }

                                break;


                            default:
                                break;
                        }
                        cid++;

                    }
                    list.add(T);
                }
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

}

