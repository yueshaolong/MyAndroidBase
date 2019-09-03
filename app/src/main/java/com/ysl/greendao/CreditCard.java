package com.ysl.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class CreditCard {
    @Id(autoincrement = true)
    Long id;

    int studentNo;
    int teacherNo;
    String userName;//持有者名字
    String cardNum;//卡号
    String whichBank;//哪个银行的
    int cardType;//卡等级 0 ~ 5

    @Generated(hash = 1860989810)
    public CreditCard() {
    }

    @Generated(hash = 26054732)
    public CreditCard(Long id, int studentNo, int teacherNo, String userName,
            String cardNum, String whichBank, int cardType) {
        this.id = id;
        this.studentNo = studentNo;
        this.teacherNo = teacherNo;
        this.userName = userName;
        this.cardNum = cardNum;
        this.whichBank = whichBank;
        this.cardType = cardType;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getCardNum() {
        return this.cardNum;
    }
    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
    public String getWhichBank() {
        return this.whichBank;
    }
    public void setWhichBank(String whichBank) {
        this.whichBank = whichBank;
    }
    public int getCardType() {
        return this.cardType;
    }
    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public int getStudentNo() {
        return this.studentNo;
    }

    public void setStudentNo(int studentNo) {
        this.studentNo = studentNo;
    }

    public int getTeacherNo() {
        return this.teacherNo;
    }

    public void setTeacherNo(int teacherNo) {
        this.teacherNo = teacherNo;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "studentNo=" + studentNo +
                ", teacherNo=" + teacherNo +
                ", userName='" + userName + '\'' +
                ", cardNum='" + cardNum + '\'' +
                ", whichBank='" + whichBank + '\'' +
                ", cardType=" + cardType +
                '}';
    }
}
