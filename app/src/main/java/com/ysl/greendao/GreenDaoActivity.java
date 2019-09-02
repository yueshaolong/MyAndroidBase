package com.ysl.greendao;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ysl.MyApp;
import com.ysl.myandroidbase.R;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GreenDaoActivity extends AppCompatActivity {

    private DaoSession daoSession;
    private Random mRandom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
        ButterKnife.bind(this);

        daoSession = ((MyApp) getApplication()).getDaoSession();
        mRandom = new Random();
    }

    @OnClick({R.id.button4, R.id.button5, R.id.button6, R.id.button7})
    public void button(View view) {
        switch (view.getId()) {
            case R.id.button4:
                insertData();
                insertData2();
                break;
            case R.id.button5:
                deleteData(new Student());
                deleteAll();
                break;
            case R.id.button6:
                updataData(new Student());
                break;
            case R.id.button7:
                queryRaw("1");
                queryAll();
            break;

            default:

                break;
        }

    }

    /**
     * 增
     */
    public void insertData() {
        for (int i = 0; i < 1000; i++) {
            Student student = new Student();
            student.setStudentNo(i);
            int age = mRandom.nextInt(10) + 10;
            student.setAge(age);
            student.setTelPhone(RandomValue.getTel());
            String chineseName = RandomValue.getChineseName();
            student.setName(chineseName);
            if (i % 2 == 0) {
                student.setSex("男");
            } else {
                student.setSex("女");
            }
            student.setAddress(RandomValue.getRoad());
            student.setGrade(String.valueOf(age % 10) + "年纪");
            student.setSchoolName(RandomValue.getSchoolName());
            daoSession.insert(student);
        }
    }
    public void insertData2() {
        for (int i = 0; i < 1000; i++) {
            Student student = new Student();
            student.setStudentNo(i);
            int age = mRandom.nextInt(10) + 10;
            student.setAge(age);
            student.setTelPhone(RandomValue.getTel());
            String chineseName = RandomValue.getChineseName();
            student.setName(chineseName);
            if (i % 2 == 0) {
                student.setSex("男");
            } else {
                student.setSex("女");
            }
            student.setAddress(RandomValue.getRoad());
            student.setGrade(String.valueOf(age % 10) + "年纪");
            student.setSchoolName(RandomValue.getSchoolName());
            daoSession.insertOrReplace(student);//插入或替换
        }
    }

    /**
     * 删
     */
    public void deleteData(Student s) {
        daoSession.delete(s);
    }
    public void deleteAll() {
        daoSession.deleteAll(Student.class);
    }

    /**
     * 改
     * @param s 要改的对象
     */
    public void updataData(Student s) {
        daoSession.update(s);
    }

    /**
     * loadAll()：查询所有数据。
     * queryRaw()：根据条件查询。
     * queryBuilder() : 方便查询的创建，后面详细讲解。
     */
    public List queryAll(){
        List<Student> students = daoSession.loadAll(Student.class);
        return students;
    }
    public List<Student> queryRaw(String s) {
        List<Student> students = daoSession.queryRaw(Student.class, " where id = ?", s);
        return students;
    }
    //查询当前Student表的所有的数据
    public List queryAllList(){
        QueryBuilder<Student> qb = daoSession.queryBuilder(Student.class);
        List<Student> list = qb.list(); // 查出所有的数据
        return list;
    }
    //查询Name为“一”的所有Student
    public List queryListByMessage(String name){
        QueryBuilder<Student> qb = daoSession.queryBuilder(Student.class);
        QueryBuilder<Student> studentQueryBuilder = qb.where(StudentDao.Properties.Name.eq("一")).orderAsc(StudentDao.Properties.Name);
        List<Student> studentList = studentQueryBuilder.list(); //查出当前对应的数据
        return studentList;
    }
    // 查询ID大于5的所有学生
    public List queryListBySqL(){
        Query<Student> query = daoSession.queryBuilder(Student.class).where(
                new WhereCondition.StringCondition("_ID IN " +
                        "(SELECT _ID FROM STUDENT WHERE _ID > 5)")
        ).build();
        List<Student> list = query.list();
        return list;
    }
    //查询Id大于5小于10，且Name值为"一"的数据
    public List queryList(){
        QueryBuilder<Student> qb = daoSession.queryBuilder(Student.class);
        qb = daoSession.queryBuilder(Student.class);
        List<Student> list2 = qb.where(
                StudentDao.Properties.Name.eq("一"),
                qb.and(StudentDao.Properties.Id.gt(5),
                        StudentDao.Properties.Id.le(50)))
                .list();
        return  list2;
    }
    //取10条Id大于1的数据，且偏移2条
    public List queryListByOther(){
        QueryBuilder<Student> qb = daoSession.queryBuilder(Student.class);
        //搜索条件为Id值大于1，即结果为[2,3,4,5,6,7,8,9,10,11];
        // offset(2)表示往后偏移2个，结果为[4,5,6,7,8,9,10,11,12,13];
        List<Student> list = qb.where(StudentDao.Properties.Id.gt(1)).limit(10).offset(2).list();
        return list;
    }

    /**
     * 使用QueryBuilder构建查询后，可以重用 Query对象以便稍后执行查询。
     * 这比始终创建新的Query对象更有效。
     * 如果查询参数没有更改，您可以再次调用list / unique方法。
     * 可以通过setParameter方法来修改条件参数值
     * @return
     */
    public List queryListByMoreTime(){
        QueryBuilder<Student> qb = daoSession.queryBuilder(Student.class);

        //搜索条件为Id值大于1，即结果为[2,3,4,5,6,7,8,9,10,11];
        // offset(2)表示往后偏移2个，结果为[4,5,6,7,8,9,10,11,12,13];
        Query<Student> query = qb.where(StudentDao.Properties.Id.gt(1)).limit(10).offset(2).build();
        List<Student> list = query.list();
        Log.d("queryListByMoreTime", "queryListByMoreTime: "+list);

        //通过SetParameter来修改上面的查询条件，比如我们将上面条件修改取10条Id值大于5，往后偏移两位的数据，方法如下！
        query.setParameter(0,5);
        List<Student> list1 = query.list();
        return list1;
    }

    /**
     * 使用QueryBuilder进行批量删除操作，不会删除单个实体，但会删除符合某些条件的所有实体。
     * 要执行批量删除，请创建QueryBuilder，调用其 buildDelete （）方法，然后执行返回的 DeleteQuery。
     */
     // 删除数据库中id大于5的所有其他数据
    public boolean deleteItem(){
        QueryBuilder<Student> where = daoSession.queryBuilder(Student.class)
                .where(StudentDao.Properties.Id.gt(5));
        DeleteQuery<Student> deleteQuery = where.buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
        return false;
    }

    /**
     * 一对一关联身份证
     */
    public void addStudent(){
        Student student = new Student();
        student.setStudentNo(1000);
        int age = mRandom.nextInt(10) + 10;
        student.setAge(age);
        student.setTelPhone(RandomValue.getTel());
        String chineseName = RandomValue.getChineseName();
        student.setName(chineseName);
        if (1000 % 2 == 0) {
            student.setSex("男");
        } else {
            student.setSex("女");
        }
        student.setAddress(RandomValue.getRoad());
        student.setGrade(String.valueOf(age % 10) + "年纪");
        student.setSchoolName(RandomValue.getSchoolName());
        daoSession.insert(student);

        //插入对应的IdCard数据
        IdCard idCard = new IdCard();
        idCard.setUserName("as");
        idCard.setIdNo(RandomValue.getRandomID());
        daoSession.insert(idCard);
    }

    /**
     * 一对多关联信用卡
     */
    public void addStudent2(){
        Student student = new Student();
        student.setStudentNo(1000);
        int age = mRandom.nextInt(10) + 10;
        student.setAge(age);
        student.setTelPhone(RandomValue.getTel());
        String chineseName = RandomValue.getChineseName();
        student.setName(chineseName);
        if (1000 % 2 == 0) {
            student.setSex("男");
        } else {
            student.setSex("女");
        }
        student.setAddress(RandomValue.getRoad());
        student.setGrade(String.valueOf(age % 10) + "年纪");
        student.setSchoolName(RandomValue.getSchoolName());
        daoSession.insert(student);

        //插入对应的CreditCard数据
        for (int j = 0; j < mRandom.nextInt(5) + 1 ; j++) {
            CreditCard creditCard = new CreditCard();
            creditCard.setUserId(123l);
            creditCard.setUserName("addf");
            creditCard.setCardNum(String.valueOf(mRandom.nextInt(899999999)
                    + 100000000) + String.valueOf(mRandom.nextInt(899999999)
                    + 100000000));
            creditCard.setWhichBank(RandomValue.getBankName());
            creditCard.setCardType(mRandom.nextInt(10));
            daoSession.insert(creditCard);
        }
    }

    /**
     * 多对多关联
     */
    public void addData(){
        //添加一个学生
        Student  student = new Student();
        student.setStudentNo(3);
        int age = mRandom.nextInt(10) + 10;
        student.setAge(age);
        student.setTelPhone(RandomValue.getTel());
        String chineseName = RandomValue.getChineseName();
        student.setName(chineseName);
        if (3 % 2 == 0) {
            student.setSex("男");
        } else {
            student.setSex("女");
        }
        student.setAddress(RandomValue.getRoad());
        student.setGrade(String.valueOf(age % 10) + "年纪");
        student.setSchoolName(RandomValue.getSchoolName());
        daoSession.insert(student);
        //添加至少四个老师
        for (int i =0; i < mRandom.nextInt(8) + 4; i++) {
            Teacher teacher = new Teacher();
            teacher.setTeacherNo(i);
            int age1 = mRandom.nextInt(20) + 18;
            teacher.setAge(age1);
            String chineseName1 = RandomValue.getChineseName();
            teacher.setName(chineseName1);
            teacher.setSchoolName(RandomValue.getSchoolName());
            if (i % 2 == 0) {
                teacher.setSex("男");
            } else {
                teacher.setSex("女");
            }
            teacher.setTelPhone(RandomValue.getTel());
            teacher.setSubject(RandomValue.getRandomSubject());
            daoSession.insert(teacher);
        }

        //把这个学生和每一个老师关联起来
        List<Teacher> teacherList = daoSession.loadAll(Teacher.class);
        Collections.shuffle(teacherList);
        for (int j = 0; j < mRandom.nextInt(8) + 1; j++) {
            if(j < teacherList.size()){
                Teacher teacher = teacherList.get(j);
                StudentAndTeacherBean teacherBean = new StudentAndTeacherBean(0l,
                        student.getId(), teacher.getId());
                daoSession.insert(teacherBean);
            }
        }
    }
}