import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.*;
import java.util.Date;
import java.util.Scanner;




public class Main {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // create database
            String DB_URL = "jdbc:mysql://localhost:3306/assignment";
            String DB_USER = "root";
            String DB_PASSWORD = "";
            //Connect db
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            System.out.println("1 Add a new book");
            System.out.println("2. Search book by id");
            System.out.println("3. Search book by name");
            System.out.println("4. Get all book");
            System.out.println("5. Update a book");
            System.out.println("6. Remove a book");
            System.out.println("7. Export a book to txt file");
            System.out.println("8. Export list book to list txt file");
            Scanner scanner = new Scanner(System.in);
            Scanner choice = new Scanner(System.in);
            int Chon = choice.nextInt();
            while (Chon >=1 && Chon<=8){
            switch(Chon){
                case 1:{
                    System.out.println("title book:");
                    String title = scanner.nextLine();
                    System.out.println("Author book:");
                    String author = scanner.nextLine();
                    System.out.println("release date book:");
                    String releaseDate = scanner.nextLine();
                    System.out.println("Content book:");
                    String content = scanner.nextLine();
                    String addStudent ="INSERT INTO book (title,author,releaseDate,content) VALUES ('"+title+"','"+author+"','"+releaseDate+"','"+content+"')";
                    statement.executeUpdate(addStudent);
                    System.out.println("add success");
                    break;
                }case 2: {
                    System.out.println("Enter the ID to search");
                    int id = scanner.nextInt();
                    String sql = "SELECT* FROM book WHERE id ="+id;
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()){
                        System.out.println("Name of book:"+resultSet.getString("title")+" Content: "+resultSet.getString("content")+"Author: "+resultSet.getString("author"));
                    }
                    break;
                }
                case 3: {
                    System.out.println("Enter the Book name to search");
                    String name = scanner.nextLine();
                    String sql = "SELECT * FROM book WHERE name="+name;
                    //Error
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()){
                        System.out.println("Name of book : "+resultSet.getString("title")+" Content: "+resultSet.getString("content")+"Author: "+resultSet.getString("author"));
                    }
                }
                case 4: {
                    String sql = "SELECT * FROM book ";
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()){
                        System.out.println("Name of book: "+resultSet.getString("title")+" Content: "+resultSet.getString("content")+"Author: "+resultSet.getString("author"));
                    }
                    break;
                }
                case 5:{
                    System.out.println("Input ID Update");
                    int id = scanner.nextInt();
                    System.out.println("Name of book");
                    String name = choice.nextLine();
                    String update = "UPDATE book SET title = ? WHERE id = ?";
                    PreparedStatement preparedStmt = connection.prepareStatement(update);
                    preparedStmt.setInt   (1, id);
                    preparedStmt.setString(2, name);
                    // execute the java preparedstatement
                    preparedStmt.executeUpdate();

                    connection.close();
                    System.out.println("update success");
                    break;

                }
                case 6:{
                    System.out.println("Enter ID for remove book");
                    int id = scanner.nextInt();
                    String sql = "DELETE FROM book WHERE id="+id;
                    statement.execute(sql);
                    System.out.println("Remove success");
                    break;
                }
                case 7 : {
                    System.out.println("Choice a book to export to txt file by id: ");
                    int id = scanner.nextInt();
                    statement = connection.createStatement();
                    String sql = "SELECT * FROM book WHERE id = " + id;
                    ResultSet rec = statement.executeQuery(sql);
                    String path = "C:\\Users\\LENOVO\\IdeaProjects\\ASMADF2\\abook.txt";
                    FileWriter writer;
                    try{
                        File file = new File(path);
                        writer = new FileWriter(file, true);
                        while ((rec!=null) && (rec.next())){
                            writer.write(rec.getString("id"));
                            writer.write(",");
                            writer.write(rec.getString("title"));
                            writer.write(",");
                            writer.write(rec.getString("author"));
                            writer.write(",");
                            writer.write(rec.getString("releaseDate"));
                            writer.write(",");
                            writer.write(rec.getString("content"));
                            writer.write("\r\n");
                        }
                        writer.close();
                        System.out.println("Export to txt file success!");
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    try{
                        if(connection !=null){
                            statement.close();
                            connection.close();
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                    break;
                }
                case 8 : {
                    statement = connection.createStatement();
                    String sql = "SELECT * FROM book ORDER BY id ASC";
                    ResultSet resultSet = statement.executeQuery(sql);
                    String path = "C:\\Users\\LENOVO\\IdeaProjects\\ASMADF2\\allbook.txt";
                    FileWriter writer;
                    try {
                        File file = new File(path);
                        writer = new FileWriter(file, true);
                        while ((resultSet != null) && (resultSet.next())) {
                            writer.write(resultSet.getString("id"));
                            writer.write(",");
                            writer.write(resultSet.getString("title"));
                            writer.write(",");
                            writer.write(resultSet.getString("author"));
                            writer.write(",");
                            writer.write(resultSet.getString("releaseDate"));
                            writer.write(",");
                            writer.write(resultSet.getString("content"));
                            writer.write("\r\n");
                        }
                        writer.close();
                        System.out.println("Export to txt file success!");
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                    try {
                        if (connection != null) {
                            statement.close();
                            connection.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
