package cn.yiban.service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by Kuexun on 2018/4/15.
 */
@WebServlet(name = "CountServlet")
public class CountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File excelFile = new File("D:\\aaa.xls");
        try {
            Workbook wb = Workbook.getWorkbook(excelFile);
            Sheet[] sheets = wb.getSheets();
            //遍历每页
            for(Sheet s : sheets){
                System.out.println(s.getName() + " : ");
                int rows = s.getRows();
                if(rows > 0){
                    //遍历每行
                    for(int i = 0 ;i < rows ; i++){
                        System.out.print("行" + i + " : ");
                        Cell[] cells = s.getRow(i);
                        if(cells.length > 0){
                            //遍历每行中的每列
                            for(Cell c : cells){
                                String str = c.getContents().trim();
                                System.out.print(str + " ; ");
                            }
                            System.out.println();
                        }
                    }
                }
            }
        } catch (BiffException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
