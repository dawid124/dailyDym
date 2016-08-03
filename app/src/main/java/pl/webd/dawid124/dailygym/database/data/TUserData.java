package pl.webd.dawid124.dailygym.database.data;

import java.util.Date;

/**
 * Created by Java on 2016-05-08.
 */
public class TUserData {
    private String userName;
    private Date createdDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
