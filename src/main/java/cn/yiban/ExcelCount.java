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
        Map<String,Integer> count = new HashMap<String, Integer>();//Ψһ��ʶ��Ʊ���Ĺ�ϵ
        Map<String,Integer> mapper = new HashMap<String, Integer>();//ѧ��������ѧ����Ϣ���±��ӳ���ϵ
        String[][] infos = new String[1000][9];

        int num = 0;//infos�ĵ�num����¼
        File excelFile = new File("D:\\aa.xls");
        try {
            Workbook wb = Workbook.getWorkbook(excelFile);
            Sheet[] sheets = wb.getSheets();
            //����ÿҳ
            for(Sheet s : sheets){
                System.out.println(s.getName() + " : ");
                int rows = s.getRows();
                if(rows > 0){
                    //����ÿ��
                    for(int i = 1 ;i < rows ; i++){
                        Cell[] cells = s.getRow(i);
                        if(cells.length > 0){
                            //����ÿ���е�ÿ��
                            int index = 5;//ѧ�������кţ���ѧ�Ų�����ʱ���ֻ���ΪΨһ��ʶ����4
                            int sum = 0;//��¼��Ͷ����Ʊ��
                            if(cells[index].getContents().trim().equals("����"))
                            {
                                index = 4;
                            }
                            if(count.containsKey(cells[index].getContents().trim()))//����ñ�ʶ�ѳ��ֹ�
                            {
                                sum = count.get(cells[index].getContents().trim());
                            } else{//����ѧ����һ��ͶƱ
                                mapper.put(cells[index].getContents().trim(),num);
                                String[] info = new String[6];//������¼
                                int j = 0;
                                for(Cell cell :cells)
                                {
                                    info[j++] = cell.getContents().trim();
                                    if (j == 6)
                                        break;
                                  //  System.out.println(info[j-1]);
                                }
                                infos[num++] = info;//numΪ������¼���±�
                                //��ʱsum = 0��
                            }
                            sum += Integer.parseInt(cells[7].getContents().trim());
                            //System.out.println(sum);
                            count.put(cells[index].getContents().trim(),sum);//Ʊ����
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
            WritableSheet ws = wwb.createSheet("ͳ��", 0);
            //������ͷ
            ws.addCell(new Label(0,0,"�ǳ�"));
            ws.addCell(new Label(1,0,"��ʵ����"));
            ws.addCell(new Label(2,0,"ѧУ��Ϣ"));
            ws.addCell(new Label(3,0,"Ժϵ��Ϣ"));
            ws.addCell(new Label(4,0,"�ֻ�����"));
            ws.addCell(new Label(5,0,"�û�ѧ��"));
            ws.addCell(new Label(6,0,"��ͶƱ��"));
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
