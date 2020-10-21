package com.apple.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.apple.constant.MessageConstant;
import com.apple.entity.Result;
import com.apple.service.ReportService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;
/**
 * @author pengmin
 * @date 2020/10/18 16:43
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private ReportService reportService;

    @RequestMapping(value = "/getMemberReport",method = RequestMethod.GET)
    public Result getMemberReport(){
        try {
            Map map = reportService.getMemberReport();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    @RequestMapping(value = "/getSetmealReport",method = RequestMethod.GET)
    public Result getSetmealReport(){
        try {
            Map map = reportService.getSetmealReport();
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    @RequestMapping(value = "/getBusinessReportData",method = RequestMethod.GET)
    public Result getBusinessReportData(){
        try {
            Map map = reportService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }


    @RequestMapping(value = "/exportBusinessReport",method = RequestMethod.GET)
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        try {
            Map map = reportService.getBusinessReportData();
            String templateFileName = request.getSession().getServletContext().getRealPath("template")+ File.separator+"report_template.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(templateFileName)));
            XLSTransformer xlsTransformer = new XLSTransformer();
            xlsTransformer.transformWorkbook(workbook,map);
            OutputStream outputStream =response.getOutputStream();
            //设置文件类型
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            //设置文件名称
            response.setHeader("content-Disposition","attachment;filename=abc.xlsx");
            workbook.write(outputStream);
            workbook.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EXPORT_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping(value = "/exportBusinessPDFReport",method = RequestMethod.GET)
    public Result exportBusinessPdfReport(HttpServletRequest request,HttpServletResponse response){
        try {
            Map map = reportService.getBusinessReportData();
            List<Map> hotSetmeal = (List<Map>) map.get("hotSetmeal");
            String jrxmlPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "exportBusiness.jrxml";
            String jasperPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "exportBusiness.jasper";
            JasperCompileManager.compileReportToFile(jrxmlPath,jasperPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, map, new JRBeanCollectionDataSource(hotSetmeal));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setHeader("content-Disposition", "attachment;filename=exportBusiness.pdf");
            //输出文件
            JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EXPORT_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping(value = "/makePdfReport",method = RequestMethod.GET)
    public Result makePdfReport(HttpServletRequest request,HttpServletResponse response){
        try {
            Map map = reportService.getBusinessReportData();
            List<Map> hotSetmeal = (List<Map>) map.get("hotSetmeal");
            String jrxmlPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "exportBusiness.jrxml";
            String jasperPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "exportBusiness.jasper";
            JasperCompileManager.compileReportToFile(jrxmlPath,jasperPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, map, new JRBeanCollectionDataSource(hotSetmeal));
            String resFile =  request.getSession().getServletContext().getRealPath("file")+ File.separator + "exportBusiness.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint,resFile);
            return new Result(true,MessageConstant.EXPORT_BUSINESS_REPORT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EXPORT_BUSINESS_REPORT_FAIL);
        }
    }
}
