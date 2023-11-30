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
import com.erp.api.dto.PerAccidentExcelDto;
import com.erp.api.exceptions.ValidationException;
import com.erp.api.utility.UtilityFunctions;


@Component
public class PersonalAccExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";


    public static boolean hasExcelFormat(MultipartFile file) {

        return TYPE.equals(file.getContentType());
    }

    
    public static Boolean verifyColumnNamesPersonalAccident(Row currentRow) {
        if (!"ID".equals(currentRow.getCell(0).getStringCellValue())) {
            throw new ValidationException("PA-301", "Row 2,ID is empty or invalid.", "Invalid columns.");
        } else if (!"Title".equals(currentRow.getCell(1).getStringCellValue())) {
            throw new ValidationException("PA-302", "Row 2,Title is empty or invalid.", "Invalid columns.");

        } else if (!"FirstName".equals(currentRow.getCell(2).getStringCellValue())) {
            throw new ValidationException("PA-303", "Row 2,First Name is empty or invalid.", "Invalid columns.");

        } else if (!"MiddleName".equals(currentRow.getCell(3).getStringCellValue())) {
            throw new ValidationException("PA-304", "Row 2,Middle Name is empty or invalid.", "Invalid columns.");

        } else if (!"LastName".equals(currentRow.getCell(4).getStringCellValue())) {
            throw new ValidationException("PA-305", "Row 2,Last Name is empty or invalid.", "Invalid columns.");

        } else if (!"Gender".equals(currentRow.getCell(5).getStringCellValue())) {
            throw new ValidationException("PA-306", "Row 2,Gender is empty or invalid.", "Invalid columns.");

        } else if (!"MobileNumber".equals(currentRow.getCell(6).getStringCellValue())) {
            throw new ValidationException("PA-307", "Row 2,MobileNumber is empty or invalid.", "Invalid columns.");

        } else if (!"Email".equals(currentRow.getCell(7).getStringCellValue())) {
            throw new ValidationException("PA-308", "Row 2,Email  is empty or invalid.", "Invalid columns.");

        } else if (!"Dob".equals(currentRow.getCell(8).getStringCellValue())) {
            throw new ValidationException("PA-309", "Row 2,Dob is empty or invalid.", "Invalid columns.");

        } else if (!"Address".equals(currentRow.getCell(9).getStringCellValue())) {
            throw new ValidationException("PA-310", "Row 2,Address is empty or invalid.", "Invalid columns.");

        } else if (!"Country".equals(currentRow.getCell(10).getStringCellValue())) {
            throw new ValidationException("PA-311", "Row 2,Country is empty or invalid.", "Invalid columns.");

        } else if (!"State".equals(currentRow.getCell(11).getStringCellValue())) {
            throw new ValidationException("PA-312", "Row 2,State is empty or invalid.", "Invalid columns.");

        } else if (!"Lga".equals(currentRow.getCell(12).getStringCellValue())) {
            throw new ValidationException("PA-313", "Row 2,Lga is empty or invalid.", "Invalid columns.");

        }else if (!"StartDate".equals(currentRow.getCell(13).getStringCellValue())) {
            throw new ValidationException("PA-314", "Row 2,Start Date is empty or invalid.", "Invalid columns.");

        } else if (!"IdType".equals(currentRow.getCell(14).getStringCellValue())) {
            throw new ValidationException("PA-315", "Row 2,Id Type is empty or invalid.", "Invalid columns.");

        } else if (!"IdNumber".equals(currentRow.getCell(15).getStringCellValue())) {
            throw new ValidationException("PA-316", "Row 2,Id Number is empty or invalid.", "Invalid columns.");

        }else if (!"BuildingName".equals(currentRow.getCell(16).getStringCellValue())) {
            throw new ValidationException("PA-317", "Row 2,Building Name is empty or invalid.", "Invalid columns.");

        } else if (!"InsurerLocation".equals(currentRow.getCell(17).getStringCellValue())) {
            throw new ValidationException("PA-318", "Row 2,Insurer Location is empty or invalid.", "Invalid columns.");

        } else if (!"InsurerOccupation".equals(currentRow.getCell(18).getStringCellValue())) {
            throw new ValidationException("PA-319", "Row 2,Insurer Occupation is empty or invalid.", "Invalid columns.");

        } else if (!"InsurerBusinessType".equals(currentRow.getCell(19).getStringCellValue())) {
            throw new ValidationException("PA-320", "Row 2,Insurer Business Type is empty or invalid.", "Invalid columns.");

        } else if (!"WorkHourRange".equals(currentRow.getCell(20).getStringCellValue())) {
            throw new ValidationException("PA-321", "Row 2,Work Hour Range is empty or invalid.", "Invalid columns.");

        } else if (!"PersonalSpecialisation".equals(currentRow.getCell(21).getStringCellValue())) {
            throw new ValidationException("PA-322", "Row 2,Personal Specialisation is empty or invalid.", "Invalid columns.");

        } else if (!"EstimateWages".equals(currentRow.getCell(22).getStringCellValue())) {
            throw new ValidationException("PA-323", "Row 2,Estimate Wages is empty or invalid.", "Invalid columns.");

        } else if (!"EstimateEarning".equals(currentRow.getCell(23).getStringCellValue())) {
            throw new ValidationException("PA-324", "Row 2,Estimate Earning is empty or invalid.", "Invalid columns.");

        }else if (!"NextToKin".equals(currentRow.getCell(24).getStringCellValue())) {
            throw new ValidationException("PA-325", "Row 2,Next ToKin is empty or invalid.", "Invalid columns.");

        } else if (!"NokRelationship".equals(currentRow.getCell(25).getStringCellValue())) {
            throw new ValidationException("PA-326", "Row 2,Nok Relationship is empty or invalid.", "Invalid columns.");

        } else if (!"NokPhoneNumber".equals(currentRow.getCell(26).getStringCellValue())) {
            throw new ValidationException("PA-327", "Row 2,Nok Phone Number is empty or invalid.", "Invalid columns.");

        } else if (!"NokAddress".equals(currentRow.getCell(27).getStringCellValue())) {
            throw new ValidationException("PA-328", "Row 2,Nok Address is empty or invalid.", "Invalid columns.");

        } else if (!"SumInsuredAmount".equals(currentRow.getCell(28).getStringCellValue())) {
            throw new ValidationException("PA-329", "Row 2,Sum Insured Amount is empty or invalid.", "Invalid columns.");

        }
        return true;
    }
    
  
	
	public static List<PerAccidentExcelDto> creditLifeExcelUpload(InputStream inputStream) {
		try {
			Workbook workbook = new XSSFWorkbook(inputStream);
			DataFormatter formatter = new DataFormatter();
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();

			List<PerAccidentExcelDto> bulkFileModels = new ArrayList<PerAccidentExcelDto>();

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
					verifyColumnNamesPersonalAccident(currentRow);
					rowNumber++;
					continue;
				}

				rowNumberCount++;
				PerAccidentExcelDto model = new PerAccidentExcelDto();
				// sr no
				if (currentRow.getCell(0) != null) {
					int srNo = (int) currentRow.getCell(0).getNumericCellValue();
					model.setSrNo(srNo);
				} else {
					throw new ValidationException("PA-330","Row #" + rowNumberCount + "'s ID field is either empty or contain invalid data!","Invalid First Name.");
				}
				
				// ----Title
				if (currentRow.getCell(1) != null) {
					String title = formatter.formatCellValue(currentRow.getCell(1));
					model.setTitle(title);
				}else {
					throw new ValidationException("PA-356","Row #" + rowNumberCount + "'s Title field is either empty or blank data!","Invalid First Name.");
				}
				
				
				// ----FirstName
				if (currentRow.getCell(2) != null) {
					if (!UtilityFunctions.validateFirstName(currentRow.getCell(2).getStringCellValue())) {
						throw new ValidationException("PA-331","Row #" + rowNumberCount+ "'s First Name field is contain invalid data!","Invalid First Name.");
					}
					model.setFirstName(currentRow.getCell(2).getStringCellValue());
				} else {
					throw new ValidationException("PA-331","Row #" + rowNumberCount + "'s First Name field is either empty or blank data!","Invalid First Name.");
				}

				// ----MiddleName
				String middleName = formatter.formatCellValue(currentRow.getCell(3));
				model.setMiddleName(middleName);

				// ----LastName
				if (currentRow.getCell(4) != null) {
					if (!UtilityFunctions.validateFirstName(currentRow.getCell(4).getStringCellValue())) {
						throw new ValidationException("PA-332","Row #" + rowNumberCount+ "'s Last Name field is contain invalid data!","Invalid Last Name.");
					}
					model.setLastName(currentRow.getCell(4).getStringCellValue());
				} else {
					throw new ValidationException("PA-332","Row #" + rowNumberCount + "'s Last Name field is either empty or blank!","Invalid Last Name.");
				}
				
				// ----gender
				if (currentRow.getCell(5) != null) {
					if ("M".equals(currentRow.getCell(5).getStringCellValue()) || "F".equals(currentRow.getCell(5).getStringCellValue())) {
						model.setGender(formatter.formatCellValue(currentRow.getCell(5)));
					} else {
						throw new ValidationException("PA-333","Row #" + rowNumberCount + "'s Gender field is contain invalid data!","Gender should be M or F.");
					}
				} else {
					throw new ValidationException("PA-333","Row #" + rowNumberCount + "'s Gender field is either empty or blank data!","Gender should be M or F.");
				}
				
				// ----MobileNumber
				if (currentRow.getCell(6) != null) {
					String phoneNo = currentRow.getCell(6).getStringCellValue();
					if (!UtilityFunctions.validateMobile(phoneNo)) {
						throw new ValidationException("PA-334","Row #" + rowNumberCount+ "'s Phone Number field is contain invalid data!","Invalid Imei No.");
					}
					model.setMobileNumber(String.valueOf(phoneNo));
				} else {
					throw new ValidationException("PA-334","Row #" + rowNumberCount + "'s Phone Number field is either empty or blank data!","Invalid Imei No.");
				}
				
				// ----email
				if (currentRow.getCell(7) != null) {
					String email = formatter.formatCellValue(currentRow.getCell(7));
					if (!UtilityFunctions.validateEmail(email)) {
						throw new ValidationException("PA-335","Row #" + rowNumberCount + "'s Email field is contain invalid data!","Invalid Email.");
					}
					model.setEmail(email);
				} else {
					throw new ValidationException("PA-335","Row #" + rowNumberCount + "'s Email field is either empty or blank data!","Invalid Email.");
				}
				
				// ----dob
				if (currentRow.getCell(8) != null) {
					if (DateUtil.isCellDateFormatted(currentRow.getCell(8))) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date dob = currentRow.getCell(8).getDateCellValue();
						Date today = new Date();
						if (dob.after(today)) {
							throw new ValidationException("PA-336","Row #" + rowNumberCount + "'s DOB field is contain invalid data!","Invalid DOB.");

						}
						String date = format.format(dob);
						model.setDob(date);
					}
				} else {
					throw new ValidationException("PA-336","Row #" + rowNumberCount + "'s DOB field is either empty or blank data!","Invalid DOB.");
				}
				
				
				//---- Address
				if (currentRow.getCell(9) != null) {
					String address = formatter.formatCellValue(currentRow.getCell(9));
					if (!UtilityFunctions.validateAddress(address)) {
						throw new ValidationException("PA-337","Row #" + rowNumberCount + "'s Address field is contain invalid data!","Invalid First Name.");
					}
					model.setAddress(address);
				} else {
					throw new ValidationException("PA-337","Row #" + rowNumberCount + "'s Address field is either empty or blank data!","Invalid First Name.");
				}
				
				//---- Country
				if (currentRow.getCell(10) != null) {
					String country = formatter.formatCellValue(currentRow.getCell(10));
					if ("50001".equals(country)) {
						model.setCountry(country);
					} else {
						throw new ValidationException("PA-338","Row #" + rowNumberCount + "'s Country field is contain invalid data!","Invalid First Name.");
					}
					
				} else {
					throw new ValidationException("PA-338","Row #" + rowNumberCount + "'s Country field is either empty or blank data!","Invalid First Name.");
				}
				
				
				// ----State
				if (currentRow.getCell(11) != null) {
					String state = formatter.formatCellValue(currentRow.getCell(11));
					if (!UtilityFunctions.validateIntegerValue(state)) {
						throw new ValidationException("PA-339","Row #" + rowNumberCount + "'s State field is contain invalid data!","Invalid First Name.");
					}
					model.setState(state);
				} else {
					throw new ValidationException("PA-339","Row #" + rowNumberCount + "'s State field is either empty or blank data!","Invalid First Name.");
				}
				
				// ----Lga
				if (currentRow.getCell(12) != null) {
					String lga = formatter.formatCellValue(currentRow.getCell(12));
					if (!UtilityFunctions.validateIntegerValue(lga)) {
						throw new ValidationException("PA-340","Row #" + rowNumberCount + "'s Lga field is contain invalid data!","Invalid First Name.");
					}
					model.setLga(lga);
				} else {
					throw new ValidationException("PA-340","Row #" + rowNumberCount + "'s Lga field is either empty or blank data!","Invalid First Name.");
				}
				
				// ----StartDate
				if (currentRow.getCell(13) != null) {
					if (DateUtil.isCellDateFormatted(currentRow.getCell(13))) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date dateOfPurchase = currentRow.getCell(13).getDateCellValue();

						LocalDate minusDate = LocalDate.now().minusDays(30);
						Date mintoday = java.sql.Date.valueOf(minusDate);
						System.out.println("------minus 30 Days------------" + mintoday);

						LocalDate curDate = LocalDate.now().plusDays(30);
						Date plustoday = java.sql.Date.valueOf(curDate);
						System.out.println("------plus 30 days------------" + plustoday);

						if (dateOfPurchase.after(mintoday) && dateOfPurchase.before(plustoday)) {
							String date = format.format(dateOfPurchase);
							model.setStartDate(date);

						} else {
							throw new ValidationException("PA-341","Row #" + rowNumberCount+ "'s Start Date field is contain invalid data!","Invalid Date of Purchase.");
						}

					}
				} else {
					throw new ValidationException("PA-341","Row #" + rowNumberCount + "'s Start Date field is either empty or blank data!","Invalid Date of Purchase.");
				}
				
				// id type
//                if(responseeeDto.getKycStatus() ==1) {
				if(true) {
                String idType = currentRow.getCell(14).getStringCellValue();
                if (!UtilityFunctions.validateIdType(idType)) {
                    throw new ValidationException("PA-342", "Row #" +rowNumberCount+"'s ID Type field is contain invalid data!", "Invalid ID Type.");
                }
                model.setIdType(idType);

                // id number
                String idNumber = formatter.formatCellValue(currentRow.getCell(15));
                if (idNumber.isEmpty()) {
                    throw new ValidationException("PA-342", "Row #" +rowNumberCount+"'s ID Number field is either empty or blank data!", "Invalid ID No.");
                }
                model.setIdNumber(idNumber);
                }else {
                	String idType = currentRow.getCell(14).getStringCellValue();
                	model.setIdType(idType);
                	String idNumber = formatter.formatCellValue(currentRow.getCell(15));
                	model.setIdNumber(idNumber);
                }

				// ----BuildingName
				if (currentRow.getCell(16) != null) {
					if (!UtilityFunctions.validateNormalText(currentRow.getCell(16).getStringCellValue())) {
						throw new ValidationException("PA-343","Row #" + rowNumberCount + "'s Building Name field is contain invalid data!","Invalid First Name.");
					}
					model.setBuildingName(currentRow.getCell(16).getStringCellValue());
				} else {
					throw new ValidationException("PA-343","Row #" + rowNumberCount + "'s Building Name field is either empty or blank data!","Invalid First Name.");
				}
				
				// ----InsurerLocation
				if (currentRow.getCell(17) != null) {
					if (!UtilityFunctions.validateAddress(currentRow.getCell(17).getStringCellValue())) {
						throw new ValidationException("PA-344","Row #" + rowNumberCount+ "'s Insurer Location field is contain invalid data!","Invalid First Name.");
					}
					model.setInsurerLocation(currentRow.getCell(17).getStringCellValue());
				} else {
					throw new ValidationException("PA-344","Row #" + rowNumberCount+ "'s Insurer Location field is either empty or blank data!","Invalid First Name.");
				}
				
				// ----InsurerOccupation
				if (currentRow.getCell(18) != null) {
					if (!UtilityFunctions.validateNormalText(currentRow.getCell(18).getStringCellValue())) {
						throw new ValidationException("PA-345","Row #" + rowNumberCount+ "'s Insurer Occupation field is contain invalid data!","Invalid First Name.");
					}
					model.setInsurerOccupation(currentRow.getCell(18).getStringCellValue());
				} else {
					throw new ValidationException("PA-345","Row #" + rowNumberCount+ "'sInsurer Occupation field is either empty or blank data!","Invalid First Name.");
				}
				
				// ----InsurerBusinessType
				if (currentRow.getCell(19) != null) {
					if (!UtilityFunctions.validateNormalText(currentRow.getCell(19).getStringCellValue())) {
						throw new ValidationException("PA-346","Row #" + rowNumberCount+ "'s Insurer Business Type field is contain invalid data!","Invalid First Name.");
					}
					model.setInsurerBusinessType(currentRow.getCell(19).getStringCellValue());
				} else {
					throw new ValidationException("PA-346","Row #" + rowNumberCount+ "'s Insurer Business Type field is either empty or blank data!","Invalid First Name.");
				}
				
				// ----WorkHourRange
				if (currentRow.getCell(20) != null) {
					if (!UtilityFunctions.validateDoubleValue(currentRow.getCell(20).getNumericCellValue())) {
						throw new ValidationException("PA-347","Row #" + rowNumberCount+ "'s WorkHour Range field is contain invalid data!","Invalid First Name.");
					}
					int workHour=(int)currentRow.getCell(20).getNumericCellValue();
					System.out.println("-----------"+workHour);
					model.setWorkHourRange(workHour);
				} else {
					throw new ValidationException("PA-347","Row #" + rowNumberCount+ "'s WorkHour Range field is either empty or blank data!","Invalid First Name.");
				}
				

				//---- PersonalSpecialisation
				if (currentRow.getCell(21) != null) {
					if (!UtilityFunctions.validateNormalText(currentRow.getCell(21).getStringCellValue())) {
						throw new ValidationException("PA-348","Row #" + rowNumberCount+ "'s Personal Specialisation field is contain invalid data!","Invalid First Name.");
					}
					model.setPersonalSpecialisation(currentRow.getCell(21).getStringCellValue());
				} else {
					throw new ValidationException("PA-348","Row #" + rowNumberCount+ "'s Personal Specialisation field is either empty or blank data!","Invalid First Name.");
				}
				
				
				//---- EstimateWages
				if (currentRow.getCell(22) != null) {
					double estimateWages = currentRow.getCell(22).getNumericCellValue();
					model.setEstimateWages(BigDecimal.valueOf(estimateWages));
				} else {
					throw new ValidationException("PA-349","Row #" + rowNumberCount + "'s Estimate Wages field is either empty or blank data!","Invalid First Name.");
				}
				
				//---- EstimateEarning
				if (currentRow.getCell(23) != null) {
					double estimateEarning = currentRow.getCell(23).getNumericCellValue();
					model.setEstimateEarning(BigDecimal.valueOf(estimateEarning));
				} else {
					throw new ValidationException("PA-350","Row #" + rowNumberCount + "'s Estimate Earning field is either empty or blank data!","Invalid First Name.");
				}
				
				//---- NextToKin
				if (currentRow.getCell(24) != null) {
					if (!UtilityFunctions.validateNormalText(currentRow.getCell(24).getStringCellValue())) {
						throw new ValidationException("PA-351","Row #" + rowNumberCount + "'s NextToKin field is contain invalid data!","Invalid First Name.");
					}
					model.setNextToKin(currentRow.getCell(24).getStringCellValue());
				} else {
					throw new ValidationException("PA-351","Row #" + rowNumberCount + "'s NextToKin field is either empty or blank data!","Invalid First Name.");
				}
				
				//---- NokRelationship
				if (currentRow.getCell(25) != null) {
					if (!UtilityFunctions.validateNormalText(currentRow.getCell(25).getStringCellValue())) {
						throw new ValidationException("PA-352","Row #" + rowNumberCount+ "'s Nok Relationship field is contain invalid data!","Invalid First Name.");
					}
					model.setNokRelationship(currentRow.getCell(25).getStringCellValue());
				} else {
					throw new ValidationException("PA-352","Row #" + rowNumberCount + "'s Nok Relationship field is either empty or blank data!","Invalid First Name.");
				}
				//---- NokPhoneNumber
				if (currentRow.getCell(26) != null) {
					if (!UtilityFunctions.validateMobile(currentRow.getCell(26).getStringCellValue())) {
						throw new ValidationException("PA-353","Row #" + rowNumberCount + "'s Nok Phone Number field is contain invalid data!","Invalid First Name.");
					}
					model.setNokPhoneNumber(currentRow.getCell(26).getStringCellValue());
				} else {
					throw new ValidationException("PA-353","Row #" + rowNumberCount + "'s Nok Phone Number field is either empty or blank data!","Invalid First Name.");
				}
				
				//---- NokAddress
				if (currentRow.getCell(27) != null) {
					if (!UtilityFunctions.validateAddress(currentRow.getCell(27).getStringCellValue())) {
						throw new ValidationException("PA-354","Row #" + rowNumberCount + "'s Nok Address field is contain invalid data!","Invalid First Name.");
					}
					model.setNokAddress(currentRow.getCell(27).getStringCellValue());
				} else {
					throw new ValidationException("PA-354","Row #" + rowNumberCount + "'s Nok Address field is either empty or blank data!","Invalid First Name.");
				}
				
				if (currentRow.getCell(28) != null) {
					double sumInsureAmount = currentRow.getCell(28).getNumericCellValue();
					model.setSumInsuredAmount(BigDecimal.valueOf(sumInsureAmount));
				} else {
					throw new ValidationException("PA-355","Row #" + rowNumberCount + "'s SumInsure Amount field is either empty or blank data!","Invalid First Name.");
				}

				bulkFileModels.add(model);
			}

			workbook.close();

			return bulkFileModels;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}


	
}
