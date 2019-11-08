package sample.DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import sample.Classes.*;
import sample.Controllers.DialogController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;



@SuppressWarnings("ALL")
public class DataAccessObject {
    SimpleDateFormat file_date = new SimpleDateFormat("dd-MM-yyyy");
    String path = System.getProperty("user.dir");
    private DBConnection database = new DBConnection();
    private ResultSet rs;
    private PreparedStatement pstmt;
    private Connection connect;
    private Statement stmt;

    public DataAccessObject() {
    }

    public void saveData(String query) {
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.pstmt.executeUpdate();
        } catch (Exception var6) {
            final String ss = var6.getMessage();
                var6.printStackTrace();
        } finally {
            this.database.close(this.connect, this.pstmt, null);
        }

    }

    public ObservableList<Category> getCategoryData(String query) {
        ObservableList Categorylist = FXCollections.observableArrayList();

        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
               Categorylist.add(new Category(rs.getString(1), rs.getInt(2)));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return Categorylist;
    }

    public ObservableList<Products> getProductsData(String query) {
        ObservableList productlist = FXCollections.observableArrayList();

        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
               productlist.add(new Products(
                       rs.getString(1),
                       rs.getString(2),
                       rs.getDouble(3),
                       rs.getInt(4),
                       rs.getString(5),
                       rs.getInt(6)));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return productlist;
    }

    public ObservableList<String> getCategoryComboBox(String query) {
        ObservableList Category_combobox_list = FXCollections.observableArrayList();

        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                Category_combobox_list.add(this.rs.getString("name_category"));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return Category_combobox_list;
    }

    public ObservableList<Clients> getClientsData(String query) {
        ObservableList list = FXCollections.observableArrayList();
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                list.add(new Clients(
                        this.rs.getString(1),
                        this.rs.getString(2),
                        this.rs.getString(3),
                        this.rs.getDouble(4),
                        this.rs.getDouble(5),
                        this.rs.getInt(6)));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return list;
    }



    public ObservableList<String> getProductComboBox(String query) {
        ObservableList list = FXCollections.observableArrayList();

        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                list.add(this.rs.getString("name_product"));

            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return list;
    }

    public ObservableList<String> getObjectComboBox(String query) {
        ObservableList list = FXCollections.observableArrayList();

        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                list.add(this.rs.getString("name_"));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return list;
    }



    public ObservableList<Orders> getOrdersData(String query) {
        ObservableList list = FXCollections.observableArrayList();
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                list.add(new Orders(this.rs.getInt(1), this.rs.getString(2), this.rs.getDate(3), rs.getDouble(4), rs.getDouble(5)));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return list;
    }

    public ObservableList<OrderDetails> getOrderDetailsData(String query) {
        ObservableList list = FXCollections.observableArrayList();
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                list.add(new OrderDetails( this.rs.getString(1), this.rs.getInt(2), this.rs.getInt(3), this.rs.getDouble(4), this.rs.getInt(5)));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return list;
    }



    public ObservableList<Integer> getOrdersComboBox(String query) {
        ObservableList list = FXCollections.observableArrayList();
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                list.add(this.rs.getInt(1));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return list;
    }


    public ObservableList<OrdersHistory> getOrdersHistoryData(String query) {
        ObservableList list = FXCollections.observableArrayList();
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                list.add(new OrdersHistory(
                        this.rs.getInt(1),
                        this.rs.getString(2),
                        this.rs.getString(3),
                        this.rs.getDouble(4),
                        this.rs.getInt(5),
                        this.rs.getString(6),
                        this.rs.getDate(7),
                        this.rs.getDouble(8),
                        this.rs.getDouble(9)));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return list;
    }

    public ObservableList<Integer> getOHComboBox(String query) {
        ObservableList list = FXCollections.observableArrayList();
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                list.add(this.rs.getInt(1));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return list;
    }

    public ObservableList<Suppliers> getSuppliersData(String query) {
        ObservableList list = FXCollections.observableArrayList();
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                list.add(new Suppliers(rs.getString("name_sup"), rs.getString("adress_sup"), rs.getString("telephone_sup"), rs.getInt(4)));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return list;
    }

    public ObservableList<Supplies> getSuppliesData(String query) {
        ObservableList list = FXCollections.observableArrayList();
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                list.add(new Supplies(rs.getInt(1), rs.getString(2), rs.getDate(3)));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return list;
    }

    public ObservableList<Supply_Details> getSupplyDetailsData(String query) {
        ObservableList list = FXCollections.observableArrayList();
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                list.add(new Supply_Details(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return list;
    }

    public ObservableList<String> getSuppliersComboBox(String query) {
        ObservableList list = FXCollections.observableArrayList();
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                list.add(this.rs.getString("name_sup"));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return list;
    }

    public ObservableList getSuppliesComboBox(String query) {
        ObservableList list = FXCollections.observableArrayList();
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                list.add(this.rs.getString(1));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return list;
    }

    public ObservableList<SuppliesHistory> getSuppliesHistoryData(String query) {
        ObservableList list = FXCollections.observableArrayList();
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                list.add(new SuppliesHistory(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getDate(6)));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return list;
    }

    public ObservableList<Integer> getSuppliesIDComboBox(String query) {
        ObservableList list = FXCollections.observableArrayList();
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                list.add(this.rs.getInt(1));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return list;
    }

    public Double getAllOrderSum(String query) {
        double sum = 0.0;

        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            while (this.rs.next()) {
                sum = rs.getDouble(1);

            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return sum;
    }

    
    public InputStream getAllOrders(String query, String file_name){
        InputStream input = null;
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Order Info Sheet");
            HSSFCellStyle stylecell = wb.createCellStyle();
            HSSFFont font = wb.createFont();
            HSSFRow rowhead = sheet.createRow((short) 0);
            font.setBold(true);
            stylecell.setFont(font);
            stylecell.setBorderLeft(BorderStyle.THICK);
            stylecell.setBorderRight(BorderStyle.THICK);
            stylecell.setBorderTop(BorderStyle.THICK);
            stylecell.setBorderBottom(BorderStyle.THICK);
            stylecell.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            stylecell.setTopBorderColor(IndexedColors.BLACK.getIndex());
            stylecell.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            stylecell.setRightBorderColor(IndexedColors.BLACK.getIndex());

            rowhead.createCell((short) 0).setCellValue("№ Заказа");
            rowhead.createCell((short) 1).setCellValue("Товар");
            rowhead.createCell((short) 2).setCellValue("Категория");
            rowhead.createCell((short) 3).setCellValue("Цена");
            rowhead.createCell((short) 4).setCellValue("Количество");
            rowhead.createCell((short) 5).setCellValue("Заказчик");
            rowhead.createCell((short) 6).setCellValue("Дата");
            rowhead.createCell((short) 7).setCellValue("Скидка");
            rowhead.createCell((short) 8).setCellValue("Общая сумма");

            int index = 1;
            while (rs.next()) {

                HSSFRow row = sheet.createRow((short) index);
                row.createCell((short) 0).setCellValue(rs.getInt(1));
                row.createCell((short) 1).setCellValue(rs.getString(2));
                row.createCell((short) 2).setCellValue(rs.getString(3));
                row.createCell((short) 3).setCellValue(rs.getDouble(4));
                row.createCell((short) 4).setCellValue(rs.getInt(5));
                row.createCell((short) 5).setCellValue(rs.getString(6));
                row.createCell((short) 6).setCellValue(rs.getString(7));
                row.createCell((short) 7).setCellValue(rs.getDouble(8));
                row.createCell((short) 8).setCellValue(rs.getDouble(9));

                index++;

                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                sheet.autoSizeColumn(7);
                sheet.autoSizeColumn(8);

                row = sheet.getRow(0);
                for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                    row.getCell(i).setCellStyle(stylecell);
                }
            }

            FileOutputStream fileOut = new FileOutputStream(new File(path + File.separator + file_name + "(" +file_date.format(new Date()) +").xls"));
            wb.write(fileOut);
            fileOut.close();
            DialogController.showInfoDialog("Оповещение", "Excel-файл успешно создан");
            rs.close();
            connect.close();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return input;
    }

    public InputStream getSupplies(String query, String file_name){
        InputStream input = null;
        try {
            this.connect = this.database.getConnection();
            this.pstmt = this.connect.prepareStatement(query);
            this.rs = this.pstmt.executeQuery();

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Supplies Info Sheet");
            HSSFCellStyle stylecell = wb.createCellStyle();
            HSSFFont font = wb.createFont();
            HSSFRow rowhead = sheet.createRow((short) 0);

            font.setBold(true);
            stylecell.setFont(font);
            stylecell.setBorderLeft(BorderStyle.THICK);
            stylecell.setBorderRight(BorderStyle.THICK);
            stylecell.setBorderTop(BorderStyle.THICK);
            stylecell.setBorderBottom(BorderStyle.THICK);

            stylecell.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            stylecell.setTopBorderColor(IndexedColors.BLACK.getIndex());
            stylecell.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            stylecell.setRightBorderColor(IndexedColors.BLACK.getIndex());

            rowhead.createCell((short) 0).setCellValue("№ Поставки");
            rowhead.createCell((short) 1).setCellValue("Товар");
            rowhead.createCell((short) 2).setCellValue("Категория");
            rowhead.createCell((short) 3).setCellValue("Количество");
            rowhead.createCell((short) 4).setCellValue("Поставщик");
            rowhead.createCell((short) 5).setCellValue("Дата");

            int index = 1;
            while (rs.next()) {

                HSSFRow row = sheet.createRow((short) index);
                row.createCell((short) 0).setCellValue(rs.getInt(1));
                row.createCell((short) 1).setCellValue(rs.getString(2));
                row.createCell((short) 2).setCellValue(rs.getString(3));
                row.createCell((short) 3).setCellValue(rs.getInt(4));
                row.createCell((short) 4).setCellValue(rs.getString(5));
                row.createCell((short) 5).setCellValue(rs.getString(6));

                index++;

                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);

                row = sheet.getRow(0);
                for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                    row.getCell(i).setCellStyle(stylecell);
                }
            }

            FileOutputStream fileOut = new FileOutputStream(new File(path + File.separator + file_name + "(" +file_date.format(new Date()) +").xls"));
            wb.write(fileOut);
            fileOut.close();
            DialogController.showInfoDialog("Оповещение", "Excel-файл успешно создан");
            rs.close();
            connect.close();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return input;
    }




}


