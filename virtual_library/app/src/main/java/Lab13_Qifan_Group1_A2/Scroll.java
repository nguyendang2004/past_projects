package Lab13_Qifan_Group1_A2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

import Lab13_Qifan_Group1_A2.users.User;

public class Scroll {
    private int scrollID; //from 0 to n
    private Date date;
    private String name;
    private User author;
    private String path;
    private int numDownloads;

    //initialise
    public Scroll (int scrollID, Date date, String name, User author, String path, int numDownloads){
        this.scrollID = scrollID;
        this.date = date;
        this.name = name;
        this.author = author;
        this.path = path;
        this.numDownloads = numDownloads;
    }

    //getters
    public int getScrollID() {
        return scrollID;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public User getAuthor() {
        return author;
    }

    public String getPath() {
        return path;
    }

    //setters
    public void setScrollID(int scrollID) {
        this.scrollID = scrollID;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getNumDownloads() {
        return numDownloads;
    }

    public void setNumDownloads(int numDownloads) {
        this.numDownloads = numDownloads;
    }

    public boolean isSameScroll(Scroll scroll) {
        return this.scrollID == scroll.getScrollID();
    }
    /* public static boolean checkContent(String content){
        for (int i =0;i < content.length(); i++){
            if ((content.charAt(i) != '0') && (content.charAt(i) != '1')) {
                return false;
            }
        }
        return true;
    } */
    public String getContent() {
        File file = new File(path);
        String read;
        Scanner fileRead = null;
        try {
            fileRead = new Scanner(file);
            read = fileRead.nextLine();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(path);
        fileRead.close();
        return read;
    }

}
