package com.example.kofeservices;

public class BookingClass {

    public  BookingClass(){}

    String  bookingid;
    String bookinguserid;
    String bookingdate;
    String bookingdescription;
    String bookingweburl;
    String bookingservices;
    String bookingmessage;
    String usersname;
    String  useremail;

    public BookingClass(String bookingid, String bookinguserid, String bookingdate, String bookingdescription, String bookingweburl, String bookingservices, String bookingmessage, String usersname, String useremail) {
        this.bookingid = bookingid;
        this.bookinguserid = bookinguserid;
        this.bookingdate = bookingdate;
        this.bookingdescription = bookingdescription;
        this.bookingweburl = bookingweburl;
        this.bookingservices = bookingservices;
        this.bookingmessage = bookingmessage;
        this.usersname = usersname;
        this.useremail = useremail;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsersname() {
        return usersname;
    }

    public void setUsersname(String usersname) {
        this.usersname = usersname;
    }


    public String getBookingid() {
        return bookingid;
    }

    public String getBookingservices() {
        return bookingservices;
    }

    public void setBookingservices(String bookingservices) {
        this.bookingservices = bookingservices;
    }
    public void setBookingid(String bookingid) {
        this.bookingid = bookingid;
    }

    public String getBookinguserid() {
        return bookinguserid;
    }

    public void setBookinguserid(String bookinguserid) {
        this.bookinguserid = bookinguserid;
    }

    public String getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(String bookingdate) {
        this.bookingdate = bookingdate;
    }

    public String getBookingdescription() {
        return bookingdescription;
    }

    public void setBookingdescription(String bookingdescription) {
        this.bookingdescription = bookingdescription;
    }

    public String getBookingweburl() {
        return bookingweburl;
    }

    public void setBookingweburl(String bookingweburl) {
        this.bookingweburl = bookingweburl;
    }



    public String getBookingmessage() {
        return bookingmessage;
    }

    public void setBookingmessage(String bookingmessage) {
        this.bookingmessage = bookingmessage;
    }


}
