package com.ysl.greendao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

@Entity
public class Student {
    @Id(autoincrement = true)
    Long id;

    @Unique
    int studentNo;//学号
    int age; //年龄
    String telPhone;//手机号
    String sex; //性别
    String name;//姓名
    String address;//家庭住址
    String schoolName;//学校名字
    String grade;//几年级

    //一对一关联，一个学生关联一个身份证
    @ToOne(joinProperty = "name")//一个name对一个IdCard，joinProperty指定外键
    IdCard idCard;//身份证

    //一对多关联，一个学生关联多张信用卡
    // 这个studentId是对应在CreditCard中的studentId
    @ToMany(referencedJoinProperty = "studentId")
    List<CreditCard> creditCardsList;

    @ToMany
    @JoinEntity(entity = StudentAndTeacherBean.class,
            sourceProperty = "studentId",
            targetProperty = "teacherId")
    List<Teacher> teacherList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1943931642)
    private transient StudentDao myDao;

    @Generated(hash = 137173928)
    private transient String idCard__resolvedKey;


    @Generated(hash = 1071002287)
    public Student(Long id, int studentNo, int age, String telPhone, String sex, String name,
            String address, String schoolName, String grade) {
        this.id = id;
        this.studentNo = studentNo;
        this.age = age;
        this.telPhone = telPhone;
        this.sex = sex;
        this.name = name;
        this.address = address;
        this.schoolName = schoolName;
        this.grade = grade;
    }
    @Generated(hash = 1556870573)
    public Student() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getStudentNo() {
        return this.studentNo;
    }
    public void setStudentNo(int studentNo) {
        this.studentNo = studentNo;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getTelPhone() {
        return this.telPhone;
    }
    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getSchoolName() {
        return this.schoolName;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    public String getGrade() {
        return this.grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1268960764)
    public List<CreditCard> getCreditCardsList() {
        if (creditCardsList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CreditCardDao targetDao = daoSession.getCreditCardDao();
            List<CreditCard> creditCardsListNew = targetDao._queryStudent_CreditCardsList(id);
            synchronized (this) {
                if (creditCardsList == null) {
                    creditCardsList = creditCardsListNew;
                }
            }
        }
        return creditCardsList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 441911208)
    public synchronized void resetCreditCardsList() {
        creditCardsList = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1986556941)
    public List<Teacher> getTeacherList() {
        if (teacherList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TeacherDao targetDao = daoSession.getTeacherDao();
            List<Teacher> teacherListNew = targetDao._queryStudent_TeacherList(id);
            synchronized (this) {
                if (teacherList == null) {
                    teacherList = teacherListNew;
                }
            }
        }
        return teacherList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 973821661)
    public synchronized void resetTeacherList() {
        teacherList = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 797242429)
    public IdCard getIdCard() {
        String __key = this.name;
        if (idCard__resolvedKey == null || idCard__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            IdCardDao targetDao = daoSession.getIdCardDao();
            IdCard idCardNew = targetDao.load(__key);
            synchronized (this) {
                idCard = idCardNew;
                idCard__resolvedKey = __key;
            }
        }
        return idCard;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 432650007)
    public void setIdCard(IdCard idCard) {
        synchronized (this) {
            this.idCard = idCard;
            name = idCard == null ? null : idCard.getUserName();
            idCard__resolvedKey = name;
        }
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1701634981)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStudentDao() : null;
    }
}
