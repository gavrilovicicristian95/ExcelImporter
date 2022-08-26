package com.excel.importer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import com.excel.importer.model.Customer;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "CustomerName", "BookingDate", "OpportunityID", "BookingType", "Total", "BookingDate"
            , "AccountExecutive", "SalesOrganization", "Team", "Product", "Renewable"};
    static String SHEET = "MOCK_DATA_47";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Customer> removeDuplicates(List<Customer> customers){

        Set<String> nameSet = new HashSet<>();
        List<Customer> customersDistinctByOpportunityId = customers.stream()
                .filter(e -> nameSet.add(e.getOpportunityId()))
                .collect(Collectors.toList());

        return customersDistinctByOpportunityId;
    }

    public static List<Customer> excelToCustomers(InputStream is, String range) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            String[] rangeArray = range.split(":");

            int rowStart = Integer.valueOf(rangeArray[0].replaceAll("\\D+",""));
            int rowEnd = Integer.valueOf(rangeArray[1].replaceAll("\\D+",""));

            String columnStart = rangeArray[0].replaceAll("[^A-Za-z]+", "");
            int columnStartIndex = CellReference.convertColStringToIndex(columnStart);
            String columnEnd = rangeArray[1].replaceAll("[^A-Za-z]+", "");
            int columnEndIndex = CellReference.convertColStringToIndex(columnEnd);

            Sheet sheet = workbook.getSheet(SHEET);

            Iterator<Row> rows = sheet.iterator();

            List<Customer> customers = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber < 3) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Customer customer = new Customer();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    int columnValue = currentCell.getColumnIndex();
                    int rowValue = currentCell.getRowIndex();

                    if((rowValue > rowEnd || rowValue < rowStart) || (columnValue < columnStartIndex || columnValue > columnEndIndex))
                        continue;
                    switch (cellIdx) {
                        case 0:
                            customer.setCustomerName(currentCell.getStringCellValue());
                            break;

                        case 1:
                            customer.setBookingDate(currentCell.getDateCellValue());
                            break;

                        case 2:
                            customer.setOpportunityId(currentCell.getStringCellValue());
                            break;

                        case 3:
                            customer.setBookingType(currentCell.getStringCellValue());
                            break;

                        case 4:
                            customer.setTotal(String.valueOf(currentCell.getNumericCellValue()));
                            break;

                        case 5:
                            customer.setAccountExecutive(currentCell.getStringCellValue());
                            break;

                        case 6:
                            customer.setSalesOrganization(currentCell.getStringCellValue());
                            break;

                        case 7:
                            customer.setTeam(currentCell.getStringCellValue());
                            break;

                        case 8:
                            customer.setProduct(currentCell.getStringCellValue());
                            break;

                        case 9:
                            customer.setRenewable(currentCell.getStringCellValue());
                            break;

                        default:
                            break;
                    }
                    cellIdx++;
                }

                customers.add(customer);
            }

            workbook.close();

            return removeDuplicates(customers);
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

}