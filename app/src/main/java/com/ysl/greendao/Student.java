package com.ysl.greendao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

@Entity(
        //如果你有多个架构，你可以告诉GreenDao当前属于哪个架构。
//        schema = "myschema",
        //标记一个实体处于活跃状态，活动实体有更新、删除和刷新方法。默认false
        active = false,
        //在数据中使用的别名，默认使用的是实体的类名。
        nameInDb = "Student",
        //定义跨多个列的索引，也可以在在class内部，需要的定义的属性位置一一注解
        indexes = {
                //使用@Index作为一个属性来创建一个索引，通过name设置索引别名，也可以通过unique给索引添加约束。
                ////设置grade和name的多行索引，并设置唯一，代替组合唯一约束
//                @Index(value = "grade,name", unique = true)
        },
        //标识DAO类是否应该创建该数据库表(默认为true)，如果您有多个实体映射到一个表，
        // 或者表的创建是在greenDAO之外进行的，那么将其设置为false。
        createInDb = true,
        // 是否生成all-properties的构造器
        // 无参构造器总是会生成
        generateConstructors = true,
        //自动生成get,set方法，默认为true
        generateGettersSetters = true)
public class Student {
    //选择 long / Long属性作为实体ID。在数据库方面，它是主键。
    // 参数autoincrement = true 表示自增，id可以不赋值或者赋值为null即可
    // （这里需要注意，如果要实现自增，id必须是Long,为long不行！)。
    @Id(autoincrement = true)
    Long id;

    //向索引添加UNIQUE约束，强制所有值都是唯一的。注意，SQLite会隐式地为该列创建索引
    @Unique
    int studentNo;//学号

    //为该属性映射的列设置一个非默认的名称，默认是将单词大写，用下划线分割单词，
    // 如属性名customName对应列名CUSTOM_NAME
    @Property(nameInDb = "age")
    int age; //年龄

    String telPhone;//手机号
    String sex; //性别

    //表明这个列非空，通常使用@NotNull标记基本类型(long,int,short,byte)，
    // 然而可使用包装类型(Long, Integer, Short, Byte)使其可空
    @NotNull
    String name;//姓名

    String address;//家庭住址
    String schoolName;//学校名字

    //为相应的列创建索引
    //name   如果不想使用greenDAO为该属性生成的默认索引名称，可通过name设置
    //unique   给索引添加唯一性约束
    @Index(name = "nianji", unique = false)
    String grade;//几年级

    //一对一关联，一个学生关联一个身份证
    //定义与另一个实体（一个实体对象）的关系
    @ToOne(joinProperty = "name")//joinProperty指定外键,如Student实体的name属性就是外键
    IdCard idCard;//身份证

    //定义与多个实体对象的关系
    //一对多关联，一个学生关联多张信用卡
    // 这个studentId是对应在CreditCard中的studentId属性
    @ToMany(referencedJoinProperty = "no")//referencedJoinProperty 指定目标实体（CreditCard）的外键studentId
    List<CreditCard> creditCardsList;

    //对于更复杂的关系，可以指定一个@joinproperty注释列表，每个@joinproperty需要原始实体中的源属性和目标实体中的引用属性。
    //如下源属性为tag,目标属性customerTag；这里是编译通过的，因为Student里没有tag属性，CreditCard里也没有CreditCard属性。
    /*@ToMany(joinProperties = {@JoinProperty(name = "tag", referencedName = "CreditCard")})
    List<CreditCard> test;*/

    //如果两个实体是多对多的关系（Student和Teacher），那么需要第三张表StudentAndTeacherBean（表示两个实体关系的表）
    @ToMany
    @JoinEntity(entity = StudentAndTeacherBean.class,//添加的第三张关系表实体
            sourceProperty = "studentId",//第三张关系表实体内，源实体（Student）的属性
            targetProperty = "teacherId")//第三张关系表实体内，目标实体（Teacher）的属性
    List<Teacher> teacherList;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 1943931642)
private transient StudentDao myDao;

@Generated(hash = 1042624483)
public Student(Long id, int studentNo, int age, String telPhone, String sex, @NotNull String name,
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

@Generated(hash = 137173928)
private transient String idCard__resolvedKey;

    //@Keep-- 注解的代码段在GreenDao下次运行时保持不变
    //1.注解实体类：默认禁止修改此类
    //2.注解其他代码段，默认禁止修改注解的代码段

    //@OrderBy-- 指定排序,若要指定排序, 需在列明以后添加 ASC(升序) 或者DESC(降序) ,
    // 例如 "propertyA DESC, propertyB ASC" 默认按升序排序 ,若不设置默认根据主键排序

    //@Convert-- 使用Converter将自定义类转换为相关的值
    //如enum类, 可以将其转换为String

    //@Generated--  表示GreenDao自动生成的代码, 不要改动, 改动会报错
    //如果希望恢复自动生成代码, 则删除改动后的代码, 下次构建会自动生成
    //如果希望保留改动的代码并不要报错, 可将@Generated改为@Keep

    //@Transient--  表明此字段不存储到数据库中，用于不需要持久化的字段，比如临时状态
    // 也可以使用Java中的transient关键字。
    // 下面的代码是编译器自动生成的样式，也可以使用@Transient注解。


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentNo=" + studentNo +
                ", age=" + age +
                ", telPhone='" + telPhone + '\'' +
                ", sex='" + sex + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", grade='" + grade + '\'' +
                ", idCard=" + getIdCard() +
                ", creditCardsList=" + getCreditCardsList() +
                ", teacherList=" + getTeacherList() +
                '}';
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
@Generated(hash = 33979033)
public void setIdCard(@NotNull IdCard idCard) {
    if (idCard == null) {
        throw new DaoException(
                "To-one property 'name' has not-null constraint; cannot set to-one to null");
    }
    synchronized (this) {
        this.idCard = idCard;
        name = idCard.getUserName();
        idCard__resolvedKey = name;
    }
}

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Keep
public List<CreditCard> getCreditCardsList() {
    if (creditCardsList == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        CreditCardDao targetDao = daoSession.getCreditCardDao();
        List<CreditCard> creditCardsListNew = targetDao._queryStudent_CreditCardsList(studentNo);
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

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1701634981)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getStudentDao() : null;
}
}
