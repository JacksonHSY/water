package com.ymkj.smi.manager.common.untils;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.ymkj.smi.manager.common.vo.ExcelBo;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @Description：Excel创建工具类
 * @ClassName: CreateExcelUtil.java
 * @Author：tianx
 * @Date：2017年8月7日
 * -----------------变更历史-----------------
 * 如：who  2017年8月7日  修改xx功能
 */
public class CreateExcelUtil {
	
	public static SXSSFWorkbook createExcel(ExcelBo excelBo){
		SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
		Sheet sheet = workbook.createSheet(excelBo.getSubTitle()); 
		
		/**设置字段*/
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short)10);
		font.setFontName("微软雅黑");
		font.setColor(HSSFColor.BLACK.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		/**设置单元格样式*/
		CellStyle headStyle = workbook.createCellStyle();
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headStyle.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
		headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		headStyle.setFont(font);
		
		CellStyle dataStyle = workbook.createCellStyle();
		dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dataStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		dataStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
		dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		dataStyle.setFont(font);
		//设置标题
		/*Row titleRow = sheet.createRow(0);
		Cell titleCell = titleRow.createCell(6);
		titleCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		titleCell.setCellValue(excelBo.getTitle());
		titleCell.setCellStyle(headStyle);*/
		
		//查询条件
		/*Row paramsRow = sheet.createRow(1);
		int paramCount=0;
		if(null != excelBo.getParams()){
			for (Entry<String, Object> entry : excelBo.getParams().entrySet()) {
				String key = entry.getKey();
				String value = (String) entry.getValue();
				Cell keyCell = paramsRow.createCell(paramCount);
				keyCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				keyCell.setCellValue(key);
				keyCell.setCellStyle(headStyle);
				
				Cell valueCell = paramsRow.createCell(paramCount+1);
				valueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				valueCell.setCellValue(value);
				valueCell.setCellStyle(headStyle);
				paramCount = paramCount+2;
			}
		}*/
		
		//表头
		Row headRow = sheet.createRow(0);
		if(null != excelBo.getThead()){
			for(int i=0;i<excelBo.getThead().length;i++){
				Cell cell = headRow.createCell(i);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(excelBo.getThead()[i]);
				cell.setCellStyle(headStyle);
			}
		}
		
		//填充数据
		if(null != excelBo.getData() && excelBo.getData().size()>0){
			for(int i=0;i<excelBo.getData().size();i++){
				Row dataRow = sheet.createRow(i+1);
				Map<Integer,Object> map = excelBo.getData().get(i);
				for(int j=0;j<map.keySet().size();j++){
					Cell cell = dataRow.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					if(null != map.get(j)){
						cell.setCellValue(map.get(j).toString());
					}else{
						cell.setCellValue("--");
					}
					cell.setCellStyle(dataStyle);
				}
				
			}
		}
		return workbook;
	}
	
	
}
