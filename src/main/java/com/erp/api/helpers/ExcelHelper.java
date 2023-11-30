package com.erp.api.helpers;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.erp.api.dto.NaiComDBResposeDto;
import com.erp.api.exceptions.ValidationException;
import com.erp.api.models.BulkFileModel;
import com.erp.api.utility.UtilityFunctions;


@Component
public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";


    public static boolean hasExcelFormat(MultipartFile file) {

        return TYPE.equals(file.getContentType());
    }

//    public static List<BulkFileModel> excelToBulkFileModel(InputStream is) {
//        try {
//            Workbook workbook = new XSSFWorkbook(is);
//            DataFormatter formatter = new DataFormatter();
//            Sheet sheet = workbook.getSheetAt(0);
//            Iterator<Row> rows = sheet.iterator();
//
//            List<BulkFileModel> bulkFileModels = new ArrayList<BulkFileModel>();
//
//            int rowNumber = 0;
//            int rowNumberCount = 2;
//            while (rows.hasNext()) {
//                Row currentRow = rows.next();
//
//                // skip header
//                if(rowNumber ==0)
//                {
//                    rowNumber++;
//                    continue;
//                }
//                if(rowNumber ==1)
//                {
//                    verifyColumnNames(currentRow);
//                    rowNumber++;
//                    continue;
//                }
//               
//                rowNumberCount++;
//                BulkFileModel model = new BulkFileModel();
//
//                //sr no
//                int srNo = (int) currentRow.getCell(0).getNumericCellValue();
//                model.setSr_no(srNo);
//
//                // device serial no
//                String deviceSerialNo = currentRow.getCell(1).getStringCellValue();
//                if (!UtilityFunctions.validateDeviceSrNo(deviceSerialNo)) {
//                    throw new ValidationException("219", "Row " +rowNumberCount+",Device Serial is empty or invalid.", "Invalid Device Serial No.");
//                }
//                model.setDevice_serial_number(deviceSerialNo);
//
//                // device type
//                String deviceType = currentRow.getCell(2).getStringCellValue();
//                if (!UtilityFunctions.validateAlphaNumericWithSpace(deviceType)) {
//                    throw new ValidationException("220","Row " +rowNumberCount+",Device Type is empty or invalid.", "Invalid Device Type.");
//                }
//                model.setDevice_type(deviceType);
//
//
//                //device make
//                String deviceMake = currentRow.getCell(3).getStringCellValue();
//                if (!UtilityFunctions.validateAlphaNumericWithSpace(deviceMake)) {
//                    throw new ValidationException("221", "Row " +rowNumberCount+",Device Make is empty or invalid.", "Invalid Device Make.");
//                }
//                model.setDevice_make(deviceMake);
//
//
//                // device modal
//                String deviceModel = formatter.formatCellValue(currentRow.getCell(4));
//                if (!UtilityFunctions.validateAlphaNumericWithSpace(deviceModel)) {
//                    throw new ValidationException("222", "Row " +rowNumberCount+",Device Modal is empty or invalid.", "Invalid Device Modal.");
//                }
//                model.setDevice_modal(deviceModel);
//
//                // date of purchase
//                if (DateUtil.isCellDateFormatted(currentRow.getCell(5))) {
//                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                    Date dateOfPurchase = currentRow.getCell(5).getDateCellValue();
//                    Date today = new Date();
//                    if (dateOfPurchase.after(today)) {
//                        throw new ValidationException("223", "Row " +rowNumberCount+",Date of Purchase is empty or invalid.", "Invalid Date of Purchase.");
//                    }
//                    String date = format.format(dateOfPurchase);
//                    model.setDate_of_purchase(date);
//                }
//
//                //imei number
//                String imeiNumber = formatter.formatCellValue(currentRow.getCell(6));
//                if (!UtilityFunctions.validateAlphaNumeric(imeiNumber)) {
//                    throw new ValidationException("224", "Row " +rowNumberCount+",Imei No is empty or invalid.", "Invalid Imei No.");
//                }
//                model.setImei_number(imeiNumber);
//
//                //device value
//                double deviceValue = currentRow.getCell(7).getNumericCellValue();
//                model.setDevice_value(Double.parseDouble(BigDecimal.valueOf(deviceValue).toPlainString()));
//
//                //invoice url
//                String invoiceUrl = formatter.formatCellValue(currentRow.getCell(8));
//                if (Objects.isNull(invoiceUrl) || invoiceUrl.isEmpty()) {
//                    throw new ValidationException("225", "Row " +rowNumberCount+",Invoice URL is empty or invalid.", "Invalid Url No.");
//
//                }
//                model.setInvoice_url(invoiceUrl);
////                if("Y".equals(currentRow.getCell(9).getStringCellValue()) || "N".equals(currentRow.getCell(9).getStringCellValue()))
////                {
////                    model.setProof_of_purchase(Objects.equals(currentRow.getCell(9).getStringCellValue(), "Y"));
////                }
////                else{
////                    throw new ValidationException("201","Validation failed for the uploaded excel Sheet.","Gender should be M or F.");
////                }
//
//
//                //email
//                String email = formatter.formatCellValue(currentRow.getCell(9));
//                if (!UtilityFunctions.validateEmail(email)) {
//                    throw new ValidationException("226", "Row " +rowNumberCount+",Email is empty or invalid.", "Invalid Email.");
//                }
//                model.setEmail(email);
//
//                //phone number
//                long phoneNo = (long) currentRow.getCell(10).getNumericCellValue();
//                model.setPhone_number(String.valueOf(phoneNo));
//
//                // id type
//                String s = currentRow.getCell(11).getStringCellValue();
//                if (!UtilityFunctions.validateIdType(s)) {
//                    throw new ValidationException("227", "Row " +rowNumberCount+",ID Type is empty or invalid.", "Invalid ID Type.");
//                }
//                model.setId_type(s);
//
//                // id number
//                String idNo = formatter.formatCellValue(currentRow.getCell(12));
//                if (idNo.isEmpty()) {
//                    throw new ValidationException("228", "Row " +rowNumberCount+",ID Number is empty or invalid.", "Invalid ID No.");
//
//                }
//                model.setId_number(idNo);
//
//                //first name
//                if (!UtilityFunctions.validateAlpha(currentRow.getCell(13).getStringCellValue())) {
//                    throw new ValidationException("229", "Row " +rowNumberCount+",First Name is empty or invalid.", "Invalid First Name.");
//                }
//                model.setFirst_name(currentRow.getCell(13).getStringCellValue());
//
//                // last name
//                if (!UtilityFunctions.validateAlphaNumericWithSpace(currentRow.getCell(14).getStringCellValue())) {
//                    throw new ValidationException("230", "Row " +rowNumberCount+",Last Name is empty or invalid.", "Invalid Last Name.");
//                }
//                model.setLast_name(currentRow.getCell(14).getStringCellValue());
//
//                // dob
//                if (DateUtil.isCellDateFormatted(currentRow.getCell(15))) {
//                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                    Date dob = currentRow.getCell(15).getDateCellValue();
//                    Date today = new Date();
//                    if (dob.after(today)) {
//                        throw new ValidationException("231", "Row " +rowNumberCount+",DOB is empty or invalid.", "Invalid DOB.");
//
//                    }
//                    String date = format.format(dob);
//                    model.setDob(date);
//                }
//
//                //gender
//                if ("M".equals(currentRow.getCell(16).getStringCellValue()) || "F".equals(currentRow.getCell(16).getStringCellValue())) {
//                    model.setGender(formatter.formatCellValue(currentRow.getCell(16)));
//                } else {
//                    throw new ValidationException("232", "Row" +rowNumberCount+",Gender is empty or invalid.", "Gender should be M or F.");
//                }
//
//                // device premium
//                double premiumAmount = currentRow.getCell(17).getNumericCellValue();
//                model.setDevice_premium_amount(String.valueOf(premiumAmount));
//
//                bulkFileModels.add(model);
//            }
//
//            workbook.close();
//
//            return bulkFileModels;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }

    
    
    public static List<BulkFileModel> excelToBulkFileModel(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            DataFormatter formatter = new DataFormatter();
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<BulkFileModel> bulkFileModels = new ArrayList<BulkFileModel>();

            int rowNumber = 0;
            int rowNumberCount = 2;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if(rowNumber ==0)
                {
                    rowNumber++;
                    continue;
                }
                if(rowNumber ==1)
                {
                    verifyColumnNames(currentRow);
                    rowNumber++;
                    continue;
                }
               
                rowNumberCount++;
                BulkFileModel model = new BulkFileModel();

                //sr no
                int srNo = (int) currentRow.getCell(0).getNumericCellValue();
                model.setSr_no(srNo);

                // device serial no
                String deviceSerialNo = currentRow.getCell(1).getStringCellValue();
                if (!UtilityFunctions.validateDeviceSrNo(deviceSerialNo)) {
//                	model.setErrorCode("219");
//                	model.setErrorMessage("Row " +rowNumberCount+",Device Serial is empty or invalid.");
                    throw new ValidationException("219", "Row #" +rowNumberCount+"'s Device Serial field is either empty or contain invalid data!", "Invalid Device Serial No.");
                    
                }
                model.setDevice_serial_number(deviceSerialNo);

                // device type
                String deviceType = currentRow.getCell(2).getStringCellValue();
                if (!UtilityFunctions.validateAlphaNumericWithSpace(deviceType)) {
                    throw new ValidationException("220","Row #" +rowNumberCount+"'s Device Type field is either empty or contain invalid data!", "Invalid Device Type.");
                }
                model.setDevice_type(deviceType);


                //device make
                String deviceMake = currentRow.getCell(3).getStringCellValue();
                if (!UtilityFunctions.validateAlphaNumericWithSpace(deviceMake)) {
                    throw new ValidationException("221", "Row #" +rowNumberCount+"'s Device Make field is either empty or contain invalid data!", "Invalid Device Make.");
                }
                model.setDevice_make(deviceMake);


                // device modal
                String deviceModel = formatter.formatCellValue(currentRow.getCell(4));
                if (!UtilityFunctions.validateAlphaNumericWithSpace(deviceModel)) {
                    throw new ValidationException("222", "Row #" +rowNumberCount+"'s Device Modal field is either empty or contain invalid data!", "Invalid Device Modal.");
                }
                model.setDevice_modal(deviceModel);

                // date of purchase
                if (DateUtil.isCellDateFormatted(currentRow.getCell(5))) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateOfPurchase = currentRow.getCell(5).getDateCellValue();
                    Date today = new Date();
                    if (dateOfPurchase.after(today)) {
                        throw new ValidationException("223", "Row #" +rowNumberCount+"'s Date of Purchase field is either empty or contain invalid data!", "Invalid Date of Purchase.");
                    }
                    String date = format.format(dateOfPurchase);
                    model.setDate_of_purchase(date);
                }

                //imei number
                String imeiNumber = formatter.formatCellValue(currentRow.getCell(6));
                if (!UtilityFunctions.validateAlphaNumeric(imeiNumber)) {
                    throw new ValidationException("224", "Row #" +rowNumberCount+"'s Imei No field is either empty or contain invalid data!", "Invalid Imei No.");
                }
                model.setImei_number(imeiNumber);

                //device value
                double deviceValue = currentRow.getCell(7).getNumericCellValue();
                model.setDevice_value(Double.parseDouble(BigDecimal.valueOf(deviceValue).toPlainString()));

                //invoice url
                String invoiceUrl = formatter.formatCellValue(currentRow.getCell(8));
                if (Objects.isNull(invoiceUrl) || invoiceUrl.isEmpty()) {
                    throw new ValidationException("225", "Row #" +rowNumberCount+"'s Invoice URL field is either empty or contain invalid data!", "Invalid Url No.");

                }
                model.setInvoice_url(invoiceUrl);
                //email
                String email = formatter.formatCellValue(currentRow.getCell(9));
                if (!UtilityFunctions.validateEmail(email)) {
                    throw new ValidationException("226", "Row #" +rowNumberCount+"'s Email field is either empty or contain invalid data!", "Invalid Email.");
                }
                model.setEmail(email);

                //phone number
                long phoneNo = (long) currentRow.getCell(10).getNumericCellValue();
                model.setPhone_number(String.valueOf(phoneNo));

                // id type
                String s = currentRow.getCell(11).getStringCellValue();
                if (!UtilityFunctions.validateIdType(s)) {
                    throw new ValidationException("227", "Row #" +rowNumberCount+"'s ID Type field is either empty or contain invalid data!", "Invalid ID Type.");
                }
                model.setId_type(s);

                // id number
                String idNo = formatter.formatCellValue(currentRow.getCell(12));
                if (idNo.isEmpty()) {
                    throw new ValidationException("228", "Row #" +rowNumberCount+"'s ID Number field is either empty or contain invalid data!", "Invalid ID No.");

                }
                model.setId_number(idNo);

                //first name
                if (!UtilityFunctions.validateAlpha(currentRow.getCell(13).getStringCellValue())) {
                    throw new ValidationException("229", "Row #" +rowNumberCount+"'s First Name field is either empty or contain invalid data!", "Invalid First Name.");
                }
                model.setFirst_name(currentRow.getCell(13).getStringCellValue());

                // last name
                if (!UtilityFunctions.validateAlphaNumericWithSpace(currentRow.getCell(14).getStringCellValue())) {
                    throw new ValidationException("230", "Row #" +rowNumberCount+"'s Last Name field is either empty or contain invalid data!", "Invalid Last Name.");
                }
                model.setLast_name(currentRow.getCell(14).getStringCellValue());

                // dob
                if (DateUtil.isCellDateFormatted(currentRow.getCell(15))) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date dob = currentRow.getCell(15).getDateCellValue();
                    Date today = new Date();
                    if (dob.after(today)) {
                        throw new ValidationException("231", "Row #" +rowNumberCount+"'s DOB field is either empty or contain invalid data!", "Invalid DOB.");

                    }
                    
                    String date = format.format(dob);
                    model.setDob(date);
                }

                //gender
                if ("M".equals(currentRow.getCell(16).getStringCellValue()) || "F".equals(currentRow.getCell(16).getStringCellValue())) {
                    model.setGender(formatter.formatCellValue(currentRow.getCell(16)));
                } else {
                    throw new ValidationException("232", "Row #" +rowNumberCount+"'s Gender field is either empty or contain invalid data!", "Gender should be M or F.");
                }

                // device premium
                double premiumAmount = currentRow.getCell(17).getNumericCellValue();
                model.setDevice_premium_amount(String.valueOf(premiumAmount));

                bulkFileModels.add(model);
            }

            workbook.close();

            return bulkFileModels;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public static Boolean verifyColumnNames(Row currentRow) {
        if (!"ID".equals(currentRow.getCell(0).getStringCellValue())) {
            throw new ValidationException("201", "Row 2,ID is empty or invalid.", "Invalid columns.");
        } else if (!"Device Serial #".equals(currentRow.getCell(1).getStringCellValue())) {
            throw new ValidationException("202", "Row 2,Device Serial #  is empty or invalid.", "Invalid columns.");

        } else if (!"Type".equals(currentRow.getCell(2).getStringCellValue())) {
            throw new ValidationException("203", "Row 2,Type is empty or invalid.", "Invalid columns.");

        } else if (!"Make".equals(currentRow.getCell(3).getStringCellValue())) {
            throw new ValidationException("204", "Row 2,Make is empty or invalid.", "Invalid columns.");

        } else if (!"Modal".equals(currentRow.getCell(4).getStringCellValue())) {
            throw new ValidationException("205", "Row 2,Modal is empty or invalid.", "Invalid columns.");

        } else if (!"DateOfPurchase".equals(currentRow.getCell(5).getStringCellValue())) {
            throw new ValidationException("206", "Row 2,DateOfPurchase is empty or invalid.", "Invalid columns.");

        } else if (!"IMEI".equals(currentRow.getCell(6).getStringCellValue())) {
            throw new ValidationException("207", "Row 2,IMEI is empty or invalid.", "Invalid columns.");

        } else if (!"Device Value (₦)".equals(currentRow.getCell(7).getStringCellValue())) {
            throw new ValidationException("208", "Row 2,Device Value (₦)  is empty or invalid.", "Invalid columns.");

        } else if (!"InvoiceURL".equals(currentRow.getCell(8).getStringCellValue())) {
            throw new ValidationException("209", "Row 2,InvoiceURL is empty or invalid.", "Invalid columns.");

        } else if (!"Email".equals(currentRow.getCell(9).getStringCellValue())) {
            throw new ValidationException("210", "Row 2,Email is empty or invalid.", "Invalid columns.");

        } else if (!"MobileNumber".equals(currentRow.getCell(10).getStringCellValue())) {
            throw new ValidationException("211", "Row 2,MobileNumber is empty or invalid.", "Invalid columns.");

        } else if (!"IDType".equals(currentRow.getCell(11).getStringCellValue())) {
            throw new ValidationException("212", "Row 2,IDType is empty or invalid.", "Invalid columns.");

        } else if (!"IDNumber".equals(currentRow.getCell(12).getStringCellValue())) {
            throw new ValidationException("213", "Row 2,IDNumber is empty or invalid.", "Invalid columns.");

        }else if (!"Title".equals(currentRow.getCell(13).getStringCellValue())) {
            throw new ValidationException("214", "Row 2,Title is empty or invalid.", "Invalid columns.");

        } else if (!"FirstName".equals(currentRow.getCell(14).getStringCellValue())) {
            throw new ValidationException("215", "Row 2,FirstName is empty or invalid.", "Invalid columns.");

        } else if (!"MiddleName".equals(currentRow.getCell(15).getStringCellValue())) {
            throw new ValidationException("216", "Row 2,MiddleName is empty or invalid.", "Invalid columns.");

        }else if (!"LastName".equals(currentRow.getCell(16).getStringCellValue())) {
            throw new ValidationException("217", "Row 2,LastName is empty or invalid.", "Invalid columns.");

        } else if (!"dob".equals(currentRow.getCell(17).getStringCellValue())) {
            throw new ValidationException("218", "Row 2,dob is empty or invalid.", "Invalid columns.");

        } else if (!"Gender".equals(currentRow.getCell(18).getStringCellValue())) {
            throw new ValidationException("219", "Row 2,Gender is empty or invalid.", "Invalid columns.");

        } 

        return true;

    }

	public static List<BulkFileModel> excelToBulkFileModelQuotation(InputStream inputStream, String token,
			NaiComDBResposeDto responseeeDto) {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            DataFormatter formatter = new DataFormatter();
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<BulkFileModel> bulkFileModels = new ArrayList<BulkFileModel>();

            int rowNumber = 0;
            int rowNumberCount = 2;
            int count=0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if(rowNumber ==0)
                {
                    rowNumber++;
                    continue;
                }
                if(rowNumber ==1)
                {
                    verifyColumnNames(currentRow);
                    rowNumber++;
                    continue;
                }
               
                rowNumberCount++;
                BulkFileModel model = new BulkFileModel();
                //sr no
                int srNo = (int) currentRow.getCell(0).getNumericCellValue();
                model.setSr_no(srNo);

                // device serial no
                String deviceSerialNo = currentRow.getCell(1).getStringCellValue();
                if (!UtilityFunctions.validateDeviceSrNo(deviceSerialNo)) {
                    throw new ValidationException("219", "Row #" +rowNumberCount+"'s Device Serial field is either empty or contain invalid data!", "Invalid Device Serial No.");
                }
                model.setDevice_serial_number(deviceSerialNo);

                // device type
                String deviceType = currentRow.getCell(2).getStringCellValue();
                if (!UtilityFunctions.validateAlphaNumericWithSpace(deviceType)) {
                    throw new ValidationException("220","Row #" +rowNumberCount+"'s Device Type field is either empty or contain invalid data!", "Invalid Device Type.");
                }
                model.setDevice_type(deviceType);


                //device make
                String deviceMake = currentRow.getCell(3).getStringCellValue();
                if (!UtilityFunctions.validateAlphaNumericWithSpace(deviceMake)) {
                    throw new ValidationException("221", "Row #" +rowNumberCount+"'s Device Make field is either empty or contain invalid data!", "Invalid Device Make.");
                }
                model.setDevice_make(deviceMake);


                // device modal
                String deviceModel = formatter.formatCellValue(currentRow.getCell(4));
                if (!UtilityFunctions.validateAlphaNumericWithSpace(deviceModel)) {
                    throw new ValidationException("222", "Row #" +rowNumberCount+"'s Device Modal field is either empty or contain invalid data!", "Invalid Device Modal.");
                }
                model.setDevice_modal(deviceModel);

                // date of purchase
                if (DateUtil.isCellDateFormatted(currentRow.getCell(5))) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateOfPurchase = currentRow.getCell(5).getDateCellValue();
                    Date today = new Date();
                    if (dateOfPurchase.after(today)) {
                        throw new ValidationException("223", "Row #" +rowNumberCount+"'s Date of Purchase field should not be future date!", "Invalid Date of Purchase.");
                    }
                    if (checkDate(format.format(dateOfPurchase)) == false) {
                        throw new ValidationException("290", "Row #" +rowNumberCount+"'s Date of Purchase field is allow only last 30 days from the current date!", "Invalid Date of Purchase.");
                    }
                    String date = format.format(dateOfPurchase);
                    model.setDate_of_purchase(date);
                }

                //imei number
                String imeiNumber = formatter.formatCellValue(currentRow.getCell(6));
                if (!UtilityFunctions.validateAlphaNumeric(imeiNumber)) {
                    throw new ValidationException("224", "Row #" +rowNumberCount+"'s Imei No field is either empty or contain invalid data!", "Invalid Imei No.");
                }
                model.setImei_number(imeiNumber);

                //device value
                double deviceValue = currentRow.getCell(7).getNumericCellValue();
                model.setDevice_value(Double.parseDouble(BigDecimal.valueOf(deviceValue).toPlainString()));

                //invoice url
                String invoiceUrl = formatter.formatCellValue(currentRow.getCell(8));
                if (Objects.isNull(invoiceUrl) || invoiceUrl.isEmpty()) {
                    throw new ValidationException("225", "Row #" +rowNumberCount+"'s Invoice URL field is either empty or contain invalid data!", "Invalid Url No.");

                }
                model.setInvoice_url(invoiceUrl);

                //email
                String email = formatter.formatCellValue(currentRow.getCell(9));
                if (!UtilityFunctions.validateEmail(email)) {
                    throw new ValidationException("226", "Row #" +rowNumberCount+"'s Email field is either empty or contain invalid data!", "Invalid Email.");
                }
                model.setEmail(email);

                //phone number
                String phoneNo = currentRow.getCell(10).getStringCellValue();
                if (!UtilityFunctions.validateMobile(phoneNo)) {
                    throw new ValidationException("291", "Row #" +rowNumberCount+"'s Phone Number field is either empty or contain invalid data!", "Invalid Imei No.");
                }
                model.setPhone_number(String.valueOf(phoneNo));

                // id type
                if(responseeeDto.getKycStatus() ==1) {
                String s = currentRow.getCell(11).getStringCellValue();
                if (!UtilityFunctions.validateIdType(s)) {
                    throw new ValidationException("227", "Row #" +rowNumberCount+"'s ID Type field is either empty or contain invalid data!", "Invalid ID Type.");
                }
                model.setId_type(s);

                // id number
                String idNo = formatter.formatCellValue(currentRow.getCell(12));
                if (idNo.isEmpty()) {
                    throw new ValidationException("228", "Row #" +rowNumberCount+"'s ID Number field is either empty or contain invalid data!", "Invalid ID No.");

                }
                model.setId_number(idNo);
                }else {
                	String s = currentRow.getCell(11).getStringCellValue();
                	model.setId_type(s);
                	String idNo = formatter.formatCellValue(currentRow.getCell(12));
                	model.setId_number(idNo);
                }

                
                String title = formatter.formatCellValue(currentRow.getCell(13));
                model.setTitle(title);
                
                //first name
                if (!UtilityFunctions.validateFirstName(currentRow.getCell(14).getStringCellValue())) {
                    throw new ValidationException("229", "Row #" +rowNumberCount+"'s First Name field is either empty or contain invalid data!", "Invalid First Name.");
                }
                model.setFirst_name(currentRow.getCell(14).getStringCellValue());
                
                
                String middleName = formatter.formatCellValue(currentRow.getCell(15));
                model.setMiddle_name(middleName);

                // last name
                if (!UtilityFunctions.validateFirstName(currentRow.getCell(16).getStringCellValue())) {
                    throw new ValidationException("230", "Row #" +rowNumberCount+"'s Last Name field is either empty or contain invalid data!", "Invalid Last Name.");
                }
                model.setLast_name(currentRow.getCell(16).getStringCellValue());

                // dob
                if (DateUtil.isCellDateFormatted(currentRow.getCell(17))) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date dob = currentRow.getCell(17).getDateCellValue();
                    Date today = new Date();
                    if (dob.after(today)) {
                        throw new ValidationException("231", "Row #" +rowNumberCount+"'s DOB field is either empty or contain invalid data!", "Invalid DOB.");

                    }
                    String date = format.format(dob);
                    model.setDob(date);
                }

                //gender
                if ("M".equals(currentRow.getCell(18).getStringCellValue()) || "F".equals(currentRow.getCell(18).getStringCellValue())) {
                    model.setGender(formatter.formatCellValue(currentRow.getCell(18)));
                } else {
                    throw new ValidationException("232", "Row #" +rowNumberCount+"'s Gender field is either empty or contain invalid data!", "Gender should be M or F.");
                }


                bulkFileModels.add(model);
            }

            workbook.close();

            return bulkFileModels;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
//	public static boolean checkDate(String date) {
//		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		LocalDate datecheck = LocalDate.parse(date);
//		LocalDate curDate = LocalDate.now();
//		int years=Period.between(datecheck,curDate).getYears();
//		if(years>0) {
//			return false;
//		}else {
//			int months=Period.between(datecheck,curDate).getMonths();
//			if(months>1) {
//				return false;
//			}else {
//				int days=Period.between(datecheck,curDate).getDays();
//				if(months==1) {
//					if(days>0) {
//						return false;
//					}else {
//						return true;
//					}
//				}else {
//					return true;
//				}
//			}
//		}
//		
//		
//	}
	
	
//	-------------- check 30 before and after
//	String testDate="2023-11-26";
//	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//	Date dateOfPurchase = java.sql.Date.valueOf(testDate);
//	
//	
//	LocalDate curDate = LocalDate.now().plusDays(30);
//	Date today = java.sql.Date.valueOf(curDate);
//	
//	LocalDate minusDate = LocalDate.now().minusDays(30);
//	Date mintoday = java.sql.Date.valueOf(minusDate);
//
//	System.out.println("------ww------------"+mintoday);
//	if (dateOfPurchase.before(today) || dateOfPurchase.after(mintoday)) {
//		System.out.println("----eeee-------------");
//		//continue;
//	}
	
	public static boolean checkDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = sdf.parse(date);
			Date d3 = new Date();
			long difference_In_Time = d3.getTime() - d1.getTime();
			long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));
			long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

			if (difference_In_Years > 0) {
				return false;
			} else {
				if (difference_In_Days >= 30) {
					return false;
				} else {
					return true;
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

	}
}
