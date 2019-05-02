import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.*;
import java.util.Date;
import java.util.Scanner;




public class Main {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // tao database
            String DB_URL = "jdbc:mysql://localhost:3306/assignment";
            String DB_USER = "root";
            String DB_PASSWORD = "";
            //Connect db
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            System.out.println("1:Input book");
            System.out.println("2:Select book by id");
            System.out.println("3:Select book by name");
            System.out.println("4:Select all book");
            System.out.println("5:Update book");
            System.out.println("6:Remove book");
            System.out.println("7:Export one book to txt");
            System.out.println("7:Export all book to txt");
            Scanner sc = new Scanner(System.in);
            Scanner ac = new Scanner(System.in);
            int Chon = sc.nextInt();
            switch(Chon){
                case 1:{
                    System.out.println("title book:");
                    String title = ac.nextLine();
                    System.out.println("Author book:");
                    String author = ac.nextLine();
                    System.out.println("release date book:");
                    String releaseDate = ac.nextLine();
                    System.out.println("Content book:");
                    String content = ac.nextLine();
                    String addStudent ="insert into book (title,author,releaseDate,content) values('"+title+"','"+author+"','"+releaseDate+"','"+content+"')";
                    statement.executeUpdate(addStudent);
                    System.out.println("add success");
                    break;
                }case 2: {
                    System.out.println("Enter the required book id");
                    int id = sc.nextInt();
                    String sql = "SELECT* FROM book WHERE id ="+id;
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()){
                        System.out.println("Title:"+resultSet.getString("title")+" Content: "+resultSet.getString("content")+"Author: "+resultSet.getString("author"));
                    }
                    break;
                }
                case 3: {
                    System.out.println("Enter the name of the book you are looking for");
                    String name = sc.nextLine();
                    String sql = "SELECT * FROM book WHERE name="+name;
                    //Error
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()){
                        System.out.println("Title : "+resultSet.getString("title")+" Content: "+resultSet.getString("content")+"Author: "+resultSet.getString("author"));
                    }
                }
                case 4: {
                    String sql = "SELECT * FROM book ";
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()){
                        System.out.println("Title: "+resultSet.getString("title")+" Content: "+resultSet.getString("content")+"Author: "+resultSet.getString("author"));
                    }
                    break;
                }
                case 5:{
                    System.out.println("Select the id you want to upload");
                    int id = sc.nextInt();
                    System.out.println("Title  book");
                    String name = ac.nextLine();
                    String query = "update book set title = ? where id = ?";
                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setString   (1, name);
                    preparedStmt.setInt(2, id);
                    // execute the java preparedstatement
                    preparedStmt.executeUpdate();

                    connection.close();
                    System.out.println("upload successfully");
                    break;

                }
                case 6:{
                    System.out.println("Select the id you want to delete");
                    int id = sc.nextInt();
                    String sql = "DELETE FROM book WHERE id="+id;
                    statement.execute(sql);
                    System.out.println("successfully deleted");
                    break;
                }
                case 7 : {
                    System.out.println("Enter the id you want to save");
                    int id =sc.nextInt();
                    String sql = "SELECT* FROM book WHERE id ="+id;
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()){
                        FileWriter fw = new FileWriter("D:\\onebook.txt");
                        fw.write("Title:"+resultSet.getString("Title: "+resultSet.getString("title")+" Content: "+resultSet.getString("content")+"Author: "+resultSet.getString("author")));
                        fw.close();

                    }
                    break;
                }
                case 8 :{

                    System.out.println("Enter the id you want to save");
                    int id =sc.nextInt();
                    String sql = "SELECT* FROM book";
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()){
                        FileWriter fw = new FileWriter("D:\\allbook.txt");
                        fw.write("Title"+resultSet.getString("Title: "+resultSet.getString("title")+" Content: "+resultSet.getString("content")+"Author: "+resultSet.getString("author")));
                        fw.close();

                    }
                }
                break;

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}