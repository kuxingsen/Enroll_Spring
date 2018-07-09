package cn.yiban;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Kuexun on 2018/4/15.
 */
public class ExcelCount {
    public static void main(String[] args) {
        Map<String,Integer> count = new HashMap<String, Integer>();//唯一标识与票数的关系
        Map<String,Integer> mapper = new HashMap<String, Integer>();//学号与所有学生信息的下标的映射关系
        String[][] infos = new String[1000][9];

        int num = 0;//infos的第num条记录
        File excelFile = new File("D:\\aa.xls");
        try {
            Workbook wb = Workbook.getWorkbook(excelFile);
            Sheet[] sheets = wb.getSheets();
            //遍历每页
            for(Sheet s : sheets){
                System.out.println(s.getName() + " : ");
                int rows = s.getRows();
                if(rows > 0){
                    //遍历每行
                    for(int i = 1 ;i < rows ; i++){
                        Cell[] cells = s.getRow(i);
                        if(cells.length > 0){
                            //遍历每行中的每列
                            int index = 5;//学号所在列号，当学号不存在时以手机号为唯一标识。即4
                            int sum = 0;//记录已投出的票数
                            if(cells[index].getContents().trim().equals("暂无"))
                            {
                                index = 4;
                            }
                            if(count.containsKey(cells[index].getContents().trim()))//如果该标识已出现过
                            {
                                sum = count.get(cells[index].getContents().trim());
                            } else{//即该学生第一次投票
                                mapper.put(cells[index].getContents().trim(),num);
                                String[] info = new String[6];//单条记录
                                int j = 0;
                                for(Cell cell :cells)
                                {
                                    info[j++] = cell.getContents().trim();
                                    if (j == 6)
                                        break;
                                  //  System.out.println(info[j-1]);
                                }
                                infos[num++] = info;//num为该条记录的下标
                                //此时sum = 0；
                            }
                            sum += Integer.parseInt(cells[7].getContents().trim());
                            //System.out.println(sum);
                            count.put(cells[index].getContents().trim(),sum);//票数加
                            }

                    }
                }
            }
        } catch (BiffException | IOException e) {
            e.printStackTrace();
        }
        try{
            File file = new File("D:\\writetest3.xls");
            WritableWorkbook wwb = Workbook.createWorkbook(file);
            WritableSheet ws = wwb.createSheet("统计", 0);
            //先做表头
            ws.addCell(new Label(0,0,"昵称"));
            ws.addCell(new Label(1,0,"真实姓名"));
            ws.addCell(new Label(2,0,"学校信息"));
            ws.addCell(new Label(3,0,"院系信息"));
            ws.addCell(new Label(4,0,"手机号码"));
            ws.addCell(new Label(5,0,"用户学号"));
            ws.addCell(new Label(6,0,"总投票数"));
            int i = 1;
            for(Map.Entry<String, Integer> mm : count.entrySet())
            {
                int index = mapper.get(mm.getKey());
                for(int j = 0;j < 6;j++)
                {
                    ws.addCell(new Label(j,i,infos[index][j]));
                }
                ws.addCell(new Label(6,i,mm.getValue().toString()));
                i++;
            }
            System.out.println(i);
            wwb.write();
            wwb.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
