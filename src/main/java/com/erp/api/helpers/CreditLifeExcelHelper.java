package com.erp.api.helpers;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.erp.api.dto.CreditLifeExcelDto;
import com.erp.api.exceptions.ValidationException;
import com.erp.api.utility.UtilityFunctions;


@Component
public class CreditLifeExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";


    public static boolean hasExcelFormat(MultipartFile file) {

        return TYPE.equals(file.getContentType());
    }

    
    public static Boolean verifyColumnNamesCreditLife(Row currentRow) {
        if (!"ID".equals(currentRow.getCell(0).getStringCellValue())) {
            throw new ValidationException("CL-201", "Row 2,ID is empty or invalid.", "Invalid columns.");
        } else if (!"Title".equals(currentRow.getCell(1).getStringCellValue())) {
            throw new ValidationException("CL-202", "Row 2,Title is empty or invalid.", "Invalid columns.");

        } else if (!"FirstName".equals(currentRow.getCell(2).getStringCellValue())) {
            throw new ValidationException("CL-203", "Row 2,First Name is empty or invalid.", "Invalid columns.");

        } else if (!"MiddleName".equals(currentRow.getCell(3).getStringCellValue())) {
            throw new ValidationException("CL-204", "Row 2,Middle Name is empty or invalid.", "Invalid columns.");

        } else if (!"LastName".equals(currentRow.getCell(4).getStringCellValue())) {
            throw new ValidationException("CL-205", "Row 2,Last Name is empty or invalid.", "Invalid columns.");

        } else if (!"Gender".equals(currentRow.getCell(5).getStringCellValue())) {
            throw new ValidationException("CL-206", "Row 2,Gender is empty or invalid.", "Invalid columns.");

        } else if (!"MobileNumber".equals(currentRow.getCell(6).getStringCellValue())) {
            throw new ValidationException("CL-207", "Row 2,MobileNumber is empty or invalid.", "Invalid columns.");

        } else if (!"Email".equals(currentRow.getCell(7).getStringCellValue())) {
            throw new ValidationException("CL-208", "Row 2,Email  is empty or invalid.", "Invalid columns.");

        } else if (!"Dob".equals(currentRow.getCell(8).getStringCellValue())) {
            throw new ValidationException("CL-209", "Row 2,Dob is empty or invalid.", "Invalid columns.");

        } else if (!"Address".equals(currentRow.getCell(9).getStringCellValue())) {
            throw new ValidationException("CL-210", "Row 2,Address is empty or invalid.", "Invalid columns.");

        } else if (!"MasterPolicyNo".equals(currentRow.getCell(10).getStringCellValue())) {
            throw new ValidationException("CL-211", "Row 2,MasterPolicyNo is empty or invalid.", "Invalid columns.");

        } else if (!"BorrowerType".equals(currentRow.getCell(11).getStringCellValue())) {
            throw new ValidationException("CL-212", "Row 2,BorrowerType is empty or invalid.", "Invalid columns.");

        } else if (!"PremiumType".equals(currentRow.getCell(12).getStringCellValue())) {
            throw new ValidationException("CL-213", "Row 2,PremiumType is empty or invalid.", "Invalid columns.");

        }else if (!"LoanAmount".equals(currentRow.getCell(13).getStringCellValue())) {
            throw new ValidationException("CL-214", "Row 2,LoanAmount is empty or invalid.", "Invalid columns.");

        } else if (!"LoanTenure".equals(currentRow.getCell(14).getStringCellValue())) {
            throw new ValidationException("CL-215", "Row 2,LoanTenure is empty or invalid.", "Invalid columns.");

        } else if (!"StartDate".equals(currentRow.getCell(15).getStringCellValue())) {
            throw new ValidationException("CL-216", "Row 2,StartDate is empty or invalid.", "Invalid columns.");

        }else if (!"Criticalillness".equals(currentRow.getCell(16).getStringCellValue())) {
            throw new ValidationException("CL-217", "Row 2,Criticalillness is empty or invalid.", "Invalid columns.");

        } else if (!"DeathOnly".equals(currentRow.getCell(17).getStringCellValue())) {
            throw new ValidationException("CL-218", "Row 2,DeathOnly is empty or invalid.", "Invalid columns.");

        } else if (!"PermanentDisability".equals(currentRow.getCell(18).getStringCellValue())) {
            throw new ValidationException("CL-219", "Row 2,PermanentDisability is empty or invalid.", "Invalid columns.");

        } else if (!"JobLoss".equals(currentRow.getCell(19).getStringCellValue())) {
            throw new ValidationException("CL-220", "Row 2,JobLoss is empty or invalid.", "Invalid columns.");

        } else if (!"LossOfBusiness".equals(currentRow.getCell(20).getStringCellValue())) {
            throw new ValidationException("CL-221", "Row 2,LossOfBusiness is empty or invalid.", "Invalid columns.");

        } else if (!"LoanNumberThirdParty".equals(currentRow.getCell(21).getStringCellValue())) {
            throw new ValidationException("CL-222", "Row 2,LoanNumberThirdParty is empty or invalid.", "Invalid columns.");

        } else if (!"Narration".equals(currentRow.getCell(22).getStringCellValue())) {
            throw new ValidationException("CL-223", "Row 2,Narration is empty or invalid.", "Invalid columns.");

        } else if (!"TrackingRefrence".equals(currentRow.getCell(23).getStringCellValue())) {
            throw new ValidationException("CL-224", "Row 2,TrackingRefrence is empty or invalid.", "Invalid columns.");

        } 

        return true;

    }
	
	public static List<CreditLifeExcelDto> creditLifeExcelUpload(InputStream inputStream) {
		try {
			Workbook workbook = new XSSFWorkbook(inputStream);
			DataFormatter formatter = new DataFormatter();
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();

			List<CreditLifeExcelDto> bulkFileModels = new ArrayList<CreditLifeExcelDto>();

			int rowNumber = 0;
			int rowNumberCount = 2;
			int count = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				if (rowNumber == 1) {
					verifyColumnNamesCreditLife(currentRow);
					rowNumber++;
					continue;
				}

				rowNumberCount++;
				CreditLifeExcelDto model = new CreditLifeExcelDto();
				
				// sr no
				int srNo = (int) currentRow.getCell(0).getNumericCellValue();
				model.setSrNo(srNo);
				
				// ----Title
				String title = formatter.formatCellValue(currentRow.getCell(1));
				model.setTitle(title);
				
				
				// ----FirstName
				if (!UtilityFunctions.validateFirstName(currentRow.getCell(2).getStringCellValue())) {
					throw new ValidationException("CL-225","Row #" + rowNumberCount + "'s First Name field is either empty or contain invalid data!","Invalid First Name.");
				}
				model.setFirstName(currentRow.getCell(2).getStringCellValue());

				// ----MiddleName
				String middleName = formatter.formatCellValue(currentRow.getCell(3));
				model.setMiddleName(middleName);

				// ----LastName
				if (!UtilityFunctions.validateFirstName(currentRow.getCell(4).getStringCellValue())) {
					throw new ValidationException("CL-226","Row #" + rowNumberCount + "'s Last Name field is either empty or contain invalid data!","Invalid Last Name.");
				}
				model.setLastName(currentRow.getCell(4).getStringCellValue());
				
				// ----gender
				if ("M".equals(currentRow.getCell(5).getStringCellValue()) || "F".equals(currentRow.getCell(5).getStringCellValue())) {
					model.setGender(formatter.formatCellValue(currentRow.getCell(5)));
				} else {
					throw new ValidationException("CL-227","Row #" + rowNumberCount + "'s Gender field is either empty or contain invalid data!","Gender should be M or F.");
				}
				
				// ----MobileNumber
				String phoneNo = currentRow.getCell(6).getStringCellValue();
				if (!UtilityFunctions.validateMobile(phoneNo)) {
					throw new ValidationException("CL-228","Row #" + rowNumberCount + "'s Phone Number field is either empty or contain invalid data!","Invalid Imei No.");
				}
				model.setMobileNumber(String.valueOf(phoneNo));
				
				// ----email
				String email = formatter.formatCellValue(currentRow.getCell(7));
				if (!UtilityFunctions.validateEmail(email)) {
					throw new ValidationException("CL-229","Row #" + rowNumberCount + "'s Email field is either empty or contain invalid data!","Invalid Email.");
				}
				model.setEmail(email);
				
				// ----dob
				if (DateUtil.isCellDateFormatted(currentRow.getCell(8))) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date dob = currentRow.getCell(8).getDateCellValue();
					Date today = new Date();
					if (dob.after(today)) {
						throw new ValidationException("CL-230","Row #" + rowNumberCount + "'s DOB field is either empty or contain invalid data!","Invalid DOB.");

					}
					String date = format.format(dob);
					model.setDob(date);
				}
				
				//---- Address
				String address = formatter.formatCellValue(currentRow.getCell(9));
				model.setAddress(address);
				
				//---- MasterPolicyNo
				String masterPolicyNo = formatter.formatCellValue(currentRow.getCell(10));
				model.setMasterPolicyNo(masterPolicyNo);
				
				
				// ----BorrowerType
				if ("Salaried".equals(currentRow.getCell(11).getStringCellValue()) || "SME".equals(currentRow.getCell(11).getStringCellValue()) || "Trader".equals(currentRow.getCell(11).getStringCellValue())) {
					model.setBorrowerType(formatter.formatCellValue(currentRow.getCell(11)));
				} else {
					throw new ValidationException("CL-231","Row #" + rowNumberCount + "'s Borrower Type field is either empty or contain invalid data!","Gender should be M or F.");
				}
				
				// ----PremiumType
				if (currentRow.getCell(12).getNumericCellValue() == 1 || currentRow.getCell(12).getNumericCellValue() == 4) {
					model.setPremiumType(formatter.formatCellValue(currentRow.getCell(12)));
				} else {
					throw new ValidationException("CL-232","Row #" + rowNumberCount + "'s Premium Type field is either empty or contain invalid data!","Gender should be M or F.");
				}
				
				// ----LoanAmount
				double loanAmount = currentRow.getCell(13).getNumericCellValue();
				model.setLoanAmount(BigDecimal.valueOf(loanAmount));
				
//				BigDecimal loanAmount = currentRow.getCell(13);
//				model.setLoanAmount(loanAmount);
				
				//----LoanTenure
				
				int loanTenure = (int) currentRow.getCell(14).getNumericCellValue();
				model.setLoanTenure(loanTenure);
				
				// ----StartDate
				if (DateUtil.isCellDateFormatted(currentRow.getCell(15))) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date dateOfPurchase = currentRow.getCell(15).getDateCellValue();
					LocalDate curDate = LocalDate.now();
					Date today = java.sql.Date.valueOf(curDate);
					if (dateOfPurchase.before(today)) {
						throw new ValidationException("CL-233","Row #" + rowNumberCount + "'s Start Date field should not be back date!","Invalid Date of Purchase.");
					}
					String date = format.format(dateOfPurchase);
					model.setStartDate(date);
				}

				// ----Criticalillness
				String criticalillness = formatter.formatCellValue(currentRow.getCell(16));
				if (!UtilityFunctions.validateTrueFalse(criticalillness)) {
					throw new ValidationException("CL-234","Row #" + rowNumberCount + "'s Criticalillness field is either empty or contain invalid data!","Invalid Imei No.");
				}
				boolean criticalillnessnew=("yes".equalsIgnoreCase(criticalillness)) ? true: false;  
				model.setCriticalillness(criticalillnessnew);
				
				// ----DeathOnly
				String deathOnly = formatter.formatCellValue(currentRow.getCell(17));
				if (!UtilityFunctions.validateTrueFalse(deathOnly)) {
					throw new ValidationException("CL-235","Row #" + rowNumberCount + "'s Death Only field is either empty or contain invalid data!","Invalid Imei No.");
				}
				boolean deathOnlynew=("yes".equalsIgnoreCase(deathOnly)) ? true: false; 
				model.setDeathOnly(deathOnlynew);
				
				// ----PermanentDisability
				String permanentDisability = formatter.formatCellValue(currentRow.getCell(18));
				if (!UtilityFunctions.validateTrueFalse(permanentDisability)) {
					throw new ValidationException("CL-236","Row #" + rowNumberCount + "'s Permanent Disability field is either empty or contain invalid data!","Invalid Imei No.");
				}
				boolean permanentDisabilitynew=("yes".equalsIgnoreCase(permanentDisability)) ? true: false; 
				model.setPermanentDisability(permanentDisabilitynew);
				
				// ----JobLoss
				String jobLoss = formatter.formatCellValue(currentRow.getCell(19));
				if (!UtilityFunctions.validateTrueFalse(jobLoss)) {
					throw new ValidationException("CL-237","Row #" + rowNumberCount + "'s Job Loss field is either empty or contain invalid data!","Invalid Imei No.");
				}
				boolean jobLossNew=("yes".equalsIgnoreCase(jobLoss)) ? true: false; 
				model.setJobLoss(jobLossNew);
				
				// ----LossOfBusiness
				String lossOfBusiness = formatter.formatCellValue(currentRow.getCell(20));
				if (!UtilityFunctions.validateTrueFalse(lossOfBusiness)) {
					throw new ValidationException("CL-238","Row #" + rowNumberCount + "'s Loss Of Business field is either empty or contain invalid data!","Invalid Imei No.");
				}
				
				boolean lossOfBusinessnew =("yes".equalsIgnoreCase(lossOfBusiness)) ? true: false; 
				model.setLossOfBusiness(lossOfBusinessnew);

				

				//---- LoanNumberThirdParty
				String loanNumberThirdParty = formatter.formatCellValue(currentRow.getCell(21));
				model.setLoanNumberThirdParty(loanNumberThirdParty);
				
				
				//---- Narration
				String narration = formatter.formatCellValue(currentRow.getCell(22));
				model.setNarration(narration);
				
				//---- TrackingRefrence
				String trackingRefrence = formatter.formatCellValue(currentRow.getCell(23));
				if (!UtilityFunctions.validateDeviceSrNo(trackingRefrence)) {
					throw new ValidationException("CL-239","Row #" + rowNumberCount + "'s Tracking Refrence field is either empty or contain invalid data!","Invalid Imei No.");
				}
				model.setTrackingRefrence(trackingRefrence);

				bulkFileModels.add(model);
			}

			workbook.close();

			return bulkFileModels;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
